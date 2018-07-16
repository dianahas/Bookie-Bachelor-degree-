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
import RegisterAccountForm from "./Sections/RegisterForm.jsx";
import {connect} from "react-redux";
import {userActions} from "../../actions";
import {goBack} from "react-router-redux";

const dashboardRoutes = [];

class Register extends React.Component {

  registerAccount = (values) => {
    this.props.onRegister(values);
  };

  onBack = () => {
    this.props.onGoBack();
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
                <h3 className={classes.title}>Create account</h3>
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
            <RegisterAccountForm onRegister={this.registerAccount} error={error} onBack={(event) => this.onBack()}/>
          </div>
        </div>
        <Footer/>
      </div>
    );
  }
}

const mapStateToProps = (state) => ({
  error: state.notification.userRegisterError,
});

const mapDispatchToProps = (dispatch) => ({
  onRegister: (value) => dispatch(userActions.register(value)),
  onGoBack: () => dispatch(goBack()),
});

const withConnect = connect(mapStateToProps, mapDispatchToProps)(Register);
export default withStyles(landingPageStyle)(withConnect);
