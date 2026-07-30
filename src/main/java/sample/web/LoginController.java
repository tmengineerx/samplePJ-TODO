package sample.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sample.common.dao.entity.Login;
import sample.service.LoginService;
import sample.web.form.LoginForm;
import sample.web.form.RegisterForm;
import sample.common.logic.SessionKeys;

@Controller
public class LoginController {

	private final LoginService loginService;
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@GetMapping("/login")
	public String loginForm(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}

	@GetMapping("/register")
	public String registerForm(Model model) {
		model.addAttribute("registerForm", new RegisterForm());
		return "register";
	}

	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("registerForm") RegisterForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "register";
		}
		if (!loginService.register(form)) {
			bindingResult.rejectValue("username", "duplicate", "そのユーザー名は既に使用されています");
			return "register";
		}
		return "redirect:/login?registered";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult,
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return "login";
		}
		Login login = loginService.authenticate(form.getUsername(), form.getPassword());
		if (login == null) {
			log.warn("login failed. username={}", form.getUsername());
			bindingResult.reject("authFailed", "ユーザー名またはパスワードが正しくありません");
			return "login";
		}
		log.info("login succeeded. username={}", login.getUsername());

		// セッション固定攻撃対策: ログイン成功時に必ずセッションを作り直す
		HttpSession oldSession = request.getSession(false);
		if (oldSession != null) {
			oldSession.invalidate();
		}
		HttpSession newSession = request.getSession(true);
		newSession.setAttribute(SessionKeys.USERNAME, login.getUsername());
		return "redirect:/tasks";
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}