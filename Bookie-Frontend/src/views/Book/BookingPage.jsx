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
import BookForm from "./Sections/BookForm.jsx";
import {connect} from "react-redux";
import {reservationActions} from "../../actions";
import {goBack, push} from "react-router-redux";

const dashboardRoutes = [];

class BookRoom extends React.Component {

  componentDidMount() {
    if (this.props.selectedHotel === null && this.props.userInfo === null) {
      this.props.goToMain();
    }
  }

  onBookReservation = (guestInfo) => {
    if (this.props.selectedHotel !== null && this.props.userInfo !== null) {
      this.props.bookRequest({
        hotelCode: this.props.selectedHotel.propertyCode,
        bookingCode: this.props.match.params.bookingCode,
        userId: this.props.userInfo.id,
        inDate: this.props.arrival,
        outDate: this.props.departure,
        persons: this.props.persons,
        address: this.props.address,
        guestInfo
      })
    }
    else {
      this.props.goToMain();
    }
  };

  onBack = () => {
    this.props.onGoBack();
  };

  render() {
    const {classes, userInfo, error, success, ...rest} = this.props;
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
                <h3 className={classes.title}>Book you room now</h3>
                <br/>
                <paragraph>
                  Create you new account to book at the best available hotels :D.
                </paragraph>
              </GridItem>
            </GridContainer>
          </div>
        </Parallax>
        <div className={classNames(classes.main, classes.mainRaised)}>
          <div className={classes.container}>
            <BookForm userInfo={userInfo} bookReservation={this.onBookReservation}
                      error={error} success={success} onBack={this.onBack}/>
          </div>
        </div>
        <Footer/>
      </div>
    );
  }
}

const mapStateToProps = (state) => ({
  selectedHotel: state.hotels.selectedHotel,
  rooms: state.hotels.rooms,
  userInfo: state.users.userInfo,
  arrival: state.hotels.arrival,
  departure: state.hotels.departure,
  persons: state.hotels.persons,
  address: state.hotels.location,
  error: state.notification.bookError,
  success: state.notification.bookSuccess
});

const mapDispatchToProps = (dispatch) => ({
  bookRequest: (params) => dispatch(reservationActions.bookReservation(params)),
  goToMain: () => dispatch(push(`/`)),
  onGoBack: () => dispatch(goBack()),
});

const withConnect = connect(mapStateToProps, mapDispatchToProps)(BookRoom);
export default withStyles(landingPageStyle)(withConnect);