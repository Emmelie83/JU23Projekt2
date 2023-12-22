package repositories;

import classes.Classroom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import util.JPAUtil;

import java.util.InputMismatchException;
import java.util.List;

import static mainclass.Main.inTransaction;

public class ClassroomRepository {

    public static Classroom getClassroomByID(int classroomID) {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            Classroom classroom = null;
            classroom = em.find(Classroom.class, classroomID);
            em.close();
            return classroom;
        } catch (InputMismatchException e) {
            System.out.println("Error: Classroom with classroom ID " + classroomID + " not found. Please try again.");
            return null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + ". Try again.");
            return null;
        }
    }

    public static List<Classroom> getAllClassrooms() {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            TypedQuery<Classroom> query = em.createQuery("SELECT c FROM Classroom c", Classroom.class);
            List<Classroom> classrooms = query.getResultList();
            em.close();
            return classrooms;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + ".");
            return null;
        }
    }

}
