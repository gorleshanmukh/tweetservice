package com.gorley.tweetapp.controller;

import com.gorley.tweetapp.Repo.impl.DynamoDBRepoUser;
import com.gorley.tweetapp.model.JwtRequest;
import com.gorley.tweetapp.model.JwtResponse;
import com.gorley.tweetapp.model.TweetUser;
import com.gorley.tweetapp.service.TweetAppUserDetailsService;
import com.gorley.tweetapp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TweetAppUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private DynamoDBRepoUser dynamoDBRepoUser;

    @RequestMapping(value = "/ping")
    public @ResponseBody String ping() {
        return "pong";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody final JwtRequest jwtRequest) throws Exception {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    jwtRequest.getLoginId(), jwtRequest.getPassword()));
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            return ResponseEntity.ok(new JwtResponse("","","","Invalid Username or Password"));
        }
        final UserDetails userDetails= this.userDetailsService.loadUserByUsername(jwtRequest.getLoginId());
        final String token = this.jwtUtil.generateToken(userDetails);
        final TweetUser user = dynamoDBRepoUser.findByLoginId(userDetails.getUsername());
        return ResponseEntity.ok(new JwtResponse(token, user.getFirstName(), userDetails.getUsername() ,"OK"));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody final TweetUser userDetails) throws Exception {
        try {
            userDetailsService.registerUser(userDetails);
        } catch (Exception e) {
            return ResponseEntity.ok(new JwtResponse("","","",e.getMessage()));
        }
        return ResponseEntity.ok(new JwtResponse("","","","OK"));
    }

    @RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
    public boolean forgotPassword(@RequestBody final JwtRequest jwtRequest) throws Exception {
        try {
            userDetailsService.updateUser(jwtRequest);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @RequestMapping(path = "/getAllUser")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(dynamoDBRepoUser.getAllUser(), HttpStatus.OK);
    }
}
