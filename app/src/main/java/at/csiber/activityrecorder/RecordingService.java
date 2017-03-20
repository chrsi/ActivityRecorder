package at.csiber.activityrecorder;

import android.app.Service;
import android.content.Intent;
import android.icu.text.AlphabeticIndex;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class RecordingService extends Service {
    public static final String TAG = RecordingService.class.getSimpleName();

    public RecordingService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO start recording entities
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        //TODO stop recording entities
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO create recording entities
    }
}
