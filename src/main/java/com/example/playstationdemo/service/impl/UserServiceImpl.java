package com.example.playstationdemo.service.impl;

import com.example.playstationdemo.entity.User;
import com.example.playstationdemo.payload.response.ApiResponse;
import com.example.playstationdemo.payload.dto.UserDto;
import com.example.playstationdemo.repository.UserRepository;
import com.example.playstationdemo.service.UserService;
import com.example.playstationdemo.utills.CommonUtills;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(@Lazy UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ApiResponse save(UserDto dto) {
        ApiResponse result = new ApiResponse();
        try {
            userRepository.save(dto.mapToEntity());
            result.setMessage("Successfully Saved!!!");
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("Error on Saving User");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse edit(UserDto userDto) {
        ApiResponse result = new ApiResponse();
        try {
            User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
            userRepository.save(userDto.mapToEntity(user));
            result.setMessage("Successfully Edited!");
            result.setSuccess(true);
        }catch (EntityNotFoundException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("User not found");
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("Something is wrong please try again!");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse all(Integer page, Integer size) {
        ApiResponse result = new ApiResponse();
        try {
            Page<User> users = userRepository.findAll(CommonUtills.getPageableByCreatedAtAsc(page, size));
            result.setMessage("Ok");
            result.setSuccess(true);
            result.setData(users.getContent()
                    .stream()
                    .map(User::mapToDto)
                    .collect(Collectors.toList()));
            result.setTotalElements(users.getTotalElements());
            result.setTotalPages(users.getTotalPages());
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("Error on coming users!!!");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse getById(Long id) {
        ApiResponse result = new ApiResponse();
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("user not found"));
            result.setMessage("User came with this id = " + id);
            result.setSuccess(true);
            result.setData(user.mapToDto());
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("User not found");
        } catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("Something is wrong");
        }
        return result;
    }

    @Override
    public ApiResponse removeById(Long id) {
        ApiResponse result = new ApiResponse();
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
            user.setEnabled(false);
            userRepository.save(user);
            result.setMessage("User deleted, id = " + id);
            result.setSuccess(true);
        }catch (EntityNotFoundException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("User not found");
        }
        catch (Exception e){
            e.printStackTrace();
            result.setMessage("Something is wrong, ERROR");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse changePassword(String login, String password) {
        ApiResponse result = new ApiResponse();
        try {
            Optional<User> user = userRepository.findByLogin(login);
            if (!user.isPresent()){
                result.setMessage("User not found");
                result.setSuccess(false);
                return result;
            }
            user.get().setPassword(passwordEncoder.encode(password));
            result.setMessage("Successfully changed");
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("Something is wrong on editing password");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse filter(String fio, Integer page, Integer size) {
        ApiResponse result = new ApiResponse();
        try {
            if (fio == null){
                Page<User> users = userRepository.findAll(CommonUtills.simplePageable(page, size));
                result.setMessage("Successfully filtered");
                result.setSuccess(true);
                result.setData(users.getContent()
                        .stream()
                        .map(User::mapToDto)
                        .collect(Collectors.toList()));
                result.setTotalElements(users.getTotalElements());
                result.setTotalPages(users.getTotalPages());
                return result;
            }
            Page<User> users = userRepository.findAllByFio(fio, CommonUtills.simplePageable(page, size));
            result.setMessage("Users successfully came!");
            result.setSuccess(true);
            result.setData(users.stream().map(User::mapToDto).collect(Collectors.toList()));
            result.setTotalPages(users.getTotalPages());
            result.setTotalElements(users.getTotalElements());
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("Error on coming users");
            result.setSuccess(false);
        }
        return result;
    }

}
