import React, {Component, FunctionComponent} from 'react';
import { Theme } from '@material-ui/core/styles/createMuiTheme';
import withStyles, { WithStyles, StyleRules } from "@material-ui/core/styles/withStyles";
import createStyles from '@material-ui/core/styles/createStyles';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import LinearProgress from '@material-ui/core/LinearProgress';
import Divider from '@material-ui/core/Divider';
import Chip from '@material-ui/core/Chip';
import GroupIcon from '@material-ui/icons/Group';
import StarIcon from '@material-ui/icons/Star';
import blueGray from '@material-ui/core/colors/blueGrey';
import ky from 'ky';
import compact from 'lodash/compact';
import isEmpty from 'lodash/isEmpty';

const api = ky.extend({ prefixUrl: 'http://localhost:8080' });

const searchResultStyle = ({ spacing }: Theme): StyleRules => createStyles({
  root: {
    padding: `${64 + 24}px 24px`,
    flex: '0 1 100%',
  },
  button: {
    margin: '24px 0',
  },
  resultSummary: {
    background: blueGray[50],
    paddingBottom: 48,
    '& #result-summary': {
      padding: `12px ${spacing(2)}px`,
    },
  },
  divider: {
    marginTop: 3,
  },
  card: {
    margin: spacing(2),
  },
});

interface CitySearchResultProps extends WithStyles<typeof searchResultStyle> {
  prefectureName: string;
  cityName: string;
}

interface CitySearchResultState {
  loading: boolean;
  results: {[key: string]: any}[];
}

class CitySearchResult extends Component<CitySearchResultProps, CitySearchResultState> {
  constructor(props: CitySearchResultProps) {
    super(props);

    this.state = {
      loading: false,
      results: [],
    };
  }

  async fetchResults() {
    const { prefectureName, cityName } = this.props;

    this.setState({ loading: true, results: [] });

    const response = await api.get(
      `dbpedia?prefecture_name=${prefectureName}&city_name=${cityName}`,
    );

    if (response.ok) {
      const results = JSON.parse(await response.text()) as {[key: string]: any}[];

      this.setState({ loading: false, results });
    } else {
      this.setState({ loading: false });
    }
  }

  render() {
    const { classes } = this.props;
    const { results, loading } = this.state;

    return (
      <div>
        <Button
          className={ classes.button }
          disabled={ loading }
          variant='contained'
          color='secondary'
          onClick={ this.fetchResults.bind(this) }>
          送信
        </Button>
        { loading ? (<LinearProgress/>) : (<Divider className={ classes.divider }/>) }
        <div className={ classes.resultSummary }>
          <div id='result-summary'>
            { `検索結果: ${results.length}件` }
          </div>

          {
            results.map((json, i) => (
              <Card key={ i } className={ classes.card }>
                <CityResultCardContent json={ json }/>
              </Card>
            ))
          }
        </div>
      </div>
    );
  }
}

const resultCardContentStyle = ({ spacing }: Theme): StyleRules => createStyles({
  chip: {
    padding: 2,
    margin: spacing(1),
  },
  colorBar: {
    height: 10,
    marginBottom: spacing(1),
  },
});

interface CityResultCardContentProps extends WithStyles<typeof resultCardContentStyle>{
  json: {[key: string]: any};
}

const CityResultCardContent = withStyles(resultCardContentStyle)((
  { classes, json }: CityResultCardContentProps
) => {
  return (
    <CardContent>
      <Typography variant='h5' component='h2'>
        { json['city_name'] }
      </Typography>
      <Typography color='textSecondary' gutterBottom>
        { json['prefecture_name'] }
      </Typography>
      { json['description'] }
    </CardContent>
  );
});

export default withStyles(searchResultStyle)(CitySearchResult);
