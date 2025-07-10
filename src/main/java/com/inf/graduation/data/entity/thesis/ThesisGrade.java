package com.inf.graduation.data.entity.thesis;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "thesis_grade")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ThesisGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "thesis_id", nullable = false)
    private Thesis thesis;

    private BigDecimal finalGrade;
}
