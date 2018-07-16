import GridItem from "../../../components/Grid/GridItem";
import GridContainer from "../../../components/Grid/GridContainer";
import NavPills from "../../../components/NavPills/NavPills";
import CustomInput from "../../../components/CustomInput/CustomInput";
import React from "react";
import Button from "../../../components/CustomButtons/Button";
import {connect} from "react-redux";
import withStyles from "material-ui/styles/withStyles";
import profilePageStyle from "../../../assets/jss/material-kit-react/views/profilePage";
import {userActions} from "../../../actions";
import ReviewCard from "../../Review/ReviewCard";
import InfoOutline from "@material-ui/icons/es/InfoOutline";
import SnackbarContent from "../../../components/Snackbar/SnackbarContent";
import Check from "@material-ui/icons/es/Check";
import Cards from 'react-credit-cards';
import 'react-credit-cards/es/styles-compiled.css';

class ProfileDetails extends React.Component {

  state = {
    firstName: null,
    lastName: null,
    email: null,
    phoneNumber: null,
    oldPassword: null,
    newPassword: null
  };

  handleProfileUpdateSubmit = (event) => {
    event.preventDefault();

    let profileUpdateObject = {
      id: this.props.userInfo.id,
      firstName: this.state.firstName,
      lastName: this.state.lastName,
      email: this.state.email,
      phoneNumber: this.state.phoneNumber
    };

    this.props.onUpdate(profileUpdateObject);
  };

  handlePasswordUpdateSubmit = (event) => {
    event.preventDefault();

    let passwordUpdateObject = {
      userId: this.props.userInfo.id,
      newPassword: this.state.newPassword,
      oldPassword: this.state.oldPassword,
    };

    this.props.onPasswordUpdate(passwordUpdateObject);

  };

  render() {
    const {userInfo, reviews, successful, profileError, passwordError, profileSuccess, passwordSuccess} = this.props;

    return (
      <GridContainer justify="center">
        <GridItem xs={12} sm={12} md={10} lg={8}>

          <NavPills
            color="warning"
            tabs={[
              {
                tabButton: "Profile",
                tabContent: (
                  <form>
                    {!!(profileError) ?
                      <SnackbarContent
                        message={
                          <span>
                            <b>DANGER:</b> Invalid input: {profileError} !
                          </span>
                        }
                        close
                        color="danger"
                        icon={InfoOutline}/>
                      : null
                    }
                    {!!(profileSuccess) ?
                      <SnackbarContent
                        message={
                          <span>
                            <b>SUCCESS:</b> Your account has been updated !
                          </span>
                        }
                        close
                        color="success"
                        icon={Check}
                      /> : null
                    }
                    <GridItem xs={12} sm={12} md={12}>
                      <CustomInput
                        labelText="First Name*"
                        id="firstName"
                        formControlProps={{
                          fullWidth: true
                        }}
                        value={this.state.firstName || userInfo.firstName}
                        onChange={e => this.setState({firstName: e.target.value})}
                      />
                    </GridItem>

                    <GridItem xs={12} sm={12} md={12}>
                      <CustomInput
                        labelText="Last Name*"
                        id="lastName"
                        formControlProps={{
                          fullWidth: true
                        }}
                        value={this.state.lastName || userInfo.lastName}
                        onChange={e => this.setState({lastName: e.target.value})}
                      />
                    </GridItem>

                    <GridItem xs={12} sm={12} md={12}>
                      <CustomInput
                        labelText="Email*"
                        id="email"
                        formControlProps={{
                          fullWidth: true
                        }}
                        value={this.state.email || userInfo.email}
                        onChange={e => this.setState({email: e.target.value})}
                      />
                    </GridItem>

                    <GridItem xs={12} sm={12} md={12}>
                      <CustomInput
                        labelText="Phone"
                        id="phoneNumber"
                        formControlProps={{
                          fullWidth: true
                        }}
                        value={this.state.phoneNumber || userInfo.phoneNumber}
                        onChange={e => this.setState({phoneNumber: e.target.value})}
                      />
                    </GridItem>

                    <Button type="button" style={{marginLeft: "10px"}} onClick={this.handleProfileUpdateSubmit}>Update
                      Profile</Button>
                  </form>
                )
              },
              {
                tabButton: "Change password",
                tabContent: (
                  <div>
                    {!!(passwordError) ?
                      <SnackbarContent
                        message={
                          <span>
                            <b>DANGER:</b> Invalid input: {passwordError} !
                          </span>
                        }
                        close
                        color="danger"
                        icon={InfoOutline}/>
                      : null
                    }
                    {!!(passwordSuccess) ?
                      <SnackbarContent
                        message={
                          <span>
                            <b>SUCCESS:</b> Your password was successfully changed !
                          </span>
                        }
                        close
                        color="success"
                        icon={Check}
                      /> : null
                    }
                    <form>
                      <GridItem xs={12} sm={12} md={12}>
                        <CustomInput
                          labelText="Old password*"
                          id="oldPassword"
                          formControlProps={{
                            fullWidth: true
                          }}
                          inputProps={{
                            type: "password",
                          }}
                          value={this.state.oldPassword}
                          onChange={e => this.setState({oldPassword: e.target.value})}
                        />
                      </GridItem>

                      <GridItem xs={12} sm={12} md={12}>
                        <CustomInput
                          labelText="New password*"
                          id="newPassword"
                          formControlProps={{
                            fullWidth: true
                          }}
                          inputProps={{
                            type: "password",
                          }}
                          value={this.state.newPassword}
                          onChange={e => this.setState({newPassword: e.target.value})}
                        />
                      </GridItem>

                      <Button type="button" style={{marginLeft: "10px"}} onClick={this.handlePasswordUpdateSubmit}>Update
                        Password</Button>
                    </form>
                  </div>
                )
              },
              {
                tabButton: "Credit Cards",
                tabContent: (
                  <Cards
                    number="1234123412341234"
                    name="John Doe"
                    expiry="10/20"
                    cvc="666"
                    focused="number"
                  />
                )
              },
              {
                tabButton: "Reviews",
                tabContent: (
                  <GridItem xs={12} sm={12} md={12} className="hello">
                    {(successful !== null && reviews.length > 0) ?
                      reviews.map((review, key) => (
                        <ReviewCard review={review} key={key}/>
                      ))
                      : <h3 style={{float: 'center'}}> You have no reviews for now</h3>}

                  </GridItem>
                )
              }
            ]}
          />

        </GridItem>
      </GridContainer>
    );
  }
}

const mapStateToProps = (state) => ({
  userInfo: state.users.userInfo,
  reviews: state.reviews.reviewsByUser,
  successful: state.reviews.successful,
  profileError: state.notification.userProfileError,
  passwordError: state.notification.userPasswordError,
  profileSuccess: state.notification.userProfileSuccess,
  passwordSuccess: state.notification.userPasswordSuccess
});

const mapDispatchToProps = (dispatch) => ({
  onUpdate: (params) => dispatch(userActions.updateProfile(params)),
  onPasswordUpdate: (params) => dispatch(userActions.changePassword(params)),
});

const withConnect = connect(mapStateToProps, mapDispatchToProps)(ProfileDetails);

export default withStyles(profilePageStyle)(withConnect);
