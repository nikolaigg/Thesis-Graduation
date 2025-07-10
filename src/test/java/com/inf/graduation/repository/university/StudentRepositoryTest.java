package com.inf.graduation.repository.university;

import com.inf.graduation.data.entity.thesis.Thesis;
import com.inf.graduation.data.entity.university.Department;
import com.inf.graduation.data.entity.university.Student;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.data.repo.university.DepartmentRepository;
import com.inf.graduation.data.repo.university.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import java.util.Set;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;
    

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindAllStudentsByDepartmentId_ReturnsAllStudents() {
        Department department = new Department();

        Student student1 = new Student();
        student1.setDepartment(department);

        Student student2 = new Student();
        student2.setDepartment(department);

        persistAll(department, student1, student2);

        List<Student> students = studentRepository.findAllStudentsByDepartmentId(department.getId());

        assertThat(students.size()).isEqualTo(2);
    }

    @Test
    void testFindAllStudentsByDepartmentIdAndThesisApplicationNull_ReturnsCorrectStudents() {
        Department department = new Department();

        Student student1 = new Student();
        student1.setDepartment(department);
        Student student2 = new Student();
        student2.setDepartment(department);

        persistAll(department, student1, student2);

        List<Student> students = studentRepository.findByDepartmentIdAndThesisApplicationIsNull(department.getId());

        assertThat(students.size()).isEqualTo(2);
        assertThat(students.get(0).getThesisApplication()).isNull();
        assertThat(students.get(1).getThesisApplication()).isNull();
    }


    @Test
    void testFindAllStudentsByThesisSupervisorNotInList_ReturnsCorrectStudents() {
        Teacher supervisor1 = new Teacher();
        Teacher supervisor2 = new Teacher();
        Teacher supervisor3 = new Teacher();

        Thesis thesis1 = new Thesis();
        supervisor1.addThesis(thesis1);

        Thesis thesis2 = new Thesis();
        supervisor2.addThesis(thesis2);

        Thesis thesis3 = new Thesis();
        supervisor3.addThesis(thesis3);

        Student student1 = new Student();
        student1.setThesis(thesis1);
        thesis1.setStudent(student1);

        Student student2 = new Student();
        student2.setThesis(thesis2);
        thesis2.setStudent(student2);

        Student student3 = new Student();
        student3.setThesis(thesis3);
        thesis3.setStudent(student3);

        persistAll(supervisor1, supervisor2, supervisor3, thesis1, thesis2, thesis3, student1, student2, student3);

        List<Student> students = studentRepository.findByThesisThesisSupervisorIdNotIn(List.of(supervisor1.getId(), supervisor2.getId()));

        assertThat(students.size()).isEqualTo(1);
        assertThat(students.get(0).equals(student3)).isTrue();
        assertThat(students.get(0).getThesis().getThesisSupervisor().getId()).isEqualTo(supervisor3.getId());
    }

    @Test
    void testFindStudentsByIdList_ReturnsCorrectStudents() {
        Student student1 = new Student();
        Student student2 = new Student();

        persistAll(student1, student2);

        Set<Student> students = studentRepository.findByIdIn(List.of(student1.getId(), student2.getId()));

        assertThat(students.size()).isEqualTo(2);

    }

    @Test
    void testFindStudentByThesisId_ReturnsCorrectStudent() {
        Thesis thesis = new Thesis();
        Student student = new Student();

        student.setThesis(thesis);
        thesis.setStudent(student);

        persistAll(student,thesis);

        Student found = studentRepository.findByThesis_Id(thesis.getId());
        assertThat(found.getId()).isEqualTo(student.getId());
        assertThat(found.getThesis().getId()).isEqualTo(thesis.getId());
    }

    private void persistAll(Object ... entities) {
        for(Object entity : entities) {
            entityManager.persist(entity);
        }
        entityManager.flush();
    }
}
