package org.example.modul223backend;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.modul223backend.Article.Article;
import org.example.modul223backend.Article.DTO.ArticleDTO;
import org.example.modul223backend.Article.ArticleRepository;
import org.example.modul223backend.Article.ArticleServiceImpl;
import org.example.modul223backend.User.User;
import org.example.modul223backend.User.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateArticleWitMockUser() {
        // Mock UserRepository to return a test user
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("TestUser");
        mockUser.setEmail("test@example.com");
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(mockUser));

        // Prepare test ArticleDTO
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setTitle("Test Article");
        articleDTO.setContent("This is a test article");
        articleDTO.setStatus("DRAFT");
        articleDTO.setCreatedById(1L);

        // Mock ArticleRepository to return the saved article
        Article mockArticle = new Article();
        mockArticle.setArticleId(1L);
        mockArticle.setTitle("Test Article");
        mockArticle.setContent("This is a test article");
        mockArticle.setStatus(Article.Status.DRAFT);
        mockArticle.setCreatedBy(mockUser);
        mockArticle.setCreatedAt(LocalDateTime.now());
        mockArticle.setUpdatedAt(LocalDateTime.now());
        when(articleRepository.save(any(Article.class))).thenReturn(mockArticle);

        // Call the service method
        ArticleDTO createdArticle = articleService.createArticle(articleDTO);

        // Assertions
        assertNotNull(createdArticle);
        assertEquals("Test Article", createdArticle.getTitle());
        assertEquals("This is a test article", createdArticle.getContent());
        assertEquals("DRAFT", createdArticle.getStatus());
        assertEquals(1L, createdArticle.getCreatedById());
    }
}
