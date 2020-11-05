import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVideoToStand, defaultValue } from 'app/shared/model/video-to-stand.model';

export const ACTION_TYPES = {
  FETCH_VIDEOTOSTAND_LIST: 'videoToStand/FETCH_VIDEOTOSTAND_LIST',
  FETCH_VIDEOTOSTAND: 'videoToStand/FETCH_VIDEOTOSTAND',
  CREATE_VIDEOTOSTAND: 'videoToStand/CREATE_VIDEOTOSTAND',
  UPDATE_VIDEOTOSTAND: 'videoToStand/UPDATE_VIDEOTOSTAND',
  DELETE_VIDEOTOSTAND: 'videoToStand/DELETE_VIDEOTOSTAND',
  RESET: 'videoToStand/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVideoToStand>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type VideoToStandState = Readonly<typeof initialState>;

// Reducer

export default (state: VideoToStandState = initialState, action): VideoToStandState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VIDEOTOSTAND_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VIDEOTOSTAND):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_VIDEOTOSTAND):
    case REQUEST(ACTION_TYPES.UPDATE_VIDEOTOSTAND):
    case REQUEST(ACTION_TYPES.DELETE_VIDEOTOSTAND):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_VIDEOTOSTAND_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VIDEOTOSTAND):
    case FAILURE(ACTION_TYPES.CREATE_VIDEOTOSTAND):
    case FAILURE(ACTION_TYPES.UPDATE_VIDEOTOSTAND):
    case FAILURE(ACTION_TYPES.DELETE_VIDEOTOSTAND):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_VIDEOTOSTAND_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_VIDEOTOSTAND):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_VIDEOTOSTAND):
    case SUCCESS(ACTION_TYPES.UPDATE_VIDEOTOSTAND):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_VIDEOTOSTAND):
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

const apiUrl = 'api/video-to-stands';

// Actions

export const getEntities: ICrudGetAllAction<IVideoToStand> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_VIDEOTOSTAND_LIST,
  payload: axios.get<IVideoToStand>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IVideoToStand> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VIDEOTOSTAND,
    payload: axios.get<IVideoToStand>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IVideoToStand> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VIDEOTOSTAND,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVideoToStand> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VIDEOTOSTAND,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVideoToStand> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VIDEOTOSTAND,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
