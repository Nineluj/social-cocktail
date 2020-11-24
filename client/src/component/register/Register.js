import React from 'react'
import UserService from '../../services/UserService'
import {Link, Redirect} from "react-router-dom";
import {Container} from "react-bootstrap";
import './Register.scss';

class Register extends React.Component {
    constructor(props) {
        super(props)
        this.userService = UserService.getInstance()
        this.state = {
            username: '',
            password: '',
            password2: '',
            role: 'ENTHUSIAST',
            barName: '',
            supervisorName: '',
            supervisorPhone: ''
        }
    }

    updateUsername = (event) => {
        this.setState({
            username: event.target.value
        });
    };

    updatePassword = (event) => {
        this.setState({
            password: event.target.value
        });
    };

    updatePassword2 = (event) => {
        this.setState({
            password2: event.target.value
        });
    };

    updateBarName = (event) => {
        this.setState({
            barName: event.target.value
        });
    };

    updateSupervisorName = (event) => {
        this.setState({
            supervisorName: event.target.value
        });
    };

    updateSupervisorPhone = (event) => {
        this.setState({
            supervisorPhone: event.target.value
        })
    };

    onRegister = () => {
        if (this.state.password === this.state.password2) {
            let asyncCall;
            if (this.state.role === "BARTENDER") {
                asyncCall = this.userService.registerBartender({
                    username: this.state.username,
                    password: this.state.password,
                    role: this.state.role,
                    barName: this.state.barName,
                    supervisorName: this.state.supervisorName,
                    supervisorPhone: this.state.supervisorPhone
                })
            } else {
                asyncCall = this.userService.registerUser({
                    username: this.state.username,
                    password: this.state.password,
                    role: this.state.role
                })
            }
            asyncCall.then(response =>
                response.status === 200
                    ? this.props.retrieveLoggedInUser()
                    : alert('Registration failed.'))
        }
        else {
            alert("Passwords do not match")
        }
    };

    render() {
        if (this.props.user.username !== undefined) {
            return (
                <Redirect to='/'/>
            )
        }

        return (
            <Container className="mt-3">
                <h1>Sign Up</h1>
                <div className="form-group row">
                    <label htmlFor="username"
                           className="col-sm-2 col-form-label">
                        Username </label>
                    <div className="col-sm-10">
                        <input className="form-control"
                               id="username"
                               placeholder="Alice"
                               value={this.state.username}
                               onChange={this.updateUsername}/>
                    </div>
                </div>
                <div className="form-group row">
                    <label htmlFor="password"
                           className="col-sm-2 col-form-label">
                        Password </label>
                    <div className="col-sm-10">
                        <input type="password"
                               className="form-control wbdv-password-fld"
                               id="password"
                               placeholder="123qwe#$%"
                               value={this.state.password}
                               onChange={this.updatePassword}/>
                    </div>
                </div>
                <div className="form-group row">
                    <label htmlFor="verify"
                           className="col-sm-2 col-form-label">
                        Verify Password </label>
                    <div className="col-sm-10">
                        <input type="password"
                               className="form-control wbdv-password-fld"
                               id="verfiy"
                               placeholder="123qwe#$%"
                               value={this.state.password2}
                               onChange={this.updatePassword2}/>
                    </div>
                </div>
                <div className="mb-3 mt-4 register-bartender-text row">
                <span>
                    Our community has two separate roles. If you are a typical user than please select the drink
                    enthusiast option and if you are a licensed bartender then please choose the bartender option.
                    The bartender option will require verification by an admin which will require you to enter additional
                    information. Once complete, it will allow you to add bartender suggestions on cocktails and also modify
                    glass descriptions but you will be a normal user until the verification is complete.
                </span>
                </div>
                <div className="form-group row">
                    <label htmlFor="role"
                           className="col-sm-2 col-form-label">
                        Role
                    </label>
                    <select className="form-control col-sm-10"
                            id="role"
                            value={this.state.role}
                            onChange={(event) => this.setState({
                                role: event.target.value
                            })}>
                        <option value='ENTHUSIAST'>Enthusiast</option>
                        <option value='BARTENDER'>Bartender</option>
                    </select>
                </div>

                { this.state.role === 'BARTENDER' &&
                <React.Fragment>
                    <div className="form-group row">
                        <label htmlFor="barName"
                               className="col-sm-2 col-form-label">
                            Name of bar </label>
                        <div className="col-sm-10">
                            <input className="form-control"
                                   id="barName"
                                   placeholder="The Horse and the Brew"
                                   value={this.state.barName}
                                   onChange={this.updateBarName}/>
                        </div>
                    </div>

                    <div className="form-group row">
                        <label htmlFor="supervisorName"
                               className="col-sm-2 col-form-label">
                            Supervisor Name </label>
                        <div className="col-sm-10">
                            <input className="form-control"
                                   id="supervisorName"
                                   placeholder="Eve Smith"
                                   value={this.state.supervisorName}
                                   onChange={this.updateSupervisorName}/>
                        </div>
                    </div>

                    <div className="form-group row">
                        <label htmlFor="supervisorPhone"
                               className="col-sm-2 col-form-label">
                            Supervisor Phone </label>
                        <div className="col-sm-10">
                            <input className="form-control"
                                   id="supervisorPhone"
                                   placeholder="(000) 000 0000"
                                   type="tel"
                                   value={this.state.supervisorPhone}
                                   onChange={this.updateSupervisorPhone}/>
                        </div>
                    </div>
                </React.Fragment>


                }


                <div className="form-group row">
                    <label className="col-sm-2 col-form-label"></label>
                    <div className="col-sm-10">
                        <button className="btn btn-primary btn-block"
                                onClick={this.onRegister}>Sign up</button>
                        <div className="row">
                            <div className="col-6">
                                <Link to="/login">Login</Link>
                            </div>
                        </div>
                    </div>
                </div>
            </Container>
        )
    }
}

export default Register
