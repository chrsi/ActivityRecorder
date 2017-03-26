package at.csiber.activityrecorder;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import at.csiber.activityrecorder.recorders.RecordNotificationHandler;
import at.csiber.activityrecorder.recorders.RecorderDirectory;
import at.csiber.activityrecorder.recorders.RecorderInterface;
import at.csiber.activityrecorder.services.RecordingService;

public class MainActivity   extends Activity
        implements CheckPasswordFragment.CheckPasswordListener{

    private static final String TAG = "MainActivity";
    public static final int MY_LOCATION_PERMISSION_REQUEST = 1093424845;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setButtons();

        RecorderDirectory recorderDirectory = RecorderDirectory.getInstance(this);
        addLocationStatusUpdates(recorderDirectory);

        //TODO: check for location permission
        if (    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION }, MY_LOCATION_PERMISSION_REQUEST);
            return;
        }
    }

    private void addLocationStatusUpdates(RecorderDirectory recorderDirectory){
        RecorderInterface<Location> locationRecorder = recorderDirectory.getRecorder(RecorderDirectory.LOCATION_RECORDER);
        locationRecorder.addNotificationHandler(new RecordNotificationHandler() {
            @Override
            public void HandleNotification(String value) {
                //TODO: change status view
                TextView statusView = (TextView) findViewById(R.id.txtStatus);
                statusView.setText(value);
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
            case MY_LOCATION_PERMISSION_REQUEST: {
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

    //<editor-fold desc="Recording Service">
    Intent recordingIntent = null;

    public void startRecording(){
        if (recordingIntent == null) {
            recordingIntent = new Intent(this, RecordingService.class);
            startService(recordingIntent);
        }
    }

    public void stopRecording(){
        if (recordingIntent != null){
            stopService(recordingIntent);
            recordingIntent = null;
        }
    }
    //</editor-fold>

    //<editor-fold desc="CheckPasswordListener">
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        stopRecording();
    }
    //</editor-fold>
}

