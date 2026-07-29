package sample.thymeleaf.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        log.info("invalid request parameter: name={}", e.getName());
        return "redirect:/tasks";
    }

    @ExceptionHandler(Exception.class)
    public String handleUnexpected(Exception e, RedirectAttributes redirectAttributes) {
        log.error("unexpected error", e);
        redirectAttributes.addFlashAttribute("error", "システムエラーが発生しました。時間をおいてお試しください。");
        return "redirect:/tasks";
    }
}