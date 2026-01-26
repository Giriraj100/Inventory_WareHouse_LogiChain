package com.exam.service;


import java.util.List;

import com.exam.dto.UserDTO;
import com.exam.entities.User;

public interface IUserService {
    List<UserDTO> getAll();
    UserDTO getById(Long id);
    UserDTO save(User user);
    void delete(Long id);
    boolean checkPassword(String rawPassword, String encodedPassword);
}