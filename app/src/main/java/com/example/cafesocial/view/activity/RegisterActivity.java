package com.example.cafesocial.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.cafesocial.R;
import com.example.cafesocial.core.activity.BaseActivity;
import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.dto.request.RegisterRequestDto;
import com.example.cafesocial.service.HttpService;
import com.example.cafesocial.service.UserService;
import com.example.cafesocial.service.iservice.AuthService;
import com.example.cafesocial.utils.Toastify;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {
    private ImageView btnClose;
    private EditText email, password,name;
    private TextView btnForgotPassword, btnLogin;
    private Button btnRegister;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    public void initView() {
        btnClose = findViewById(R.id.btnClose);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnRegister = findViewById(R.id.btnRegister);
        btnForgotPassword = findViewById(R.id.btnForgotPass);
        btnLogin = findViewById(R.id.btnLogin);
        name = findViewById(R.id.name);
    }

    @Override
    public void initValidation() {
        validation.addValidation(email, android.util.Patterns.EMAIL_ADDRESS, "Định dạng email chưa đúng");
        validation.addValidation(password, RegexTemplate.NOT_EMPTY, "Mật khẩu không được để trống");
        validation.addValidation(name, RegexTemplate.NOT_EMPTY, "Tên không được để trống");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(UserService.isAuthen()) {
            goToActivity(MainActivity.class);
            return;
        }
    }

    public void initEvent() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(MainActivity.class);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(LoginActivity.class);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                String nameText = name.getText().toString();

                if(validation.validate()) {
                    // call api register
                    HttpService.get(AuthService.class).register(new RegisterRequestDto(emailText, passwordText, nameText)).enqueue(new Callback<ResponseData<Boolean>>() {
                        @Override
                        public void onResponse(Call<ResponseData<Boolean>> call, Response<ResponseData<Boolean>> response) {
                            if(response.code() ==200) {
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.putExtra("email", emailText);
                                intent.putExtra("password", passwordText);
                                goToActivity(LoginActivity.class, intent);
                            }else if(response.code()==400) {
                                Toastify.toastError(getApplicationContext(), "Tài khoản đã tồn tại");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseData<Boolean>> call, Throwable t) {
                            Toastify.toastError(getApplicationContext(), null);
                        }
                    });
                }


            }
        });
    }
}