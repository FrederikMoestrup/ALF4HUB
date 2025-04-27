package dat.service;

import dat.daos.BlogPostDAO;
import dat.dtos.BlogPostDTO;
import dat.utils.ProfanityFilter;

public class BlogPostService {

    private final BlogPostDAO blogPostDAO;

    // yet to be used anywhere
    public BlogPostService(BlogPostDAO blogPostDAO) {
        this.blogPostDAO = blogPostDAO;
    }

    public BlogPostDTO createBlogPost(BlogPostDTO blogPostDTO) {
        if (blogPostDTO != null && !blogPostDTO.getContent().isEmpty()) {
            if (ProfanityFilter.containsProfanity(blogPostDTO.getContent())) {
                throw new IllegalStateException("Blogpost contains profanity");
            }
        }
        return blogPostDAO.create(blogPostDTO);
    }
}
