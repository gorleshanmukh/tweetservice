package com.gorley.tweetapp.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtResponse {
    String token;
    String userName;
    String loginId;
    String errorMessage;
}
