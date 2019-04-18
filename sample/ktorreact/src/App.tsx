import React, {Component, FunctionComponent} from 'react';
import { Theme } from '@material-ui/core/styles/createMuiTheme';
import withStyles, { WithStyles, StyleRules } from "@material-ui/core/styles/withStyles";
import createStyles from '@material-ui/core/styles/createStyles';
import AppFrame from './AppFrame';
import ImasSearchBox from './ImasSearchBox';
import ImasSearchResult from './ImasSearchResult';
import CitySearchBox from './CitySearchBox';
import CitySearchResult from './CitySearchResult';
import pick from 'lodash/pick';

const endpointList = ['im@sparql', 'dbpedia'];

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
  'im@sparql': { [key: string]: any };
  dbpedia: { [key: string]: any },
  endpoint: string;
}

class App extends Component<AppProps, AppState> {
  constructor(props: AppProps) {
    super(props);

    this.state = {
      'im@sparql': {
        contents: [],
        idolName: '',
        additionalInfo: '',
      },
      dbpedia: {
        prefectureName: '',
        cityName: '',
      },
      endpoint: endpointList[0],
    };
  }

  handleChangeConditions = (conditions: {[key: string]: any}) => {
    const { endpoint } = this.state;

    switch (endpoint) {
      case 'im@sparql':
        this.setState({ 'im@sparql': conditions });
        break;
      case 'dbpedia':
        this.setState({ dbpedia: conditions });
        break;
    }
  };

  handleDrawerItemSelect = (text: string, index: number) => {
    this.setState({ endpoint: text });
  };

  render() {
    const { classes } = this.props;
    const { endpoint } = this.state;

    return (
      <AppFrame
        drawerItems={ endpointList }
        onDrawerItemSelect={ this.handleDrawerItemSelect }>
        <div className={ classes.root }>
          <SearchContainer
            endpoint={ endpoint }
            conditions={ pick(this.state, endpointList) }
            onChangeConditions={ this.handleChangeConditions.bind(this) }/>
        </div>
      </AppFrame>
    );
  }
}

interface SearchContainerProps {
  endpoint: string;
  conditions: { [key: string]: any };
  onChangeConditions: (conditions: {[key: string]: any}) => void;
}

const SearchContainer: FunctionComponent<SearchContainerProps> = (
  { endpoint, conditions, onChangeConditions }: SearchContainerProps
) => {
  switch (endpoint) {
    case 'im@sparql':
      return (
        <div>
          <ImasSearchBox
            conditions={ conditions['im@sparql'] }
            onChange={ onChangeConditions }
          />
          <ImasSearchResult { ...conditions['im@sparql'] }/>
        </div>
      );
    case 'dbpedia':
      return (
        <div>
          <CitySearchBox
            conditions={ conditions.dbpedia }
            onChange={ onChangeConditions }
          />
          <CitySearchResult { ...conditions.dbpedia }/>
        </div>
      );
    default:
      return null;
  }
};

export default withStyles(appStyle)(App);
