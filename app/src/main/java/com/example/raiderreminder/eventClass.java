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
    private String eventName = ""; // event name
    private int year = 0;
    private int month = 0;
    private int day = 0;
    private int hour = 0; // military time
    private int minute = 0;
    private int second = 0;
    private String location = "None";
    private String notificationMessage = eventName + " at " + year + "/" + month + "/" + day + " "
            + hour + ":" + minute + ", in " + location; //message for notification
    private int requestCode = (int) System.currentTimeMillis();
    private PendingIntent pendingIntent;
    // description maybe?
    Calendar task = Calendar.getInstance();
    private long timeInMillis = 0; //format used in setting timed notifications
    // create a timezone

    // public methods

    // creates a sets the time for a calendar object
    public void createCalEvent(){
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setAlarm(Context context) {
        // Get time & date data from event
        // Set the alarm to trigger at the specified date and time
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("eventClass", this); // Appends alarm info (could be event info?) to intent
        pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        // Cancel any existing alarms with the same requestCode
        alarmManager.cancel(pendingIntent);

        // Set the new alarm
        Toast.makeText(context, "Alarm set for" + getTimeInMillis(),Toast.LENGTH_SHORT).show();
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
        //} else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
       // }
    }
    // Serializable implementation, may not ultimately need this tbd
    private static final long serialVersionUID = 1L;
}
