package com.restaurants.users.Controller;

import com.restaurants.users.Dto.UserDto;
import com.restaurants.users.model.CreateUserRequestModel;
import com.restaurants.users.model.ResponseModel;
import com.restaurants.users.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class userController {

    @Autowired
    UserService userService;


    @PostMapping
    public ResponseEntity<ResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel user) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto= modelMapper.map(user,UserDto.class);
        UserDto createdUser=userService.createUser(userDto);
        ResponseModel responseModel = modelMapper.map(createdUser, ResponseModel.class);
        return  ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }
}
