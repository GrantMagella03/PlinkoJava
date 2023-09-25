package com.maxtraining.c40.Plinko.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Integer> {
	Optional<User> findByUsernameAndPassword(String username, String password);
	@Query(value = "select * from Users Order By score desc limit 1", nativeQuery = true)
	Optional<User> findTopScore();
}
