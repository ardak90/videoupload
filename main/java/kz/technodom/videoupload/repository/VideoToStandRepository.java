package kz.technodom.videoupload.repository;

import kz.technodom.videoupload.domain.VideoFile;
import kz.technodom.videoupload.domain.VideoToStand;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the VideoToStand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoToStandRepository extends JpaRepository<VideoToStand, Long> {
    Optional<List<VideoToStand>> findAllByStartDateIsLessThanAndEndDateGreaterThan(LocalDateTime date, LocalDateTime date2);
    Optional<List<VideoToStand>> findAllByEndDateIsLessThan(LocalDateTime date);
    Optional<List<VideoToStand>> findAllByVideoFile(VideoFile videoFile);

}
