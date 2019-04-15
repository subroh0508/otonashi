import React, {Component, FunctionComponent} from 'react';
import { Theme } from '@material-ui/core/styles/createMuiTheme';
import withStyles, { WithStyles, StyleRules } from "@material-ui/core/styles/withStyles";
import createStyles from '@material-ui/core/styles/createStyles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import Drawer from '@material-ui/core/Drawer';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Divider from '@material-ui/core/Divider';
import MenuIcon from '@material-ui/icons/Menu';

import './App.css';

const appFrameStyle = ({ spacing }: Theme): StyleRules => createStyles({
  drawerHeader: {
    padding: spacing(2),
  },
  list: {
    width: 250,
  },
  title: {
    marginLeft: spacing(2),
    flex: '0 1 auto',
  },
});

interface AppFrameState {
  title: string;
  open: boolean;
}

interface AppFrameProps extends WithStyles<typeof appFrameStyle>{
  drawerItems: string[];
  onDrawerItemSelect: (text: string, index: number) => void;
}

class AppFrame extends Component<AppFrameProps, AppFrameState> {
  constructor(props: AppFrameProps) {
    super(props);

    this.state = {
      title: props.drawerItems[0],
      open: false,
    };
  }

  handleDrawerOpen = () => {
    this.setState({ open: true });
  };

  handleDrawerClose = () => {
    this.setState({ open: false });
  };

  handleItemSelect = (text: string, index: number) => {
    const { onDrawerItemSelect } = this.props;

    onDrawerItemSelect(text, index);
    this.setState({ open: false, title: text });
  };

  render() {
    const { classes, drawerItems } = this.props;
    const { title, open } = this.state;

    return (
      <div>
        <AppBar>
          <Toolbar>
            <IconButton
              edge='start'
              color='inherit'
              onClick={ this.handleDrawerOpen }>
              <MenuIcon/>
            </IconButton>
            <Typography className={ classes.title } variant='h6' color='inherit'>
              { title }
            </Typography>
          </Toolbar>
        </AppBar>
        <Drawer
          open={ open }
          onClose={ this.handleDrawerClose }>
          <div
            className={ classes.list }
            role='button'
            onClick={ this.handleDrawerClose }
            onKeyDown={ this.handleDrawerClose }>
            <Typography className={ classes.drawerHeader } variant='h6' color='inherit'>
              エンドポイント
            </Typography>
            <Divider/>
            <DrawerList
              list={ drawerItems }
              onSelect={ this.handleItemSelect }
            />
          </div>
        </Drawer>
        { this.props.children }
      </div>
    );
  }
}

interface DrawerListProps {
  list: string[];
  onSelect: (text: string, index: number) => void;
}

const DrawerList : FunctionComponent<DrawerListProps> = (
  { list, onSelect }: DrawerListProps,
) => {
  return (
    <List>
      {
        list.map((listText, index) => (
          <ListItem button key={ index }
            onClick={ onSelect.bind(null, listText, index) }>
            <ListItemText primary={ listText }/>
          </ListItem>
        ))
      }
    </List>
  );
};

export default withStyles(appFrameStyle)(AppFrame);
