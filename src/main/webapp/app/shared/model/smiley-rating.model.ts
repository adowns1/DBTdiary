import { IUser } from 'app/core/user/user.model';
import { Rating } from 'app/shared/model/enumerations/rating.model';

export interface ISmileyRating {
  id?: number;
  rating?: Rating;
  user?: IUser;
}

export class SmileyRating implements ISmileyRating {
  constructor(public id?: number, public rating?: Rating, public user?: IUser) {}
}
