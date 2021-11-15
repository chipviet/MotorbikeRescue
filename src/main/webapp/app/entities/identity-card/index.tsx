import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import IdentityCard from './identity-card';
import IdentityCardDetail from './identity-card-detail';
import IdentityCardUpdate from './identity-card-update';
import IdentityCardDeleteDialog from './identity-card-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={IdentityCardUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={IdentityCardUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={IdentityCardDetail} />
      <ErrorBoundaryRoute path={match.url} component={IdentityCard} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={IdentityCardDeleteDialog} />
  </>
);

export default Routes;
