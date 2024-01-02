package crudServices;

import classes.*;
import mainclass.UserInputHandler;
import repositories.*;

import java.util.InputMismatchException;
import java.util.List;

public class Update {

    public static void studentCourseGrade() {
        try {
            Student student = null;
            while (student == null) {
                Read.showAllStudents();
                System.out.println("For which student (ID) do you want to update a grade?");
                int studentID = UserInputHandler.readIntInput();
                student = StudentRepository.getStudentByID(studentID);
                List<StudentCourseGrade> studentCourseGrades = StudentCourseGradeRepository.getStudentCourseGradesByStudentID(studentID);
                if (studentCourseGrades.isEmpty()) {
                    System.out.println("No student course grades found for the student.");
                    return;
                }
                if (student == null)
                    System.out.println("\nError: Student with ID " + studentID + " not found. Please try again:\n");
            }
            Course course = null;
            while (course == null) {
                Read.showStudentCourseGradesByStudent(student.getId());
                System.out.println("For which course (ID) do you want to update a grade?");
                int courseID = UserInputHandler.readIntInput();
                course = CourseRepository.getCourseByID(courseID);
                if (course == null)
                    System.out.println("\nError: Course with ID " + courseID + " not found. Please try again:\n");
            }
            Grade grade = null;
            while (grade == null) {
                Read.showAllGrades();
                System.out.println("Which grade (ID) do you want to assign?");
                int gradeID = UserInputHandler.readIntInput();
                grade = GradeRepository.getGradeByID(gradeID);
                if (grade == null)
                    System.out.println("\nError: Grade with ID " + gradeID + " not found. Please try again:\n");
            }
            StudentCourseGrade scg = StudentCourseGradeRepository.getStudentCourseGradeByStudentIDAndCourseID(student.getId(), course.getId());
            if (scg != null) scg.setGrade(grade);
            StudentCourseGradeRepository.mergeStudentCourseGrade(scg);
            System.out.println("Student course grade successfully updated.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please try again.");
        }
    }

    public static void studentName() {
        try {
            Student student = null;
            while (student == null) {
                Read.showAllStudents();
                System.out.println("Which student (ID) do you want to change?");
                int studentID = UserInputHandler.readIntInput();
                student = StudentRepository.getStudentByID(studentID);
                if (student == null)
                    System.out.println("\nError: Student with ID " + studentID + " not found. Please try again:\n");
            }
            System.out.println("New first name: ");
            String firstName = UserInputHandler.readStringInput();
            System.out.println("New last name: ");
            String lastName = UserInputHandler.readStringInput();
            student.setFirstName(firstName);
            student.setLastName(lastName);
            StudentRepository.mergeStudent(student);
            System.out.println("Student name successfully updated.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please try again.");
        }
    }

    public static void courseTeacher() {
        try {
            Course course = null;
            while (course == null) {
                Read.showAllCourses();
                System.out.println("For which course (ID) do you want to change the teacher?");
                int courseID = UserInputHandler.readIntInput();
                course = CourseRepository.getCourseByID(courseID);
                if (course == null)
                    System.out.println("\nError: Course with course ID " + courseID + " not found. Please try again:\n");
            }
            Teacher teacher = null;
            int teacherID = 0;
            while (teacher == null) {
                Read.showAllTeachers();
                System.out.println("Which teacher (ID) would you like to add? For no teacher, enter 0:");
                teacherID = UserInputHandler.readIntInput();
                teacher = TeacherRepository.getTeacherByID(teacherID);
                if (teacher == null) {
                    System.out.println("\nError: Teacher with ID " + teacherID + " not found. Please try again:");
                    return;
                }
            }
            if (teacherID == 0) course.setTeacher(null);
            course.setTeacher(teacher);
            CourseRepository.mergeCourse(course);
            System.out.println("Teacher for the course successfully updated.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please try again.");
        }
    }

}
