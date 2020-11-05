import { Moment } from 'moment';
import { IStand } from 'app/shared/model/stand.model';

export interface IVideoFile {
  id?: number;
  uuid?: string;
  size?: number;
  mimeType?: string;
  startDate?: Moment;
  endDate?: Moment;
  stand?: IStand;
}

export const defaultValue: Readonly<IVideoFile> = {};
