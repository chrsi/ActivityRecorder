package at.csiber.activityrecorder.recorders;

/**
 *  Handles notifications from various recorders.
*/
public interface RecordNotificationHandler<T> {
    /**
     * Defines what should be done with the recorded event.
     * @param value string message describing the recorded event.
     */
    void HandleNotification(T value);
}
