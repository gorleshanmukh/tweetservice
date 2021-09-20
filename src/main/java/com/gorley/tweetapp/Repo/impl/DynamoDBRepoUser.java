package com.gorley.tweetapp.Repo.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.gorley.tweetapp.model.JwtRequest;
import com.gorley.tweetapp.model.TweetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DynamoDBRepoUser {

    @Autowired
    private DynamoDBMapper mapper;

    public void createUser(final TweetUser userDetails) throws Exception {
        final TweetUser user = mapper.load(TweetUser.class, userDetails.getLoginId());
        if (user == null) {
            mapper.save(userDetails);
        } else {
            throw new Exception("User already exists");
        }
    }

    public TweetUser findByLoginId(final String loginId) throws UsernameNotFoundException {
        return mapper.load(TweetUser.class, loginId);
    }

    public TweetUser findByContactNumber(final String contactNumber) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":contactNumber", new AttributeValue().withS(contactNumber));
        DynamoDBScanExpression query = new DynamoDBScanExpression()
                .withFilterExpression("contactNumber = :contactNumber")
                .withExpressionAttributeValues(eav);
        List<TweetUser> user = mapper.scan(TweetUser.class, query);
        if (user != null &&     user.size() > 0) {
            return user.get(0);
        }
        return null;
    }

    public TweetUser findByEmailId(final String emailId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":emailId", new AttributeValue().withS(emailId));
        DynamoDBScanExpression query = new DynamoDBScanExpression()
                .withFilterExpression("emailId = :emailId")
                .withExpressionAttributeValues(eav);
        List<TweetUser> user = mapper.scan(TweetUser.class, query);
        if (user != null && user.size() > 0) {
            return user.get(0);
        }
        return null;
    }

    public List<TweetUser> getAllUser() {
        List<TweetUser> user = mapper.scan(TweetUser.class, new DynamoDBScanExpression());
        for (TweetUser eachUser : user) {
            eachUser.setPassword("");
        }
        return user;
    }

    public void updatePassword(final JwtRequest userBean) {
        TweetUser user = findByLoginId(userBean.getLoginId());
        if (user != null) {
            user.setPassword(userBean.getPassword());
            mapper.save(user);
        } else {
            throw new UsernameNotFoundException("User does not exist");
        }
    }
}
