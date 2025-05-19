package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.BlogPostDAO;
import dat.dtos.BlogPostDTO;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class BlogController {

    private final BlogPostDAO blogPostDAO;

    public BlogController() {
        if  (HibernateConfig.getTest()) {
        } else {
            this.blogPostDAO = BlogPostDAO.getInstance(emf);
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


            ctx.res().setStatus(201);
            ctx.json(createdBlogPostDTO, BlogPostDTO.class);
        } catch (Exception e) {
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
        }
