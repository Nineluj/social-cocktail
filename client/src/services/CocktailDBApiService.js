
export default class CocktailDBApiService {
    constructor() {
        this.searchCocktailUrl = 'https://www.thecocktaildb.com/api/json/v1/1/search.php?s=SEARCH_TERM';
        this.getCocktailDetailsByIdUrl = 'https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=COCKTAIL_ID';
    }

    static myInstance = null;    
    static getInstance() {
        if (CocktailDBApiService.myInstance === null) {
            CocktailDBApiService.myInstance = new CocktailDBApiService();
        }
        return CocktailDBApiService.myInstance
    }

    searchCocktail = (searchTerm) => {
        return fetch(this.searchCocktailUrl.replace('SEARCH_TERM', searchTerm))
        .then(response => response.json())
    }

    getCocktailDetailsById = (id) => {
        return fetch(this.getCocktailDetailsByIdUrl.replace('COCKTAIL_ID', id))
        .then(response => response.json())
    }
}