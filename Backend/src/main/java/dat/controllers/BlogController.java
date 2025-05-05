package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.BlogPostDAO;
import dat.daos.TeamDAO;
import dat.dtos.BlogPostDTO;
import dat.dtos.PlayerAccountDTO;
import dat.dtos.TeamDTO;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.sql.SQLException;
import java.util.List;

public class BlogController {

    private final BlogPostDAO blogPostDAO;

    public BlogController() {
        if  (HibernateConfig.getTest()) {
            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
            this.blogPostDAO = BlogPostDAO.getInstance(emf);
        } else {
            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("ALF4HUB_DB");
            this.blogPostDAO = BlogPostDAO.getInstance(emf);
        }
    }

    public void getAll(Context ctx) {
        try {
            List<BlogPostDTO> blogDTOs = blogPostDAO.getAll();

            if (blogDTOs == null || blogDTOs.isEmpty()) {
                ctx.status(200).result("No blog posts found");
            } else {
                ctx.status(200).json(blogDTOs, BlogPostDTO.class);
            }
        } catch (Exception e) {
            ctx.status(500).result("Internal server error: " + e.getMessage());
        }
    }

    public void getById(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            BlogPostDTO blogPostDTO = blogPostDAO.getById((long) id);
            if (blogPostDTO == null) {
                throw new ApiException(404, "BlogPost not found");
            }
            ctx.res().setStatus(200);
            ctx.json(blogPostDTO, BlogPostDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        } catch (Exception e) {
            throw new ApiException(500, "Internal server error");
        }
    }

    public void create(Context ctx) throws ApiException {
        try{
            BlogPostDTO blogPostDTO = ctx.bodyAsClass(BlogPostDTO.class);

            if (blogPostDTO == null || blogPostDTO.getTitle() == null || blogPostDTO.getContent() == null) {
                throw new ApiException(400, "Missing required fields: title or content");
            }

            BlogPostDTO createdBlogPostDTO = blogPostDAO.create(blogPostDTO);
            ctx.res().setStatus(201);
            ctx.json(createdBlogPostDTO, BlogPostDTO.class);
        } catch (Exception e) {
            ctx.status(500).result("Internal server error: " + e.getMessage());
        }
    }

    public void getAllPreview(Context ctx) {
        try {
            List<BlogPostDTO> blogDTOs = blogPostDAO.getAllWithOnlyContentPreview();
            if (blogDTOs == null || blogDTOs.isEmpty()) {
                ctx.status(200).result("No blog posts with content preview found");
            } else {
                ctx.status(200).json(blogDTOs, BlogPostDTO.class);
            }
        } catch (Exception e) {
            ctx.status(500).result("Internal server error: " + e.getMessage());
        }
    }
    public void update(Context ctx) throws ApiException {
        try {
            long id = Long.parseLong(ctx.pathParam("id"));

            BlogPostDTO blogPostDTO = ctx.bodyAsClass(BlogPostDTO.class);

            if (blogPostDTO == null ||
                    blogPostDTO.getTitle() == null || blogPostDTO.getTitle().trim().isEmpty() ||
                    blogPostDTO.getContent() == null || blogPostDTO.getContent().trim().isEmpty()) {
                throw new ApiException(400, "Der mangler nødvendige felter: titel og/eller indhold");
            }

            BlogPostDTO updatedBlogPost = blogPostDAO.update(id, blogPostDTO);

            ctx.status(200).json(updatedBlogPost, BlogPostDTO.class);

        } catch (ApiException e) {
            throw e;
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Ugyldigt ID-format");
        } catch (Exception e) {
            throw new ApiException(500, "Intern serverfejl: " + e.getMessage());
        }

    }

    public void delete(Context ctx) throws ApiException {
        try {
            long id = Long.parseLong(ctx.pathParam("id"));
            BlogPostDTO deletedBlogPost = blogPostDAO.delete(id);
            if (deletedBlogPost == null) {
                throw new ApiException(404, "BlogPost not found");
            }
            ctx.status(200).json(deletedBlogPost, BlogPostDTO.class);
        } catch (ApiException e) {
            ctx.status(e.getStatusCode()).result(e.getMessage());
        } catch (NumberFormatException e) {
            ctx.status(400).result("Invalid ID format");
        }

    }

    }