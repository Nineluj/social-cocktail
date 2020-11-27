import React from 'react'
import {Card} from 'react-bootstrap';
import {Link} from 'react-router-dom'

const UserListPanel = ({title, users, following, loggedInId, getLoggedInFollowing}) =>
    <Card>
        <Card.Body>
            <Card.Title>
                <h3>
                    {title}
                </h3>
            </Card.Title>
            <ul>
                {users.map(user => 
                    <div>
                        <li>
                            <Link to={`/profile/${user.id}`}>
                                <span>
                                    {user.username}
                                </span>
                            </Link>
                        </li>
                    </div>
                )}
            </ul>
        </Card.Body>
    </Card>

export default UserListPanel
