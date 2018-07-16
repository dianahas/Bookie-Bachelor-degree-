import React, {Component} from 'react'
import {withStyles} from "material-ui/styles/index";
import {connect} from "react-redux";

const google = window.google;
const styles = theme => ({
  map: {
    height: 500,
    width: 500,
  },
});

class GoogleMap extends Component {

  componentDidMount() {
    const {latitude, longitude} = this.props;

    this.initMap(latitude, longitude);
  }

  initMap = (latitude, longitude) => {
    let location = {lat: latitude, lng: longitude};
    let map = new google.maps.Map(document.getElementById('map'), {
      zoom: 15,
      center: location
    });

    let mainHotelMarker = new google.maps.Marker({
      position: location,
    });
    mainHotelMarker.setMap(map);

    let hotelsList = this.props.hotelSearchInfoList;

    for (let i = 0; i < hotelsList.length; i++) {
      let hotelLocation = {lat: hotelsList[i].location.latitude, lng: hotelsList[i].location.longitude};
      let hotelMarker = new google.maps.Marker({
        position: hotelLocation,
        title: hotelsList[i].propertyName
      });

      hotelMarker.setMap(map);
    }

    let service = new google.maps.places.PlacesService(map);

    this.searchForRestaurants(service, map, location);
    this.searchForParking(service, map, location);

  };

  searchForParking = (service, map, location) => {
    service.nearbySearch({
      location: location,
      radius: 1000,
      type: ['parking']
    }, function callback(results, status) {
      if (status === google.maps.places.PlacesServiceStatus.OK) {
        for (let i = 0; i < results.length; i++) {
          let placeLoc = results[i].geometry.location;

          let parkingMarker = new google.maps.Marker({
            position: placeLoc,
            title: results[i].name,
            icon: 'https://maps.google.com/mapfiles/kml/shapes/parking_lot_maps.png',
          });

          parkingMarker.setMap(map);
        }
      }
    });
  };

  searchForRestaurants = (service, map, location) => {
    service.nearbySearch({
      location: location,
      radius: 5500,
      type: ['restaurant']
    }, function callback(results, status) {
      if (status === google.maps.places.PlacesServiceStatus.OK) {
        for (let i = 0; i < results.length; i++) {
          let placeLoc = results[i].geometry.location;

          let restaurantMarker = new google.maps.Marker({
            position: placeLoc,
            title: results[i].name,
            icon: 'https://i.imgur.com/iWb4G4Q.png',
          });

          restaurantMarker.setMap(map);
        }
      }
    });
  };

  static defaultProps = {
    center: {lat: 40.7446790, lng: -73.9485420},
    zoom: 11
  };

  render() {
    const {classes} = this.props;

    return (
      <div className='google-map'>
        <div id="map" className={classes.map}></div>
      </div>
    )
  }
}

const mapStateToProps = (state) => ({
  hotelSearchInfoList: state.hotels.hotelSearchInfoList,
});

const withConnect = connect(mapStateToProps, null)(GoogleMap);

export default withStyles(styles)(withConnect);