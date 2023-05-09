package com.example.cafesocial.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.cafesocial.R;
import com.example.cafesocial.core.activity.BaseActivity;
import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.adapter.ListAdapter;
import com.example.cafesocial.core.model.Model;
import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.model.Benefit;
import com.example.cafesocial.model.ImageUri;
import com.example.cafesocial.model.Purpose;
import com.example.cafesocial.model.Store;
import com.example.cafesocial.model.Type;
import com.example.cafesocial.service.HttpService;
import com.example.cafesocial.service.UserService;
import com.example.cafesocial.service.iservice.PostService;
import com.example.cafesocial.utils.FormData;
import com.example.cafesocial.utils.Toastify;
import com.example.cafesocial.view.fragment.DiscoverFragment;
import com.example.cafesocial.view.holder.ImageUriHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostActivity extends BaseActivity implements BaseItemListener {
    private final int PICK_IMAGE_REQUEST = 1;

    private TextView title;
    private CardView btnClose;
    private RatingBar rateLocation, rateSpace, rateService, ratePrice, rateMenu;
    private EditText inputTitle, inputContent;
    private CardView btnAddImage;
    private RecyclerView reImageAdd;
    private Button btnAddPost;

    private List<ImageUri> listImageUri = new ArrayList<>();
    private ListAdapter<ImageUri, ImageUriHolder> listImageAdapter;

    private Store store = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_post;
    }

    @Override
    public void init() {
        if(!UserService.isAuthen()) {
            goToActivity(LoginActivity.class);
        }
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        store = (Store) intent.getSerializableExtra("store");
        if(store == null) {
            goToActivity(MainActivity.class);
        }

        title = findViewById(R.id.title);
        title.setText("Đánh giá "+store.getName());

        btnClose = findViewById(R.id.btnClose);
        rateLocation = findViewById(R.id.rateStarLocation);
        rateService = findViewById(R.id.rateStarService);
        rateSpace = findViewById(R.id.rateStarSpace);
        ratePrice = findViewById(R.id.rateStarPrice);
        rateMenu = findViewById(R.id.rateStarMenu);
        inputTitle = findViewById(R.id.inputPostTitle);
        if(UserService.getUser() != null)
            inputTitle.setText("Đánh giá của "+UserService.getUser().getUsername()+" cho "+store.getName());
        inputContent = findViewById(R.id.inputPostContent);
        btnAddImage = findViewById(R.id.btnAddImage);
        reImageAdd = findViewById(R.id.listImageAdd);
        btnAddPost = findViewById(R.id.btnAddPost);
        btnAddPost.setTextColor(getResources().getColor(R.color.black));
        btnAddPost.setBackgroundColor(getResources().getColor(R.color.gray));

        listImageAdapter = new ListAdapter<ImageUri, ImageUriHolder>(R.layout.item_image_uri, ImageUriHolder.class, listImageUri, this);
        GridLayoutManager imageManager = new GridLayoutManager(this, 3);
        reImageAdd.setAdapter(listImageAdapter);
        reImageAdd.setLayoutManager(imageManager);
    }

    @Override
    public void initValidation() {
//        validation.addValidation(inputTitle, RegexTemplate.NOT_EMPTY, "Tiêu đề là bắt buộc");
        validation.addValidation(inputContent, RegexTemplate.NOT_EMPTY, "Nội dung là bắt buộc");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!UserService.isAuthen()) {
            goToActivity(LoginActivity.class);
        }
    }

    @Override
    public void initEvent() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermionAndPickImage(PICK_IMAGE_REQUEST);
            }
        });

        inputContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(validation.validate()) {
                    btnAddPost.setTextColor(getResources().getColor(R.color.white));
                    btnAddPost.setBackgroundColor(getResources().getColor(R.color.primary));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation.validate() && store != null) {
                    btnAddPost.setEnabled(false);
                    System.out.println(UserService.getUser());
                    String content = inputContent.getText().toString().trim();
                    System.out.println(UserService.getToken());
                    HttpService.get(PostService.class).createPost(
                            UserService.getToken(),
                            FormData.getStringPart(rateLocation.getRating()+""),
                            FormData.getStringPart(rateSpace.getRating()+""),
                            FormData.getStringPart(rateMenu.getRating()+""),
                            FormData.getStringPart(rateService.getRating()+""),
                            FormData.getStringPart(ratePrice.getRating()+""),
                            FormData.getStringPart(content),
                            FormData.getStringPart(store.getId()+""),
                            FormData.getMultipartFromArrayImage("images[]", listImageUri)
                    ).enqueue(new Callback<ResponseData<Boolean>>() {
                        @Override
                        public void onResponse(Call<ResponseData<Boolean>> call, Response<ResponseData<Boolean>> response) {
                            btnAddPost.setEnabled(true);
                            if(response.code() == 200) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("fragment", "discovery");
                                goToActivity(MainActivity.class, intent);
                                return;
                            } else {
                                System.out.println("call create post fail");
                                Toastify.toastError(getApplicationContext(), null);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseData<Boolean>> call, Throwable t) {
                            System.out.println("loi server");
                            System.out.println(t);
                            Toastify.toastError(getApplicationContext(), null);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onItemClick(int layoutId, View view, Model item , int position) {
        if(item instanceof ImageUri) {
                listImageUri.remove(item);
                listImageAdapter.notifyDataSetChanged();
        }
    }


    private void requestPermionAndPickImage(int requestType) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            pickImage(requestType);
            return;
        }
        // Các bạn nhớ request permison cho các máy M trở lên nhé, k là crash ngay đấy.
        int result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            pickImage(requestType);
        } else {
            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE}, requestType);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != PICK_IMAGE_REQUEST) return;
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage(requestCode);
        } else {
            Toast.makeText(getApplicationContext(), "Permission denine",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void pickImage(int requestType) {
        // Gọi intent của hệ thống để chọn ảnh nhé.
        Intent intent = new Intent();
        intent.setType("image/*");
        // Thêm dòng này để có thể select nhiều ảnh trong 1 lần nhé các bạn
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Files to Upload"),
                requestType);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null &&
                data.getClipData() != null) {
            listImageUri.addAll(getListImageUriFromDataIntent(data, PICK_IMAGE_REQUEST));
            listImageAdapter.notifyDataSetChanged();

        }
    }

    public List<ImageUri> getListImageUriFromDataIntent(Intent data, int requestCode) {
        List<ImageUri> list = new ArrayList<>();
        ClipData clipData = data.getClipData();

        for (int i = 0; i < clipData.getItemCount(); i++) {
            ClipData.Item item = clipData.getItemAt(i);
            Uri uri = item.getUri();
            list.add(new ImageUri(uri, requestCode, getRealPathFromURI(uri)));
        }
        return list;
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}