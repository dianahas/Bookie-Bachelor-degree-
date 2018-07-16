package com.ubb.licenta.user.service;

import com.ubb.licenta.constants.DocumentTypeEnum;
import com.ubb.licenta.user.entity.UserEntity;
import com.ubb.licenta.user.repository.UserRepository;
import com.ubb.licenta.user.resource.*;
import com.ubb.licenta.utils.ApplicationException;
import com.ubb.licenta.utils.ValidationUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ubb.licenta.constants.ApplicationConstants.REGISTRATION_EMAIL_SUBJECT;
import static com.ubb.licenta.constants.SecurityConstants.*;

@Component
public class UserObject {
    private static final Logger log = LoggerFactory.getLogger( UserObject.class );

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public JavaMailSender emailSender;

    private final static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private final static MapperFacade mapper = mapperFactory.getMapperFacade();

    static {
        mapperFactory.classMap( UserInfo.class, UserEntity.class )
                .byDefault()
                .register();

        mapperFactory.classMap( UserEntity.class, UserInfo.class )
                .byDefault()
                .register();
    }

    public UserInfo addUser( UserInfo userInfo ) throws ApplicationException {
        if ( userRepository.findByUsername( userInfo.getUsername() ) != null ) {
            throw new ApplicationException( "An user with that username already exists" );
        }

        if ( !EmailValidator.getInstance().isValid( userInfo.getEmail() ) ) {
            throw new ApplicationException( "The email is not correct" );
        }

        ValidationUtils.validateRequiredObject( userInfo.getPassword(), "userInfo.password" );

        UserEntity userEntity = mapper.map( userInfo, UserEntity.class );

        userEntity.setPassword( bCryptPasswordEncoder.encode( userEntity.getPassword() ) );
        userEntity.setCreateTimestamp( LocalDateTime.now() );
        userEntity.setType( DocumentTypeEnum.USER_TYPE.getType() );

        sendSimpleMessage( userInfo.getEmail(), REGISTRATION_EMAIL_SUBJECT, "hahaha" );

        return mapper.map( userRepository.save( userEntity ), UserInfo.class );
    }

    public UserInfo updateUser( UserInfo userInfo ) throws ApplicationException {

        if ( userInfo.getEmail() != null && !EmailValidator.getInstance().isValid( userInfo.getEmail() ) ) {
            throw new ApplicationException( "The email is not correct" );
        }

        UserEntity userEntity = userRepository.findOne( userInfo.getId() );

        if ( userEntity != null ) {
            if ( userInfo.getFirstName() != null ) {
                userEntity.setFirstName( userInfo.getFirstName() );
            }

            if ( userInfo.getLastName() != null ) {
                userEntity.setLastName( userInfo.getLastName() );
            }

            if ( userInfo.getEmail() != null ) {
                userEntity.setEmail( userInfo.getEmail() );
            }

            if ( userInfo.getPhoneNumber() != null ) {
                userEntity.setPhoneNumber( userInfo.getPhoneNumber() );
            }
        }

        return mapper.map( userRepository.save( userEntity ), UserInfo.class );
    }

    public void changePassword( PasswordChangeInfo passwordChangeInfo ) throws ApplicationException {
        UserEntity userEntity = userRepository.findOne( passwordChangeInfo.getUserId() );

        if ( userEntity != null ) {
            if ( bCryptPasswordEncoder.matches( passwordChangeInfo.getOldPassword(), userEntity.getPassword() ) ) {
                userEntity.setPassword( bCryptPasswordEncoder.encode( passwordChangeInfo.getNewPassword() ) );

                userRepository.save( userEntity );

            } else throw new ApplicationException( "Could not change password, they do not match" );
        }
    }


    public UserInfo getUserByUsername( String username ) {
        UserEntity userEntity = userRepository.findByUsername( username );
        ValidationUtils.validateRequiredObject( userEntity, "userEntity", "No user was found with that username" );

        return mapper.map( userEntity, UserInfo.class );
    }

    public UserInfo getUserById( String id ) {
        UserEntity userEntity = userRepository.findOne( id );
        ValidationUtils.validateRequiredObject( userEntity, "userEntity", "No user was found with that id" );

        return mapper.map( userEntity, UserInfo.class );
    }

    public Map<String, UserInfo> findUsers( UserSearchCriteria userSearchCriteria ) {
        List<UserEntity> userEntities;

        if ( userSearchCriteria.getUserIds() != null ) {
            userEntities = userRepository.findAll( userSearchCriteria.getUserIds() );
        } else if ( userSearchCriteria.getIsActive() != null ) {
            userEntities = userRepository.findAllByIsActiveEquals( userSearchCriteria.getIsActive() );
        } else userEntities = userRepository.findAll();

        return userEntities.stream().collect( Collectors.toMap( UserEntity::getId, userEntity ->
                mapper.map( userEntity, UserInfo.class ) ) );
    }

    public void deleteUser( String userId ) {
        UserEntity userEntity = userRepository.findOne( userId );
        ValidationUtils.validateRequiredObject( userEntity, "userEntity", "No user was found with that id" );

        userEntity.setIsActive( false );
        userRepository.save( userEntity );
    }

    public UserLoginResponseInfo login( UserLoginInfo userLoginInfo ) throws ApplicationException {
        if ( userLoginInfo.getUsername() == null || userLoginInfo.getPassword() == null ) {
            throw new ApplicationException( "Please fill in username and password" );
        }

        String username = userLoginInfo.getUsername();
        String password = userLoginInfo.getPassword();

        UserEntity user = userRepository.findByUsername( userLoginInfo.getUsername() );

        if ( user == null ) {
            throw new ApplicationException( "User email not found." );
        }

        if ( !bCryptPasswordEncoder.matches( password, user.getPassword() ) ) {
            throw new ApplicationException( "Invalid login. Please check your name and password." );
        }

        String jwtToken = TOKEN_PREFIX + Jwts.builder()
                .setSubject( username )
                .setExpiration( new Date( System.currentTimeMillis() + EXPIRATION_TIME ) )
                .signWith( SignatureAlgorithm.HS512, SECRET.getBytes() )
                .compact();

        return new UserLoginResponseInfo( jwtToken, mapper.map( user, UserInfo.class ) );
    }

    private void sendSimpleMessage( String to, String subject, String text ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo( to );
        message.setSubject( subject );
        message.setText( text );
        emailSender.send( message );
    }
}
