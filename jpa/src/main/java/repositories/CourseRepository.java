package repositories;

import classes.Course;
import classes.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import util.JPAUtil;

import java.util.InputMismatchException;
import java.util.List;

import static mainclass.Main.inTransaction;

public class CourseRepository {
    public static Course getCourseByID(int courseID) {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            Course course;
            course = em.find(Course.class, courseID);
            em.close();
            return course;
        } catch (InputMismatchException e) {
            System.out.println("Error: Course with course ID " + courseID + " not found. Please try again.");
            return null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + ". Try again.");
            return null;
        }
    }

    public static void removeCourse(Course course) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            try {
                transaction.begin();
                em.createQuery("DELETE FROM StudentCourseGrade scg WHERE scg.course = :course")
                        .setParameter("course", course)
                        .executeUpdate();
                Course managedCourse = em.find(Course.class, course.getId());

                if (managedCourse != null) em.remove(managedCourse);
                transaction.commit();
                System.out.println("Course successfully removed.");
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                System.out.println("Error removing course: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
        }
    }

    public static List<Object[]> getAllObjectsInCourses() {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            TypedQuery<Object[]> query = em.createQuery(
                    "SELECT c, t, cl " +
                            "FROM Course c " +
                            "LEFT JOIN c.teacher t " +
                            "LEFT JOIN c.classroom cl", Object[].class);

            List<Object[]> objectList = query.getResultList();
            em.close();
            return objectList;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static List<Course> getCoursesByTeacher(Teacher teacher) {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            TypedQuery<Course> query = em.createQuery("select c from Course c" +
                            " where c.teacher.id =:id ", Course.class)
                    .setParameter("id", teacher.getId());
            List<Course> courses = query.getResultList();
            em.close();
            return courses;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + ". Try again.");
            return null;
        }
    }

    public static void mergeCourse(Course course) {
        EntityManager em = JPAUtil.getEntityManager();
        inTransaction(entityManager -> entityManager.merge(course));
        em.close();
    }

    public static void persistCourse(Course course) {
        EntityManager em = JPAUtil.getEntityManager();
        inTransaction(entityManager -> entityManager.persist(course));
        em.close();
    }

}
