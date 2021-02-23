package com.example.medical.affichage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;


import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CameraScannerActivity extends AppCompatActivity {
    private ZXingScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hna tvérifi ida 3ndo permission te3 caméra wela la
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 8);

        }else {
            setupCamera();
        }
    }

    private void setupCamera(){
        mScannerView = new ZXingScannerView(this); // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.startCamera();
        mScannerView.setResultHandler(result -> {
            //hna c bn aw 9ra code w raih yrj3o l page li 9blha
            mScannerView.stopCamera();
            Intent i = new Intent();
            i.putExtra("code",  result.getText());
            setResult(RESULT_OK, i);
            finish();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 8) {
            //hna tvérifi ida 3ndo permission te3 caméra wela la
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupCamera();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 8);
            }
        }
    }
}