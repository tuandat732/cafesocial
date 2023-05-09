package com.example.cafesocial.view.activity;

import android.content.Intent;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.cafesocial.R;
import com.example.cafesocial.core.activity.BaseActivity;
import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.dto.request.LoginRequestDto;
import com.example.cafesocial.dto.response.AuthLoginResponseDto;
import com.example.cafesocial.model.User;
import com.example.cafesocial.service.AsyncStorage;
import com.example.cafesocial.service.HttpService;
import com.example.cafesocial.service.UserService;
import com.example.cafesocial.service.iservice.AuthService;
import com.example.cafesocial.utils.Toastify;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    private EditText email;
    private EditText password;
    private TextView btnForgotPassword, btnRegister;
    private Button btnLogin, btnClose;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    public void initView() {
        btnClose = findViewById(R.id.btnClose);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnRegister = findViewById(R.id.btnRegister);
        btnForgotPassword = findViewById(R.id.btnForgotPass);
        btnLogin = findViewById(R.id.btnLogin);
        setInputRegisterForLogin();
    }

    public void setInputRegisterForLogin() {
        Intent intent = getIntent();
        email.setText(intent.getStringExtra("email"));
        password.setText(intent.getStringExtra("password"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(UserService.isAuthen()) {
            goToActivity(MainActivity.class);
            return;
        }
        setInputRegisterForLogin();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        setInputRegisterForLogin();
//    }

    @Override
    public void initValidation() {
        validation.addValidation(email, android.util.Patterns.EMAIL_ADDRESS, "Định dạng email chưa đúng");
        validation.addValidation(password, RegexTemplate.NOT_EMPTY, "Mật khẩu không được để trống");
    }

    public void initEvent() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(MainActivity.class);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(RegisterActivity.class);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                // call api login
                if(validation.validate()) {
                    // call api login
                    HttpService.get(AuthService.class).login(new LoginRequestDto(emailText, passwordText)).enqueue(new Callback<ResponseData<AuthLoginResponseDto>>() {
                        @Override
                        public void onResponse(Call<ResponseData<AuthLoginResponseDto>> call, Response<ResponseData<AuthLoginResponseDto>> response) {
                            if(response.code() == 200) {
                                UserService.setAuth(response.body().getData().getUser(),"Bearer "+ response.body().getData().getToken());
                                AsyncStorage.build(getApplicationContext()).set("token", UserService.getToken());
                                System.out.println(UserService.getUser());
                                System.out.println(UserService.getToken());
                                System.out.println("successs");
                                finish();
                            } else if (response.code() == 401){
                                Toastify.toastError(getApplicationContext(), "Tài khoản hoặc mật khẩu không đúng");
                            } else {
                                Toastify.toastError(getApplicationContext(), null);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseData<AuthLoginResponseDto>> call, Throwable t) {
                            // TODO: alert error
                            System.out.println("login loi");
                            System.out.println(t);
                            Toastify.toastError(getApplicationContext(), null);
                        }
                    });

                }
            }
        });
    }
}