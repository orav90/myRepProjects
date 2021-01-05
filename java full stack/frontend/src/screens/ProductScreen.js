import React ,{useState,useEffect} from 'react'
import {Link} from 'react-router-dom'
import {Row,Col,Image,ListGroup,Card,Button, Form} from 'react-bootstrap'
import Rating from '../components/Rating'
import {useDispatch, useSelector} from 'react-redux'
import { listProductDetails } from '../actions/productActions'
import Loader from '../components/Loader'
import Message from '../components/Message'

const ProductScreen = ( {history,match}) => {

    const [quantity,setQuantity] = useState(1)

    const dispatch = useDispatch()

    const productDetails = useSelector(state => state.productDetails)
    const {loading, error, product} = productDetails 
    useEffect(() => {
        dispatch(listProductDetails(match.params.id))
    }, [dispatch,match.params.id])

    const addProductToCartHandler = () =>{
        history.push(`/cart/${match.params.id}?quantity=${quantity}`)
    }


    return (
        <div >
            <Link className='btn btn-light my-3' to='/'>
                Back
            </Link>
            {loading ? (
                <Loader/> 
            ) : error ?(
                <Message variant='danger'>{error}</Message>
            ) :(
                    <Row variant='flush'>

                    <Col md={6}>
                        <Image src={product.image} alt={product.name} fluid/>
                    </Col>
                    <Col md={3}>
                        <ListGroup variant="flush">
                            <ListGroup.Item>
                                <h3>{product.name}</h3>
                            </ListGroup.Item>
                            <ListGroup.Item>
                                <Rating 
                                value = {product.rating}
                                text = {product.rating} reviews/>
                            </ListGroup.Item>
                            <ListGroup.Item>
                                Price: ${product.price}
                            </ListGroup.Item>
                            <ListGroup.Item>
                                Description: ${product.description}
                            </ListGroup.Item>
                        </ListGroup>
                    </Col>
                    <Col md={3}>
                        <Card style = {{marginTop: 30}}>
                        <ListGroup variant="flush">
                            <ListGroup.Item>
                                <Row> 
                                    <Col>Price:</Col>
                                    <Col> ${product.price} </Col>
                                </Row>
                            </ListGroup.Item>
                            <ListGroup.Item>
                                <Row>
                                    <Col>Status:</Col>
                                    <Col>{product.countInStock > 0? "In stock" : "Not in stock"} </Col>
                                </Row>
                            </ListGroup.Item>

                            {product.countInStock > 0 && (
                                <ListGroup.Item>
                                    <Row>
                                        <Col>
                                            Quantity
                                        </Col>
                                            <Form.Control as='select' value={quantity} onChange={(e) =>
                                             setQuantity(e.target.value)}>
                                                 {[...Array(product.countInStock).keys()].map(number => (
                                                     <option key={number +1} value={number+1}>
                                                         {number+1}
                                                     </option>
                                                 ))}  
         
                                            </Form.Control>
                                        <Col>
                                        </Col>
                                    </Row>
                                </ListGroup.Item>
                            )}

                            <ListGroup.Item>
                                <Button 
                                    onClick = {addProductToCartHandler}
                                    className = 'btn-block' 
                                    type = 'button' 
                                    disabled={product.countInStock === 0 }
                                    >
                                    Add to cart
                                </Button>
                            </ListGroup.Item>
                        </ListGroup>
                        </Card>
                        
                    </Col>
                </Row>
            )}
           
        </div>
    )
}

export default ProductScreen
