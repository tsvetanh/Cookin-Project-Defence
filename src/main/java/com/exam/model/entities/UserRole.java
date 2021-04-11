package com.exam.model.entities;

import com.exam.model.entities.emuns.Role;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class UserRole extends BaseEntity {

    private Role role;

    @Enumerated(EnumType.STRING)
    public Role getRole() {
        return role;
    }

    public UserRole setRole(Role role) {
        this.role = role;
        return this;
    }
}
