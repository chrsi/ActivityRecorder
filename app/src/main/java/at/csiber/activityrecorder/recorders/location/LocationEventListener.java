package at.csiber.activityrecorder.recorders.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import at.csiber.activityrecorder.recorders.RecordNotifier;

/**
 * Event Listener for Location Updates
 */
public class LocationEventListener implements LocationListener {
    private RecordNotifier<Location> recordNotifier;

    /**
     * Creates an LocationEventListener object
     * @param recordNotifier is used to notify various subscriber about location updates
     */
    public LocationEventListener(RecordNotifier<Location> recordNotifier){
        this.recordNotifier = recordNotifier;
    }

    @Override
    public void onLocationChanged(Location location) {
        recordNotifier.notify(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}