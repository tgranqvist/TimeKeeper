package fi.tkjg.timekeeper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fi.tkjg.timekeeper.data.TimeRecord;

public class TimeKeeper extends AppCompatActivity
        implements AdapterView.OnItemClickListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        adapter = new TimeRecordArrayAdapter(this, new ArrayList<TimeRecord>());
        ListView items = (ListView) findViewById(R.id.recordsList);
        items.setAdapter(adapter);
        items.setOnItemClickListener(this);

        IntentFilter intentFilter = new IntentFilter(Constants.SERVICE_REPLY);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);

        TimeRecord now = TimeRecord.fromNow();
        Intent serviceIntent = new Intent(this, BackgroundService.class)
            .setAction(Constants.COMMAND_ADD_ENTRY)
            .putExtra(Constants.ITEM, now);
        startService(serviceIntent);

        startService(new Intent(this, BackgroundService.class).setAction(Constants.COMMAND_LIST_ENTRIES));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_export: {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Your Time Keeper database backup");
                intent.putExtra(Intent.EXTRA_TEXT, "Test");
                startActivity(Intent.createChooser(intent, "Export as email"));
                break;
            }
            case R.id.action_about: {
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // PRIVATE //
    private static final String TAG = "TimeKeeper";

    private TimeRecordArrayAdapter adapter;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TimeKeeper", "received broadcast " + intent.toString());
            switch (intent.getStringExtra(Constants.COMMAND)) {
                case Constants.COMMAND_LIST_ENTRIES:
                    List<TimeRecord> records = (List<TimeRecord>)intent.getSerializableExtra(Constants.ITEMS);
                    adapter.clear();
                    adapter.addAll(records);
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String msg = String.format(Locale.getDefault(), "pos: %d, id: %d", position, id);
        Toast.makeText(TimeKeeper.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).show();
    }
}