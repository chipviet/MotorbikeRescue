import { IConnection } from 'app/shared/model/connection.model';
import { IUser } from 'app/shared/model/user.model';

export interface IRating {
  id?: number;
  star?: number | null;
  comment?: string | null;
  createAt?: string | null;
  request?: IConnection | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IRating> = {};
