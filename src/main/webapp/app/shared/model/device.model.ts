import { IUser } from 'app/shared/model/user.model';

export interface IDevice {
  id?: number;
  name?: string | null;
  deviceUuid?: string | null;
  platform?: string | null;
  version?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IDevice> = {};
