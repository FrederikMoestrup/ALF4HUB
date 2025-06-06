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
        if (blogPostDTO == null || blogPostDTO.getTitle() == null || blogPostDTO.getTitle().isBlank() || blogPostDTO.getContent() == null || blogPostDTO.getContent().isBlank()) {
            throw new IllegalArgumentException("Title and content must not be empty.");
        }

        if (ProfanityFilter.containsProfanity(blogPostDTO.getTitle()) || ProfanityFilter.containsProfanity(blogPostDTO.getContent())) {
            throw new IllegalArgumentException("Blog post contains profanity.");
        }

        blogPostDTO.setStatus(BlogPostStatus.READY);
        return blogPostDAO.create(blogPostDTO);
    }

    public BlogPostDTO publishDraft(BlogPostDTO blogPostDTO, long draftId) {
        if (blogPostDTO == null || blogPostDTO.getTitle() == null || blogPostDTO.getTitle().isBlank() || blogPostDTO.getContent() == null || blogPostDTO.getContent().isBlank()) {
            throw new IllegalArgumentException("Title and content must not be empty.");
        }

        if (ProfanityFilter.containsProfanity(blogPostDTO.getTitle()) || ProfanityFilter.containsProfanity(blogPostDTO.getContent())) {
            throw new IllegalArgumentException("Blog post contains profanity.");
        }

        return blogPostDAO.publishDraft(blogPostDTO, draftId);
    }

    public BlogPostDTO updateDraft(BlogPostDTO blogPostDTO, long draftId) {
        if (blogPostDTO == null || blogPostDTO.getTitle() == null || blogPostDTO.getTitle().isBlank() || blogPostDTO.getContent() == null || blogPostDTO.getContent().isBlank()) {
            throw new IllegalArgumentException("Title and content must not be empty.");
        }

        return blogPostDAO.updateDraft(blogPostDTO, draftId);
    }

    public BlogPostDTO createBlogPostDraft(BlogPostDTO blogPostDTO) {
        if (blogPostDTO == null || blogPostDTO.getTitle() == null || blogPostDTO.getTitle().isBlank() || blogPostDTO.getContent() == null || blogPostDTO.getContent().isBlank()) {
            throw new IllegalArgumentException("Title and content must not be empty.");
        }

        return blogPostDAO.saveAsDraft(blogPostDTO);
    }
}
