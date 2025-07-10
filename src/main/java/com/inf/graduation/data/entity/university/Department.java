package com.inf.graduation.data.entity.university;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "department")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department")
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "department")
    private Set<Teacher> teachers = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "head_id", nullable = true)
    private Teacher headOfDepartment;

    public void addStudent(Student student) {
        if(students == null) return;

        students.add(student);
    }

    public void addTeacher(Teacher teacher) {
        if(teacher == null) return;

        teachers.add(teacher);
    }
}
