package com.example.serverpost.model;

import com.example.serverpost.exception.post.PostPriceException;
import com.example.serverpost.exception.post.PostValidationException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Post{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    @Column(nullable = false)
    private LocalDateTime date;
    private Long category;
    private String name;
    private Integer price;
    private String img;
    private String description;
    @OneToMany(mappedBy = "postId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> commentList;


    public Post() {}


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        if(category == null)
            this.category = 0L;
        else
            this.category = category;

    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        if(price == null || price < 0) {
            throw new PostPriceException("price == null or price < 0");
        }
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null)
            name = "";
        if(name.length() > 100)
            throw new PostValidationException("name > 100char");
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description == null)
            description = "";
        if(description.length() > 500)
            throw new PostValidationException("description > 500char");
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id && Objects.equals(name, post.name) && Objects.equals(description, post.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "Post{" + "id=" + id + ", userId=" + userId + ", date=" + date + ", category=" + category + ", name='" + name + '\'' + ", price=" + price + ", img='" + img + '\'' + ", description='" + description + '\'' + ", commentList=" + commentList + '}';
    }
}
