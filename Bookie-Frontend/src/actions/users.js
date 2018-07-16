import http from '../config/http';
import {push} from "react-router-redux";
import * as notifications from './notifications';

export const initLogin = (value) => {
  return async (dispatch) => {
    http.post('/user/login', value)
      .then((response) => {
        let loginToken = response.data.loginToken;
        let userInfo = response.data.userInfo;

        localStorage.setItem('USER_IS_LOGGED_IN', true);
        localStorage.setItem('USER_LOGIN_TOKEN', loginToken);
        localStorage.setItem('USER_INFO_KEY', JSON.stringify(userInfo));
        localStorage.setItem('USER_ID_KEY', userInfo.id);
        dispatch(onLoginSuccess(userInfo));

        // let path = this.props.match.url;
        // dispatch(push(path));
        const urlPath = sessionStorage.getItem('CURRENT_URL_PATH');
        sessionStorage.removeItem('CURRENT_URL_PATH');
        dispatch(push(urlPath));
      })
      .catch((error) => {
        if (error.response !== 'undefined') {
          dispatch(notifications.onNotificationErrorInit(error.response.data, "LOGIN_ERROR"));
        } else {
          dispatch(notifications.onNotificationErrorInit(error, "LOGIN_ERROR"));
        }
      });

  };
};

export const register = (value) => {
  return (dispatch) => {
    http.post('/user/register', value)
      .then((response) => {
        const registerUserInfo = response.data;

        dispatch(onRegisterSuccess(registerUserInfo));
        dispatch(push('/login'));
      })
      .catch((error) => {
        if (error.response !== 'undefined') {
          dispatch(notifications.onNotificationErrorInit(error.response.data, "REGISTER_ERROR"))
        } else {
          dispatch(notifications.onNotificationErrorInit(error, "REGISTER_ERROR"))
        }
      });

  };
};

export const updateProfile = (value) => {
  return (dispatch) => {
    http.defaults.headers.common['Authorization'] = localStorage.getItem('USER_LOGIN_TOKEN');
    http.post('/user/update', value)
      .then((response) => {
        const updatedUserInfo = response.data;

        dispatch(onUpdateSuccess(updatedUserInfo));
        dispatch(notifications.onNotificationSuccessInit("success", "PROFILE_SUCCESS"));
        dispatch(push(`/profile/${updatedUserInfo.id}`));
      })
      .catch((error) => {
        if (error.response !== 'undefined') {
          dispatch(notifications.onNotificationErrorInit(error.response.data, "PROFILE_ERROR"))
        } else {
          dispatch(notifications.onNotificationErrorInit(error, "PROFILE_ERROR"))
        }
      });

  };
};

export const changePassword = (value) => {
  return (dispatch) => {
    http.defaults.headers.common['Authorization'] = localStorage.getItem('USER_LOGIN_TOKEN');
    http.post('/user/password', value)
      .then((response) => {
        dispatch(notifications.onNotificationSuccessInit("success", "PASSWORD_SUCCESS"));
        dispatch(onPasswordUpdateSuccess());
      })
      .catch((error) => {
        if (error.response !== 'undefined') {
          dispatch(notifications.onNotificationErrorInit(error.response.data, "PASSWORD_ERROR"))
        } else {
          dispatch(notifications.onNotificationErrorInit(error, "PASSWORD_ERROR"))
        }
      });

  };
};

export const isLoggedIn = () => (dispatch) => {
  const userInfo = localStorage.getItem('USER_INFO_KEY');
  if (userInfo) {
    dispatch(onLoginSuccess(JSON.parse(userInfo)));
  }
};

export function onSavePath(values) {
  sessionStorage.setItem('CURRENT_URL_PATH', values);
}

export const onLoginSuccess = (payload) => {
  return {type: 'ON_LOGIN_SUCCESS', payload};
};

export const onLogout = () => {
  localStorage.removeItem('USER_IS_LOGGED_IN');
  localStorage.removeItem('USER_INFO_KEY');
  localStorage.removeItem('USER_LOGIN_TOKEN');

  return (dispatch) => {
    dispatch(onLogoutSuccess());
    dispatch(push(`/`));
  };
};

export const onLogoutSuccess = () => {
  return {type: 'ON_LOGIN_SUCCESS'};
};

export const onRegisterSuccess = (payload) => {
  return {type: 'ON_REGISTER_SUCCESS', payload};
};

export const onUpdateSuccess = (payload) => {
  return {type: 'ON_UPDATE_SUCCESS', payload};
};

export const onPasswordUpdateSuccess = () => {
  return {type: 'ON_PASSWORD_UPDATE_SUCCESS'};
};