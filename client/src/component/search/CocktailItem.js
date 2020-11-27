import React from 'react';
import { Card } from 'react-bootstrap';
import './CocktailItem.scss';
import { Link } from 'react-router-dom';

const CocktailItem = (props) => (
  <Link className="cocktail-card-link" to={`/details/${props.data.idDrink}`}>
    <Card className="cocktail-card">
      <Card.Img
        variant="top"
        style={{ backgroundImage: `url(${props.data.strDrinkThumb})` }}
      />
      <Card.Body className="cocktail-card-body">
        <div className="">
          <Card.Title>{props.data.strDrink}</Card.Title>
        </div>
        <div>Category: {props.data.strCategory}</div>
        <div>Glass Type: {props.data.strGlass}</div>
      </Card.Body>
    </Card>
  </Link>
);

export default CocktailItem;
