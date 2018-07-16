import React, {Component} from 'react';
import {withStyles} from 'material-ui/styles';
import Button from "components/CustomButtons/Button.jsx";
import PropTypes from 'prop-types';
import Dialog from "material-ui/es/Dialog/Dialog";
import GoogleMap from "../../../components/GoogleMap/index"
import {DialogContent} from "material-ui";
import CardHeader from "../../../components/Card/CardHeader";
import CardBody from "../../../components/Card/CardBody";
import profilePageStyle from "assets/jss/material-kit-react/views/profilePage.jsx";
import Card from "../../../components/Card/Card";
import GridItem from "components/Grid/GridItem.jsx";

class HotelCard extends Component {

  state = {
    open: false,
  };

  handleClickOpen = () => {
    this.setState({open: true});
  };

  handleClose = () => {
    this.setState({open: false});
  };

  onDetails(hotelPropertyCode) {
    this.props.onDetails(hotelPropertyCode);
  }

  render() {
    const {hotel, classes} = this.props;

    return (
      <GridItem xs={12} sm={6} md={6} lg={6} xl={4}>
        <Card className={classes.textCenter}>
          <CardHeader color="info">{hotel.propertyName}</CardHeader>
          <CardBody style={{height: "350px"}}>
            <img
              style={{height: "180px", width: "100%", display: "block"}}
              className={classes.imgCardTop}
              src={hotel.pictureUrl}
              alt="Card-img-cap"
            />

            <h4 className={classes.cardTitle}>
              <p>
                <span style={{fontWeight: "bold", fontSize: "16px"}}>Rooms starting from: </span>
                <span> {hotel.totalPrice.amount} {hotel.totalPrice.currency}</span>
              </p>
            </h4>

            <p>
              <span style={{fontWeight: "bold", fontSize: "16px"}}> Situated in: </span>
              <span> {hotel.address.city} {hotel.address.line1}</span>
            </p>

            {hotel.rating !== 0 ?
              <p>
                <span style={{fontWeight: "bold", fontSize: "16px"}}> Hotel rating is: </span>
                {hotel.rating}
              </p>
              : null}

            {hotel.maxRoomDimension !== -1 ?
              <p>
                <span style={{fontWeight: "bold", fontSize: "16px"}}>Room size: </span>
                {hotel.maxRoomDimension} sq. ft.
              </p>
              : null}

          </CardBody>

          <div style={{textAlign: "center"}} className={classes.textCenter}>
            <Button color="primary" onClick={(event) => this.onDetails(hotel.propertyCode)}>
              Rooms
            </Button>

            <Button onClick={this.handleClickOpen} color="primary">
              Location
            </Button>

            <Button color="primary">
              Wishlist
            </Button>
          </div>

        </Card>

        <Dialog
          open={this.state.open}
          onClose={this.handleClose}
          aria-labelledby="alert-dialog-title"
          aria-describedby="alert-dialog-description"
        >
          <DialogContent>
            <GoogleMap latitude={hotel.location.latitude} longitude={hotel.location.longitude}/>
          </DialogContent>

        </Dialog>
      </GridItem>

    );
  }
}

HotelCard.propTypes = {
  onDetails: PropTypes.func,
};

export default withStyles(profilePageStyle)(HotelCard);