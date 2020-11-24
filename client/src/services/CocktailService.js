import BaseService from "./BaseService";

export default class CocktailService extends BaseService {
    static myInstance = null;    
    static getInstance() {
        if (CocktailService.myInstance === null) {
            CocktailService.myInstance = new CocktailService();
        }
        return CocktailService.myInstance
    }

    // Returns status of creating a cocktail
    createCocktail = (cocktail, glassType) => this.createItem(`/cocktails?glassType=${glassType}`, cocktail);

    // {
    //     return fetch(encodeURI(this.createCocktailUrl.replace('GLASS_TYPE', glassType)), {
    //         method: 'POST',
    //         body: JSON.stringify(cocktail),
    //         credentials: 'include',
    //         headers: {
    //             'content-type': 'application/json'
    //         }
    //     }).then(response => response.json())
    // }

    findCocktailById = id => this.findById(`/cocktails/${id}`);

    findUserLikes = id => this.findById(`/cocktails/${id}/likes`);

    likeCocktail = id => this.postNoResponse(`/cocktails/${id}/likes`);

    addTip = (id, tip) => this.createItem(`/cocktails/${id}/addTip`, tip)
}
