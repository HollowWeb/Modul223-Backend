package org.example.modul223backend.exception.Article;

/**
 * Shows the specified exception if an error happens.
 */
public class InvalidArticleDataException extends RuntimeException {

    public InvalidArticleDataException(String message) {
        super(message);
    }
}