package com.example.playstationdemo.component;

import com.example.playstationdemo.entity.Role;
import com.example.playstationdemo.entity.User;
import com.example.playstationdemo.entity.enums.RoleType;
import com.example.playstationdemo.repository.RoleRepository;
import com.example.playstationdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${spring.datasource.initialization-mode}")
    private String mode;

    public DataLoader(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")){
            Role admin = roleRepository.save(new Role(RoleType.ROLE_ADMIN));
            Role worker = roleRepository.save(new Role(RoleType.ROLE_WORKER));
            userRepository.save(new User(
                        "Namozov",
                        "playBoy",
                        passwordEncoder.encode("9009"),
                        Collections.singleton(admin)
            ));
        }
    }
}
