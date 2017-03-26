package at.csiber.activityrecorder.recorders.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import at.csiber.activityrecorder.recorders.AbstractRecordNotifier;
import at.csiber.activityrecorder.recorders.RecorderInterface;

public class LocationRecorder extends AbstractRecordNotifier<Location>
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
    protected String convertToString(Location record) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Calendar c = Calendar.getInstance();
        String now = df.format(c.getTime());

        //TODO: fix string format
        return now + " from " + record.getProvider() + ": " + record.getLatitude() + " / " + record.getLongitude();
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
}