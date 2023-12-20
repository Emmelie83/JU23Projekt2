package crud;

import classes.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import mainclass.UserInputHandler;
import util.JPAUtil;

import java.util.*;

public class Read {


    public static void showStudentCourseGrade() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<StudentCourseGrade> query = em.createQuery("SELECT scg " +
                "FROM StudentCourseGrade scg", StudentCourseGrade.class);
        List<StudentCourseGrade> list = query.getResultList();
        String format = "%-10s%-20s%-10s%s%n";
        System.out.printf(format, "ID:", "G-ID:", "S-ID:", "C-ID:");
        System.out.printf(format, "--", "----", "----", "----");
        for (StudentCourseGrade scg : list) {
            System.out.printf(format, scg.getId(),
                    scg.getCourse().getName(),
                    scg.getGrade().getName(),
                    scg.getStudent().getFirstName());
        }
        em.close();
    }

    public static void showStudentGrades(Student s) {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<StudentCourseGrade> query = em.createQuery("select scg  from StudentCourseGrade scg " +
                        "where scg.student.id =:id", StudentCourseGrade.class)
                .setParameter("id", s.getId());
        printStudentGrades(query);
        em.close();
    }

    private static void printStudentGrades(TypedQuery<StudentCourseGrade> query) {
        List<StudentCourseGrade> list = query.getResultList();
        String format = "%-10s%-20s%-20s%-15s\n";
        System.out.printf(format, "Id:", "Name:", "Course:", "Grade:");
        System.out.printf(format, "--", "----", "------", "-----");
        list.forEach((e) -> System.out.printf(
                format, e.getCourse().getId(),
                e.getStudent().getFirstName() + " " + e.getStudent().getLastName(),
                e.getCourse().getName(),
                e.getGrade().getName()
        ));
    }

    public static void showAllStudentGrades() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<StudentCourseGrade> query = em.createQuery("select scg  " +
                        "from StudentCourseGrade scg ORDER BY scg.student.firstName asc"
                , StudentCourseGrade.class);
        printStudentGrades(query);
        em.close();
    }

    public static void howManyAGrades() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<StudentCourseGrade> query = em.createQuery("select scg from StudentCourseGrade scg" +
                        " where scg.grade.id =: id", StudentCourseGrade.class)
                .setParameter("id", 1);
        List<StudentCourseGrade> listOfAGrades = query.getResultList();
        System.out.println("There is a total of " + listOfAGrades.size() + " A-grades");

        Map<String, Integer> totalAGradesPerClass = new HashMap<>();

        listOfAGrades.forEach(scg -> {
            if ("A".equals(scg.getGrade().getName())) {
                totalAGradesPerClass.merge(scg.getCourse().getName(), 1, Integer::sum);
            }
        });
        totalAGradesPerClass.forEach((className, totalAGrades) ->
                System.out.println(className + ", Total A Grades: " + totalAGrades)
        );
        em.close();
    }

    public static void showStudentCourseGradesByCourseId() {
        EntityManager em = JPAUtil.getEntityManager();
        System.out.println("Available Courses:");
        showCourses();
        System.out.println("Enter the ID of the course to show grades for:");
        int courseId = UserInputHandler.readIntInput();
        TypedQuery<StudentCourseGrade> query = em.createQuery(
                "SELECT scg FROM StudentCourseGrade scg WHERE scg.course.id = :courseId", StudentCourseGrade.class);
        query.setParameter("courseId", courseId);
        List<StudentCourseGrade> studentGrades = query.getResultList();

        String format = "%-20s%-20s%n";
        System.out.printf(format, "Student name:", "Grade:");
        System.out.printf(format, "------------", "-----");
        for (StudentCourseGrade scg : studentGrades) {
            if (!studentGrades.isEmpty()) {
                Student s = scg.getStudent();
                System.out.printf(format, s.getFirstName() + " " + s.getLastName(), scg.getGrade().getName());
            } else {
                System.out.println("No student grades found for the specified course.");
            }
        }
    }

    public static void printGradeCountsByCourseId() {
        EntityManager em = JPAUtil.getEntityManager();
        System.out.println("Available Courses:");
        showCourses();
        System.out.println("Enter the ID of the course to count grades for:");
        int courseId = UserInputHandler.readIntInput();

        TypedQuery<Object[]> query = em.createQuery(
                "SELECT scg.grade, COUNT(scg) " +
                        "FROM StudentCourseGrade scg " +
                        "WHERE scg.course.id = :courseId " +
                        "GROUP BY scg.grade.id " +
                        "ORDER BY scg.grade.id", Object[].class);

        query.setParameter("courseId", courseId);
        List<Object[]> results = query.getResultList();
        if (!results.isEmpty()) {
            String format = "%-20s%-20s%n";
            System.out.printf(format, "Grade:", "Count:");
            System.out.printf(format, "----", "-----");
            for (Object[] result : results) {
                Grade grade = (Grade) result[0];
                Long gradeCount = (Long) result[1];

                if (grade != null) {
                    System.out.printf("%-20s%-20s%n", grade.getName(), gradeCount);
                }
            }
        } else {
            System.out.println("No grades to show.");
        }
    }

    public static void printStudentsCountByCourseId() {
        EntityManager em = JPAUtil.getEntityManager();
        System.out.println("Available Courses:");
        showCourses();
        System.out.println("Enter the ID of the course to count students for:");
        int courseId = UserInputHandler.readIntInput();

        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(scg.student.id) " +
                        "FROM StudentCourseGrade scg " +
                        "WHERE scg.course.id = :courseId", Long.class);

        query.setParameter("courseId", courseId);
        Long studentCount = query.getSingleResult();
        System.out.println("Number of students in this course: " + studentCount);
    }

    public static void showTeachers() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<Teacher> query = em.createQuery("SELECT t FROM Teacher t", Teacher.class);
        List<Teacher> teachers = query.getResultList();
        String format = "%-20s%s%n";
        System.out.printf(format, "ID:", "Teacher:");
        System.out.printf(format, "--", "--------");
        for (Teacher t : teachers) {
            System.out.printf(format, t.getId(), t.getFirstName() + " " + t.getLastName());
        }
    }

    public static void showStudents() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s", Student.class);
        List<Student> students = query.getResultList();
        String format = "%-15s%-20s%s%n";
        System.out.printf(format, "ID:", "First name:", "Last name:");
        System.out.printf(format, "--", "----------", "---------");
        for (Student s : students) {
            System.out.printf(format, s.getId(), s.getFirstName(), s.getLastName());
        }
    }

    public static void showCourses() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<Object[]> query = em.createQuery(
                "SELECT c, t, cl " +
                        "FROM Course c " +
                        "LEFT JOIN c.teacher t " +
                        "LEFT JOIN c.classroom cl", Object[].class);

        List<Object[]> resultList = query.getResultList();
        String format = "%-10s%-20s%-30s%s%n";
        System.out.printf(format, "ID:", "Course:", "Teacher:", "Classroom:");
        System.out.printf(format, "--", "------", "---------", "----------");

        for (Object[] result : resultList) {
            Course course = (Course) result[0];
            Teacher teacher = (Teacher) result[1];
            Classroom classroom = (Classroom) result[2];
            if (course != null) {
                System.out.printf("%-10d%-20s", course.getId(), course.getName());
            }
            if (teacher != null) {
                System.out.printf("%-30s", teacher.getFirstName() + " " + teacher.getLastName());
            } else {
                System.out.printf("%-30s", "N/A");
            }
            if (classroom != null) {
                System.out.printf("%s%n", classroom.getClassroomName());
            } else {
                System.out.printf("%s%n", "N/A");
            }
        }
    }

    public static void showGrades() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<Grade> query = em.createQuery("SELECT g FROM Grade g", Grade.class);
        List<Grade> grades = query.getResultList();
        String format = "%-20s%s%n";
        System.out.printf(format, "ID:", "Grade:");
        System.out.printf(format, "--", "----");
        for (Grade g : grades) {
            System.out.printf(format, g.getId(), g.getName());
        }
    }

    public static void showClassrooms() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<Classroom> query = em.createQuery("SELECT c FROM Classroom c", Classroom.class);
        List<Classroom> classrooms = query.getResultList();
        if (!classrooms.isEmpty()) {
            String format = " %-20s%-20s%-20s%n";
            System.out.printf(format, "ID:", "Name:", "Capacity:");
            System.out.printf(format, "--", "----:", "--------");
            for (Classroom c : classrooms) {
                System.out.printf(format, c.getClassroomId(), c.getClassroomName(), c.getClassroomCapacity());
            }
        }
    }

    public static void totalAmountOfStudents() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<Student> query = em.createQuery("SELECT COUNT(*) FROM Student ", Student.class);
        System.out.println("There is a total of " + query.getResultList().getFirst() + " students in the school.");
        em.close();
    }


    //For javaFX
    public static List<String> showCoursesFX() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<Object[]> query = em.createQuery(
                "SELECT c, t, cl " +
                        "FROM Course c " +
                        "LEFT JOIN c.teacher t " +
                        "LEFT JOIN c.classroom cl", Object[].class);

        List<Object[]> resultList = query.getResultList();
        String format = "%-10s%-20s%-30s%s%n";
        System.out.printf(format, "ID:", "Course:", "Teacher:", "Classroom:");
        System.out.printf(format, "--", "------", "---------", "----------");

        List<String> returnToFX = new ArrayList<>();

        for (Object[] result : resultList) {
            Course course = (Course) result[0];
            Teacher teacher = (Teacher) result[1];
            Classroom classroom = (Classroom) result[2];
            if (course != null) {
                System.out.printf("%-10d%-20s", course.getId(), course.getName());
            }
            if (teacher != null) {
                System.out.printf("%-30s", teacher.getFirstName() + " " + teacher.getLastName());
            } else {
                System.out.printf("%-30s", "N/A");
            }
            if (classroom != null) {
                System.out.printf("%s%n", classroom.getClassroomName());
            } else {
                System.out.printf("%s%n", "N/A");
            }

            if (teacher != null && classroom != null) {
               String s = String.format("%-5s%-20s%-30s%s", String.valueOf(course.getId()),
                        course.getName(),
                        teacher.getFirstName(),
                        classroom.getClassroomName());
                returnToFX.add(s);
            }
        }

        return returnToFX;
    }
}