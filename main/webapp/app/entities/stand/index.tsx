import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Stand from './stand';
import StandDetail from './stand-detail';
import StandUpdate from './stand-update';
import StandDeleteDialog from './stand-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StandDetail} />
      <ErrorBoundaryRoute path={match.url} component={Stand} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={StandDeleteDialog} />
  </>
);

export default Routes;
