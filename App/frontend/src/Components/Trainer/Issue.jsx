import React from 'react'
import axios from 'axios'
import {useState } from 'react';

const Issue = (props) => {

    const [ticketSolution, setSolution] = useState(null);
    const closeTicket = () => {
        if(ticketSolution!=null){
        axios.patch("http://localhost:8080/ticket/status/" + props.ticketId, {solution:ticketSolution}
        ).then(response => {
            console.log(response);
            window.location.reload();
        }).catch(error => {
            console.log(error.data)
        });
    }
    }


    return (
        <>
            <div className="card">
                <div className="card-body">
                    <h5 className="card-title">{props.title}</h5>
                    <p className="card-body" id="issue-topic">Topic: {props.topic}</p>
                    <p className="card-body">{props.message}</p>
                    <p className="card-body">Posted by: {props.author}</p> 
                    <p className="card-body" id="issue-date">Created on: {props.date}</p>
                    <p className="card-body" id="issue-priority">Priority: {props.priority}</p>
                    
                    <input className="issueSolution" type="text" id="issue" onChange={e => setSolution(e.target.value)} required placeholder="Please enter your solution to the issue" required></input> <br></br>  
                    <button className="card-body" id="solve-button" onClick={closeTicket}>Done</button>
                </div>
            </div>

        </>
    )
}

export default Issue;