import React, {Component} from 'react';
import {withStyles} from 'material-ui/styles';
import GridContainer from "../../../components/Grid/GridContainer";
import {CardActions, DialogActions, DialogContent, DialogContentText} from "material-ui";
import Dialog from "material-ui/es/Dialog/Dialog";
import PropTypes from "prop-types";
import Card from "../../../components/Card/Card";
import CardBody from "../../../components/Card/CardBody";
import Button from "../../../components/CustomButtons/Button";
import GridItem from "components/Grid/GridItem.jsx";
import CardHeader from "components/Card/CardHeader.jsx";
import Quote from "../../../components/Typography/Quote";

const styles = (theme) => ({
  headline: {
    fontSize: 20,
  }
});

class RoomCard extends Component {
  state = {
    open: false,
  };

  handleClickOpen = () => {
    this.setState({open: true});
  };

  handleClose = () => {
    this.setState({open: false});
  };

  render() {
    const {classes, room, isLoggedIn} = this.props;

    const handleBook = () => {
      if (!isLoggedIn) {
        this.handleClickOpen();
      } else this.props.onBook(room.bookingCode);
    };

    return (
      <GridItem xs={12} sm={6} md={6} lg={4}>
        <Card className={classes.textCenter}>
          <CardHeader style={{
            textAlign: 'center',
            backgroundColor: "#6c757d",
            color: 'white',
            minHeight: '120px'
          }}>{room.descriptions}</CardHeader>
          <CardBody style={{minHeight: '160px'}}>

            <GridContainer className="quote">
              <GridItem xs={6}>
                <Quote
                  text="Guests"
                  author={room.nrOfGuests}
                />
              </GridItem>

              <GridItem xs={6}>
                <Quote
                  text="Price"
                  author={room.totalPrice.amount}
                />
              </GridItem>
            </GridContainer>
          </CardBody>
          <CardActions border>

            <Button size="small" color="primary" onClick={handleBook}>
              Book room
            </Button>

            <Dialog
              open={this.state.open}
              onClose={this.handleClose}
              aria-labelledby="alert-dialog-title"
              aria-describedby="alert-dialog-description"
            >
              <DialogContent>
                <DialogContentText id="alert-dialog-description">
                  Please log in first
                </DialogContentText>
              </DialogContent>
              <DialogActions>
                <Button onClick={this.handleClose} color="primary" autoFocus>
                  Ok
                </Button>
              </DialogActions>
            </Dialog>

          </CardActions>
        </Card>
      </GridItem>
    );
  }
}

RoomCard.propTypes = {
  onBook: PropTypes.func,
};

export default withStyles(styles)(RoomCard)