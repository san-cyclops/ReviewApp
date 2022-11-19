package mana.movie;

public interface ReviewService {
	
	Review findById(Long id);
	
	void saveReview(Review review);
	
	void updateReview(Review Review);
	
	void deleteReviewByID(Long Id);


}
