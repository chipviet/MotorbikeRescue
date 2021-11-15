import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RescueTeam from './rescue-team';
import RescueTeamDetail from './rescue-team-detail';
import RescueTeamUpdate from './rescue-team-update';
import RescueTeamDeleteDialog from './rescue-team-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RescueTeamUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RescueTeamUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RescueTeamDetail} />
      <ErrorBoundaryRoute path={match.url} component={RescueTeam} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RescueTeamDeleteDialog} />
  </>
);

export default Routes;
