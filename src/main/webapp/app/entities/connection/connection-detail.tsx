import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './connection.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ConnectionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const connectionEntity = useAppSelector(state => state.connection.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="connectionDetailsHeading">
          <Translate contentKey="mrProjectApp.connection.detail.title">Connection</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{connectionEntity.id}</dd>
          <dt>
            <span id="createAt">
              <Translate contentKey="mrProjectApp.connection.createAt">Create At</Translate>
            </span>
          </dt>
          <dd>{connectionEntity.createAt}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="mrProjectApp.connection.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{connectionEntity.longitude}</dd>
          <dt>
            <span id="latitude">
              <Translate contentKey="mrProjectApp.connection.latitude">Latitude</Translate>
            </span>
          </dt>
          <dd>{connectionEntity.latitude}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="mrProjectApp.connection.status">Status</Translate>
            </span>
          </dt>
          <dd>{connectionEntity.status}</dd>
          <dt>
            <Translate contentKey="mrProjectApp.connection.user">User</Translate>
          </dt>
          <dd>{connectionEntity.user ? connectionEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="mrProjectApp.connection.request">Request</Translate>
          </dt>
          <dd>{connectionEntity.request ? connectionEntity.request.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/connection" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/connection/${connectionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ConnectionDetail;
