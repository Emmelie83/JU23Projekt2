package crudServices;

import classes.*;
import mainclass.UserInputHandler;
import repositories.*;

import java.util.InputMismatchException;

public class Update {

    public static void studentCourseGrade() {
        try {
            Read.showAllStudents();
            System.out.println("For which student (ID) do you want to update a grade?");
            int studentID = UserInputHandler.readIntInput();
            Read.showStudentCourseGradesByStudent(studentID);
            System.out.println("For which course (ID) should the grade be updated?");
            int courseID = UserInputHandler.readIntInput();
            Course course = CourseRepository.getCourseByID(courseID);
            if (course == null) {
                System.out.println("Course with ID " + courseID + " not found.");
                return;
            }
            Read.showAllGrades();
            System.out.println("What grade (ID) should the student have?");
            int gradeID = UserInputHandler.readIntInput();
            Grade grade = GradeRepository.getGradeByID(gradeID);
            if (grade != null) {
                StudentCourseGradeRepository.updateStudentCourseGrade(grade, studentID, courseID);
            } else System.out.println("Grade with ID " + gradeID + " not found.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please try again.");
        }
    }

    public static void studentName() {
        try {
            Read.showAllStudents();
            System.out.println("Which student (ID) would you like to change? ");
            int studentID = UserInputHandler.readIntInput();
            System.out.println("New first name: ");
            String firstName = UserInputHandler.readStringInput();
            System.out.println("New last name: ");
            String lastName = UserInputHandler.readStringInput();
            Student student = StudentRepository.getStudentByID(studentID);
            if (student != null) {
                student.setFirstName(firstName);
                student.setLastName(lastName);
                StudentRepository.mergeStudent(student);
                System.out.println("Student name successfully updated.");
            } else {
                System.out.println("Student with student ID " + studentID + " not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter a valid student ID.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please try again.");
        }
    }

    public static void courseTeacher() {
        try {
            Read.showAllCourses();
            System.out.println("Which course (ID) would you like to update? ");
            int courseID = UserInputHandler.readIntInput();
            Read.showAllTeachers();
            System.out.println("Which teacher (ID) would you like to add? For no teacher, enter 0:");
            int teacherID = UserInputHandler.readIntInput();
            Teacher teacher = TeacherRepository.getTeacherByID(teacherID);
            Course course = CourseRepository.getCourseByID(courseID);
            if (teacher == null || course == null) {
                System.out.println("Not a valid choice. Teacher or course not found.");
                return;
            }
            course.setTeacher(teacher);
            CourseRepository.mergeCourse(course);
            System.out.println("Teacher for the course successfully updated.");
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter valid integers for course ID and teacher ID.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please try again.");
        }
    }

}
