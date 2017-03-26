package at.csiber.activityrecorder.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.icu.text.AlphabeticIndex;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

        return START_REDELIVER_INTENT;
    }

    RecorderInterface<Location> locationRecorder;

    @Override
    public void onDestroy() {
        //TODO stop recording entities
        locationRecorder.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //TODO create recording entities
        locationRecorder = recorderDirectory.getRecorder(RecorderDirectory.LOCATION_RECORDER);
    }
}
