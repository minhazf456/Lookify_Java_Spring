package com.codingdojo.lookiify.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.lookiify.models.Song;
import com.codingdojo.lookiify.services.LookiifyService;

@Controller
public class LookiifyController {
	
	private final LookiifyService lookiifyService;
	
	public LookiifyController(LookiifyService lookiifyService) {
	this.lookiifyService = lookiifyService;
	}
	
	@RequestMapping("/")
	public String index(Model model) {
		return "index.jsp";
	}
	
	@RequestMapping("/dashboard")
	public String dashboard(Model model) {
		List<Song> songs = lookiifyService.allSongs();
		model.addAttribute("songs", songs);
		return "dashboard.jsp";
	}
	
	
	@RequestMapping("/songs/new")
	public String addNew(@ModelAttribute("addNew") Song song, Model model) {
		return "new.jsp";
	}
	
	@RequestMapping(value="/process", method=RequestMethod.POST)
	public String process(@Valid @ModelAttribute("addNew") Song song, BindingResult result, Model model) {
	    if (result.hasErrors()) {
			List<Song> songs = lookiifyService.allSongs();
			model.addAttribute("songs", songs);
	        return "new.jsp";
	    }
	    else {
	        lookiifyService.addSong(song);
	        return "redirect:/dashboard";
	    }
	}
	
	@RequestMapping("/search/topten")
	public String topten(Model model) {
		List<Song> songs = lookiifyService.getTopTen();
		model.addAttribute("songs", songs);
		return "topten.jsp";
	}
	
	@RequestMapping("/songs/{id}")
	public String songs(@PathVariable("id") Long id, Model model) {
		Song song = lookiifyService.findSong(id);
		model.addAttribute("song", song);
		return "song.jsp";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		lookiifyService.deleteSong(id);
		return "redirect:/dashboard";
	}
	
	@RequestMapping("/search/{artist}")
	public String search(@PathVariable("artist") String artist, Model model) {
		List<Song> songs = lookiifyService.getSearchSongs(artist);
		model.addAttribute("artist", artist);
		model.addAttribute("songs", songs);
		return "search.jsp";
	}
	
	@PostMapping("/search")
	public String search(@RequestParam("artist") String artist) {
		return "redirect:/search/"+artist;
	}
}
