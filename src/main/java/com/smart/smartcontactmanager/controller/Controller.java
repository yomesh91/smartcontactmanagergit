package com.smart.smartcontactmanager.controller;

import com.smart.smartcontactmanager.dao.UserRepository;
import com.smart.smartcontactmanager.entities.User;
import com.smart.smartcontactmanager.helper.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
        model.addAttribute("user",new User());
        return "signup";
    }
    @PostMapping("/register-user")
    public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result, @RequestParam(value = "agreement",defaultValue = "false") boolean agreement, Model model, HttpSession session){
       try{
           if(!agreement){
               throw new Exception("You have not check me out");
           }

           if (result.hasErrors()){
               model.addAttribute("user",user);
               return "signup";
           }
           user.setRole("ROLE_USER");
           user.setEnabled(true);
           user.setImageUrl("default.png");
           user.setPassword(passwordEncoder.encode(user.getPassword()));
           User user1 = userRepository.save(user);
           model.addAttribute("user",user1);
           Message msg = new Message();
           msg.setContent("Successfully Registered!!");
           msg.setType("alert-success");
           session.setAttribute("msg",msg);

       }catch(Exception e){
           e.printStackTrace();
           model.addAttribute("user",user);
           Message msg = new Message();
           msg.setContent("Something Went Wrong!! "+e.getMessage());
           msg.setType("alert-danger");
           session.setAttribute("msg",msg);
       }
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
