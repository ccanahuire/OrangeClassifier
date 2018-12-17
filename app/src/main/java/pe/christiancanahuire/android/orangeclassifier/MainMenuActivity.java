package pe.christiancanahuire.android.orangeclassifier;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CAMERA = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        findViewById(R.id.btn_classifier).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickClassifier();
            }
        });

        findViewById(R.id.btn_bitmaptest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToBitmapTest();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    navigateToCameraClassifier();
                }
                break;
        }
    }

    private void onClickClassifier() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        } else {
            navigateToCameraClassifier();
        }
    }

    private void navigateToCameraClassifier() {
        CameraActivity.start(this);
    }

    private void navigateToBitmapTest() {
        BitmapTestActivity.start(this);
    }
}
