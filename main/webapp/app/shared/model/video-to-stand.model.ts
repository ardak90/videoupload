import { IVideoFile } from 'app/shared/model/video-file.model';
import { IStand } from 'app/shared/model/stand.model';

export interface IVideoToStand {
  id?: number;
  videoFile?: IVideoFile;
  stand?: IStand;
}

export const defaultValue: Readonly<IVideoToStand> = {};
