package com.example.cafesocial.view.fragment;

import android.Manifest.*;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.cafesocial.R;
import com.example.cafesocial.core.adapter.BaseItemListener;
import com.example.cafesocial.core.adapter.ListAdapter;
import com.example.cafesocial.core.fragment.BaseFragment;
import com.example.cafesocial.core.model.Model;
import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.model.Benefit;
import com.example.cafesocial.model.ImageUri;
import com.example.cafesocial.model.Location;
import com.example.cafesocial.model.Purpose;
import com.example.cafesocial.model.Store;
import com.example.cafesocial.model.Type;
import com.example.cafesocial.service.HttpService;
import com.example.cafesocial.service.UserService;
import com.example.cafesocial.service.iservice.AuthService;
import com.example.cafesocial.service.iservice.BenefitService;
import com.example.cafesocial.service.iservice.LocationService;
import com.example.cafesocial.service.iservice.PurposeService;
import com.example.cafesocial.service.iservice.StoreService;
import com.example.cafesocial.service.iservice.TypeService;
import com.example.cafesocial.utils.FormData;
import com.example.cafesocial.utils.Toastify;
import com.example.cafesocial.utils.Utils;
import com.example.cafesocial.view.activity.LoginActivity;
import com.example.cafesocial.view.activity.MainActivity;
import com.example.cafesocial.view.holder.BenefitSelectHolder;
import com.example.cafesocial.view.holder.ImageUriHolder;
import com.example.cafesocial.view.holder.PurposeSelectHolder;
import com.example.cafesocial.view.holder.TypeSelectHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStoreFragment extends BaseFragment implements BaseItemListener {
    public final static int PICK_IMAGE_REQUEST = 1;
    public final static int PICK_MENU_REQUEST = 2;

    private EditText inputName, inputPresent, inputAddress,
            inputTimeOpen, inputTimeClose,
            inputPriceStart, inputPriceEnd,
            inputWifi, inputWifiPass,
            inputPhone, inputFace, inputIns, inputWeb, inputLatitude, inputLongitude;

    private Spinner inputLocation;
    private RecyclerView reStoreTypes, reStoreBenefits, reImageAdd, reMenuAdd, reStorePurposes;
    private Button btnAdd;
    private CardView btnAddImage, btnAddMenu;

    private List<ImageUri> listImageUri = new ArrayList<>();
    private List<ImageUri> listMenuUri = new ArrayList<>();
    private List<Benefit> listBenefits = new ArrayList<>();
    private List<Type> listTypes = new ArrayList<>();
    private List<Purpose> listPurposes = new ArrayList<>();
    private List<Location> listLocations = new ArrayList<>();

    private ListAdapter<ImageUri, ImageUriHolder> listImageAdapter;
    private ListAdapter<ImageUri, ImageUriHolder> listMenuAdapter;
    private ListAdapter<Type, TypeSelectHolder> listTypeAdapter;
    private ListAdapter<Benefit, BenefitSelectHolder> listBenefitAdapter;
    private ListAdapter<Purpose, PurposeSelectHolder> listPurposeAdapter;
    private ArrayAdapter<Location> locationAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_store, container, false);
    }


    public void initView(View view) {
        inputName=  view.findViewById(R.id.inputName);
        inputPresent = view.findViewById(R.id.inputPresent);
        inputAddress= view.findViewById(R.id.inputAddress);
        inputTimeOpen = view.findViewById(R.id.inputTimeOpen);
        inputTimeClose = view.findViewById(R.id.inputTimeClose);
        inputPriceStart = view.findViewById(R.id.inputPriceStart);
        inputPriceEnd = view.findViewById(R.id.inputPriceEnd);
        inputWifi = view.findViewById(R.id.inputWifi);
        inputWifiPass = view.findViewById(R.id.inputWifiPass);
        inputPhone= view.findViewById(R.id.inputPhone);
        inputFace = view.findViewById(R.id.inputFacebook);
        inputIns = view.findViewById(R.id.inputInstagram);
        inputWeb = view.findViewById(R.id.inputWebsite);
        inputLatitude = view.findViewById(R.id.inputLatitude);
        inputLongitude = view.findViewById(R.id.inputLongitude);

        inputLocation = view.findViewById(R.id.inputLocation);

        reStoreTypes = view.findViewById(R.id.listStoreTypes);
        reStoreBenefits = view.findViewById(R.id.listStoreBenefits);
        reImageAdd = view.findViewById(R.id.listImageAdd);
        reMenuAdd = view.findViewById(R.id.listMenuAdd);
        reStorePurposes = view.findViewById(R.id.listStorePurposes);

        btnAdd = view.findViewById(R.id.btnAdd);
        btnAddImage = view.findViewById(R.id.btnAddImage);
        btnAddMenu = view.findViewById(R.id.btnAddMenu);

        listTypeAdapter = new ListAdapter<>(R.layout.item_input_select, TypeSelectHolder.class, listTypes, this);
        GridLayoutManager typeManager = new GridLayoutManager(getContext(), 2);
        reStoreTypes.setAdapter(listTypeAdapter);
        reStoreTypes.setLayoutManager(typeManager);

        listBenefitAdapter = new ListAdapter<>(R.layout.item_input_select, BenefitSelectHolder.class, listBenefits, this);
        GridLayoutManager benefitManager = new GridLayoutManager(getContext(), 2);
        reStoreBenefits.setAdapter(listBenefitAdapter);
        reStoreBenefits.setLayoutManager(benefitManager);

        listPurposeAdapter = new ListAdapter<>(R.layout.item_input_select, PurposeSelectHolder.class, listPurposes, this);
        GridLayoutManager purposeManager = new GridLayoutManager(getContext(), 2);
        reStorePurposes.setAdapter(listPurposeAdapter);
        reStorePurposes.setLayoutManager(purposeManager);

        // init recycler
        listImageAdapter = new ListAdapter<>(R.layout.item_image_uri, ImageUriHolder.class, listImageUri, this);
        GridLayoutManager imageManager = new GridLayoutManager(getContext(), 3);
        reImageAdd.setAdapter(listImageAdapter);
        reImageAdd.setLayoutManager(imageManager);

        listMenuAdapter = new ListAdapter<>(R.layout.item_image_uri, ImageUriHolder.class, listMenuUri, this);
        GridLayoutManager menuManager = new GridLayoutManager(getContext(), 3);
        reMenuAdd.setAdapter(listMenuAdapter);
        reMenuAdd.setLayoutManager(menuManager);

        locationAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listLocations);
        inputLocation.setAdapter(locationAdapter);
    }

    @Override
    public void initValidation() {
        validation.addValidation(inputName, RegexTemplate.NOT_EMPTY, "Tên cửa hàng là bắt buộc");
        validation.addValidation(inputAddress, RegexTemplate.NOT_EMPTY, "Địa chỉ là bắt buộc");
        validation.addValidation(inputTimeOpen, RegexTemplate.NOT_EMPTY, "Thời gian mở cửa là bắt buộc");
        validation.addValidation(inputTimeClose, RegexTemplate.NOT_EMPTY, "Thời gian đóng cửa là bắt buộc");
        validation.addValidation(inputPriceStart, RegexTemplate.NOT_EMPTY, "Số tiền là bắt buộc");
        validation.addValidation(inputPriceEnd, RegexTemplate.NOT_EMPTY, "Số tiền là bắt buộc");
        validation.addValidation(inputPhone, RegexTemplate.NOT_EMPTY, "Số đt là bắt buộc");
        validation.addValidation(inputLatitude, RegexTemplate.NOT_EMPTY, "Tọa độ là bắt buộc");
        validation.addValidation(inputLongitude, RegexTemplate.NOT_EMPTY, "Tọa độ là bắt buộc");
    }

    @Override
    public void onResume() {
        super.onResume();
//        if(!UserService.isAuthen()) {
//            goToActivity(LoginActivity.class);
//        }
    }

    @Override
    public void init() {
        HttpService.get(BenefitService.class).getList().enqueue(new Callback<ResponseData<List<Benefit>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Benefit>>> call, Response<ResponseData<List<Benefit>>> response) {
                listBenefits.clear();
                listBenefits.addAll(response.body().getData());
                listBenefitAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseData<List<Benefit>>> call, Throwable t) {
                System.out.println("call fail");
                System.out.println(t);
            }
        });

        HttpService.get(TypeService.class).getList().enqueue(new Callback<ResponseData<List<Type>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Type>>> call, Response<ResponseData<List<Type>>> response) {
                listTypes.clear();
                listTypes.addAll(response.body().getData());
                listTypeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseData<List<Type>>> call, Throwable t) {
                System.out.println("call fail");
                System.out.println(t);
            }
        });

        HttpService.get(PurposeService.class).getList().enqueue(new Callback<ResponseData<List<Purpose>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Purpose>>> call, Response<ResponseData<List<Purpose>>> response) {
                listPurposes.clear();
                listPurposes.addAll(response.body().getData());
                listPurposeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseData<List<Purpose>>> call, Throwable t) {
                System.out.println("call fail");
                System.out.println(t);
            }
        });

        HttpService.get(LocationService.class).getList().enqueue(new Callback<ResponseData<List<Location>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Location>>> call, Response<ResponseData<List<Location>>> response) {
                listLocations.clear();
                listLocations.addAll(response.body().getData());
                locationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseData<List<Location>>> call, Throwable t) {

            }
        });
    }

    @Override
    public void initListener() {
        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermionAndPickImage(PICK_IMAGE_REQUEST);
            }
        });

        btnAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermionAndPickImage(PICK_MENU_REQUEST);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validation.validate()) {
                    btnAdd.setEnabled(false);
                    String name = inputName.getText().toString().trim();
                    String present = inputPresent.getText().toString().trim();
                    String address = inputAddress.getText().toString().trim();
                    String timeOpen = inputTimeOpen.getText().toString().trim();
                    String timeClose = inputTimeClose.getText().toString().trim();
                    Float priceStart = null;
                    Float priceEnd = null;
                    try {
                        priceStart = Float.parseFloat(inputPriceStart.getText().toString().trim());
                        priceEnd = Float.parseFloat(inputPriceEnd.getText().toString().trim());
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Số tiền nhập chưa đúng định dạng", Toast.LENGTH_SHORT).show();
                    }
                    Double latitude = null;
                    Double longitude = null;
                    try {
                        latitude = Double.parseDouble(inputPriceStart.getText().toString().trim());
                        longitude = Double.parseDouble(inputPriceEnd.getText().toString().trim());
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Tọa nhập chưa đúng định dạng", Toast.LENGTH_SHORT).show();
                    }
                    String wifi = inputWifi.getText().toString().trim();
                    String wifiPass = inputWifiPass.getText().toString().trim();
                    String phone = inputPhone.getText().toString().trim();
                    String facebook = inputFace.getText().toString().trim();
                    String ins = inputIns.getText().toString().trim();
                    String web = inputWeb.getText().toString().trim();

                    Location location = (Location) inputLocation.getSelectedItem();

                    List<Long> listIdType = new ArrayList<>();
                    listTypes.forEach(type -> {
                        if(type.getIsSelect()) {
                            listIdType.add(type.getId());
                        }
                    });
                    List<Long> listIdBenefit = new ArrayList<>();
                    listBenefits.forEach(benefit -> {
                        if(benefit.getIsSelect()) {
                            listIdBenefit.add(benefit.getId());
                        }
                    });
                    List<Long> listIdPurpose = new ArrayList<>();
                    listPurposes.forEach(purpose -> {
                        if(purpose.getIsSelect()) {
                            listIdPurpose.add(purpose.getId());
                        }
                    });
                    System.out.println("list benefit");
                    System.out.println(listIdBenefit.size());

                    HttpService.get(StoreService.class).createStore(
                            UserService.getToken(),
                            FormData.getStringPart(name),
                            FormData.getStringPart(address),
                            FormData.getStringPart(present),
                            FormData.getStringPart(latitude+""),
                            FormData.getStringPart(longitude+""),
                            FormData.getStringPart(timeOpen),
                            FormData.getStringPart(timeClose),
                            FormData.getStringPart(String.valueOf(priceStart)),
                            FormData.getStringPart(String.valueOf(priceEnd)),
                            FormData.getStringPart(wifi),
                            FormData.getStringPart(wifiPass),
                            FormData.getStringPart(phone),
                            FormData.getStringPart(facebook),
                            FormData.getStringPart(ins),
                            FormData.getStringPart(web),
                            FormData.getStringPart(String.valueOf(location.getId())),
                            FormData.getMultipartFromArrayImage("images[]",listImageUri),
                            FormData.getMultipartFromArrayImage("menus[]", listMenuUri),
                            listIdBenefit,
                            listIdPurpose,
                            listIdType
                    ).enqueue(new Callback<ResponseData<Boolean>>() {
                        @Override
                        public void onResponse(Call<ResponseData<Boolean>> call, Response<ResponseData<Boolean>> response) {
                            btnAdd.setEnabled(true);
                            addFragment(R.id.drawerLayout, new HomeFrament(), "homeFrag", "homeFrag", null);
                        }

                        @Override
                        public void onFailure(Call<ResponseData<Boolean>> call, Throwable t) {
                            System.out.println("create store fail");
                            System.out.println(t);
                            Toastify.toastError(getContext(), null);
                        }
                    });
                }
            }
        });
    }

    private void requestPermionAndPickImage(int requestType) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            pickImage(requestType);
            return;
        }
        // Các bạn nhớ request permison cho các máy M trở lên nhé, k là crash ngay đấy.
        int result = ContextCompat.checkSelfPermission(getContext(),
                permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            pickImage(requestType);
        } else {
            requestPermissions(new String[]{
                    permission.READ_EXTERNAL_STORAGE}, requestType);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        if (requestCode != PICK_IMAGE_REQUEST && requestCode != PICK_MENU_REQUEST) return;
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage(requestCode);
        } else {
            Toast.makeText(getContext(), "Permission denine",
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

        } else if(requestCode == PICK_MENU_REQUEST && resultCode == Activity.RESULT_OK && data!=null &&data.getClipData() != null) {
            listMenuUri.addAll(getListImageUriFromDataIntent(data, PICK_MENU_REQUEST));
            listMenuAdapter.notifyDataSetChanged();
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
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
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

    @Override
    public void onItemClick(int layoutId, View view, Model item , int position) {
        if(item instanceof ImageUri) {
            switch (((ImageUri) item).getRequestCode()) {
                case PICK_IMAGE_REQUEST:
                    listImageUri.remove(item);
                    listImageAdapter.notifyDataSetChanged();
                    break;
                case PICK_MENU_REQUEST:
                    listMenuUri.remove(item);
                    listMenuAdapter.notifyDataSetChanged();
                    break;
            }
        } else if(item instanceof Benefit){
            listBenefits.get(position).setIsSelect(!listBenefits.get(position).getIsSelect());
            System.out.println("check benefit");
        } else if(item instanceof Type) {
            listTypes.get(position).setIsSelect(!listTypes.get(position).getIsSelect());
            System.out.println("check type");
        } else if(item instanceof Purpose) {
            listPurposes.get(position).setIsSelect(!listPurposes.get(position).getIsSelect());
            System.out.println("check purr");
        }
    }
}
