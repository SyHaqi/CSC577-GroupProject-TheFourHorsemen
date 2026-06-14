package com.haqi.csc577groupproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.haqi.csc577groupproject.R;
import com.haqi.csc577groupproject.model.User;
import com.haqi.csc577groupproject.remote.ApiUtils;
import com.haqi.csc577groupproject.remote.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateProfileActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtPhone;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_createprofile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.createProfile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtUsername = findViewById(R.id.edtUsername);
        edtEmail    = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtPhone    = findViewById(R.id.edtPhone);
        tvLogin     = findViewById(R.id.tvLogin);

        tvLogin.setOnClickListener(v -> finish());
    }

    public void registerClicked(View view) {
        String username = edtUsername.getText().toString();
        String email    = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String phone    = edtPhone.getText().toString();

        if (validateForm(username, email, password, phone)) {
            doRegister(username, email, password, phone);
        }
    }

    private void doRegister(String username, String email, String password, String phone) {
        UserService userService = ApiUtils.getUserService();
        Call<User> call = userService.register(username, email, password, phone);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    User user = (User) response.body();
                    if (user != null) {
                        displayToast("Registration successful! Please login.");
                        finish();
                    } else {
                        displayToast("Registration error");
                    }
                } else {
                    try {
                        String errorResp = response.errorBody().string();
                        displayToast("Registration failed: " + errorResp);
                    } catch (Exception e) {
                        Log.e("SocialGoodApp:", e.toString());
                        displayToast("Error");
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                displayToast("Error connecting to server.");
                Log.e("SocialGoodApp:", t.toString());
            }
        });
    }

    private boolean validateForm(String username, String email, String password, String phone) {
        if (username == null || username.trim().isEmpty()) { displayToast("Username is required"); return false; }
        if (email == null    || email.trim().isEmpty())    { displayToast("Email is required");    return false; }
        if (password == null || password.trim().isEmpty()) { displayToast("Password is required"); return false; }
        if (phone == null    || phone.trim().isEmpty())    { displayToast("Phone is required");    return false; }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("username", edtUsername.getText().toString());
        outState.putString("email",    edtEmail.getText().toString());
        outState.putString("password", edtPassword.getText().toString());
        outState.putString("phone",    edtPhone.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        edtUsername.setText(savedInstanceState.getString("username"));
        edtEmail.setText(savedInstanceState.getString("email"));
        edtPassword.setText(savedInstanceState.getString("password"));
        edtPhone.setText(savedInstanceState.getString("phone"));
    }

    public void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}