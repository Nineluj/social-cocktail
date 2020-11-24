import React from 'react';
import { Col, Container, Form, Row } from 'react-bootstrap';
import CocktailItem from './CocktailItem';
import Image from 'react-bootstrap/Image';
import { Link } from 'react-router-dom';
import CocktailDBApiService from '../../services/CocktailDBApiService';
import { Redirect } from 'react-router';

class Search extends React.Component {
  constructor(props) {
    super(props);
    this.isComponentMounted = true;
    this.cocktailDBApiService = CocktailDBApiService.getInstance();

    if (this.props.searchCriteria !== undefined) {
      this.state = {
        cocktailSearchText: this.props.searchCriteria,
        shownCocktails: [],
        rerouteActive: false,
        isComponentMounted: true,
        hasSearched: false
      };
      this.searchCocktail();
    } else {
      this.state = {
        cocktailSearchText: '',
        shownCocktails: [],
        rerouteActive: false,
        hasSearched: false
      };
    }
  }

  componentWillMount() {
    this.isComponentMounted = true;
  }

  componentWillUnmount() {
    this.isComponentMounted = false;
  }

  updateResults = (results) => {
    this.setState({
      shownCocktails: results.drinks,
      hasSearched: true
    });
  };

  searchCocktail = () => {
    this.cocktailDBApiService
        .searchCocktail(this.state.cocktailSearchText)
        .then(
            (cocktails) => this.isComponentMounted && this.updateResults(cocktails)
        );
  };

  render() {
    if (this.state.rerouteActive) {
      return <Redirect to={`/search/${this.state.cocktailSearchText}`} />;
    }
    let { cocktailSearchText, shownCocktails, hasSearched } = this.state;

    return (
        <div className="dark-background full-height demo">
          <div className="demo-main-div">
            <Row>
              <img
                  className="logo"
                  height="120px"
                  width="120px"
                  src={require('../../logo.svg')}
              />
              <Form className="search-form">
                <Form.Group controlId="exampleForm.ControlInput1">
                  <h2 className="demo-main-title">Cocktail Search</h2>
                  <Form.Label>Lookup your favorite cocktail</Form.Label>
                  <Row>
                    <Col xs={8} md={9}>
                      <Form.Control
                          type="text"
                          placeholder="Margarita..."
                          value={cocktailSearchText}
                          onChange={(event) =>
                              this.setState({
                                cocktailSearchText: event.target.value,
                              })
                          }
                      />
                    </Col>
                    <Col xs={3}>
                      <Link
                          to={`/search/${cocktailSearchText}`}
                          onClick={this.searchCocktail}
                      >
                        <button className="btn btn-success" type="button">
                          Search
                        </button>
                      </Link>
                    </Col>
                  </Row>
                </Form.Group>
              </Form>
            </Row>
            <Row>
              {shownCocktails !== null && shownCocktails.length > 0 &&
              <Col xs={12} className="demo-results">
                {
                  shownCocktails.map((cocktail) => (
                      <CocktailItem key={cocktail.idDrink} data={cocktail} />
                  ))}
              </Col>
              }
              {(shownCocktails === null || shownCocktails.length === 0) && hasSearched &&
              <Col xs={12} className="mt-2">
                <h5>Womp Womp. No matches found.</h5>
              </Col>
              }
              {(shownCocktails === null || shownCocktails.length === 0) && !hasSearched &&
                <h5>Enter a query to explore our world of cocktails!</h5>
              }
            </Row>
          </div>
        </div>
    );
  }
}

export default Search;
