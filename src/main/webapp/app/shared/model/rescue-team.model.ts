export interface IRescueTeam {
  id?: number;
  name?: string | null;
  phoneNumber?: string | null;
  longitude?: string | null;
  latitude?: string | null;
  address?: string | null;
  createAt?: string | null;
}

export const defaultValue: Readonly<IRescueTeam> = {};
