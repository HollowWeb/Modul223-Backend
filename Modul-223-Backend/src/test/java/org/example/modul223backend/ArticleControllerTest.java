package org.example.modul223backend;

import org.example.modul223backend.Article.ArticleController;
import org.example.modul223backend.Article.ArticleService;
import org.example.modul223backend.Article.DTO.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticleControllerTest {

    @InjectMocks
    private ArticleController articleController;

    @Mock
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createArticle_ShouldReturnCreatedArticle() {
        ArticleCreateDTO articleCreateDTO = new ArticleCreateDTO("New Article", "This is the content", Set.of("Tech", "Java"));
        ArticleDTO mockArticle = new ArticleDTO(1L, "New Article", "This is the content", "DRAFT", Set.of("Tech", "Java"), LocalDateTime.now(), LocalDateTime.now(), "testUser");

        when(articleService.createArticle(articleCreateDTO)).thenReturn(mockArticle);

        ResponseEntity<ArticleDTO> response = articleController.createArticle(articleCreateDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("New Article", response.getBody().getTitle());
        assertEquals(Set.of("Tech", "Java"), response.getBody().getTags());

        verify(articleService, times(1)).createArticle(articleCreateDTO);
    }

    @Test
    void getArticleById_ShouldReturnArticle() {
        ArticleDTO mockArticle = new ArticleDTO(1L, "Test Article", "Some Content", "PUBLISHED", Set.of("Tech"), LocalDateTime.now(), LocalDateTime.now(), "testUser");

        when(articleService.getArticleById(1L)).thenReturn(mockArticle);

        ResponseEntity<ArticleDTO> response = articleController.getArticleById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Test Article", response.getBody().getTitle());
        assertEquals("PUBLISHED", response.getBody().getStatus());
        assertEquals(Set.of("Tech"), response.getBody().getTags());

        verify(articleService, times(1)).getArticleById(1L);
    }

    @Test
    void updateArticle_ShouldReturnUpdatedArticle() {
        ArticleUpdateDTO updateDTO = new ArticleUpdateDTO("Updated Title", "Updated Content", "PUBLISHED", Set.of("UpdatedTag"));
        ArticleDTO updatedArticle = new ArticleDTO(1L, "Updated Title", "Updated Content", "PUBLISHED", Set.of("UpdatedTag"), LocalDateTime.now(), LocalDateTime.now(), "testUser");

        when(articleService.updateArticle(eq(1L), any(ArticleUpdateDTO.class))).thenReturn(updatedArticle);

        ResponseEntity<ArticleDTO> response = articleController.updateArticle(1L, updateDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Updated Title", response.getBody().getTitle());
        assertEquals(Set.of("UpdatedTag"), response.getBody().getTags());

        verify(articleService, times(1)).updateArticle(1L, updateDTO);
    }

    @Test
    void deleteArticle_ShouldReturnSuccessMessage() {
        doNothing().when(articleService).deleteArticle(1L);

        ResponseEntity<String> response = articleController.deleteArticle(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Article deleted successfully.", response.getBody());

        verify(articleService, times(1)).deleteArticle(1L);
    }

    @Test
    void listArticles_ShouldReturnEmptyList() {
        when(articleService.listArticles(null, null, null)).thenReturn(Collections.emptyList());

        ResponseEntity<List<ArticleDTO>> response = articleController.listArticles(null, null, null);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());

        verify(articleService, times(1)).listArticles(null, null, null);
    }

    @Test
    void requestApproval_ShouldUpdateStatusToPendingApproval() {
        ArticleUpdateDTO updateDTO = new ArticleUpdateDTO(null, null, "PENDING_APPROVAL", null);
        ArticleDTO updatedArticle = new ArticleDTO(1L, "Test Article", "Content", "PENDING_APPROVAL", Set.of("Tech"), LocalDateTime.now(), LocalDateTime.now(), "testUser");

        when(articleService.updateArticle(eq(1L), any(ArticleUpdateDTO.class))).thenReturn(updatedArticle);

        ResponseEntity<ArticleDTO> response = articleController.requestApproval(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("PENDING_APPROVAL", response.getBody().getStatus());

        verify(articleService, times(1)).updateArticle(1L, updateDTO);
    }

    @Test
    void approveArticle_ShouldUpdateStatusToPublished() {
        ArticleDTO approvedArticle = new ArticleDTO(1L, "Approved Article", "Content", "PUBLISHED", Set.of("Tech"), LocalDateTime.now(), LocalDateTime.now(), "adminUser");

        when(articleService.approveArticle(1L)).thenReturn(approvedArticle);

        ResponseEntity<ArticleDTO> response = articleController.approveArticle(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("PUBLISHED", response.getBody().getStatus());

        verify(articleService, times(1)).approveArticle(1L);
    }

    @Test
    void getArticlesByUserId_ShouldReturnUserArticles() {
        List<ArticleDTO> articles = List.of(
                new ArticleDTO(1L, "User Article 1", "Content", "DRAFT", Set.of("Java"), LocalDateTime.now(), LocalDateTime.now(), "testUser")
        );

        when(articleService.getArticlesByUserId(1L)).thenReturn(articles);

        ResponseEntity<List<ArticleDTO>> response = articleController.getArticlesByUserId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("User Article 1", response.getBody().get(0).getTitle());

        verify(articleService, times(1)).getArticlesByUserId(1L);
    }

    @Test
    void getPendingArticles_ShouldReturnPendingArticles() {
        List<ArticleDTO> pendingArticles = List.of(
                new ArticleDTO(1L, "Pending Article", "Content", "PENDING_APPROVAL", Set.of("Review"), LocalDateTime.now(), LocalDateTime.now(), "editorUser")
        );

        when(articleService.getPendingArticles()).thenReturn(pendingArticles);

        ResponseEntity<List<ArticleDTO>> response = articleController.getPendingArticles();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("PENDING_APPROVAL", response.getBody().get(0).getStatus());

        verify(articleService, times(1)).getPendingArticles();
    }

    @Test
    void denyArticle_ShouldReturnHttpStatusOk() {
        doNothing().when(articleService).denyArticle(1L);

        assertEquals(200, articleController.denyArticle(1L).value());
        verify(articleService, times(1)).denyArticle(1L);
    }

    @Test
    void getAllArticles_ShouldReturnListOfArticles() {
        List<ArticleDTO> articles = List.of(
                new ArticleDTO(1L, "Article 1", "Content", "PUBLISHED", Set.of("Tech"), LocalDateTime.now(), LocalDateTime.now(), "adminUser")
        );

        when(articleService.getAllArticles()).thenReturn(articles);

        ResponseEntity<List<ArticleDTO>> response = articleController.getAllArticles();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        verify(articleService, times(1)).getAllArticles();
    }
}