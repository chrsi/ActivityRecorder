package at.csiber.activityrecorder;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity   extends Activity
                            implements CheckPasswordFragment.CheckPasswordListener{

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setButtons();


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

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        stopRecording();
    }
}

