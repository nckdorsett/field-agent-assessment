import React from 'react';
import './bootstrap.css';

class Agent extends React.Component {

    handleDeleteClick = () => {
        const agentId = this.props.agent.agentId;
        this.props.delete(agentId);
    }

    handleEditClick = () => {
        const agentId = this.props.agent.agentId;
        this.props.edit(agentId);
    }

    render() {
        const agent = this.props.agent;
        return (
            <tr>
                <td>{agent.firstName}</td>
                <td>{agent.middleName}</td>
                <td>{agent.lastName}</td>
                <td>{agent.dob}</td>
                <td>{agent.heightInInches}</td>
                <td><button onClick={this.handleEditClick} class="btn btn-info">Edit</button>
                <button onClick={this.handleDeleteClick} class="btn btn-danger">Delete</button></td>
            </tr>
        )
    }
}

export default Agent;