import React from 'react';
import './App.scss';
import Login from './component/login/Login';
import {BrowserRouter as Router, Route} from 'react-router-dom'
import Register from './component/register/Register';
import Profile from './component/profile/Profile';
import Search from './component/search/Search';
import Home from './component/home/Home';
import UserService from './services/UserService'
import CocktailDetails from './component/details/CocktailDetails';
import NavHeader from "./component/NavHeader";
import AdminDashboard from "./component/admin/AdminDashboard";

class App extends React.Component {
    constructor(props) {
        super(props)
        this.userService = UserService.getInstance()
        this.state = {
            user: {}
        };
        this.retrieveLoggedInUser()
    }

    retrieveLoggedInUser = () => {
        this.userService.getLoggedInUser()
            .then(returnedUser => {
                this.setState({
                    user: returnedUser
                })});
    };

    render() {
        return (
            <Router>
                <NavHeader user={this.state.user}/>
                <Route exact path="/"
                       render={() => <Home user={this.state.user}/>}
                />
                <Route exact path="/login"
                       render={() => <Login user={this.state.user}
                                            retrieveLoggedInUser={this.retrieveLoggedInUser}/>}
                />
                <Route exact path="/register"
                       render={() => <Register user={this.state.user}
                                               retrieveLoggedInUser={this.retrieveLoggedInUser}/>}
                />
                <Route exact path="/profile"
                       render={() => <Profile user={this.state.user}
                                              retrieveLoggedInUser={this.retrieveLoggedInUser}
                       />}
                />
                <Route exact path="/profile/:id"
                       render={({match}) => <Profile id={match.params.id}
                                                     user={this.state.user}
                                                     retrieveLoggedInUser={this.retrieveLoggedInUser}
                       />}
                />
                <Route exact path="/search"
                       render={() => <Search user={this.state.user}/>}
                />
                <Route exact path="/details/:id"
                       render={({match}) => <CocktailDetails id={match.params.id} user={this.state.user}/>} />

                <Route exact path="/search/:searchCriteria"
                       render={({match}) => <Search user={this.state.user}
                                                    searchCriteria={match.params.searchCriteria}/>}
                />
                <Route exact path="/admin"
                       render={() => <AdminDashboard user={this.state.user}/>}/>
            </Router>
        )
    }
}

export default App;
