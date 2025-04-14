package dat.dtos;

import dat.entities.DummyBlogpost;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DummyBlogpostDTO {
    private int blogpostId;
    private String blogpostTitle;
    private String blogpostAuthor;
    private String blogpostContent;
    private String blogpostDate;


    public DummyBlogpostDTO(DummyBlogpost dummyBlogpost) {
        this.blogpostId = dummyBlogpost.getBlogpostId();
        this.blogpostTitle = dummyBlogpost.getBlogpostTitle();
        this.blogpostAuthor = dummyBlogpost.getBlogpostAuthor();
        this.blogpostContent = dummyBlogpost.getBlogpostContent();
        this.blogpostDate = dummyBlogpost.getBlogpostDate();
    }

    public DummyBlogpostDTO(String blogpostTitle, String blogpostAuthor, String blogpostContent, String blogpostDate) {
        this.blogpostTitle = blogpostTitle;
        this.blogpostAuthor = blogpostAuthor;
        this.blogpostContent = blogpostContent;
        this.blogpostDate = blogpostDate;
    }

    public DummyBlogpostDTO(int blogpostId, String blogpostTitle, String blogpostAuthor, String blogpostContent, String blogpostDate) {
        this.blogpostId = blogpostId;
        this.blogpostTitle = blogpostTitle;
        this.blogpostAuthor = blogpostAuthor;
        this.blogpostContent = blogpostContent;
        this.blogpostDate = blogpostDate;
    }

    public String getBlogpostTitle() {
        return blogpostTitle;
    }

    public void setBlogpostTitle(String blogpostTitle) {
        this.blogpostTitle = blogpostTitle;
    }

    public String getBlogpostAuthor() {
        return blogpostAuthor;
    }

    public void setBlogpostAuthor(String blogpostAuthor) {
        this.blogpostAuthor = blogpostAuthor;
    }

    public String getBlogpostContent() {
        return blogpostContent;
    }

    public void setBlogpostContent(String blogpostContent) {
        this.blogpostContent = blogpostContent;
    }

    public String getBlogpostDate() {
        return blogpostDate;
    }

    public void setBlogpostDate(String blogpostDate) {
        this.blogpostDate = blogpostDate;
    }

    public int getBlogpostId() {
        return blogpostId;
    }

}
