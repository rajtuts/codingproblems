package com.jpa.relationshipsdemo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
public class PlayerController {

	@Autowired
	PlayerService service;
	
	@Autowired
	PlayerProfileService profileService;
	
	@GetMapping
	public List<Player> allPlayers() {
		return service.allPlayers();	    
	}
	
	@GetMapping("/{id}")
	public Player getPlayer(@PathVariable int id){
		return service.getPlayer(id);
	}
	
	@PostMapping
	public Player addPlayer(@RequestBody Player player) {

		return service.addPlayer(player);
	}
	
	@DeleteMapping("/{id}")
	public void deletePlayer(@PathVariable int id) {
		service.deletePlayer(id);
	}
	
	@PutMapping("/{id}/profiles/{profile_id}")
	public Player assignDetail(@PathVariable int id, @PathVariable int profile_id) {
	    PlayerProfile profile = profileService.getPlayerProfile(profile_id); 
	    return service.assignProfile(id, profile);
	}
}