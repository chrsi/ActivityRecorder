package at.csiber.activityrecorder.services;

import android.hardware.SensorEvent;

import static at.csiber.activityrecorder.recorders.RecorderDirectory.ACCELERATION_RECORDER;

/**
 * Logs the Acceleration events into a File
 */
public class AccelerationLogger extends LoggingService<SensorEvent> {
    @Override
    protected String getFileIdentifier() {
        return "acc";
    }

    @Override
    protected String getRecorderType() {
        return ACCELERATION_RECORDER;
    }
}
