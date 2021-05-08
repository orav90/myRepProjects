import {counterReducer} from './CountReducer'
import {recordReducer} from './RecordReducer'
import { combineReducers } from 'redux'


export const reducers = combineReducers({
    counter : counterReducer,
    record : recordReducer
  
})