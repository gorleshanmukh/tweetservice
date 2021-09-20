package com.gorley.tweetapp.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TweetDTO {

    public String id;

    private String message;

    private String userID;

    private TweetUser user;

    private String timeStamp;

    private List<ReplyTweet> replies;

    private List<LikeTweet> likes;

}
