package com.gorley.tweetapp.controller;

import com.gorley.tweetapp.model.*;
import com.gorley.tweetapp.service.TweetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
public class Home {

    @Autowired
    private TweetServiceImpl tweetService;

    @PostMapping("/add")
    public ResponseEntity<Tweet> postTweet(@RequestBody Tweet tweet) throws Exception {
        tweet.setTimeStamp(LocalDateTime.now().toString());
        return new ResponseEntity<Tweet>(tweetService.saveTweet(tweet), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TweetDTO>> getAllTweets() throws Exception {
        return new ResponseEntity<>(tweetService.getAllTweets(), HttpStatus.OK);
    }

    @PostMapping("/reply/{tweetId}")
    public ResponseEntity<ReplyTweet> addReply(@PathVariable(name = "tweetId") String tweetId,
                                               @RequestBody ReplyTweet tweetReply) {
        return new ResponseEntity<>(tweetService.postReply(tweetReply, tweetId), HttpStatus.OK);
    }

    @GetMapping("/all/reply/{tweetId}")
    public ResponseEntity<List<ReplyTweet>> getAllReply(@PathVariable(name = "tweetId") String tweetId) {
        return new ResponseEntity<>(tweetService.getAllReplyByTweets(tweetId), HttpStatus.OK);
    }

    @PostMapping("/like/{tweetId}")
    public ResponseEntity<LikeTweet> likeTweet(@PathVariable(name = "tweetId") String tweetId) {
        return new ResponseEntity<LikeTweet>(tweetService.likeTweet(tweetId), HttpStatus.OK);
    }

}
