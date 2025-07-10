package com.inf.graduation.data.entity.university;

import com.inf.graduation.data.entity.thesis.Thesis;
import com.inf.graduation.data.entity.thesis.ThesisApplication;
import com.inf.graduation.data.entity.user.User;
import com.inf.graduation.data.entity.user.UserProfile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "student")
@Getter
@Setter
public class Student extends UserProfile {

    private String name;
    private String facultyNumber;

    @ManyToOne
    private Department department;

    @OneToOne(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private User user;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private ThesisApplication thesisApplication;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL,orphanRemoval = true)
    private Thesis thesis;


    public void setDepartment(Department department) {
        this.department = department;
        department.addStudent(this);
    }

}
