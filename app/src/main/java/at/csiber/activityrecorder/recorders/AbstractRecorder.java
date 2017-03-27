package at.csiber.activityrecorder.recorders;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements basic functionality for Recorders.
 * Notifies each subscriber about new records of a specific type.
 * The notifications are Domain objects, describing the event.
 * Implements the "observer pattern"
 * @param <TRecorded> describes the recorded event type (e.g. SensorEvent from SensorManager)
 * @param <T> describes the used domain class of a recorded event (e.g. Acceleration for SensorEvent objects)
 */
public abstract class AbstractRecorder<TRecorded, T> implements RecordNotifierInterface<T> {
    List<RecordNotificationHandler> notificationHandlers = new ArrayList<>();

    /**
     * Adds an object to the list of notified handlers
     * @param handler is the observer. See "observer pattern"
     */
    public void addNotificationHandler(RecordNotificationHandler<T> handler){
        notificationHandlers.add(handler);
    }

    /**
     * Removes an object from the list of notified handlers
     * @param handler is the observer. See "observer pattern"
     */
    public void removeNotificationHandler(RecordNotificationHandler<T> handler){
        notificationHandlers.remove(handler);
    }

    /**
     * converts the recorded event to a domain object
     * @param record is an update provided by some recorder (e.g. Location from LocationManager)
     * @return message describing the event
     */
    protected abstract T convertToDomain(TRecorded record);

    public void notify(TRecorded record) {
        for(RecordNotificationHandler notificationHandler : notificationHandlers){
            notificationHandler.HandleNotification(convertToDomain(record));
        }
    }
}
