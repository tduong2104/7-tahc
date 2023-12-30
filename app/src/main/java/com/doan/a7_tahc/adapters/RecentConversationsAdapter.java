package com.doan.a7_tahc.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doan.a7_tahc.R;
import com.doan.a7_tahc.databinding.ItemContainerRecentConversionBinding;
import com.doan.a7_tahc.models.ChatMessage;

import java.util.List;

public class RecentConversationsAdapter extends RecyclerView.Adapter<RecentConversationsAdapter.ConversionViewHolder>{

    private final List<ChatMessage> chatMessages;

    public RecentConversationsAdapter(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemContainerRecentConversionBinding binding = ItemContainerRecentConversionBinding.inflate(layoutInflater,parent,false);
        return new ConversionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionViewHolder holder, int position) {
        holder.setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    class ConversionViewHolder extends RecyclerView.ViewHolder{

        ItemContainerRecentConversionBinding binding;
        ConversionViewHolder(ItemContainerRecentConversionBinding itemContainerRecentConversionBinding){
            super(itemContainerRecentConversionBinding.getRoot());
            binding = itemContainerRecentConversionBinding;
        }
    void  setData(ChatMessage chatMessage){
            binding.textName.setText(chatMessage.conversionName);
            binding.textRecentMessage.setText(chatMessage.message);
        //if(chatMessage.conversionImage != null && !chatMessage.conversionImage.isEmpty())
        //    binding.imageProfile.setImageBitmap(getConversationImage(chatMessage.conversionImage));
        //else binding.imageProfile.setImageResource(R.drawable.img);
    }
    }

    Bitmap getConversationImage (String encodedImage){
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}
