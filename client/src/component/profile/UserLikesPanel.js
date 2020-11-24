import React from 'react'
import {Link} from 'react-router-dom'

const UserLikesPanel = (props) =>
    <div>
        <h3>{props.title}</h3>
        {// Here, hit API endpoint to get most recent
            //  comments, and .map(comment => <Comment/>)
        }
        {props.cocktails !== undefined &&
        <ul>
            {props.cocktails.map(cocktail => 
                <li>
                    <h4>
                        <Link to={`/details/${cocktail.id}`}>
                            {cocktail.name}
                        </Link>
                    </h4>
                </li>)}
        </ul>}
    </div>;

export default UserLikesPanel
