import React, {Component, FunctionComponent} from 'react';
import { Theme } from '@material-ui/core/styles/createMuiTheme';
import withStyles, { WithStyles, StyleRules } from "@material-ui/core/styles/withStyles";
import createStyles from '@material-ui/core/styles/createStyles';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import ky from 'ky';

const api = ky.extend({ prefixUrl: 'http://localhost:8080' });

const searchResultStyle = ({ spacing }: Theme): StyleRules => createStyles({
  root: {
    padding: `${64 + 24}px 24px`,
    flex: '0 1 100%',
  },
  button: {
    margin: '24px 0',
  },
  card: {
    margin: spacing(2),
  },
});

interface SearchResultProps extends WithStyles<typeof searchResultStyle> {
  contents: string[];
  idolName: string;
  additionalInfo: string;
}

interface SearchResultState {
  results: {[key: string]: any}[];
}

class SearchResult extends Component<SearchResultProps, SearchResultState> {
  constructor(props: SearchResultProps) {
    super(props);

    this.state = {
      results: [],
    };
  }

  async fetchResults() {
    const { contents, idolName, additionalInfo } = this.props;

    this.setState({ results: [] });

    const response = await api.get(
      `imasparql?contents[]=${contents.map((c, _) => `${c}`).join('&contents[]=')}&idol_name=${idolName}&additional=${additionalInfo}`,
    );

    console.log(response);
    if (response.ok) {
      const results = JSON.parse(await response.text()) as {[key: string]: any}[];

      this.setState({ results });
    }
  }

  render() {
    const { classes } = this.props;
    const { results } = this.state;

    return (
      <div>
        <Button
          className={ classes.button }
          variant='contained'
          color='secondary'
          onClick={ this.fetchResults.bind(this) }>
          送信
        </Button>
        <Divider/>
        <div>
          { `検索結果: ${results.length}件` }
        </div>

        {
          results.map((json, i) => (
            <Card key={ i } className={ classes.card }>
              <CardContent>
                <Typography variant='h5' component='h2'>
                  { json['name'] }
                </Typography>
                <Typography color='textSecondary' gutterBottom>
                  { json['id'] }
                </Typography>
                <Typography color='textSecondary'>
                  { `${json['age_str']}歳 / ${json['blood_type']}型 / ${json['three_size']}` }
                </Typography>
              </CardContent>
            </Card>
          ))
        }
      </div>
    );
  }
}

export default withStyles(searchResultStyle)(SearchResult);
