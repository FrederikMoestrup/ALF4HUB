package dat.entities;

import dat.dtos.DummyBlogpostDTO;

public class DummyBlogpost {

    private int blogpostId;
    private String blogpostTitle;
    private String blogpostAuthor;
    private String blogpostContent;
    private String blogpostDate;


    public DummyBlogpost(String blogpostTitle, String blogpostAuthor, String blogpostContent, String blogpostDate) {
        this.blogpostTitle = blogpostTitle;
        this.blogpostAuthor = blogpostAuthor;
        this.blogpostContent = blogpostContent;
        this.blogpostDate = blogpostDate;
    }

    public DummyBlogpost(int blogpostId, String blogpostTitle, String blogpostAuthor, String blogpostContent, String blogpostDate) {
        this.blogpostId = blogpostId;
        this.blogpostTitle = blogpostTitle;
        this.blogpostAuthor = blogpostAuthor;
        this.blogpostContent = blogpostContent;
        this.blogpostDate = blogpostDate;
    }

    public DummyBlogpost(DummyBlogpostDTO blogpostDTO) {
        this.blogpostId = blogpostDTO.getBlogpostId();
        this.blogpostTitle = blogpostDTO.getBlogpostTitle();
        this.blogpostAuthor = blogpostDTO.getBlogpostAuthor();
        this.blogpostContent = blogpostDTO.getBlogpostContent();
        this.blogpostDate = blogpostDTO.getBlogpostDate();

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


