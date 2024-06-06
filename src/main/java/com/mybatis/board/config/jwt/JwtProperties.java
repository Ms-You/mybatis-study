package com.mybatis.board.config.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties("jwt")
@Component
public class JwtProperties {
    private String issuer;
    private String secretKey;
    private Integer accessTokenExpire;
}
