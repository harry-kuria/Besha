package ultramodern.activity.besha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


@SuppressWarnings("ALL")
public class Add_Card extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private static final String TAG="TRACK";
    private static int REQUEST_CODE_CAMERA_PERMISSION = 101;

    private IntentFilter intentFilter;

    public static final String BroadCastStringForAction = "CheckInternet";

    CameraBridgeViewBase javaCameraView;
    Mat mRGBA,mRGBAT;
    SurfaceView mSurfaceView;
    SurfaceHolder mSurfaceHolder;
    BottomNavigationView bottomnav;
    RelativeLayout layout;

    TextView noInternet;
    BaseLoaderCallback baseLoaderCallback ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__card);

        intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCastStringForAction);

        Intent serviceIntent = new Intent(this,MyService.class);
        startService(serviceIntent);

        permission();

        layout = findViewById(R.id.addCardLayout);
        bottomnav = findViewById(R.id.bottomnav);
        noInternet = findViewById(R.id.textView19);
        javaCameraView = findViewById(R.id.my_camera);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);
        javaCameraView.setCameraPermissionGranted();
        //setting surfaceView translucent
        baseLoaderCallback= new BaseLoaderCallback(this) {
            @Override
            public void onManagerConnected(int status) {
                switch (status)
                {
                    case BaseLoaderCallback.SUCCESS:
                    {
                        javaCameraView.enableView();
                        break;
                    }
                    default:
                    {
                        super.onManagerConnected(status);
                    }

                }

            }
        };

        if (isOnline(getApplicationContext())){
            set_Visibility_ON();
        }
        else {
            set_Visibility_OFF();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        registerReceiver(myReceiver,intentFilter);
    }


    public BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BroadCastStringForAction)){
                if (intent.getStringExtra("online_status").equals("true")){
                    set_Visibility_ON();
                }
                else {
                    set_Visibility_OFF();
                }
            }
        }
    };

    public boolean isOnline(Context c){
        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo !=null && networkInfo.isConnectedOrConnecting()){
            return true;
        }
        else {
            return false;
        }
    }

    public void set_Visibility_ON(){
        noInternet.setVisibility(View.GONE);
        javaCameraView.setVisibility(View.VISIBLE);
        bottomnav.setVisibility(View.VISIBLE);
        layout.setBackgroundColor(Color.WHITE);
        //binding.notConnected.setVisibility(View.GONE);
        //binding.submit.setVisibility(View.VISIBLE);
        //binding.parent.setBackgroundColor(Color.WHITE);

    }

    public void set_Visibility_OFF(){
        noInternet.setVisibility(View.VISIBLE);
        javaCameraView.setVisibility(View.GONE);
        bottomnav.setVisibility(View.GONE);
        layout.setBackgroundColor(Color.RED);
        //binding.notConnected.setVisibility(View.VISIBLE);
        //binding.submit.setVisibility(View.GONE);
        //binding.parent.setBackgroundColor(Color.RED);

    }


    private boolean permission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.CAMERA},
                    50);
        }else{
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (javaCameraView !=null){
            javaCameraView.disableView();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (javaCameraView !=null){
            javaCameraView.disableView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()){
            Log.d(TAG,"OPENCV IS CONFIGURED SUCCESSFULLY");
            baseLoaderCallback.onManagerConnected(BaseLoaderCallback.SUCCESS);

        }
        else {
            Log.d(TAG,"OPENCV IS HAVING ISSUES");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3,this,baseLoaderCallback);
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRGBA = new Mat(width,height, CvType.CV_8UC4);
    }

    @Override
    public void onCameraViewStopped() {
        mRGBA.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRGBA=inputFrame.rgba();
        mRGBAT = mRGBA.t();
        Core.flip(mRGBA.t(),mRGBAT,1);
        Imgproc.resize(mRGBAT,mRGBAT,mRGBA.size());
        return mRGBAT;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}