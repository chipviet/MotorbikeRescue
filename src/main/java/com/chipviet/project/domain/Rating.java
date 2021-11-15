package com.chipviet.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Rating.
 */
@Entity
@Table(name = "rating")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "star")
    private Double star;

    @Column(name = "comment")
    private String comment;

    @Column(name = "create_at")
    private String createAt;

    @JsonIgnoreProperties(value = { "user", "request" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Connection request;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rating id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getStar() {
        return this.star;
    }

    public Rating star(Double star) {
        this.setStar(star);
        return this;
    }

    public void setStar(Double star) {
        this.star = star;
    }

    public String getComment() {
        return this.comment;
    }

    public Rating comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreateAt() {
        return this.createAt;
    }

    public Rating createAt(String createAt) {
        this.setCreateAt(createAt);
        return this;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public Connection getRequest() {
        return this.request;
    }

    public void setRequest(Connection connection) {
        this.request = connection;
    }

    public Rating request(Connection connection) {
        this.setRequest(connection);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Rating user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rating)) {
            return false;
        }
        return id != null && id.equals(((Rating) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rating{" +
            "id=" + getId() +
            ", star=" + getStar() +
            ", comment='" + getComment() + "'" +
            ", createAt='" + getCreateAt() + "'" +
            "}";
    }
}
