package at.csiber.activityrecorder.recorders.acceleration;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import at.csiber.activityrecorder.recorders.AbstractRecordNotifier;
import at.csiber.activityrecorder.recorders.RecorderInterface;

public class AccelerationRecorder extends AbstractRecordNotifier<SensorEvent> implements RecorderInterface<SensorEvent> {

    private SensorManager sensorManager;
    private Sensor accelerationSensor;
    private SensorEventListener accelerationEventListener;

    public AccelerationRecorder(Context ctx){
        sensorManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
        accelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accelerationEventListener = new AccelerationEventListener(this);
    }

    @Override
    protected String convertToString(SensorEvent record) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Calendar c = Calendar.getInstance();
        String now = df.format(c.getTime());

        //TODO: fix string format
        return now + " from accelerometer: " + record.values[0] + " / " + record.values[1] + " / " + record.values[2];
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
