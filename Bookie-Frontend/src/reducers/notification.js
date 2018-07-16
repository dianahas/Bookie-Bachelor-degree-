const initialState = {
  userLoginError: null,
  userRegisterError: null,
  userProfileError: null,
  userPasswordError: null,
  userProfileSuccess: null,
  userPasswordSuccess: null,

  bookError: null,
  bookSuccess: null,
  cancelReservationError: null,
  cancelReservationSuccess: null,

  reviewError: null,
  reviewSuccess: null,

  notificationSuccess: null,

  hotelsError: null
};

const notificationReducer = (state = initialState, action) => {
  switch (action.type) {
    case ("PROFILE_SUCCESS"):
      return {...state, userProfileSuccess: action.payload};
    case ("PASSWORD_SUCCESS"):
      return {...state, userPasswordSuccess: action.payload};
    case ("LOGIN_ERROR"):
      return {...state, userLoginError: action.payload.notification};
    case ("REGISTER_ERROR"):
      return {...state, userRegisterError: action.payload.notification};
    case ("PROFILE_ERROR"):
      return {...state, userProfileError: action.payload.notification};
    case ("PASSWORD_ERROR"):
      return {...state, userPasswordError: action.payload.notification};

    case ("BOOK_ERROR"):
      return {...state, bookError: action.payload.notification};
    case ("BOOK_SUCCESS"):
      return {...state, bookSuccess: action.payload};
    case ("CANCEL_RESERVATION_ERROR"):
      return {...state, cancelReservationError: action.payload.notification};
    case ("CANCEL_RESERVATION_SUCCESS"):
      return {...state, cancelReservationSuccess: action.payload};
    case ("REVIEW_ERROR"):
      return {...state, reviewError: action.payload.notification};
    case ("REVIEW_SUCCESS"):
      return {...state, reviewSuccess: action.payload};

    case ("HOTELS_ERROR"):
      return {...state, hotelsError: action.payload.notification};

    case ("NOTIFICATION_CLOSE"):
      return {
        ...state,
        userLoginError: null,
        userRegisterError: null,
        userProfileSuccess: null,
        userPasswordSuccess: null,
        userProfileError: null,
        userPasswordError: null,
        bookError: null,
        bookSuccess: null,
        cancelReservationError: null,
        cancelReservationSuccess: null,
        reviewError: null,
        reviewSuccess: null,
      };
    default:
      return state;
  }
};

export default notificationReducer;
