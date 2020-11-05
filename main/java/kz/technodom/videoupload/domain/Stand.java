package kz.technodom.videoupload.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Stand.
 */
@Entity
@Table(name = "stand")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Stand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "city")
    private String city;

    @Column(name="upd_start_time")
    private LocalTime updateStartTime;

    @Column(name="upd_end_time")
    private LocalTime updateEndTime;

    @OneToMany(mappedBy = "stand", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<VideoToStand> videosToStand = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Stand ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getStoreName() {
        return storeName;
    }

    public Stand storeName(String storeName) {
        this.storeName = storeName;
        return this;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCity() {
        return city;
    }

    public Stand city(String city) {
        this.city = city;
        return this;
    }

    public Set<VideoToStand> getVideosToStand() {
        return videosToStand;
    }

    public void setVideosToStand(Set<VideoToStand> videosToStand) {
        this.videosToStand = videosToStand;
    }

    public LocalTime getUpdateStartTime() {
        return updateStartTime;
    }

    public void setUpdateStartTime(LocalTime updateStartTime) {
        this.updateStartTime = updateStartTime;
    }

    public LocalTime getUpdateEndTime() {
        return updateEndTime;
    }

    public void setUpdateEndTime(LocalTime updateEndTime) {
        this.updateEndTime = updateEndTime;
    }

    public void setCity(String city) {
        this.city = city;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public Stand removeVideoToStandSet(VideoToStand videoToStand) {
        this.videosToStand.remove(videoToStand);
        videoToStand.setStand(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stand)) {
            return false;
        }
        return id != null && id.equals(((Stand) o).id);
    }


    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Stand{" +
            "id=" + getId() +
            ", ipAddress='" + getIpAddress() + "'" +
            ", storeName='" + getStoreName() + "'" +
            ", city='" + getCity() + "'" +
            "}";
    }
}
