package US3.dat.controllers;


import dat.config.HibernateConfig;
import dat.controllers.BlogController;
import dat.daos.BlogPostDAO;
import dat.dtos.BlogPostDTO;
import dat.enums.BlogPostStatus;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.mockito.Mockito;

import jakarta.persistence.EntityManagerFactory;

import java.util.List;

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
    void createMissingTitle() {
        BlogPostDTO inputPost = new BlogPostDTO();
        inputPost.setContent("Test Content");

        when(ctx.bodyAsClass(BlogPostDTO.class)).thenReturn(inputPost);

        ApiException exception = assertThrows(ApiException.class, () -> {
            blogController.create(ctx);
        });

        assertEquals(400, exception.getStatusCode());
        assertEquals("Missing required fields: title or content", exception.getMessage());
    }

    @Test
    void createMissingContent() {
        BlogPostDTO inputPost = new BlogPostDTO();
        inputPost.setTitle("Test Title");

        when(ctx.bodyAsClass(BlogPostDTO.class)).thenReturn(inputPost);

        ApiException exception = assertThrows(ApiException.class, () -> {
            blogController.create(ctx);
        });

        assertEquals(400, exception.getStatusCode());
        assertEquals("Missing required fields: title or content", exception.getMessage());
    }

    @Test
    void getAllEmptyPosts() {
        BlogPostDAO mockDAO = mock(BlogPostDAO.class);
        when(mockDAO.getAll()).thenReturn(null);

        BlogController controller = new BlogController();

        Context mockCtx = mock(Context.class);
        when(mockCtx.status(200)).thenReturn(mockCtx);
        when(mockCtx.status(500)).thenReturn(mockCtx);

        controller.getAll(mockCtx);

        verify(mockCtx).status(200);
        verify(mockCtx).result("No blog posts found");
    }


    @Test
    void getAllEmptyPreview() {
        BlogPostDAO mockDAO = mock(BlogPostDAO.class);
        when(mockDAO.getAllWithOnlyContentPreview()).thenReturn(null);

        BlogController controller = new BlogController();

        Context mockCtx = mock(Context.class);
        when(mockCtx.status(200)).thenReturn(mockCtx);
        when(mockCtx.status(500)).thenReturn(mockCtx);

        controller.getAllPreview(mockCtx);

        verify(mockCtx).status(200);
        verify(mockCtx).result("No blog posts with content preview found");
    }


    @Test
    void getByIdNonExistentPost() {
        BlogPostDAO mockDAO = mock(BlogPostDAO.class);
        when(mockDAO.getById(anyLong())).thenReturn(null);


        when(ctx.pathParam("id")).thenReturn("999");

        ApiException exception = assertThrows(ApiException.class, () -> {
            blogController.getById(ctx);
        });

        assertEquals(404, exception.getStatusCode());
        assertEquals("BlogPost not found", exception.getMessage());
    }

}