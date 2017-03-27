package at.csiber.activityrecorder;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import at.csiber.activityrecorder.recorders.RecordNotificationHandler;
import at.csiber.activityrecorder.recorders.RecorderDirectory;
import at.csiber.activityrecorder.recorders.RecorderInterface;
import at.csiber.activityrecorder.services.AccelerationLogger;
import at.csiber.activityrecorder.services.LocationLogger;
import at.csiber.activityrecorder.services.RecordingService;

public class MainActivity   extends Activity
        implements CheckPasswordFragment.CheckPasswordListener{

    private static final String TAG = "MainActivity";
    public static final int MY_LOCATION_PERMISSION_REQUEST = 1093424845;
    public static final int WRITE_EXT_REQUEST = 394859781;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setButtons();

        RecorderDirectory recorderDirectory = RecorderDirectory.getInstance(this);
        addLocationStatusUpdates(recorderDirectory);
        addAccelerationStatusUpdates(recorderDirectory);

        //TODO: check for location permission
        if (    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION }, MY_LOCATION_PERMISSION_REQUEST);
            return;
        }
        if (    ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXT_REQUEST);
            return;
        }
    }

    private void addLocationStatusUpdates(RecorderDirectory recorderDirectory){
        RecorderInterface<Location> locationRecorder = recorderDirectory.getRecorder(RecorderDirectory.LOCATION_RECORDER);
        locationRecorder.addNotificationHandler(new RecordNotificationHandler<Location>() {
            @Override
            public void HandleNotification(Location value) {
                TextView statusView = (TextView) findViewById(R.id.txtStatus);
                statusView.setText("Location from " + value.getProvider() + ": " + value.getLatitude() + ", " + value.getLongitude());
            }
        });
    }

    private void addAccelerationStatusUpdates(RecorderDirectory recorderDirectory){
        RecorderInterface<Acceleration> accelerationRecorder = recorderDirectory.getRecorder(RecorderDirectory.ACCELERATION_RECORDER);
        accelerationRecorder.addNotificationHandler(new RecordNotificationHandler<Acceleration>() {
            @Override
            public void HandleNotification(Acceleration value) {
                TextView statusView = (TextView) findViewById(R.id.txtAccStatus);
                statusView.setText("Acceleration: \n" + value.getX() + "\n" + value.getY() + "\n" + value.getZ());
            }
        });
    }

    private void setButtons(){
        Button startButton = (Button) findViewById(R.id.btnStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
            }
        });

        Button stopButton = (Button) findViewById(R.id.btnStop);
        stopButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CheckPasswordFragment frag = new CheckPasswordFragment();
                frag.show(getFragmentManager(), "CheckPassword");
                return true;
            }
        });
    }

    //<editor-fold desc="Request Permission">
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            default: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    throw new RuntimeException("Application makes no sense without required permissions.");
                }
                return;
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Handle Services">
    Map<String, Intent> intentMap = new HashMap<>();

    private void startServiceIntent(Class<?> service){
        if (intentMap.get(service.getName()) == null){
            Intent intent = new Intent(this, service);
            startService(intent);
            intentMap.put(service.getName(), intent);
        }

    }

    private void endServiceIntent(Class<?> service){
        if (intentMap.containsKey(service.getName())){
            Intent intent = intentMap.get(service.getName());
            stopService(intent);
            intentMap.remove(service.getName());
        }
    }

    public void startRecording(){
        startServiceIntent(LocationLogger.class);
        startServiceIntent(AccelerationLogger.class);
        startServiceIntent(RecordingService.class);
    }

    public void stopRecording(){
        endServiceIntent(LocationLogger.class);
        endServiceIntent(AccelerationLogger.class);
        endServiceIntent(RecordingService.class);
    }
    //</editor-fold>

    //<editor-fold desc="CheckPasswordListener">
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        stopRecording();
    }
    //</editor-fold>
}

