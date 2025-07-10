package com.inf.graduation.data.entity.university;

import com.inf.graduation.data.entity.thesis.Thesis;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teacher_grade")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Thesis thesis;

    @ManyToOne
    private Teacher teacher;

    private Integer grade;
}
