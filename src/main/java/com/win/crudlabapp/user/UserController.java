package com.win.crudlabapp.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepo;
    
    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "add-user";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "add-user";
        }

        userRepo.save(user);
        model.addAttribute("users", userRepo.findAll());
        
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));

        model.addAttribute("user", user);
        
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        if(result.hasErrors()){
            user.setId(id);
            return "update-user";
        }

        userRepo.save(user);
        model.addAttribute("users", userRepo.findAll());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));

        userRepo.delete(user);
        model.addAttribute("users", userRepo.findAll());
        return "index";
    }
}