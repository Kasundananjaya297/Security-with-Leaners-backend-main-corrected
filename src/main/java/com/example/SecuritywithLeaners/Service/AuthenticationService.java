package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.DTO.UsersDTO;
import com.example.SecuritywithLeaners.Entity.Users;
import com.example.SecuritywithLeaners.Repo.UsersRepo;
import com.example.SecuritywithLeaners.Util.JWTUtils;
import com.example.SecuritywithLeaners.Util.SaveUer;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class AuthenticationService  {
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private SaveUer saveUer;

    public ResponseDTO SaveUser(UsersDTO usersDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if(!usersRepo.existsById(usersDTO.getUsername())){
                String EncodedPassword = passwordEncoder.encode(usersDTO.getPassword());
                usersDTO.setPassword(EncodedPassword);
                usersRepo.save(modelMapper.map(usersDTO, Users.class));
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setMessage("Success");
                responseDTO.setUsername(usersDTO.getUsername());
                usersDTO.setPassword("");
                responseDTO.setContent(usersDTO);
                responseDTO.setStatus(HttpStatus.ACCEPTED);
            }
            else{
                responseDTO.setCode(varList.RSP_DUPLICATED);
                responseDTO.setMessage("Duplicated");
                responseDTO.setContent(null);
                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
            }
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setMessage("Failed");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }
        return responseDTO;
    }
    public ResponseDTO validUser(UsersDTO usersDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            // Fetch the user by username
            if(usersRepo.existsById(usersDTO.getUsername())){
                Optional<Users> userOptional = usersRepo.findById(usersDTO.getUsername());
                Users user = userOptional.get();
                String storedPassword = user.getPassword();
                if ((usersRepo.existsById(usersDTO.getUsername()))&&(passwordEncoder.matches(usersDTO.getPassword(), storedPassword))) {
                    var jwt = jwtUtils.generateToken(user);
                    // Valid user
                    responseDTO.setToken(jwt);
                    responseDTO.setCode(varList.RSP_SUCCES);
                    responseDTO.setMessage("Valid User");
                    responseDTO.setRole(user.getRole());
                    responseDTO.setContent(true);
                    responseDTO.setUsername(user.getUsername());
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                } else {
                    responseDTO.setCode(varList.RSP_FAIL);
                    responseDTO.setMessage("Invalid User");
                    responseDTO.setContent(false);
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                }
            }else{
                responseDTO.setCode(varList.RSP_FAIL);
                responseDTO.setMessage("Invalid User");
                responseDTO.setContent(false);
                responseDTO.setStatus(HttpStatus.ACCEPTED);
            }

        } catch (Exception e) {
            // Handle exceptions
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }
        return responseDTO;
    }
    public ResponseDTO RefreshToken(ResponseDTO rDTO) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            String extractedUserName = jwtUtils.extractUsername(rDTO.getToken());
            Optional<Users> userOptional = usersRepo.findById(extractedUserName);

            if (jwtUtils.isTokenValid(rDTO.getToken(), userOptional.orElse(null))) {
                responseDTO.setToken(jwtUtils.generateToken(userOptional.get()));
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setMessage("Token Refreshed");
                responseDTO.setContent(null);
                responseDTO.setStatus(HttpStatus.ACCEPTED);
            } else {
                responseDTO.setCode(varList.RSP_FAIL);
                responseDTO.setMessage("Invalid Token");
                responseDTO.setContent(null);
                responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseDTO;
    }
    public Boolean SaveUserInternally(UsersDTO usersDTO){
        try {
            if(!usersRepo.existsById(usersDTO.getUsername())){
                String EncodedPassword = passwordEncoder.encode(usersDTO.getGeneratedPassword());
                usersDTO.setPassword(EncodedPassword);
                usersRepo.save(modelMapper.map(usersDTO, Users.class));
                return true;
            }
            else{
                return false;
            }
        }catch (Exception e){
            return false;
        }

    }
    public ResponseDTO updatePassword(UsersDTO usersDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if(usersRepo.existsById(usersDTO.getUsername())){
                Optional<Users> userOptional = usersRepo.findById(usersDTO.getUsername());
                Users user = userOptional.get();
                String storedPassword = user.getPassword();
                if ((usersRepo.existsById(usersDTO.getUsername()))&&(passwordEncoder.matches(usersDTO.getPassword(), storedPassword))) {
                    String EncodedPassword = passwordEncoder.encode(usersDTO.getNewPassword());
                    user.setPassword(EncodedPassword);
                    user.setGeneratedPassword("");
                    user.setIsActive(true);
                    usersRepo.saveAndFlush(user);
                    responseDTO.setCode(varList.RSP_SUCCES);
                    responseDTO.setMessage("Password Updated");
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                } else {
                    responseDTO.setCode(varList.RSP_FAIL);
                    responseDTO.setMessage("Invalid User");
                    responseDTO.setContent(null);
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                }
            }
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setMessage("Failed");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }
        return responseDTO;
    }
    public ResponseDTO resetPassword(UsersDTO usersDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if(usersRepo.existsById(usersDTO.getUsername())){
                Optional<Users> userOptional = usersRepo.findById(usersDTO.getUsername());
                Users user = userOptional.get();
                usersDTO.setGeneratedPassword(SaveUer.generateRandomPassword(usersDTO.getUsername()));
                String EncodedPassword = passwordEncoder.encode(usersDTO.getGeneratedPassword());
                user.setPassword(EncodedPassword);
                user.setGeneratedPassword(usersDTO.getGeneratedPassword());
                user.setIsActive(true);
                usersRepo.saveAndFlush(user);
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setMessage("Password Updated");
                responseDTO.setStatus(HttpStatus.ACCEPTED);
            }else {
                responseDTO.setCode(varList.RSP_ERROR);
                responseDTO.setMessage("Invalid User");
                responseDTO.setContent(null);
                responseDTO.setStatus(HttpStatus.ACCEPTED);
            }
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setMessage("Failed");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }
        return responseDTO;
    }



}
