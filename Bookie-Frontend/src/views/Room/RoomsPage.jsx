import React from "react";
// core components
import Header from "components/Header/Header.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import HeaderLinks from "components/Header/HeaderLinks.jsx";
import Parallax from "components/Parallax/Parallax.jsx";
import landingPageStyle from "assets/jss/material-kit-react/views/landingPage.jsx";
import classNames from "classnames";
// card components
import {connect} from "react-redux";
import Button from "../../components/CustomButtons/Button";
import {goBack, push} from "react-router-redux";
import GridContainer from "../../components/Grid/GridContainer";
import withStyles from "material-ui/styles/withStyles";
import PropTypes from 'prop-types';
import RoomCard from "./RoomCard/index"
import Footer from "../../components/Footer/Footer";
import NavPills from "../../components/NavPills/NavPills";
import CardBody from "../../components/Card/CardBody";
import Card from "../../components/Card/Card";
import ReviewCard from "../Review/ReviewCard";
import {FIVE_STARS, FOUR_STARS, ONE_STAR, THREE_STARS, TWO_STARS} from "../../constants/ReviewDisplayingContants";

class HotelDetails extends React.Component {
  componentDidMount() {
    const {roomRatesSuccessful, selectedHotel} = this.props;

    if (roomRatesSuccessful === null || selectedHotel === null) {
      this.props.goToMain();
    }
  }

  onBook = (bookingCode) => {
    this.props.handleBook(bookingCode);
  };

  onBack = () => {
    this.props.onGoBack();
  };

  render() {
    const {classes, selectedHotel, userInfo, rooms, amenityList, reviewList, ...rest} = this.props;

    return <div>
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
      {selectedHotel === null ? this.props.goToMain() :
        <div>
          <Parallax small filter image={require("assets/img/profile-bg.jpg")}>
            <div className={classes.container}>
              <GridContainer>
                <GridItem xs={12} sm={12} md={12}>
                  <h2 className={classes.title}>{selectedHotel.propertyName}</h2>
                </GridItem>
              </GridContainer>
            </div>
          </Parallax>

          < div className={classNames(classes.main, classes.mainRaised)}>
            <div>

              <GridContainer justify="center">
                <GridItem xs={11} sm={11} md={10} lg={8} className={classes.navWrapper}>

                  <Card>
                    <CardBody>
                      <GridContainer>
                        {/*<GridItem className="facilities" xs={11} sm={11} md={11} lg={6}>*/}
                        {/*<h4 style={{fontWeight: "bold"}}> Facilities </h4>*/}

                        {/*<NavPills*/}
                        {/*color="primary"*/}
                        {/*tabs={[*/}
                        {/*{*/}
                        {/*tabButton: "Room",*/}
                        {/*tabContent: (*/}
                        {/*<Quote*/}
                        {/*text="Item 1, Item 2, Item 3, Item 4"*/}
                        {/*/>*/}
                        {/*)*/}
                        {/*},*/}
                        {/*{*/}
                        {/*tabButton: "Ceva",*/}
                        {/*tabContent: (*/}
                        {/*<Quote*/}
                        {/*text="Item 1, Item 2, Item 3, Item 4"*/}
                        {/*/>*/}
                        {/*)*/}
                        {/*},*/}
                        {/*{*/}
                        {/*tabButton: "Altceva",*/}
                        {/*tabContent: (*/}
                        {/*<Quote*/}
                        {/*text="Item 1, Item 2, Item 3, Item 4"*/}
                        {/*/>*/}
                        {/*)*/}
                        {/*}*/}

                        {/*]}*/}
                        {/*/>*/}

                        {/*</GridItem>*/}

                        <GridItem className="reviews" xs={12} sm={12} md={12} lg={12} style={{textAlign: 'center'}}>
                          <h4 style={{fontWeight: "bold", textAlign: "center"}}> Reviews </h4>

                          <NavPills
                            color="primary"
                            tabs={[
                              {
                                tabButton: <i class="material-icons test">star star star star star</i>,
                                tabContent: (
                                  <div style={{height: "100px"}}>
                                    {(typeof(reviewList[FIVE_STARS]) !== 'undefined' && reviewList[FIVE_STARS].length > 0) ?
                                      reviewList[FIVE_STARS].map((review, key) => (
                                        <ReviewCard review={review} key={key}/>
                                      ))
                                      : <h3> There are no reviews for now </h3>}
                                  </div>
                                )
                              },
                              {
                                tabButton: <i class="material-icons">star star star star</i>,
                                tabContent: (
                                  <div style={{height: "100px"}}>
                                    {(typeof(reviewList[FOUR_STARS]) !== 'undefined' && reviewList[FOUR_STARS].length > 0) ?
                                      reviewList[FOUR_STARS].map((review, key) => (
                                        <ReviewCard review={review} key={key}/>
                                      ))
                                      : <h3> There are no reviews for now </h3>}
                                  </div>
                                )
                              },
                              {
                                tabButton: <i class="material-icons">star star star</i>,
                                tabContent: (
                                  <div style={{height: "100px"}}>
                                    {(typeof(reviewList[THREE_STARS]) !== 'undefined' && reviewList[THREE_STARS].length > 0) ?
                                      reviewList[THREE_STARS].map((review, key) => (
                                        <ReviewCard review={review} key={key}/>
                                      ))
                                      : <h3> There are no reviews for now </h3>}
                                  </div>
                                )
                              },
                              {
                                tabButton: <i class="material-icons">star star</i>,
                                tabContent: (
                                  <div style={{height: "100px"}}>
                                    {(typeof(reviewList[TWO_STARS]) !== 'undefined' && reviewList[TWO_STARS].length > 0) ?
                                      reviewList[TWO_STARS].map((review, key) => (
                                        <ReviewCard review={review} key={key}/>
                                      ))
                                      : <h3> There are no reviews for now </h3>}
                                  </div>
                                )
                              },
                              {
                                tabButton: <i class="material-icons">star</i>,
                                tabContent: (
                                  <div style={{height: "100px"}}>
                                    {(typeof(reviewList[ONE_STAR]) !== 'undefined' && reviewList[ONE_STAR].length > 0) ?
                                      reviewList[ONE_STAR].map((review, key) => (
                                        <ReviewCard review={review} key={key}/>
                                      ))
                                      : <h3> There are no reviews for now </h3>}
                                  </div>
                                )
                              }

                            ]}
                          />

                        </GridItem>
                      </GridContainer>
                    </CardBody>
                  </Card>

                  <GridContainer>
                    {rooms.length > 0 ?
                      rooms.map((room, key) =>
                        <RoomCard key={key} room={room} isLoggedIn={!!(userInfo)} onBook={this.onBook} {...this.props}/>
                      )
                      : <h3 style={{float: 'center'}}>No rooms were found</h3>
                    }
                  </GridContainer>
                </GridItem>

                <GridItem xs={12} sm={12} md={10} lg={8} style={{padding: "40px"}}>
                  <Button style={{float: 'right'}} onClick={(event) => this.onBack()}> Back </Button>
                </GridItem>
              </GridContainer>

            </div>
          </div>

          <Footer/>
        </div>
      }
    </div>;
  }
}

HotelDetails.propTypes = {
  selectedHotel: PropTypes.object,
  rooms: PropTypes.array,
  amenityList: PropTypes.array,
  userInfo: PropTypes.object,
  handleBook: PropTypes.func,
  goToMain: PropTypes.func,
  roomRatesSuccessful: PropTypes.any
};

const mapStateToProps = (state) => ({
  selectedHotel: state.hotels.selectedHotel,
  roomRatesSuccessful: state.hotels.roomRatesSuccessful,
  rooms: state.hotels.rooms,
  amenityList: state.hotels.amenityList,
  reviewList: state.hotels.reviewList,
  userInfo: state.users.userInfo,
});

const mapDispatchToProps = (dispatch) => ({
  handleBook: (bookingCode) => dispatch(push(`/book/${bookingCode}`)),
  goToMain: () => dispatch(push(`/`)),
  onGoBack: () => dispatch(goBack()),
});

const withConnect = connect(mapStateToProps, mapDispatchToProps)(HotelDetails);

export default withStyles(landingPageStyle)(withConnect);