package at.csiber.activityrecorder.recorders;

import android.content.Context;

import java.util.Dictionary;
import java.util.Hashtable;

import at.csiber.activityrecorder.recorders.location.LocationRecorder;

/**
 * Directory containing every recorder.
 * Provides access to every recorder, that is registered by this class.
 * Currently: Location
 * Implements: "singleton pattern"
 */
public class RecorderDirectory {
    public static final String LOCATION_RECORDER = "at.csiber.activityrecorder.recorders.LOCATION_RECORDER";

    //<editor-fold desc="Singleton">
    public static RecorderDirectory recorderDirectoryInstance;

    /**
     * Get an instance of a RecorderDirectory.
     * @param ctx is the Context, where the recorders will be created.
     * @return instance of a RecorderDirectory
     */
    public static RecorderDirectory getInstance(Context ctx){
        if (recorderDirectoryInstance == null) recorderDirectoryInstance = new RecorderDirectory(ctx);
        return recorderDirectoryInstance;
    }
    //</editor-fold>

    //<editor-fold desc="Object">
    private RecorderDirectory(Context ctx){
        recorders = new Hashtable<>();
        recorders.put(LOCATION_RECORDER, new LocationRecorder(ctx));
    }

    private Dictionary<String, RecorderInterface> recorders;

    /**
     * Get a registered recorded using an identifier.
     * @param recorderIdentifier identifies a recorder using predefined Strings (e.g. LOCATION_RECORDER)
     * @return a registered recorder.
     */
    public RecorderInterface getRecorder(String recorderIdentifier){
        return recorders.get(recorderIdentifier);
    }
    //</editor-fold>
}
