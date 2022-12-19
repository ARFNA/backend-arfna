package org.arfna.database.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subscribers")
public class Subscriber implements Serializable {
    private static final long serialVersionUID = -3132718808977114814L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Expose
    private int id;

    @Column(name = "name")
    @Expose
    private String name;

    @Column(name = "email_address")
    @Expose
    private String emailAddress;

    @Column(name = "password")
    @Expose
    private String password;

    @Column(name = "role")
    @Expose
    private String role = "none";

    @OneToMany(mappedBy="author", fetch= FetchType.EAGER)
    @Expose
    private List<Post> posts;


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

    public List<Post> getPosts() {
        if (posts == null)
            return new ArrayList<>();
        return this.posts;
    }

    public void addPost(Post p) {
        if (posts == null)
            posts = new ArrayList<>();
        this.posts.add(p);
    }

    /**
     * USE WITH CARE
     * Do not use this when writing to the database, but when posting a response
     */
    public void setPostsToNull() {
        this.posts = null;
    }

    public void copyNewInformation(Subscriber other) {
        if (this.getName() != null && !this.getName().equals(other.getName()))
            other.setName(this.getName());
        if (this.getEmailAddress() != null && !this.getEmailAddress().equals(other.getEmailAddress()))
            other.setEmailAddress(this.getEmailAddress());
        if (this.getPassword() != null && !this.getPassword().equals(other.getPassword()))
            other.setPassword(this.getPassword());
        if (this.getRole() != null && !this.getRole().equals("none") && !this.getRole().equals(other.getRole()))
            other.setRole(this.getRole());
    }
}
