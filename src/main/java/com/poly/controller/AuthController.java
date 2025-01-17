package com.poly.controller;

import java.security.Principal;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poly.DAO.UserDAO;
import com.poly.Service.UserService;
import com.poly.entity.Users;
import com.poly.Service.MailerService;

import net.bytebuddy.utility.RandomString;

@Controller
public class AuthController {



	@Autowired
	UserService accountService;

	@Autowired
	MailerService mailer;

	@CrossOrigin("*")
	@ResponseBody
	@RequestMapping("/rest/auth/authentication")
	public Object getAuthentication(HttpSession session) {
		return session.getAttribute("authentication");
	}
	
	@RequestMapping("/auth/login/form")
	public String logInForm(Model model, @ModelAttribute("Users") Users users) {
		model.addAttribute("Users", new Users());
		return "auth/login_register";
	}

	@RequestMapping("/auth/login/success")
    public String logInSuccess(Model model, @ModelAttribute("Users") Users user, Principal Principal ) {
        String username = Principal.getName();

        Users loggedInUser = accountService.findById(username);

        if (!loggedInUser.getChangedpass()) {
            return "redirect:/auth/changedpass";
        } else {
            //model.addAttribute("message", "Logged in successfully");
            return "redirect:/index";
        }
    }
	
	@RequestMapping("/auth/changedpass")
    public String changedPass(Model model) {
        return "auth/changedpass";
    }

	@PostMapping("/auth/doChangePassword")
    public String doChangePassword(@RequestParam("password") String password,
                                   @RequestParam("confirmPassword") String confirmPassword,
                                   Principal principal,
                                   Model model) {
        if (password.equals(confirmPassword)) {
            String username = principal.getName();

            Users user = accountService.findById(username);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);

            user.setPassword(hashedPassword);
            user.setChangedpass(true);

            accountService.updatePassword(user, password);

            return "redirect:/index";
        } else {
            model.addAttribute("error", "Password and Confirm Password do not match.");
            return "auth/changedpass";
        }
    }

	@RequestMapping("/auth/login/error")
	public String logInError(Model model, @Validated @ModelAttribute("Users") Users users, Errors errors) {
		if (errors.hasErrors()) {
			model.addAttribute("message", "Wrong login information!");
			return "auth/login_register";
		}
		return "auth/login_register";
	}

	@RequestMapping("/auth/unauthoried")
	public String unauthoried(Model model, @ModelAttribute("Users") Users users) {
		model.addAttribute("message", "You don't have access!");
		return "auth/login_register";
	}

	@RequestMapping("/auth/logout/success")
	public String logOutSuccess(Model model, @ModelAttribute("Users") Users users) {
		model.addAttribute("message", "You are logged out!");
		return "auth/login_register";
	}
	
	// OAuth2
	@RequestMapping("/oauth2/login/success")
	public String oauth2(OAuth2AuthenticationToken oauth2) {
		accountService.loginFromOAuth2(oauth2);
		return "forward:/auth/login_register/success";
	}

	@GetMapping("/auth/register")
    public String signUpForm(Model model) {
        model.addAttribute("Users", new Users());
        return "auth/register";
    }


    @PostMapping("/auth/register")
    public String signUpSuccess(Model model, @Validated @ModelAttribute("Users") Users account, Errors error,
                                HttpServletResponse response) throws Exception {
    	
    	if (error.hasErrors()) {
            model.addAttribute("message", "Please correct the error below!");
            return "auth/register";
        }

    	account.setPassword("1111111");
    	account.setToken("token");
        account.setChangedpass(false);
        
        accountService.create(account);

        model.addAttribute("message", "New account registration successful!");

        try {
            String generatedPassword = RandomString.make(8);
            account.setPassword(generatedPassword);
            accountService.updatePassword(account, generatedPassword);

            String emailMessage = "Thank you for registering!\nYour password: " + generatedPassword;
            mailer.sendEmail(account.getEmail(), emailMessage);
            model.addAttribute("message", "New account registration successful! Check your email for the password.");
            model.addAttribute("login", "Quay lại ");
        } catch (MessagingException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error while sending email");
        }
        //response.addHeader("refresh", "2;url=/auth/login/form");
        return "auth/register";
    }

	@GetMapping("/auth/forgot-password")
	public String forgotPasswordForm(Model model) {
		return "auth/forgot-password";
	}

	@PostMapping("/auth/forgot-password")
	public String processForgotPassword(@RequestParam("email") String email, HttpServletRequest request, Model model)
			throws Exception {
		try {
			String token = RandomString.make(50);
			accountService.updateToken(token, email);
			String resetLink = getSiteURL(request) + "/auth/reset-password?token=" + token;
			// mailer.sendEmail(email, resetLink);
			mailer.sendEmail(email, resetLink);
			model.addAttribute("message", "We have sent a reset password link to your email. "
					+ "If you don't see the email, check your spam folder.");
		} catch (MessagingException e) {
			e.printStackTrace();
			model.addAttribute("error", "Error while sending email");
		}
		return "auth/forgot-password";
	}

	@GetMapping("/auth/reset-password")
	public String resetPasswordForm(@Param(value = "token") String token, Model model) {
		Users account = accountService.getByToken(token);
		model.addAttribute("token", token);
		if (account == null) {
			model.addAttribute("message", "Invalid token!");
			return "redirect:/auth/login_register/form";
		}
		return "auth/reset-password";
	}

	@PostMapping("/auth/reset-password")
	public String processResetPassword(@RequestParam("token") String code, @RequestParam("password") String password,
			HttpServletResponse response, Model model) {
		Users token = accountService.getByToken(code);
		if (token == null) {
			model.addAttribute("message", "Invalid token!");
		} else {
			accountService.updatePassword(token, password);
			model.addAttribute("message", "You have successfully changed your password!");
			response.addHeader("refresh", "2;url=/auth/login_register/form");
		}
		return "auth/reset-password";
	}

	@GetMapping("/auth/change-password")
	public String changePasswordForm(Model model) {
		return "auth/change-password";
	}

	@PostMapping("/auth/change-password")
	public String processChangePassword(Model model, @RequestParam("username") String username,
			@RequestParam("password") String newPassword) {
		Users account = accountService.findById(username);
		accountService.changePassword(account, newPassword);
		model.addAttribute("message", "Change password successfully!");
		return "auth/change-password";
	}

	public String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}
}
