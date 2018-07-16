import React from "react";
import { withStyles, SnackbarContent as Snack, IconButton } from "material-ui";
import { Close } from "@material-ui/icons";
// nodejs library to set properties for components
import PropTypes from "prop-types";

import snackbarContentStyle from "assets/jss/material-kit-react/components/snackbarContentStyle.jsx";
import {notificationActions} from "../../actions";
import {connect} from "react-redux";

class SnackbarContent extends React.Component {
  constructor(props) {
    super(props);
    this.closeAlert = this.closeAlert.bind(this);
    const { classes, message, color, close, icon } = props;
    var action = [];
    if (close !== undefined) {
      action = [
        <IconButton
          className={classes.iconButton}
          key="close"
          aria-label="Close"
          color="inherit"
          onClick={this.closeAlert}
        >
          <Close className={classes.close} />
        </IconButton>
      ];
    }
    this.state = {
      alert: (
        <Snack
          message={
            <div>
              {icon !== undefined ? (
                <props.icon className={classes.icon} />
              ) : null}
              {message}
              {close !== undefined ? action : null}
            </div>
          }
          classes={{
            root: classes.root + " " + classes[color],
            message: classes.message + " " + classes.container
          }}
          // action={action}
        />
      )
    };
  }
  closeAlert() {
    this.setState({alert: null});
    this.props.onNotificationClose();
  }
  render() {
    return this.state.alert;
  }
}

SnackbarContent.propTypes = {
  classes: PropTypes.object.isRequired,
  message: PropTypes.node.isRequired,
  color: PropTypes.oneOf(["info", "success", "warning", "danger", "primary"]),
  close: PropTypes.bool,
  icon: PropTypes.func
};

const mapDispatchToProps = (dispatch) => ({
  onNotificationClose: () => dispatch(notificationActions.onNotificationClose()),
});

const withConnect = connect(null, mapDispatchToProps)(SnackbarContent);

export default withStyles(snackbarContentStyle)(withConnect);