import React from "react";
// material-ui components
import withStyles from "material-ui/styles/withStyles";
// @material-ui/icons
// core components
import GridContainer from "components/Grid/GridContainer.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import CustomInput from "components/CustomInput/CustomInput.jsx";
import Button from "components/CustomButtons/Button.jsx";
import workStyle from "assets/jss/material-kit-react/views/landingPageSections/workStyle.jsx";
// card components
import Card from "components/Card/Card.jsx";
import CardBody from "components/Card/CardBody.jsx";

import Quote from "components/Typography/Quote.jsx";

class WorkSection extends React.Component {
  render() {
    const {classes} = this.props;
    return (
      <div className={classes.section}>
        <GridContainer justify="center">
          <GridItem cs={12} sm={12} md={12}>

            <Card>
              <CardBody>
                <h3 className={classes.title}>Add something here</h3>

                <form>
                  <GridContainer>
                    <GridItem xs={12} sm={12} md={6}>
                      <CustomInput
                        labelText="Your Name*"
                        id="name"
                        formControlProps={{
                          fullWidth: true
                        }}
                      />
                    </GridItem>
                    <GridItem xs={12} sm={12} md={6}>
                      <CustomInput
                        labelText="Your Email*"
                        id="email"
                        formControlProps={{
                          fullWidth: true
                        }}
                      />
                    </GridItem>
                    <CustomInput
                      labelText="Your Message*"
                      id="message"
                      formControlProps={{
                        fullWidth: true,
                        className: classes.textArea
                      }}
                      inputProps={{
                        multiline: true,
                        rows: 5
                      }}
                    />

                    <GridItem xs={12} sm={12} md={12} style={{marginTop: "20px"}}>
                      <Quote
                        text="* Required fields"
                      />
                    </GridItem>

                    <GridItem xs={12} sm={12} md={12} lg={12} style={{padding: "40px"}}>
                      <Button color="primary">Send Message</Button>
                      <Button style={{float: 'right'}}> Back </Button>
                    </GridItem>
                  </GridContainer>
                </form>

              </CardBody>
            </Card>
          </GridItem>
        </GridContainer>
      </div>
    );
  }
}

export default withStyles(workStyle)(WorkSection);
