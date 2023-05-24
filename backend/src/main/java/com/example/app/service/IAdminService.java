package com.example.app.service;

import com.example.app.exceptions.AppException;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface IAdminService {
    public void dropAll();
    public void repopulateDb();
    public void changeRol(String user_handle, String rol) throws AppException;

    public void changeAll(Integer num) throws AppException;
}
