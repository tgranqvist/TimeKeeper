package fi.tkjg.timekeeper.data;

import java.util.Calendar;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by granqto on 08-12-2016.
 */

public class TimeRecord implements Serializable {
    public String date;
    public int in;
    public int out;
    public boolean isVelhoOk;

    public String toString() {
        return String.format("%s in %s, out %s", date, toTimeString(in), toTimeString(out));
    }


    public static TimeRecord fromNow() {
        Calendar now = Calendar.getInstance();
        TimeRecord record = new TimeRecord();
        record.date = String.format(Locale.getDefault(), "%d-%d-%d", now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        record.in = now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE);
        record.out = 0;
        record.isVelhoOk = false;

        return record;
    }

    // PRIVATE //
    private String toTimeString(int time) {
        return String.format(Locale.getDefault(), "%d:%d", (time / 60), (time % 60));
    }
}
