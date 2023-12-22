package crudServices;

import classes.*;
import mainclass.UserInputHandler;
import repositories.*;

import java.util.List;

public class Read {

    public static void showAllTeachers() {
        List<Teacher> teachers = TeacherRepository.getAllTeachers();
        if (teachers.isEmpty()) {
            System.out.println("No teachers found.");
            return;
        }
        String format = "%-20s%s%n";
        System.out.printf(format, "ID:", "Teacher:");
        System.out.printf(format, "--", "--------");
        for (Teacher t : teachers) System.out.printf(format, t.getId(), t.getFirstName() + " " + t.getLastName());
    }

    public static void showAllStudents() {
        List<Student> students = StudentRepository.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        String format = "%-15s%-20s%s%n";
        System.out.printf(format, "ID:", "First name:", "Last name:");
        System.out.printf(format, "--", "----------", "---------");
        for (Student s : students) {
            System.out.printf(format, s.getId(), s.getFirstName(), s.getLastName());
        }
    }

    public static void showAllCourses() {
        List<Object[]> resultList = CourseRepository.getAllObjectsInCourses();
        if (resultList.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        String format = "%-10s%-20s%-30s%s%n";
        System.out.printf(format, "ID:", "Course:", "Teacher:", "Classroom:");
        System.out.printf(format, "--", "------", "---------", "----------");
        for (Object[] result : resultList) {
            Course course = (Course) result[0];
            Teacher teacher = (Teacher) result[1];
            Classroom classroom = (Classroom) result[2];
            if (course != null) System.out.printf("%-10d%-20s", course.getId(), course.getName());
            if (teacher != null) System.out.printf("%-30s", teacher.getFirstName() + " " + teacher.getLastName());
            else System.out.printf("%-30s", "N/A");
            if (classroom != null) System.out.printf("%s%n", classroom.getClassroomName());
            else System.out.printf("%s%n", "N/A");
        }
    }

    public static void showAllGrades() {
        List<Grade> grades = GradeRepository.getAllGrades();
        if (grades.isEmpty()) {
            System.out.println("No grades found.");
            return;
        }
        String format = "%-20s%s%n";
        System.out.printf(format, "ID:", "Grade:");
        System.out.printf(format, "--", "----");
        for (Grade g : grades) System.out.printf(format, g.getId(), g.getName());
    }

    public static void showAllClassrooms() {
        List<Classroom> classrooms = ClassroomRepository.getAllClassrooms();
        if (classrooms.isEmpty()) {
            System.out.println("No classrooms found.");
            return;
        }
        String format = " %-20s%-20s%s%n";
        System.out.printf(format, "ID:", "Name:", "Capacity:");
        System.out.printf(format, "--", "----", "--------");
        for (Classroom c : classrooms)
            System.out.printf(format, c.getClassroomId(), c.getClassroomName(), c.getClassroomCapacity());
    }

    public static void showAllStudentCourseGrades() {
        List<StudentCourseGrade> studentCourseGrades = StudentCourseGradeRepository.getAllStudentCourseGrades();
        if (studentCourseGrades.isEmpty()) {
            System.out.println("No student course grades found.");
            return;
        }
        String format = "%-20s%-10s%s%n";
        System.out.printf(format, "Course:", "Grade:", "Student");
        System.out.printf(format, "-------", "----", "----------------");
        for (StudentCourseGrade scg : studentCourseGrades) {
            System.out.printf(format,
                    scg.getCourse().getName(),
                    scg.getGrade().getName(),
                    scg.getStudent().getFirstName() + " " + scg.getStudent().getLastName());
        }
    }

    public static void showStudentCourseGradesByStudent() {
        Student student = null;
        while (student == null) {
            showAllStudents();
            System.out.println("Which student (ID) do you want to show grades for?");
            int studentID = UserInputHandler.readIntInput();
            student = StudentRepository.getStudentByID(studentID);
            if (student == null)
                System.out.println("\nError: Student with student ID " + studentID + " not found. Please try again.\n");
        }
        List<StudentCourseGrade> studentCourseGrades = StudentCourseGradeRepository.getStudentCourseGradesByStudentID(student.getId());
        if (studentCourseGrades.isEmpty()) {
            System.out.println("No grades found for the student.");
            return;
        }
        printStudentCourseGradesByStudent(studentCourseGrades);
    }

    public static void showStudentCourseGradesByStudent(int studentID) {
        List<StudentCourseGrade> studentCourseGrades = StudentCourseGradeRepository.getStudentCourseGradesByStudentID(studentID);
        if (studentCourseGrades.isEmpty()) {
            System.out.println("No grades found for the student.");
            return;
        }
        printStudentCourseGradesByStudent(studentCourseGrades);
    }

    private static void printStudentCourseGradesByStudent(List<StudentCourseGrade> studentCourseGrades) {
        String format = "%-10s%-20s%s%n";
        System.out.printf(format, "ID:", "Course:", "Grade:");
        System.out.printf(format, "--", "----", "-----");
        studentCourseGrades.forEach((e) -> System.out.printf(
                format, e.getCourse().getId(),
                e.getCourse().getName(),
                e.getGrade().getName()
        ));
    }

    public static void showStudentCourseGradesByCourseID() {
        Course course = null;
        while (course == null) {
            showAllCourses();
            System.out.println("Which course (ID) do you want to show grades for?");
            int courseID = UserInputHandler.readIntInput();
            course = CourseRepository.getCourseByID(courseID);
            if (course == null)
                System.out.println("\nError: Course with ID " + courseID + " not found. Please try again:\n");
        }
        List<StudentCourseGrade> studentCourseGrades = StudentCourseGradeRepository.getStudentCourseGradesByCourseID(course.getId());
        if (studentCourseGrades.isEmpty()) {
            System.out.println("No grades found for the course.");
            return;
        }
        String format = "%-20s%s%n";
        System.out.printf(format, "Student name:", "Grade:");
        System.out.printf(format, "------------", "-----");
        for (StudentCourseGrade scg : studentCourseGrades) {
            System.out.printf(format, scg.getStudent().getFirstName() + " " + scg.getStudent().getLastName(), scg.getGrade().getName());
        }
    }

    public static void showTotalStudentCount() {
        Long studentCount = StudentRepository.getTotalStudentsCount();
        if (studentCount == null) return;
        System.out.println("There is a total of " + studentCount + " students in the school.");
    }

    public static void showStudentCountByCourseID() {
        Course course = null;
        while (course == null) {
            showAllCourses();
            System.out.println("Which course (ID) do you want to show student counts for?");
            int courseID = UserInputHandler.readIntInput();
            course = CourseRepository.getCourseByID(courseID);
            if (course == null)
                System.out.println("\nError: Course with ID " + courseID + " not found. Please try again:\n");
        }
        Long studentCount = StudentRepository.getStudentCountByCourseID(course.getId());
        System.out.println("Number of students in this course: " + studentCount);
    }

    public static void showGradeCountsByCourseID() {
        Course course = null;
        while (course == null) {
            showAllCourses();
            System.out.println("Which course (ID) do you want to show grade counts for?");
            int courseID = UserInputHandler.readIntInput();
            course = CourseRepository.getCourseByID(courseID);
            if (course == null)
                System.out.println("\nError: Course with ID " + courseID + " not found. Please try again:\n");
        }
        List<Object[]> results = GradeRepository.getGradeCountsByCourseID(course.getId());
        if (results.isEmpty()) {
            System.out.println("No grades found for the course");
            return;
        }
        String format = "%-20s%s%n";
        System.out.printf(format, "Grade:", "Count:");
        System.out.printf(format, "----", "-----");
        for (Object[] result : results) {
            Grade grade = (Grade) result[0];
            Long gradeCount = (Long) result[1];
            if (grade != null) System.out.printf("%-20s%-20s%n", grade.getName(), gradeCount);
        }
    }

}
