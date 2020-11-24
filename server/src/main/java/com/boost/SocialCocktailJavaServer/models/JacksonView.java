package com.boost.SocialCocktailJavaServer.models;

public class JacksonView {
    interface freeContext {}
    interface withCommentContext {}
    interface withCocktailContext {}
    interface withUserContext {}
    interface withTipContext {}

    public interface forCommentRequest extends freeContext, withCocktailContext, withUserContext, withTipContext {}
    public interface forCocktailRequest extends freeContext, withCommentContext, withUserContext, withTipContext {}
    public interface forUserRequest extends freeContext, withCocktailContext, withCommentContext, withTipContext {}
    public interface forTipRequest extends freeContext, withCocktailContext, withCommentContext, withUserContext {}
}
