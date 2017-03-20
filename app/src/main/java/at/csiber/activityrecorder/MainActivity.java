package at.csiber.activityrecorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
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

    public void startRecording(){
    }

    public void stopRecording(){
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        stopRecording();
    }
}
