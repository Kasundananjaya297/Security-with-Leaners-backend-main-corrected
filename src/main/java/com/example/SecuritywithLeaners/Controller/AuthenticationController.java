package com.example.SecuritywithLeaners.Controller;

import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.DTO.UsersDTO;
import com.example.SecuritywithLeaners.Service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping( "api/authentication")
@CrossOrigin
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/addUser")
    public ResponseEntity AddUser(@RequestBody UsersDTO usersDTO){
        ResponseDTO responseDTO = authenticationService.SaveUser(usersDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());

    }
    @PostMapping("/ValidateUser")
    public ResponseEntity validateUser(@RequestBody UsersDTO usersDTO){
        System.out.println("validateUser+++++");
        ResponseDTO responseDTO = authenticationService.validUser(usersDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/refreshToken")
    public ResponseEntity refreshToken(@RequestBody ResponseDTO usersDTO){
        ResponseDTO responseDTO = authenticationService.RefreshToken(usersDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }

}
