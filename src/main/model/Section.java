package model;

public class Section {
    private String courseID;
    private String sectionID;
    private String activity;
    private String startTime;
    private String endTime;
    private String daysOfWeek;

    //EFFECTS: represents a section of a certain class, created by schedule generation algorithm from course data file,
    // not to be created by users.
    // information stored in each section is its course, specific section ID, the days of the week it is held on, and
    // start and end times
    public Section(String courseID, String sectionID, String activity,
                   String daysOfWeek, String startTime, String endTime) {
        this.courseID = courseID;
        this.sectionID = sectionID;
        this.activity = activity;
        this.daysOfWeek = daysOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    //EFFECTS: prints formatted section data
    public void printSectionData() {
        System.out.println(courseID + " " + sectionID + " (" + activity + ")");
        System.out.println(daysOfWeek + ", " + startTime + "-" + endTime);
    }

    //EFFECTS: parses startTime string a returns a double representing the time in hours
    public double getStartTimeAsNumber() {
        if (startTime.length() == 4) { //hour is only 1 digit
            return Integer.parseInt(startTime.substring(0,1)) + Double.parseDouble(startTime.substring(2,4)) / 60;
        } else {
            return Integer.parseInt(startTime.substring(0,2)) + Double.parseDouble(startTime.substring(3,5)) / 60;
        }
    }

    //EFFECTS: parses endTime string a returns a double representing the time in hours
    public double getEndTimeAsNumber() {
        if (endTime.length() == 4) { //hour is only 1 digit
            return Integer.parseInt(endTime.substring(0,1)) + Double.parseDouble(endTime.substring(2,4)) / 60;
        } else {
            return Integer.parseInt(endTime.substring(0,2)) + Double.parseDouble(endTime.substring(3,5)) / 60;
        }
    }



    public String getCourseID() {
        return courseID;
    }

    public String getSectionID() {
        return sectionID;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }
}
