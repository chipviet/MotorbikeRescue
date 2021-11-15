export interface IIdentityCard {
  id?: number;
  cardID?: string | null;
  name?: string | null;
  dob?: string | null;
  home?: string | null;
  address?: string | null;
  sex?: string | null;
  nationality?: string | null;
  doe?: string | null;
  photo?: string | null;
}

export const defaultValue: Readonly<IIdentityCard> = {};
