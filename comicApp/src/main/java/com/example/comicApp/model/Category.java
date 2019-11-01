package com.example.comicApp.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "category")
@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotBlank
    private String title;

    //@NotBlank
    private String content;

    private String name;

    private String urlName;

    public Category() {
        this.id = id;
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

//    @Column(nullable = true, updatable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    @CreatedDate
//    private Date createdAt;
//
//    @Column(nullable = true)
//    @Temporal(TemporalType.TIMESTAMP)
//    @LastModifiedDate
//    private Date updatedAt;

    @OneToMany(mappedBy = "category")
    private List<Comic> comics = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }

    public List<Comic> getComics() {
        return comics;
    }

}
