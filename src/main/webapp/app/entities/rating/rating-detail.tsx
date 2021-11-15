import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './rating.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RatingDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const ratingEntity = useAppSelector(state => state.rating.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ratingDetailsHeading">
          <Translate contentKey="mrProjectApp.rating.detail.title">Rating</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.id}</dd>
          <dt>
            <span id="star">
              <Translate contentKey="mrProjectApp.rating.star">Star</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.star}</dd>
          <dt>
            <span id="comment">
              <Translate contentKey="mrProjectApp.rating.comment">Comment</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.comment}</dd>
          <dt>
            <span id="createAt">
              <Translate contentKey="mrProjectApp.rating.createAt">Create At</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.createAt}</dd>
          <dt>
            <Translate contentKey="mrProjectApp.rating.request">Request</Translate>
          </dt>
          <dd>{ratingEntity.request ? ratingEntity.request.id : ''}</dd>
          <dt>
            <Translate contentKey="mrProjectApp.rating.user">User</Translate>
          </dt>
          <dd>{ratingEntity.user ? ratingEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/rating" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rating/${ratingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RatingDetail;
