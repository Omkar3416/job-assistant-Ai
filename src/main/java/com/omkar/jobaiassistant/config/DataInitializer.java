package com.omkar.jobaiassistant.config;

import com.omkar.jobaiassistant.entity.Role;
import com.omkar.jobaiassistant.entity.RoleName;
import com.omkar.jobaiassistant.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {

        if (!roleRepository.existsByName(RoleName.USER)) {
            roleRepository.save(
                    new Role(RoleName.USER)
            );
        }

        if (!roleRepository.existsByName(RoleName.ADMIN)) {
            roleRepository.save(
                    new Role(RoleName.ADMIN)
            );
        }
    }
}