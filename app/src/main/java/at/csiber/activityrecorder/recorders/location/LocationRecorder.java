package at.csiber.activityrecorder.recorders.location;

import android.content.Context;
import android.location.LocationManager;

import at.csiber.activityrecorder.recorders.AbstractRecorder;
import at.csiber.activityrecorder.recorders.RecorderInterface;

public class LocationRecorder extends AbstractRecorder<android.location.Location, Location>
        implements RecorderInterface<Location> {

    private LocationManager locationManager;
    private LocationEventListener eventListener = new LocationEventListener(this);

    /**
     * Records location updates from various location providers
     * @param ctx
     */
    public LocationRecorder(Context ctx){
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);

    }

    @Override
    public void start() throws SecurityException{
        //TODO: think about GPS / NETWORK strategy
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, eventListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 0, eventListener);
    }

    @Override
    public void stop() {
        locationManager.removeUpdates(eventListener);
    }

    @Override
    protected Location convertToDomain(android.location.Location record) {
        return new at.csiber.activityrecorder.recorders.location.Location(record.getLatitude(), record.getLongitude(), record.getProvider());
    }
}