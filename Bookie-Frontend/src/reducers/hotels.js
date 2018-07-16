const initialState = {
  hotelSearchInfoList: [],
  selectedHotel: null,
  rooms: [],
  amenityList: [],
  reviewList: {},
  location: null,
  latitude: null,
  longitude: null,
  persons: null,
  arrival: null,
  departure: null,
  searchByHotel: false,
  successful: null,
  roomRatesSuccessful: null,
  totalNrOfPages: null,
  currentPage: null,
  isSort: null,
  error: null,
  ascending: null,
  byPrice: null,
  byRating: null,
  byRoomDimension: null,
};

const hotelsReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'GET_HOTELS_INIT':
      return state;
    case 'GET_HOTELS_SUCCESS':
      return {
        ...state,
        hotelSearchInfoList: action.payload.hotels,
        successful: action.payload.successful,
        location: action.payload.address,
        latitude: action.payload.latitude,
        longitude: action.payload.longitude,
        persons: action.payload.persons,
        arrival: action.payload.arrival,
        departure: action.payload.departure,
        searchByHotel: action.payload.searchByHotel,
        totalNrOfPages: action.payload.totalNrOfPages,
        currentPage: action.payload.currentPage
      };
    case 'GET_HOTELS_ERROR':
      return {...state, error: action.payload};

    case 'PAGINATE_HOTELS_SUCCESS':
      return {
        ...state,
        hotelSearchInfoList: action.payload.hotels,
        successful: action.payload.successful,
        currentPage: action.payload.currentPage
      };
    case 'PAGINATE_HOTELS_ERROR':
      return {...state, error: action.payload};

    case 'SORT_BY_PRICE_SUCCESS':
      return {
        ...state,
        hotelSearchInfoList: action.payload.hotels,
        successful: action.payload.successful,
        currentPage: action.payload.currentPage,
        isSort: action.payload.isSort,
        totalNrOfPages: action.payload.totalNrOfPages,
        ascending: action.payload.ascending,
        byPrice: action.payload.byPrice,
        byRating: action.payload.byRating,
        byRoomDimension: action.payload.byRoomDimension,
      };
    case 'SORT_BY_PRICE_ERROR':
      return {...state, error: action.payload};

    case 'GET_HOTEL_DETAILS_SUCCESS':
      return {
        ...state,
        selectedHotel: action.payload.hotelUIInfoResponse,
        rooms: action.payload.roomDetailsInfos,
        amenityList: action.payload.amenities,
        reviewList: action.payload.reviews,
        roomRatesSuccessful: action.payload.successful
      };
    case 'GET_HOTEL_DETAILS_ERROR':
      return {...state, error: action.payload};
    default:
      return state;
  }
};

export default hotelsReducer;