package com.smart.smartcontactmanager.controller;

import com.smart.smartcontactmanager.dao.ContactRepositry;
import com.smart.smartcontactmanager.dao.UserRepository;
import com.smart.smartcontactmanager.entities.Contact;
import com.smart.smartcontactmanager.entities.User;
import com.smart.smartcontactmanager.helper.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepositry contactRepositry;

    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.getUserByUserName(email);
        model.addAttribute("user", user);

    }

    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("title", "Home");
        return "normal/user_dashboard";
    }

    @GetMapping("/add-contact")
    public String addContact(Model model) {
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());
        return "normal/add_contact";
    }

    @PostMapping("/register-contact")
    public String registerContact(
            @ModelAttribute Contact contact,
            @RequestParam("profileImage") MultipartFile file,
            Principal principal, HttpSession session) {
        try {
            String email = principal.getName();
            User user = userRepository.getUserByUserName(email);
            //file uploding
            if (file.isEmpty()) {
                System.out.println("file is empty");
                contact.setImage("contact.png");

            } else {
                contact.setImage(file.getOriginalFilename());
                File file1 = new ClassPathResource("/static/images").getFile();
                Path path = Paths.get(file1.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is uploaded");
            }
            contact.setUser(user);
            user.getContacts().add(contact);
            userRepository.save(user);
            System.out.println(contact);
            System.out.println("Added to database");
            session.setAttribute("message", new Message("Your Contact is Added Successfully..","success"));
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("message", new Message("Something Went Wrong..","danger"));
        }
        return "normal/add_contact";
    }
    @GetMapping("/show-contacts/{page}")
    public String showContacts(@PathVariable("page") Integer page, Model model,Principal principal){
        model.addAttribute("title","Show User contacts");
        String email = principal.getName();
        User user = userRepository.getUserByUserName(email);
        Pageable pageable = PageRequest.of(page, 3);
        Page<Contact> contacts = contactRepositry.findContactByUser(user.getId(),pageable);
        model.addAttribute("contacts",contacts);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",contacts.getTotalPages());
        return "normal/show_contacts";
    }
    @GetMapping("/contact/{conId}")
    public String showContactDetailById(@PathVariable("conId") Integer conId,Model model,Principal principal){
        Optional<Contact> optionalContact = contactRepositry.findById(conId);
        Contact contact = optionalContact.get();

        String email = principal.getName();
        User user = userRepository.getUserByUserName(email);
        if (user.getId() == contact.getUser().getId()) {
            model.addAttribute("contact", contact);
            model.addAttribute("title",contact.getName());
        }
            return "normal/contact_detail";
    }
    @GetMapping("/delete/{conId}")
    public String deleteContact(@PathVariable("conId") Integer conId,Model model,HttpSession session){
        Optional<Contact> optionalContact = contactRepositry.findById(conId);
        Contact contact = optionalContact.get();
        contactRepositry.delete(contact);
        session.setAttribute("message",new Message("Contact deleted succesfully","success"));
        return "redirect:/user/show-contacts/0";
    }

}
