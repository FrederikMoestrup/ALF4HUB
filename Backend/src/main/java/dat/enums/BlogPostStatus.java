package dat.enums;

public enum BlogPostStatus {
    DRAFT,
    READY, // to either be saved as draft or published
    PENDING_REVIEW, // for content review perhaps?
    PUBLISHED,
    APPROVED,
    REJECTED,
}
