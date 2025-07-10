    package com.inf.graduation.data.entity.thesis;

    import com.inf.graduation.data.entity.thesis.enums.ThesisStatus;
    import com.inf.graduation.data.entity.university.Student;
    import com.inf.graduation.data.entity.university.Teacher;
    import jakarta.persistence.*;
    import lombok.*;

    import java.time.LocalDate;

    @Entity
    @Table(name="thesis")
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public class Thesis {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;
        private String description;
        private LocalDate uploadDate;

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "student_id")
        private Student student;

        @ManyToOne(cascade = CascadeType.ALL)
        private Teacher thesisSupervisor;

        @OneToOne(mappedBy = "thesisReviewed", cascade = CascadeType.ALL)
        private ThesisReview review;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private ThesisStatus status = ThesisStatus.PENDING_REVIEW;

    }
