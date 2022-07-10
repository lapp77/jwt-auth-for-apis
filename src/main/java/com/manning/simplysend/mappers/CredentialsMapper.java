package com.manning.simplysend.mappers;

import com.manning.simplysend.dto.UserDTO;
import com.manning.simplysend.entities.UserCredentials;

public class CredentialsMapper {

    public static UserCredentials from(UserDTO userDTO) {
        UserCredentials uc = new UserCredentials();
        uc.setPassword(userDTO.getPassword());
        return uc;
    }
}
