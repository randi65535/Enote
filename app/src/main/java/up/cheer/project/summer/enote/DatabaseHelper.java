package up.cheer.project.summer.enote;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.sql.Statement;
import java.util.LinkedList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MEMO_DB";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE MEMO (db_isbn varchar(255) PRIMARY KEY, db_title varchar(255), db_author varchar(255), db_content TEXT);");
        } catch (Exception e) {
            Log.e("Database Error", "CREATE Error");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(Memo memo) {

        SQLiteDatabase db = null;
        SQLiteStatement stmt = null;
        try {

            db = this.getWritableDatabase();
            stmt = db.compileStatement("INSERT INTO MEMO VALUES (?,?,?,?)");
            stmt.bindString(1, memo.getmIsbn());
            stmt.bindString(2, memo.getmTitle());
            stmt.bindString(3, memo.getmAuthor());
            stmt.bindString(4, memo.getmContent());
            stmt.execute();

        } catch (Exception e) {
            Log.e("Database Error", "Insert Error");
        }
        close(db);
    }

    public void delete(String isbn) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();
            SQLiteStatement stmt = db.compileStatement("DELETE FROM MEMO WHERE db_isbn = ?");
            stmt.bindString(1, isbn);
            stmt.execute();
        } catch (Exception e) {
            Log.e("Database Error", "Delete Error");
        } finally {
            close(db);
        }

    }

    public void update(String isbn, String content){
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();
            SQLiteStatement stmt = db.compileStatement("UPDATE MEMO SET db_content = ? WHERE db_isbn = ?");
            stmt.bindString(1, content);
            stmt.bindString(2, isbn);
            stmt.execute();
        } catch (Exception e) {
            Log.e("Database Error", "Update Error");
        } finally {
            close(db);
        }
    }

    public LinkedList<Memo> getAllMemo() {

        LinkedList<Memo> list = new LinkedList<>();
        SQLiteDatabase db = null;
        try {
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM MEMO", null);

            while (cursor.moveToNext()) {
                list.add(new Memo(
                        //SQLite는 index를 0부터 시작한다.
                        cursor.getString(0),    // isbn
                        cursor.getString(1),    // title
                        cursor.getString(2),    // author
                        cursor.getString(3)     // content
                ));
            }
        } catch (Exception e) {
            Log.e("Database  Error", "Select Error");
        } finally {
            close(db);
        }

        return list;
    }

    public void close(SQLiteDatabase db) {
        if (db != null) db.close();
    }

    public int getColumnCount(){

        SQLiteDatabase db = null;
        int columnCount = 0;
        try {
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT COUNT(db_isbn) FROM MEMO", null);
            columnCount = cursor.getColumnCount();
        } catch (Exception e) {
            Log.e("Database  Error", "Count Error");
        } finally {
            close(db);
        }

        return columnCount;

    }

    public String getContentByIsbn(String isbn) {
        SQLiteDatabase db = null;
        String content = null;
        try {
            db = this.getReadableDatabase();
            String query = "SELECT db_isbn FROM MEMO WHERE db_isbn = " + isbn;
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
           content = cursor.getString(0);

        } catch (Exception e) {
            Log.e("Database  Error", "Select Error");
        } finally {
            close(db);
        }

        return content;

    }

}

