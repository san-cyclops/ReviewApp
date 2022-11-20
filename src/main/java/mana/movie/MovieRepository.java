package mana.movie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie , Long> {
	@Query("SELECT u FROM Movie u")
	public List<Movie> findAll();
	
	@Query("SELECT u FROM Movie u WHERE u.id = ?1")
	public Movie findByID(Long movieid);
}
