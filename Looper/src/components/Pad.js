import React, { useState,useEffect } from 'react'
import { useDispatch } from 'react-redux'
import '../App.css';
import { increment, decrement,add_record,remove_record,clear_records } from '../actions'
import styled from "styled-components";

const Pad = (props) => {
    let {audio,time,play,record} = props
    const [isPlaying,setIsPLaying] = useState(false);
    const toggle = () => setIsPLaying(!isPlaying);
    const dispatch = useDispatch()

    useEffect(() => {
        //if play is active and sample is playing, handle loop
        if(isPlaying && play)
        {
            if(time === 0){
                audio.sample.play();
                audio.sample.loop = true;
            }
        }
        else{
            
            audio.sample.pause();
            audio.sample.currentTime = 0;
        }

    }, [time,isPlaying,play,audio.sample])

    useEffect(() => {
        //if play and record buttons are active, handle records list
        if(play && record ){
            if(isPlaying)
            {
                dispatch(add_record(audio.sample));
            }
            else 
            {
                dispatch(remove_record(audio.sample));
            }
        }
    }, [isPlaying,audio.sample,dispatch,play,record])

    
    useEffect(() => {
        //clear records list when record and play are active
        if(play && record){
            dispatch(clear_records())
        }
    },[record,play,dispatch])

    const buttonsCount = () =>{
        
        //if sample is not playing increment, otherwise decrement counter
        isPlaying? dispatch(decrement()) : dispatch(increment());
        toggle();
      
      }
      
    return (
        <div className = "padContainer">
            <Button play={play} isPlaying={isPlaying} onClick={() => buttonsCount()}>
                <img src={audio.icon} alt='icon'></img> 
            </Button>

        </div>
    )
}


const Button = styled.button`
  background-color: 'white';
  padding: 15px 60px;
  border-color: ${(props) => props.play  && props.isPlaying  ? "green" : "red"};
  border-width: thick;
  margin: 5px 0px;
  border-radius: 50%;
`;

export default Pad
