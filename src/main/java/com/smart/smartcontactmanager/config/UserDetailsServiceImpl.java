package com.smart.smartcontactmanager.config;

import com.smart.smartcontactmanager.dao.UserRepository;
import com.smart.smartcontactmanager.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user = userRepository.getUserByUserName(email);
       if (user==null){
           throw new UsernameNotFoundException("could not found user!!");
       }
       CustomUserDetails customUserDetails = new CustomUserDetails(user);

       return customUserDetails;
    }
}
