package kz.technodom.videoupload.repository;

import kz.technodom.videoupload.domain.VideoFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VideoFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoFileRepository extends JpaRepository<VideoFile, Long> {

}
