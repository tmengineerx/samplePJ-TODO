package sample.thymeleaf.web;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sample.common.dao.entity.Login;
import sample.service.LoginService;

@Controller
public class LoginController {

	private final LoginService loginService;

	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}

	@GetMapping("/register")
	public String registerForm() {
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute Login login) {
		loginService.register(login);
		return "redirect:/login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam String username,
	@RequestParam String password, HttpSession session) {
		Login login = loginService.authenticate(username, password);
		if (login == null) {
			return "redirect:/login";
		}
		session.setAttribute("username", login.getUsername());
		return "redirect:/tasks";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}