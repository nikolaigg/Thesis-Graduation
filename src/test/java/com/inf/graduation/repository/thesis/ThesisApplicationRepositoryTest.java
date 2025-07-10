package com.inf.graduation.repository.thesis;

import com.inf.graduation.data.entity.thesis.Thesis;
import com.inf.graduation.data.entity.thesis.ThesisApplication;
import com.inf.graduation.data.entity.thesis.enums.ThesisApplicationStatus;
import com.inf.graduation.data.entity.university.Department;
import com.inf.graduation.data.entity.university.Student;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.data.repo.thesis.ThesisApplicationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ThesisApplicationRepositoryTest {
    
    @Autowired
    private ThesisApplicationRepository thesisApplicationRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindApplicationsBySupervisorId_ReturnsCorrectApplications() {
        Teacher supervisor1 = new Teacher();

        Student student1 = new Student();
        Student student2 = new Student();

        ThesisApplication application1 = new ThesisApplication();
        ThesisApplication application2 = new ThesisApplication();
        application1.setStudent(student1);
        application2.setStudent(student2);

        supervisor1.addApplication(application1);
        supervisor1.addApplication(application2);

        persistAll( student1, student2,supervisor1, application1, application2);

        List<ThesisApplication> foundApplications = thesisApplicationRepository
                .findByThesisSupervisor_Id(supervisor1.getId());

        assertThat(foundApplications.size()).isEqualTo(2);

        assertThat(foundApplications.get(0).getId()).isEqualTo(application1.getId());
        assertThat(foundApplications.get(0).getThesisSupervisor().getId()).isEqualTo(supervisor1.getId());

        assertThat(foundApplications.get(1).getId()).isEqualTo(application2.getId());
        assertThat(foundApplications.get(1).getThesisSupervisor().getId()).isEqualTo(supervisor1.getId());

    }

    @Test
    void testFindByStatusAndThesisSupervisorNot_ReturnsCorrectApplications() {
        Teacher supervisor1 = new Teacher();
        Teacher supervisor2 = new Teacher();

        Student student1 = new Student();
        Student student2 = new Student();

        ThesisApplication application1 = new ThesisApplication();
        application1.setStatus(ThesisApplicationStatus.PENDING);
        application1.setStudent(student1);
        supervisor1.addApplication(application1);

        ThesisApplication application2 = new ThesisApplication();
        application2.setStatus(ThesisApplicationStatus.PENDING);
        application2.setStudent(student2);
        supervisor2.addApplication(application2);

        persistAll(student1, student2, supervisor1, supervisor2,application1, application2);

        List<ThesisApplication> foundApplications = thesisApplicationRepository
                .findByStatusAndThesisSupervisorNot(ThesisApplicationStatus.PENDING, supervisor2);

        assertThat(foundApplications.size()).isEqualTo(1);

        assertThat(foundApplications.get(0).getId()).isEqualTo(application1.getId());
        assertThat(foundApplications.get(0).getStatus()).isEqualTo(ThesisApplicationStatus.PENDING);
        assertThat(foundApplications.get(0).getThesisSupervisor().getId()).isEqualTo(supervisor1.getId());
    }

    @Test
    void testFindByStatusAndThesisSupervisorDepartmentAndSupervisorNot_ReturnsCorrectApplications() {
        Department department = new Department();
        Student student1 = new Student();
        Student student2 = new Student();

        Teacher supervisor1 = new Teacher();
        supervisor1.setDepartment(department);

        Teacher supervisor2 = new Teacher();
        supervisor2.setDepartment(department);

        ThesisApplication application1 = new ThesisApplication();
        application1.setStatus(ThesisApplicationStatus.PENDING);
        application1.setStudent(student1);
        supervisor1.addApplication(application1);

        ThesisApplication application2 = new ThesisApplication();
        application2.setStatus(ThesisApplicationStatus.PENDING);
        application2.setStudent(student2);
        supervisor2.addApplication(application2);

        persistAll(department,student1,student2, supervisor1, supervisor2, application1, application2);

        List<ThesisApplication> foundApplications = thesisApplicationRepository
                .findByStatusAndThesisSupervisor_DepartmentAndThesisSupervisorNot(
                        ThesisApplicationStatus.PENDING, department, supervisor2);

        assertThat(foundApplications.size()).isEqualTo(1);

        assertThat(foundApplications.get(0).getId()).isEqualTo(application1.getId());
        assertThat(foundApplications.get(0).getStatus()).isEqualTo(ThesisApplicationStatus.PENDING);
        assertThat(foundApplications.get(0).getThesisSupervisor().getDepartment().getId()).isEqualTo(department.getId());
        assertThat(foundApplications.get(0).getThesisSupervisor().getId()).isEqualTo(supervisor1.getId());
    }

    @Test
    void testFindByStatusAndStudentDepartment_ReturnsCorrectApplications() {
        Department department = new Department();
        Teacher supervisor = new Teacher();

        Student student = new Student();
        student.setDepartment(department);

        ThesisApplication application = new ThesisApplication();
        application.setStatus(ThesisApplicationStatus.PENDING);
        application.setStudent(student);
        application.setThesisSupervisor(supervisor);

        persistAll(department, student,supervisor, application);

        List<ThesisApplication> foundApplications = thesisApplicationRepository
                .findByStatusAndStudent_Department(ThesisApplicationStatus.PENDING, department);

        assertThat(foundApplications.size()).isEqualTo(1);

        assertThat(foundApplications.get(0).getId()).isEqualTo(application.getId());
        assertThat(foundApplications.get(0).getStatus()).isEqualTo(ThesisApplicationStatus.PENDING);
        assertThat(foundApplications.get(0).getStudent().getDepartment().getId()).isEqualTo(department.getId());
    }

    @Test
    void testFindByStatus_ReturnsCorrectApplications() {
        Teacher supervisor = new Teacher();
        Student student = new Student();

        ThesisApplication application = new ThesisApplication();
        application.setStatus(ThesisApplicationStatus.PENDING);
        application.setStudent(student);
        application.setThesisSupervisor(supervisor);

        persistAll(student, supervisor, application);

        List<ThesisApplication> foundApplications = thesisApplicationRepository.findByStatus(ThesisApplicationStatus.PENDING);

        assertThat(foundApplications.size()).isEqualTo(1);

        assertThat(foundApplications.get(0).getId()).isEqualTo(application.getId());
        assertThat(foundApplications.get(0).getStatus()).isEqualTo(ThesisApplicationStatus.PENDING);
    }

    @Test
    void testFindByStudentId_ReturnsCorrectApplication() {
        Student student = new Student();
        Teacher supervisor = new Teacher();
        ThesisApplication application = new ThesisApplication();
        application.setStudent(student);
        application.setThesisSupervisor(supervisor);

        persistAll(student, supervisor, application);

        ThesisApplication foundApplication = thesisApplicationRepository.findByStudent_Id(student.getId());

        assertThat(foundApplication).isNotNull();
        assertThat(foundApplication.getId()).isEqualTo(application.getId());
        assertThat(foundApplication.getStudent().getId()).isEqualTo(student.getId());
    }

    @Test
    void testFindByStatusAndThesisSupervisorId_ReturnsCorrectApplications() {
        Teacher supervisor = new Teacher();
        Student student = new Student();

        ThesisApplication application = new ThesisApplication();
        application.setStatus(ThesisApplicationStatus.PENDING);
        application.setStudent(student);
        supervisor.addApplication(application);

        persistAll(student, supervisor, application);

        List<ThesisApplication> foundApplication = thesisApplicationRepository
                .findByStatusAndThesisSupervisor_Id(ThesisApplicationStatus.PENDING, supervisor.getId());

        assertThat(foundApplication.size()).isEqualTo(1);

        assertThat(foundApplication.get(0).getId()).isEqualTo(application.getId());
        assertThat(foundApplication.get(0).getStatus()).isEqualTo(ThesisApplicationStatus.PENDING);
        assertThat(foundApplication.get(0).getThesisSupervisor().getId()).isEqualTo(supervisor.getId());
    }

    private void persistAll(Object... entities) {
        for (Object entity : entities) {
            entityManager.persist(entity);
        }
        entityManager.flush();
    }
}
