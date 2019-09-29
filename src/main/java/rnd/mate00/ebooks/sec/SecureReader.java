package rnd.mate00.ebooks.sec;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "users")
public class SecureReader {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "username")
    private String userName;

    private String password;

    private boolean enabled;

    @ManyToMany
    @JoinTable(name = "users_roles")
    private Set<Role> roles = new HashSet<>();

    public SecureReader() {
    }

    public SecureReader(String name, String password, boolean enabled) {
        this.userName = name;
        this.password = password;
        this.enabled = enabled;
    }

    public SecureReader(String name, String password, boolean enabled, Set<Role> roles) {
        this.userName = name;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    public SecureReader(int id, String name, String password, boolean enabled) {
        this.id = id;
        this.userName = name;
        this.password = password;
        this.enabled = enabled;
    }

    public SecureReader(int id, String name, String password, boolean enabled, Set<Role> roles) {
        this.id = id;
        this.userName = name;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecureReader that = (SecureReader) o;
        return userName.equals(that.userName) &&
                password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password);
    }

    @Override
    public String toString() {
        return "SecureReader{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }
}
