import React from 'react';
import Card from 'react-bootstrap/Card';
import { Button } from 'react-bootstrap';
import CommentService from '../services/CommentService';

let commentService = CommentService.getInstance();

const Comment = ({
  title,
  date,
  id,
  content,
  author,
  cocktail,
  hideCocktailLink,
  isPrivateProfile,
  getRecentComments
}) => (
  <Card className="mx-auto comment">
    <div className="comment-info">
      <div>
        <a href={`/profile/${author.id}`}>{author.username}</a>'s comment&nbsp;
        {!hideCocktailLink && (
          <span>
            on:
            <a className="comment-cocktail" href={`/details/${cocktail.id}`}>
              {cocktail.name}
            </a>
          </span>
        )}
      </div>
      <div className="text-muted">{date.slice(0, 10)}</div>
    </div>
    <div className="comment-content">
      <div className="comment-title">{title}</div>
      <div className="comment-body">{content}</div>
    </div>
    {isPrivateProfile &&
        <Button variant={'danger'}
                onClick={() => {
                    commentService.deleteCommentById(id)
                    .then(response => {
                        if (response.status === 200) {
                            getRecentComments()
                        }
                    })
                }}>Delete</Button>
    }
  </Card>
);

export default Comment;
