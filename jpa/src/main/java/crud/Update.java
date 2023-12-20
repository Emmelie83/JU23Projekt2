package crud;

import classes.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import mainclass.UserInputHandler;
import util.JPAUtil;

import static mainclass.Main.inTransaction;


public class Update {

    //StudentId + courseId endast 1 gång! Unikt index

    public static void studentName() {
        Read.showStudents();
        System.out.println("Which student (ID) would you like to change? ");
        EntityManager em1 = JPAUtil.getEntityManager();
        int studentId = UserInputHandler.readIntInput();

        System.out.println("New first name: ");
        String studentFirstName = UserInputHandler.readStringInput();

        System.out.println("New last name: ");
        String studentLastName = UserInputHandler.readStringInput();

        updateStudentName(studentId, studentFirstName, studentLastName);
        em1.close();
    }

    private static void updateTeacherCourse(int teacherId, int courseId) {
        EntityManager emd = JPAUtil.getEntityManager();

        Teacher teacher = emd.find(Teacher.class, teacherId);
        Course course = emd.find(Course.class, courseId);

        if (teacher == null || course == null) {
            System.out.println("Not a valid choice");
            return;
        }
        emd.getTransaction().begin();
        course.setTeacher(teacher);
        emd.merge(teacher);
        emd.persist(teacher);
        emd.getTransaction().commit();
        emd.close();
    }

    public static void courseTeacher() {
        EntityManager em = JPAUtil.getEntityManager();
        Read.showCourses();
        System.out.println("Which course (ID) would you like to update? ");
        int courseId = UserInputHandler.readIntInput();

        Read.showTeachers();
        System.out.println("Which teacher (ID) would you like to add? For no teacher, enter 0:");
        int teacherId = UserInputHandler.readIntInput();

        updateTeacherCourse(teacherId, courseId);
    }

    public static void grades() {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            Read.showStudents();
            System.out.println("What studentId do you want to use?");
            int studentId = UserInputHandler.readIntInput();

            Read.showStudentGrades(em.find(Student.class, studentId));
            System.out.println("For what course (ID) should the grade be updated?");
            int courseId = UserInputHandler.readIntInput();

            Read.showGrades();
            System.out.println("What grade (ID) should the student have?");
            int gradeId = UserInputHandler.readIntInput();

            Grade grade = em.find(Grade.class, gradeId);

            if (grade != null) {
                Query query = em.createQuery(
                        "UPDATE StudentCourseGrade scg SET scg.grade = :grade " +
                                "WHERE scg.student.id = :studentId AND scg.course.id = :courseId");
                query.setParameter("grade", grade);
                query.setParameter("studentId", studentId);
                query.setParameter("courseId", courseId);
                int updatedCount = query.executeUpdate();

                transaction.commit();
                System.out.println("Student grade updated successfully." + updatedCount);
            } else {
                System.out.println("Grade with ID " + gradeId + " not found.");
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private static void updateStudentName(int studentId, String firstName, String lastName) {
        inTransaction(entityManager -> {
            Student person = entityManager.find(Student.class, studentId);
            if (person != null) {
                person.setFirstName(firstName);
                person.setLastName(lastName);
                System.out.println("Student name updated successfully.");
            } else {
                System.out.println("Person not found with id: " + studentId);
            }
        });
    }
}
