package org.example.modul223backend.exception.Article;

/**
 * Shows the specified exception if an error happens.
 */
public class ArticleNotFoundException extends RuntimeException {

    public ArticleNotFoundException(String message) {
        super(message);
    }
}
