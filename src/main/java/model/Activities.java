package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Activities {
    private List<MonitoredData> data;
    private final String fileName = "Activities.txt";

    public Activities(){
        data = new ArrayList<MonitoredData>();
    }

    public void readData(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            data = stream.map(d -> {
                try {
                    return new MonitoredData(dateFormat.parse(d.split("\t\t")[0]),
                            dateFormat.parse(d.split("\t\t")[1]),
                            d.split("\t\t")[2].trim());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder content = new StringBuilder();
        for (MonitoredData monitoredData: data)
            content.append(monitoredData.toString()).append('\n');

        writeFile("TASK_1.txt", content.toString());
    }

    public void countDays() {
        Set<Date> days = data.stream().map(d -> {
            try {
                return d.getDay();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toSet());
        writeFile("TASK_2.txt", "Number of days is: " + String.valueOf(days.size()));
    }

    public void countActivities() {
        Map<String, Long> activities = data.stream().collect(Collectors.groupingBy(MonitoredData::getActivity, Collectors.counting()));

        StringBuilder builder = new StringBuilder();
        builder.append("Number of times each activity appeared:\n");
        activities.forEach((key, value) -> builder.append(key + ": " + value + " times" + "\n"));

        writeFile("TASK_3.txt", builder.toString());
    }

    public void countActivitiesPerDay() {
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<Date, Map<String, Long>> activities = data.stream().collect(Collectors.groupingBy(
                t -> {
                    try {
                        return t.getDay();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return null;
                },
                Collectors.groupingBy(MonitoredData::getActivity, Collectors.counting())));

        StringBuilder builder = new StringBuilder();
        builder.append("Each day activities\n");
        activities.forEach((key, value) -> builder.append(dFormat.format(key) + " activities: " + value + "\n"));

        writeFile("TASK_4.txt", builder.toString());
    }

    //task5
    public void countTotalDuration() {
        Map<String, Long> result = data.stream().collect(Collectors.groupingBy(MonitoredData::getActivity,
                Collectors.summingLong(MonitoredData::getDiff)));

        StringBuilder builder = new StringBuilder();
        builder.append("Duration of each activity\n");

        result.forEach((key, value) -> builder.append(key + " " + value + " minutes\n"));

        writeFile("TASK_5.txt", builder.toString());

    }

    //task6
    public void frequency() {
        Map<String, Long> freq = data.stream().collect(Collectors.groupingBy(MonitoredData::getActivity, Collectors.mapping(MonitoredData::getDiff,
                Collectors.summingLong(d->{
                    if(d<5)
                        return 1;
                    else
                        return 0;}))));
        Map<String, Long> activities = data.stream().collect(Collectors.groupingBy(MonitoredData::getActivity, Collectors.counting()));

        StringBuilder builder = new StringBuilder();
        builder.append("Activities:\n");

        List<String> names = new ArrayList<>(freq.keySet());
        List<Long> favorable = new ArrayList<> (freq.values());
        List<Long> total = new ArrayList<>(activities.values());

        for(int i = 0; i < freq.size(); i++) {
            if((double) favorable.get(i)/total.get(i) >= 0.9) {
                builder.append(names.get(i) + "\n");
            }
        }
        writeFile("TASK_6.txt", builder.toString());
    }

    private void writeFile(String fileName, String content){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File(fileName));
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
