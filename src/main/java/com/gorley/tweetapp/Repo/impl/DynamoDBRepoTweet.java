package com.gorley.tweetapp.Repo.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.gorley.tweetapp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DynamoDBRepoTweet {

    @Autowired
    private DynamoDBMapper mapper;

    public void saveTweet(final Tweet tweetDetails) {
        mapper.save(tweetDetails);
    }

    public void saveLike(final LikeTweet likeTweet) {
        mapper.save(likeTweet);
    }

    public void saveReply(final ReplyTweet reply) {
        mapper.save(reply);
    }

    public List<ReplyTweet> getAllReply(String tweetId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":tweetId", new AttributeValue().withS(tweetId));
        DynamoDBScanExpression query = new DynamoDBScanExpression()
                .withFilterExpression("tweetId = :tweetId")
                .withExpressionAttributeValues(eav);
        return mapper.scan(ReplyTweet.class, query);
    }

    public List<LikeTweet> getLikesByTweetId(String tweetId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":tweetId", new AttributeValue().withS(tweetId));
        DynamoDBScanExpression query = new DynamoDBScanExpression()
                .withFilterExpression("tweetId = :tweetId")
                .withExpressionAttributeValues(eav);
        return mapper.scan(LikeTweet.class, query);
    }

    public List<LikeTweet> getAllLikes(List<LikeTweet> likes, String id) {
        List<LikeTweet> likesRefined = new ArrayList<>();
        for (LikeTweet like : likes) {
            if (like.getTweetId().equals(id)) {
                likesRefined.add(like);
            }
        }
        return likesRefined;
    }

    public List<ReplyTweet> getAllReplies(List<ReplyTweet> replies, String id) {
        List<ReplyTweet> repliesRefined = new ArrayList<>();
        for (ReplyTweet reply : replies) {
            if (reply.getTweetId().equals(id)) {
                repliesRefined.add(reply);
            }
        }
        return repliesRefined;
    }

    public List<TweetDTO> getAllTweets() {
        List<TweetDTO> tweetDTOList = new ArrayList<>();
        List<Tweet> tweets = mapper.scan(Tweet.class, new DynamoDBScanExpression());
        List<LikeTweet> likes = mapper.scan(LikeTweet.class, new DynamoDBScanExpression());
        List<ReplyTweet> replies = mapper.scan(ReplyTweet.class, new DynamoDBScanExpression());
        for(Tweet tweet : tweets) {
            TweetDTO tweetDTO = new TweetDTO();
            tweetDTO.setMessage(tweet.getMessage());
            tweetDTO.setId(tweet.getId());
            tweetDTO.setUserID(tweet.getUserID());
            tweetDTO.setTimeStamp(tweet.getTimeStamp());
            tweetDTO.setReplies(getAllReplies(replies, tweet.getId()));
            tweetDTO.setLikes(getAllLikes(likes, tweet.getId()));
            tweetDTOList.add(tweetDTO);
        }
        return tweetDTOList;
    }
}
