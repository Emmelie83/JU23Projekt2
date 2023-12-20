package classes;

import jakarta.persistence.*;

@Entity
@Table(name = "Classroom", schema = "projekt7")

public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer classroomId;

    @Column(name = "classroomName", nullable = false, length = 30)
    private String classroomName;

    @Column(name = "classroomCapacity", nullable = false)
    private Integer classroomCapacity;

    public Integer getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Integer classroomId) {
        this.classroomId = classroomId;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public Integer getClassroomCapacity() {
        return classroomCapacity;
    }

    public void setClassroomCapacity(Integer classroomCapacity) {
        this.classroomCapacity = classroomCapacity;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + classroomId +
                ", classroomName='" + classroomName + '\'' +
                ", classroomCapacity=" + classroomCapacity +
                '}';
    }
}