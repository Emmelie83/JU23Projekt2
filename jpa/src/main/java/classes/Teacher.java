package classes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Teacher", schema = "projekt7")

public class Teacher {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "firstName", nullable = false, length = 30)
    private String firstName;

    @Column(name = "lastName", nullable = false, length = 30)
    private String lastName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}