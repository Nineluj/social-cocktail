import React from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {Col, ListGroup, Row} from "react-bootstrap";

const BartenderRequest = ({username, barName, supervisorName, supervisorPhone, id, verifyBartender}) => (
    <ListGroup.Item>
        <Row>
        <Col xs={10} className="text-left font-weight-bold">
            Employee <i>{username}</i> who works for <i>{supervisorName}</i> (Phone: {supervisorPhone}) at bar {barName}
        </Col>

        <Col xs={2} className="text-right">
            <FontAwesomeIcon icon="check-circle" size="lg" onClick={() => verifyBartender(id)}/>
        </Col>
        </Row>
    </ListGroup.Item>);

export default BartenderRequest;
