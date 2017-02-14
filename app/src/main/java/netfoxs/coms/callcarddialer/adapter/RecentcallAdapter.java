package netfoxs.coms.callcarddialer.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import netfoxs.coms.callcarddialer.Bean.Recentcalls;
import netfoxs.coms.callcarddialer.R;


/**
 * Created by WIIS on 5/19/2016.
 */
public class RecentcallAdapter extends BaseAdapter {
    Context mContext;
   List<Recentcalls> recentcallses;

    public RecentcallAdapter(Context mContext,  List<Recentcalls> recentcallses) {
        this.mContext = mContext;
        this.recentcallses = recentcallses;
    }

    @Override
    public int getCount() {
        return recentcallses.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    static class ViewHolderItem {
        TextView textViewItem,textView_timedate,textView_number;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderItem viewHolder;


        // The convertView argument is essentially a "ScrapView" as described is Lucas post
        // http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
        // It will have a non-null value when ListView is asking you recycle the row layout.
        // So, when convertView is not null, you should simply update its contents instead of inflating a new row    layout.

        if(convertView==null){

            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.adapterrecentcall, parent, false);

            // well set up the ViewHolder
            viewHolder = new ViewHolderItem();
            viewHolder.textViewItem = (TextView) convertView.findViewById(R.id.textview_name);
            viewHolder.textView_number= (TextView) convertView.findViewById(R.id.textView_number);
            viewHolder.textView_timedate= (TextView) convertView.findViewById(R.id.textView_timedate);
            // store the holder with the view.
            convertView.setTag(viewHolder);

        }else{
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

          viewHolder.textView_timedate.setText(recentcallses.get(position).getDateTime());
        viewHolder.textView_number.setText(recentcallses.get(position).getContactNumber());
        viewHolder.textViewItem.setText(recentcallses.get(position).getDisplayName());
        return convertView;
    }
}
