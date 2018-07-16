import React from "react";
// nodejs library that concatenates classes
import classNames from "classnames";
// material-ui components
import withStyles from "material-ui/styles/withStyles";
// core components
import Header from "components/Header/Header.jsx";
import HeaderLinks from "components/Header/HeaderLinks.jsx";
import Footer from "components/Footer/Footer.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import Parallax from "components/Parallax/Parallax.jsx";
// main design template
import SectionPills from "./Sections/Main.jsx";
import componentsStyle from "assets/jss/material-kit-react/views/components.jsx";

class App extends React.Component {
  render() {
    const {classes, ...rest} = this.props;
    return (
      <div>
        <Header
          brand=""
          rightLinks={<HeaderLinks/>}
          fixed
          color="transparent"
          changeColorOnScroll={{
            height: 400,
            color: "white"
          }}
          {...rest}
        />
        <Parallax image={require("assets/img/bg8.jpg")}>
          <div className={classes.container}>
            <GridContainer>
              <GridItem>
                <div className={classes.brand}>
                  <h3 className={classes.title}>Bookie</h3>
                  <h4 className={classes.subtitle}>
                    Lorem ipsum dolor sit amet, consectetuer adipiscing.
                  </h4>
                </div>
              </GridItem>
            </GridContainer>
          </div>
        </Parallax>

        <div className={classNames(classes.main, classes.mainRaised)}>
          <SectionPills/>
        </div>
        <Footer/>
      </div>
    );
  }
}

export default withStyles(componentsStyle)(App);