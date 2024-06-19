package com.api.auth.service;

import com.api.auth.dto.RegisterDTO;
import com.api.auth.dto.UserDTO;
import com.api.auth.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MappingService {

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO toDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User toModel(RegisterDTO registerDTO) {
        return modelMapper.map(registerDTO, User.class);
    }

}
