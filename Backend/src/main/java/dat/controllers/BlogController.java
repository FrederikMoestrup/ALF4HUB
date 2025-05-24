package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.BlogPostDAO;
import dat.dtos.BlogPostDTO;
import dat.exceptions.ApiException;
import dat.service.BlogPostService;
import dat.utils.ProfanityFilter;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class BlogController {

    private final BlogPostDAO blogPostDAO;
    private final BlogPostService blogPostService;

    public BlogController() {
        EntityManagerFactory emf;
        if (HibernateConfig.getTest()) {
            emf = HibernateConfig.getEntityManagerFactoryForTest();
        } else {
            emf = HibernateConfig.getEntityManagerFactory("ALF4HUB_DB");
        }

        this.blogPostDAO = BlogPostDAO.getInstance(emf);
        this.blogPostService = new BlogPostService(blogPostDAO);
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
        } catch (EntityNotFoundException e) {
            throw new ApiException(404, "BlogPost not found");
        } catch (Exception e) {
            throw new ApiException(500, "Internal server error");
        }
    }

    public void getDraftByUserId(Context ctx) throws ApiException {
        try {
            int userId = Integer.parseInt(ctx.pathParam("id"));
            List<BlogPostDTO> blogDTOs = blogPostDAO.getDraftByUserId(userId);

            if (blogDTOs == null || blogDTOs.isEmpty()) {
                ctx.status(200).result("No drafts found");
            } else {
                ctx.status(200).json(blogDTOs, BlogPostDTO.class);
            }
        } catch (Exception e) {
            ctx.status(500).result("Internal server error: " + e.getMessage());
        }
    }

    public void create(Context ctx) throws ApiException {
        try {
            BlogPostDTO blogPostDTO = ctx.bodyAsClass(BlogPostDTO.class);

            BlogPostDTO createdBlogPostDTO = blogPostService.createBlogPost(blogPostDTO);

            ctx.res().setStatus(201);
            ctx.json(createdBlogPostDTO, BlogPostDTO.class);
        } catch (EntityNotFoundException e) {
            throw new ApiException(404, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ApiException(400, e.getMessage());
        } catch (Exception e) {
            throw new ApiException(500, "Internal server error: " + e.getMessage());
        }
    }

    public void createDraft(Context ctx) throws ApiException {
        try {
            BlogPostDTO blogPostDTO = ctx.bodyAsClass(BlogPostDTO.class);

            BlogPostDTO createdBlogPostDTO = blogPostService.createBlogPostDraft(blogPostDTO);

            ctx.status(201).json(createdBlogPostDTO, BlogPostDTO.class);
        } catch (EntityNotFoundException e) {
            throw new ApiException(404, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ApiException(400, e.getMessage());
        } catch (Exception e) {
            throw new ApiException(500, "Internal server error: " + e.getMessage());
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

    public void getValidatedDraft(Context ctx) throws ApiException {
        try {
            BlogPostDTO blogPostDTO = ctx.bodyAsClass(BlogPostDTO.class);

            BlogPostDTO validatedDraft = ProfanityFilter.returnCensoredBlogPost(blogPostDTO);

            ctx.res().setStatus(200);
            ctx.json(validatedDraft, BlogPostDTO.class);
        } catch (Exception e) {
            throw new ApiException(500, "Internal server error");
        }
    }

    public void publishDraft(Context ctx) throws ApiException {
        try {
            BlogPostDTO blogPostDTO = ctx.bodyAsClass(BlogPostDTO.class);
            long draftId = Integer.parseInt(ctx.pathParam("id"));

            BlogPostDTO createdBlogPostDTO = blogPostService.publishDraft(blogPostDTO, draftId);

            ctx.status(201).json(createdBlogPostDTO, BlogPostDTO.class);
        } catch (EntityNotFoundException e) {
            throw new ApiException(404, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ApiException(400, e.getMessage());
        } catch (Exception e) {
            throw new ApiException(500, "Internal server error: " + e.getMessage());
        }
    }

    public void updateDraft(Context ctx) throws ApiException {
        try {
            BlogPostDTO blogPostDTO = ctx.bodyAsClass(BlogPostDTO.class);
            long draftId = Integer.parseInt(ctx.pathParam("id"));

            BlogPostDTO createdBlogPostDTO = blogPostService.updateDraft(blogPostDTO, draftId);

            ctx.status(201).json(createdBlogPostDTO, BlogPostDTO.class);
        } catch (EntityNotFoundException e) {
            throw new ApiException(404, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ApiException(400, e.getMessage());
        } catch (Exception e) {
            throw new ApiException(500, "Internal server error: " + e.getMessage());
        }
    }

}
