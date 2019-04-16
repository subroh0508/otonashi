import React, { Component } from 'react';
import { Theme } from '@material-ui/core/styles/createMuiTheme';
import withStyles, { WithStyles, StyleRules } from "@material-ui/core/styles/withStyles";
import createStyles from '@material-ui/core/styles/createStyles';
import AppFrame from './AppFrame';
import SearchBox from './SearchBox';
import SearchResult from './SearchResult';

const endpointList = ['im@sparql'];

const appStyle = ({ spacing }: Theme): StyleRules => createStyles({
  root: {
    padding: `${64 + 24}px 24px`,
    flex: '0 1 100%',
  },
  button: {
    margin: '24px 0',
  },
});

interface AppProps extends WithStyles<typeof appStyle> {}

interface AppState {
  conditions: {
    contents: string[],
    idolName: string,
    additionalInfo: string,
  };
  endpoint: string;
}

class App extends Component<AppProps, AppState> {
  constructor(props: AppProps) {
    super(props);

    this.state = {
      conditions: {
        contents: [],
        idolName: '',
        additionalInfo: '',
      },
      endpoint: endpointList[0],
    };
  }

  handleChangeConditions = (conditions: any) => {
    this.setState({ conditions });
  };

  handleDrawerItemSelect = (text: string, index: number) => {
    this.setState({ endpoint: text });
  };

  render() {
    const { classes } = this.props;
    const { conditions } = this.state;

    return (
      <AppFrame
        drawerItems={ endpointList }
        onDrawerItemSelect={ this.handleDrawerItemSelect }>
        <div className={ classes.root }>
          <SearchBox
            conditions={ conditions }
            onChange={ this.handleChangeConditions }
          />
          <SearchResult { ...conditions }/>
        </div>
      </AppFrame>
    );
  }
}

export default withStyles(appStyle)(App);
