/* eslint-disable */
import React from "react";
// material-ui components
import List from "material-ui/List";
import ListItem from "material-ui/List/ListItem";
import Tooltip from "material-ui/Tooltip";
// core components
import Button from "components/CustomButtons/Button.jsx";
import IconButton from "components/CustomButtons/IconButton.jsx";
import headerLinksStyle from "../../assets/jss/material-kit-react/components/headerLinksStyle";
import {connect} from "react-redux";
import {withStyles} from "material-ui";
import {reservationActions, reviewActions, userActions} from "../../actions";
import CustomDropdown from 'components/CustomDropdown/CustomDropdown.jsx';

class HeaderLinks extends React.Component {

  render() {
    const {classes, userInfo} = this.props;

    const handleProfileClick = () => {
      this.props.retrieveReservations(userInfo.id);
      this.props.getReviews({userId: userInfo.id});
    };

    const onLogout = () => {
      this.props.logout();
    };

    const onLogin = () => {
      let path = location.pathname;
      this.props.saveUrl(path);
    };

    return (
      <List className={classes.list}>

        <ListItem className={classes.listItem}>
          <Button
            href="/search"
            color="transparent"
            className={classes.navLink}
          >
            Home
          </Button>
        </ListItem>

        {!!(userInfo) ?
          <ListItem className={classes.listItem}>
            <Button
              color="transparent"
              className={classes.navLink}
              onClick={handleProfileClick}
            >
              Profile
            </Button>
          </ListItem>
          :
          <ListItem className={classes.listItem}>
            <Button
              href="/register"
              color="transparent"
              className={classes.navLink}
            >
              Register
            </Button>
          </ListItem>
        }

        <ListItem className={classes.listItem}>
          <CustomDropdown
            dropup
            buttonText="Language"
            buttonProps={{
              round: true,
              color: "info",
              className: "newButton"
            }}
            dropdownList={[
              "English",
              "French",
              "Romanian"
            ]}
          />
        </ListItem>

        <ListItem className={classes.listItem}>
          <CustomDropdown
            dropup
            buttonText="Currency"
            buttonProps={{
              round: true,
              color: "info",
              className: "newButton"
            }}
            dropdownList={[
              "USD",
              "FRANC",
              "RON"
            ]}
          />
        </ListItem>

        {!!(userInfo) ?
          <ListItem className={classes.listItem}>
            <Button
              color="transparent"
              className={classes.navLink}
              onClick={onLogout}
            >
              Log out
            </Button>
          </ListItem>
          :
          <ListItem className={classes.listItem}>
            <Button
              href="/login"
              color="transparent"
              className={classes.navLink}
              onClick={onLogin}
            >
              Login
            </Button>
          </ListItem>
        }

        <ListItem className={classes.listItem}>
          <Tooltip
            id="instagram-facebook"
            title="Follow us on facebook"
            placement={window.innerWidth > 959 ? "top" : "left"}
            classes={{tooltip: classes.tooltip}}
          >
            <IconButton
              color="transparent"
              href="https://www.facebook.com/something_here"
              target="_blank"
              className={classes.navLink + " " + classes.socialIconsButton}
            >
              <i className={classes.socialIcons + " fab fa-facebook"}/>
            </IconButton>
          </Tooltip>
        </ListItem>

        <ListItem className={classes.listItem}>
          <Tooltip
            id="instagram-tooltip"
            title="Follow us on instagram"
            placement={window.innerWidth > 959 ? "top" : "left"}
            classes={{tooltip: classes.tooltip}}
          >
            <IconButton
              color="transparent"
              href="https://www.instagram.com/something_here"
              target="_blank"
              className={classes.navLink + " " + classes.socialIconsButton}
            >
              <i className={classes.socialIcons + " fab fa-instagram"}/>
            </IconButton>
          </Tooltip>
        </ListItem>
      </List>
    );
  }
}

const mapStateToProps = (state) => ({
  userInfo: state.users.userInfo,
  isLoggedIn: state.users.isLoggedIn,
});

const mapDispatchToProps = (dispatch) => ({
  retrieveReservations: (userId) => dispatch(reservationActions.fetchReservationsByUser(userId)),
  getReviews: (values) => dispatch(reviewActions.getAllReviewsByUser(values)),
  saveUrl: (values) => dispatch(userActions.onSavePath(values)),
  logout: () => dispatch(userActions.onLogout()),
});



let app = withStyles(headerLinksStyle)(HeaderLinks);
export default connect(mapStateToProps, mapDispatchToProps)(app);
