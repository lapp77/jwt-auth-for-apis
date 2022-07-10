package com.manning.simplysend.mappers;

import com.manning.simplysend.dto.UserDTO;
import com.manning.simplysend.entities.User;

public class UserMapper {

    public static User fromDTO(UserDTO userDTO) {
        User u = new User();
        u.setFirstName(userDTO.getFirstName());
        u.setLastName(userDTO.getLastName());
        u.setAddress(userDTO.getAddress());
        u.setAge(userDTO.getAge());
        u.setEmail(userDTO.getEmailId());
        u.setRole(userDTO.getRole());
        u.setTag(userDTO.getTag());
        u.setPhone(userDTO.getPhone());
        return u;
    }

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setAddress(user.getAddress());
        dto.setAge(user.getAge());
        dto.setEmailId(user.getEmail());
        dto.setRole(user.getRole());
        dto.setTag(user.getTag());
        dto.setPhone(user.getPhone());
        if (user.getManager() != null) {
            dto.setManagerID(user.getManager().getId());
        }
        return dto;
    }

}
