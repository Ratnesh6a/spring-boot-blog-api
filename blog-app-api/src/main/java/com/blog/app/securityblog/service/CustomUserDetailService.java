package com.blog.app.securityblog.service;

import com.blog.app.entity.Users;

import com.blog.app.exception.ResouceNotFoundException;
import com.blog.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*@Service
public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // loading user from database by username
        Users user = this.userRepository.findByEmail(username).orElseThrow(()->new ResouceNotFoundException("User", "email"+username,0 ));

        return user;
    } // authentication through database

}*/
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //loading user from dataBase via his username
        Users user = this.userRepository.findByEmail(username).orElseThrow(()->new ResouceNotFoundException("User", "email"+username,0 ));
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("It is username given  "+user);
        return user;
    }
}
