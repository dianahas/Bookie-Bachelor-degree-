import React from "react";
import PlacesAutocomplete from 'react-places-autocomplete'

class GoogleAutocomplete extends React.Component {

  constructor(props) {
    super(props);
    this.state = {address: ''};
  }

  render() {
    const {onSelect, defaultValue} = this.props;
    const renderFunc = (({getInputProps, suggestions, getSuggestionItemProps}) => (
      <div>
        <input
          style={{height: '40px', width: '100%', color: '#555'}}
          {...getInputProps({
            placeholder: 'Search Places ...',
            className: 'location-search-input',
            autoFocus: true
          })}
        />
        <div className="autocomplete-dropdown">
          {suggestions.map(suggestion => {
            const className = suggestion.active ? 'suggestion-item--active' : 'suggesion-item';
            const style = suggestion.active
              ? {backgroundColor: '#fafafa', cursor: 'pointer'}
              : {backgroundColor: '#ffffff', cursor: 'pointer'};
            return (
              <div {...getSuggestionItemProps(suggestion, {className, style})}>
                {suggestion.description}
              </div>
            );
          })}
        </div>
      </div>
    ));

    const myStyles = {
      root: {
        display: 'flex',
        width: '350%'
      },
      input: {width: '100%'},
      autocompleteContainer: {backgroundColor: 'pink'},
      autocompleteItem: {color: 'black'},
      autocompleteItemActive: {color: 'green'}
    };

    return (
      <PlacesAutocomplete
        value={this.state.address || defaultValue}
        onChange={value => this.setState({address: value})}
        onSelect={onSelect}
        styles={myStyles}
      >
        {renderFunc}
      </PlacesAutocomplete>
    );
  }
}

export default GoogleAutocomplete;
