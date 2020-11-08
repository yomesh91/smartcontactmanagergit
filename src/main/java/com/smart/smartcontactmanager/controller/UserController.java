package com.smart.smartcontactmanager.controller;

import com.smart.smartcontactmanager.dao.UserRepository;
import com.smart.smartcontactmanager.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @ModelAttribute
    public void addCommonData(Model model,Principal principal){
        String email = principal.getName();
        User user = userRepository.getUserByUserName(email);
        model.addAttribute("user",user);

    }
    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal){
        model.addAttribute("title","Home");
       return "normal/user_dashboard";
    }
    @GetMapping("/add-contact")
    public String addContact(Model model){
        model.addAttribute("title","Add Contact");
        return "normal/add_contact";
    }

}
