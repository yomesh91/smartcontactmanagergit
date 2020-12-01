package com.smart.smartcontactmanager.controller;

import com.smart.smartcontactmanager.dao.ContactRepositry;
import com.smart.smartcontactmanager.dao.UserRepository;
import com.smart.smartcontactmanager.entities.Contact;
import com.smart.smartcontactmanager.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class SearchController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepositry contactRepositry;

    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query") String query, Principal principal){

        /*System.out.println(query);*/
        User user = userRepository.getUserByUserName(principal.getName());
        List<Contact> contacts = contactRepositry.findByNameContainingAndUser(query, user);
        return ResponseEntity.ok(contacts);
    }
}
