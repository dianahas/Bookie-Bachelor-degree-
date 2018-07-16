import React from "react";
// material-ui components
import withStyles from "material-ui/styles/withStyles";
// core components
import GridContainer from "components/Grid/GridContainer.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import CustomInput from "components/CustomInput/CustomInput.jsx";
import Button from "components/CustomButtons/Button.jsx";
import workStyle from "assets/jss/material-kit-react/views/landingPageSections/workStyle.jsx";
// card components
import Card from "components/Card/Card.jsx";
import CardBody from "components/Card/CardBody.jsx";
import SnackbarContent from "../../../components/Snackbar/SnackbarContent";
import InfoOutline from "@material-ui/icons/es/InfoOutline";
import PropTypes from "prop-types";

import Quote from "components/Typography/Quote.jsx";

class RegisterAccountForm extends React.Component {

  state = {
    username: '',
    password: '',
    confirmPassword: '',
    firstName: '',
    lastName: '',
    email: '',
    confirmEmail: '',
    phoneNumber: ''
  };

  handleRegisterSubmit = (event) => {
    event.preventDefault();

    if (this.state.email === this.state.confirmEmail && this.state.password === this.state.confirmPassword) {
      let registerObject = {
        username: this.state.username,
        password: this.state.password,
        firstName: this.state.firstName,
        lastName: this.state.lastName,
        email: this.state.email,
        phoneNumber: this.state.phoneNumber
      };

      this.props.onRegister(registerObject);
    }
  };

  render() {
    const {classes, error, onBack} = this.props;

    return (
      <div className={classes.section}>
        <GridContainer justify="center">
          <GridItem cs={12} sm={12} md={12}>

            <Card>
              <CardBody>
                <h3 className={classes.title}>Create a new account</h3>
                {!!(error) ?
                  <SnackbarContent
                    message={
                      <span>
                      <b>Invalid input: </b> {error} !
                    </span>
                    }
                    close
                    color="danger"
                    icon={InfoOutline}/>
                  : null
                }

                <form>
                  <GridContainer>
                    <GridItem xs={12} sm={12} md={12}>
                      <CustomInput
                        labelText="Username*"
                        id="username"
                        formControlProps={{
                          fullWidth: true
                        }}
                        value={this.state.username}
                        onChange={e => this.setState({username: e.target.value})}
                      />
                    </GridItem>

                    <GridItem xs={12} sm={12} md={6}>
                      <CustomInput
                        labelText="First Name*"
                        id="firstName"
                        formControlProps={{
                          fullWidth: true
                        }}
                        value={this.state.firstName}
                        onChange={e => this.setState({firstName: e.target.value})}
                      />
                    </GridItem>

                    <GridItem xs={12} sm={12} md={6}>
                      <CustomInput
                        labelText="Last Name*"
                        id="lastName"
                        formControlProps={{
                          fullWidth: true
                        }}
                        value={this.state.lastName}
                        onChange={e => this.setState({lastName: e.target.value})}
                      />
                    </GridItem>

                    <GridItem xs={12} sm={12} md={6}>
                      <CustomInput
                        labelText="E-mail*"
                        id="email"
                        formControlProps={{
                          fullWidth: true
                        }}
                        value={this.state.email}
                        onChange={e => this.setState({email: e.target.value})}
                      />
                    </GridItem>

                    <GridItem xs={12} sm={12} md={6}>
                      <CustomInput
                        labelText="Confirm E-mail*"
                        id="confirmEmail"
                        formControlProps={{
                          fullWidth: true
                        }}
                        value={this.state.confirmEmail}
                        onChange={e => this.setState({confirmEmail: e.target.value})}
                      />
                    </GridItem>

                    <GridItem xs={12} sm={12} md={6}>
                      <CustomInput
                        labelText="Password*"
                        id="password"
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          type: "password",
                        }}
                        value={this.state.password}
                        onChange={e => this.setState({password: e.target.value})}
                      />
                    </GridItem>

                    <GridItem xs={12} sm={12} md={6}>
                      <CustomInput
                        labelText="Confirm Password*"
                        id="confirmPassword"
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          type: "password",
                        }}
                        value={this.state.confirmPassword}
                        onChange={e => this.setState({confirmPassword: e.target.value})}
                      />
                    </GridItem>

                    <GridItem xs={12} sm={12} md={12}>
                      <CustomInput
                        labelText="Phone number"
                        id="phoneNumber"
                        formControlProps={{
                          fullWidth: true
                        }}
                        value={this.state.phoneNumber}
                        onChange={e => this.setState({phoneNumber: e.target.value})}
                      />
                    </GridItem>

                    <GridItem xs={12} sm={12} md={12} style={{marginTop: "20px"}}>
                      <Quote
                        text="* Required fields"
                      />
                    </GridItem>

                    <GridItem style={{marginTop: "35px"}} md={12}>
                      <Button color="primary" onClick={this.handleRegisterSubmit}>Create Account</Button>
                    </GridItem>
                  </GridContainer>
                </form>
                <Button style={{float: 'right'}} onClick={(event) => onBack()}> Back </Button>
              </CardBody>
            </Card>

          </GridItem>
        </GridContainer>
      </div>
    );
  }
}

RegisterAccountForm.propTypes = {
  classes: PropTypes.object,
  error: PropTypes.object,
  onRegister: PropTypes.func,
  onBack: PropTypes.func,
};

export default withStyles(workStyle)(RegisterAccountForm);