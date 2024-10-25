package com.vivid.time_sheet.controller;

import com.vivid.time_sheet.DTO.UserDto;
import com.vivid.time_sheet.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class Auth {

    @Autowired
    private LoginService loginService;

    // GET request for login page
    @GetMapping("/login")
    public String showLoginPage(Model model, HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("csrfToken", csrfToken);
        return "login";
    }


    // POST request for login submission
    @PostMapping("/login")
    public String login(HttpServletRequest request, @RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        boolean isAuthenticated = loginService.validateUser(email, password);

        if (isAuthenticated) {
            HttpSession session = request.getSession();
            session.setAttribute("email", email); // Set session attribute

            return "redirect:/dashboard"; // Redirect to dashboard upon successful login
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login"; // Show login page with error
        }
    }

    // GET request for dashboard page
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard"; // Render the dashboard template
    }

    // POST request for registering a new user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        boolean isRegistered = loginService.registerUser(userDto.getEmail(), userDto.getPassword(), userDto.getFname(),
                userDto.getLname(), userDto.getRole(), userDto.getStatus());

        if (isRegistered) {
            return ResponseEntity.ok("User registered successfully");
        } else {
            return ResponseEntity.status(500).body("Error registering user");
        }
    }

    // Handle logout
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate(); // Invalidate session

        return "redirect:/login"; // Redirect to login after logout
    }
}
