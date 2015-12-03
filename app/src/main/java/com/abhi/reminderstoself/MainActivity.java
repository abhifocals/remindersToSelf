package com.abhi.reminderstoself;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> reminders = new ArrayList<String>();
    private EditText enteredReminderView;
    private String userEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enteredReminderView = (EditText) findViewById(R.id.newReminder);

        if (savedInstanceState != null) {
            reminders = (ArrayList<String>) savedInstanceState.get("savedReminders");
            userEntry = savedInstanceState.getString("userEntry");
            enteredReminderView.setText(userEntry);
        }

        //getIntent if called by RemindersActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            reminders = (ArrayList<String>) extras.get("existingReminders");
        }
    }

    public void onClickShowReminders(View view) {
        String enteredReminder = enteredReminderView.getText().toString();

        //if nothing entered, show existing reminders, if any
        if (!enteredReminder.isEmpty() && reminders.contains(enteredReminder)) {
            Toast.makeText(getApplication().getBaseContext(), R.string.newReminderToast, Toast.LENGTH_SHORT).show();
        } else if (enteredReminder.isEmpty() && reminders.size() == 0) {
            Toast.makeText(getApplication().getBaseContext(), R.string.emptyList, Toast.LENGTH_SHORT).show();
        } else {
            if (!enteredReminder.isEmpty()) {
                reminders.add(enteredReminder);
            }
            ImageButton showRemindersImageButton = (ImageButton) findViewById(R.id.showReminders);
            Intent intent = new Intent(this, RemindersActivity.class);
            intent.putStringArrayListExtra("reminders", reminders);
            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putStringArrayList("savedReminders", reminders);

        if (!enteredReminderView.getText().toString().isEmpty()) {
            savedInstanceState.putString("userEntry", enteredReminderView.getText().toString());
        }
    }
}