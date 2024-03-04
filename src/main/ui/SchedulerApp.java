package ui;

import model.Schedule;
import model.ScheduleList;

import java.util.Scanner;

//This class handles the running of the app. It stores the list of schedules and current schedule that is being
// worked on. The main loop of this program is a while loop in this class that prints possible actions for the user
// and processes the user's response in the console.
public class SchedulerApp {

    private DataParser dataParser;
    private ScheduleList scheduleList;
    private Schedule currentSchedule;
    private Scanner inputScanner;
    private boolean appRunning;

    //EFFECTS: runs the scheduler application
    public SchedulerApp() {
        dataParser = new DataParser();
        scheduleList = new ScheduleList();
        currentSchedule = null;
        appRunning = false;
        runApp();
    }

    //EFFECTS: prints prompts for user and processes user input
    //MODIFIES: this
    private void runApp() {
        inputScanner = new Scanner(System.in);

        appRunning = true;

        while (appRunning) {
            if (currentSchedule == null) {
                displayMainMenu();
                processInputMain(inputScanner.nextLine());
            } else {
                displayScheduleMenu();
                processInputSchedule(inputScanner.nextLine());
            }
        }
    }

    //EFFECTS: displays menu of options for user
    private void displayMainMenu() {
        if (!scheduleList.isEmpty()) {
            System.out.println("\nschedule list: ");
            scheduleList.printList();
            System.out.println("\ns -> select schedule to edit");
            System.out.println("r -> remove a schedule");
            System.out.println("c -> create new schedule");
            System.out.println("q -> quit");

        } else { // no schedules yet, can only create one or quit
            System.out.println("\nc -> create new schedule");
            System.out.println("q -> quit");
        }

    }

    //EFFECTS: displays menu of options for user
    private void displayScheduleMenu() {
        System.out.println("\n" + currentSchedule.getTitle() + " (term " + currentSchedule.getTerm() + ")");
        currentSchedule.printCourseList();
        System.out.println("\na -> add course by id");
        System.out.println("r -> remove a course");
        System.out.println("g -> generate schedule");
        System.out.println("b -> back to schedule list");
        System.out.println("q -> quit");
    }

    //EFFECTS: processes user input
    //MODIFIES: this
    private void processInputMain(String in) {
        if (in.equals("q")) {
            System.out.println("quitting application");
            appRunning = false;
        } else if (in.equals("s")) { //selecting
            System.out.println("\nenter schedule number to select: ");
            processInputScheduleList(inputScanner.nextLine(), in);
        } else if (in.equals("r")) { //removing
            System.out.println("\nenter schedule number to remove: ");
            processInputScheduleList(inputScanner.nextLine(), in);
        } else if (in.equals("c")) { //creating
            System.out.println("\nenter schedule name to create: ");
            processInputScheduleList(inputScanner.nextLine(), in);
        } else {
            System.out.println("invalid input");
        }
    }

    //EFFECTS: processes user input
    //MODIFIES: this
    private void processInputScheduleList(String in, String operation) {
        if (operation.equals("s")) { //selecting schedule from list
            try {
                currentSchedule = scheduleList.getList().get(Integer.parseInt(in) - 1);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("schedule at index: " + in + " does not exist");
            } catch (NumberFormatException e) {
                System.out.println("invalid input");
            }
        } else if (operation.equals("r")) { //removing schedule from list
            if (scheduleList.removeSchedule(in)) {
                System.out.println("schedule at index: " + in + " was removed");
            } else {
                System.out.println("schedule at index: " + in + " does not exist");
            }
        } else { //operation is "c", creating new schedule
            System.out.println("which term (1 or 2):");
            String next = inputScanner.nextLine();

            if (next.equals("1") || next.equals("2")) {
                scheduleList.addSchedule(new Schedule(in, Integer.parseInt(next)));
            } else {
                System.out.println("invalid term");
            }
        }
    }

    //EFFECTS: processes user input
    //MODIFIES: this
    private void processInputSchedule(String in) {
        if (in.equals("q")) {
            System.out.println("quitting application");
            appRunning = false;
        } else if (in.equals("b")) {
            currentSchedule = null;
        } else if (in.equals("a")) {
            System.out.println("enter course id: ");
            processInputAddCourse(formatCourseInput(inputScanner.nextLine()));
        } else if (in.equals("r")) {
            System.out.println("enter index to remove: ");
            processInputRemoveCourse(inputScanner.nextLine());
        } else if (in.equals("g")) {
            currentSchedule.generateSchedule();
        } else {
            System.out.println("invalid input");
        }
    }

    private void processInputAddCourse(String in) {
        if (dataParser.checkCourse(in, currentSchedule.getTerm())) {
            currentSchedule.addCourse(in);
        }
    }

    private void processInputRemoveCourse(String in) {
        if (currentSchedule.removeCourse(in)) {
            System.out.println("course at index: " + in + " was removed");
        } else {
            System.out.println("course at index: " + in + " does not exist");
        }
    }

    //EFFECTS: formats input to be all upper case and adds space between department and course number if needed
    private String formatCourseInput(String in) {
        String s = in.toUpperCase();
        //add space between department and class number if it is missing
        if (s.length() > 5 && Character.isLetter(s.charAt(3)) && Character.isDigit(s.charAt(4))) {
            s = s.substring(0,4) + " " + s.substring(4, s.length());
        }
        return s;
    }

}
