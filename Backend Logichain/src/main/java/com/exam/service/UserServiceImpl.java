package com.exam.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exam.custom_exceptions.ResourceNotFoundException;
import com.exam.dto.UserDTO;
import com.exam.entities.User;
import com.exam.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository repo;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAll() {
        return repo.findAll().stream()
                .map(e -> mapper.map(e, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getById(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return mapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO save(User user) {

        if (user.getPassword() != null && !user.getPassword().startsWith("$2")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return mapper.map(repo.save(user), UserDTO.class);
    }


    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        repo.deleteById(id);
    }

    @Override
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}