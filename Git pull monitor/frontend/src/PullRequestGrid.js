import React from 'react'

const PullRequestGrid = ({pullRequests}) => {
    if(pullRequests.length > 0)
    { 
        return (
            <div className="pullGrid">
            {pullRequests.map(p =>{
            return (
                <div key={p.id}>
                    Title:{p.title}<br/>
                    Body:{p.body}<br/>
                    User:{p.username}<br/>
                    Created At:{p.createdAt}<br/>
                    Url:{p.url}
                </div>
            )      
        })}
            </div>)

    }

    
    
    else{
        return (<h3>No pull requests yet</h3>)
    }
}

export default PullRequestGrid
