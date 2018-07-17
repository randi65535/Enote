package up.cheer.project.summer.enote;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddMemoActivity extends AppCompatActivity {

    Button insertMemoBtn, returnMenuBtn, searchIsbnBtn;
    EditText inputIsbnTextEdit, inputAuthorTextEdit, inputTitleTextEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_memo);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        insertMemoBtn = findViewById(R.id.InsertMemoBtn);
        returnMenuBtn = findViewById(R.id.ReturnMainBtn1);
        searchIsbnBtn = findViewById(R.id.SearchIsbnBtn);

        inputIsbnTextEdit = findViewById(R.id.InputIsbnTextEdit);
        inputTitleTextEdit = findViewById(R.id.InputTitleTextEdit);
        inputAuthorTextEdit = findViewById(R.id.InputAuthorTextEdit);


        searchIsbnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputIsbnTextEdit.getText().toString().equals("")){
                    Toast.makeText(AddMemoActivity.this, "ISBN을 기입하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String isbn = inputIsbnTextEdit.getText().toString().replace("-", "");

//                Toast.makeText(getApplicationContext(), isbn, Toast.LENGTH_SHORT).show();

            }
        });

        insertMemoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(inputIsbnTextEdit.getText().toString().equals("")){
                    Toast.makeText(AddMemoActivity.this, "ISBN을 기입하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(inputTitleTextEdit.getText().toString().equals("")){
                    Toast.makeText(AddMemoActivity.this, "도서명을 기입하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(inputAuthorTextEdit.getText().toString().equals("")){
                    Toast.makeText(AddMemoActivity.this, "작가명을 기입하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });


        returnMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }
}
