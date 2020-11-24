import BaseService from "./BaseService";

export default class CommentService extends BaseService {
    static myInstance = null;    
    static getInstance() {
        if (CommentService.myInstance === null) {
            CommentService.myInstance = new CommentService();
        }
        return CommentService.myInstance
    }

    getRecentComments = numPosts => this.findAllItems(`/comments/recent/${numPosts}`);

    getFollowingComments = numPosts => this.findAllItems(`/comments/following/${numPosts}`);

    getComments = numPosts => this.findAllItems(`/comments/${numPosts}`);

    getCommentsByUserId = (userId, numPosts) => this.findAllItems(`/user/${userId}/comments/${numPosts}`)

    createComment = (comment, cocktailId) => this.createItem(`/cocktail/${cocktailId}/comments`, comment);

    findCommentsByCocktailId = (cocktailId) => this.findById(`/cocktail/${cocktailId}/comments`);

    deleteCommentById = (commentId) => this.deleteItem(`/comments/${commentId}`);
}
