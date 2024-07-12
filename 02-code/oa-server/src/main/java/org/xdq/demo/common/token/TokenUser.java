package org.xdq.demo.common.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class TokenUser {

    private String userId;
    private String userName;

    private String userIP;


}
