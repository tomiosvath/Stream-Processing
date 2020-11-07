package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MonitoredData {
    private Date startTime, endTime;
    private String activity;

    public MonitoredData(Date startTime, Date endTime, String activity){
        this.startTime = startTime;
        this.endTime = endTime;
        this.activity = activity;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public long getDiff() {
        long diffInMillis = endTime.getTime() - startTime.getTime();
        return TimeUnit.MINUTES.convert(diffInMillis,TimeUnit.MILLISECONDS);
    }

    public String toString(){
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dFormat.format(startTime) + " " + dFormat.format(endTime) + " " + activity;
    }

    public Date getDay() throws ParseException {
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dFormat.parse(dFormat.format(startTime));
    }
}
