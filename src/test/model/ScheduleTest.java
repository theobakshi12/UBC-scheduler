package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {
    private Schedule testSchedule;

    @BeforeEach
    public void setup() {
        testSchedule = new Schedule("computer science schedule", 1);
    }

    @Test
    public void addCourseTest() {
        testSchedule.addCourse("CPSC 210");
        testSchedule.addCourse("CPSC 221");
        assertEquals("CPSC 210", testSchedule.getCourseList().get(0));
        testSchedule.addCourse("CPSC 221");
        assertEquals(2, testSchedule.getCourseList().size());
    }

    @Test
    public void containsCourseTest() {
        testSchedule.addCourse("CPSC 310");
        testSchedule.addCourse("CPSC 427");
        assertFalse(testSchedule.containsCourse("CPSC 110"));
        assertTrue(testSchedule.containsCourse("CPSC 310"));
    }

    @Test
    public void printCourseListTest() {
        testSchedule.addCourse("CPSC 110");
        assertEquals("1: CPSC 110\n", testSchedule.printCourseList());
        testSchedule.addCourse("CPSC 121");
        assertEquals("1: CPSC 110\n2: CPSC 121\n", testSchedule.printCourseList());
    }

    @Test
    public void checkConflictsTest() {
        List<Section> testList = new ArrayList<>();
        Section s1 = new Section("", "", "", "Mon Wed Fri", "11:00", "12:30");
        Section s2 = new Section("", "", "","Tue Thu", "13:30", "15:00");
        testList.add(s1);
        testList.add(s2);

        Section s3 = new Section("", "","", "Mon Wed Fri",
                "09:00", "11:30"); //overlaps start time of s1, true
        Section s4 = new Section("", "","", "Mon Wed Fri",
                "11:30", "12:00"); //overlaps middle of s1, true
        Section s5 = new Section("", "","", "Mon Wed Fri",
                "12:00", "14:00"); //overlaps end of s1, true
        Section s6 = new Section("", "","", "Mon Wed Fri",
                "13:00", "15:30"); //after s1, false
        Section s7 = new Section("", "", "","Tue Thu",
                "10:00", "11:30"); //overlaps s1 but wrong days, false
        Section s8 = new Section("", "", "","Mon Wed Fri",
                "10:00", "11:00"); //ends at exact start time of s1, false
        Section s9 = new Section("", "", "","Mon Wed Fri",
                "10:00", "11:01"); //ends one minute past start time of s1, true
        Section s10 = new Section("", "", "","Mon Wed Fri",
                "12:30", "14:00"); //starts at exact end time of s1, false
        Section s11 = new Section("", "", "","Tue Thu",
                "12:30", "17:00"); //completely covers s2, true
        Section s12 = new Section("", "", "","Tue Thu",
                "13:30", "15:00"); //exactly the same as s2, true

        assertTrue(testSchedule.checkConflicts(s3, testList));
        assertTrue(testSchedule.checkConflicts(s4, testList));
        assertTrue(testSchedule.checkConflicts(s5, testList));
        assertFalse(testSchedule.checkConflicts(s6, testList));
        assertFalse(testSchedule.checkConflicts(s7, testList));
        assertFalse(testSchedule.checkConflicts(s8, testList));
        assertTrue(testSchedule.checkConflicts(s9, testList));
        assertFalse(testSchedule.checkConflicts(s10, testList));
        assertTrue(testSchedule.checkConflicts(s11, testList));
        assertTrue(testSchedule.checkConflicts(s12, testList));

    }

    @Test
    public void checkDayOverlapTest() {
        Section s1 = new Section("", "", "", "Mon Wed Fri", "11:00", "12:30");
        Section s2 = new Section("", "", "","Tue Thu", "13:30", "15:00");
        Section s3 = new Section("", "", "","Mon", "13:30", "15:00");
        Section s4 = new Section("", "", "","Fri", "13:30", "15:00");
        Section s5 = new Section("", "", "","Thu Wed Fri", "13:30", "15:00");

        assertFalse(testSchedule.checkDayOverlap(s1,s2));
        assertTrue(testSchedule.checkDayOverlap(s1,s3));
        assertTrue(testSchedule.checkDayOverlap(s1,s4));
        assertTrue(testSchedule.checkDayOverlap(s1,s5));
        assertTrue(testSchedule.checkDayOverlap(s2,s5));
    }

    @Test
    public void generateScheduleTest() {
        testSchedule.addCourse("CPSC 121");
        testSchedule.generateSchedule();
        assertEquals("101", testSchedule.getSchedule().get(0).getSectionID());

        testSchedule.addCourse("CPSC 110");
        testSchedule.generateSchedule();
        assertEquals("101", testSchedule.getSchedule().get(0).getSectionID());
        assertEquals("102", testSchedule.getSchedule().get(3).getSectionID());


    }

}