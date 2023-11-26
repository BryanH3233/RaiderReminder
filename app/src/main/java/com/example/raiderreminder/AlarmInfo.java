package com.example.raiderreminder;

import android.app.PendingIntent;

import java.io.Serializable;

//Alarm notification related info class
//May want to merge this with the event class in some form
public class AlarmInfo implements Serializable{
    private long timeInMillis;
    private int requestCode;
    private PendingIntent pendingIntent;
    private boolean isSwitchOn;
    private String notificationMessage; // Add a new field

    public AlarmInfo(long timeInMillis, int requestCode, PendingIntent pendingIntent, String notificationMessage) {
        this.timeInMillis = timeInMillis;
        this.requestCode = requestCode;
        this.pendingIntent = pendingIntent;
        this.isSwitchOn = true; // Default to on
        this.notificationMessage = notificationMessage;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public PendingIntent getPendingIntent() {
        return pendingIntent;
    }

    public boolean isSwitchOn() {
        return isSwitchOn;
    }

    public void setSwitchOn(boolean switchOn) {
        isSwitchOn = switchOn;
    }

    // Serializable implementation
    private static final long serialVersionUID = 1L;
}