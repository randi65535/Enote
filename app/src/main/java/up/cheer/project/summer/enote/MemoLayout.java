package up.cheer.project.summer.enote;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

// 직접 넣어주는 클래스이다.
public class MemoLayout extends LinearLayout {

    private TextView memoLayoutTitle, memoLayoutAuthor;

    public MemoLayout(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.memo_layout, this, true);
        memoLayoutTitle = findViewById(R.id.MemoLayoutTitle);
        memoLayoutAuthor = findViewById(R.id.MemoLayoutAuthor);

    }

    void inflateTitle(String title){
        memoLayoutTitle.setText(title);
    }

    void inflateAuthor(String author){
        memoLayoutAuthor.setText(author);
    }
}
