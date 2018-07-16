import http from '../config/http';
import {push} from 'react-router-redux';
import * as notifications from "./notifications";

export const getHotels = (params) => async (dispatch) => {
  try {
    const response = await findHotels(params);

    let hotels = response.data.results;
    let successful = response.data.successful;
    let address = params.address;
    let latitude = params.latitude;
    let longitude = params.longitude;
    let arrival = params.checkInDate;
    let departure = params.checkOutDate;
    let persons = params.persons;
    let searchByHotel = params.isSearchByHotel;
    let totalNrOfPages = response.data.totalNrOfPages;
    let currentPage = response.data.currentPage;

    dispatch(getHotelsSuccess({
      hotels,
      successful,
      address,
      latitude,
      longitude,
      arrival,
      departure,
      persons,
      searchByHotel,
      totalNrOfPages,
      currentPage
    }));

    dispatch(push('/hotels'));
  }

  catch (error) {
    // let err = error.response;
    if (error.response !== undefined) {
      dispatch(notifications.onNotificationErrorInit(error.response.data, "HOTELS_ERROR"))
    } else {
      dispatch(notifications.onNotificationErrorInit(error, "HOTELS_ERROR"))
    }
  }
};

export const paginateHotels = (params) => async (dispatch) => {
  try {
    const response = await paginate(params);

    let hotels = response.data.results;
    let successful = response.data.successful;
    let currentPage = response.data.currentPage;

    dispatch(paginateHotelsSuccess({
      hotels,
      successful,
      currentPage
    }));

    dispatch(push('/hotels'));
  }

  catch (error) {
    dispatch(paginateHotelsFailure());
  }

};

export const sortHotels = (params) => async (dispatch) => {
  try {
    const response = await processSortHotels(params);

    let hotels = response.data.results;
    let successful = response.data.successful;
    let currentPage = response.data.currentPage;
    let isSort = response.data.isSort;
    let totalNrOfPages = response.data.totalNrOfPages;
    let ascending = params.ascending;
    let byPrice = params.byPrice;
    let byRating = params.byRating;
    let byRoomDimension = params.byRoomDimension;

    dispatch(sortHotelsSuccess({
      hotels,
      successful,
      currentPage,
      isSort,
      totalNrOfPages,
      ascending,
      byPrice,
      byRating,
      byRoomDimension,
    }));

    dispatch(push('/hotels'));
  }
  catch (error) {
    dispatch(sortHotelsFailure());
  }
};

export const getHotelDetails = (params) => async (dispatch) => {

  try {
    const response = await getDetails(params);
    dispatch(getHotelDetailsSuccess(response.data));

    dispatch(push(`/hotel/${params.hotelPropertyId}`));
  }
  catch (error) {
    dispatch(getHotelDetailsError());
  }
};

export const findHotels = async (params) => {
  return await http.get(`/hotelsearch`, {params});
};

export const paginate = async (params) => {
  return await http.get(`/hotelsearch/paginate`, {params});
};

export const processSortHotels = async (params) => {
  return await http.get(`/hotelsearch/sort`, {params});
};

export const getDetails = async (params) => {
  return await http.get(`/roomrates`, {params});
};

//hotel-search
export const getHotelsSuccess = (data) => ({type: 'GET_HOTELS_SUCCESS', payload: data});
export const getHotelsFailure = (error) => ({type: 'GET_HOTELS_ERROR', payload: error});

//paginate hotels
export const paginateHotelsSuccess = (data) => ({type: 'PAGINATE_HOTELS_SUCCESS', payload: data});
export const paginateHotelsFailure = () => ({type: 'PAGINATE_HOTELS_ERROR'});

//room-rates
export const getHotelDetailsSuccess = (data) => ({type: 'GET_HOTEL_DETAILS_SUCCESS', payload: data});
export const getHotelDetailsError = () => ({type: 'GET_HOTEL_DETAILS_ERROR'});

//sort hotels by price
export const sortHotelsSuccess = (data) => ({type: 'SORT_BY_PRICE_SUCCESS', payload: data});
export const sortHotelsFailure = () => ({type: 'SORT_BY_PRICE_ERROR'});