package org.launchcode.hello.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

import org.launchcode.hello.domain.enumeration.Rating;

/**
 * A SmileyRating.
 */
@Entity
@Table(name = "smiley_rating")
public class SmileyRating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "rating")
    private Rating rating;

    //should i declare enum here? or make a file somewhere else?
    enum rating {
        Happy,
        Neutral,
        Sad,
        Angry
    }

    @ManyToOne
    @JsonIgnoreProperties("smileyRatings")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rating getRating() {
        return rating;
    }

    public SmileyRating rating(Rating rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public SmileyRating user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SmileyRating)) {
            return false;
        }
        return id != null && id.equals(((SmileyRating) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SmileyRating{" +
            "id=" + getId() +
            ", rating='" + getRating() + "'" +
            "}";
    }
}
