package ui;

import model.Section;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// This class uses the json.simple library in order to search the local json file course_data which contains
// information about UBC classes. The course IDs are stored in a JSONArray coursesArray which can then be checked
// to verify if a given course exists.
public class DataParser {
    private final String filePath = "data/course_data.json";

    private JSONArray coursesArray;

    //EFFECTS: attempt to read local json file according to filePath constant and create a JSONArray with the data
    // if successful
    public DataParser() {
        try {
            readCoursesArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    //EFFECTS: check if a course with given id exists in data file
    public boolean checkCourse(String id, int term) {
        for (int i = 0; i < coursesArray.size(); i++) {
            JSONObject course = (JSONObject) coursesArray.get(i);

            if (course.get("id").equals(id)) {
                JSONArray sectionArray = (JSONArray) course.get("sections");
                for (int j = 0; j < sectionArray.size(); j++) {
                    JSONObject sectionData = (JSONObject) sectionArray.get(j);
                    if (sectionData.get("term").equals("1-2") || sectionData.get("term").equals(String.valueOf(term))) {
                        return true; // check that given course has a section in the right term
                    }
                }
                System.out.println("course does not have any sections for this term");
                return false;
            }
        }
        System.out.println("course with given id does not exist");
        return false;
    }

    //EFFECTS: return a list of all sections for a given class, section type, and term
    public List<Section> getCourseSections(String id, String activity, int term) {
        List<Section> sectionList = new ArrayList<>();
        for (int i = 0; i < coursesArray.size(); i++) {
            JSONObject course = (JSONObject) coursesArray.get(i);

            if (course.get("id").equals(id)) {
                JSONArray sectionArray = (JSONArray) course.get("sections");
                for (int j = 0; j < sectionArray.size(); j++) {
                    JSONObject sectionData = (JSONObject) sectionArray.get(j);
                    if ((sectionData.get("term").equals("1-2")
                            || Integer.parseInt((String) sectionData.get("term")) == term)
                            && sectionData.get("activity").equals(activity)) {
                        Section newSection = new Section(id, (String) sectionData.get("section"),
                                (String) sectionData.get("activity"), (String) sectionData.get("days"),
                                (String) sectionData.get("start"), (String) sectionData.get("end"));
                        sectionList.add(newSection);
                    }
                }
            }
        }
        return sectionList;
    }

    //EFFECTS: return a list of all section types for a given course
    public List<String> getCourseActivities(String id, int term) {
        List<String> activities = new ArrayList<>();
        for (int i = 0; i < coursesArray.size(); i++) {
            JSONObject course = (JSONObject) coursesArray.get(i);

            if (course.get("id").equals(id)) {
                JSONArray sectionArray = (JSONArray) course.get("sections");
                for (int j = 0; j < sectionArray.size(); j++) {
                    JSONObject sectionData = (JSONObject) sectionArray.get(j);
                    if ((sectionData.get("term").equals("1-2")
                            || Integer.parseInt((String) sectionData.get("term")) == term)) {
                        String activity = (String) sectionData.get("activity");
                        if (!activities.contains(activity) && !activity.equals("Waiting List")) {
                            activities.add(activity);
                        }
                    }
                }
            }
        }
        return activities;
    }

    public void findAllActivities() {
        List<String> activities = new ArrayList();
        for (int i = 0; i < coursesArray.size(); i++) {
            JSONObject course = (JSONObject) coursesArray.get(i);
            JSONArray sectionArray = (JSONArray) course.get("sections");
            for (int j = 0; j < sectionArray.size(); j++) {
                JSONObject sectionData = (JSONObject) sectionArray.get(j);
                String act = (String) sectionData.get("activity");
                if (!activities.contains(act)) {
                    activities.add(act);
                }
            }
        }

        for (String s : activities) {
            System.out.println(s);
        }
    }

    //MODIFIES: this
    //EFFECTS: finds course data json file from given path and creates a JSONObject
    public void readCoursesArray() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader(filePath);
        Object obj = parser.parse(reader);
        JSONObject jsonobj = (JSONObject)obj;

        coursesArray = (JSONArray) jsonobj.get("courses");
    }
}
