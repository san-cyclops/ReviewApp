package mana.movie;

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
	public String viewHomePage() {
		return "index";
	}
	
	@GetMapping("/main")
	public String viewMainPage() {
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

		return "register_success";
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

		return "save_success";
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
	public Map<Long, String> getreviewList() {
		List<User> listUsers = userRepo.findAll();

		Map<Long, String> userList = new HashMap<Long, String>();

		for (User user : listUsers) {
			userList.put(user.getId(), user.getName());
		}
		return userList;
	}

}
