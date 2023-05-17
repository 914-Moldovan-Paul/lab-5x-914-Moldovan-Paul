package com.example.app.service;

import com.example.app.exceptions.AppException;
import com.example.app.model.User;
import com.example.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class AdminService implements IAdminService{
    @Autowired
    private Environment env;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void dropAll() {
        try {

            Connection conn = DriverManager.getConnection(
                    env.getRequiredProperty("spring.datasource.url"),
                    env.getRequiredProperty("spring.datasource.username"),
                    env.getRequiredProperty("spring.datasource.password"));
            List<String> statementsStrings = Arrays.asList(
                    "SET FOREIGN_KEY_CHECKS = 0;",
                    "TRUNCATE TABLE review;",
                    "TRUNCATE TABLE hospital;",
                    "TRUNCATE TABLE doctor;",
                    "SET FOREIGN_KEY_CHECKS = 1;"
            );
            statementsStrings.forEach(statementString -> {
                try {
                    Statement statement = conn.createStatement();
                    statement.execute(statementString);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void repopulateDb() {
        try {

            Connection conn = DriverManager.getConnection(
                    env.getRequiredProperty("spring.datasource.url"),
                    env.getRequiredProperty("spring.datasource.username"),
                    env.getRequiredProperty("spring.datasource.password"));
            Scanner scanner = new Scanner(new BufferedReader(new FileReader("generate-sql.sql")));
            scanner.useDelimiter(";");

            while(scanner.hasNext()) {
                String statementString = scanner.next();
                Statement statement = conn.createStatement();
                statement.execute(statementString);
            }
            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeRol(String user_handle, String rol) throws AppException {
        Optional<User> userOptional = userRepository.findById(user_handle);
        if(userOptional.isEmpty()) {
            throw new AppException("User doesn't exist");
        }
        userOptional.get().setRol(rol);
        userRepository.save(userOptional.get());
    }
}
