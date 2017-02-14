package netfoxs.coms.callcarddialer.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import netfoxs.coms.callcarddialer.Bean.Adsclass;
import netfoxs.coms.callcarddialer.R;

/**
 * Created by WIIS on 5/31/2016.
 */
public class Adsadapter extends RecyclerView.Adapter<Adsadapter.ViewHolderItem> {
    ArrayList<Adsclass> adsclasses;
    Activity context;

    public Adsadapter(ArrayList<Adsclass> adsclasses, Activity context) {
        this.adsclasses = adsclasses;
        this.context = context;
    }


    @Override
    public ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.adsadapterlayout,null);
ViewHolderItem viewHolderItem=new ViewHolderItem(itemView);
        return viewHolderItem;
    }

    @Override
    public void onBindViewHolder(ViewHolderItem holder, final int position) {
        holder.text_date.setText(adsclasses.get(position).getAdstime());
        Glide.with(context)
                .load(adsclasses.get(position).getAdsimage())
                .placeholder(R.drawable.playsholder)
                .error(R.drawable.playsholder)
                .into(holder.iamge_ads);
        holder.iamge_ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(adsclasses.get(position).getAdsurl()));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return adsclasses.size();
    }


    public class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView text_date;
        ImageView iamge_ads;

        public ViewHolderItem(View itemView) {
            super(itemView);
            text_date = (TextView) itemView.findViewById(R.id.text_date);
            iamge_ads= (ImageView) itemView.findViewById(R.id.iamge_ads);
        }
    }
}
