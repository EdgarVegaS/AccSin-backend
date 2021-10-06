package com.accsin.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.accsin.SpringAppContext;
import com.accsin.models.request.UserLoginRequestModel;
import com.accsin.models.responses.UserRest;
import com.accsin.models.shared.dto.UserDto;
import com.accsin.services.interfaces.UserServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private ModelMapper mapper;

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager, ModelMapper mapper) {
        this.authenticationManager = authenticationManager;
        this.mapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {

            UserLoginRequestModel userModel = new ObjectMapper().readValue(request.getInputStream(),
                    UserLoginRequestModel.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userModel.getEmail(),
                    userModel.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        String username = ((User) authResult.getPrincipal()).getUsername();

        String token = Jwts.builder().setSubject(username)
                        .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_DATE))
                        .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();

        UserServiceInterface userService = (UserServiceInterface) SpringAppContext.getBean("userService");
        UserDto useDto = userService.getUser(username);
        UserRest userResponse = mapper.map(useDto, UserRest.class);
        userResponse.setToken(SecurityConstants.TOKEN_PREFIX + token);

        ObjectMapper objMapper = new ObjectMapper();
        String userJsonString = objMapper.writeValueAsString(userResponse);
        PrintWriter out = response.getWriter();

        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("UserId",useDto.getUserId());
        response.addHeader(SecurityConstants.HEADER_STRING,SecurityConstants.TOKEN_PREFIX + token);

        try {
            out.print(userJsonString);
        }finally{
            out.close();
        }
    }
}