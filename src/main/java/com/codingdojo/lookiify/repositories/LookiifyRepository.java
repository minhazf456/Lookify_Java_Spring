package com.codingdojo.lookiify.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.lookiify.models.Song;

@Repository
public interface LookiifyRepository extends CrudRepository<Song, Long> {
	
	List<Song> findAll();
	
	List<Song> findByArtist(String artist);

	List<Song> findTop10ByOrderByRatingDesc();
}
