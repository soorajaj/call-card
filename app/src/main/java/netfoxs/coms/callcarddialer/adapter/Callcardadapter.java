package netfoxs.coms.callcarddialer.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import netfoxs.coms.callcarddialer.Bean.Callcard;
import netfoxs.coms.callcarddialer.ChaingnumberActivity;
import netfoxs.coms.callcarddialer.R;

/**
 * Created by WIIS on 6/6/2016.
 */
public class Callcardadapter extends BaseAdapter {

    List<Callcard> adsclasses;
    Activity context;

    public Callcardadapter(List<Callcard> adsclasses, Activity context) {
        this.adsclasses = adsclasses;
        this.context = context;
    }
    @Override
    public int getCount() {
        return adsclasses.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;


        // The convertView argument is essentially a "ScrapView" as described is Lucas post
        // http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
        // It will have a non-null value when ListView is asking you recycle the row layout.
        // So, when convertView is not null, you should simply update its contents instead of inflating a new row    layout.

        if(convertView==null){

            // inflate the layout
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.adaptercallcard, parent, false);

            // well set up the ViewHolder
            viewHolder = new ViewHolderItem();
          viewHolder.text_name = (TextView) convertView.findViewById(R.id.text_name);
            viewHolder.text_number= (TextView) convertView.findViewById(R.id.text_number);
            viewHolder.leanier_clickable= (LinearLayout) convertView.findViewById(R.id.leanier_clickable);
            // store the holder with the view.
            convertView.setTag(viewHolder);

        }else{
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        viewHolder.text_name.setText(adsclasses.get(position).getCardeName());
        viewHolder.text_number.setText(adsclasses.get(position).getCallCode() + "-" + adsclasses.get(position).getCallNumber());
        viewHolder.leanier_clickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChaingnumberActivity.class);
                intent.putExtra("flag", "fromlist");
                intent.putExtra("name", adsclasses.get(position).getCardeName());
                intent.putExtra("number", adsclasses.get(position).getCallNumber());
                intent.putExtra("pos", adsclasses.get(position).getPosition());
                intent.putExtra("id", adsclasses.get(position).getId());
                intent.putExtra("state",adsclasses.get(position).getState());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    public class ViewHolderItem {
        TextView text_name;
        TextView text_number;
        LinearLayout leanier_clickable;
    }
}
