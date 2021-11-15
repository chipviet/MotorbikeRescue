import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/connection">
      <Translate contentKey="global.menu.entities.connection" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/request">
      <Translate contentKey="global.menu.entities.request" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/rating">
      <Translate contentKey="global.menu.entities.rating" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/identity-card">
      <Translate contentKey="global.menu.entities.identityCard" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/device">
      <Translate contentKey="global.menu.entities.device" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/rescue-team">
      <Translate contentKey="global.menu.entities.rescueTeam" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
