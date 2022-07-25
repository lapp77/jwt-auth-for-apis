package com.manning.simplysend.api;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.manning.simplysend.dto.UserDTO;
import com.manning.simplysend.exceptions.InvalidProfileException;
import com.manning.simplysend.exceptions.UserAlreadyRegisteredException;
import com.manning.simplysend.services.UserService;

@RestController
@RequestMapping(path = "/v1/users")
public class UserController {

    UserService usersService;

    public UserController(UserService usersService) {
        this.usersService = usersService;
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public Page<UserDTO> listUsers(@RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        try {
            return usersService.listUsers(page, limit);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @GetMapping(value = "/me", produces = { MediaType.APPLICATION_JSON_VALUE })
    public UserDTO userInfo(@AuthenticationPrincipal String userEmailId) {
        try {
            return usersService.userInfo(userEmailId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createUser(@RequestBody UserDTO userDTO) {
        try {
            usersService.createUser(userDTO);
        } catch (UserAlreadyRegisteredException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } catch (InvalidProfileException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @PostMapping(value = "/{userId}/revoke")
    @ResponseStatus(code = HttpStatus.OK)
    public void revoke(@PathVariable("userId") Long userID) {
        try {
            usersService.revoke(userID);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }
}
