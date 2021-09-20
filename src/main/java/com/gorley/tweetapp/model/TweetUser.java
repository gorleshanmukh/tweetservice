package com.gorley.tweetapp.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamoDBTable(tableName = "tweetUser")
public class TweetUser implements Serializable {

    private static final long serialVersionUID = 2771413367966271864L;

    private String firstName;

    private String lastName;

    @DynamoDBHashKey
        private String loginId;

        private String emailId;

    private String contactNumber;

    private String password;

}