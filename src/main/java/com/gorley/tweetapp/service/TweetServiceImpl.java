package com.gorley.tweetapp.service;

import com.gorley.tweetapp.Repo.impl.DynamoDBRepoTweet;
import com.gorley.tweetapp.Repo.impl.DynamoDBRepoUser;
import com.gorley.tweetapp.model.*;
import com.gorley.tweetapp.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TweetServiceImpl {

    @Autowired
    private DynamoDBRepoTweet dynamoDBRepoTweet;

    @Autowired
    private DynamoDBRepoUser dynamoDBRepoUser;

    public Tweet saveTweet(Tweet tweet) throws Exception {
        tweet.setUserID(UserUtil.getUserName());
        try {
            dynamoDBRepoTweet.saveTweet(tweet);
            return tweet;
        } catch (Exception e) {
            throw new Exception("Something went wrong while posting tweet");
        }
    }

    public List<TweetDTO> getAllTweets() {
        List<TweetDTO> tweets = dynamoDBRepoTweet.getAllTweets();
        for (TweetDTO tweet : tweets) {
            TweetUser tweetUser = dynamoDBRepoUser.findByLoginId(tweet.getUserID());
            tweetUser.setPassword("");
            tweet.setUser(tweetUser);
        }
        return tweets;
    }

    public ReplyTweet postReply(ReplyTweet tweetReply, String tweetId) {
        tweetReply.setTweetId(tweetId);
        tweetReply.setTimeStamp(LocalDateTime.now().toString());
        tweetReply.setUserID(UserUtil.getUserName());
        dynamoDBRepoTweet.saveReply(tweetReply);
        return tweetReply;
    }

    public List<ReplyTweet> getAllReplyByTweets(String tweetId) {
        return dynamoDBRepoTweet.getAllReply(tweetId);
    }

    public LikeTweet likeTweet(String tweetId) {
        LikeTweet like = new LikeTweet();
        like.setTweetId(tweetId);
        like.setLikeBy(UserUtil.getUserName());
        dynamoDBRepoTweet.saveLike(like);
        return like;
    }

}
