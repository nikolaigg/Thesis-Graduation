package com.inf.graduation.data.entity.university;

import com.inf.graduation.data.entity.thesis.Thesis;
import com.inf.graduation.data.entity.thesis.ThesisApplication;
import com.inf.graduation.data.entity.user.User;
import com.inf.graduation.data.entity.user.UserProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teacher")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Teacher extends UserProfile {

    private String name;

    @ManyToOne
    private Department department;

    @OneToOne(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "thesisSupervisor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ThesisApplication> applications = new HashSet<>();

    @OneToMany(mappedBy = "thesisSupervisor", orphanRemoval = true)
    private Set<Thesis> theses = new HashSet<>();

    public void setDepartment(Department department) {
        this.department = department;
        department.addTeacher(this);
    }

    public void addThesis(Thesis thesis) {
        theses.add(thesis);
        thesis.setThesisSupervisor(this);
    }

    public void addApplication(ThesisApplication thesisApplication) {
        applications.add(thesisApplication);
        thesisApplication.setThesisSupervisor(this);
    }

}
