package crudServices;

import classes.*;
import mainclass.UserInputHandler;
import repositories.*;

public class Create {

    public static void course() {
        System.out.println("Enter new course name: ");
        String name = UserInputHandler.readStringInput();
        final Course course = new Course();
        course.setName(name);
        Teacher teacher = null;
        while (teacher == null) {
            Read.showAllTeachers();
            System.out.println("Which teacher (ID) would you like to assign?. Enter 0 for no teacher: ");
            int teacherID = UserInputHandler.readIntInput();
            if (teacherID == 0) {
                teacher = null;
                break;
            }
            teacher = TeacherRepository.getTeacherByID(teacherID);
            if (teacher == null)
                System.out.println("\nError: Teacher with ID " + teacherID + " not found. Please try again:\n");
        }
        Classroom classroom = null;
        while (classroom == null) {
            Read.showAllClassrooms();
            System.out.println("Which classroom (ID) would you like to assign?: ");
            int classroomID = UserInputHandler.readIntInput();
            classroom = ClassroomRepository.getClassroomByID(classroomID);
            if (classroom == null)
                System.out.println("\nError: Classroom with ID " + classroomID + " not found. Please try again:\n");
        }
        course.setTeacher(teacher);
        course.setClassroom(classroom);
        CourseRepository.persistCourse(course);
        System.out.println("You have successfully added a new course.");
    }

    public static void student() {
        System.out.println("Enter new student first name: ");
        String firstName = UserInputHandler.readStringInput();
        System.out.println("Enter new student last name: ");
        String lastName = UserInputHandler.readStringInput();
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        StudentRepository.persistStudent(student);
        System.out.println("You have successfully added a new student.");
    }

    public static void teacher() {
        System.out.println("Enter new teacher first name: ");
        String firstName = UserInputHandler.readStringInput();
        System.out.println("Enter new teacher last name: ");
        String lastName = UserInputHandler.readStringInput();
        Teacher teacher = new Teacher();
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        TeacherRepository.persistTeacher(teacher);
        System.out.println("You have successfully added a new teacher.");
    }

    public static void studentCourseGrade() {
        Student student = null;
        while (student == null) {
            Read.showAllStudents();
            System.out.println("Which student (ID) do you want to grade?");
            int studentID = UserInputHandler.readIntInput();
            student = StudentRepository.getStudentByID(studentID);
            if (student == null)
                System.out.println("\nError: Student with ID " + studentID + " not found. Please try again:\n");
        }
        Course course = null;
        while (course == null) {
            Read.showAllCourses();
            System.out.println("In which course (ID) do you want to grade the student?");
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
        StudentCourseGrade scg = new StudentCourseGrade();
        scg.setStudent(student);
        scg.setCourse(course);
        scg.setGrade(grade);
        StudentCourseGradeRepository.persistStudentCourseGrade(scg);
        System.out.println("You have successfully added a new student course grade.");
    }
}
