package com.dbsnack.domain.entities.trainings;

import com.dbsnack.domain.entities.users.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "trainings")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private TechnologyStack technologyStack;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Column(name = "required_hours", nullable = false)
    private int requiredHours;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    private List<Topic> topics;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    public Training() {
        this.setTopics(new ArrayList<>());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TechnologyStack getTechnologyStack() {
        return technologyStack;
    }

    public void setTechnologyStack(TechnologyStack technologyStack) {
        this.technologyStack = technologyStack;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getRequiredHours() {
        return requiredHours;
    }

    public void setRequiredHours(int requiredHours) {
        this.requiredHours = requiredHours;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void addTopic(Topic topic) {
        this.topics.add(topic);
    }
}
