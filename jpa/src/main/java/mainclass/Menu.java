package mainclass;

import crud.Create;
import crud.Delete;
import crud.Read;
import crud.Update;
import crudServices.CreateService;

public class Menu {

    static void create() {
        boolean isRunning = true;
        while (isRunning) {
            String createMenu = """
                    Register
                    --------------
                    1. New course
                    2. New student
                    3. New teacher
                    4. New student course grade
                    0. Back
                    """;
            System.out.println(createMenu);
            int menuChoice = UserInputHandler.menuInput(4);
            switch (menuChoice) {
                case 0 -> isRunning = false;
                case 1 -> CreateService.course();
                case 2 -> CreateService.student();
                case 3 -> CreateService.teacher();
                case 4 -> CreateService.studentCourseGrade();
            }
            UserInputHandler.pressEnterToContinue(menuChoice);
        }
    }

     static void read() {
        boolean isRunning = true;
        while (isRunning) {
            String readMenu = """
                    Show
                    -------------------
                    1. All courses
                    2. Student grades for a course
                    3. Show total amount of students
                    0. Back
                    """;
            System.out.println(readMenu);
            int menuChoice = UserInputHandler.menuInput(3);
            switch (menuChoice) {
                case 0 -> isRunning = false;
                case 1 -> Read.showCourses();
                case 2 -> Read.showStudentCourseGradesByCourseId();
                case 3 -> Read.totalAmountOfStudents();
            }
            UserInputHandler.pressEnterToContinue(menuChoice);
        }
    }

    static void count() {
        boolean isRunning = true;
        while (isRunning) {
            String readStatisticsMenu = """
                    Statistics
                    -------------------------
                    1. Show grades per course
                    2. Show students per course
                    0. Back
                    """;

            System.out.println(readStatisticsMenu);
            int menuChoice = UserInputHandler.menuInput(2);
            switch (menuChoice) {
                case 0 -> isRunning = false;
                case 1 -> Read.printGradeCountsByCourseId();
                case 2 -> Read.printStudentsCountByCourseId();
            }
            UserInputHandler.pressEnterToContinue(menuChoice);
        }
    }

    static void update() {
        boolean isRunning = true;
        while (isRunning) {
            String updateMenu = """
                    Update
                    ------------------------
                    1. Grades
                    2. Student name
                    3. Add teacher to course
                    0. Back
                    """;
            System.out.println(updateMenu);
            int menuChoice = UserInputHandler.menuInput(3);
            switch (menuChoice) {
                case 0 -> isRunning = false;
                case 1 -> Update.grades();
                case 2 -> Update.studentName();
                case 3 -> Update.courseTeacher();
            }
            UserInputHandler.pressEnterToContinue(menuChoice);
        }
    }

    static void delete() {
        boolean isRunning = true;
        while (isRunning) {
            String deleteMenu = """
                    Remove
                    ----------
                    1. Student
                    2. Teacher
                    3. Course
                    0. Back
                    """;
            System.out.println(deleteMenu);
            int menuChoice = UserInputHandler.menuInput(3);
            switch (menuChoice) {
                case 0 -> isRunning = false;
                case 1 -> Delete.student();
                case 2 -> Delete.teacher();
                case 3 -> Delete.course();
            }
            UserInputHandler.pressEnterToContinue(menuChoice);
        }
    }

    public static void showMain(){
        String mainMenu="""
                    Main menu
                    -----------------------------------
                    1. Register course, student etc.
                    2. Show courses, student grades etc.
                    3. Show statistics
                    4. Update existing data
                    5. Remove
                    6. Test
                    0. Quit
                    """;
        System.out.println(mainMenu);
    }
    public static String showMainTwo(){
        String mainMenu="""
                    Main menu
                    -----------------------------------
                    1. Register course, student etc.
                    2. Show courses, student grades etc.
                    3. Show statistics
                    4. Update existing data
                    5. Remove
                    6. Test
                    0. Quit
                    """;
        return mainMenu;
    }




    //java fx
    public static String readTwo(){
        String readMenu = """
                    Show
                    -------------------
                    1. All courses
                    2. Student grades for a course
                    3. Show total amount of students
                    0. Back
                    """;
        return readMenu;
    }
}