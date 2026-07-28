package sample.thymeleaf.web;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sample.common.dao.entity.Task;
import sample.service.TaskService;
import sample.common.logic.SessionKeys;

@Controller
@RequestMapping("/tasks")
public class TaskController {

	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@GetMapping()
	public String list(@RequestParam(defaultValue = "1") int page, HttpSession session, Model model) {
		String username = SessionKeys.currentUsername(session);
		model.addAttribute("page", taskService.findPage(username, page));
		return "tasks/list";
	}

	@GetMapping("/new")
	public String newForm() {
		return "tasks/form-new";
	}

	@PostMapping()
	public String create(@ModelAttribute Task task, HttpSession session) {
		String username = SessionKeys.currentUsername(session);
		task.setUsername(username);
		taskService.insert(task);
		return "redirect:/tasks";
	}

	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Long id, HttpSession session, Model model) {
		String username = SessionKeys.currentUsername(session);
		Task task = taskService.findById(id, username);
		model.addAttribute("task", task);
		return "tasks/form-edit";
	}

	@PostMapping("/update/{id}")
	public String update(@PathVariable Long id, @ModelAttribute Task task, HttpSession session) {
		String username = SessionKeys.currentUsername(session);
		task.setId(id);
		task.setUsername(username);
		taskService.update(task);
		return "redirect:/tasks";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable Long id, HttpSession session) {
		String username = SessionKeys.currentUsername(session);
		taskService.delete(id, username);
		return "redirect:/tasks";
	}
}