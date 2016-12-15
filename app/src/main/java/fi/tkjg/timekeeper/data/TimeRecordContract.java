package fi.tkjg.timekeeper.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Tom Granqvist on 07-12-2016.
 *
 * Contract class for the database tables.
 */

public final class TimeRecordContract implements BaseColumns{

    /*
    public static class TimeRecordEntry implements BaseColumns {
        public static final String TABLE_NAME = "records";
        public static final String COLUMN_TIME_IN = "time_in";
        public static final String COLUMN_TIME_OUT = "time_out";
        public static final String COLUMN_VELHO_OK = "velho_ok";

        public static final String[] ALL_COLUMNS = {
            _ID, COLUMN_TIME_IN, COLUMN_TIME_OUT, COLUMN_VELHO_OK
        };
    };*/

    public static final String TABLE_NAME = "records";
    public static final String COLUMN_TIME_IN = "time_in";
    public static final String COLUMN_TIME_OUT = "time_out";
    public static final String COLUMN_VELHO_OK = "velho_ok";

    public static final String[] ALL_COLUMNS = {
            _ID, COLUMN_TIME_IN, COLUMN_TIME_OUT, COLUMN_VELHO_OK
    };

    // PRIVATE //
    private TimeRecordContract() {
        // No instances
    }
}
