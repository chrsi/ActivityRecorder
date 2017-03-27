package at.csiber.activityrecorder.recorders.acceleration;

/**
 * Domain Class for acceleration data encapsulating the SensorEvent
 */
public class Acceleration {
    Acceleration(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private float x;
    private float y;
    private float z;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
