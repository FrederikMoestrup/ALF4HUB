package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.BlogPostDAO;
import dat.dtos.BlogPostDTO;
import dat.enums.BlogPostStatus;
import dat.exceptions.ApiException;
import dat.service.BlogPostService;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class BlogController {

    private final BlogPostDAO blogPostDAO;
    private final BlogPostService blogPostService;

    public BlogController() {
        EntityManagerFactory emf;
        if  (HibernateConfig.getTest()) {
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

    public void create(Context ctx) throws ApiException {
        BlogPostDTO blogPostDTO = ctx.bodyAsClass(BlogPostDTO.class);

        if (blogPostDTO == null || blogPostDTO.getTitle() == null || blogPostDTO.getContent() == null) {
            throw new ApiException(400, "Missing required fields: title or content");
        }

        try{
            BlogPostDTO createdBlogPostDTO = blogPostService.createBlogPost(blogPostDTO);
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

    // Create new draft post (Det her er Juvenas del har dog ikke lavet noget endnu)


    // Get all drafts for current user
    public void getMyDrafts(Context ctx) throws ApiException {
        try {
            Long currentUserId = getAuthenticatedUserId(ctx);
            if (currentUserId == null) {
                throw new ApiException(401, "Authentication required");
            }

            List<BlogPostDTO> drafts = blogPostDAO.getDraftsByUser(currentUserId);
            ctx.json(drafts);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(500, "Error retrieving drafts");
        }
    }

    // Publish a draft
    public void publishDraft(Context ctx) throws ApiException {
        try {
            Long postId = Long.parseLong(ctx.pathParam("id"));
            Long currentUserId = getAuthenticatedUserId(ctx);

            // First verify the post exists and belongs to the user
            BlogPostDTO post = blogPostDAO.getById(postId);
            if (!post.getUserId().equals(currentUserId)) {
                throw new ApiException(403, "You can only publish your own drafts");
            }

            BlogPostDTO updatedPost = blogPostDAO.updateStatus(postId, BlogPostStatus.PUBLISHED);
            ctx.json(updatedPost);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid post ID");
        } catch (EntityNotFoundException e) {
            throw new ApiException(404, "Post not found");
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(500, "Error publishing draft");
        }
    }

    // Helper method - returns just the user ID
    private Long getAuthenticatedUserId(Context ctx) {
        // Example implementations:
        // 1. From session: return ctx.sessionAttribute("userId");
        // 2. From JWT: return getUserIdFromJwt(ctx.header("Authorization"));
        // Implement according to your auth system
        return 1L; // placeholder - replace with actual implementation
    }
}
