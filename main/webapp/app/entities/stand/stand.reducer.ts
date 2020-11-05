import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IStand, defaultValue } from 'app/shared/model/stand.model';

export const ACTION_TYPES = {
  FETCH_STAND_LIST: 'stand/FETCH_STAND_LIST',
  FETCH_STAND: 'stand/FETCH_STAND',
  CREATE_STAND: 'stand/CREATE_STAND',
  UPDATE_STAND: 'stand/UPDATE_STAND',
  DELETE_STAND: 'stand/DELETE_STAND',
  RESET: 'stand/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IStand>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type StandState = Readonly<typeof initialState>;

// Reducer

export default (state: StandState = initialState, action): StandState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_STAND_LIST):
    case REQUEST(ACTION_TYPES.FETCH_STAND):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_STAND):
    case REQUEST(ACTION_TYPES.UPDATE_STAND):
    case REQUEST(ACTION_TYPES.DELETE_STAND):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_STAND_LIST):
    case FAILURE(ACTION_TYPES.FETCH_STAND):
    case FAILURE(ACTION_TYPES.CREATE_STAND):
    case FAILURE(ACTION_TYPES.UPDATE_STAND):
    case FAILURE(ACTION_TYPES.DELETE_STAND):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_STAND_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_STAND):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_STAND):
    case SUCCESS(ACTION_TYPES.UPDATE_STAND):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_STAND):
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

const apiUrl = 'api/stands';

// Actions

export const getEntities: ICrudGetAllAction<IStand> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_STAND_LIST,
  payload: axios.get<IStand>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IStand> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_STAND,
    payload: axios.get<IStand>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IStand> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_STAND,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IStand> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_STAND,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IStand> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_STAND,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
