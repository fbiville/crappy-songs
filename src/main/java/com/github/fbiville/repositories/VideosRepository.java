package com.github.fbiville.repositories;

import com.github.fbiville.domain.Video;
import org.springframework.data.repository.CrudRepository;

public interface VideosRepository extends CrudRepository<Video, Long> {
}
