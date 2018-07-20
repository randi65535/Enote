package up.cheer.project.summer.enote;

public class Memo {

    private String mIsbn;
    private String mTitle;
    private String mAuthor;
    private String mContent;

    public Memo(String mIsbn, String mTitle, String mAuthor, String mContent) {
        this.mIsbn = mIsbn;
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mContent = mContent;
    }

    public Memo(String mIsbn, String mTitle, String mAuthor) {
        this.mIsbn = mIsbn;
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
    }

    public String getmIsbn() {
        return mIsbn;
    }

    public void setmIsbn(String mIsbn) {
        this.mIsbn = mIsbn;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }
}
