package at.csiber.activityrecorder.recorders;

/**
 * Created by siber on 26.03.2017.
 */

public interface RecordNotifier<T> {
    void notify(T record);
    void addNotificationHandler(RecordNotificationHandler handler);
    void removeNotificationHandler(RecordNotificationHandler handler);
}
