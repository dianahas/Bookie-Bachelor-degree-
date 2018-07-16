const initialState = {
  review: null,
  reviewsByUser: null,
  successful: null
};

const reviewsReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'GET_REVIEW_INIT':
      return state;
    case 'ADD_REVIEW_SUCCESS':
      return {
        ...state,
        review: action.payload,
      };
    case 'ADD_REVIEW_ERROR':
      return {...state, error: action.error};

    case 'GET_REVIEWS_BY_USER_SUCCESS':
      return {
        ...state,
        reviewsByUser: action.payload.reviews,
        successful: action.payload.successful,
      };
    case 'GET_REVIEWS_BY_USER_ERROR':
      return {...state, error: action.error};
    default:
      return state;
  }
};

export default reviewsReducer;