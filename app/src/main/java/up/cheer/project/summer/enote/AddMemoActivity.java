package up.cheer.project.summer.enote;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class AddMemoActivity extends AppCompatActivity {

    Button insertMemoBtn, returnMenuBtn, searchIsbnBtn;
    EditText inputIsbnTextEdit, inputAuthorTextEdit, inputTitleTextEdit;
    TextView isbnTextView;

    Handler mHandler = new Handler();
    String urlStr = "http://seoji.nl.go.kr/landingPage/SearchApi.do?cert_key=5e5a4c575334ac512cc7907e5020563e&result_style=json&page_no=1&page_size=10&isbn=9788992555784";

    class mThread extends Thread{

        @Override
        public void run() {
            try {
                URL url = new URL(urlStr);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    readStream(in);
                    urlConnection.disconnect();
                }else{
                    Toast.makeText(getApplicationContext(), "에러발생", Toast.LENGTH_SHORT).show();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public void readStream(InputStream in){
            final String data = readData(in);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    isbnTextView.setText(data);
                }
            });
        }

        public String readData(InputStream is){
            String data = "";
            Scanner s = new Scanner(is);
            while(s.hasNext()) data += s.nextLine() + "\n";
            s.close();
            return data;
        }
    }

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

        isbnTextView = findViewById(R.id.IsbnTextView);


        searchIsbnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputIsbnTextEdit.getText().toString().equals("")){
                    Toast.makeText(AddMemoActivity.this, "ISBN을 기입하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String isbn = inputIsbnTextEdit.getText().toString().replace("-", "");

                new mThread().start();

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

                new mThread().start();
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
