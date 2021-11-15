import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale from './locale';
import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import connection from 'app/entities/connection/connection.reducer';
// prettier-ignore
import request from 'app/entities/request/request.reducer';
// prettier-ignore
import rating from 'app/entities/rating/rating.reducer';
// prettier-ignore
import identityCard from 'app/entities/identity-card/identity-card.reducer';
// prettier-ignore
import device from 'app/entities/device/device.reducer';
// prettier-ignore
import rescueTeam from 'app/entities/rescue-team/rescue-team.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  connection,
  request,
  rating,
  identityCard,
  device,
  rescueTeam,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
