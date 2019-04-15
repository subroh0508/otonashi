import React, { FunctionComponent } from 'react';
import { Theme } from '@material-ui/core/styles/createMuiTheme';
import withStyles, { WithStyles, StyleRules } from "@material-ui/core/styles/withStyles";
import createStyles from '@material-ui/core/styles/createStyles';
import FormGroup from '@material-ui/core/FormGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import CheckBox from '@material-ui/core/Checkbox';
import TextField from '@material-ui/core/TextField';
import RadioGroup from '@material-ui/core/RadioGroup';
import Radio from '@material-ui/core/Radio';
import Typography from '@material-ui/core/Typography';
import uniq from 'lodash/uniq';
import reject from 'lodash/reject';

const searchBoxStyle = (theme: Theme): StyleRules => createStyles({
  root: {
    '& > div': {
      paddingBottom: 16,
    },
  },
});

interface SearchBoxProps extends WithStyles<typeof searchBoxStyle> {
  conditions: {
    contents: string[],
    idolName: string,
    additionalInfo: string,
  };
  onChange: (conditions: {
    contents: string[],
    idolName: string,
    additionalInfo: string,
  }) => void;
}

const SearchBox: FunctionComponent<SearchBoxProps> = (
  { classes, onChange, conditions }: SearchBoxProps,
) => {
  const handleOnContents = (name: string, checked: boolean) => {
    let contents = [];
    if (checked) {
      contents = uniq([...conditions.contents, name]);
    } else {
      contents = reject(conditions.contents, (s) => s === name);
    }

    onChange({ ...conditions, contents });
  };

  const handleOnIdolName = (idolName: string) => {
    onChange({ ...conditions, idolName });
  };

  const handleOnAdditionalInfo = (additionalInfo: string) => {
    onChange({ ...conditions, additionalInfo });
  };

  return (
    <div className={ classes.root }>
      <ContentsCheckGroup
        contents={ conditions.contents }
        onChange={ handleOnContents }
      />
      <IdonNameTextField
        idolName={ conditions.idolName }
        onChange={ handleOnIdolName }
      />
      <AdditionalInfoRadioGroup
        additionalInfo={ conditions.additionalInfo }
        onChange={ handleOnAdditionalInfo }
      />
    </div>
  );
};

interface ContentsCheckGroupProps {
  contents: Array<string>;
  onChange: (name: string, checked: boolean) => void;
}

const ContentsCheckGroup: FunctionComponent<ContentsCheckGroupProps> = (
  { contents, onChange }: ContentsCheckGroupProps,
) => {
  const handleChange = (name: string) => (event: React.ChangeEvent<HTMLInputElement>) => {
    onChange(name, event.target.checked);
  };

  return (
    <div>
      <Typography variant='h5'>所属コンテンツ</Typography>
      <FormGroup row>
        <FormControlLabel
          control={
            <CheckBox
              checked={ contents.includes('765AS') }
              onChange={ handleChange('765AS') }
              value='765AS'
            />
          }
          label='765PRO ALLSTARS'
        />
        <FormControlLabel
          control={
            <CheckBox
              checked={ contents.includes('MillionStars') }
              onChange={ handleChange('MillionStars') }
              value='MillionStars'
            />
          }
          label='MILLIONSTARS'
        />
        <FormControlLabel
          control={
            <CheckBox
              checked={ contents.includes('CinderellaGirls') }
              onChange={ handleChange('CinderellaGirls') }
              value='CinderellaGirls'
            />
          }
          label='CINDERELLA GIRLS'
        />
        <FormControlLabel
          control={
            <CheckBox
              checked={ contents.includes('283Pro') }
              onChange={ handleChange('283Pro') }
              value='283Pro'
            />
          }
          label='SHINY COLORS'
        />
        <FormControlLabel
          control={
            <CheckBox
              checked={ contents.includes('315ProIdols') }
              onChange={ handleChange('315ProIdols') }
              value='315ProIdols'
            />
          }
          label='315 STARS'
        />
        <FormControlLabel
          control={
            <CheckBox
              checked={ contents.includes('others') }
              onChange={ handleChange('others') }
              value='others'
            />
          }
          label='OTHERS'
        />
        <FormControlLabel
          control={
            <CheckBox
              checked={ contents.includes('staff') }
              onChange={ handleChange('staff') }
              value='staff'
            />
          }
          label='STAFF'
        />
      </FormGroup>
    </div>
  );
};

const idolNameStyle = (theme: Theme): StyleRules => createStyles({
  textField: {
    paddingBottom: 16,
  },
});

interface IdolNameProps extends WithStyles<typeof idolNameStyle> {
  idolName: string;
  onChange: (idolName: string) => void;
}

const IdonNameTextField = withStyles(idolNameStyle)((
  { classes, idolName, onChange }: IdolNameProps,
) => {
  const handleOnChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    onChange(event.target.value);
  };

  return (
    <div>
      <TextField
        className={ classes.textField }
        label='アイドル名'
        value={ idolName }
        onChange={ handleOnChange }
        margin='normal'
        variant='outlined'
      />
    </div>
  );
});

interface AdditionalInfoProps {
  additionalInfo: string;
  onChange: (key: string) => void;
}

const AdditionalInfoRadioGroup: FunctionComponent<AdditionalInfoProps> = (
  { additionalInfo, onChange }: AdditionalInfoProps,
) => {
  const handleOnChange = (_: React.ChangeEvent<{}>, value: string) => {
    onChange(value);
  };

  return (
    <div>
      <Typography variant='h5'>追加情報</Typography>
      <RadioGroup
        name='additionalInfo'
        value={ additionalInfo }
        onChange={ handleOnChange }
      >
        <FormControlLabel value='clothes' control={ <Radio/> } label='衣装'/>
        <FormControlLabel value='units' control={ <Radio/> } label='ユニット'/>
      </RadioGroup>
    </div>
  );
};

export default withStyles(searchBoxStyle)(SearchBox);
