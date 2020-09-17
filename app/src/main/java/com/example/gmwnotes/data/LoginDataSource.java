package com.example.gmwnotes.data;

import com.example.gmwnotes.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    //ususario 'jfreire' mockado para teste rapido
    LoggedInUser user = new LoggedInUser("jfreire", "jfreire", "admin", 7.0);

    public Result<LoggedInUser> login(String username, String password) {
        if(user.getUserId().equals(username) && user.getPassword().equals(password)){
            return new Result.Success<>(user);
        }
        else{
            return new Result.Error(new IOException("Error logging in"));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}