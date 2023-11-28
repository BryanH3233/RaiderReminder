package com.example.raiderreminder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class EventManager {
    private static List<eventClass> events;  // Static field

    private static void sortEvents() {
        Collections.sort(events, new Comparator<eventClass>() {
            @Override
            public int compare(eventClass event1, eventClass event2) {
                // Compare events based on date and time
                // You may need to implement this comparison based on your requirements
                // For simplicity, I'm assuming events with earlier date and time are "smaller"
                if (event1.getYear() != event2.getYear()) {
                    return Integer.compare(event1.getYear(), event2.getYear());
                } else if (event1.getMonth() != event2.getMonth()) {
                    return Integer.compare(event1.getMonth(), event2.getMonth());
                } else if (event1.getDay() != event2.getDay()) {
                    return Integer.compare(event1.getDay(), event2.getDay());
                } else if (event1.getHour() != event2.getHour()) {
                    return Integer.compare(event1.getHour(), event2.getHour());
                } else {
                    return Integer.compare(event1.getMinute(), event2.getMinute());
                }
            }
        });
    }

    static {
        events = new ArrayList<>();  // Static initializer block
    }

    public static void addEventToList(eventClass event) {
        events.add(event);
        sortEvents();
    }

    public static void removeEventFromList(String eventName) {
        for (Iterator<eventClass> iterator = events.iterator(); iterator.hasNext(); ) {
            eventClass event = iterator.next();
            if (event.getName().equals(eventName)) {
                iterator.remove();
                // Assuming each event has a unique name, you can break out of the loop once found
                break;
            }
        }
    }
    // New method to get events for a specific day
    // used in calenderFragment
    public static ArrayList<eventClass> getEventsForDay(LocalDate date) {
        ArrayList<eventClass> eventsForDay = new ArrayList<>();
        for (eventClass event : events) {
            if (event.getYear() == date.getYear() &&
                    event.getMonth() == date.getMonthValue() &&
                    event.getDay() == date.getDayOfMonth()) {
                eventsForDay.add(event);
            }
        }
        return eventsForDay;
    }

    // Getter method to retrieve the list of events
    public static List<eventClass> getEvents() {
        return events;
    }
}

