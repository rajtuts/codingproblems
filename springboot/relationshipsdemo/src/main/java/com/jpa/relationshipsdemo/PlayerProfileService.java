package com.jpa.relationshipsdemo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PlayerProfileService {

	@Autowired
	PlayerProfileRepository repo;
	
	//get all profiles
	public List<PlayerProfile> allPlayerProfiles() {
		return repo.findAll();	    
	}
	//get profile by id
	public PlayerProfile getPlayerProfile(int id){
		return repo.findById(id).get();
	}
	//add profile
    public PlayerProfile addPlayerProfile(PlayerProfile profile) {
    	profile.setId(0);	
    	return repo.save(profile);
	}
    //delete profile
 	public void deletePlayerProfile(int id) {
 	    PlayerProfile tempPlayerProfile = repo.findById(id).get(); 

 	    //set the playerProfile field of the Player object to null
 	    tempPlayerProfile.getPlayer().setPlayerProfile(null);

 	    //set the player field of the PlayerProfile object to null
 	    tempPlayerProfile.setPlayer(null);

 	    //save changes
 	    repo.save(tempPlayerProfile);

 	    //delete the PlayerProfile object
 	    repo.delete(tempPlayerProfile); 
	}
 
}