package pe.christiancanahuire.android.orangeclassifier;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.io.InputStream;

public class BitmapTestActivity extends OpenCvActivity {

    private static final String TAG = BitmapTestActivity.class.getSimpleName();

    public static void start(Context context) {
        Intent starter = new Intent(context, BitmapTestActivity.class);
        context.startActivity(starter);
    }

    private ImageView mSourceImageView;
    private ImageView mProcessedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_test);

        mSourceImageView = findViewById(R.id.iv_source_bitmap);
        mProcessedImageView = findViewById(R.id.iv_processed_bitmap);
    }

    @Override
    protected void onOpenCvManagerConnected() {
        Bitmap sourceBitmap = loadBitmapFromAsset("images/orange2.jpg");

        if (sourceBitmap != null) {
            mSourceImageView.setImageBitmap(sourceBitmap);

            Mat sourceMat = new Mat(sourceBitmap.getWidth(), sourceBitmap.getHeight(), CvType.CV_8U, new Scalar(4));
            Utils.bitmapToMat(sourceBitmap, sourceMat);
            Mat processedMat = processImage(sourceMat);
            Bitmap processedBitmap = Bitmap.createBitmap(processedMat.cols(), processedMat.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(processedMat, processedBitmap);

            mProcessedImageView.setImageBitmap(processedBitmap);
        } else {
            Log.e(TAG, "Could not open asset bitmap.");
        }
    }

    private Bitmap loadBitmapFromAsset(String assetPath) {
        AssetManager assetManager = getAssets();
        InputStream is;

        try {
            is = assetManager.open(assetPath);
            return BitmapFactory.decodeStream(is);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    private Mat processImage(Mat inputRgba) {
        Mat gray = new Mat(inputRgba.size(), CvType.CV_8U);
        Imgproc.cvtColor(inputRgba, gray, Imgproc.COLOR_RGB2GRAY, 4);
        Mat blurred = gray.clone();
        Imgproc.medianBlur(gray, blurred, 3);
        Imgproc.GaussianBlur(blurred, blurred, new Size(3, 3), 0, 0);

        Mat edges = new Mat(inputRgba.size(), CvType.CV_8U);
        Imgproc.Canny(blurred, edges, 80, 100);
        return blurred;
    }
}
