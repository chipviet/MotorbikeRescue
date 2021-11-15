import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './rescue-team.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RescueTeamDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const rescueTeamEntity = useAppSelector(state => state.rescueTeam.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rescueTeamDetailsHeading">
          <Translate contentKey="mrProjectApp.rescueTeam.detail.title">RescueTeam</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{rescueTeamEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="mrProjectApp.rescueTeam.name">Name</Translate>
            </span>
          </dt>
          <dd>{rescueTeamEntity.name}</dd>
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="mrProjectApp.rescueTeam.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{rescueTeamEntity.phoneNumber}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="mrProjectApp.rescueTeam.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{rescueTeamEntity.longitude}</dd>
          <dt>
            <span id="latitude">
              <Translate contentKey="mrProjectApp.rescueTeam.latitude">Latitude</Translate>
            </span>
          </dt>
          <dd>{rescueTeamEntity.latitude}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="mrProjectApp.rescueTeam.address">Address</Translate>
            </span>
          </dt>
          <dd>{rescueTeamEntity.address}</dd>
          <dt>
            <span id="createAt">
              <Translate contentKey="mrProjectApp.rescueTeam.createAt">Create At</Translate>
            </span>
          </dt>
          <dd>{rescueTeamEntity.createAt}</dd>
        </dl>
        <Button tag={Link} to="/rescue-team" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rescue-team/${rescueTeamEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RescueTeamDetail;
