package kz.technodom.videoupload.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A VideoToStand.
 */
@Entity
@Table(name = "video_to_stand")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VideoToStand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("videosToStand")
    private VideoFile videoFile;

    @ManyToOne
    @JsonIgnoreProperties("videosToStand")
    private Stand stand;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Almaty")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Almaty")
    private LocalDateTime endDate;

    @Column(name = "is_uploaded")
    private boolean isUploaded;

    @Column(name = "is_failed_to_upload")
    private boolean isFailedToUpload;

    @Column(name = "is_failed_to_delete")
    private boolean isFailedToDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VideoFile getVideoFile() {
        return videoFile;
    }

    public VideoToStand videoFile(VideoFile videoFile) {
        this.videoFile = videoFile;
        return this;
    }

    public void setVideoFile(VideoFile videoFile) {
        this.videoFile = videoFile;
    }

    public Stand getStand() {
        return stand;
    }

    public VideoToStand stand(Stand stand) {
        this.stand = stand;
        return this;
    }

    public void setStand(Stand stand) {
        this.stand = stand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VideoToStand)) {
            return false;
        }
        return id != null && id.equals(((VideoToStand) o).id);
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    public boolean isFailedToUpload() {
        return isFailedToUpload;
    }

    public void setFailedToUpload(boolean failedToUpload) {
        isFailedToUpload = failedToUpload;
    }

    public boolean isFailedToDelete() {
        return isFailedToDelete;
    }

    public void setFailedToDelete(boolean failedToDelete) {
        isFailedToDelete = failedToDelete;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "VideoToStand{" +
            "id=" + getId() +
            "}";
    }
}
