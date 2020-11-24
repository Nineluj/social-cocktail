import React from 'react';
import { Col } from 'react-bootstrap';

const HomeWelcomePanel = ({ username }) => (
  <Col xs={12} className="mb-3">
    <div className="">
      <h2 className="">
        Welcome to your Homepage,{' '}
        {username !== undefined ? username : 'Guest User'}!
      </h2>
      {username !== undefined && (
        <div className="card-text">
          Below, you will find your activity feeds. <br />
          If you would like to search for a drink, navigate to the Search page
          by clicking the above link. <br />
          To view your personal profile, navigate to the Profile page by
          clicking the above link.
        </div>
      )}
      {username === undefined && (
        <div className="card-text">
          <h6>
            Below, you will see recent comments. If you login, you can see
            comments from the people you follow, and view your most recent
            activity.
          </h6>
        </div>
      )}
    </div>
  </Col>
);

export default HomeWelcomePanel;
