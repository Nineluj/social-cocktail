import sha256 from 'crypto-js/sha256';
import {API_ROOT} from '../api-config';

export default class UserService {
    constructor() {
        this.authenticateUserUrl = `${API_ROOT}/users/login`;
        this.registerUserUrl = `${API_ROOT}/users/register`;
        this.registerBartenderUrl = `${API_ROOT}/users/bartender`;
        this.getLoggedInUserUrl = `${API_ROOT}/user`
        this.logoutUserUrl = `${API_ROOT}/user/logout`
        this.updateUserUrl = `${API_ROOT}/user`
        this.findUserByIdUrl = `${API_ROOT}/users/USER_ID`
        this.addLikedCocktailUrl = `${API_ROOT}/user/likes/cocktail/COCKTAIL_ID`
        this.getFollowingUrl = `${API_ROOT}/user/following`
        this.getFollowersUrl = `${API_ROOT}/user/followers`
        this.getFollowingByIdUrl = `${API_ROOT}/user/following/USER_ID`
        this.getFollowersByIdUrl = `${API_ROOT}/user/followers/USER_ID`
        this.addFollowingUrl = `${API_ROOT}/user/following/USER_ID`
        this.getLikedCocktailsUrl = `${API_ROOT}/users/USER_ID/likes/NUM_LIKES`
    }

    static myInstance = null;    
    static getInstance() {
        if (UserService.myInstance === null) {
            UserService.myInstance = new UserService();
        }
        return UserService.myInstance
    }

    authenticateUser = (user) => {
        user.password = sha256(user.password).toString()
        return fetch(this.authenticateUserUrl, {
            method: 'POST',
            body: JSON.stringify(user),
            credentials: 'include',
            headers: {
                'content-type': 'application/json'
            }
        })
    }

    registerUser = (user) => {
        user.password = sha256(user.password).toString()
        return fetch(this.registerUserUrl, {
            method: 'POST',
            body: JSON.stringify(user),
            credentials: 'include',
            headers: {
                'content-type': 'application/json'
            }
        })
    }

    registerBartender = (user) => {
        user.password = sha256(user.password).toString()
        return fetch(this.registerBartenderUrl, {
            method: 'POST',
            body: JSON.stringify(user),
            credentials: 'include',
            headers: {
                'content-type': 'application/json'
            }
        })
    };

    getLoggedInUser = () => {
        return fetch(this.getLoggedInUserUrl, {
            method: 'GET',
            credentials: 'include'
        }).then(response => {
            if (response.status === 200) {
                return response.json()
            }
            return {}
        })
    }

    logoutUser = () => {
        return fetch(this.logoutUserUrl, {
            method: 'GET',
            credentials: 'include'
        })
    }

    updateUser = (user) => {
        return fetch(this.updateUserUrl, {
            method: 'PUT',
            credentials: 'include',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        }).then(response => response.json())
    }

    findUserById = (id) => {
        return fetch(this.findUserByIdUrl.replace('USER_ID', id), {
            method: 'GET',
            credentials: 'include'
        }).then(response => {
            if (response.status === 200) {
                return response.json()
            }
            return undefined
        })
    }

    getFollowers = () => {
        return fetch(this.getFollowersUrl, {
            method: 'GET',
            credentials: 'include'
        }).then(response => response.json())
    }

    getFollowing = () => {
        return fetch(this.getFollowingUrl, {
            method: 'GET',
            credentials: 'include'
        }).then(response => response.json())
    }

    getFollowersById = (userId) => {
        return fetch(this.getFollowersByIdUrl.replace('USER_ID', userId), {
            method: 'GET',
            credentials: 'include'
        }).then(response => response.json())
    }

    getFollowingById = (userId) => {
        return fetch(this.getFollowingByIdUrl.replace('USER_ID', userId), {
            method: 'GET',
            credentials: 'include'
        }).then(response => response.json())
    }

    addFollowing = (userId) => {
        return fetch(this.addFollowingUrl.replace('USER_ID', userId), {
            method: 'POST',
            credentials: 'include'
        }).then(response => response.json())
    }

    getLikedCocktails = (userId, numLikes) => {
        return fetch(this.getLikedCocktailsUrl.replace('USER_ID', userId).replace('NUM_LIKES', numLikes), {
            method: 'GET',
            credentials: 'include'
        }).then(response => response.json())
    }
}
