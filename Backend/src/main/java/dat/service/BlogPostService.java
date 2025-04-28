package dat.service;

import dat.daos.BlogPostDAO;
import dat.dtos.BlogPostDTO;
import dat.enums.BlogPostStatus;
import dat.utils.ProfanityFilter;

public class BlogPostService {

    private final BlogPostDAO blogPostDAO;

    public BlogPostService(BlogPostDAO blogPostDAO) {
        this.blogPostDAO = blogPostDAO;
    }

    public BlogPostDTO createBlogPost(BlogPostDTO blogPostDTO) {
            if (blogPostDTO == null || blogPostDTO.getContent() == null || blogPostDTO.getContent().isBlank()) {
                throw new IllegalArgumentException("Blog post content cannot be empty.");
            }

            if (ProfanityFilter.containsProfanity(blogPostDTO.getContent())) {
                throw new IllegalStateException("Blog post contains profanity.");
            }

            blogPostDTO.setStatus(BlogPostStatus.READY);
            return blogPostDAO.create(blogPostDTO);
    }
}
