//package com.gorley.tweetapp.Repo.impl;
//
//import com.gorley.tweetapp.Repo.TweetRepo;
//import com.gorley.tweetapp.model.LikeTweet;
//import com.gorley.tweetapp.model.ReplyTweet;
//import com.gorley.tweetapp.model.Tweet;
//import com.gorley.tweetapp.model.TweetDTO;
//import com.gorley.tweetapp.util.UserUtil;
//import org.bson.types.ObjectId;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.aggregation.Aggregation;
//import org.springframework.data.mongodb.core.aggregation.LookupOperation;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class TweetRepoImpl implements TweetRepo {
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
////    @Override
////    public Optional<Tweet> saveTweet(Tweet tweet) {
////        return Optional.of(mongoTemplate.save(tweet));
////    }
//
//    @Override
//    public List<TweetDTO> getAllTweets() {
//
//        LookupOperation replyLookUp = LookupOperation.newLookup().from("reply").localField("_id")
//                .foreignField("tweetId").as("replies");
//
//        LookupOperation likeLookUp = LookupOperation.newLookup().from("like").localField("_id").foreignField("tweetId")
//                .as("likes");
//
//        Aggregation ag = Aggregation.newAggregation(replyLookUp, likeLookUp,
//                Aggregation.project("message", "timeStamp", "userID").and("replies").as("replies").and("likes").as("likes"));
//
//        List<TweetDTO> tweets = mongoTemplate.aggregate(ag, "tweet", TweetDTO.class).getMappedResults();
//
//        return tweets;
//    }
//
//    @Override
//    public ReplyTweet saveReply(ReplyTweet reply) {
//        reply.setUserID(UserUtil.getUserName());
//        return mongoTemplate.save(reply);
//    }
//
//    @Override
//    public List<ReplyTweet> getAllReply(String tweetId) {
//        final ObjectId tweetObjectID = new ObjectId(tweetId);
//        return mongoTemplate.find(Query.query(Criteria.where("tweetId").is(tweetObjectID)), ReplyTweet.class);
//    }
//
//    @Override
//    public LikeTweet saveLike(LikeTweet likeTweet) {
//        return mongoTemplate.save(likeTweet);
//    }
//
//    @Override
//    public List<LikeTweet> getAllLikesByUser(String userId) {
//        return mongoTemplate.find(Query.query(Criteria.where("likeBy").is(userId)), LikeTweet.class);
//    }
//}
