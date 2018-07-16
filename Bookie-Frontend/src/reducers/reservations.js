const initialState = {
  reservationsByUser: null,
  reservation: null,
  successful: null,
  review: null
};

const reservationsReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'GET_RESERVATIONS_INIT':
      return state;
    case 'BOOK_RESERVATION_SUCCESS':
      return {
        ...state,
        reservation: action.payload,
      };
    case 'FETCH_RESERVATIONS_BY_USER_SUCCESS':
      return {
        ...state,
        reservationsByUser: action.payload.reservationsByUserResult,
        successful: action.payload.successful,
      };
    case 'FETCH_RESERVATIONS_BY_USER_ERROR':
      return {...state, error: action.error};

    case 'RESERVATION_DETAILS_SUCCESS':
      return {
        ...state, reservation: action.payload.reservation,
        review: action.payload.review || {}
      };
    default:
      return state;
  }
};

export default reservationsReducer;