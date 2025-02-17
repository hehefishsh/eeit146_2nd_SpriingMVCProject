package tw.eeit1462.springmvcproject.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import tw.eeit1462.springmvcproject.model.Employee;

@ControllerAdvice
public class GlobalControllerAdvice {

	@ModelAttribute
	public void addUserToModel(Model model) {
		// 獲取當前登入的用戶
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			// 假設當前用戶的詳細資料實現了 UserDetails 接口並有 getUsername() 等方法
			Object principal = authentication.getPrincipal();
			if (principal instanceof Employee) {
				Employee currentUser = (Employee) principal;
				model.addAttribute("employee", currentUser);
			}
		}
	}

}
