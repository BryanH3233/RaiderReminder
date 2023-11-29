package com.example.raiderreminder;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.app.AlarmManager;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.Serializable;

import android.widget.Toast;
public class eventClass implements Serializable{
    // private data
    private static int uniqueIdCounter = 0;
    private int uniqueId;
    private String eventName = ""; // event name
    private int year = 0;
    private int month = 0;
    private int day = 0;
    private int hour = 0; // military time
    private int minute = 0;
    private int second = 0;
    private String location = "None";
    private boolean isChecked;
    private String notificationMessage = eventName + " at " + year + "/" + month + "/" + day + " "
            + hour + ":" + minute + ", in " + location; //message for notification

    // description maybe?
    Calendar task = Calendar.getInstance();
    private long timeInMillis = 0; //format used in setting timed notifications
    public eventClass() { //Constructor: iterates id's for request codes
        this.uniqueId = uniqueIdCounter++;
    }
    // create a timezone

    // public methods

    // creates a sets the time for a calendar object
    public void createCalEvent(){
        task = Calendar.getInstance();
        task.set(year,month -1,day,hour,minute,second);
        notificationMessage = eventName + " at " + year + "/" + month + "/" + day + " "
                + hour + ":" + minute + ", in " + location;
    }

    // change the name and get the name
    public void setName(String eventName){this.eventName = eventName;}
    public String getName(){return eventName;}

    // change the year or get the year
    public void setYear(int year){
        this.year = year;
        createCalEvent(); // reset the calendar
    }
    public int getYear(){return year;}

    // change the month or get the month
    public void setMonth(int month){
        this.month = month;
        createCalEvent(); // reset the calendar
    }
    public int getMonth(){return month;}

    // change the day or get the day
    public void setDay(int day){
        this.day = day;
        createCalEvent(); // reset the calendar
    }
    public int getDay(){return day;}

    // get the hour or change the hour
    public void setHour(int hour){
        this.hour = hour;
        createCalEvent(); // reset the calendar
    }
    public int getHour(){return hour;}

    // get the minute or change the minute

    public void setMinute(int minute){
        this.minute = minute;
        createCalEvent(); // reset the calendar
    }
    public int getMinute(){return minute;}

    // set and get the time in milliseconds
    public void setTimeInMillis(long timeInMillis){
        this.timeInMillis = timeInMillis;
        createCalEvent(); // reset the calendar
    }
    public long getTimeInMillis(){return timeInMillis;}
    // get the notification message text
    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setLocation(String location) {
        this.location = location;
        createCalEvent(); // rest the calendar event
    }
    public String getLocation(){return location;}


    public String getDateAndTime() {
        // Format the date and time as a string
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(task.getTime());
    }

    public String getTime12Hour() {
        // Format the time as a string in 12-hour format
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(task.getTime());
    }

    // Getter and setter for isChecked
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    public Calendar getTask() {
        return task;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setAlarms(Context context) {
        // Set alarms for 1 day, 1 week, and 1 hour before
        setAlarm(context, timeInMillis - 24 * 60 * 60 * 1000,1); // 1 day before
        setAlarm(context, timeInMillis - 7 * 24 * 60 * 60 * 1000,2); // 1 week before
        setAlarm(context, timeInMillis - 60 * 60 * 1000,3); // 1 hour before
        // Set the main alarm
        setAlarm(context, timeInMillis,0);
    }

    public void setAlarm(Context context, long alarmTimeInMillis, int type) {
        // Check if the alarm time is in the past
        if (alarmTimeInMillis <= System.currentTimeMillis()) {
            Toast.makeText(context, "Cannot set alarm for past time", Toast.LENGTH_SHORT).show();
            return;
        }
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        // Pass the event info needed to recreate PendingIntent
        intent.putExtra("eventClass", this);

        // Use a combination of uniqueId and requestCode to create a unique identifier
        int uniqueRequestCode = uniqueId * 1000 + type;

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, uniqueRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

        // Cancel any existing alarms with the same requestCode
        alarmManager.cancel(pendingIntent);

        // Set the new alarm
        Toast.makeText(context, "Alarm set for " + uniqueRequestCode, Toast.LENGTH_SHORT).show();
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTimeInMillis, pendingIntent);
    }

    public void cancelAlarms(Context context) {

        // Cancel the main alarm
        cancelAlarm(context, timeInMillis,0);

        // Cancel alarms for 1 day, 1 week, and 1 hour before
        cancelAlarm(context, timeInMillis - 24 * 60 * 60 * 1000,1); // 1 day before
        cancelAlarm(context, timeInMillis - 7 * 24 * 60 * 60 * 1000,2); // 1 week before
        cancelAlarm(context, timeInMillis - 60 * 60 * 1000,3); // 1 hour before
    }
    public void cancelAlarm(Context context, long alarmTimeInMillis, int type) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        // Pass the event info needed to recreate PendingIntent
        intent.putExtra("eventClass", this);

        // Use a combination of uniqueId and requestCode to create a unique identifier
        int uniqueRequestCode = uniqueId * 1000 + type;

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, uniqueRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

        // Cancel the alarm
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(context, "Alarm canceled for " + uniqueRequestCode, Toast.LENGTH_SHORT).show();
        }
    }

    // Serializable implementation, may not ultimately need this tbd
    private static final long serialVersionUID = 1L;
}
