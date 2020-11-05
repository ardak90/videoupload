import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VideoToStand from './video-to-stand';
import VideoToStandDetail from './video-to-stand-detail';
import VideoToStandUpdate from './video-to-stand-update';
import VideoToStandDeleteDialog from './video-to-stand-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VideoToStandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VideoToStandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VideoToStandDetail} />
      <ErrorBoundaryRoute path={match.url} component={VideoToStand} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={VideoToStandDeleteDialog} />
  </>
);

export default Routes;
