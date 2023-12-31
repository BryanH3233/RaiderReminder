package com.example.raiderreminder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
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

    // Remove events that have already passed
    public static void removeExpiredEvents() {
        Calendar currentCalendar = Calendar.getInstance();

        // Use an iterator to safely remove elements while iterating
        Iterator<eventClass> iterator = events.iterator();
        while (iterator.hasNext()) {
            eventClass event = iterator.next();

            // Check if the event has already passed
            if (event.task.before(currentCalendar)) {
                iterator.remove(); // Remove the expired event
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

