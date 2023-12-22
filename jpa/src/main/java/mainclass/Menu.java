package mainclass;

import crudServices.Create;
import crudServices.Delete;
import crudServices.Read;
import crudServices.Update;

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
                case 1 -> Create.course();
                case 2 -> Create.student();
                case 3 -> Create.teacher();
                case 4 -> Create.studentCourseGrade();
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
                    2. All student course grades
                    3. All grades for a course
                    4. All grades for a student
                    0. Back
                    """;
            System.out.println(readMenu);
            int menuChoice = UserInputHandler.menuInput(4);
            switch (menuChoice) {
                case 0 -> isRunning = false;
                case 1 -> Read.showAllCourses();
                case 2 -> Read.showAllStudentCourseGrades();
                case 3 -> Read.showStudentCourseGradesByCourseID();
                case 4 -> Read.showStudentCourseGradesByStudent();
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
                    1. Show number of different grades for a course
                    2. Show number of students for a course
                    0. Back
                    """;

            System.out.println(readStatisticsMenu);
            int menuChoice = UserInputHandler.menuInput(3);
            switch (menuChoice) {
                case 0 -> isRunning = false;
                case 1 -> Read.showGradeCountsByCourseID();
                case 2 -> Read.showStudentCountByCourseID();
                case 3 -> Read.showTotalStudentCount();
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
                case 1 -> Update.studentCourseGrade();
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
                    0. Quit
                    """;
        System.out.println(mainMenu);
    }

}