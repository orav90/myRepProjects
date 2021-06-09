import React,{useEffect,useState} from 'react'
import axios from 'axios'
import PullRequestTable from './PullRequestTable';
import PullRequestGrid from './PullRequestGrid';


const Screen = () => {
    const [pullRequests, setPullRequests]=useState([])
    const [view,setView] = useState(true)
    const url = "http://localhost:5000";
    let image


    const getRequests =  () => {
        axios.get(`${url}/pulls`)
        .then((response) =>{
            setPullRequests(response.data)
        })
        .catch((err) => console.log(err))
    }

    const getImage = () => {
        axios.get(`${url}/image`)
        .then((response) =>{
            image = response.data
        })
        .catch((err) => console.log(err))
    }

      useEffect(()=>{
        const interval = setInterval(() => {
            getRequests()
          }, 5000);
          return () => clearInterval(interval);
    },[]) 

    useEffect(()=>{
        if(pullRequests.length > 0)
        {
            getImage()
        }
    },[pullRequests.length]) 

    return (
        <div>
            {view?
            <PullRequestGrid pullRequests = {pullRequests}/> :
            <PullRequestTable pullRequests = {pullRequests}/>}
            {pullRequests.length > 0 ? <img className="screenshot"  src={`${url}/screenshot.png`}></img> : null}
            <div className="viewbtn"><button onClick ={() =>setView(!view)}> {view ? "Table" : "Grid"}</button></div>

        </div>
    )
}

export default Screen
