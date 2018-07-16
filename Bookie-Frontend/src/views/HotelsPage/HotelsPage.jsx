import React from "react";
// core components
import Header from "components/Header/Header.jsx";
import Footer from "components/Footer/Footer.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import HeaderLinks from "components/Header/HeaderLinks.jsx";
import Parallax from "components/Parallax/Parallax.jsx";
import landingPageStyle from "assets/jss/material-kit-react/views/landingPage.jsx";
// card components
import {connect} from "react-redux";
import {hotelsActions} from "../../actions";

import {goBack, push} from "react-router-redux";
import GridContainer from "../../components/Grid/GridContainer";

import withStyles from "material-ui/styles/withStyles";

import HotelCard from "./HotelCard"
import Button from "../../components/CustomButtons/Button";

import SortBar from "../../components/SortBar/index"

import classNames from "classnames";
import SearchMask from "../Search/Sections/SearchMask";
import {geocodeByAddress, getLatLng} from "react-places-autocomplete";

class HotelsPage extends React.Component {
  componentDidMount() {
    const {successful} = this.props;

    if (successful === null) {
      this.props.goToMain();
    }
  }

  onDetails = (hotelPropertyId) => {
    let values = {
      hotelPropertyId,
      inDate: this.props.inDate,
      outDate: this.props.outDate
    };

    this.props.hotelDetails(values);
  };

  onSortHotelsClick = (values) => {
    let params = {
      checkInDate: this.props.inDate,
      checkOutDate: this.props.outDate,
      latitude: this.props.latitude,
      longitude: this.props.longitude,
      persons: this.props.persons,
      address: this.props.location,
      initial: true,
      startIndex: 0,
      ascending: values.ascending,
      byPrice: values.byPrice,
      byRating: values.byRating,
      byRoomDimension: values.byRoomDimension,
      notSort: values.default === true,
    };

    this.props.onSortHotels(params);
  };

  onNextClick = () => {
    if (this.props.currentPage < this.props.totalNrOfPages) {
      let startIndex = this.props.currentPage * 10;
      let params;

      if (this.props.isSort) {
        params = {
          checkInDate: this.props.inDate,
          checkOutDate: this.props.outDate,
          latitude: this.props.latitude,
          longitude: this.props.longitude,
          persons: this.props.persons,
          address: this.props.location,
          isSearchByHotel: false,
          initial: false,
          startIndex: startIndex,
          notSort: false,
          ascending: this.props.ascending,
          byPrice: this.props.byPrice,
          byRating: this.props.byRating,
          byRoomDimension: this.props.byRoomDimension
        };
      } else {
        params = {
          checkInDate: this.props.inDate,
          checkOutDate: this.props.outDate,
          latitude: this.props.latitude,
          longitude: this.props.longitude,
          persons: this.props.persons,
          address: this.props.location,
          isSearchByHotel: false,
          initial: false,
          startIndex: startIndex,
          notSort: true
        };
      }

      this.props.processPaginateHotels(params);
    }
  };

  onPreviousClick = () => {
    if (this.props.currentPage > 1) {
      let startIndex = (this.props.currentPage - 2) * 10;
      let params;

      if (this.props.isSort) {
        params = {
          checkInDate: this.props.inDate,
          checkOutDate: this.props.outDate,
          latitude: this.props.latitude,
          longitude: this.props.longitude,
          persons: this.props.persons,
          address: this.props.location,
          initial: false,
          startIndex: startIndex,
          notSort: false,
          ascending: this.props.ascending,
          byPrice: this.props.byPrice,
          byRating: this.props.byRating,
          byRoomDimension: this.props.byRoomDimension
        };

      } else {
        params = {
          checkInDate: this.props.inDate,
          checkOutDate: this.props.outDate,
          latitude: this.props.latitude,
          longitude: this.props.longitude,
          persons: this.props.persons,
          address: this.props.location,
          initial: false,
          startIndex: startIndex,
          notSort: true
        };
      }

      this.props.processPaginateHotels(params);
    }
  };

  onBack = () => {
    this.props.onGoBack();
  };

  render() {
    const {classes, hotelSearchInfoList, location, searchByHotel, currentPage, totalNrOfPages, inDate, outDate, persons, ...rest} = this.props;
    let destination = searchByHotel ? location : "Hotels in " + location;

    const onSearchSubmit = (values) => {
      let isSearchByHotel = false;
      let address = values.address;
      if (address !== null && values.inDate !== null && values.outDate !== null) {

        geocodeByAddress(address)
          .then(results => {
            //de obicei contine si establishment/point of interest
            //deocamdata, am observat ca doar cand contine "lodging" este hotel
            if (results[0].types.includes("lodging")) {
              isSearchByHotel = true;
            }

            return getLatLng(results[0])
          })
          .then(latLng => {
            let latitude = latLng.lat;
            let longitude = latLng.lng;

            let initialSearch = true;
            let startIndex = 0;

            let hotelSearchInput = {
              checkInDate: values.inDate,
              checkOutDate: values.outDate,
              latitude,
              longitude,
              persons: values.persons,
              address,
              isSearchByHotel,
              initialSearch,
              startIndex
            };

            this.props.getHotelsSearch(hotelSearchInput);
          })
          .catch(error => console.error('Error', error))
      }
    };

    return (
      <div>
        <Header
          color="transparent"
          brand=""
          rightLinks={<HeaderLinks/>}
          fixed
          changeColorOnScroll={{
            height: 200,
            color: "white"
          }}
          {...rest}
        />

        <Parallax small filter image={require("assets/img/profile-bg.jpg")}>
          <div className={classes.container}>
            <GridContainer>
              <GridItem xs={12} sm={12} md={12}>
                <h2 className={classes.title}>{destination}</h2>
                <br/>
              </GridItem>
            </GridContainer>
          </div>
        </Parallax>


        <div className={classNames(classes.main)}>
          <div>

            <GridContainer justify="center">

              <GridItem xs={11} sm={11} md={10} lg={8} className={classes.navWrapper}>
                <SortBar className={classes.searchBar} onSort={this.onSortHotelsClick}/>
              </GridItem>

              <GridItem xs={11} sm={10} md={9} lg={7}>
                <SearchMask location={location} inDate={inDate} outDate={outDate} persons={persons}
                            handleSubmit={onSearchSubmit}/>
              </GridItem>

              <GridContainer justify="center" xs={11} sm={11} md={10} lg={8} style={{padding: "20px"}}>
                {hotelSearchInfoList.length > 0
                  ? hotelSearchInfoList.map((hotel, index) => (
                    <HotelCard onDetails={this.onDetails}
                               id={index} hotel={hotel} {...this.props}/>
                  ))
                  : <h3 style={{float: 'center'}}> No hotels were found :( </h3>}
              </GridContainer>

              <GridItem justify="center" xs={12} sm={12} md={10} lg={8} style={{padding: "40px"}}>
                {currentPage > 1 ?
                  <div>
                    <Button onClick={this.onPreviousClick} style={{float: 'left'}}> Previous </Button>
                  </div> : null}

                {currentPage < totalNrOfPages ?
                  <div>
                    <Button onClick={this.onNextClick} style={{float: 'left'}}> Next </Button>
                  </div> : null
                }
                <Button style={{float: 'right'}} onClick={(event) => this.onBack}> Back </Button>
              </GridItem>
            </GridContainer>
          </div>
        </div>

        <Footer/>
      </div>
    );
  }
}

const mapStateToProps = (state) => ({
  hotelSearchInfoList: state.hotels.hotelSearchInfoList,
  successful: state.hotels.successful,
  location: state.hotels.location,
  latitude: state.hotels.latitude,
  longitude: state.hotels.longitude,
  inDate: state.hotels.arrival,
  outDate: state.hotels.departure,
  persons: state.hotels.persons,
  searchByHotel: state.hotels.searchByHotel,
  currentPage: state.hotels.currentPage,
  totalNrOfPages: state.hotels.totalNrOfPages,
  isSort: state.hotels.isSort,
  ascending: state.hotels.ascending,
  byPrice: state.hotels.byPrice,
  byRating: state.hotels.byRating,
  byRoomDimension: state.hotels.byRoomDimension,
});

const mapDispatchToProps = (dispatch) => ({
  hotelDetails: (params) => dispatch(hotelsActions.getHotelDetails(params)),
  onSortHotels: (params) => dispatch(hotelsActions.sortHotels(params)),
  processPaginateHotels: (params) => dispatch(hotelsActions.paginateHotels(params)),
  getHotelsSearch: (params) => dispatch(hotelsActions.getHotels(params)),
  goToMain: () => dispatch(push(`/`)),
  onGoBack: () => dispatch(goBack()),
});

const withConnect = connect(mapStateToProps, mapDispatchToProps)(HotelsPage);

export default withStyles(landingPageStyle)(withConnect);