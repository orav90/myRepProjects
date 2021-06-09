import React from 'react'

const PullRequestTable = ({pullRequests}) => {
    if(pullRequests.length > 0)
    {
        return (
            <div>
                <table className="pullTable">
                    <thead>
                       <tr>
                         <th>Title</th>
                         <th>Body</th>
                         <th>Username</th>
                         <th>CreatedAt</th>
                         <th>URL</th>
                       </tr>
                    </thead>
                    <tbody>
                        {pullRequests.map((p =>
                            <tr>
                                <td>{p.title}</td>
                                <td>{p.body}</td>
                                <td>{p.username}</td>
                                <td>{p.createdAt}</td>
                                <td>{p.url}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
           );
    }

    
    
    else{
        return (<h3>No pull requests yet</h3>)
    }
}

export default PullRequestTable
