package sample.thymeleaf.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sample.common.exception.TaskNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(TaskNotFoundException.class)
    public String handleTaskNotFound(TaskNotFoundException e, RedirectAttributes redirectAttributes) {
        log.warn("task not found or not owned: {}", e.getMessage());
        redirectAttributes.addFlashAttribute("error", "対象のタスクが見つかりませんでした");
        return "redirect:/tasks";
    }
}