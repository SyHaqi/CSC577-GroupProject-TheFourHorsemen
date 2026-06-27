package com.haqi.csc577groupproject;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.haqi.csc577groupproject.R;
import com.haqi.csc577groupproject.model.FailLogin;
import com.haqi.csc577groupproject.model.User;
import com.haqi.csc577groupproject.remote.ApiUtils;
import com.haqi.csc577groupproject.remote.UserService;
import com.haqi.csc577groupproject.sharedpref.SharedPrefManager;
import com.google.gson.Gson;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

        private EditText edtUsername;
        private EditText edtPassword;
        private TextView tvRegister;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                EdgeToEdge.enable(this);
                setContentView(R.layout.activity_login);
                ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
                        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                        return insets;
                });

                edtUsername = findViewById(R.id.edtUsername);
                edtPassword = findViewById(R.id.edtPassword);
                tvRegister  = findViewById(R.id.tvRegister);

                // Dynamic greeting
                TextView tvGreeting = findViewById(R.id.tvGreeting);
                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                if (hour >= 5 && hour < 12) {
                        tvGreeting.setText("Good morning,");
                } else if (hour >= 12 && hour < 18) {
                        tvGreeting.setText("Good afternoon,");
                } else {
                        tvGreeting.setText("Good evening,");
                }

                // Notification bell
                ImageButton btnNotification = findViewById(R.id.btnNotification);
                btnNotification.setOnClickListener(v -> {
                        String[] messages = {
                                "🔧 Maintenance scheduled on 6/8/2026 at 12:00 AM",
                                "🚗 New ride available: UiTM → Melaka Sentral",
                                "📢 Welcome to RideShare UiTM!"
                        };
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Notifications")
                                .setItems(messages, null)
                                .setPositiveButton("Close", null)
                                .show();
                });

                // Register link
                tvRegister.setOnClickListener(v -> {
                        startActivity(new Intent(getApplicationContext(), CreateProfileActivity.class));
                });
        }

        public void loginClicked(View view) {

                // get username and password entered by user
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                // validate form, make sure it is not empty
                if (validateLogin(username, password)) {
                        // if not empty, login using REST API
                        doLogin(username, password);
                }

        }

        /**
         * Call REST API to login
         *
         * @param username username
         * @param password password
         */

        private void doLogin(String username, String password) {
                UserService userService = ApiUtils.getUserService();
                Call<User> call = userService.login(username, password);
                call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call call, Response response) {
                                if (response.isSuccessful()) {
                                        User user = (User) response.body();
                                        if (user != null && user.getToken() != null) {
                                                displayToast("Login successful");
                                                SharedPrefManager spm = new SharedPrefManager(getApplicationContext());
                                                spm.storeUser(user);
                                                finish();
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        } else {
                                                displayToast("Login error");
                                        }
                                } else {
                                        try {
                                                String errorResp = response.errorBody().string();
                                                FailLogin e = new Gson().fromJson(errorResp, FailLogin.class);
                                                displayToast(e.getError().getMessage());
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

        private boolean validateLogin(String username, String password) {
                if (username == null || username.trim().isEmpty()) {
                        displayToast("Username is required");
                        return false;
                }
                if (password == null || password.trim().isEmpty()) {
                        displayToast("Password is required");
                        return false;
                }
                return true;
        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
                super.onSaveInstanceState(outState);
                outState.putString("username", edtUsername.getText().toString());
                outState.putString("password", edtPassword.getText().toString());
        }

        @Override
        protected void onRestoreInstanceState(Bundle savedInstanceState) {
                super.onRestoreInstanceState(savedInstanceState);
                edtUsername.setText(savedInstanceState.getString("username"));
                edtPassword.setText(savedInstanceState.getString("password"));
        }

        public void displayToast(String message) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
}