package com.example.app.controller;

import com.example.app.exceptions.AppException;
import com.example.app.model.User;
import com.example.app.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@CrossOrigin
@Validated
@RestController
public class AdminController {
    @Autowired
    IAdminService adminService;

    @PostMapping("/admin/drop-all")
    public ResponseEntity<Map<String, String>> drop_all(
            @RequestAttribute("user") User user
    ) {
        Map<String, String> response = new HashMap<>();
        if (!Objects.equals(user.getUserRol().getName(), "admin")) {
            response.put("error", "Unauthorized for this endpoint");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        adminService.dropAll();
        response.put("message", "dropped");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/admin/recreate")
    public ResponseEntity<Map<String, String>> recreate(
            @RequestAttribute("user") User user
    ) {
        Map<String, String> response = new HashMap<>();
        if (!Objects.equals(user.getUserRol().getName(), "admin")) {
            response.put("error", "Unauthorized for this endpoint");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        adminService.repopulateDb();
        response.put("message", "dropped");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/admin/change-rol")
    public ResponseEntity<Map<String, String>> changeRol(
            @RequestAttribute("user") User user,
            @RequestParam("user_handle") String user_handle,
            @RequestParam("rol") String rol
    ) {
        Map<String, String> response = new HashMap<>();
        if (!Objects.equals(user.getUserRol().getName(), "admin")) {
            response.put("error", "Unauthorized for this endpoint");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        try {
            adminService.changeRol(user_handle, rol);
        } catch (AppException e) {
            response.put("error", e.toString());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/admin/changeall/{num}")
    public ResponseEntity<Map<String, String>> changeAll(@PathVariable("num") Integer num) {
        Map<String, String> response = new HashMap<>();
        try {
            adminService.changeAll(num);
        } catch (AppException e) {
            response.put("error", e.toString());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
