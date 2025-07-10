    package com.inf.graduation.data.entity.user;

    import jakarta.persistence.*;
    import lombok.*;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;

    import java.util.*;

    @Entity
    @Table(name = "app_user")
    @AllArgsConstructor
    @Getter
    @Setter
    public class User implements UserDetails{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String username;
        private String password;

        @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinTable(
                name = "role_users",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id")
        )
        private Set<Role> roles = new HashSet<>();
        private boolean accountNonExpired;
        private boolean accountNonLocked;
        private boolean credentialsNonExpired;
        private boolean enabled ;

        @OneToOne(cascade = CascadeType.REMOVE)
        @JoinColumn(name = "profile_id")
        private UserProfile userProfile;

        public User() {
            this.accountNonExpired = true;
            this.accountNonLocked = true;
            this.credentialsNonExpired = true;
            this.enabled = true;
        }


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return this.roles;
        }
    }
