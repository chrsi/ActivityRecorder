package at.csiber.activityrecorder.services.recording;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.location.Location;
import android.os.IBinder;

import at.csiber.activityrecorder.recorders.RecorderDirectory;
import at.csiber.activityrecorder.recorders.RecorderInterface;

/**
 * Service for handling the Lifecycle of the various Recorders
 */
public class RecordingService extends Service {
    public static final String TAG = RecordingService.class.getSimpleName();

    private RecorderDirectory recorderDirectory;

    public RecordingService() {
        recorderDirectory = RecorderDirectory.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);

        locationRecorder.start();
        accelerationRecorder.start();

        return START_REDELIVER_INTENT;
    }

    private RecorderInterface<Location> locationRecorder;
    private RecorderInterface<SensorEvent> accelerationRecorder;

    @Override
    public void onDestroy() {
        //TODO stop recording entities
        locationRecorder.stop();
        accelerationRecorder.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        locationRecorder = recorderDirectory.getRecorder(RecorderDirectory.LOCATION_RECORDER);
        accelerationRecorder = recorderDirectory.getRecorder(RecorderDirectory.ACCELERATION_RECORDER);
    }
}
