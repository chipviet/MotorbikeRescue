import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './rescue-team.reducer';
import { IRescueTeam } from 'app/shared/model/rescue-team.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RescueTeamUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const rescueTeamEntity = useAppSelector(state => state.rescueTeam.entity);
  const loading = useAppSelector(state => state.rescueTeam.loading);
  const updating = useAppSelector(state => state.rescueTeam.updating);
  const updateSuccess = useAppSelector(state => state.rescueTeam.updateSuccess);
  const handleClose = () => {
    props.history.push('/rescue-team');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...rescueTeamEntity,
      ...values,
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
          ...rescueTeamEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mrProjectApp.rescueTeam.home.createOrEditLabel" data-cy="RescueTeamCreateUpdateHeading">
            <Translate contentKey="mrProjectApp.rescueTeam.home.createOrEditLabel">Create or edit a RescueTeam</Translate>
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
                  id="rescue-team-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('mrProjectApp.rescueTeam.name')}
                id="rescue-team-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('mrProjectApp.rescueTeam.phoneNumber')}
                id="rescue-team-phoneNumber"
                name="phoneNumber"
                data-cy="phoneNumber"
                type="text"
              />
              <ValidatedField
                label={translate('mrProjectApp.rescueTeam.longitude')}
                id="rescue-team-longitude"
                name="longitude"
                data-cy="longitude"
                type="text"
              />
              <ValidatedField
                label={translate('mrProjectApp.rescueTeam.latitude')}
                id="rescue-team-latitude"
                name="latitude"
                data-cy="latitude"
                type="text"
              />
              <ValidatedField
                label={translate('mrProjectApp.rescueTeam.address')}
                id="rescue-team-address"
                name="address"
                data-cy="address"
                type="text"
              />
              <ValidatedField
                label={translate('mrProjectApp.rescueTeam.createAt')}
                id="rescue-team-createAt"
                name="createAt"
                data-cy="createAt"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rescue-team" replace color="info">
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

export default RescueTeamUpdate;
