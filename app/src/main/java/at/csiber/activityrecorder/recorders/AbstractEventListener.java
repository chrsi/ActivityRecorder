package at.csiber.activityrecorder.recorders;

import android.content.Context;

/**
 * Created by siber on 26.03.2017.
 */

public abstract class AbstractEventListener<T>{
    private RecordNotifier<T> recordNotifier;

    protected RecordNotifier<T> getRecordNotifier(){
        return recordNotifier;
    }

    public AbstractEventListener(RecordNotifier<T> recordNotifier){
        this.recordNotifier = recordNotifier;
    }
}
