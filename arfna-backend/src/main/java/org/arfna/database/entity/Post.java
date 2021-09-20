package org.arfna.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "posts")
public class Post implements Serializable {
    private static final long serialVersionUID = 390357598599325393L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name="s_id")
    private Subscriber author;

    @Column(name="title")
    private String title;

    @Column(name="markdown")
    private String markdown;

    @Column(name="thumbnail")
    private String thumbnail;

    @Column(name="is_submitted")
    private boolean isSubmitted;

    @Column(name="is_accepted")
    private boolean isAccepted;

    @Column(name="is_published")
    private boolean isPublished;

    @Column(name="created_at")
    private Date createdAt;

    @Column(name="last_updated")
    private Date lastUpdated;

    @PrePersist
    public void onCreate() {
        createdAt = new Date();
        lastUpdated = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        lastUpdated = new Date();
    }

    public int getId() {
        return id;
    }

    public Post setId(int id) {
        this.id = id;
        return this;
    }

    public Subscriber getAuthor() {
        return author;
    }

    public Post setAuthor(Subscriber author) {
        this.author = author;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Post setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getMarkdown() {
        return markdown;
    }

    public Post setMarkdown(String markdown) {
        this.markdown = markdown;
        return this;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Post setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public Post setSubmitted(boolean submitted) {
        isSubmitted = submitted;
        return this;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public Post setAccepted(boolean accepted) {
        isAccepted = accepted;
        return this;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public Post setPublished(boolean published) {
        isPublished = published;
        return this;
    }

    public void copy(Post other) {
        other.setTitle(this.getTitle());
        other.setMarkdown(this.getMarkdown());
        other.setThumbnail(this.getThumbnail());
        other.setSubmitted(this.isSubmitted());
        other.setAccepted(this.isAccepted());
        other.setPublished(this.isPublished());
    }
}
