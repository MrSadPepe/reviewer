package dev.pepus.reviews.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    private String id;
    private String name;
    @Transient
    @OneToMany(mappedBy = "roles")
    @JsonManagedReference
    private List<User> users;
    public Role() {
    }

    public Role(String id) {
        this.id = id;
        this.name = (id.equals("0")) ? "peasant" : "admin";
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setRole(String id) {
        this.id = id;
        this.name = (id.equals("0")) ? "peasant" : "admin";
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id.equals(role.id) && name.equals(role.name) && users.equals(role.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, users);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}