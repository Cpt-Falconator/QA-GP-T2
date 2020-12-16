import React, { useEffect, useState, useContext } from 'react'
import axios from 'axios'
import Issue from './Issue';
import RespondedIssuesProps from './RespondedIssuesProps';
import IpContext from '../../IpContext';
import ReactDOM from 'react-dom';


const ViewAllIssues = () => {

    const ip = useContext(IpContext);
    const [data, setData] = useState([]);
    const [respondedData, setRespondedData] = useState([]);

    
    
    useEffect(() => {
        axios.get("http://"+ip+"/ticket/findByStatus/Closed")
            .then(response => {
                response.data.map( (child) => {
                    if(child.status === "Closed" ){
                        console.log("I'm here");
                        setRespondedData(response.data);
                    }else{
                    }
                })
            })
    }, [])  



    const Respondeditems = (respondedData.map((issue) => (
        
        <RespondedIssuesProps
            ticketId={issue.id}
            title={issue.title}
            topic={issue.topic}
            message={issue.issue}
            date={issue.submitDate}
            priority={issue.urgency}
        />
    )))



    return (
        <>
            

            <h3 id="completed-heading">Completed Items</h3>
            <div name="issues" id="completed-issues">
        <p id="completed-p"> Below shows all the items that has been responded to by the trainers.</p>
                {Respondeditems}
            </div>
        </>
    );

};
export default ViewAllIssues;