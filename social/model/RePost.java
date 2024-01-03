package social.model;

public class RePost extends SimplePost {

    private User subPostAuthor;
    private Post subPost;

    public RePost(String text, User subPostAuthor, Post subPost) {
        super(text);
        if (subPostAuthor == null || subPost == null) {
            throw new NullPointerException();
        }
        this.subPostAuthor = subPostAuthor;
        this.subPost = subPost;
    }

    @Override
    public String getText() {
        return super.getText() + " (repost de " + this.subPostAuthor.getName() + " : " + this.subPost.getText() + ")";
    }
}
