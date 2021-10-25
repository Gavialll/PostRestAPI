package com.example.serverpost.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String city;
    private String number;
    @Column(unique = true)
    private String login;
    private String password;
    @ManyToOne()
    private Role role;
    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> postList;
    private String img;

    public User() {
    }

    public User(String login) {
        this.login = login;
    }

    public User(String firstName, String lastName, String login, String password, Role role, String img) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.role = role;
        this.img = img;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(postList, user.postList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, postList);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", login='" + login + '\'' + ", password='" + password + '\'' + ", role=" + role.getName() + ", postList=" + postList + ", img='" + img + '\'' + '}';
    }
}
