package com.abhi.reminderstoself;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RemindersActivity extends AppCompatActivity {

    private ArrayList<String> reminders = new ArrayList<String>();
    private TextView reminderText;
    private int lastEnteredReminder;
    private int currentReminderIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        reminderText = (TextView) findViewById(R.id.reminderText);
        Intent intent = getIntent();

        //display last user entered reminder, if not building from a saved state
        if (savedInstanceState != null) {
            currentReminderIndex = (int) savedInstanceState.get("currentlyDisplayedReminder");
            reminders = savedInstanceState.getStringArrayList("reminders");
            reminderText.setText(reminders.get(currentReminderIndex));

        } else {
            reminders = (ArrayList<String>) intent.getExtras().get("reminders");
            lastEnteredReminder = reminders.size() - 1;
            reminderText.setText(reminders.get(lastEnteredReminder));
        }
    }

    public void onClickAdd(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putStringArrayListExtra("existingReminders", reminders);
        startActivity(intent);
    }

    public void onClickDelete(View view) {
        currentReminderIndex = getCurrentRemindersIndex();
        reminders.remove(currentReminderIndex);

        //if have more reminders, stay on Reminders screen, else go back to the main screen
        if (reminders.size() > 0) {
            onClickNext(view);
        } else {
            onClickAdd(view);
        }
    }

    public void onClickNext(View view) {
        currentReminderIndex = getCurrentRemindersIndex();

        if (currentReminderIndex == reminders.size() - 1) {
            Toast.makeText(getApplicationContext(), R.string.endOfList, Toast.LENGTH_SHORT).show();
        } else {
            reminderText.setText(reminders.get(currentReminderIndex + 1));
        }
    }

    public void onClickPrevious(View view) {
        currentReminderIndex = getCurrentRemindersIndex();

        if (currentReminderIndex == 0) {
            Toast.makeText(getApplicationContext(), R.string.begOfList, Toast.LENGTH_SHORT).show();
        } else {
            reminderText.setText(reminders.get(currentReminderIndex - 1));
        }
    }

    public int getCurrentRemindersIndex() {
        return reminders.indexOf(reminderText.getText().toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        currentReminderIndex = getCurrentRemindersIndex();
        savedInstanceState.putInt("currentlyDisplayedReminder", currentReminderIndex);
        savedInstanceState.putStringArrayList("reminders", reminders);
    }
}
