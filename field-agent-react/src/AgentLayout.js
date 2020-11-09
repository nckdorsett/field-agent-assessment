import React from 'react';
import Agent from './Agent.js';
import AddForm from './Add.js';
import Update from './Update.js';
import Navbar from './Navbar.js';
import Delete from './Delete.js';
import './bootstrap.css';

class AgentLayout extends React.Component {
    constructor() {
        super()
        this.state = {
            agents: [],
            mode: '',
            errors: [],
            id: 0,
            firstName: '',
            middleName: '',
            lastName: '',
            dob: '',
            heightInInches: ''
        };
    }

    componentDidMount() {
        this.showAgents();
    }

    showAgents = () => {
        fetch('http://localhost:8080/api/agent')
            .then(response => response.json())
            .then(data => {
                this.setState({
                    agents: data
                });
        })
    }

    beginAgentAddition = () => {
        this.setState({
            mode: 'Add'
        });
    }

    cancel = () => {
        this.setState({
            mode: '',
            errors: []
        });
    }

    handleChange = (event) => {
        this.setState({
            [event.target.name]: event.target.value
        })
    }

    
    addSubmitHandler = (event) => {
        event.preventDefault();
        this.setState({
            errors: []
        });
        fetch(`http://localhost:8080/api/agent/`, {
        method: 'Post',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          firstName: this.state.firstName,
          middleName: this.state.middleName,
          lastName: this.state.lastName,
          dob: this.state.dob,
          heightInInches: this.state.heightInInches
        })
        })
        .then(response => {
          if (response.status === 201) {
            console.log('success!');
            this.showAgents();
            this.setState({
                mode: ''
            });
          } else if (response.status === 400) {
            console.log("errors!");
            console.log(response);
            response.json().then(data => {
                console.log(data);
                this.setState({
                    errors: data
                });
            })
          } else {
            console.log('Oops... it broke');
            console.log(response);
            response.json().then(data => {
                console.log(data);
                this.setState({
                    errors: data
                });
            })
          }
        })
    }

    editAgentHandler = (agentId) => {
        console.log(agentId);
        fetch(`http://localhost:8080/api/agent/${agentId}`)
        .then(response => response.json())
        .then(data => {
            this.setState({
                mode: 'Edit',
                id: data.agentId,
                firstName: data.firstName,
                middleName: data.middleName,
                lastName: data.lastName,
                dob: data.dob,
                heightInInches: data.heightInInches
            });
        });
    }

    deleteAgentHandler = (agentId) => {
        console.log(agentId);
        fetch(`http://localhost:8080/api/agent/${agentId}`)
        .then(response => response.json())
        .then(data => {
            this.setState({
                mode: 'Delete',
                id: data.agentId,
                firstName: data.firstName,
                middleName: data.middleName,
                lastName: data.lastName,
                dob: data.dob,
                heightInInches: data.heightInInches
            });
        });
    }

    editSubmitHandler = (event) => {
        event.preventDefault();
        this.setState({
            errors: []
        });
        fetch(`http://localhost:8080/api/agent/${this.state.id}`, {
        method: 'Put',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          agentId: this.state.id,
          firstName: this.state.firstName,
          middleName: this.state.middleName,
          lastName: this.state.lastName,
          dob: this.state.dob,
          heightInInches: this.state.heightInInches
        })
        })
        .then(response => {
            if (response.status === 204) {
                console.log('success!');
                this.showAgents();
                this.setState({
                    mode: ''
                });
            } else if (response.status === 404) {
                console.log('errors!');
                console.log(response);
                response.json().then(data => {
                    console.log(data);
                    this.setState({
                        errors: data
                    });
                })
            } else {
                console.log('Oops... it broke');
                response.json().then(data => {
                    console.log(data);
                    this.setState({
                        errors: data
                    });
                })
            }
        })
        
    }

    deleteAgent = (event) => {
        this.setState({
            mode: '',
            errors: []
        });
        fetch(`http://localhost:8080/api/agent/${this.state.id}`, {
            method: 'Delete',
        })
        .then(response => {
            if (response.status === 204) {
                console.log('success!');
                this.showAgents();
            } else if (response.status === 404) {
                console.log('errors!');
                console.log(response);
                response.json().then(data => {
                    console.log(data);
                    this.setState({
                        errors: data
                    });
                })
            } else {
                console.log('Oops... it broke');
                response.json().then(data => {
                    console.log(data);
                    this.setState({
                        errors: data
                    });
                })
            }
        })
    }

    render() {
        const { mode, errors } = this.state;
        return (
            <>
                <Navbar />
                {mode === 'Add' && (<AddForm 
                    addSubmitHandler={this.addSubmitHandler}
                    handleChange={this.handleChange}
                    cancel={this.cancel}
                />)}
                {mode === 'Edit' && (<Update
                    state={this.state}
                    editSubmitHandler={this.editSubmitHandler}
                    handleChange={this.handleChange}
                    cancel={this.cancel}
                />)}
                {mode === 'Delete' && (<Delete 
                    state={this.state}
                    deleteAgent={this.deleteAgent}
                    cancel={this.cancel}
                />)}
                {errors.length > 0 && (
                <ul>
                    {errors.map((error) =>
                        <li key={error}>
                            {error}
                        </li>)}
                </ul>
                )}
                {mode === '' && (
                    <div class="container">
                        <div class="row">
                            <div class="col">
                                <h1>Agent List</h1>
                            </div>
                            <div class="col">
                                <button onClick={this.beginAgentAddition} class="btn btn-primary">
                                Add an Agent
                                </button>
                            </div>
                        </div>
                    </div>
                )}
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>First Name</th>
                            <th>Middle Name</th>
                            <th>Last Name</th>
                            <th>Date of Birth</th>
                            <th>Height in Inches</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.agents.map((agent) => <Agent
                            agent={agent}
                            key={agent.agentId}
                            delete={this.deleteAgentHandler}
                            edit={this.editAgentHandler}
                            />)}
                    </tbody>
                </table>
            </>
        );
      }
}

export default AgentLayout;