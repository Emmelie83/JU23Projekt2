package repositories;

import classes.Course;
import classes.Grade;
import classes.Student;
import classes.StudentCourseGrade;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import mainclass.UserInputHandler;
import util.JPAUtil;

import java.util.InputMismatchException;
import java.util.List;

import static mainclass.Main.inTransaction;

public class StudentCourseGradeRepository {
    public static void removeStudentCourseGrade(StudentCourseGrade studentCourseGrade) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            if (!em.contains(studentCourseGrade)) {
                studentCourseGrade = em.merge(studentCourseGrade);
            }
            em.remove(studentCourseGrade);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            em.close();
        }
    }

    public static List<StudentCourseGrade> getAllStudentCourseGrades() {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            TypedQuery<StudentCourseGrade> query = em.createQuery("SELECT scg " +
                    "FROM StudentCourseGrade scg", StudentCourseGrade.class);

            List<StudentCourseGrade> studentCourseGrades = query.getResultList();
            em.close();
            return studentCourseGrades;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + ". Try again.");
            return null;
        }
    }

    public static List<StudentCourseGrade> getStudentCourseGradesByStudentID(int studentID) {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            TypedQuery<StudentCourseGrade> query = em.createQuery("select scg  from StudentCourseGrade scg " +
                            "where scg.student.id =:id", StudentCourseGrade.class)
                    .setParameter("id", studentID);
            List<StudentCourseGrade> studentCourseGrades = query.getResultList();
            em.close();
            return studentCourseGrades;
        } catch (InputMismatchException e) {
            System.out.println("Error: Student course grades for student with student ID " + studentID + " not found.");
            return null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + ". Try again.");
            return null;
        }
    }

    public static List<StudentCourseGrade> getStudentCourseGradesByCourseID(int courseID) {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            TypedQuery<StudentCourseGrade> query = em.createQuery(
                    "SELECT scg FROM StudentCourseGrade scg WHERE scg.course.id = :courseId", StudentCourseGrade.class);
            query.setParameter("courseId", courseID);
            List<StudentCourseGrade> studentCourseGrades = query.getResultList();
            em.close();
            return studentCourseGrades;
        } catch (InputMismatchException e) {
            System.out.println("Error: Student course grades for course with course ID " + courseID + " not found.");
            return null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + ". Try again.");
            return null;
        }
    }


    public static StudentCourseGrade getStudentCourseGradeByStudentIDAndCourseID(int studentID, int courseID) {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            TypedQuery<StudentCourseGrade> query = em.createQuery(
                    "SELECT scg FROM StudentCourseGrade scg WHERE scg.student.id = : studentId AND scg.course.id = :courseId", StudentCourseGrade.class);
            query.setParameter("studentId", studentID);
            query.setParameter("courseId", courseID);
            StudentCourseGrade studentCourseGrade = query.getSingleResult();
            em.close();
            return studentCourseGrade;
        } catch (InputMismatchException e) {
            System.out.println("Error: Student course grades for course with student ID " + studentID + " and course ID " + courseID + " not found.");
            return null;
        } catch (Exception e) {
            System.out.println("An error occurred. No changes made.");
            return null;
        }
    }


    public static void mergeStudentCourseGrade(StudentCourseGrade scg) {
        EntityManager em = JPAUtil.getEntityManager();
        inTransaction(entityManager -> entityManager.merge(scg));
        em.close();
    }

        public static void persistStudentCourseGrade (StudentCourseGrade studentCourseGrade){
            EntityManager em = JPAUtil.getEntityManager();
            inTransaction(entityManager -> entityManager.persist(studentCourseGrade));
            em.close();
        }
    }
