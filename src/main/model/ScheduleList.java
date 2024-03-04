package model;

import java.util.ArrayList;
import java.util.List;

//This class represents a list of schedules that can be accessed and modified
public class ScheduleList {

    private List<Schedule> list;

    //EFFECTS: create an empty ArrayList to hold schedules
    public ScheduleList() {
        list = new ArrayList<>();
    }

    //EFFECTS: prints names of every schedule in the lsit
    public String printList() {
        String output = "";
        for (int i = 0; i < list.size(); i++) {
            output += ((i + 1) + ": " + list.get(i).getTitle() + " (term " + list.get(i).getTerm() + ")\n");
        }
        System.out.println(output);
        return output;
    }

    //EFFECTS: returns true if list is empty
    public boolean isEmpty() {
        return list.size() == 0;
    }

    //EFFECTS: returns true if schedule was successfully removed and false otherwise
    //MODIFIES: this
    public boolean removeSchedule(String id) {
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            System.out.println("invalid input");
            return false;
        }
        if (Integer.parseInt(id) > list.size()) {
            return false;
        }
        return list.remove(list.get(Integer.parseInt(id) - 1));
    }

    public List<Schedule> getList() {
        return list;
    }

    public void addSchedule(Schedule s) {
        list.add(s);
    }

}
