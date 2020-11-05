import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VideoFile from './video-file';
import VideoFileDetail from './video-file-detail';
import VideoFileUpdate from './video-file-update';
import VideoFileDeleteDialog from './video-file-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VideoFileUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VideoFileUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VideoFileDetail} />
      <ErrorBoundaryRoute path={match.url} component={VideoFile} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={VideoFileDeleteDialog} />
  </>
);

export default Routes;
