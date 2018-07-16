import React, {Component} from 'react';
import {withStyles} from 'material-ui/styles';
import GridContainer from "../../../components/Grid/GridContainer";
import PropTypes from "prop-types";
import Card from "../../../components/Card/Card";
import CardBody from "../../../components/Card/CardBody";
import Button from "../../../components/CustomButtons/Button";
import GridItem from "components/Grid/GridItem.jsx";
import landingPageStyle from "../../../assets/jss/material-kit-react/views/landingPage";
import InputLabel from "material-ui/es/Input/InputLabel";
import FormControl from "material-ui/es/Form/FormControl";
import GoogleAutocomplete from "./GoogleSearch";
import Datetime from "react-datetime";
import CustomInput from "../../../components/CustomInput/CustomInput";
import CardFooter from "../../../components/Card/CardFooter";
import moment from "moment/moment";

class SearchMask extends Component {

  state = {
    checkIn: '',
    checkOut: '',
    persons: '',
    address: '',
  };

  handleSelect = (address) => {
    this.setState({address: address})
  };

  handleSubmitSearch = () => {
    let checkInDate;
    let checkOutDate;

    if (this.state.checkIn === "") {
      checkInDate = this.props.inDate;
    } else {
      checkInDate = this.state.checkIn.format("YYYY-MM-DD");
    }

    if (this.state.checkOut === "") {
      checkOutDate = this.props.outDate;
    } else {
      checkOutDate = this.state.checkOut.format("YYYY-MM-DD");
    }

    let values = {
      inDate: checkInDate,
      outDate: checkOutDate,
      address: this.state.address || this.props.location,
      persons: this.state.persons || this.props.persons
    };

    this.props.handleSubmit(values)
  };

  render() {
    const {classes, location, inDate, outDate, persons} = this.props;

    const getValidDatesCheckIn = (currentDate) => {
      if (this.state.checkOut !== "") {
        return currentDate.isBefore(this.state.checkOut);
      }

      else {
        // only allow today and future dates
        let yesterday = moment().subtract(1, 'day');
        return currentDate.isAfter(yesterday);
      }
    };

    const getValidDatesCheckOut = (currentDate) => {
      if (this.state.checkIn !== "") {
        return currentDate.isAfter(this.state.checkIn);
      }

      else {
        // only allow today and future dates
        let yesterday = moment().subtract(1, 'day');
        return currentDate.isAfter(yesterday);
      }
    };

    return (
      <Card>
        <CardBody>
          <GridContainer>
            <GridItem xs={12} sm={12} md={12} style={{marginBottom: "30px", marginTop: "20px"}}>
              <InputLabel className={classes.labelRoot + " " + classes.labelRootSuccess}>
                Search for address
              </InputLabel>
              <FormControl fullWidth>
                <GoogleAutocomplete
                  style={{width: '90%'}}
                  onSelect={this.handleSelect}
                  defaultValue={location}
                />
              </FormControl>

            </GridItem>

            <GridItem xs={12} sm={4} md={4}>
              <InputLabel className={classes.label}>
                From date
              </InputLabel>
              <FormControl fullWidth style={{marginTop: "23px"}}>
                <Datetime
                  value={this.state.checkIn || inDate}
                  inputProps={{placeholder: ""}}
                  onChange={date => this.setState({checkIn: date})}
                  isValidDate={getValidDatesCheckIn}
                  closeOnSelect={true}
                />
              </FormControl>
            </GridItem>

            <GridItem xs={12} sm={4} md={4}>
              <InputLabel className={classes.label}>
                To date
              </InputLabel>
              <FormControl fullWidth style={{marginTop: "23px"}}>
                <Datetime
                  value={this.state.checkOut || outDate}
                  inputProps={{placeholder: ""}}
                  onChange={date => this.setState({checkOut: date})}
                  isValidDate={getValidDatesCheckOut}
                  closeOnSelect={true}
                />
              </FormControl>
            </GridItem>

            <GridItem xs={12} sm={4} md={4}>
              <InputLabel className={classes.label}>
                Number of persons
              </InputLabel>
              <CustomInput
                labelText=""
                className={classes.personPicker}
                id="persons"
                formControlProps={{
                  fullWidth: true
                }}
                inputProps={{
                  type: "number",
                }}
                value={this.state.persons || persons}
                onChange={e => this.setState({persons: e.target.value})}
              />
            </GridItem>
          </GridContainer>

        </CardBody>
        <CardFooter>
          <Button color="default" onClick={this.handleSubmitSearch}>Search</Button>
        </CardFooter>
      </Card>
    );
  }
}

SearchMask.propTypes = {
  inDate: PropTypes.string,
  outDate: PropTypes.string,
  location: PropTypes.string,
  persons: PropTypes.string,
  handleSubmit: PropTypes.func,
};

export default withStyles(landingPageStyle)(SearchMask)