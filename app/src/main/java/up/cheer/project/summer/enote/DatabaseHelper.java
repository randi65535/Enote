package up.cheer.project.summer.enote;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.LinkedList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MEMO (db_isbn varchar(255) PRIMARY KEY, db_title varchar(255), db_author varchar(255), db_content TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(Memo memo){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("INSERT INTO MEMO VALUES (?,?,?,?)");
        stmt.bindString(1, memo.getmIsbn());
        stmt.bindString(2, memo.getmTitle());
        stmt.bindString(3, memo.getmAuthor());
        stmt.bindString(4, memo.getmContent());
        stmt.execute();
    }

    public void delete(String isbn){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("DELETE FROM MEMO WHERE db_isbn = ?");
        stmt.bindString(1, isbn);
        stmt.execute();
    }

    public LinkedList<Memo> getAllMemo(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MEMO", null);

        LinkedList<Memo> list = new LinkedList<>();
        while (cursor.moveToNext()) {
            list.add(new Memo(
                                //SQLite는 index를 0부터 시작한다.
                                cursor.getString(0),    // isbn
                                cursor.getString(1),    // title
                                cursor.getString(2),    // author
                                cursor.getString(3)     // content
                            ));
        }

        return list;
    }

    public void close(SQLiteDatabase db){
        if(db != null) db.close();
    }
}
