package com.example.sahith.eventifi3;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseObject;


public class NewEventActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        Intent intent = getIntent();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
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

    public void closeEventCreator(View view){
        EditText edit1 = (EditText)findViewById(R.id.nameInput);
        String name = edit1.getText().toString();
        EditText edit2 = (EditText)findViewById(R.id.descriptionInput);
        String description = edit2.getText().toString();
        EditText edit3 = (EditText)findViewById(R.id.startDateInput);
        String startDate = edit3.getText().toString();
        EditText edit4 = (EditText)findViewById(R.id.startTimeInput);
        String startTime = edit4.getText().toString();
        EditText edit5 = (EditText)findViewById(R.id.endDateInput);
        String endDate = edit5.getText().toString();
        EditText edit6 = (EditText)findViewById(R.id.endTimeInput);
        String endTime = edit6.getText().toString();
        EditText edit7 = (EditText)findViewById(R.id.capacityInput);
        String capacity = edit7.getText().toString();
        EditText edit8 = (EditText)findViewById(R.id.addressInput);
        String address = edit8.getText().toString();
        ParseObject event = new ParseObject("Event");
        event.put("name", name);
        event.put("description", description);
        event.put("startDate", startDate);
        event.put("startTime", startTime);
        event.put("endDate", endDate);
        event.put("endTime", endTime);
        event.put("capacity", capacity);
        event.put("address", address);
        event.put("display", true);
        event.saveInBackground();

    }
}
