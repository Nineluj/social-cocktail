import React from 'react';
import UserService from '../../services/UserService';
import CommentsPanel from '../CommentsPanel';
import { Col, Container, Row } from 'react-bootstrap';
import HomeWelcomePanel from './HomeWelcomePanel';
import CommentService from '../../services/CommentService';

class Home extends React.Component {
  constructor(props) {
    super(props);
    this.userService = UserService.getInstance();
    this.commentService = CommentService.getInstance();
    this.state = {
      recentComments: [],
      followingComments: [],
      yourComments: [],
    };
    if (this.props.user.id === undefined) {
      this.commentService.getRecentComments(3).then((comments) =>
          this.setState({
            recentComments: comments,
          })
      );
    } else {
      this.commentService.getFollowingComments(3).then((comments) =>
          this.setState({
            followingComments: comments,
          })
      );
      this.commentService.getComments(3).then((comments) =>
          this.setState({
            yourComments: comments,
          })
      );
    }
  }

  // Needed for logged in homepage to populate on instantiation and refresh
  componentDidMount() {
    if (this.props.user.id !== undefined) {
      this.commentService.getFollowingComments(3).then((comments) =>
          this.setState({
            followingComments: comments,
          })
      );
      this.commentService.getComments(3).then((comments) =>
          this.setState({
            yourComments: comments,
          })
      );
    }
  }

  componentDidUpdate(prevProps) {
    if (this.props.user.id !== prevProps.user.id) {
      this.commentService.getFollowingComments(3)
          .then(comments => this.setState({
            followingComments: comments
          }))
      this.commentService.getComments(3)
          .then(comments => this.setState({
            yourComments: comments
          }))
    }
  }

  render() {
    return (
        <Container fluid className="dark-background full-height">
          <div className="home-main-div row">
            <HomeWelcomePanel username={this.props.user.username} />
            {/*// Below, for any comments panel, make the API call to get the*/}
            {/*// desired comments, and return a <CommentsPanel/> during the*/}
            {/*// .then after the promise.*/}
            {this.props.user.id === undefined && (
                <Row className="container light-background info-panel">
                  <Col xs={12} md={6}>
                    <CommentsPanel
                        title="Recent Comments on our Platform"
                        comments={this.state.recentComments}
                    />
                  </Col>
                </Row>
            )}
            {this.props.user.id !== undefined && (
                <Row className="container light-background info-panel">
                  <Col xs={12} md={6}>
                    <CommentsPanel
                        title="Your Friends's Activity"
                        comments={this.state.followingComments}
                    />
                  </Col>
                  <Col xs={12} md={6}>
                    <CommentsPanel
                        title="Your Recent Activity"
                        comments={this.state.yourComments}
                    />
                  </Col>
                </Row>
            )}
          </div>
        </Container>
    );
  }
}

export default Home;
