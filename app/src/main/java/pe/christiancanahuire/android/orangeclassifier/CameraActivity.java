package pe.christiancanahuire.android.orangeclassifier;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.core.Mat;

public class CameraActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener {
    private static final String TAG = CameraActivity.class.getSimpleName();

    public static void start(Context context) {
        Intent starter = new Intent(context, CameraActivity.class);
        context.startActivity(starter);
    }

    private JavaCameraView mJavaCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJavaCameraView = findViewById(R.id.jcv_camera);
        mJavaCameraView.setCvCameraViewListener(this);

    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        Log.i(TAG, String.format("onCameraViewStarted width = %d, height = %d", width, height));
    }

    @Override
    public void onCameraViewStopped() {
        Log.i(TAG, "onCameraViewStopped");
    }

    @Override
    public Mat onCameraFrame(Mat inputFrame) {
        Log.d(TAG, "onCameraFrame");
        return null;
    }
}
