package com.blog.app.controller;
//
//import com.blog.app.securityblog.helper.JwtTokenHelper;
//import com.blog.app.securityblog.request.JwtAuthRequest;
//import com.blog.app.securityblog.response.JwtAuthResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1/auth/")
//public class AuthControllerA {
//    @Autowired
//    private JwtTokenHelper jwtTokenHelper;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @PostMapping("/login")
//    public ResponseEntity<JwtAuthResponse> createToken(
//            @RequestBody JwtAuthRequest jwtAuthRequest
//            ){
//        this.authenticate(jwtAuthRequest.getUsername(),jwtAuthRequest.getPassword());
//        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
//        String token = jwtTokenHelper.generateToken(userDetails);
//        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
//        jwtAuthResponse.setToken(token);
//        return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.OK);
//    }
//
//    private void authenticate(String username, String password)  {
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//
//            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//
//    }
//}

import com.blog.app.payloads.UsersDto;
import com.blog.app.securityblog.helper.JwtTokenHelper;
import com.blog.app.securityblog.request.JwtAuthRequest;
import com.blog.app.securityblog.response.JwtAuthResponse;
import com.blog.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private  UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public ResponseEntity<UsersDto> createUsers(@Valid @RequestBody UsersDto userDto){
        UsersDto createUserDto = this.userService.registerUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request)  {

        JwtAuthResponse response = new JwtAuthResponse();
        try {
            this.authenticate(request.getUsername(), request.getPassword());

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

            String token = this.jwtTokenHelper.generateToken(userDetails);


            response.setToken(token);

            return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            response.setToken(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.BAD_GATEWAY);
        }

    }

    private void authenticate(String username, String password) throws RuntimeException {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try{
            this.authenticationManager.authenticate(authenticationToken);
            }
        catch (RuntimeException e){
            System.out.println("No Valid Details");
           throw new RuntimeException("Invalid User name or Password");

        }



    }


}
