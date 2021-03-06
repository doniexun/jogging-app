package io.github.sodaoud.joggingapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.github.sodaoud.joggingapp.util.Util;

public class WelcomeActivity extends AppCompatActivity {


    private static final int GET_ACCOUNTS_REQUEST_CODE = 32112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.GET_ACCOUNTS}, GET_ACCOUNTS_REQUEST_CODE);
            return;
        }
        if (Util.getAccount(this) != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GET_ACCOUNTS_REQUEST_CODE) {
            if (Util.getAccount(this) != null) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        }
    }

    public void onLoginBtn(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, 123);
    }

    public void onSignUpBtn(View v) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivityForResult(intent, 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.GET_ACCOUNTS}, GET_ACCOUNTS_REQUEST_CODE);
                return;
            }
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
