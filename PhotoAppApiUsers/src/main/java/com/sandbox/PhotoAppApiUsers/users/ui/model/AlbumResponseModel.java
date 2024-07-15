package com.sandbox.PhotoAppApiUsers.users.ui.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlbumResponseModel {

    private String albumId;
    private String userId;
    private String name;
    private String description;
}
