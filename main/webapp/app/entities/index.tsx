import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Stand from './stand';
import VideoFile from './video-file';
import VideoToStand from './video-to-stand';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/stand`} component={Stand} />
      <ErrorBoundaryRoute path={`${match.url}/video-file`} component={VideoFile} />
      <ErrorBoundaryRoute path={`${match.url}/video-to-stand`} component={VideoToStand} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
