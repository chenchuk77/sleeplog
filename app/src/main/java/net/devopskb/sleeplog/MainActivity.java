package net.devopskb.sleeplog;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AsyncResponse{

//    public final String SERVER_URL = "http://10.0.2.2:5000/";
    public final String SERVER_URL = "http://84.111.154.20:5000/";

    // Array of strings...
    String[] ccValues = {"0 cc","10 cc","20 cc","30 cc","40 cc","50 cc","60 cc","70 cc",
            "80 cc","90 cc","100 cc","110 cc","120 cc","130 cc","140 cc","150 cc"};

    Integer selected_cc = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // bind adapter to data source
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, ccValues);

        // get the cc_list view item and bind to the adapter
        final ListView listView = (ListView) findViewById(R.id.cc_list);
        listView.setAdapter(adapter);
        // update context with selection
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected_cc_string = (String)listView.getItemAtPosition(position);
                Log.d("INFO", "selected_cc_string - " + selected_cc_string);
                String str = selected_cc_string.split(" ")[0];
                Log.d("INFO", "str - " + str);
                selected_cc = Integer.parseInt(str);
            }
        });

        // wil appear if cb_milk is checked
        listView.setVisibility(View.INVISIBLE);
        final CheckBox cb_milk = (CheckBox) findViewById(R.id.cb_milk);
        cb_milk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    listView.setVisibility(View.VISIBLE);
                } else {
                    listView.setVisibility(View.INVISIBLE);
                }
            }
        });

        final Button btnSleep = (Button)findViewById(R.id.btn_sleep);
        btnSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("INFO", "btnSleep pressed.");
                Boolean sleep_with_tits = ((CheckBox)findViewById(R.id.cb_tits)).isChecked();
                Boolean is_cry = ((CheckBox)findViewById(R.id.cb_cry)).isChecked();
                String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                SleepRecord record = new SleepRecord("SLEEP", sleep_with_tits , is_cry, selected_cc, time);
                RetrieveFeedTask task = new RetrieveFeedTask();
                //this to set delegate/listener back to the parent ( we are inside onClick... )
                task.delegate = MainActivity.this;
                task.execute(SERVER_URL + "sleepws/addrecord/" + record.toString());
                Log.d("INFO", "async task created.");
            }
        });
        final Button btnWakeup = (Button)findViewById(R.id.btn_wkup);
        btnWakeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("INFO", "btnWakeup pressed.");
                String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                SleepRecord record = new SleepRecord("WAKEUP", false , false, 0, time);
                RetrieveFeedTask task = new RetrieveFeedTask();
                //this to set delegate/listener back to the parent ( we are inside onClick... )
                task.delegate = MainActivity.this;
                task.execute(SERVER_URL + "sleepws/addrecord/" + record.toString());
                Log.d("INFO", "async task created.");
            }
        });
        final Button btnGetAll = (Button)findViewById(R.id.btn_getall);
        btnGetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("INFO", "btnGetAll pressed.");
                RetrieveFeedTask task = new RetrieveFeedTask();
                //this to set delegate/listener back to the parent ( we are inside onClick... )
                task.delegate = MainActivity.this;
                task.execute(SERVER_URL + "sleepws/getall");
                Log.d("INFO", "async task created.");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //this override the implemented method from asyncTask
    @Override
    public void processFinish(String reply){
        Log.d("INFO", "reply received: " + reply);
        final EditText et_output = (EditText) findViewById(R.id.et_output);
        et_output.setText(reply);

        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.

    }
}
