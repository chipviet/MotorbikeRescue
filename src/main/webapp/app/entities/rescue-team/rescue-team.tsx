import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './rescue-team.reducer';
import { IRescueTeam } from 'app/shared/model/rescue-team.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RescueTeam = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const rescueTeamList = useAppSelector(state => state.rescueTeam.entities);
  const loading = useAppSelector(state => state.rescueTeam.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="rescue-team-heading" data-cy="RescueTeamHeading">
        <Translate contentKey="mrProjectApp.rescueTeam.home.title">Rescue Teams</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="mrProjectApp.rescueTeam.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="mrProjectApp.rescueTeam.home.createLabel">Create new Rescue Team</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {rescueTeamList && rescueTeamList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="mrProjectApp.rescueTeam.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="mrProjectApp.rescueTeam.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="mrProjectApp.rescueTeam.phoneNumber">Phone Number</Translate>
                </th>
                <th>
                  <Translate contentKey="mrProjectApp.rescueTeam.longitude">Longitude</Translate>
                </th>
                <th>
                  <Translate contentKey="mrProjectApp.rescueTeam.latitude">Latitude</Translate>
                </th>
                <th>
                  <Translate contentKey="mrProjectApp.rescueTeam.address">Address</Translate>
                </th>
                <th>
                  <Translate contentKey="mrProjectApp.rescueTeam.createAt">Create At</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {rescueTeamList.map((rescueTeam, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${rescueTeam.id}`} color="link" size="sm">
                      {rescueTeam.id}
                    </Button>
                  </td>
                  <td>{rescueTeam.name}</td>
                  <td>{rescueTeam.phoneNumber}</td>
                  <td>{rescueTeam.longitude}</td>
                  <td>{rescueTeam.latitude}</td>
                  <td>{rescueTeam.address}</td>
                  <td>{rescueTeam.createAt}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${rescueTeam.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${rescueTeam.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${rescueTeam.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="mrProjectApp.rescueTeam.home.notFound">No Rescue Teams found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default RescueTeam;
