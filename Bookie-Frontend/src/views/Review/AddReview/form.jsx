import React from "react";
import PropTypes from "prop-types";
import GridContainer from "../../../components/Grid/GridContainer";
import GridItem from "../../../components/Grid/GridItem";
import CustomInput from "../../../components/CustomInput/CustomInput";
import Button from "../../../components/CustomButtons/Button";
import withStyles from "material-ui/styles/withStyles";
import workStyle from "assets/jss/material-kit-react/views/landingPageSections/workStyle.jsx";
import Rater from 'react-rater'
import 'react-rater/lib/react-rater.css'

class AddReviewForm extends React.Component {

  state = {
    text: '',
    rating: ''
  };

  handleRatingClick = (rating) => {
    this.setState({rating: rating.rating});
  };

  handleSubmit = (e) => {
    e.preventDefault();

    let values = {
      text: this.state.text === "" ? this.props.selectedReview.text : this.state.text,
      rating: this.state.rating === "" ? this.props.selectedReview.rating : this.state.rating,
      hotelName: this.props.hotelName
    };

    this.props.onSubmitReview(values);
  };

  render() {
    const {selectedReview} = this.props;

    return (
      <form>
        <GridContainer>
          <GridItem xs={12} sm={12} md={12} lg={12}>
            <CustomInput
              labelText="Review text"
              id="text"
              formControlProps={{
                fullWidth: true
              }}
              value={this.state.text || selectedReview.text}
              onChange={e => this.setState({text: e.target.value})}
            />
          </GridItem>

          <GridItem xs={12} sm={12} md={12} lg={12}>
            <Rater total={5} rating={this.state.rating || selectedReview.rating} onRate={this.handleRatingClick}/>
          </GridItem>

        </GridContainer>

        <Button onClick={this.handleSubmit}>
          Submit
        </Button>
      </form>
    );
  }
}

AddReviewForm.propTypes = {
  onSubmitReview: PropTypes.func.isRequired,
  selectedReview: PropTypes.object.isRequired,
  hotelName: PropTypes.string.isRequired,
};

export default withStyles(workStyle)(AddReviewForm);