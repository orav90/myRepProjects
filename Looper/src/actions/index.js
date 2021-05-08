

export const increment = () =>{
    return {type:'INC'};
}

export const decrement = () =>{
    return {type:'DEC'};
}

export const add_record = (record) => {
    return {
        type: 'ADD_ITEM',
        payload:{ record }
    }
}


export const remove_record = (record) =>{
    return {
        type: 'REMOVE_ITEM',
        payload:{ record }
    }
}

export const clear_records = () => (dispatch) =>{
    dispatch ({
        type: 'CLEAR_ITEMS'
    })
}

