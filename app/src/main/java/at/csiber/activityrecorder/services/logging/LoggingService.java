package at.csiber.activityrecorder.services.logging;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    protected static final String SEPERATOR = ";";

    private static final DateFormat LOG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    //Defines the identifier for the log file.
    protected abstract String getFileIdentifier();

    //Defines the used Recorder. (e.g. RecorderDirectory.LOCATION_RECORDER for location)
    protected abstract String getRecorderType();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Creates a unique fileName using a counter variable as postfix.
     * Format: Date-fileName[-CounterVar].csv
     * The counter variable is optional. The name of the first file does not contain it.
     * @param directory that contains the files
     * @return a fileName with optional counter at the end.
     */
    private String getFileName(File directory, String fileName){
        //Calculate Current Day
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        String currentDay = df.format(c.getTime());

        //Create File with the format: Date-fileName
        final String filePrefix = currentDay + "-" + fileName;

        //TODO: could cause an exception if an existing file will be deleted.
        // This will happen, because it only appends the number of files inside the directory.
        // It does not check whether it exists or not.
        int nrOfFiles = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().startsWith(filePrefix);
            }
        }).length;

        //Append the number only if there already exists a file with the format: Date-fileName
        return nrOfFiles == 0 ? filePrefix + ".csv" :
                                filePrefix + "-" + nrOfFiles + ".csv";
    }

    protected abstract String createString(T event);

    @Override
    public void onCreate() {
        //Initialize recorder
        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        RecorderDirectory recorderDirectory = RecorderDirectory.getInstance(this);
        recorder = recorderDirectory.getRecorder(getRecorderType());

        //Initialize log directory
        // Use SD Card as External Storage, because it provides more capacity: currently 32GB
        File logFileDir = getExternalFilesDirs(null)[1];
        logFileDir.mkdirs();

        //Initialize log file
        logFile = new File(logFileDir, getFileName(logFileDir, getFileIdentifier()));
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

    private String getTime(){
        return LOG_DATE_FORMAT.format(Calendar.getInstance().getTime());
    }

    private RecordNotificationHandler fileLogger = new RecordNotificationHandler<T>(){
        @Override
        public void HandleNotification(T value) {
            try {
                writer.write(getTime() + SEPERATOR + createString(value) + "\n");
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
