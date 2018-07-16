const initialState = {
  userInfo: null,
  registeredUserInfo: null,
  isLoggedIn: null,
  error: null,
};

const usersReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'ON_LOGIN_INIT':
      return state;
    case 'ON_LOGIN_SUCCESS':
      return {...state, isLoggedIn: true, userInfo: action.payload, error: null};
    case 'ON_LOGIN_ERROR':
      return {...state, error: action.error};
    case 'ON_LOGOUT':
      return {...state, isLoggedIn: null, userInfo: null, error: null};
    case 'ON_REGISTER_SUCCESS':
      return {...state, isLoggedIn: null, registeredUserInfo: action.payload, error: null};
    case 'ON_REGISTER_ERROR':
      return {...state, error: action.error};
    case 'ON_UPDATE_SUCCESS':
      return {...state, userInfo: action.payload, error: null};
    case 'ON_UPDATE_ERROR':
      return {...state, error: action.error};
    default:
      return state;
  }
};

export default usersReducer;