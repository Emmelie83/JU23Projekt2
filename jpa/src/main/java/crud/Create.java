package crud;

import classes.*;
import jakarta.persistence.EntityManager;
import mainclass.UserInputHandler;
import util.JPAUtil;

import java.util.InputMismatchException;

import static mainclass.Main.inTransaction;

public class Create {

    static EntityManager em = JPAUtil.getEntityManager();

    public static void course() {
        System.out.println("Enter new course name: ");
        String name = UserInputHandler.readStringInput();
        final Course course = new Course();
        course.setName(name);
        System.out.println("Available Teachers:");
        Read.showTeachers();
        System.out.println("Which teacher (ID) would you like to assign?: ");
        Teacher teacher = null;
        while (teacher == null) {
            try {
                int teacherId = UserInputHandler.readIntInput();
                teacher = em.find(Teacher.class, teacherId);
                if (teacher == null) {
                    System.out.println("Error: Teacher with teacherId " + teacherId + " not found. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input for teacher ID. Please enter a valid integer.");
            }
        }
        course.setTeacher(teacher);
        Read.showClassrooms();
        System.out.println("Which classroom (ID) would you like to assign?: ");
        Classroom classroom = null;
        while (classroom == null) {
            try {
                int classroomId = UserInputHandler.readIntInput();
                classroom = em.find(Classroom.class, classroomId);
                if (classroom == null) {
                    System.out.println("Error: Classroom with classroomId " + classroomId + " not found. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input for classroom ID. Please enter a valid integer.");
            }
        }
        course.setClassroom(classroom);
        persistObject(course);
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
        persistObject(student);
        System.out.println("You have successfully added a new student.");
    }

    public static void studentCourseGrade() {
        EntityManager em = JPAUtil.getEntityManager();
        Read.showStudents();
        System.out.println("Which student (ID) do you want to grade?");
        int studentId = UserInputHandler.readIntInput();
        Student student = em.find(Student.class, studentId);
        Read.showCourses();
        System.out.println("In which course (ID) do you want to grade the student?");
        int courseId = UserInputHandler.readIntInput();
        Course course = em.find(Course.class, courseId);
        Read.showGrades();
        System.out.println("Which grade (ID) do you want to assign?");
        int gradeId = UserInputHandler.readIntInput();
        Grade grade = em.find(Grade.class, gradeId);
        StudentCourseGrade scg = new StudentCourseGrade();
        scg.setStudent(student);
        scg.setCourse(course);
        scg.setGrade(grade);
        persistObject(scg);
        System.out.println("You have successfully added a new student course grade.\n");
    }

    public static void teacher() {
        System.out.println("Enter new teacher first name: ");
        String firstName = UserInputHandler.readStringInput();
        System.out.println("Enter new teacher last name: ");
        String lastName = UserInputHandler.readStringInput();
        Teacher teacher = new Teacher();
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        persistObject(teacher);
        System.out.println("You have successfully added a new teacher.");
    }

    public static void persistObject(Object o){
        inTransaction(entityManager -> entityManager.persist(o));
        em.close();
    }
}