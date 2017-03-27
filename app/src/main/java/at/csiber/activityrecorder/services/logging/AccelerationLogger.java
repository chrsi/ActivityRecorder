package at.csiber.activityrecorder.services.logging;

import at.csiber.activityrecorder.recorders.acceleration.Acceleration;

import static at.csiber.activityrecorder.recorders.RecorderDirectory.ACCELERATION_RECORDER;

/**
 * Logs the Acceleration events into a File
 */
public class AccelerationLogger extends LoggingService<Acceleration> {
    @Override
    protected String getFileIdentifier() {
        return "acc";
    }

    @Override
    protected String getRecorderType() {
        return ACCELERATION_RECORDER;
    }

    @Override
    protected String createString(Acceleration event) {
        return event.getX() + SEPERATOR + event.getY() + SEPERATOR + event.getZ();
    }
}
