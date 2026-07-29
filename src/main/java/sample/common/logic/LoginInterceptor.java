
package sample.common.logic;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws IOException {
		if (SessionKeys.currentUsername(request.getSession()) == null) {
			response.sendRedirect("/login");
			return false;
		}
		return true;
	}
}