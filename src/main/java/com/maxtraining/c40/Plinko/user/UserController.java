package com.maxtraining.c40.Plinko.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/users")

public class UserController {
	@Autowired
	UserRepository userRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<User>> getAllUsers(){
		Iterable<User>Users = userRepo.findAll();
		return new ResponseEntity<Iterable<User>>(Users, HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserByID(@PathVariable int id){
		if (id <= 0) return new ResponseEntity(HttpStatus.BAD_REQUEST);
		Optional<User> user = userRepo.findById(id);
		if (user.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
		return new ResponseEntity(user.get(), HttpStatus.OK);
	}
	@GetMapping("/{username}/{password}")
	public ResponseEntity<User> signinUser(@PathVariable String username, @PathVariable String password){
		Optional<User> user = userRepo.findByUsernameAndPassword(username, password);
		if (user.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
		return new ResponseEntity(user.get(), HttpStatus.OK);
	}
	@GetMapping("/highscore")
	public ResponseEntity<User> getHighScore()
	{
		Optional<User> user = userRepo.findTopScore();
		if (user.isEmpty()) return new ResponseEntity(HttpStatus.BAD_REQUEST);
		return new ResponseEntity(user.get(), HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<User> addNewUser(@RequestBody User user) {
		if (user.getId() != 0) return new ResponseEntity(HttpStatus.BAD_REQUEST);
		userRepo.save(user);
		return new ResponseEntity(user, HttpStatus.CREATED);
	}
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable int id){
		if (user.getId() != id) return new ResponseEntity(HttpStatus.BAD_REQUEST);
		if (id <= 0) return new ResponseEntity(HttpStatus.BAD_REQUEST);
		userRepo.save(user);
		return new ResponseEntity(user, HttpStatus.OK);
	}
	@DeleteMapping("{id}")
	public ResponseEntity deleteUser(@PathVariable int id) {
		if (id <= 0) return new ResponseEntity(HttpStatus.BAD_REQUEST);
		userRepo.deleteById(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
