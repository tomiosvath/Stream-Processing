package main;

import model.Activities;

public class Main {
    public static void main(String[] args) {
        Activities activities = new Activities();
        activities.readData();
        activities.countDays();
        activities.countActivities();
        activities.countActivitiesPerDay();
        activities.countTotalDuration();
        activities.frequency();
    }
}
