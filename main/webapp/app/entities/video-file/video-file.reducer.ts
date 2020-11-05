import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVideoFile, defaultValue } from 'app/shared/model/video-file.model';

export const ACTION_TYPES = {
  FETCH_VIDEOFILE_LIST: 'videoFile/FETCH_VIDEOFILE_LIST',
  FETCH_VIDEOFILE: 'videoFile/FETCH_VIDEOFILE',
  CREATE_VIDEOFILE: 'videoFile/CREATE_VIDEOFILE',
  UPDATE_VIDEOFILE: 'videoFile/UPDATE_VIDEOFILE',
  DELETE_VIDEOFILE: 'videoFile/DELETE_VIDEOFILE',
  RESET: 'videoFile/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVideoFile>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type VideoFileState = Readonly<typeof initialState>;

// Reducer

export default (state: VideoFileState = initialState, action): VideoFileState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VIDEOFILE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VIDEOFILE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_VIDEOFILE):
    case REQUEST(ACTION_TYPES.UPDATE_VIDEOFILE):
    case REQUEST(ACTION_TYPES.DELETE_VIDEOFILE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_VIDEOFILE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VIDEOFILE):
    case FAILURE(ACTION_TYPES.CREATE_VIDEOFILE):
    case FAILURE(ACTION_TYPES.UPDATE_VIDEOFILE):
    case FAILURE(ACTION_TYPES.DELETE_VIDEOFILE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_VIDEOFILE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_VIDEOFILE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_VIDEOFILE):
    case SUCCESS(ACTION_TYPES.UPDATE_VIDEOFILE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_VIDEOFILE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/video-files';

// Actions

export const getEntities: ICrudGetAllAction<IVideoFile> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_VIDEOFILE_LIST,
  payload: axios.get<IVideoFile>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IVideoFile> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VIDEOFILE,
    payload: axios.get<IVideoFile>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IVideoFile> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VIDEOFILE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVideoFile> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VIDEOFILE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVideoFile> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VIDEOFILE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
