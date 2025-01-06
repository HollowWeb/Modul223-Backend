package org.example.modul223backend.Article;

import org.example.modul223backend.Article.ArticleDTO;
import org.example.modul223backend.Article.Article;
import org.example.modul223backend.Article.Article.Status;
import org.example.modul223backend.Article.ArticleRepository;
import org.example.modul223backend.User.User;
import org.example.modul223backend.User.UserRepository;
import org.example.modul223backend.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ArticleDTO createArticle(ArticleDTO articleDTO) {
        User user = userRepository.findById(articleDTO.getCreatedById())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Article article = Mapper.mapToArticleEntity(articleDTO, user);
        article = articleRepository.save(article);
        return Mapper.mapToArticleDTO(article);
    }

    @Override
    public ArticleDTO updateArticle(Long id, ArticleDTO articleDTO) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setStatus(Status.valueOf(articleDTO.getStatus()));
        articleRepository.save(article);

        return Mapper.mapToArticleDTO(article);
    }

    @Override
    public void deleteArticle(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new RuntimeException("Article not found");
        }
        articleRepository.deleteById(id);
    }

    @Override
    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        return Mapper.mapToArticleDTO(article);
    }

    @Override
    public List<ArticleDTO> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(Mapper::mapToArticleDTO).collect(Collectors.toList());
    }

    @Override
    public List<ArticleDTO> getArticlesByStatus(String status) {
        List<Article> articles = articleRepository.findByStatus(Status.valueOf(status));
        return articles.stream().map(Mapper::mapToArticleDTO).collect(Collectors.toList());
    }
}
