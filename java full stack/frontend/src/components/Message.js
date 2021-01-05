import React from 'react'
import {Alert} from 'react-bootstrap'

//children props is the text we are passing
const Message = ({variant,children}) => {
    return (
        <Alert variant={variant}>
            {children}
        </Alert>
    )
}
Message.defaultProps={
    variant: 'info'
}
export default Message
