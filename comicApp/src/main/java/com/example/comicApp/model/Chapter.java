package com.example.comicApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.security.auth.callback.CallbackHandler;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "chapter")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private  String title;

//    @Column(nullable = true, updatable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    @CreatedDate
//    private Date createdAt;
//
//    @Column(nullable = true)
//    @Temporal(TemporalType.TIMESTAMP)
//    @LastModifiedDate
//    private Date updatedAt;



    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comic_id")
    private Comic comic;

    public Comic getComics(){return comic;}

    public void setComic(Comic comics){this.comic = comics;}

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Chapter)) return false;
        return id!= null && id.equals(((Chapter) o ).getId());
    }
    @Override
    public  int hashCode(){
        return 31;
    }

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


}
