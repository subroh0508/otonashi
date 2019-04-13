import React, { Component } from 'react';
import Button from '@material-ui/core/Button';
import logo from './logo.svg';
import SearchBox from './SearchBox';
import './App.css';

interface AppState {
  conditions: {
    contents: string[],
    idolName: string,
    additionalInfo: string,
  };
}

class App extends Component<{}, AppState> {
  constructor(props: {}) {
    super(props);

    this.state = {
      conditions: {
        contents: [],
        idolName: '',
        additionalInfo: '',
      },
    };

    this.handleOnChangeConditions = this.handleOnChangeConditions.bind(this);
  }

  handleOnChangeConditions(conditions: any) {
    console.log(conditions);
    this.setState({ conditions });
  }

  render() {
    const { conditions } = this.state;

    return (
      <div className="App">
        <SearchBox
          conditions={ conditions }
          onChange={ this.handleOnChangeConditions }
        />
      </div>
    );
  }
}

export default App;
