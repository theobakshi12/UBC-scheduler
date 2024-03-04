package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleListTest {
    private ScheduleList testList;
    private Schedule s1;
    private Schedule s2;

    @BeforeEach
    public void setup() {
        testList = new ScheduleList();
        s1 = new Schedule("my schedule", 2);
        s2 = new Schedule("cs schedule", 1);

    }

    @Test
    public void isEmptyTest() {
        assertTrue(testList.isEmpty());
        testList.addSchedule(s1);
        assertFalse(testList.isEmpty());
    }

    @Test
    public void printListTest() {
        testList.addSchedule(s1);
        assertEquals("1: my schedule (term 2)\n", testList.printList());
        testList.addSchedule(s2);
        assertEquals("1: my schedule (term 2)\n2: cs schedule (term 1)\n", testList.printList());
    }

    @Test
    public void removeScheduleTest() {
        testList.addSchedule(s1);
        testList.addSchedule(s2);
        assertFalse(testList.removeSchedule("3"));
        assertTrue(testList.removeSchedule("2"));
        assertEquals(s1, testList.getList().get(0));
        assertFalse(testList.removeSchedule("2"));
        assertTrue(testList.removeSchedule("1"));
    }
}
