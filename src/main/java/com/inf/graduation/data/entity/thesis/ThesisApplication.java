package com.inf.graduation.data.entity.thesis;

import com.inf.graduation.data.entity.thesis.enums.ThesisApplicationStatus;
import com.inf.graduation.data.entity.university.Student;
import com.inf.graduation.data.entity.university.Teacher;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "thesis_application")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ThesisApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;
    private String objective;
    private String tasks;
    private String usedTechnologies;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", unique = true)
    private Student student;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher thesisSupervisor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ThesisApplicationStatus status = ThesisApplicationStatus.PENDING;
}
