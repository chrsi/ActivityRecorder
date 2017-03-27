package at.csiber.activityrecorder.recorders;

public abstract class AbstractEventListener<TSensed, T>{
    private AbstractRecorder<TSensed, T> recordNotifier;

    protected AbstractRecorder<TSensed, T> getRecordNotifier(){
        return recordNotifier;
    }

    public AbstractEventListener(AbstractRecorder<TSensed, T> recordNotifier){
        this.recordNotifier = recordNotifier;
    }
}
