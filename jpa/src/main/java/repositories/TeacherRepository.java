package repositories;

import classes.Classroom;
import classes.Course;
import classes.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import util.JPAUtil;

import java.util.InputMismatchException;
import java.util.List;

import static mainclass.Main.inTransaction;

public class TeacherRepository {

    public static Teacher getTeacherByID(int teacherID) {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            Teacher teacher = null;
            teacher = em.find(Teacher.class, teacherID);
            em.close();
            return teacher;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + ".");
            return null;
        }
    }

    public static void removeTeacher(Teacher teacher) {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Teacher managedTeacher = em.merge(teacher);
            em.remove(managedTeacher);
            transaction.commit();
            System.out.println("Teacher successfully removed.");
        } catch (Exception e) {
            System.out.println("Error removing teacher: " + e.getMessage());
        }
    }


    public static List<Teacher> getAllTeachers() {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            TypedQuery<Teacher> query = em.createQuery("SELECT t FROM Teacher t", Teacher.class);
            List<Teacher> teachers = query.getResultList();
            em.close();
            return teachers;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + ".");
            return null;
        }

    }

    public static void persistTeacher(Teacher teacher) {
        EntityManager em = JPAUtil.getEntityManager();
        inTransaction(entityManager -> entityManager.persist(teacher));
        em.close();
    }
}
