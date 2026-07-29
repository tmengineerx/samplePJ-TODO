package sample.thymeleaf.web.form;

import jakarta.validation.constraints.NotBlank;

public class LoginForm {

    @NotBlank(message = "ユーザー名を入力してください")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank(message = "パスワードを入力してください")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}