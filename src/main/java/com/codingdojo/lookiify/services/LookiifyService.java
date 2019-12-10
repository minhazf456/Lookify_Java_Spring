package com.codingdojo.lookiify.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codingdojo.lookiify.models.Song;
import com.codingdojo.lookiify.repositories.LookiifyRepository;

@Service
public class LookiifyService {
	private final LookiifyRepository lookiifyRepository;
	
	public LookiifyService(LookiifyRepository lookifyRepository ) {
		this.lookiifyRepository = lookifyRepository;
	}
	
	public List<Song> allSongs() {
		return lookiifyRepository.findAll();
	}
	
	public Song findSong(Long id) {
		Optional<Song> optionalSong = lookiifyRepository.findById(id);
        if(optionalSong.isPresent()) {
            return optionalSong.get();
        }
        else {
            return null;
        }
	}
	
	public Song addSong(Song rock) {
		return lookiifyRepository.save(rock);
	}
	
	public void deleteSong(Long id) {
		lookiifyRepository.deleteById(id);
	}
	
	
	public List<Song> getTopTen(){
		return lookiifyRepository.findTop10ByOrderByRatingDesc();
	}
	
	public List<Song> getSearchSongs(String artist){
		return lookiifyRepository.findByArtist(artist);
	}
}