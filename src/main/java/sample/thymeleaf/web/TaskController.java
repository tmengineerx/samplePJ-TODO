package sample.thymeleaf.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;

import sample.thymeleaf.web.form.TaskForm;
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
	public String newForm(Model model) {
		model.addAttribute("taskForm", new TaskForm());
		return "tasks/form-new";
	}

	@PostMapping()
	public String create(@Valid @ModelAttribute("taskForm") TaskForm form, BindingResult bindingResult,
			HttpSession session) {
		if (bindingResult.hasErrors()) {
			return "tasks/form-new";
		}
		String username = SessionKeys.currentUsername(session);
		Task task = new Task();
		task.setTitle(form.getTitle());
		task.setContent(form.getContent());
		task.setName(form.getName());
		task.setStartDate(form.getStartDate());
		task.setEndDate(form.getEndDate());
		task.setUsername(username);
		taskService.insert(task);
		return "redirect:/tasks";
	}

	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Long id, HttpSession session, Model model) {
		String username = SessionKeys.currentUsername(session);
		Task task = taskService.findById(id, username);
		TaskForm form = new TaskForm();
		form.setTitle(task.getTitle());
		form.setContent(task.getContent());
		form.setName(task.getName());
		form.setStartDate(task.getStartDate());
		form.setEndDate(task.getEndDate());
		model.addAttribute("taskForm", form);
		model.addAttribute("taskId", id);
		return "tasks/form-edit";
	}

	@PostMapping("/update/{id}")
	public String update(@Valid @ModelAttribute("taskForm") TaskForm form, BindingResult bindingResult,
			@PathVariable Long id, HttpSession session, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("taskId", id);
			return "tasks/form-edit";
		}
		String username = SessionKeys.currentUsername(session);
		Task task = new Task();
		task.setId(id);
		task.setUsername(username);
		task.setTitle(form.getTitle());
		task.setContent(form.getContent());
		task.setName(form.getName());
		task.setStartDate(form.getStartDate());
		task.setEndDate(form.getEndDate());
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