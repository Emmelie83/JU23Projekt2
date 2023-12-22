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
                        System.out.println("\nStudent with ID " + studentID + " successfully removed.");
                    } else {
                        System.out.println("\nError: Student with ID " + studentID + " removal was unsuccessful.\n");
                    }
                } else {
                    System.out.println("\nError: Student with ID " + studentID + " not found. Please try again:\n");
                }
            } catch (Exception e) {
                System.out.println("\nAn unexpected error occurred. Please try again.\n");
            }
        }
    }

    public static void course() {
        Course course = null;
        while (course == null) {
            Read.showAllCourses();
            System.out.println("Which course (ID) would you like to remove?: ");
            int courseID = UserInputHandler.readIntInput();
            course = CourseRepository.getCourseByID(courseID);
            if (course == null) {
                System.out.println("\nError: Course with ID " + courseID + " not found. Please try again:\n");
                return;
            }
        }
        CourseRepository.removeCourse(course);
        System.out.println("Course successfully removed.");
    }


    public static void teacher() {
        Teacher teacher = null;
        while (teacher == null) {
            Read.showAllTeachers();
            System.out.println("Which teacher (ID) would you like to remove? ");
            int teacherID = UserInputHandler.readIntInput();
            teacher = TeacherRepository.getTeacherByID(teacherID);
            if (teacher == null) {
                System.out.println("\nError: Teacher with ID " + teacherID + " not found. Please try again:");
                return;
            }
        }
        List<Course> courses = CourseRepository.getCoursesByTeacher(teacher);
        for (Course course : courses) {
            course.setTeacher(null);
            CourseRepository.updateCourse(course);
        }
        TeacherRepository.removeTeacher(teacher);
    }
}

