package com.ubb.licenta.user.resource;

import com.ubb.licenta.user.service.UserService;
import com.ubb.licenta.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserResource {
    private static final Logger log = LoggerFactory.getLogger( UserResource.class );

    private static final String PATH_USER_ID = "/{id}";
    private static final String PATH_USERNAME = "/{username}";

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewUser( @RequestBody UserInfo userInfo ) {

        ResponseEntity<?> response;

        try {
            log.info( "*** Add new user [{}]", userInfo );

            ValidationUtils.validateRequiredObject( userInfo.getUsername(), "username" );
            ValidationUtils.validateRequiredObject( userInfo.getEmail(), "email" );
            ValidationUtils.validateRequiredObject( userInfo.getPassword(), "password" );

            UserInfo responseDto = userService.addUser( userInfo );
            response = new ResponseEntity<>( responseDto, HttpStatus.CREATED );

        } catch ( Exception t ) {
            log.error( "Error when trying to store user [{}]. Error: [{}]", userInfo, t );
            response = new ResponseEntity<>( t.getMessage(), HttpStatus.BAD_REQUEST );
        }

        return response;
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser( @RequestBody UserInfo userInfo ) {

        ResponseEntity<?> response;

        try {
            log.info( "*** Updating a user [{}]", userInfo );

            UserInfo responseDto = userService.updateUser( userInfo );
            response = new ResponseEntity<>( responseDto, HttpStatus.CREATED );

        } catch ( Exception t ) {
            log.error( "Error when trying to update user [{}]. Error: [{}]", userInfo, t );
            response = new ResponseEntity<>( t.getMessage(), HttpStatus.BAD_REQUEST );
        }

        return response;
    }

    @PostMapping(value = "/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser( @RequestBody PasswordChangeInfo passwordChangeInfo ) {

        ResponseEntity<?> response;

        try {

            ValidationUtils.validateRequiredObject( passwordChangeInfo.getNewPassword(), "newPassword" );
            ValidationUtils.validateRequiredObject( passwordChangeInfo.getOldPassword(), "oldPassword" );

            log.info( "*** Changing the password for the user [{}]", passwordChangeInfo.getUserId() );

            userService.changePassword( passwordChangeInfo );
            response = new ResponseEntity<>( null, HttpStatus.CREATED );

        } catch ( Exception t ) {
            log.error( "Error when trying to change the password for the user [{}]. Error: [{}]", passwordChangeInfo.getUserId(), t );
            response = new ResponseEntity<>( t.getMessage(), HttpStatus.BAD_REQUEST );
        }

        return response;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login( @RequestBody UserLoginInfo userLoginInfo ) {

        ResponseEntity<?> response;

        try {
            log.info( "*** login the user [{}]" );

            response = new ResponseEntity<>( userService.login( userLoginInfo ), HttpStatus.OK );

            log.info( "*** Successfully logged in the user [{}]" );

        } catch ( Exception t ) {
            log.error( "Error when trying to log in. Error: [{}]", t );
            response = new ResponseEntity<>( t.getMessage(), HttpStatus.BAD_REQUEST );
        }

        return response;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserByUsername( @RequestParam(value = "username") String username ) {

        ResponseEntity<?> response;

        try {
            log.info( "*** get user [{}]", username );

            UserInfo responseDto = userService.getUserByUsername( username );
            response = new ResponseEntity<>( responseDto, HttpStatus.OK );

            log.info( "*** Successfully added the user [{}]", username );

        } catch ( Exception t ) {
            log.error( "Error when trying to user [{}]. Error: [{}]", username, t );
            response = new ResponseEntity<>( t.getMessage(), HttpStatus.BAD_REQUEST );
        }

        return response;
    }

    @GetMapping(value = "/id" + PATH_USER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserById( @PathVariable @Valid String id ) {

        ResponseEntity<?> response;

        try {
            log.info( "*** get user [{}]", id );

            UserInfo responseDto = userService.getUserById( id );
            response = new ResponseEntity<>( responseDto, HttpStatus.OK );

            log.info( "*** Successfully retrieved the user [{}]", id );

        } catch ( Exception t ) {
            log.error( "Error when trying to get the user [{}]. Error: [{}]", id, t );
            response = new ResponseEntity<>( t.getMessage(), HttpStatus.BAD_REQUEST );
        }

        return response;
    }

    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findUsers( @ModelAttribute @Valid() UserSearchCriteria userSearchCriteria ) {
        ResponseEntity<?> response;

        try {
            log.info( "*** find users" );

            Map<String, UserInfo> responseDto = userService.findUsers( userSearchCriteria );
            response = new ResponseEntity<>( responseDto, HttpStatus.OK );

            log.info( "*** Successfully retrieved the users" );

        } catch ( Exception t ) {
            log.error( "Error when trying to get the users. Error: [{}]", t );
            response = new ResponseEntity<>( t.getMessage(), HttpStatus.BAD_REQUEST );
        }

        return response;
    }

    @DeleteMapping(value = PATH_USER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser( @PathVariable @Valid String id ) {
        ResponseEntity<?> response;

        try {
            log.info( "*** delete the user [{}]", id );

            userService.deleteUser( id );
            response = new ResponseEntity<>( HttpStatus.OK );

            log.info( "*** Successfully deleted the user [{}]", id );

        } catch ( Exception t ) {
            log.error( "Error when trying to delete the user [{}]. Error: [{}]", id, t );
            response = new ResponseEntity<>( t.getMessage(), HttpStatus.BAD_REQUEST );
        }

        return response;
    }
}
