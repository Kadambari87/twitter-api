package model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseModel {
    private String userId;
    private String userFirstName;
    private String userLastName;
    private String email;
}
