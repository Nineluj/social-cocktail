import React from 'react';
import { Link } from 'react-router-dom';
import { Nav, Navbar } from 'react-bootstrap';
import UserService from '../services/UserService';
import './NavHeader.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

let userService = UserService.getInstance();

const NavHeader = ({ user }) => (
  <Navbar expand="lg" className="main-navbar">
    <Navbar.Brand as={Link} to="/">
      <h1>Social Cocktail</h1>
    </Navbar.Brand>
    <Navbar.Toggle />
    <Navbar.Collapse>
      <Nav className="mr-auto">
        <Nav.Item>
          <Nav.Link as={Link} to="/">
            <h4>Home</h4>
          </Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link as={Link} to="/search">
            <h4>Search</h4>
          </Nav.Link>
        </Nav.Item>
        {user.id === undefined && (
          <Nav.Item>
            <Nav.Link as={Link} to="/login">
              <h4>Login</h4>
            </Nav.Link>
          </Nav.Item>
        )}
        {user.id === undefined && (
          <Nav.Item>
            <Nav.Link as={Link} to="/register">
              <h4>Register</h4>
            </Nav.Link>
          </Nav.Item>
        )}
      </Nav>
    </Navbar.Collapse>
    {user.id !== undefined && (
      <Navbar.Collapse className="justify-content-end">
        {user.isAdmin && (
          <Nav.Item>
            <Nav.Link as={Link} to="/admin">
              <FontAwesomeIcon
                icon="users-cog"
                size="lg"
                className="align-middle text-dark"
              />
              <span className="ml-2">Manage Requests</span>
            </Nav.Link>
          </Nav.Item>
        )}
        <Nav.Item>
          <Nav.Link as={Link} to="/profile">
            <FontAwesomeIcon
              icon="user"
              size="lg"
              className="align-middle text-dark"
            />
            <span className="ml-2">{user.username}</span>
          </Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link
            as={Link}
            to="/"
            onClick={() =>
              userService
                .logoutUser()
                .then((response) =>
                  response.status === 200
                    ? window.location.reload()
                    : alert('Could not log out.')
                )
            }
          >
            <FontAwesomeIcon
              icon="sign-out-alt"
              size="lg"
              className="align-middle text-dark"
            />
          </Nav.Link>
        </Nav.Item>
      </Navbar.Collapse>
    )}
  </Navbar>
);

export default NavHeader;
