package mana.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	@Query("SELECT u FROM Review u WHERE u.userid = ?1")
	public User findByUser(Long userid);
	
}
