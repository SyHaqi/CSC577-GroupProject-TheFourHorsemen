package com.haqi.csc577groupproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.haqi.csc577groupproject.R;
import com.haqi.csc577groupproject.model.User;
import com.haqi.csc577groupproject.sharedpref.SharedPrefManager;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText edtUsername, edtEmail, edtPhone, edtRole;
    private ImageView imgAvatar;
    private SharedPrefManager spm;

    // Gallery picker launcher
    private final ActivityResultLauncher<Intent> galleryLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK
                                && result.getData() != null) {
                            Uri imageUri = result.getData().getData();
                            try {
                                Bitmap raw = MediaStore.Images.Media
                                        .getBitmap(getContentResolver(), imageUri);
                                // Crop to circle then display
                                imgAvatar.setImageBitmap(getCircularBitmap(raw));
                            } catch (Exception e) {
                                Log.e("SocialGoodApp:", e.toString());
                                Toast.makeText(this, "Could not load image", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_updateprofile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.updateProfile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // References
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail    = findViewById(R.id.edtEmail);
        edtPhone    = findViewById(R.id.edtPhone);
        edtRole     = findViewById(R.id.edtPassword);
        imgAvatar   = findViewById(R.id.imgAvatar);

        // Load current user from SharedPrefs — pre-fill fields
        spm = new SharedPrefManager(getApplicationContext());
        User user = spm.getUser();
        edtUsername.setText(user.getUsername());
        edtEmail.setText(user.getEmail());
        edtPhone.setText(user.getPhone());
        edtRole.setText(user.getRole());

        // Back button — go back to MainActivity
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // "change image" overlay — open gallery picker
        LinearLayout overlayChangeImage = findViewById(R.id.overlayChangeImage);
        overlayChangeImage.setOnClickListener(v -> openGallery());
    }

    /**
     * Opens the device gallery so user can pick a photo.
     */
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    /**
     * Crops any Bitmap into a perfect circle.
     */
    private Bitmap getCircularBitmap(Bitmap src) {
        int size = Math.min(src.getWidth(), src.getHeight());
        int x = (src.getWidth()  - size) / 2;
        int y = (src.getHeight() - size) / 2;
        Bitmap squared = Bitmap.createBitmap(src, x, y, size, size);

        Bitmap output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint   = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(squared, 0, 0, paint);
        return output;
    }

    /**
     * Save button handler — your friend will wire this to the API later.
     */
    public void updateClicked(View view) {
        String email = edtEmail.getText().toString();
        String phone = edtPhone.getText().toString();

        if (email == null || email.trim().isEmpty()) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone == null || phone.trim().isEmpty()) {
            Toast.makeText(this, "Phone is required", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: your friend will call userService.updateProfile() here
        Toast.makeText(this, "Profile saved (UI only for now)", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("email", edtEmail.getText().toString());
        outState.putString("phone", edtPhone.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        edtEmail.setText(savedInstanceState.getString("email"));
        edtPhone.setText(savedInstanceState.getString("phone"));
    }
}