package com.chipviet.project.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A IdentityCard.
 */
@Entity
@Table(name = "identity_card")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IdentityCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "card_id")
    private String cardID;

    @Column(name = "name")
    private String name;

    @Column(name = "dob")
    private String dob;

    @Column(name = "home")
    private String home;

    @Column(name = "address")
    private String address;

    @Column(name = "sex")
    private String sex;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "doe")
    private String doe;

    @Column(name = "photo")
    private String photo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IdentityCard id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardID() {
        return this.cardID;
    }

    public IdentityCard cardID(String cardID) {
        this.setCardID(cardID);
        return this;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getName() {
        return this.name;
    }

    public IdentityCard name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return this.dob;
    }

    public IdentityCard dob(String dob) {
        this.setDob(dob);
        return this;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getHome() {
        return this.home;
    }

    public IdentityCard home(String home) {
        this.setHome(home);
        return this;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAddress() {
        return this.address;
    }

    public IdentityCard address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return this.sex;
    }

    public IdentityCard sex(String sex) {
        this.setSex(sex);
        return this;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return this.nationality;
    }

    public IdentityCard nationality(String nationality) {
        this.setNationality(nationality);
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDoe() {
        return this.doe;
    }

    public IdentityCard doe(String doe) {
        this.setDoe(doe);
        return this;
    }

    public void setDoe(String doe) {
        this.doe = doe;
    }

    public String getPhoto() {
        return this.photo;
    }

    public IdentityCard photo(String photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IdentityCard)) {
            return false;
        }
        return id != null && id.equals(((IdentityCard) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IdentityCard{" +
            "id=" + getId() +
            ", cardID='" + getCardID() + "'" +
            ", name='" + getName() + "'" +
            ", dob='" + getDob() + "'" +
            ", home='" + getHome() + "'" +
            ", address='" + getAddress() + "'" +
            ", sex='" + getSex() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", doe='" + getDoe() + "'" +
            ", photo='" + getPhoto() + "'" +
            "}";
    }
}
