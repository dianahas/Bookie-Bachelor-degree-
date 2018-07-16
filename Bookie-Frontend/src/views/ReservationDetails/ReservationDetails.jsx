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
import {connect} from "react-redux";
import Card from "../../components/Card/Card";
import CardHeader from "../../components/Card/CardHeader";
import CardBody from "../../components/Card/CardBody";
import Button from "../../components/CustomButtons/Button";
import CardFooter from "../../components/Card/CardFooter";

import AddReviewForm from "../Review/AddReview/form"
import {reservationActions, reviewActions} from "../../actions";
import InfoOutline from "@material-ui/icons/es/InfoOutline";
import Check from "@material-ui/icons/es/Check";
import SnackbarContent from "../../components/Snackbar/SnackbarContent";
import {goBack, push} from "react-router-redux";

const dashboardRoutes = [];

class ReservationDetails extends React.Component {

  onReviewAddSubmit(values) {
    let params = {
      hotelCode: this.props.reservationDetails.hotelCode,
      userId: this.props.userInfo.id,
      text: values.text,
      rating: values.rating
    };

    this.props.onReviewAdd(params);
  }

  cancelReservation = (reservationId) => {
    this.props.onCancel(reservationId)
  };

  onBack = () => {
    this.props.onGoBack();
  };

  render() {
    const {
      classes, reservationDetails, selectedReview, errorReservation, errorReview,
      successReservation, successReview, ...rest
    } = this.props;

    const onReviewSubmit = (values) => {
      this.onReviewAddSubmit(values);
    };

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
                <h3 className={classes.title}>Reservation Details</h3>
                <br/>
              </GridItem>
            </GridContainer>
          </div>
        </Parallax>

        <div className={classNames(classes.main, classes.mainRaised)}>
          <div className={classes.container}>
            <div className={classes.section}>
              <GridContainer style={{marginBottom: "80px"}}>
                <GridItem xs={12} sm={12} md={12} lg={12}>
                  {!!(errorReservation) ?
                    <SnackbarContent
                      message={
                        <span>
                            <b>DANGER:</b> The reservation could not be canceled: {errorReservation} !
                          </span>
                      }
                      close
                      color="danger"
                      icon={InfoOutline}/>
                    : null
                  }
                </GridItem>

                <GridItem xs={12} sm={12} md={12} lg={12}>
                  {!!(successReservation) ?
                    <SnackbarContent
                      message={
                        <span>
                            <b>SUCCESS:</b> Your reservation was successfully canceled !
                          </span>
                      }
                      close
                      color="success"
                      icon={Check}
                    /> : null
                  }
                </GridItem>

                <GridItem xs={12} sm={12} md={12} lg={12} style={{marginTop: "30px"}}>
                  <Card className={classes.textCenter}>
                    <CardHeader color="info">Your reservation at hotel {reservationDetails.hotelName}</CardHeader>

                    <CardBody>
                      <GridContainer>
                        <GridItem xs={12} sm={6} md={4} lg={4}>
                          <img
                            style={{height: "200px", width: "100%", display: "block"}}
                            className={classes.imgCardTop}
                            src={reservationDetails.pictureUrl}
                            alt="Card-img-cap"
                          />
                        </GridItem>

                        <GridItem xs={12} sm={6} md={4} lg={4}>
                          <h4 className={classes.cardTitle}>
                            <p>
                              <span style={{fontWeight: "bold", fontSize: "16px"}}>The price is : </span>
                              <span> {reservationDetails.price} USD</span>
                            </p>
                          </h4>

                          {reservationDetails.address !== undefined ?
                            <h4 className={classes.cardTitle}>
                              <p>
                                <span style={{fontWeight: "bold", fontSize: "16px"}}> Situated in: </span>
                                {reservationDetails.address}
                              </p>
                            </h4>
                            : null
                          }

                          {reservationDetails.inDate !== undefined ?
                            <h4 className={classes.cardTitle}>
                              <p>
                                <span style={{fontWeight: "bold", fontSize: "16px"}}> In-date is: </span>
                                {reservationDetails.inDate}
                              </p>
                            </h4>
                            : null
                          }

                          {reservationDetails.inDate !== undefined ?
                            <h4 className={classes.cardTitle}>
                              <p>
                                <span style={{fontWeight: "bold", fontSize: "16px"}}> Out-date is: </span>
                                {reservationDetails.outDate}
                              </p>
                            </h4>
                            : null
                          }

                        </GridItem>

                        <GridItem xs={12} sm={6} md={4} lg={4}>
                          {reservationDetails.guestInfo !== undefined ?
                            <h4 className={classes.cardTitle}>
                              <p>
                                <span style={{fontWeight: "bold", fontSize: "16px"}}>Reservation was made for : </span>
                                {reservationDetails.guestInfo.firstName} {reservationDetails.guestInfo.lastName}
                              </p>
                            </h4>
                            : null
                          }

                          {reservationDetails.persons !== undefined ?
                            <h4 className={classes.cardTitle}>
                              <p>
                              <span style={{
                                fontWeight: "bold",
                                fontSize: "16px"
                              }}>For {reservationDetails.persons} guests</span>
                              </p>
                            </h4>
                            : null
                          }

                        </GridItem>
                      </GridContainer>
                    </CardBody>

                    <CardFooter>
                      {reservationDetails.status === 1 ?
                        <GridItem xs={12} sm={12} md={12}>
                          <Button color="primary"
                                  onClick={(event) => this.cancelReservation(reservationDetails.id)}>Cancel</Button>
                          <Button color="primary">Update</Button>
                        </GridItem>
                        : null
                      }
                    </CardFooter>

                  </Card>
                  <Button style={{float: "right"}} onClick={(event) => this.onBack()}> Back </Button>
                </GridItem>
              </GridContainer>
            </div>
          </div>
        </div>

        {reservationDetails.status !== null && reservationDetails.status === 3 ?
          <div className={classNames(classes.main, classes.mainRaised)}>
            <div className={classes.container}>
              <div className={classes.section}>
                <GridContainer style={{marginBottom: "80px"}}>

                  <GridItem xs={12} sm={12} md={12} lg={12}>
                    {!!(errorReview) ?
                      <SnackbarContent
                        message={
                          <span>
                            <b>DANGER:</b> The review could not be submitted: {errorReview} !
                          </span>
                        }
                        close
                        color="danger"
                        icon={InfoOutline}/>
                      : null
                    }
                  </GridItem>

                  <GridItem xs={12} sm={12} md={12} lg={12}>
                    {!!(successReview) ?
                      <SnackbarContent
                        message={
                          <span>
                            <b>SUCCESS:</b> Your review was successfully submitted !
                          </span>
                        }
                        close
                        color="success"
                        icon={Check}
                      /> : null
                    }
                  </GridItem>

                  <GridItem xs={12} sm={12} md={12} lg={12} style={{marginTop: "20px"}}>
                    <Card className={classes.textCenter}>
                      <CardHeader color="info">Add a review here</CardHeader>

                      <CardBody>
                        <AddReviewForm selectedReview={selectedReview} onSubmitReview={onReviewSubmit}
                                       hotelName={reservationDetails.hotelName}/>
                      </CardBody>
                    </Card>
                  </GridItem>
                </GridContainer>
              </div>
            </div>
          </div>
          : null
        }

        <Footer/>
      </div>
    );
  }
}

const mapStateToProps = (state) => ({
  reservationDetails: state.reservations.reservation,
  userInfo: state.users.userInfo,
  selectedReview: state.reservations.review,
  errorReservation: state.notification.cancelReservationError,
  errorReview: state.notification.reviewError,
  successReservation: state.notification.cancelReservationSuccess,
  successReview: state.notification.reviewSuccess
});

const mapDispatchToProps = (dispatch) => ({
  onReviewAdd: (values) => dispatch(reviewActions.addReview(values)),
  onCancel: (values) => dispatch(reservationActions.cancelReservation(values)),
  goToMain: () => dispatch(push(`/`)),
  onGoBack: () => dispatch(goBack()),
});

const withConnect = connect(mapStateToProps, mapDispatchToProps)(ReservationDetails);

export default withStyles(landingPageStyle)(withConnect);