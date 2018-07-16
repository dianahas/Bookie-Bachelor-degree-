import http from "../config/http";
import * as notifications from "./notifications";

export const addReview = (params) => async (dispatch) => {
  http.defaults.headers.common['Authorization'] = localStorage.getItem('USER_LOGIN_TOKEN');
  http.post('/review/add', params)
    .then((response) => {
      dispatch(addReviewSuccess(response.data));
      dispatch(notifications.onNotificationSuccessInit("success", "REVIEW_SUCCESS"));
    })
    .catch((error) => {
      if (error.response !== 'undefined') {
        dispatch(notifications.onNotificationErrorInit(error.response.data, "REVIEW_ERROR"))
      } else {
        dispatch(notifications.onNotificationErrorInit(error, "REVIEW_ERROR"))
      }
    });
};

export const getAllReviewsByUser = (params) => async (dispatch) => {

  try {
    const response = await performGetReviewsByUser(params);
    dispatch(getReviewByUserSuccess(response.data));
  }
  catch (error) {
  }
};

export const performGetReviewsByUser = async (params) => {
  http.defaults.headers.common['Authorization'] = localStorage.getItem('USER_LOGIN_TOKEN');
  return await http.get(`/review/all`, {params});
};

export const addReviewSuccess = (data) => ({type: 'ADD_REVIEW_SUCCESS', payload: data});

//get review by hotel and user
export const getReviewByUserSuccess = (data) => ({type: 'GET_REVIEWS_BY_USER_SUCCESS', payload: data});