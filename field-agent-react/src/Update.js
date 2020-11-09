import React from 'react';
import './bootstrap.css';

class Update extends React.Component {
    render() {
        return (
            <div class="container">
                <form onSubmit={this.props.editSubmitHandler}>
                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label htmlFor="FirstName">First Name</label>
                                <input type="text" value={this.props.state.firstName} name="firstName" onChange={this.props.handleChange}/>
                            </div>
                            <div class="form-group">
                                <label htmlFor="MiddleName">Middle Name</label>
                                <input type="text" value={this.props.state.middleName} name="middleName" onChange={this.props.handleChange}/>
                            </div>
                            <div class="form-group">
                                <label htmlFor="LastName">Last Name</label>
                                <input type="text" value={this.props.state.lastName} name="lastName" onChange={this.props.handleChange}/>
                            </div>
                        </div>
                        <div class="col">
                            <div class="form-group">
                                <label htmlFor="dob">Date of Birth</label>
                                <input type="date" value={this.props.state.dob} name="dob" onChange={this.props.handleChange}/>
                            </div>
                            <div class="form-group">
                                <label htmlFor="heightInInches">Height in Inches</label>
                                <input type="number" value={this.props.state.heightInInches} name="heightInInches" onChange={this.props.handleChange}/>
                            </div>  
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary">
                                    Submit Edit
                                </button>
                                <button type="cancel" class="btn btn-warning" 
                                    onClick={this.props.cancel}>
                                    Cancel Edit
                                </button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        )
    }
}

export default Update;