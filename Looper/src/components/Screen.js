import React ,{ useState,useEffect } from 'react'
import Pad from './Pad'
import { useSelector } from 'react-redux'
import { samplesArray } from '../samples'
import '../App.css';


const Screen = () => {
    
    const [play,setPlay] = useState(false);
    const [seconds, setSeconds] = useState(0);
    const counter = useSelector(state => state.counter)
    const recordsList = useSelector(state => state.record)
    const [record,setRecord] = useState(false);

    //handle timer loop
    useEffect(() => {
        if(play)
        { 
            const interval = setInterval(() => {

                //if only one pad is active, start immediately
                if(counter === 1 ){
                    setSeconds(0);
                }
                if(seconds >= 7)
                {
                    setSeconds(0);
                }
                else{
                    setSeconds(seconds => seconds+1);
                }

        }, 1000);
        return () => clearInterval(interval);
        }
    }, [seconds,play,counter]);

    //play the samples records list according to user requests
    const playRecordsList = () =>{
        for(let i in recordsList){
            recordsList[i].loop = false;
            recordsList[i].play();
        }
    }

    return (
    <div className='homepage-bgimage'>
    <h1 className="loopmachine">Loop Machine</h1>
    <div className="samplesContainer">
        {samplesArray.map(audio =>{
            return (
                    <Pad 
                        audio = {audio}
                        time = {seconds}
                        play = {play}
                        record = {record}
                        key ={audio.idx}>
                    </Pad>)      
        })}
    </div>
           
             <div style={{textAlign:'center'}}>
                     <button className ='playButton' onClick={() => setPlay(!play)} >
                         {play? 'Stop' : 'Start'}
                    </button>
                    <br/>
                    <button className ='recordButton' onClick={() => {setRecord(!record)}} >
                    
                         {record? 'Stop Recording' : 'Record'}
                    </button>                    
                    <button className = 'loadButton'
                    disabled={record || recordsList.length <= 0}
                     
                    onClick={playRecordsList}>Load</button>
                    
            </div>
    </div>)
}

export default Screen
