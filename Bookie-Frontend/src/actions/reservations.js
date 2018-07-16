import http from '../config/http';
import {push} from "react-router-redux";
import * as notifications from "./notifications";

export const bookReservation = (params) => async (dispatch) => {
  http.defaults.headers.common['Authorization'] = localStorage.getItem('USER_LOGIN_TOKEN');
  http.post('/reservation/book', params)
    .then((response) => {
      dispatch(bookReservationSuccess(response.data));
      dispatch(notifications.onNotificationSuccessInit("success", "BOOK_SUCCESS"));
      dispatch(push(`/details`));
    })
    .catch((error) => {
      if (error.response !== 'undefined') {
        dispatch(notifications.onNotificationErrorInit(error.response.data, "BOOK_ERROR"))
      } else {
        dispatch(notifications.onNotificationErrorInit(error, "BOOK_ERROR"))
      }
    });
};

export const fetchReservationsByUser = (userId) => async (dispatch) => {

  try {
    const response = await getReservationsForUser({userId});
    dispatch(fetchReservationsByUserSuccess(response.data));
    dispatch(push(`/profile/${userId}`));
  }
  catch (error) {
    dispatch(fetchReservationsByUserFailure());
  }
};

export const displayReservationDetails = (input) => async (dispatch) => {

  try {
    let params = {hotelCode: input.reservation.hotelCode, userId: input.userId};
    const response = await performGetReview(params);
    dispatch(reservationDetailsSuccess({
      review: response.data,
      reservation: input.reservation
    }));

    dispatch(push(`/details`));
  }
  catch (error) {
  }
};

export const cancelReservation = (params) => async (dispatch) => {

  http.defaults.headers.common['Authorization'] = localStorage.getItem('USER_LOGIN_TOKEN');
  http.post('/reservation/cancel', params)
    .then((response) => {
      dispatch(cancelReservationSuccess(response.data));
      dispatch(notifications.onNotificationSuccessInit("success", "CANCEL_RESERVATION_SUCCESS"));
      dispatch(push(`/details`));
    })
    .catch((error) => {
      if (error.response !== 'undefined') {
        dispatch(notifications.onNotificationErrorInit(error.response.data, "CANCEL_RESERVATION_ERROR"))
      } else {
        dispatch(notifications.onNotificationErrorInit(error, "CANCEL_RESERVATION_ERROR"))
      }
    });
};

export const getReservationsForUser = async (params) => {
  http.defaults.headers.common['Authorization'] = localStorage.getItem('USER_LOGIN_TOKEN');
  return await http.get(`/reservation/user`, {params});
};

export const performGetReview = async (params) => {
  http.defaults.headers.common['Authorization'] = localStorage.getItem('USER_LOGIN_TOKEN');
  return await http.get(`/review`, {params});
};

//book reservation
export const bookReservationSuccess = (data) => ({type: 'BOOK_RESERVATION_SUCCESS', payload: data});

//fetch reservations for user
export const fetchReservationsByUserSuccess = (data) => ({type: 'FETCH_RESERVATIONS_BY_USER_SUCCESS', payload: data});
export const fetchReservationsByUserFailure = () => ({type: 'FETCH_RESERVATIONS_BY_USER_ERROR'});

export const cancelReservationSuccess = (data) => ({type: 'BOOK_RESERVATION_SUCCESS', payload: data});

export const reservationDetailsSuccess = (data) => ({type: 'RESERVATION_DETAILS_SUCCESS', payload: data});
