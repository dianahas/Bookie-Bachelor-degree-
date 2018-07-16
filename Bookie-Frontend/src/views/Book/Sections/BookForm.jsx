import React from "react";
// material-ui components
import withStyles from "material-ui/styles/withStyles";
// core components
import GridContainer from "components/Grid/GridContainer.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import CustomInput from "components/CustomInput/CustomInput.jsx";
import Button from "components/CustomButtons/Button.jsx";

import workStyle from "assets/jss/material-kit-react/views/landingPageSections/workStyle.jsx";
import PropTypes from 'prop-types';
// @material-ui/icons
import Quote from "components/Typography/Quote.jsx";
// card components
import Card from "components/Card/Card.jsx";
import CardBody from "components/Card/CardBody.jsx";
import SnackbarContent from "../../../components/Snackbar/SnackbarContent";
import InfoOutline from "@material-ui/icons/es/InfoOutline";
import Check from "@material-ui/icons/es/Check";

class BookForm extends React.Component {

  state = {
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: ''
  };

  onBookSubmit = (event) => {
    event.preventDefault();

    let guestInfo = {
      firstName: this.state.firstName === "" ? this.props.userInfo.firstName : this.state.firstName,
      lastName: this.state.lastName === "" ? this.props.userInfo.lastName : this.state.lastName,
      email: this.state.email === "" ? this.props.userInfo.email : this.state.email,
      phoneNumber: this.state.phoneNumber === "" ? this.props.userInfo.phoneNumber : this.state.phoneNumber,
    };

    this.props.bookReservation(guestInfo);
  };

  render() {
    const {classes, userInfo, error, success, onBack} = this.props;
    return (
      <div className={classes.section}>
        <GridContainer justify="center">
          <GridItem cs={12} sm={12} md={12}>
            <Card>
              <CardBody>
                {!!(error) ?
                  <SnackbarContent
                    message={
                      <span>
                        <b>DANGER:</b> Could not book room: {error} !
                      </span>
                    }
                    close
                    color="danger"
                    icon={InfoOutline}/>
                  : null
                }

                {!!(success) ?
                  <SnackbarContent
                    message={
                      <span>
                            <b>SUCCESS:</b> Your reservation was successfully booked !
                          </span>
                    }
                    close
                    color="success"
                    icon={Check}
                  /> : null
                }

                <h3 className={classes.title}>Book you room here</h3>
                <Quote
                  text="For now the payment can only be made at the property."
                  author=""
                />

                <form>
                  <GridContainer>
                    <GridItem xs={12} sm={12} md={6}>
                      <CustomInput
                        labelText="First Name"
                        id="firstName"
                        formControlProps={{
                          fullWidth: true
                        }}
                        value={this.state.firstName || userInfo.firstName}
                        onChange={e => this.setState({firstName: e.target.value})}
                      />
                    </GridItem>

                    <GridItem xs={12} sm={12} md={6}>
                      <CustomInput
                        labelText="Last Name"
                        id="lastName"
                        formControlProps={{
                          fullWidth: true
                        }}
                        value={this.state.lastName || userInfo.lastName}
                        onChange={e => this.setState({lastName: e.target.value})}
                      />
                    </GridItem>

                    <GridItem xs={12} sm={12} md={6}>
                      <CustomInput
                        labelText="E-mail"
                        id="email"
                        formControlProps={{
                          fullWidth: true
                        }}
                        value={this.state.email || userInfo.email}
                        onChange={e => this.setState({email: e.target.value})}
                      />
                    </GridItem>

                    <GridItem xs={12} sm={12} md={6}>
                      <CustomInput
                        labelText="Phone number"
                        id="phoneNumber"
                        formControlProps={{
                          fullWidth: true
                        }}
                        value={this.state.phoneNumber || userInfo.phoneNumber}
                        onChange={e => this.setState({phoneNumber: e.target.value})}
                      />
                    </GridItem>

                    <GridItem
                      style={{marginTop: "25px"}} xs={12} sm={12} md={12}
                    >
                      <Button color="primary" onClick={this.onBookSubmit}>Book room</Button>
                    </GridItem>

                  </GridContainer>
                </form>

              </CardBody>
            </Card>
            <Button style={{float: 'right'}} onClick={(event) => onBack()}> Back </Button>

          </GridItem>
        </GridContainer>
      </div>
    );
  }
}

BookForm.propTypes = {
  selectedHotel: PropTypes.object,
  userInfo: PropTypes.object,
  bookReservation: PropTypes.func.isRequired,
  onBack: PropTypes.func.isRequired,
  error: PropTypes.object,
  success: PropTypes.object
};

export default withStyles(workStyle)(BookForm);