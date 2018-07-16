import { Switch } from 'react-router-dom';
import { connect } from 'react-redux';

const ConnectedSwitch = connect((state) => ({
  location: state.router.location,
}))(Switch);

export default ConnectedSwitch;
