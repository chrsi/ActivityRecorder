package at.csiber.activityrecorder.recorders;

/**
 *  Handles notifications from various recorders.
*/
public interface RecordNotificationHandler {
    /**
     * Defines what should be done with the recorded event.
     * @param value string message describing the recorded event.
     */
    void HandleNotification(String value);
}
