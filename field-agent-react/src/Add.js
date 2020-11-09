import React from 'react';
import './bootstrap.css';

class AddForm extends React.Component {
    render() {
        return (
            <div class="container">
                <form onSubmit={this.props.addSubmitHandler}>
                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label htmlFor="FirstName">First Name</label>
                                <input type="text" name="firstName" onChange={this.props.handleChange}/>
                            </div>
                            <div class="form-group">
                                <label htmlFor="MiddleName">Middle Name</label>
                                <input type="text" name="middleName" onChange={this.props.handleChange}/>
                            </div>
                            <div class="form-group">
                                <label htmlFor="LastName">Last Name</label>
                                <input type="text" name="lastName" onChange={this.props.handleChange}/>
                            </div>
                        </div>
                        <div class="col">
                            <div class="form-group">
                                <label htmlFor="dob">Date of Birth</label>
                                <input type="date" name="dob" onChange={this.props.handleChange}/>
                            </div>
                            <div class="form-group">
                                <label htmlFor="heightInInches">Height in Inches</label>
                                <input type="number" name="heightInInches" onChange={this.props.handleChange}/>
                            </div>  
                            <div>
                                <button type="submit" class="btn btn-primary">
                                    Submit Addition
                                </button>
                                <button type="cancel" class="btn btn-warning"
                                    onClick={this.props.cancel}> 
                                    Cancel Addition
                                </button>
                            </div>
                        </div>
                    </div>
                </form>
            </div> 
        )   
    }
}

export default AddForm;