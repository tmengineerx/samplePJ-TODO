package sample.thymeleaf.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String register(@Valid @ModelAttribute Login login, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "ユーザー名・パスワードは半角英数字で入力してください");
			return "register";
		}
		boolean success = loginService.register(login);
		if (!success) {
			redirectAttributes.addFlashAttribute("error", "そのユーザー名は既に使用されています");
			return "redirect:/register";
		}
		return "redirect:/login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam String username,
	@RequestParam String password, HttpSession session,
	RedirectAttributes redirectAttributes) {
		Login login = loginService.authenticate(username, password);
		if (login == null) {
			redirectAttributes.addFlashAttribute("error", "ユーザー名またはパスワードが正しくありません");
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