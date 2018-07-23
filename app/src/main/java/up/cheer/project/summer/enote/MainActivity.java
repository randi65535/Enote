package up.cheer.project.summer.enote;

import android.content.Intent;
import android.os.Debug;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity{

    Button addMemoBtn;
    ListView memoListView;
    ListviewAdapter adapter;

    DatabaseHelper appDB;
    LinkedList<Memo> list = new LinkedList<>();

    //이 값이 중복되면 안된다.
    public static final int DO_NOT_ACTION               =   100;
    public static final int REQUEST_ADD_ACTIVITY        =   1001;
    public static final int REQUEST_MANAGE_ACTIVITY     =   1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addMemoBtn = findViewById(R.id.AddMemoBtn);
        memoListView = findViewById(R.id.MemoListView);


        //DB에서 리스트를 불러오는 과정
        appDB = new DatabaseHelper(this);
        list = appDB.getAllMemo();
        //과정 끝

        //레이아웃에 데이터를 삽입하는 과정
        adapter = new ListviewAdapter(this);
        adapter.setList(list);
        memoListView.setAdapter(adapter);
        //과정 끝

        addMemoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMemoActivity.class);
                startActivityForResult(intent, REQUEST_ADD_ACTIVITY);
            }
        });


        memoListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, ManageMemoActivity.class);

                Memo memo = adapter.getItem(position);
                intent.putExtra("position", position);
                intent.putExtra("isbn", memo.getmIsbn());
                intent.putExtra("title", memo.getmTitle());
                intent.putExtra("author", memo.getmAuthor());
                intent.putExtra("content", memo.getmContent());
                startActivityForResult(intent, REQUEST_MANAGE_ACTIVITY);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode)
        {
            case AddMemoActivity.ADDED : case ManageMemoActivity.DELETED :

                //DB값이 변경되지 않았다는 소리
                if (adapter.getCount() == appDB.getColumnCount()){
                    return;
                }

                list = appDB.getAllMemo();
                adapter.setList(list);
                memoListView.setAdapter(adapter);

                break;

            case ManageMemoActivity.UPDATED :

                    list.get(data.getIntExtra("position", -1)).setmContent(data.getStringExtra("content"));

                break;

            default:

                break;

        }

    }

}
