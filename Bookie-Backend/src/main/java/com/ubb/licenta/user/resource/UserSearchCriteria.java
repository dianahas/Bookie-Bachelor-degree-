package com.ubb.licenta.user.resource;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserSearchCriteria {
    private List<String> userIds;
    private Boolean isAdmin;
    private Boolean isActive;
}
