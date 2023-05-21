package com.events.aggregator.controller;

import com.events.aggregator.dto.UserDto;
import com.events.aggregator.entity.User;
import com.events.aggregator.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/register")
@AllArgsConstructor
public class RegisterController {

    private final UserService userService;

    @GetMapping("")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @PostMapping("/save")
    public String saveRegistration(@Valid @ModelAttribute("userDto") UserDto userDto,
                                   BindingResult result,
                                   Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", "duplicate",
                    "This email is already taken");
        }

        if (!Objects.equals(userDto.getPassword(), userDto.getPassword2())) {
            result.rejectValue("password2", "NotValid",
                    "Passwords do not match");
        }

        if (result.hasErrors()) {
            model.addAttribute("userDto", userDto);
            return "register";
        }

        userService.addUser(userDto);
        return "redirect:/register?success";
    }
}
