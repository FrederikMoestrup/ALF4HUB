package US7.controllers;



import dat.config.HibernateConfig;
import dat.controllers.BlogController;
import dat.daos.BlogPostDAO;
import dat.dtos.BlogPostDTO;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.mockito.Mockito;

import jakarta.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlogControllerTest {

    private BlogController blogController;
    private Context ctx;
    private HttpServletResponse httpServletResponse;
    private static EntityManagerFactory emf;

    @BeforeAll
    static void setupClass() {
        HibernateConfig.setTest(true);
        emf = HibernateConfig.getEntityManagerFactoryForTest();
    }

    @AfterAll
    static void tearDownClass() {
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    void setUp() {
        assertTrue(HibernateConfig.getTest());

        blogController = new BlogController();

        ctx = Mockito.mock(Context.class);
        httpServletResponse = Mockito.mock(HttpServletResponse.class);
        when(ctx.res()).thenReturn(httpServletResponse);
    }
    @Test
    void updateMissingTitle() {
        BlogPostDTO inputPost = new BlogPostDTO();
        inputPost.setContent("Updated Content");

        when(ctx.pathParam("id")).thenReturn("1");
        when(ctx.bodyAsClass(BlogPostDTO.class)).thenReturn(inputPost);
        when(ctx.status(anyInt())).thenReturn(ctx);
        when(ctx.result(anyString())).thenReturn(ctx);

        ApiException exception = assertThrows(ApiException.class, () -> {
            blogController.update(ctx);
        });

        assertEquals(400, exception.getStatusCode());
        assertEquals("Der mangler nødvendige felter: titel og/eller indhold", exception.getMessage());
    }

    @Test
    void updateMissingContent() {
        BlogPostDTO inputPost = new BlogPostDTO();
        inputPost.setTitle("Updated Title");

        when(ctx.pathParam("id")).thenReturn("1");
        when(ctx.bodyAsClass(BlogPostDTO.class)).thenReturn(inputPost);
        when(ctx.status(anyInt())).thenReturn(ctx);
        when(ctx.result(anyString())).thenReturn(ctx);

        ApiException exception = assertThrows(ApiException.class, () -> {
            blogController.update(ctx);
        });

        assertEquals(400, exception.getStatusCode());
        assertEquals("Der mangler nødvendige felter: titel og/eller indhold", exception.getMessage());
    }

    @Test
    void deleteBlogPost() throws ApiException {
        BlogPostDTO inputPost = new BlogPostDTO();
        inputPost.setTitle("Test Title");
        inputPost.setContent("Test Content");

        when(ctx.pathParam("id")).thenReturn("1");
        when(ctx.bodyAsClass(BlogPostDTO.class)).thenReturn(inputPost);
        when(ctx.status(anyInt())).thenReturn(ctx);
        when(ctx.result(anyString())).thenReturn(ctx);

        blogController.delete(ctx);

        assertEquals(null, ctx.status());

        verify(ctx).status(404);
    }

}
