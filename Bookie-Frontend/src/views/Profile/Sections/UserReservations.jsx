import React from "react";
// nodejs library that concatenates classes
import PropTypes from 'prop-types';
// material-ui components
import withStyles from "material-ui/styles/withStyles";
// @material-ui/icons
import Camera from "@material-ui/icons/Camera";
import Palette from "@material-ui/icons/Palette";
import Favorite from "@material-ui/icons/Favorite";
// core components
import GridContainer from "components/Grid/GridContainer.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import NavPills from "components/NavPills/NavPills.jsx";
// card components
import Card from "components/Card/Card.jsx";
import CardBody from "components/Card/CardBody.jsx";

import profilePageStyle from "assets/jss/material-kit-react/views/profilePage.jsx";
import CardHeader from "../../../components/Card/CardHeader";
import {connect} from "react-redux";
import Button from "../../../components/CustomButtons/Button";
import {reservationActions} from "../../../actions";

class UserReservations extends React.Component {
  componentDidMount() {
    const {successful} = this.props;

    if (successful === null) {
      //this.props.goToMain();
    }
  }

  onDetailsOpen = (reservation) => {
    this.props.reservationDetails({
      reservation,
      userId: this.props.userInfo.id
    });
  };

  render() {
    const {classes, reservationsByUser, successful} = this.props;

    return (
      <div>
        <GridContainer justify="center">
          <GridItem xs={12} sm={12} md={10} lg={8} className={classes.navWrapper}>
            <NavPills
              color="info"
              tabs={[
                {
                  tabButton: "Upcoming",
                  tabIcon: Camera,
                  tabContent: (
                    <GridItem xs={12} sm={12} md={12} className="hello">
                      {(successful !== null && reservationsByUser.upcoming.length > 0) ?
                        reservationsByUser.upcoming.map((reservation, key) => (
                          <Card style={{marginBottom: '50px'}} key={key}>
                            <CardHeader color="info">{reservation.hotelName}</CardHeader>
                            <CardBody>
                              <div>
                                <p> Your hotel reservation from </p>
                                <span>{reservation.inDate} - {reservation.outDate}</span>
                              </div>
                              <Button className={classes.icon}
                                      onClick={(event) => this.onDetailsOpen(reservation)}>
                                See reservation details
                              </Button>
                            </CardBody>
                          </Card>
                        ))
                        : <h3 style={{float: 'center'}}> You have no upcoming bookings for now</h3>}

                    </GridItem>
                  )
                },
                {
                  tabButton: "Done",
                  tabIcon: Palette,
                  tabContent: (
                    <GridItem xs={12} sm={12} md={12} className="hello">
                      {(successful !== null && reservationsByUser.past.length > 0) ?
                        reservationsByUser.past.map((reservation, key) => (
                          <Card style={{marginBottom: '50px'}} key={key}>
                            <CardHeader color="info">{reservation.hotelName}</CardHeader>

                            <CardBody>
                              <div>
                                <p> Your hotel reservation from </p>
                                <span>{reservation.inDate} - {reservation.outDate}</span>
                              </div>
                              <Button className={classes.icon}
                                      onClick={(event) => this.onDetailsOpen(reservation)}>
                                See reservation details
                              </Button>

                            </CardBody>
                          </Card>
                        ))
                        : <h3 style={{float: 'center'}}> You have no past bookings for now</h3>}
                    </GridItem>
                  )
                },
                {
                  tabButton: "Canceled",
                  tabIcon: Favorite,
                  tabContent: (
                    <GridItem xs={12} sm={12} md={12} className="hello">

                      {(successful !== null && reservationsByUser.cancelled.length > 0) ?
                        reservationsByUser.cancelled.map((reservation, key) => (
                          <Card style={{marginBottom: '50px'}} key={key}>
                            <CardHeader color="info">{reservation.hotelName}</CardHeader>

                            <CardBody>
                              <div>
                                <p> Your hotel reservation from </p>
                                <span>{reservation.inDate} - {reservation.outDate}</span>
                              </div>
                              <Button className={classes.icon}
                                      onClick={(event) => this.onDetailsOpen(reservation)}>
                                See reservation details
                              </Button>

                            </CardBody>
                          </Card>
                        ))
                        : <h3 style={{float: 'center'}}> You have no cancelled bookings for now</h3>}
                    </GridItem>
                  )
                }
              ]}
            />
          </GridItem>

        </GridContainer>
      </div>
    );
  }
}

UserReservations.propTypes = {
  classes: PropTypes.object.isRequired,
  successful: PropTypes.bool,
  userInfo: PropTypes.object
};

const mapStateToProps = (state) => ({
  reservationsByUser: state.reservations.reservationsByUser,
  successful: state.reservations.successful,
  userInfo: state.users.userInfo
});

const mapDispatchToProps = (dispatch) => ({
  reservationDetails: (params) => dispatch(reservationActions.displayReservationDetails(params)),
});

const withConnect = connect(mapStateToProps, mapDispatchToProps)(UserReservations);

export default withStyles(profilePageStyle)(withConnect);
