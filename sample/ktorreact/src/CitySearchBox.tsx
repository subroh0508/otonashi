import React, { FunctionComponent } from 'react';
import { Theme } from '@material-ui/core/styles/createMuiTheme';
import withStyles, { WithStyles, StyleRules } from "@material-ui/core/styles/withStyles";
import createStyles from '@material-ui/core/styles/createStyles';
import TextField from '@material-ui/core/TextField';

const searchBoxStyle = (theme: Theme): StyleRules => createStyles({
  root: {
    '& > div': {
      paddingBottom: 16,
    },
  },
});

interface CitySearchBoxProps extends WithStyles<typeof searchBoxStyle> {
  conditions: {
    prefectureName: string,
    cityName: string,
  };
  onChange: (conditions: {
    prefectureName: string,
    cityName: string,
  }) => void;
}

const CitySearchBox: FunctionComponent<CitySearchBoxProps> = (
  { classes, onChange, conditions }: CitySearchBoxProps,
) => {
  const handleOnPrefectureName = (prefectureName: string) => {
    onChange({ ...conditions, prefectureName });
  };

  const handleOnCityName = (cityName: string) => {
    onChange({ ...conditions, cityName });
  };

  return (
    <div className={ classes.root }>
      <NameTextField
        label='都道府県名'
        name={ conditions.prefectureName }
        onChange={ handleOnPrefectureName }
      />
      <NameTextField
        label='市区町村名'
        name={ conditions.cityName }
        onChange={ handleOnCityName }
      />
    </div>
  );
};

const nameStyle = (theme: Theme): StyleRules => createStyles({
  textField: {
    paddingBottom: 16,
  },
});

interface NameProps extends WithStyles<typeof nameStyle> {
  label: string;
  name: string;
  onChange: (idolName: string) => void;
}

const NameTextField = withStyles(nameStyle)((
  { classes, label, name, onChange }: NameProps,
) => {
  const handleOnChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    onChange(event.target.value);
  };

  return (
    <div>
      <TextField
        className={ classes.textField }
        label={ label }
        value={ name }
        onChange={ handleOnChange }
        margin='normal'
        variant='outlined'
      />
    </div>
  );
});

export default withStyles(searchBoxStyle)(CitySearchBox);
