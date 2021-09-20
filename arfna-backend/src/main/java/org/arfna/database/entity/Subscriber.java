package org.arfna.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "subscribers")
public class Subscriber implements Serializable {
    private static final long serialVersionUID = -3132718808977114814L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role = "none";

    @OneToMany(mappedBy="id")
    private Set<Post> posts;


    public int getId() {
        return id;
    }

    public Subscriber setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Subscriber setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Subscriber setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Subscriber setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRole() {
        return role;
    }

    public Subscriber setRole(String role) {
        this.role = role;
        return this;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void addPost(Post p) {
        this.posts.add(p);
    }

    public void copy(Subscriber other) {
        other.setName(this.getName());
        other.setEmailAddress(this.getEmailAddress());
        other.setPassword(this.getPassword());
        other.setRole(this.getRole());
    }
}
