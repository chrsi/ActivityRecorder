package at.csiber.activityrecorder.recorders;

/**
 * Provides methods to start and stop a recorder.
 * @param <T> is the type of event object, the recorder provides (e.g. Location from LocationManager)
 */
public interface RecorderInterface<T> extends RecordNotifier<T> {
    /**
     * Starts the recording process.
     * @throws SecurityException will be thrown, if the permissions aren't set yet.
     */
    void start() throws SecurityException;

    /**
     * Stops the recording process
     */
    void stop();
}
