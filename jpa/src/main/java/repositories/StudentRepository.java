package repositories;

import classes.Classroom;
import classes.Student;
import classes.StudentCourseGrade;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import util.JPAUtil;

import java.util.InputMismatchException;
import java.util.List;

import static mainclass.Main.inTransaction;

public class StudentRepository {

    public static Student getStudentByID(int studentID) {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            Student student = null;
            student = em.find(Student.class, studentID);
            em.close();
            return student;
        } catch (Exception e) {
            System.out.println("Error: " + e.toString() + ". Try again.");
            return null;
        }
    }

    public static List<Student> getAllStudents() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s", Student.class);
        List<Student> students = query.getResultList();
        em.close();
        return students;
    }

    public static boolean removeStudent(Student student) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Student managedStudent = em.merge(student);
            em.remove(managedStudent);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error removing student: " + e.getMessage());
            return false;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public static Long getStudentCountByCourseID(int courseID) {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(scg.student.id) " +
                            "FROM StudentCourseGrade scg " +
                            "WHERE scg.course.id = :courseId", Long.class);
            query.setParameter("courseId", courseID);
            Long studentCount = query.getSingleResult();
            em.close();
            return studentCount;
        } catch (InputMismatchException e) {
            System.out.println("Error: Course with course ID " + courseID + " not found. Please try again.");
            return null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + ". Try again.");
            return null;
        }
    }

    public static Long getTotalStudentsCount() {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(s) FROM Student s", Long.class);
            Long studentCount = query.getSingleResult();
            em.close();
            return studentCount;
        } catch (Exception e) {
            System.out.println("Something went wrong.");
            return null;
        }
    }

    public static void mergeStudent(Student student) {
        EntityManager em = JPAUtil.getEntityManager();
        inTransaction(entityManager -> entityManager.merge(student));
        em.close();
    }

    public static void persistStudent(Student student) {
        EntityManager em = JPAUtil.getEntityManager();
        inTransaction(entityManager -> entityManager.persist(student));
        em.close();
    }
}
