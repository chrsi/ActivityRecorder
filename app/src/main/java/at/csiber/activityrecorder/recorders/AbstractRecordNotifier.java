package at.csiber.activityrecorder.recorders;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siber on 26.03.2017.
 */

/**
 * Notifies each subscriber about new records of a specific type.
 * The notifications are string messages, describing the event.
 * Implements the "observer pattern"
 * @param <T> describes the type of a recorded event (e.g. Location from LocationListener)
 */
public abstract class AbstractRecordNotifier<T> implements RecordNotifier<T> {
    List<RecordNotificationHandler> notificationHandlers = new ArrayList<>();

    /**
     * Adds an object to the list of notified handlers
     * @param handler is the observer. See "observer pattern"
     */
    public void addNotificationHandler(RecordNotificationHandler handler){
        notificationHandlers.add(handler);
    }

    /**
     * Removes an object from the list of notified handlers
     * @param handler is the observer. See "observer pattern"
     */
    public void removeNotificationHandler(RecordNotificationHandler handler){
        notificationHandlers.remove(handler);
    }

    /**
     * creates a string message from a recorded event
     * @param record is an update provided by some recorder (e.g. Location from LocationManager)
     * @return message describing the event
     */
    protected abstract String convertToString(T record);

    @Override
    public void notify(T record) {
        for(RecordNotificationHandler notificationHandler : notificationHandlers){
            notificationHandler.HandleNotification(convertToString(record));
        }
    }
}
