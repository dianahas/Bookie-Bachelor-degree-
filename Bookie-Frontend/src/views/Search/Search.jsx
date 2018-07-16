import React from "react";
// nodejs library that concatenates classes
import classNames from "classnames";
// material-ui components
import withStyles from "material-ui/styles/withStyles";
// core components
import Header from "components/Header/Header.jsx";
import Footer from "components/Footer/Footer.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import HeaderLinks from "components/Header/HeaderLinks.jsx";
import Parallax from "components/Parallax/Parallax.jsx";
import landingPageStyle from "assets/jss/material-kit-react/views/landingPage.jsx";
// Sections for this page
import HotelSearchSection from "./Sections/Main.jsx";
import * as hotelsActions from "../../actions/hotels";
import {connect} from "react-redux";

const dashboardRoutes = [];

class LandingPage extends React.Component {

  onHotelSearch = ({checkInDate, checkOutDate, latitude, longitude, persons, address, isSearchByHotel, initialSearch, startIndex}) => {
    this.props.getHotelsSearch({
      checkInDate,
      checkOutDate,
      latitude,
      longitude,
      persons,
      address,
      isSearchByHotel,
      initialSearch,
      startIndex
    });
  };

  render() {
    const {classes, error, ...rest} = this.props;
    return (
      <div>
        <Header
          color="transparent"
          routes={dashboardRoutes}
          brand=""
          rightLinks={<HeaderLinks/>}
          fixed
          changeColorOnScroll={{
            height: 400,
            color: "white"
          }}
          {...rest}
        />

        <Parallax filter image={require("assets/img/landing-bg.jpg")}>
          <div className={classes.container}>
            <GridContainer>
              <GridItem xs={12} sm={12} md={6}>
                <h2 className={classes.title}>Bookie</h2>
                <h4>
                  All in one booking engine. Your Story Starts With Us
                </h4>
                <br/>
              </GridItem>
            </GridContainer>
          </div>
        </Parallax>

        <div className={classNames(classes.main, classes.mainRaised)}>
          <div className={classes.container}>
            <HotelSearchSection onSearch={this.onHotelSearch} error={error}/>
          </div>
        </div>

        <Footer/>
      </div>
    );
  }
}

const mapStateToProps = (state) => ({
  error: state.notification.hotelsError
});

const mapDispatchToProps = (dispatch) => ({
  getHotelsSearch: (params) => dispatch(hotelsActions.getHotels(params)),
});

const withConnect = connect(mapStateToProps, mapDispatchToProps)(LandingPage);


export default withStyles(landingPageStyle)(withConnect);