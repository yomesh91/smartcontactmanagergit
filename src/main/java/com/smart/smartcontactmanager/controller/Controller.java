package com.smart.smartcontactmanager.controller;

import com.smart.smartcontactmanager.dao.UserRepository;
import com.smart.smartcontactmanager.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("title","home");
        return "home";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("title","sign-up");
        return "signup";
    }
    @PostMapping("/register-user")
    public String registerUser(@ModelAttribute("user") User user, @RequestParam(value = "agreement",defaultValue = "false") boolean agreement, Model model){
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(agreement);
        userRepository.save(user);
        return "signup";
    }
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("title","login");
        return "login";
    }

   /* @GetMapping("/print")
    public String print(Model model){
        List<String> list = new ArrayList<String>();
        int i;
        for(i=0;i<=10;i++){
            System.out.println(i);
            list.add("Yomesh"+i);
            model.addAttribute("list",list);
        }
        return "print";
    }*/


   /* @Autowired
    private UserRepository userRepository;
    @GetMapping("/test")
    @ResponseBody
    public String test(){
        User user = new User();
        user.setName("Yomesh");
        userRepository.save(user);
        return "welcome";
    }*/
}
