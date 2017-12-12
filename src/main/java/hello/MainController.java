package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/Users")
public class MainController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping(path="/List")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping(path="/Add")
	public @ResponseBody String addNewUser(@RequestParam String name
			, @RequestParam String email) {
		User n = new User();
		n.setName(name);
		n.setEmail(email);
		userRepository.save(n);
		return "Saved";
		
	}
	
	@GetMapping(path="/Update")
	public @ResponseBody String updateUser(@RequestParam int id, @RequestParam String name
			, @RequestParam String email) {
		User u = userRepository.findOne(id);
		u.setName(name);
		u.setEmail(email);
		userRepository.save(u);
		return "Updated";
		
	}
	
	@GetMapping(path="/Get") // Map ONLY GET Requests
	public @ResponseBody User getUser (@RequestParam int id) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		User u = userRepository.findOne(id);
		return u;
	}
	
	@GetMapping(path="/Delete") // Map ONLY GET Requests
	public @ResponseBody String deleteUser (@RequestParam int id) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		String msg = "";
		try {
			userRepository.delete(id);
			msg = "User "+id+" deleted";
		}
		catch (EmptyResultDataAccessException exc) {
			msg = "User "+id+" doesn't exist... can't delete!";
		}
		return msg;
	}
}
