import React from "react";
// nodejs library that concatenates classes
import classNames from "classnames";
import PropTypes from 'prop-types';
// material-ui components
import withStyles from "material-ui/styles/withStyles";
// core components
import Header from "components/Header/Header.jsx";
import Footer from "components/Footer/Footer.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import HeaderLinks from "components/Header/HeaderLinks.jsx";
import Parallax from "components/Parallax/Parallax.jsx";
import profilePageStyle from "assets/jss/material-kit-react/views/profilePage.jsx";
import {connect} from "react-redux";
import UserReservations from "./Sections/UserReservations";
import {goBack, push} from "react-router-redux";
import ProfileDetails from "./Sections/ProfileDetails";
import Button from "../../components/CustomButtons/Button";

class ProfilePage extends React.Component {
  componentDidMount() {
    const {successful} = this.props;

    if (successful === null) {
      this.props.goToMain();
    }
  }

  onBack = () => {
    this.props.onGoBack();
  };

  render() {
    const {classes, userInfo, ...rest} = this.props;

    return (
      <div>
        <Header
          color="transparent"
          brand=""
          rightLinks={<HeaderLinks/>}
          fixed
          changeColorOnScroll={{
            height: 200,
            color: "white"
          }}
          {...rest}
        />

        <Parallax small filter image={require("assets/img/profile-bg.jpg")}>
          <div className={classes.container}>
            <GridContainer>
              <GridItem xs={12} sm={12} md={12}>
                <h2 className={classes.title}
                    style={{textAlign: "center"}}>{userInfo.firstName} {userInfo.lastName}</h2>
              </GridItem>
            </GridContainer>
          </div>
        </Parallax>

        <div className={classNames(classes.main, classes.mainRaised)}>
          <div>
            <div className={classes.container}>

              <ProfileDetails/>

              <UserReservations/>
              <Button style={{float: "right"}} onClick={(event) => this.onBack()}> Back </Button>

            </div>
          </div>
        </div>

        <Footer/>
      </div>
    );
  }
}

ProfilePage.propTypes = {
  classes: PropTypes.object.isRequired,
  successful: PropTypes.bool,
  userInfo: PropTypes.object
};

const mapStateToProps = (state) => ({
  successful: state.reservations.successful,
  userInfo: state.users.userInfo
});

const mapDispatchToProps = (dispatch) => ({
  goToMain: () => dispatch(push(`/`)),
  onGoBack: () => dispatch(goBack()),
});

const withConnect = connect(mapStateToProps, mapDispatchToProps)(ProfilePage);

export default withStyles(profilePageStyle)(withConnect);
