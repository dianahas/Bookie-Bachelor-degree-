package com.ubb.licenta.user.service;

import com.ubb.licenta.user.resource.*;
import com.ubb.licenta.utils.ApplicationException;

import java.util.Map;

public interface UserService {
    UserInfo addUser( UserInfo userInfo ) throws ApplicationException;

    UserInfo updateUser( UserInfo userInfo ) throws ApplicationException;

    UserInfo getUserByUsername( String username );

    UserInfo getUserById( String id );

    void deleteUser( String userId );

    Map<String, UserInfo> findUsers( UserSearchCriteria userSearchCriteria );

    UserLoginResponseInfo login( UserLoginInfo userLoginInfo ) throws ApplicationException;

    void changePassword( PasswordChangeInfo passwordChangeInfo ) throws ApplicationException;
}
