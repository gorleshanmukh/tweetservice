//package com.gorley.tweetapp.Repo.impl;
//
//import com.gorley.tweetapp.Repo.UserRepo;
//import com.gorley.tweetapp.model.JwtRequest;
//import com.gorley.tweetapp.model.TweetUser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class UserRepoImpl implements UserRepo {
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Override
//    public Optional<TweetUser> findByLoginId(final String loginId) {
//        TweetUser user = mongoTemplate.findOne(Query.query(Criteria.where("loginId").is(loginId)),
//                TweetUser.class);
//        if (user != null)
//            return Optional.of(user);
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<TweetUser> findByEmailId(final String emailId) {
//        TweetUser user = mongoTemplate.findOne(Query.query(Criteria.where("emailId").is(emailId)),
//                TweetUser.class);
//        if (user != null)
//            return Optional.of(user);
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<TweetUser> findByContactNumber(final String contactNumber) {
//        TweetUser user = mongoTemplate.findOne(Query.query(Criteria.where("contactNumber").is(contactNumber)),
//                TweetUser.class);
//        if (user != null)
//            return Optional.of(user);
//        return Optional.empty();
//    }
//
//    @Override
//    public void saveUser(final TweetUser userInfoBean) {
//        mongoTemplate.save(userInfoBean);
//    }
//
//    @Override
//    public void updatePassword(final JwtRequest userBean) {
//        Update update = new Update();
//        update.set("password", userBean.getPassword());
//        mongoTemplate.updateFirst(Query.query(Criteria.where("loginId").is(userBean.getLoginId())), update,
//                TweetUser.class);
//    }
//
//    @Override
//    public List<TweetUser> getAllUser() {
//        List<TweetUser> user = mongoTemplate.findAll(TweetUser.class);
//        return user;
//    }
//}
