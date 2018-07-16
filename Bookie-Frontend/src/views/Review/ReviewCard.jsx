import React, {Component} from 'react';
import {withStyles} from 'material-ui/styles';
import PropTypes from 'prop-types';
import CardBody from "../../components/Card/CardBody";
import profilePageStyle from "assets/jss/material-kit-react/views/profilePage.jsx";
import Card from "../../components/Card/Card";
import GridItem from "components/Grid/GridItem.jsx";
import Rater from 'react-rater'
import CardHeader from "../../components/Card/CardHeader";
import Typography from "material-ui/es/Typography/Typography";

class ReviewCard extends Component {

  render() {
    const {review, classes} = this.props;

    return (
      <GridItem xs={8} sm={8} md={8}>
        <Card className={classes.textCenter}>
          <CardHeader color="info">{review.hotelName}</CardHeader>

          <CardBody style={{height: "100px"}}>
            <GridItem xs={12} sm={12} md={12}>
              <Typography className={classes.cardTitle}>
                Text: {review.text}
              </Typography>
            </GridItem>

            <GridItem xs={12} sm={12} md={12}>
              <span> Rating: </span>
              <span> <Rater total={5} rating={review.rating}/></span>
            </GridItem>
          </CardBody>

        </Card>
      </GridItem>

    );
  }
}

ReviewCard.propTypes = {
  review: PropTypes.object.isRequired,
};

export default withStyles(profilePageStyle)(ReviewCard);