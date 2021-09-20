package com.gorley.tweetapp.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtRequest {
    String loginId;
    String password;

    @Override
    public String toString() {
        return "JwtRequest{" +
                "username='" + loginId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
