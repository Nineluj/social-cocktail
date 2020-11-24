import React from 'react'
import {Redirect} from 'react-router'
import {Container} from "react-bootstrap";
import ListGroup from "react-bootstrap/ListGroup";
import BartenderRequest from "./BartenderRequest";
import AdminService from "../../services/AdminService";

class AdminDashboard extends React.Component {
    constructor(props) {
        super(props);

        this.adminService = AdminService.getInstance();
        this.fetchingData = false;
        this.state = {
            requests: [],
        }

        this.adminService.getBartenderRequests().then(requests => this.setState({ requests: requests }))
    }

    componentDidUpdate(prevProps) {
        if (prevProps.user.id !== this.props.user.id) {
            this.fetchingData = true;

            this.adminService.getBartenderRequests().then(requests => this.setState({ requests: requests }))
        }
    }

    verifyBartender = (uid) =>
        this.adminService.verifyBartenderRequest(uid)
            .then(response => response.status === 200 ?
                this.setState(prevState => ({ requests: prevState.requests.filter(req => req.id !== uid) }))
                : alert(`Error approving user with id ${uid}`));

    render() {
        if (!this.props.user.isAdmin) {
            return (
                <Redirect to='/'/>
            )
        }

        let { requests } = this.state;

        return (
            <Container className="mt-3">
                <h2>Review the bartender requests</h2>
                {requests.length > 0 &&
                <ListGroup>
                    {requests.map(req => <BartenderRequest {...req} key={req.id} verifyBartender={this.verifyBartender}/>) }
                </ListGroup>
                }
                {requests.length === 0 &&
                <h4>No pending requests, come back later!</h4>
                }
            </Container>
        )
    }
}

export default AdminDashboard;
