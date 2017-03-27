package at.csiber.activityrecorder.recorders.location;

import android.location.LocationListener;
import android.os.Bundle;

import at.csiber.activityrecorder.recorders.AbstractEventListener;
import at.csiber.activityrecorder.recorders.AbstractRecorder;

/**
 * Event Listener for Location Updates
 */
public class LocationEventListener extends AbstractEventListener<android.location.Location, Location> implements LocationListener {

    public LocationEventListener(AbstractRecorder recordNotifier) {
        super(recordNotifier);
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        getRecordNotifier().notify(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {    }

    @Override
    public void onProviderEnabled(String provider) {    }

    @Override
    public void onProviderDisabled(String provider) {    }
}