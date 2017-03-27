package at.csiber.activityrecorder.recorders.acceleration;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import at.csiber.activityrecorder.recorders.AbstractRecorder;
import at.csiber.activityrecorder.recorders.RecorderInterface;

public class AccelerationRecorder extends AbstractRecorder<SensorEvent, Acceleration> implements RecorderInterface<Acceleration> {

    private SensorManager sensorManager;
    private Sensor accelerationSensor;
    private SensorEventListener accelerationEventListener;

    public AccelerationRecorder(Context ctx){
        sensorManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
        accelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accelerationEventListener = new AccelerationEventListener(this);
    }

    @Override
    protected Acceleration convertToDomain(SensorEvent record) {
        return new Acceleration(record.values[0], record.values[1], record.values[2]);
    }

    @Override
    public void start() throws SecurityException {
        sensorManager.registerListener(accelerationEventListener, accelerationSensor, 10000);
    }

    @Override
    public void stop() {
        sensorManager.unregisterListener(accelerationEventListener, accelerationSensor);
    }
}
