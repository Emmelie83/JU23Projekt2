package crud;

import classes.Course;
import classes.Student;
import classes.StudentCourseGrade;
import classes.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import mainclass.Main;
import mainclass.UserInputHandler;
import util.JPAUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static mainclass.Main.inTransaction;

public class Delete {
    private static void removeObject(Object o, int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.find(o.getClass(), id));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No changes made.");
        }
        em.close();
    }

    public static void teacher() {
        EntityManager em = JPAUtil.getEntityManager();
        Read.showTeachers();
        System.out.println("Which teacher (ID) would you like to remove? ");
        int teacherId = UserInputHandler.readIntInput();
        removeTeacherFromCourse(em.find(Teacher.class, teacherId));
        removeObject(em.find(Teacher.class, teacherId), teacherId);
    }

    private static void removeTeacherFromCourse(Teacher teacher) {
        if (teacher == null) {
            System.out.println("There is no teacher with that id");
            return;
        }
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<Course> query = em.createQuery("select c from Course c" +
                        " where c.teacher.id =:id ", Course.class)
                .setParameter("id", teacher.getId());
        List<Course> list = query.getResultList();
        for (Course scg : list) {
            em.getTransaction().begin();
            scg.setTeacher(null);
            em.getTransaction().commit();
        }
    }

    public static void course() {
        EntityManager em = JPAUtil.getEntityManager();
        System.out.println("Which course (ID) would you like to remove?: ");
        Read.showCourses();
        int courseID = UserInputHandler.readIntInput();
        removeObject(em.find(Course.class, courseID), courseID);
        em.close();
    }

    public static void removeAssociatedCourseAndGrade(Student s) {
        if (s == null) {
            System.out.println("There is no student with that id");
            return;
        }
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<StudentCourseGrade> query = em.createQuery("" +
                        "Select scg from StudentCourseGrade scg" +
                        " where scg.student.id=:id", StudentCourseGrade.class)
                .setParameter("id", s.getId());
        List<StudentCourseGrade> list = query.getResultList();
        for (StudentCourseGrade scg : list) {
            removeObject(scg, scg.getId());
        }
        em.close();
    }

    public static void student() {
        EntityManager em = JPAUtil.getEntityManager();
        System.out.println("Which student (ID) would you like to remove?: ");
        Read.showStudents();
        int studentID = UserInputHandler.readIntInput();
        Student student = em.find(Student.class, studentID);
        removeAssociatedCourseAndGrade(student);
        removeObject(student, studentID);
        em.close();
    }

    /* public static void student() {
        System.out.println("Which student (ID) would you like to remove?: ");
        Read.showStudents();
        int studentId = UserInputHandler.readIntInput();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            Student student = em.find(Student.class, studentId);
            if (student != null) {
                em.createQuery("DELETE FROM StudentCourseGrade scg WHERE scg.student.id = :studentId")
                        .setParameter("studentId", studentId)
                        .executeUpdate();
                em.remove(student);

                transaction.commit();
                System.out.println("Student with studentId: " + studentId + " successfully removed.");
            } else {
                System.out.println("Student with studentId: " + studentId + " not found.");
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    } */
    /* public static void teacher() {
        System.out.println("Which teacher (ID) would you like to remove?: ");
        Read.showTeachers();
        int teacherId = UserInputHandler.readIntInput();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            Teacher teacher = em.find(Teacher.class, teacherId);
            if (teacher != null) {
                em.createQuery("UPDATE Course c SET c.teacher = null WHERE c.teacher.id = :teacherId")
                        .setParameter("teacherId", teacherId)
                        .executeUpdate();

                em.remove(teacher);

                transaction.commit();

                System.out.println("Teacher with teacherId: " + teacherId + " successfully removed.");
            } else {
                System.out.println("Teacher with teacherId: " + teacherId + " not found.");
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }*/
}
