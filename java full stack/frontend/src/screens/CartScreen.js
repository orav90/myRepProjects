import React,{ useEffect } from 'react'
import { Link } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'
import { Row, Col, ListGroup, Image, Form, Button} from 'react-bootstrap'
import Message from '../components/Message'
import { addItemToCart,removeItemFromCart } from '../actions/cartActions'

const CartScreen = ({ match,location,history }) => {

    const productId = match.params.id

    const quantity = location.search ? Number(location.search.split('=')[1]) : 1

    const cart = useSelector(state => state.cart)

    console.log(quantity)
    console.log(productId)

    const {cartItems} = cart
    console.log(cart)

    const dispatch = useDispatch()

    useEffect(() => {
        if(productId){
            console.log("log")
            dispatch(addItemToCart(productId,quantity))
        }
    }, [dispatch,productId,quantity])

    const purchaseHandlder = () =>{
        history.push('/login?redirect=shipping')
    }

    const removeItemFromCartHandler = (id) =>{
        console.log(id)
        dispatch(removeItemFromCart(id))
    }

    return (
        <Row>
            <Col md={8}>
                <h1>Shopping Cart</h1>
                {cartItems.length === 0 ? <Message>Your cart is empty <Link to='/'>Go back</Link></Message>
                :(
                    <ListGroup variant='flush'>
                        {cartItems.map(item => (
                            <ListGroup.Item key={item.product}>
                                <Row>
                                    <Col md={2}>
                                        <Image src={item.image} alt={item.name} fluid rounded></Image>
                                    </Col>
                                    <Col md={3}>
                                        <Link to={`/product/${item.product}`}>{item.name}</Link>
                                    </Col>
                                    <Col md={2}> ${item.price} </Col>
                                    <Col md={2}>
                                    <Form.Control as='select' value={item.quantity} onChange={(e) =>
                                             dispatch(addItemToCart(item.product,Number(e.target.value)))}>
                                                 {[...Array(item.countInStock).keys()].map(number => (
                                                     <option key={number +1} value={number+1}>
                                                         {number+1}
                                                     </option>
                                                 ))}  
         
                                            </Form.Control>
                                    </Col>
                                    <Col md={2}>
                                        <Button type='button' variant='light' onClick={() => removeItemFromCartHandler(item.product)}>
                                            <i className='fas fa-trash'></i>
                                        </Button>
                                    </Col>
                                </Row>
                            </ListGroup.Item>
                        ))}
                    </ListGroup>
                )}
            </Col>
            <Col md={4}>
                <Row>
                    <ListGroup variant='flush'>
                        <ListGroup.Item>
                            <h3> Total of {cartItems.reduce((acc,curr) => acc+curr.quantity,0)} items</h3>
                            <h5>Total Amount
                                 ${cartItems.reduce((acc,curr) => acc+curr.quantity*curr.price,0).toFixed(2)}</h5>
                        </ListGroup.Item>
                        <ListGroup.Item>
                            <Button type='button' className='btn-block' disabled={cartItems.length === 0}
                            onClick = {purchaseHandlder}>
                                Purchase
                            </Button>
                        </ListGroup.Item>
                    </ListGroup>
                </Row>
            </Col>

        </Row>
    )
}

export default CartScreen
