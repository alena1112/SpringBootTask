package com.task.springboottask.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

/**
 * Class for generate and validate tokens
 *
 */
public interface TokenGeneration {
    /**
     * @return userName from token
     */
    String getUserNameFromToken(String token);

    /**
     * @return expiration date from token
     */
    Date getExpirationDateFromToken(String token);

    /**
     * @return claims from token
     */
    <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);

    /**
     * @return token which generate based userDetails
     */
    String generateToken(UserDetails userDetails);

    /**
     * @return true is token is valud. Otherwise false
     */
    Boolean validateToken(String token, UserDetails userDetails);
}
