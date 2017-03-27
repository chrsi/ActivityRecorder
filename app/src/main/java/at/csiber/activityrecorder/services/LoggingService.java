package at.csiber.activityrecorder.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import at.csiber.activityrecorder.recorders.RecordNotificationHandler;
import at.csiber.activityrecorder.recorders.RecorderDirectory;
import at.csiber.activityrecorder.recorders.RecorderInterface;

/**
 * Logs various Events of Type T into a csv File
 * @param <T> Type of Events that will be logged
 */
public abstract class LoggingService<T> extends Service {
    private RecorderInterface<T> recorder;
    private FileOutputStream fos;
    private OutputStreamWriter writer;
    private File logFile;

    //Defines the used Recorder. (e.g. RecorderDirectory.LOCATION_RECORDER for location)
    protected abstract String GetRecorderType();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //Initialize recorder
        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        RecorderDirectory recorderDirectory = RecorderDirectory.getInstance(this);
        recorder = recorderDirectory.getRecorder(GetRecorderType());

        //Initialize log directory
        // Use SD Card as External Storage, because it provides more capacity: currently 32GB
        File logFileDir = getExternalFilesDirs(null)[1];
        logFileDir.mkdirs();

        //Initialize log file
        logFile = new File(logFileDir, GetRecorderType() + ".csv");
        try {
            logFileDir.createNewFile();
        } catch (IOException e) {
            //TODO: handle errors
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        openStream();
        recorder.addNotificationHandler(fileLogger);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        recorder.removeNotificationHandler(fileLogger);
        closeStream();

        super.onDestroy();
    }

    private RecordNotificationHandler fileLogger = new RecordNotificationHandler(){
        @Override
        public void HandleNotification(String value) {
            try {
                writer.write(value + "\n");
            } catch (IOException e) {
                //TODO: handle errors
                e.printStackTrace();
            }
        }
    };

    /**
     * Opens the Streams required for logging.
     */
    private void openStream() {
        if ((writer == null) || (fos == null)){
            try {
                fos = new FileOutputStream(logFile);
                writer = new OutputStreamWriter(fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Closes the Streams required for logging.
     */
    private void closeStream() {
        try {
            writer.close();
            writer = null;
            fos.close();
            fos = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
