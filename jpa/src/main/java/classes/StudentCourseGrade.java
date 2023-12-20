package classes;

import jakarta.persistence.*;

@Entity
@Table(name = "StudentCourseGrade", schema = "projekt7", uniqueConstraints = @UniqueConstraint(columnNames = {"studentId", "courseId"}))

public class StudentCourseGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gradeId")
    private Grade grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "courseId", nullable = false)
    private Course course;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "StudentCourseGrade{" +
                "id=" + id +
                ", grade=" + grade +
                ", student=" + student +
                ", course=" + course +
                '}';
    }
}