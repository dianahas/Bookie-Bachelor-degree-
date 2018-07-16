import React, {Component} from "react";
// material-ui components
import withStyles from "material-ui/styles/withStyles";
// react component plugin for creating a beautiful datetime dropdown picker
import Datetime from "react-datetime";
// core components
import GridContainer from "components/Grid/GridContainer.jsx";
import GridItem from "components/Grid/GridItem.jsx";
// cards components
import Card from "components/Card/Card.jsx";
import CardBody from "components/Card/CardBody.jsx";
import CardHeader from "components/Card/CardHeader.jsx";
import CardFooter from "components/Card/CardFooter.jsx";
import Button from "components/CustomButtons/Button.jsx";
// material-ui components
import InputLabel from "material-ui/Input/InputLabel";
import FormControl from "material-ui/Form/FormControl";
import productStyle from "assets/jss/material-kit-react/views/landingPageSections/productStyle.jsx";
import {geocodeByAddress, getLatLng} from 'react-places-autocomplete'
import GoogleAutocomplete from "./GoogleSearch";
import CustomInput from "../../../components/CustomInput/CustomInput";
import PropTypes from "prop-types";
import SnackbarContent from "../../../components/Snackbar/SnackbarContent";
import InfoOutline from "@material-ui/icons/es/InfoOutline";
import Quote from "../../../components/Typography/Quote";
import moment from "moment";

class HotelSearchSection extends Component {

  state = {
    checkIn: '',
    checkOut: '',
    persons: '',
    address: '',
    lat: '',
    long: ''
  };

  handleSelect = (address) => {
    this.setState({address: address})
  };

  render() {
    const {classes, onSearch, error} = this.props;

    const getLocation = () => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition, showError);
      } else {
        console.log("Geolocation is not supported by this browser.");
      }
    };

    const showPosition = (position) => {
      let latitude = position.coords.latitude;
      let longitude = position.coords.longitude;

      let currentDate = new Date();
      let checkInDate = currentDate.toJSON().slice(0, 10).replace(/-/g, '-');
      let checkOutDate = new Date(currentDate);
      checkOutDate.setDate(checkOutDate.getDate() + 1);
      checkOutDate = checkOutDate.toJSON().slice(0, 10).replace(/-/g, '-');

      let persons = 2;

      fetch("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&sensor=true")
        .then(res => res.json())
        .then(
          (result) => {
            console.log(JSON.stringify(result));
            console.log(JSON.stringify(result.results[2]));
            let address;
            if (result.results[2] !== null && result.results[2] !== undefined) {
              address = result.results[2].formatted_address;
            } else {
              address = "Cluj-Napoca, Romania";
            }
            let hotelSearchInput = {
              checkInDate, checkOutDate, latitude, longitude, persons, address,
              isSearchByHotel: false, initialSearch: true, startIndex: 0
            };
            onSearch(hotelSearchInput, false);

          },
          // Note: it's important to handle errors here
          // instead of a catch() block so that we don't swallow
          // exceptions from actual bugs in components.
          (error) => {
          }
        );
    };

    function showError(error) {
      switch (error.code) {
        case error.PERMISSION_DENIED:
          console.log("User denied the request for Geolocation.");
          break;
        case error.POSITION_UNAVAILABLE:
          console.log("Location information is unavailable.");
          break;
        case error.TIMEOUT:
          console.log("The request to get user location timed out.");
          break;
        case error.UNKNOWN_ERROR:
          console.log("An unknown error occurred.");
          break;
        default:
          console.log("error");
      }
    }

    const onSearchSubmit = () => {
      let isSearchByHotel = false;
      let address = this.state.address;
      let checkIn = this.state.checkIn;
      let checkOut = this.state.checkOut;
      if (address !== null && address !== "" && checkIn !== "" && checkOut !== "") {

        let checkInDate = checkIn.format("YYYY-MM-DD");
        let checkOutDate = checkOut.format("YYYY-MM-DD");

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
              checkInDate,
              checkOutDate,
              latitude,
              longitude,
              persons: this.state.persons,
              address,
              isSearchByHotel,
              initialSearch,
              startIndex
            };

            onSearch(hotelSearchInput);
          })
          .catch(error => console.error('Error', error))
      }
    };

    const getValidDatesCheckIn = (currentDate) => {
      if (this.state.checkOut !== "") {
        return currentDate.isBefore(this.state.checkOut);
      }

      else {
        // only allow today and future dates
        let yesterday = moment().subtract(1, 'day');
        return currentDate.isAfter(yesterday);
      }
    };

    const getValidDatesCheckOut = (currentDate) => {
      if (this.state.checkIn !== "") {
        return currentDate.isAfter(this.state.checkIn);
      }

      else {
        // only allow today and future dates
        let yesterday = moment().subtract(1, 'day');
        return currentDate.isAfter(yesterday);
      }
    };

    return (
      <div className={classes.section}>
        <GridContainer justify="center" style={{marginBottom: "40px"}}>
          <GridItem xs={12} sm={12} md={8}>
            <h2 className={classes.title}>Enter your next destination</h2>
            <h5 className={classes.description}>
              Search for hotels around the world or use Current Location to search for hotels near you
            </h5>
          </GridItem>
        </GridContainer>

        <GridContainer style={{marginBottom: "60px"}}>
          <GridItem md={12}>

            <Card className={classes.textCenter}>
              <CardHeader color="info">Search Engine</CardHeader>

              <CardBody>
                <GridContainer>
                  {!!(error) ? (

                    <GridItem xs={12} sm={12} md={12}>
                      <SnackbarContent
                        message={
                          <span>
                            <b>DANGER ALERT : </b> Wrong search inputs: {error}
                          </span>
                        }
                        close
                        color="danger"
                        icon={InfoOutline}
                      />
                    </GridItem>
                  ) : null}


                  <GridItem xs={8} sm={8} md={9} style={{marginBottom: "30px", marginTop: "20px"}}>
                    <InputLabel className={classes.labelRoot + " " + classes.labelRootSuccess}>
                    </InputLabel>

                    <FormControl fullWidth>
                      <GoogleAutocomplete
                        style={{width: '90%'}}
                        onSelect={this.handleSelect}/>
                    </FormControl>
                  </GridItem>

                  <GridItem xs={3} sm={3} md={3} style={{marginBottom: "30px", marginTop: "5px"}}>
                    <Button style={{marginTop: "13px"}}
                            onClick={getLocation}> <i className="material-icons">place</i> &nbsp; Current Location
                    </Button>
                  </GridItem>

                  <GridItem xs={12} sm={6} md={6}>
                    <InputLabel
                      className={classes.label}
                      id="checkIn">
                      From date
                      <span style={{}}> * </span>
                    </InputLabel>
                    <FormControl fullWidth style={{marginBottom: "30px", marginTop: "5px"}}>
                      <Datetime
                        inputProps={{placeholder: ""}}
                        onChange={date => this.setState({checkIn: date})}
                        isValidDate={getValidDatesCheckIn}
                        closeOnSelect={true}
                      />
                    </FormControl>
                  </GridItem>

                  <GridItem xs={12} sm={6} md={6}>
                    <InputLabel className={classes.label}>
                      To date
                      <span style={{}}> * </span>
                    </InputLabel>
                    <FormControl fullWidth style={{marginBottom: "30px", marginTop: "5px"}}>
                      <Datetime
                        inputProps={{placeholder: ""}}
                        onChange={date => this.setState({checkOut: date})}
                        isValidDate={getValidDatesCheckOut}
                        closeOnSelect={true}
                      />
                    </FormControl>
                  </GridItem>

                  <GridItem xs={12} sm={6} md={6} style={{marginTop: "20px"}}>
                    <InputLabel>
                      Number of persons
                      <span style={{}}> * </span>
                    </InputLabel>
                    <CustomInput
                      labelText=""
                      className={classes.personPicker}
                      id="persons"
                      formControlProps={{
                        fullWidth: true
                      }}
                      inputProps={{
                        type: "number",
                      }}
                      onChange={e => this.setState({persons: e.target.value})}
                    />
                  </GridItem>

                  <GridItem xs={12} sm={6} md={6} style={{marginTop: "20px"}}>
                    <InputLabel>
                      Range
                    </InputLabel>
                    <CustomInput
                      labelText=""
                      className={classes.personPicker}
                      id="range"
                      formControlProps={{
                        fullWidth: true
                      }}
                      inputProps={{
                        type: "number",
                      }}
                    />
                  </GridItem>

                  <GridItem xs={12} sm={12} md={12} style={{marginTop: "20px", textAlign: "left"}}>
                    <Quote
                      text="* Required fields"
                    />
                  </GridItem>

                </GridContainer>
              </CardBody>

              <CardFooter>
                <Button onClick={onSearchSubmit}>Search</Button>
              </CardFooter>
            </Card>

          </GridItem>
        </GridContainer>
      </div>
    );
  }
}

HotelSearchSection.propTypes = {
  classes: PropTypes.object,
  error: PropTypes.object,
  onSearch: PropTypes.func,
};

export default withStyles(productStyle)(HotelSearchSection);
