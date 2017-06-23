package com.example.dell.mysqliteapplicationtest;

/**
 * Created by dell on 23/05/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

public class RandomUserAdapter extends RecyclerView.Adapter<RandomUserAdapter.ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;

    //List of superHeroes
    List<RandomUser> superHeroes;

    public RandomUserAdapter(List<RandomUser> superHeroes, Context context) {
        super();
        //Getting all the superheroes
        this.superHeroes = superHeroes;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.random_user_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final RandomUser superHero = superHeroes.get(position);
        holder.textName.setText("Name: " + superHero.getTitle() + " " + superHero.getFirst() + " " + superHero.getLast());
        holder.textEmail.setText("Email: " + superHero.getEmail());
        Glide.with(context).load(superHero.getThumbnail()).asBitmap().placeholder(R.drawable.img_marico_logo).centerCrop().into(new BitmapImageViewTarget(holder.imgImage) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.imgImage.setImageDrawable(circularBitmapDrawable);
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RandomUserDetailActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("email", superHero.getEmail());
                intent.putExtra("pic", superHero.getThumbnail());
                intent.putExtra("phone", superHero.getPhone());
                intent.putExtra("name", superHero.getTitle() + " " + superHero.getFirst() + " " + superHero.getLast());
                intent.putExtra("location", superHero.getStreet() + " " + superHero.getCity() + " " + superHero.getState() + " " + superHero.getPostcode());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return superHeroes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgImage;
        public TextView textName;
        public TextView textEmail;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            imgImage = (ImageView) itemView.findViewById(R.id.imgImage);
            textName = (TextView) itemView.findViewById(R.id.textName);
            textEmail = (TextView) itemView.findViewById(R.id.textEmail);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}
