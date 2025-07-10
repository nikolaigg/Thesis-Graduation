package com.inf.graduation.repository.university;

import com.inf.graduation.data.entity.university.Department;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.data.repo.university.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testTeachersFindByDepartmentId_ReturnsCorrectTeachers() {
        Department department = new Department();
        Teacher teacher1 = new Teacher();
        Teacher teacher2 = new Teacher();

        teacher1.setDepartment(department);
        teacher2.setDepartment(department);

        entityManager.persist(department);
        entityManager.persist(teacher1);
        entityManager.persist(teacher2);
        entityManager.flush();

        List<Teacher> teachers = teacherRepository.findByDepartmentId(department.getId());

        assertThat(teachers.size()).isEqualTo(2);

        assertThat(teachers.get(0).getId()).isEqualTo(teacher1.getId());
        assertThat(teachers.get(0).getDepartment().getId()).isEqualTo(department.getId());

        assertThat(teachers.get(1).getId()).isEqualTo(teacher2.getId());
        assertThat(teachers.get(1).getDepartment().getId()).isEqualTo(department.getId());

    }

    @Test
    void testFindByIdList_ReturnsCorrectTeachers() {
        Teacher teacher1 = new Teacher();
        Teacher teacher2 = new Teacher();

        entityManager.persist(teacher1);
        entityManager.persist(teacher2);
        entityManager.flush();

        Set<Teacher> teachers = teacherRepository.findByIdIn(Set.of(teacher1.getId(), teacher2.getId()));

        assertThat(teachers.size()).isEqualTo(2);
    }
}
