package at.csiber.activityrecorder.recorders;

public interface RecordNotifierInterface<T> {
    void addNotificationHandler(RecordNotificationHandler<T> handler);
    void removeNotificationHandler(RecordNotificationHandler<T> handler);
}
