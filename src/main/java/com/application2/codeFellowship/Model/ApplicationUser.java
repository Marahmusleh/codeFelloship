package com.application2.codeFellowship.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class ApplicationUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(unique = true)
    private String username;

    private String password;
    private String firstName;
    private String lastName;
    private int dateOfBirth;
    private String bio;

    @OneToMany(mappedBy = "user")
    private List<Post> postUser;


    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "following_follower",
            joinColumns = @JoinColumn(name = "following_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    List<ApplicationUser> followers = new ArrayList<>();
    @ManyToMany(mappedBy = "followers")
    List<ApplicationUser> following = new ArrayList<>();

    public ApplicationUser() {
    }

    public ApplicationUser( String username, String password, String firstName, String lastName, int dateOfBirth, String bio) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
    }


    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public int getDateOfBirth() {
        return dateOfBirth;
    }


    public String getBio() {
        return bio;
    }

    public List<Post> getPostUser() {
        return postUser;
    }

    public void setPostUser(List<Post> postUser) {
        postUser = postUser;
    }

    public List<ApplicationUser> getFollowers() {
        return followers;
    }

    public void setFollowers(List<ApplicationUser> followers) {
        this.followers = followers;
    }

    public List<ApplicationUser> getFollowing() {
        return following;
    }

    public void setFollowing(List<ApplicationUser> following) {
        this.following = following;
    }
}
