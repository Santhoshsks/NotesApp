package org.example.notes.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface UserService {

    public User getUserByEmail(String email);
}


