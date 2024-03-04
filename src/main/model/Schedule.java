package model;

import ui.DataParser;

import java.util.ArrayList;
import java.util.List;

// This class represents a schedule with a title, a list of course names (meant as a temporary list to eventually
// be put through timetable generation, and a list that will store the chosen sections for those courses after the
// schedule-generating algorithm is run.
public class Schedule {
    private String title; // name of schedule
    private int term; //term 1 or 2
    private List<String> courses; // selected courses
    private List<Section> schedule; // list of chosen sections after schedule generation

    //EFFECTS: create a schedule with given title and term, and with a blank course and section list.
    // throws InvalidTermException if term is a number other than 1 or 2
    // throws EmptyNameException if name is an empty string ""
    public Schedule(String title, int term) {
        this.title = title;
        this.term = term;
        courses = new ArrayList<>();
        schedule = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: Adds course with given ID to course list if it is not already on the list. If already on list, do
    // nothing except print a reminder.
    public void addCourse(String id) {
        if (!containsCourse(id)) {
            courses.add(id);
            System.out.println("added course: " + id + " to list");
        } else {
            System.out.println("schedule already contains this course");
        }
    }

    //EFFECTS: prints each course name in the list on its own line
    public String printCourseList() {
        String output = "";
        for (int i = 0; i < courses.size(); i++) {
            output += ((i + 1) + ": " + courses.get(i) + "\n");
        }
        System.out.println(output);
        return output;
    }

    //EFFECTS: checks to see if the list already contains a given course
    public boolean containsCourse(String id) {
        for (String course : courses) {
            if (course.equals(id)) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: returns true if course was successfully removed and false otherwise
    //MODIFIES: this
    public boolean removeCourse(String id) {
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            System.out.println("invalid input");
            return false;
        }
        if (Integer.parseInt(id) > courses.size()) {
            return false;
        }
        return courses.remove(courses.get(Integer.parseInt(id) - 1));
    }

    //MODIFIES: this
    //EFFECTS: uses a backtracking algorithm to generate a valid schedule with section data from the currently
    // selected courses. if a valid schedule exists, set schedule to the result and print it
    public void generateSchedule() {
        DataParser dp = new DataParser();

        List<List<Section>> allCourseSections = new ArrayList<>();

        //create list of all sections for each course
        for (String s : courses) {
            List<String> activities = dp.getCourseActivities(s, term); //first find all necessary activities for course

            for (String a : activities) { //then add a list of sections for each activity to the main list
                List<Section> courseSections = dp.getCourseSections(s, a, term);
                allCourseSections.add(courseSections);
            }
        }

        List<Section> generated = generateSchedule(allCourseSections, new ArrayList<>());

        if (generated != null) {
            schedule = generated;
            System.out.println("Schedule successfully generated!");
            for (Section s : schedule) {
                s.printSectionData();
            }
        } else {
            System.out.println("No possible configuration of these courses exists.");
        }
    }

    //recursive helper function for generateSchedule
    public List<Section> generateSchedule(List<List<Section>> main, List<Section> current) {
        if (current.size() == main.size()) { //complete
            return new ArrayList<>(current);
        }
        for (Section s : main.get(current.size())) {
            if (!checkConflicts(s, current)) {
                List<Section> updated = new ArrayList<>(current);
                updated.add(s);

                List<Section> next = generateSchedule(main, updated);

                if (next != null) {
                    return next;
                }
            }
        }
        return null;
    }

    //EFFECTS: returns true if there is overlap between a given new section and given schedule
    public boolean checkConflicts(Section newSection, List<Section> schedule) {
        for (Section s : schedule) {
            if (checkDayOverlap(s, newSection)) {
                double s1 = s.getStartTimeAsNumber();
                double e1 = s.getEndTimeAsNumber();
                double s2 = newSection.getStartTimeAsNumber();
                double e2 = newSection.getEndTimeAsNumber();
                if (s1 <= s2 && e1 > s2 || s2 <= s1 && e2 > s1) { //detect if there is overlap in the sections
                    return true;
                }
            }
        }
        //case 1: s1 e1 s2 e2 //false
        //case 2:  s1 s2 e1 e2 //true
        //case 3: s1 s2 e2 e1 //true
        //case 4:  s2 e2 s1 e1 //false
        //case 5: s2 s1 e2 e1 //true
        //case 6: s2 s1 e1 e2 //true
        return false;
    }

    //EFFECTS: returns true if there is an overlap in days for 2 given sections
    public boolean checkDayOverlap(Section s1, Section s2) {
        List<String> s1days = new ArrayList<>();
        for (int i = 0; i < s1.getDaysOfWeek().length(); i += 4) {
            s1days.add(s1.getDaysOfWeek().substring(i, i + 3));
        }
        List<String> s2days = new ArrayList<>();
        for (int i = 0; i < s2.getDaysOfWeek().length(); i += 4) {
            s2days.add(s2.getDaysOfWeek().substring(i, i + 3));
        }

        for (String s1day : s1days) {
            for (String s2day : s2days) {
                if (s1day.equals(s2day)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getTitle() {
        return title;
    }

    public int getTerm() {
        return term;
    }

    public List<String> getCourseList() {
        return courses;
    }

    public List<Section> getSchedule() {
        return schedule;
    }


}
