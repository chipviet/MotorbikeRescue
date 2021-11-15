import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './device.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DeviceDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const deviceEntity = useAppSelector(state => state.device.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="deviceDetailsHeading">
          <Translate contentKey="mrProjectApp.device.detail.title">Device</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{deviceEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="mrProjectApp.device.name">Name</Translate>
            </span>
          </dt>
          <dd>{deviceEntity.name}</dd>
          <dt>
            <span id="deviceUuid">
              <Translate contentKey="mrProjectApp.device.deviceUuid">Device Uuid</Translate>
            </span>
          </dt>
          <dd>{deviceEntity.deviceUuid}</dd>
          <dt>
            <span id="platform">
              <Translate contentKey="mrProjectApp.device.platform">Platform</Translate>
            </span>
          </dt>
          <dd>{deviceEntity.platform}</dd>
          <dt>
            <span id="version">
              <Translate contentKey="mrProjectApp.device.version">Version</Translate>
            </span>
          </dt>
          <dd>{deviceEntity.version}</dd>
          <dt>
            <Translate contentKey="mrProjectApp.device.user">User</Translate>
          </dt>
          <dd>{deviceEntity.user ? deviceEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/device" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/device/${deviceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DeviceDetail;
