package com.sandbox.PhotoAppApiUsers.users.ui.controllers;

import com.sandbox.PhotoAppApiUsers.users.shared.UserDto;
import com.sandbox.PhotoAppApiUsers.users.ui.model.CreateUserRequestModel;
import com.sandbox.PhotoAppApiUsers.users.ui.model.CreateUserResponseModel;
import com.sandbox.PhotoAppApiUsers.users.ui.model.UserResponseModel;
import com.sandbox.PhotoAppApiUsers.users.ui.service.UsersService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private Environment environment;
    @Autowired
    private UsersService usersService;
//    @Value("${local.server.port}")
//    String port;
    @Value("${token.secret}")
    String tokenSecret;

    @GetMapping("/status/check")
    public String status(){
        return "Working on port " + environment.getProperty("local.server.port") + " using token " + tokenSecret;
//        return "Working on port " + port + " using token " + tokenSecret;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto createdUser = usersService.createUser(userDto);

        CreateUserResponseModel body = modelMapper.map(createdUser, CreateUserResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @GetMapping(value = "/{userId}", produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    @PreAuthorize("principal == #userId")
    @PostAuthorize("principal == returnObject.getBody().getUserId()")
    @PostAuthorize("principal == returnObject.body.userId")
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId){

        UserDto userDto = usersService.getUserByUserId(userId);
        UserResponseModel returnValue = new ModelMapper().map(userDto, UserResponseModel.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

}
