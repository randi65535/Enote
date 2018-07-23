package up.cheer.project.summer.enote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ManageMemoActivity  extends AppCompatActivity {

    public static final int DELETED = 204;
    public static final int UPDATED = 203;

    Button updateMemoBtn, returnMenuBtn, deleteMemoBtn;
    TextView bookIsbn, bookTitle, bookAuthor;
    EditText bookContent;
    Intent intent;

    DatabaseHelper appDB = new DatabaseHelper(ManageMemoActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_memo);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        updateMemoBtn = findViewById(R.id.UpdateMemoBtn);
        deleteMemoBtn = findViewById(R.id.DeleteMemoBtn);
        returnMenuBtn = findViewById(R.id.ReturnMainBtn2);

        bookIsbn = findViewById(R.id.BookIsbn);
        bookTitle = findViewById(R.id.BookTitle);
        bookAuthor = findViewById(R.id.BookAuthor);
        bookContent = findViewById(R.id.BookContent);

        intent = getIntent();


        String isbn = intent.getStringExtra("isbn");
        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");
        String content = intent.getStringExtra("content");

        bookIsbn.setText(isbn);
        bookTitle.setText(title);
        bookAuthor.setText(author);
        bookContent.setText(content);



        updateMemoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isbn = bookIsbn.getText().toString();
                String content = bookContent.getText().toString();
                Intent intent = getIntent();
                //Intent intent = new Intent(ManageMemoActivity.this, MainActivity.class);
                appDB.update(isbn, content);

                int position = intent.getIntExtra("position", 0);

                intent.putExtra("position", position);
                intent.putExtra("content", content);
                Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_LONG);
                setResult(ManageMemoActivity.UPDATED, intent);
                finish();
            }
        });

        returnMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        deleteMemoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isbn = intent.getStringExtra("isbn");
                appDB.delete(isbn);

                setResult(DELETED);
                finish();
            }
        });

    }

}