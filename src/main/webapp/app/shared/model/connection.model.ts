import { IUser } from 'app/shared/model/user.model';
import { IRequest } from 'app/shared/model/request.model';
import { ConnectionStatus } from 'app/shared/model/enumerations/connection-status.model';

export interface IConnection {
  id?: number;
  createAt?: string | null;
  longitude?: string | null;
  latitude?: string | null;
  status?: ConnectionStatus | null;
  user?: IUser | null;
  request?: IRequest | null;
}

export const defaultValue: Readonly<IConnection> = {};
