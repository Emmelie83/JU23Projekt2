package crudServices;

import classes.Course;
import classes.Student;
import classes.StudentCourseGrade;
import classes.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import mainclass.UserInputHandler;
import repositories.CourseRepository;
import repositories.StudentCourseGradeRepository;
import repositories.StudentRepository;
import repositories.TeacherRepository;
import util.JPAUtil;

import java.util.InputMismatchException;
import java.util.List;

public class Delete {

    public static void student() {
        Student student = null;
        while (student == null) {
            try {
                Read.showAllStudents();
                System.out.println("Which student (ID) would you like to remove?: ");
                int studentID = UserInputHandler.readIntInput();
                student = StudentRepository.getStudentByID(studentID);
                if (student != null) {
                    List<StudentCourseGrade> studentCourseGrades = StudentCourseGradeRepository.getStudentCourseGradesByStudentID(studentID);
                    for (StudentCourseGrade scg : studentCourseGrades) {
                        StudentCourseGradeRepository.removeStudentCourseGrade(scg);
                    }
                    boolean studentRemoved = StudentRepository.removeStudent(student);
                    if (studentRemoved) {
                        System.out.println("Student with ID " + studentID + " successfully removed.");
                    } else {
                        System.out.println("Error: Student with ID " + studentID + " removal was unsuccessful.");
                    }
                } else {
                    System.out.println("Error: Student with ID " + studentID + " not found. Please enter a valid student ID.");
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred. Please try again.");
            }
        }
    }

    public static void course() {
        System.out.println("Which course (ID) would you like to remove?: ");
        Read.showAllCourses();
        int courseID = UserInputHandler.readIntInput();
        Course course = CourseRepository.getCourseByID(courseID);
        if (course != null) {
            CourseRepository.removeCourse(course);
        } else System.out.println("Error: Course with course ID " + courseID + " not found.");
    }


    public static void teacher() {
        Read.showAllTeachers();
        System.out.println("Which teacher (ID) would you like to remove? ");
        int teacherID = UserInputHandler.readIntInput();
        Teacher teacher = TeacherRepository.getTeacherByID(teacherID);
        if (teacher == null) {
            System.out.println("Error: Teacher with ID " + teacherID + " not found.");
            return;
        }
        List<Course> courses = CourseRepository.getCoursesByTeacher(teacher);
        if (courses.isEmpty()) {
            System.out.println("No courses associated with this teacher.");
        }
        for (Course course : courses) CourseRepository.removeCourse(course);
        TeacherRepository.removeTeacher(teacher);

    }
}

