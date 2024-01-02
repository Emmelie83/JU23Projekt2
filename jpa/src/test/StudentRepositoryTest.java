

import classes.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import repositories.StudentRepository;


import java.util.Arrays;
import java.util.Collection;


public class StudentRepositoryTest {


    @Test
    void testGetStudentById1() {


    }


    @Test
    void testGetStudentById() {
            Student student = StudentRepository.getStudentByID(3);
            student.setFirstName("Arne");
            student.setLastName("Anka");
            StudentRepository.mergeStudent(student);
            Student student2 = StudentRepository.getStudentByID(3);
            assert (student2.getFirstName().equals("Arne"));

    }

}
