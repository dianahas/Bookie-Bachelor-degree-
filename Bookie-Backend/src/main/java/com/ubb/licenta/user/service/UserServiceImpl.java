package com.ubb.licenta.user.service;

import com.ubb.licenta.user.resource.*;
import com.ubb.licenta.utils.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserObject userObject;

    @Override
    public UserInfo addUser( UserInfo userInfo ) throws ApplicationException {
        return userObject.addUser( userInfo );
    }

    @Override
    public UserInfo updateUser( UserInfo userInfo ) throws ApplicationException {
        return userObject.updateUser( userInfo );
    }

    @Override
    public UserInfo getUserByUsername( String username ) {
        return userObject.getUserByUsername( username );
    }

    @Override
    public UserInfo getUserById( String id ) {
        return userObject.getUserById( id );
    }

    @Override
    public void deleteUser( String userId ) {
        userObject.deleteUser( userId );
    }

    @Override
    public Map<String, UserInfo> findUsers( UserSearchCriteria userSearchCriteria ) {
        return userObject.findUsers( userSearchCriteria );
    }

    @Override
    public UserLoginResponseInfo login( UserLoginInfo userLoginInfo ) throws ApplicationException {
        return userObject.login( userLoginInfo );
    }

    @Override
    public void changePassword( PasswordChangeInfo passwordChangeInfo ) throws ApplicationException {
        userObject.changePassword( passwordChangeInfo );
    }
}
