import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IRequest } from 'app/shared/model/request.model';
import { getEntities as getRequests } from 'app/entities/request/request.reducer';
import { getEntity, updateEntity, createEntity, reset } from './connection.reducer';
import { IConnection } from 'app/shared/model/connection.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { ConnectionStatus } from 'app/shared/model/enumerations/connection-status.model';

export const ConnectionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const requests = useAppSelector(state => state.request.entities);
  const connectionEntity = useAppSelector(state => state.connection.entity);
  const loading = useAppSelector(state => state.connection.loading);
  const updating = useAppSelector(state => state.connection.updating);
  const updateSuccess = useAppSelector(state => state.connection.updateSuccess);
  const connectionStatusValues = Object.keys(ConnectionStatus);
  const handleClose = () => {
    props.history.push('/connection' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
    dispatch(getRequests({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...connectionEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      request: requests.find(it => it.id.toString() === values.request.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          status: 'APPROVE',
          ...connectionEntity,
          user: connectionEntity?.user?.id,
          request: connectionEntity?.request?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mrProjectApp.connection.home.createOrEditLabel" data-cy="ConnectionCreateUpdateHeading">
            <Translate contentKey="mrProjectApp.connection.home.createOrEditLabel">Create or edit a Connection</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="connection-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('mrProjectApp.connection.createAt')}
                id="connection-createAt"
                name="createAt"
                data-cy="createAt"
                type="text"
              />
              <ValidatedField
                label={translate('mrProjectApp.connection.longitude')}
                id="connection-longitude"
                name="longitude"
                data-cy="longitude"
                type="text"
              />
              <ValidatedField
                label={translate('mrProjectApp.connection.latitude')}
                id="connection-latitude"
                name="latitude"
                data-cy="latitude"
                type="text"
              />
              <ValidatedField
                label={translate('mrProjectApp.connection.status')}
                id="connection-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {connectionStatusValues.map(connectionStatus => (
                  <option value={connectionStatus} key={connectionStatus}>
                    {translate('mrProjectApp.ConnectionStatus' + connectionStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="connection-user"
                name="user"
                data-cy="user"
                label={translate('mrProjectApp.connection.user')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="connection-request"
                name="request"
                data-cy="request"
                label={translate('mrProjectApp.connection.request')}
                type="select"
              >
                <option value="" key="0" />
                {requests
                  ? requests.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/connection" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ConnectionUpdate;
