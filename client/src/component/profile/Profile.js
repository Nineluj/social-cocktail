import React from 'react'
import UserService from '../../services/UserService';
import {Link} from 'react-router-dom'
import UserListPanel from './UserListPanel';
import {Button, Col, Row} from 'react-bootstrap';
import UserLikesPanel from './UserLikesPanel';
import CommentsPanel from '../CommentsPanel';
import CommentService from '../../services/CommentService';
import './Profile.scss';
import { Redirect } from 'react-router-dom'

class Profile extends React.Component {
    constructor(props) {
        super(props)
        this.userService = UserService.getInstance()
        this.commentService = CommentService.getInstance()

        // We are viewing a public profile
        if (this.props.id !== undefined) {
            this.state = {
                isPublic: true,
                user: {},
                userId: this.props.id,
                followers: [],
                following: [],
                firstFiveComments: [],
                firstFiveCocktails: [],
                loggedInFollowing: [],
                redirectHome: false
            }
            this.retrieveAllPublicUserData()
            this.getLoggedInFollowing()
        }
        else if (this.props.user.id !== undefined) {
            this.state = {
                isPublic: false,
                user: this.props.user,
                userId: this.props.user.id,
                followers: [],
                following: [],
                firstFiveComments: [],
                firstFiveCocktails: [],
                loggedInFollowing: [],
                redirectHome: false
            };

            this.retrieveAllPrivateUserData()
        } else {
            this.state = {
                isPublic: false,
                user: {},
                userId: -1,
                followers: [],
                following: [],
                firstFiveComments: [],
                firstFiveCocktails: [],
                loggedInFollowing: [],
                redirectHome: false
            }
        }
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.props.user.id !== prevProps.user.id) {
            this.setState({
                user: this.props.user,
                userId: this.props.user.id
            })
        }
    }

    static getDerivedStateFromProps(props, state) {

      if (props.user.username !== state.user.username &&
          !state.isPublic) {
        return {user: props.user, userId: props.user.id}
      }
      else {
          return state;
      }
    }

    retrieveAllPublicUserData = () => {
        this.userService.findUserById(this.props.id)
        .then(user => {
            if (user !== undefined) {
                this.setState({
                    user: user
                })
                return user;
            }
            this.setState({
                redirectHome: true
            })
            return Promise.reject('Undefined User')
        }).then(res => {
            this.userService.getFollowersById(this.props.id)
            .then(followers => this.setState({
                followers: followers
            }))

            this.userService.getFollowingById(this.props.id)
            .then(following => this.setState({
                following: following
            }))

            this.commentService.getCommentsByUserId(this.props.id, 5)
            .then(comments => this.setState({
                firstFiveComments: comments
            }))

            this.userService.getLikedCocktails(this.props.id, 5)
            .then(cocktails => this.setState({
                firstFiveCocktails: cocktails
            }))
        })
    }

    retrieveAllPrivateUserData = () => {
        this.userService.getFollowers()
        .then(followers => this.setState({
            followers: followers
        }))

        this.userService.getFollowing()
        .then(following => this.setState({
            following: following
        }))

        this.commentService.getCommentsByUserId(this.props.user.id, 5)
        .then(comments => this.setState({
            firstFiveComments: comments
        }))

        this.userService.getLikedCocktails(this.props.user.id, 5)
        .then(cocktails => this.setState({
            firstFiveCocktails: cocktails
        }))
    }
    
    getLoggedInFollowing = () => {
        if (this.user !== undefined && this.user.id !== undefined) {
            this.userService.getFollowing()
            .then(following => this.setState({
                loggedInFollowing: following
            }))
        }
    }

    getRecentComments = () => {
        this.commentService.getCommentsByUserId(this.props.user.id, 5)
        .then(comments => this.setState({
            firstFiveComments: comments
        }))
    }

    render() {
        if (this.state.redirectHome) {
            alert('This user does not exist')
            return <Redirect to='/'></Redirect>
        }
        if (this.state.userId !== undefined && 
            this.props.id !== undefined &&
            this.state.userId !== this.props.id) {
                this.setState({
                    userId: this.props.id
                })
                this.retrieveAllPublicUserData()
        }
        if (this.state.user.id === undefined && 
            !this.state.isPublic) {
            return (<h1>
                        Please <Link to="/login">Login</Link> to View Profile
                    </h1>
                )
        }
        else if (!this.state.isPublic) {
            return (
                <div className="container mt-3">
                    <h1>Profile</h1>
                    <form>
                        <div className="form-group row">
                            <label htmlFor="username"
                                className="col-sm-2 col-form-label">
                                Username </label>
                            <div className="col-sm-10">
                                <input className="form-control"
                                    id="username"
                                    placeholder="Alice"
                                    readOnly
                                    value={this.state.user.username}/>
                            </div>
                        </div>

                        <div className="form-group row">
                            <label htmlFor="role"
                                   className="col-sm-2 col-form-label">
                                Role
                            </label>
                            <div className="col-sm-10">
                                <select className="form-control"
                                        id="role"
                                        disabled
                                        onChange={(event) => {
                                            let updatedUser = {...this.state.user};
                                            updatedUser.role = event.target.value;

                                            this.setState({
                                                user: updatedUser
                                            })
                                        }}
                                        value={this.state.user.role}>
                                    <option value='ENTHUSIAST'>Enthusiast</option>
                                    <option value='BARTENDER'>Bartender</option>
                                </select>
                            </div>
                        </div>

                        {
                            this.state.user.role === "BARTENDER" &&
                            <div className="row mt-2 mb-4">
                                <div className="col-5 offset-2">
                                    <span className="font-weight-bold mr-1">
                                        Status:
                                    </span>
                                    {this.state.user.verified
                                        ? "You have been verified"
                                        : "You are pending verification"}
                                </div>
                            </div>
                        }

                        <div className="form-group row">
                            <label htmlFor="phone"
                                className="col-sm-2 col-form-label">
                                Phone </label>
                            <div className="col-sm-10">
                                <input className="form-control"
                                    id="phone"
                                    placeholder="(123) 456-7890"
                                    type="tel"
                                    onChange={(event) => {
                                        let updatedUser = {...this.state.user};
                                        updatedUser.phoneNum = event.target.value;

                                        this.setState({
                                            user: updatedUser
                                        })
                                    }}
                                    value={this.state.user.phoneNum}/>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="email"
                                className="col-sm-2 col-form-label">
                                Email </label>
                            <div className="col-sm-10">
                                <input className="form-control"
                                    id="email"
                                    placeholder="alice@wonderland.com"
                                    type="email"
                                    onChange={(event) => {
                                        let updatedUser = {...this.state.user};
                                        updatedUser.email = event.target.value;

                                        this.setState({
                                            user: updatedUser
                                        })
                                    }}
                                    value={this.state.user.email}/>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label className="col-sm-2 col-form-label"/>
                            <div className="col-sm-10">
                                <button className="btn btn-success btn-block"
                                        onClick={() => {
                                            this.userService.updateUser(this.state.user)
                                                .then(user => this.setState({
                                                    user: user,
                                                    userId: user.id
                                                }))

                                            this.props.retrieveLoggedInUser()
                                        }}>
                                    Update
                                </button>
                            </div>
                        </div>
                    </form>
                    <Row>
                        <Col xs={12} md={6}>
                            <CommentsPanel title='Recent Comments by this User'
                                           comments={this.state.firstFiveComments}
                                           isPrivateProfile={true}
                                           getRecentComments={this.getRecentComments}/>
                        </Col>
                        <Col xs={12} md={6}>
                            <UserLikesPanel title='Recently liked Cocktails'
                                            cocktails={this.state.firstFiveCocktails}/>
                        </Col>
                        
                        <Col xs={12} md={6}>
                            <UserListPanel title='Following' 
                                           users={this.state.following} 
                                           following={this.state.loggedInFollowing}
                                           loggedInId={this.props.user.id}
                                           getLoggedInFollowing={this.getLoggedInFollowing}/>
                        </Col>
                        <Col xs={12} md={6}>
                            <UserListPanel title='Followers' 
                                           users={this.state.followers} 
                                           following={this.state.loggedInFollowing}
                                           loggedInId={this.props.user.id}
                                           getLoggedInFollowing={this.getLoggedInFollowing}/>
                        </Col>
                    </Row>
                </div>
            )
        }
        else {
            return (
                <div className="container container mt-3">
                        <Row>
                            <h1>Profile</h1>
                            {(!this.state.loggedInFollowing.map(followUser => followUser.id).includes(this.state.user.id) &&
                              this.props.user !== undefined && 
                              this.props.user.id !== undefined &&
                              this.props.user.id.toString() !== this.state.userId)
                             &&
                            <Button className="follow-btn ml-2" onClick={() => this.userService.addFollowing(this.state.userId)
                                                    .then(userGotFollowed => {
                                                        this.setState(prevState => ({
                                                            loggedInFollowing: prevState.loggedInFollowing.concat([userGotFollowed]),
                                                            followers: prevState.followers.concat([this.props.user])
                                                        }))
                                                    })
                                            }>
                                Follow
                            </Button>}
                        </Row>
                        <form>
                            <div className="form-group row">
                                <label htmlFor="username"
                                    className="col-sm-2 col-form-label">
                                    Username </label>
                                <div className="col-sm-10">
                                    <input className="form-control"
                                        id="username"
                                        placeholder="Alice"
                                        readOnly
                                        value={this.state.user.username}/>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="role"
                                       className="col-sm-2 col-form-label">
                                    Role
                                </label>
                                <div className="col-sm-10">
                                    <select className="form-control"
                                            id="role"
                                            disabled
                                            value={this.state.user.role}>
                                        <option value='ENTHUSIAST'>Enthusiast</option>
                                        <option value='BARTENDER'>Bartender</option>
                                    </select>
                                </div>
                            </div>

                            {
                                this.state.user.role === "BARTENDER" &&
                                <div className="row mt-2 mb-4">
                                    <div className="col-5 offset-2">
                                    <span className="font-weight-bold mr-1">
                                        Status:
                                    </span>
                                        {this.state.user.verified
                                            ? "User has been verified"
                                            : "User is pending verification"}
                                    </div>
                                </div>
                            }
                            <div className="form-group row">
                                <label className="col-sm-2 col-form-label"></label>
                            </div>
                        </form>
                        <Row>
                            <Col xs={6}>
                                <CommentsPanel title='Recent Comments by this User'
                                            comments={this.state.firstFiveComments}/>
                            </Col>
                            <Col xs={6}>
                                <UserLikesPanel title='Recently liked Cocktails'
                                                cocktails={this.state.firstFiveCocktails}/>
                            </Col>
                            
                            <Col xs={6}>
                                <UserListPanel title='Following' 
                                            users={this.state.following} 
                                            following={this.state.loggedInFollowing}
                                            loggedInId={this.props.user.id}
                                            getLoggedInFollowing={this.getLoggedInFollowing}/>
                            </Col>
                            <Col xs={6}>
                                <UserListPanel title='Followers' 
                                            users={this.state.followers} 
                                            following={this.state.loggedInFollowing}
                                            loggedInId={this.props.user.id}
                                            getLoggedInFollowing={this.getLoggedInFollowing}/>
                            </Col>
                        </Row>
                    </div>
            )
        }
    }
}

export default Profile
