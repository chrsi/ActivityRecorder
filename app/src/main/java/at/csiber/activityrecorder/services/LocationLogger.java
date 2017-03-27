package at.csiber.activityrecorder.services;

import android.location.Location;

import static at.csiber.activityrecorder.recorders.RecorderDirectory.LOCATION_RECORDER;

/**
 * Logs the Location events into a File
 */
public class LocationLogger extends LoggingService<Location> {
    @Override
    protected String GetRecorderType() {
        return LOCATION_RECORDER;
    }
}
