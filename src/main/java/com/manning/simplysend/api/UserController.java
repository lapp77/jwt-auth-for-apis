package com.manning.simplysend.api;

import com.manning.simplysend.dto.UserDTO;
import com.manning.simplysend.exceptions.InvalidProfileException;
import com.manning.simplysend.exceptions.UserAlreadyRegisteredException;
import com.manning.simplysend.services.UserService;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/v1/users")
public class UserController {

    UserService usersService;

    public UserController(UserService usersService) {
        this.usersService = usersService;
    }

    @GetMapping(name = "", produces = { MediaType.APPLICATION_JSON_VALUE })
    public Page<UserDTO> listUsers(@RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        try {
            return usersService.listUsers(page, limit);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @PostMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
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
}
