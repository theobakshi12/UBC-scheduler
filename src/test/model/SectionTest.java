package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SectionTest {
    private Section test1;
    private Section test2;

    @BeforeEach
    public void setup() {
        test1 = new Section("CPSC 100", "101", "","Tue Thu",
                "8:30", "9:00");
        test2 = new Section("CPSC 100", "101", "","Tue Thu",
                "12:15", "21:30");
    }

    @Test
    public void getStartTimeAsNumberTest() {
        assertEquals(8.5, test1.getStartTimeAsNumber());
        assertEquals(12.25, test2.getStartTimeAsNumber());
    }

    @Test
    public void getEndTimeAsNumberTest() {
        assertEquals(9.0, test1.getEndTimeAsNumber());
        assertEquals(21.5, test2.getEndTimeAsNumber());
    }


}
