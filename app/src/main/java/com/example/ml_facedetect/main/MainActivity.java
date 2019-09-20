package com.example.ml_facedetect.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ml_facedetect.R;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    public static final int CAMERA_REQUEST = 111;
    private static final int PERMISSION_REQUEST = 100;

    private TextView empText;
    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);

        empText = findViewById(R.id.emp_name);
        findViewById(R.id.open_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empText.setText("");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
                    } else {
                        presenter.openCamera(MainActivity.this);
                    }
                }
            }
        });

        presenter.loadRemoteModel();
        presenter.loadLocalModel();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST) {
            presenter.openCamera(MainActivity.this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && data != null && data.getExtras() != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            presenter.labelImages(photo);
        }
    }

    public void setEmp(String empName) {
        empText.setText("Employee Name: " + empName);
    }

    public void errorToast(String errorMsg) {
        Toast.makeText(MainActivity.this, getResources().getString(R.string.error_toast), Toast.LENGTH_SHORT).show();
    }
}