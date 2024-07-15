package com.sandbox.PhotoAppApiUsers.users.shared;

import com.sandbox.PhotoAppApiUsers.users.ui.model.AlbumResponseModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDto implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userId;
    private String encryptedPassword;
    private List<AlbumResponseModel> albums;
}
