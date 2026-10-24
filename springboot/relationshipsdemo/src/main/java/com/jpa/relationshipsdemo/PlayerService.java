package com.jpa.relationshipsdemo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PlayerService {

	@Autowired
	PlayerRepository repo;
	
	//get all players
	public List<Player> allPlayers() {
		return repo.findAll();	    
	}
	//get player by id
	public Player getPlayer(int id){
		return repo.findById(id).get();
	}
	//add player
    public Player addPlayer(Player player) {
    	player.setId(0);

    	return repo.save(player);
	}
    //delete player
 	public void deletePlayer(int id) {
		repo.deleteById(id);
	}
 	//assign a profile to a player
 	public Player assignProfile(int id, PlayerProfile profile) {
 		Player player = repo.findById(id).get();
 		player.setPlayerProfile(profile);
 		return repo.save(player);
	} 
}