package com.inf.graduation.config;

import com.inf.graduation.data.entity.university.Student;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.data.entity.user.User;
import com.inf.graduation.data.entity.user.UserProfile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoggedUser {

    public static User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

    public static UserProfile getLoggedInUserProfile() {
        return getLoggedInUser().getUserProfile();
    }

    public static Teacher getLoggedInTeacher() {
        UserProfile profile = getLoggedInUserProfile();
        if (profile instanceof Teacher) {
            return (Teacher) profile;
        }
        throw new IllegalStateException("Logged-in user is not a teacher");
    }

    public static Student getLoggedInStudent() {
        UserProfile profile = getLoggedInUserProfile();
        if (profile instanceof Student) {
            return (Student) profile;
        }
        throw new IllegalStateException("Logged-in user is not a student");
    }
}
