import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './request.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RequestDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const requestEntity = useAppSelector(state => state.request.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="requestDetailsHeading">
          <Translate contentKey="mrProjectApp.request.detail.title">Request</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{requestEntity.id}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="mrProjectApp.request.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{requestEntity.longitude}</dd>
          <dt>
            <span id="latitude">
              <Translate contentKey="mrProjectApp.request.latitude">Latitude</Translate>
            </span>
          </dt>
          <dd>{requestEntity.latitude}</dd>
          <dt>
            <span id="createAt">
              <Translate contentKey="mrProjectApp.request.createAt">Create At</Translate>
            </span>
          </dt>
          <dd>{requestEntity.createAt}</dd>
          <dt>
            <span id="message">
              <Translate contentKey="mrProjectApp.request.message">Message</Translate>
            </span>
          </dt>
          <dd>{requestEntity.message}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="mrProjectApp.request.status">Status</Translate>
            </span>
          </dt>
          <dd>{requestEntity.status}</dd>
          <dt>
            <Translate contentKey="mrProjectApp.request.user">User</Translate>
          </dt>
          <dd>{requestEntity.user ? requestEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/request" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/request/${requestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RequestDetail;
