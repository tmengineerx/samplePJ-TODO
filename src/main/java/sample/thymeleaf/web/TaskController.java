package sample.thymeleaf.web;

import java.util.List;

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

@Controller
@RequestMapping("/tasks")
public class TaskController {
	
	private final TaskService taskService;
	
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	@GetMapping()
	public String list(@RequestParam(defaultValue = "1") int page, HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
		List<Task> tasks = taskService.findByUsername(username, page);
		int totalCount = taskService.countByUsername(username);
		int totalPages = (int) Math.ceil((double) totalCount / 10);
		model.addAttribute("tasks", tasks);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		return "tasks/list";
	}
	
	@GetMapping("/new")
	public String newForm() {
		return "tasks/form-new";
	}
	
	@PostMapping()
	public String create(@ModelAttribute Task task, HttpSession session) {
		String username = (String) session.getAttribute("username");
		task.setUsername(username);
		taskService.insert(task);
		return "redirect:/tasks";
	}
	
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Long id, HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
		Task task = taskService.findById(id, username);
		model.addAttribute("task", task);
		return "tasks/form-edit";
	}
	
	@PostMapping("/update/{id}")
	public String update(@PathVariable Long id, @ModelAttribute Task task, HttpSession session) {
		String username = (String) session.getAttribute("username");
		task.setId(id);
		task.setUsername(username);
		taskService.update(task);
		return "redirect:/tasks";
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable Long id, HttpSession session) {
		String username = (String) session.getAttribute("username");
		taskService.delete(id, username);
		return "redirect:/tasks";
	}
}