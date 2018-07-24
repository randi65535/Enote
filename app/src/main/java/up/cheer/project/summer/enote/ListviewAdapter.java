package up.cheer.project.summer.enote;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.LinkedList;
import java.util.List;

public class ListviewAdapter extends BaseAdapter {

    private Context context;
    private LinkedList<Memo> list = new LinkedList<>();

    public void setList(LinkedList<Memo> list) {
        this.list = list;
    }

    public ListviewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Memo getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //set Adapter하면 발생하는 곳
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MemoLayout memoLayout;

        if (convertView == null){
            memoLayout = new MemoLayout(context);
        } else {
            memoLayout = (MemoLayout) convertView;
        }

        memoLayout.inflateTitle(list.get(position).getmTitle());
        memoLayout.inflateAuthor(list.get(position).getmAuthor());
        return memoLayout;
    }

}

