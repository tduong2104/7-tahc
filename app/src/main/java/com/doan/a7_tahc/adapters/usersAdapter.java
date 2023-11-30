package com.doan.a7_tahc.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.doan.a7_tahc.R;
import com.doan.a7_tahc.models.User;

import java.util.List;

public class usersAdapter extends RecyclerView.Adapter<usersAdapter.UserViewHolder>{

    private List<User> users;

    public usersAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_user, parent, false);
        return new UserViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull usersAdapter.UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.emailTextView.setText(user.email);
        holder.nameTextView.setText(user.name);
        holder.imageView.setImageBitmap(getUserImage(user.image));

    }

    @Override
    public int getItemCount() {
        return users.size();
    }



    public void setUserList(List<User> userList) {
        this.users = userList;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView emailTextView;
        public TextView nameTextView;


        public UserViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
        }
    }
    private Bitmap getUserImage(String encodedImage){
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

}
