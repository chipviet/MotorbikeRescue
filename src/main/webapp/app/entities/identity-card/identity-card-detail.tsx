import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './identity-card.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const IdentityCardDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const identityCardEntity = useAppSelector(state => state.identityCard.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="identityCardDetailsHeading">
          <Translate contentKey="mrProjectApp.identityCard.detail.title">IdentityCard</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{identityCardEntity.id}</dd>
          <dt>
            <span id="cardID">
              <Translate contentKey="mrProjectApp.identityCard.cardID">Card ID</Translate>
            </span>
          </dt>
          <dd>{identityCardEntity.cardID}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="mrProjectApp.identityCard.name">Name</Translate>
            </span>
          </dt>
          <dd>{identityCardEntity.name}</dd>
          <dt>
            <span id="dob">
              <Translate contentKey="mrProjectApp.identityCard.dob">Dob</Translate>
            </span>
          </dt>
          <dd>{identityCardEntity.dob}</dd>
          <dt>
            <span id="home">
              <Translate contentKey="mrProjectApp.identityCard.home">Home</Translate>
            </span>
          </dt>
          <dd>{identityCardEntity.home}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="mrProjectApp.identityCard.address">Address</Translate>
            </span>
          </dt>
          <dd>{identityCardEntity.address}</dd>
          <dt>
            <span id="sex">
              <Translate contentKey="mrProjectApp.identityCard.sex">Sex</Translate>
            </span>
          </dt>
          <dd>{identityCardEntity.sex}</dd>
          <dt>
            <span id="nationality">
              <Translate contentKey="mrProjectApp.identityCard.nationality">Nationality</Translate>
            </span>
          </dt>
          <dd>{identityCardEntity.nationality}</dd>
          <dt>
            <span id="doe">
              <Translate contentKey="mrProjectApp.identityCard.doe">Doe</Translate>
            </span>
          </dt>
          <dd>{identityCardEntity.doe}</dd>
          <dt>
            <span id="photo">
              <Translate contentKey="mrProjectApp.identityCard.photo">Photo</Translate>
            </span>
          </dt>
          <dd>{identityCardEntity.photo}</dd>
        </dl>
        <Button tag={Link} to="/identity-card" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/identity-card/${identityCardEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default IdentityCardDetail;
