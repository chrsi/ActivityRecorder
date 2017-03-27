package at.csiber.activityrecorder.recorders.acceleration;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;

import at.csiber.activityrecorder.recorders.AbstractEventListener;
import at.csiber.activityrecorder.recorders.RecordNotifier;

public class AccelerationEventListener extends AbstractEventListener<SensorEvent, Acceleration> implements SensorListener, SensorEventListener {

    public AccelerationEventListener(AbstractRecorder<SensorEvent, Acceleration> recordNotifier) {
        super(recordNotifier);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        getRecordNotifier().notify(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {
    }

    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {
    }
}
