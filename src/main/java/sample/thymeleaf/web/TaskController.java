package sample.thymeleaf.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sample.service.TaskService;

@Controller
@RequestMapping("/tasks")
public class TaskController {
	
	@GetMapping
	public String list() {
		return "tasks/list";
	}
	
	@GetMapping("/new")
	public String newForm() {
		return "tasks/form-new";
	}
	
	@PostMapping()
	public String create() {
		return "redirect:/tasks";
	}
	
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Long id) {
		return "tasks/form-edit";
	}
	
	@PostMapping("/update/{id}")
	public String update(@PathVariable Long id) {
		return "redirect:/tasks";
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		return "redirect:/tasks";
	}
}