package fi.tkjg.timekeeper;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.Serializable;

import fi.tkjg.timekeeper.data.TimeRecord;
import fi.tkjg.timekeeper.data.TimeRecordDBHelper;

/**
 * This service handles the background jobs for the TimeKeeper application.
 * It's a private service available only to this app.
 */

public class BackgroundService extends IntentService {

    public BackgroundService() {
        super(BackgroundService.class.getName());
        broadcastManager = LocalBroadcastManager.getInstance(this);
        dbHelper = new TimeRecordDBHelper(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (null == intent || null == intent.getAction()) return;
        String action = intent.getAction();

        switch (action) {
            case Constants.COMMAND_LIST_ENTRIES:
                doListEntries();
                break;
            case Constants.COMMAND_ADD_ENTRY:
                doAddEntry((TimeRecord)intent.getSerializableExtra(Constants.ITEM));
                break;
            default:
                Log.e("BackgroundService", "Undefined action " + action);
        }
    }

    // PRIVATE //
    private final LocalBroadcastManager broadcastManager;
    private final TimeRecordDBHelper dbHelper;

    private void doListEntries() {
        Intent reply = new Intent(Constants.SERVICE_REPLY);
        reply.putExtra(Constants.COMMAND, Constants.COMMAND_LIST_ENTRIES);
        reply.putExtra(Constants.ITEMS, (Serializable)dbHelper.getAllEntries());
        broadcastManager.sendBroadcast(reply);
    }

    private void doAddEntry(TimeRecord record) {
        dbHelper.addEntry(record);
    }
}