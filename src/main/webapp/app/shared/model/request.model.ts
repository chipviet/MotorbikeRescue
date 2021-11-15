import { IConnection } from 'app/shared/model/connection.model';
import { IUser } from 'app/shared/model/user.model';
import { RequestStatus } from 'app/shared/model/enumerations/request-status.model';

export interface IRequest {
  id?: number;
  longitude?: string | null;
  latitude?: string | null;
  createAt?: string | null;
  message?: string | null;
  status?: RequestStatus | null;
  connections?: IConnection[] | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IRequest> = {};
