package crudServices;

import classes.*;
import crud.Create;
import crud.Read;
import mainclass.UserInputHandler;

public class CreateService {

    public static void course() {
        System.out.println("Enter new course name: ");
        String name = UserInputHandler.readStringInput();
        final Course course = new Course();
        course.setName(name);
        Teacher teacher = null;
        while (teacher == null) {
            Read.showTeachers();
            System.out.println("Which teacher (ID) would you like to assign?: ");
            int teacherId = UserInputHandler.readIntInput();
            teacher = Create.findTeacher(teacherId);
            if (teacher == null)
                System.out.println("\nError: Teacher with teacherId " + teacherId + " not found. Please try again.");
        }
        Classroom classroom = null;
        while (classroom == null) {
            Read.showClassrooms();
            System.out.println("Which classroom (ID) would you like to assign?: ");
            int classroomId = UserInputHandler.readIntInput();
            classroom = Create.findClassroom(classroomId);
            if (classroom == null)
                System.out.println("Error: Classroom with classroomId " + classroomId + " not found. Please try again.");
        }
        course.setTeacher(teacher);
        course.setClassroom(classroom);
        Create.persistObject(course);
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
        Create.persistObject(student);
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
        Create.persistObject(teacher);
        System.out.println("You have successfully added a new teacher.");
    }

    public static void studentCourseGrade() {
        Student student = null;
        while (student == null) {
            Read.showStudents();
            System.out.println("Which student (ID) do you want to grade?");
            int studentId = UserInputHandler.readIntInput();
            student = Create.findStudent(studentId);
            if (student == null)
                System.out.println("\nError: Student with studentId " + studentId + " not found. Please try again.\n");
        }
        Course course = null;
        while (course == null) {
            Read.showCourses();
            System.out.println("In which course (ID) do you want to grade the student?");
            int courseId = UserInputHandler.readIntInput();
            course = Create.findCourse(courseId);
            if (course == null)
                System.out.println("\nError: Course with courseId " + courseId + " not found. Please try again.\n");
        }
        Grade grade = null;
        while (grade == null) {
            Read.showGrades();
            System.out.println("Which grade (ID) do you want to assign?");
            int gradeId = UserInputHandler.readIntInput();
            grade = Create.findGrade(gradeId);
            if (grade == null)
                System.out.println("\nError: Grade with gradeId " + gradeId + " not found. Please try again.\n");
        }
        StudentCourseGrade scg = new StudentCourseGrade();
        scg.setStudent(student);
        scg.setCourse(course);
        scg.setGrade(grade);
        Create.persistObject(scg);
        System.out.println("You have successfully added a new student course grade.");
    }

}
