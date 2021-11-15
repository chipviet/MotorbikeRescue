import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Connection from './connection';
import ConnectionDetail from './connection-detail';
import ConnectionUpdate from './connection-update';
import ConnectionDeleteDialog from './connection-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ConnectionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ConnectionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ConnectionDetail} />
      <ErrorBoundaryRoute path={match.url} component={Connection} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ConnectionDeleteDialog} />
  </>
);

export default Routes;
