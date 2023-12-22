package repositories;

import classes.Classroom;
import classes.Grade;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import util.JPAUtil;

import java.util.InputMismatchException;
import java.util.List;

import static mainclass.Main.inTransaction;

public class GradeRepository {

    public static Grade getGradeByID(int gradeID) {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            Grade grade = null;
            grade = em.find(Grade.class, gradeID);
            em.close();
            return grade;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + ". Try again.");
            return null;
        }
    }

    public static List<Grade> getAllGrades() {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            TypedQuery<Grade> query = em.createQuery("SELECT g FROM Grade g", Grade.class);
            List<Grade> grades = query.getResultList();
            em.close();
            return grades;
        } catch (Exception e) {
            System.out.println("Error: " + e.toString() + ". Try again.");
            return null;
        }
    }

    public static List<Object[]> getGradeCountsByCourseID(int courseID) {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            TypedQuery<Object[]> query = em.createQuery(
                    "SELECT scg.grade, COUNT(scg) " +
                            "FROM StudentCourseGrade scg " +
                            "WHERE scg.course.id = :courseId " +
                            "GROUP BY scg.grade.id " +
                            "ORDER BY scg.grade.id", Object[].class);

            query.setParameter("courseId", courseID);
            List<Object[]> objectList = query.getResultList();
            em.close();
            return objectList;
        } catch (InputMismatchException e) {
            System.out.println("Error: Course with course ID " + courseID + " not found. Please try again.");
            return null;
        } catch (Exception e) {
            System.out.println("Error: " + e.toString() + ". Try again.");
            return null;
        }
    }

    public static void persistGrade(Grade grade) {
        EntityManager em = JPAUtil.getEntityManager();
        inTransaction(entityManager -> entityManager.persist(grade));
        em.close();
    }

}
