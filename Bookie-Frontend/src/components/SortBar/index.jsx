import {withStyles} from "material-ui/styles/index";
import React, {Component} from "react";
import List from "material-ui/es/List/List";
import PropTypes from "prop-types";
import Button from "../CustomButtons/Button";

const styles = (theme) => ({
  bar: {
    display: "inline"
  }
});

class SortBar extends Component {

  onDefault = () => {
    this.props.onSort({
      ascending: false,
      byPrice: false,
      byRating: false,
      byRoomDimension: false,
      default: true
    });
  };

  onSortByPrice = (ascending) => {
    this.props.onSort({
      ascending,
      byPrice: true,
      byRating: false,
      byRoomDimension: false,
      default: false
    });
  };

  onSortByRating = () => {
    this.props.onSort({
      ascending: false,
      byPrice: false,
      byRating: true,
      byRoomDimension: false,
      default: false
    });
  };

  onSortByRoomDimension = () => {
    this.props.onSort({
      ascending: false,
      byPrice: false,
      byRating: false,
      byRoomDimension: true
    });
  };

  render() {
    const {classes} = this.props;

    return (
      <div style={{textAlign: 'center', padding: '30px'}}>
        <List className={classes.bar}>
          <Button variant="raised" color="primary" onClick={() => this.onDefault()}>Default</Button>
          <Button variant="raised" color="primary" onClick={() => this.onSortByPrice(true)}>Ascending price</Button>
          <Button variant="raised" color="primary" onClick={() => this.onSortByPrice(false)}>Descending price</Button>
          <Button variant="raised" color="primary" onClick={() => this.onSortByRating()}>Rating from reviews</Button>
          <Button variant="raised" color="primary" onClick={() => this.onSortByRoomDimension()}>Room dimension</Button>
        </List>
      </div>
    );
  }
}

SortBar.propTypes = {
  classes: PropTypes.object.isRequired,
  onSort: PropTypes.func.isRequired,
};

export default withStyles(styles)(SortBar);