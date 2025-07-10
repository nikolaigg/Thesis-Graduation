package com.inf.graduation.data.entity.thesis;

import com.inf.graduation.data.entity.university.Student;
import com.inf.graduation.data.entity.university.Teacher;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "thesis_defense_session")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ThesisDefenseSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate defenseDate;

    @ManyToMany
    @JoinTable(
            name = "thesis_defense_committee",
            joinColumns = @JoinColumn(name = "thesis_defense_session_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private Set<Teacher> committee = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "thesis_defense_students",
            joinColumns = @JoinColumn(name = "thesis_defense_session_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students = new HashSet<>();
}
