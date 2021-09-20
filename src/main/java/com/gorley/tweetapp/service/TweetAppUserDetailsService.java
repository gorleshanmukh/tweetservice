package com.gorley.tweetapp.service;

import com.gorley.tweetapp.Repo.impl.DynamoDBRepoUser;
import com.gorley.tweetapp.model.JwtRequest;
import com.gorley.tweetapp.model.TweetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TweetAppUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder bycrypt;

    @Autowired
    private DynamoDBRepoUser dynamoDBRepoUser;

    public String registerUser(final TweetUser userInfo) throws Exception {
        if (dynamoDBRepoUser.findByLoginId(userInfo.getLoginId()) != null) {
            throw new RuntimeException("Login Id already exists");
        } else if (dynamoDBRepoUser.findByEmailId(userInfo.getEmailId()) != null) {
            throw new RuntimeException("email Id already exists");
        } else if (dynamoDBRepoUser.findByContactNumber(userInfo.getContactNumber()) != null) {
            throw new RuntimeException("contact number already exists");
        } else {
            userInfo.setPassword(bycrypt.encode(userInfo.getPassword()));
            dynamoDBRepoUser.createUser(userInfo);
            return "User created successfully";
        }
    }

    public String updateUser(final JwtRequest userInfo) {
            userInfo.setPassword(bycrypt.encode(userInfo.getPassword()));
            dynamoDBRepoUser.updatePassword(userInfo);
            return "Password updated successfully";
    }

    @Override
    public UserDetails loadUserByUsername(final String loginId) throws UsernameNotFoundException {
        final TweetUser user = dynamoDBRepoUser.findByLoginId(loginId);
        if (user != null) {
            return new User(user.getLoginId(),user.getPassword(),new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("user not found");
        }
    }
}
