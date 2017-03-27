package at.csiber.activityrecorder.recorders;

import android.content.Context;
public abstract class AbstractEventListener<TSensed, T>{
    private AbstractRecorder<TSensed, T> recordNotifier;

/**
 * Created by siber on 26.03.2017.
 */

    protected AbstractRecorder<TSensed, T> getRecordNotifier(){
        return recordNotifier;
    }

    public AbstractEventListener(AbstractRecorder<TSensed, T> recordNotifier){
        this.recordNotifier = recordNotifier;
    }
}
