package com.example.medical.affichage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.medical.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class OCRActivity extends AppCompatActivity {

    private SurfaceView surfaceView;

    private CameraSource cameraSource;

    private TextToSpeech textToSpeech;
    private String stringResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_c_r);
        textToSpeech = new TextToSpeech(this, status -> {
        });
        setupCamera();
        findViewById(R.id.getText).setOnClickListener(v -> {
            if (stringResult== null){
                stringResult="";
            }
            Intent i = new Intent();
            i.putExtra("text",  stringResult);
            setResult(RESULT_OK, i);
            finish();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 8) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupCamera();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 8);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraSource!=null)
            cameraSource.release();
    }

    private void textRecognizer() {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                .setRequestedPreviewSize(1280, 1024)
                .build();

        surfaceView = findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(OCRActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start(surfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });


            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {
                }

                @Override
                public void receiveDetections(@NonNull Detector.Detections<TextBlock> detections) {

                    SparseArray<TextBlock> sparseArray = detections.getDetectedItems();
                    StringBuilder stringBuilder = new StringBuilder();

                    for (int i = 0; i<sparseArray.size(); ++i){
                        TextBlock textBlock = sparseArray.valueAt(i);
                        if (textBlock != null){
                            stringBuilder.append(textBlock.getValue()).append(" ");
                        }
                    }

                    final String stringText = stringBuilder.toString();

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(() -> {
                        stringResult = stringText;
                        if (stringResult.length() > 3)
                            resultObtained();
                    });
                }
            });
        }

        private void resultObtained(){
            TextView textView = findViewById(R.id.textView);
            textView.setText(stringResult);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textToSpeech.speak(stringResult, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        }

        public void setupCamera(){
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 8);

            }else {
                textRecognizer();
            }
        }
}