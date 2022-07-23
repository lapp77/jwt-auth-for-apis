package com.manning.simplysend.services;

import org.springframework.data.domain.Page;

import com.manning.simplysend.dto.UserDTO;

public interface UserService {
    void createUser(UserDTO userDTO);

    Page<UserDTO> listUsers(Integer page, Integer limit);
}
