package at.csiber.activityrecorder.recorders.location;

/**
 * Domain Class for location data encapsulating Location events.
 */
public class Location {
    public  Location(double latitude, double longitude, String provider){
        this.latitude = latitude;
        this.longitude = longitude;
        this.provider = provider;
    }

    private double latitude;
    private double longitude;
    private String provider;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getProvider() {
        return provider;
    }
}
