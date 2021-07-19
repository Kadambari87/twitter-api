package model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TwitterResponseModel {
    private String userid;
    private String message;
    private Date postedDate;
}
