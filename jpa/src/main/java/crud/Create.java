package crud;

import classes.*;
import jakarta.persistence.EntityManager;
import mainclass.UserInputHandler;
import util.JPAUtil;

import java.util.InputMismatchException;

import static mainclass.Main.inTransaction;

public class Create {

    static EntityManager em = JPAUtil.getEntityManager();

    public static Teacher findTeacher(int teacherId) {
        EntityManager em = JPAUtil.getEntityManager();
        Teacher teacher = null;
        try {
            teacher = em.find(Teacher.class, teacherId);
        } catch (InputMismatchException e) {
        }
        em.close();
        return teacher;
    }

    public static Classroom findClassroom(int classroomId) {
        EntityManager em = JPAUtil.getEntityManager();
        Classroom classroom = null;
        try {
            classroom = em.find(Classroom.class, classroomId);
        } catch (InputMismatchException e) {
        }
        em.close();
        return classroom;
    }

    public static Student findStudent(int studentId) {
        EntityManager em = JPAUtil.getEntityManager();
        Student student = null;
        try {
            student = em.find(Student.class, studentId);
        } catch (InputMismatchException e) {
        }
        em.close();
        return student;
    }

    public static Course findCourse(int courseId) {
        EntityManager em = JPAUtil.getEntityManager();
        Course course = null;
        try {
            course = em.find(Course.class, courseId);
        } catch (InputMismatchException e) {
        }
        em.close();
        return course;
    }

    public static Grade findGrade(int gradeId) {
        EntityManager em = JPAUtil.getEntityManager();
        Grade grade = null;
        try {
            grade = em.find(Grade.class, gradeId);
        } catch (InputMismatchException e) {
        }
        em.close();
        return grade;
    }


    public static void persistObject(Object o) {
        inTransaction(entityManager -> entityManager.persist(o));
        em.close();
    }
}