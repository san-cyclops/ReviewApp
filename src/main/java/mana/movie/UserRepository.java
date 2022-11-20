package mana.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.username = ?1")
	public User findByUser(String user);
	
	@Query("SELECT u FROM User u WHERE u.id = ?1")
	public User findByID(Long userid);
	
	
	
}
