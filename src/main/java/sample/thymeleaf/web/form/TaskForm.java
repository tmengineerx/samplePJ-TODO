package sample.thymeleaf.web.form;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.AssertTrue;

public class TaskForm {
    @NotBlank(message = "タイトルを入力してください")
    @Size(max = 255, message = "タイトルは255文字以内で入力してください")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Size(max = 1000, message = "詳細は1000文字以内で入力してください")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Size(max = 100, message = "名前は100文字以内で入力してください")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private LocalDate startDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    private LocalDate endDate;

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @AssertTrue(message = "終了日は開始日以降の日付を入力してください")
    public boolean isValidPeriod() {
        if (startDate == null || endDate == null) {
            return true;
        }
        return !endDate.isBefore(startDate);
    }
}
