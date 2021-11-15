import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './identity-card.reducer';
import { IIdentityCard } from 'app/shared/model/identity-card.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const IdentityCardUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const identityCardEntity = useAppSelector(state => state.identityCard.entity);
  const loading = useAppSelector(state => state.identityCard.loading);
  const updating = useAppSelector(state => state.identityCard.updating);
  const updateSuccess = useAppSelector(state => state.identityCard.updateSuccess);
  const handleClose = () => {
    props.history.push('/identity-card' + props.location.search);
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
      ...identityCardEntity,
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
          ...identityCardEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mrProjectApp.identityCard.home.createOrEditLabel" data-cy="IdentityCardCreateUpdateHeading">
            <Translate contentKey="mrProjectApp.identityCard.home.createOrEditLabel">Create or edit a IdentityCard</Translate>
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
                  id="identity-card-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('mrProjectApp.identityCard.cardID')}
                id="identity-card-cardID"
                name="cardID"
                data-cy="cardID"
                type="text"
              />
              <ValidatedField
                label={translate('mrProjectApp.identityCard.name')}
                id="identity-card-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('mrProjectApp.identityCard.dob')}
                id="identity-card-dob"
                name="dob"
                data-cy="dob"
                type="text"
              />
              <ValidatedField
                label={translate('mrProjectApp.identityCard.home')}
                id="identity-card-home"
                name="home"
                data-cy="home"
                type="text"
              />
              <ValidatedField
                label={translate('mrProjectApp.identityCard.address')}
                id="identity-card-address"
                name="address"
                data-cy="address"
                type="text"
              />
              <ValidatedField
                label={translate('mrProjectApp.identityCard.sex')}
                id="identity-card-sex"
                name="sex"
                data-cy="sex"
                type="text"
              />
              <ValidatedField
                label={translate('mrProjectApp.identityCard.nationality')}
                id="identity-card-nationality"
                name="nationality"
                data-cy="nationality"
                type="text"
              />
              <ValidatedField
                label={translate('mrProjectApp.identityCard.doe')}
                id="identity-card-doe"
                name="doe"
                data-cy="doe"
                type="text"
              />
              <ValidatedField
                label={translate('mrProjectApp.identityCard.photo')}
                id="identity-card-photo"
                name="photo"
                data-cy="photo"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/identity-card" replace color="info">
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

export default IdentityCardUpdate;
