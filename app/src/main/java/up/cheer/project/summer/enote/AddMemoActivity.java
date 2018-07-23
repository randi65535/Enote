package up.cheer.project.summer.enote;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

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

    public static final int ADDED                       =   201;

    Handler mHandler = new Handler();
    StringBuffer urlStrBuffer = new StringBuffer();



    class mThread extends Thread{

        String author;
        String title;

        @Override
        public void run() {
            try {
                URL url = new URL(urlStrBuffer.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    readStream(in);
                    urlConnection.disconnect();
                }

            } catch (MalformedURLException e) {
                Toast.makeText(getApplicationContext(), "URL 없음", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlStrBuffer.setLength(0);
            }

        }

        public void readStream(InputStream in){
            final String data = readData(in);
            try {


                JSONObject jsonObject =  new JSONObject(data);
                JSONArray jArr = jsonObject.getJSONArray("docs");
                //어짜피 ISBN은 고유값이라 1개 밖에 나오지 않는다.
                jsonObject = jArr.getJSONObject(0);


                author = jsonObject.getString("AUTHOR");
                title = jsonObject.getString("TITLE");

            } catch (Exception e) {

            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    inputAuthorTextEdit.setText(author);
                    inputTitleTextEdit.setText(title);
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


                if(isbn.length() < 10) {
                    Toast.makeText(AddMemoActivity.this, "ISBN의 길이가 너무 짧습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(isbn.length() > 20) {
                    Toast.makeText(AddMemoActivity.this, "ISBN의 길이가 너무 깁니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                urlStrBuffer.append("http://seoji.nl.go.kr/landingPage/SearchApi.do?cert_key=5e5a4c575334ac512cc7907e5020563e&result_style=json&page_no=1&page_size=10&isbn="+isbn);

                //api를 읽음
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

                DatabaseHelper appDB = new DatabaseHelper(AddMemoActivity.this);
                String author = inputAuthorTextEdit.getText().toString();

                author = author.replace("[","");
                author = author.replace("]","");
                author = author.replace(";",", ");
                appDB.insert(new Memo(
                        inputIsbnTextEdit.getText().toString(),
                        inputTitleTextEdit.getText().toString(),
                        author
                ));

                setResult(ADDED);
                finish();

            }
        });


        returnMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(MainActivity.DO_NOT_ACTION);
                finish();
            }
        });

    }

}

