
export const recordReducer = (state = [], action) =>
{
    switch (action.type)
    {
        case 'ADD_ITEM' :
            return [
                ...state,
                action.payload.record
              ]
        case 'REMOVE_ITEM':
            return state.filter(record => record !== action.payload.record)
        case 'CLEAR_ITEMS':
            return [];
        default:
            return state;
    }
}

