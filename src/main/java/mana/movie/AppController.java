package mana.movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ReviewRepository reviewRepo;
	@Autowired
	private MovieRepository movieRepo;

	@GetMapping("")
	public String viewHomePage(Model model) {
		model.addAttribute("reviewList", getreviewList());
		return "index";
	}
	
	@GetMapping("/main")
	public String viewMainPage(Model model) {
		model.addAttribute("reviewList", getreviewList());
		return "main";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());

		return "signup_form";
	}

	@PostMapping("/process_register")
	public String processRegister(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		userRepo.save(user);

		return "main";
	}

	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> listUsers = userRepo.findAll();
		model.addAttribute("listUsers", listUsers);

		return "users";
	}

	@GetMapping("/addreview")
	public String showAddReviewForm(Model model) {
		model.addAttribute("review", new Review());
		model.addAttribute("movieList", getMovieList());
		model.addAttribute("userList", getuserList());
		return "addreview_form";
	}

	@PostMapping("/savereview")
	public String saveReview(Review review) {
		reviewRepo.save(review);

		return "main";
	}
	
	@GetMapping("/addmovie")
	public String showAddMovieForm(Model model) {
		model.addAttribute("movie", new Movie());
		
		Map<Integer,String> category = new HashMap<Integer, String>();
		Integer i =0;
		for (MyEnum type : MyEnum.values()) { 
			category.put(i,type.toString());
		}
		model.addAttribute("category", category);
		return "addmovie_form";
	}

	@PostMapping("/savemovie")
	public String saveMovie(Movie movie) {
		movieRepo.save(movie);

		return "main";
	}
	

	@GetMapping("/movies")
	public String listmovie(Model model) {
		List<User> listUsers = userRepo.findAll();
		model.addAttribute("listUsers", listUsers);
		return "movies";
	}

	@ModelAttribute("movieList")
	public Map<Long, String> getMovieList() {
		List<Movie> listMovies = movieRepo.findAll();

		Map<Long, String> movieList = new HashMap<Long, String>();

		for (Movie movie : listMovies) {
			movieList.put(movie.getId(), movie.getName());
		}
		return movieList;
	}
	@ModelAttribute("userList")
	public Map<Long, String> getuserList() {
		List<User> listUsers = userRepo.findAll();

		Map<Long, String> userList = new HashMap<Long, String>();

		for (User user : listUsers) {
			userList.put(user.getId(), user.getName());
		}
		return userList;
	}
	
	@ModelAttribute("reviewList")
	public List<ReviewRating> getreviewList() {
		
		//Map<Long,String> reviewList = new HashMap<Long, String>();
		
		
		List<ReviewRating> reviewList = new ArrayList<ReviewRating>();
		
		List<Review> listReview = reviewRepo.findAll();


		for (Review review : listReview) {
			ReviewRating reviewRating = new ReviewRating();
			User user = userRepo.findByID(review.getUserid()); 
			Movie movie = movieRepo.findByID(review.getMovieid()); 
			
			reviewRating.setMovie(movie.getName());
			reviewRating.setSummary(review.getSummary());
			long l= movie.getCategory();
			int i=(int)l;  
			reviewRating.setCategory(MyEnum.fromInteger(i).toString());
			reviewRating.setScore(review.getScore());
  
			reviewList.add(reviewRating);
			//reviewList.put(review.getMovieid(), user.getName() + " | " + movie.getName());
		}
		return reviewList;
	}
	
	public enum MyEnum {
		Comedy,
		Action,
		Suspense,
		Others;

	    public static MyEnum fromInteger(int x) {
	        switch(x) {
	        case 0:
	            return Comedy;
	        case 1:
	            return Action;
	        case 2:
	            return Suspense;
	        case 3:
	            return Others;
	        }
	        return null;
	    }
	}

}
