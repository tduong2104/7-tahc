package com.doan.a7_tahc.activities;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.doan.a7_tahc.R;
import com.doan.a7_tahc.utilities.Constants;
import com.doan.a7_tahc.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.messaging.FirebaseMessaging;

import org.checkerframework.checker.units.qual.C;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.BitSet;
import java.util.HashMap;
import android.content.ContentResolver;




public class ProfileFragment extends Fragment {

    private Button btn_Logout;
    private Button btn_Update;
    private EditText edtProfileUsername;
    private EditText edtEmail;



    PreferenceManager preferenceManager;
    private String encodedImage;
    private ImageView imgProfile;

    private String encodeImage(Bitmap bitmap){
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight()*previewWidth/bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth,previewHeight,false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK){
                    if(result.getData() != null){
                        Uri imageUri = result.getData().getData();
                        try {
                            InputStream inputStream = requireActivity().getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            imgProfile.setImageBitmap(bitmap);
                            encodedImage = encodeImage(bitmap);

                            }
                        catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        preferenceManager = new PreferenceManager(getContext());
        btn_Logout = view.findViewById(R.id.logout_btn);
        imgProfile = view.findViewById(R.id.profile_image_view);
        btn_Update = view.findViewById(R.id.profle_update_btn);
        edtProfileUsername = view.findViewById(R.id.profile_username);
        edtEmail = view.findViewById(R.id.profile_email);
        edtEmail.setEnabled(false);
        loadUserDetails();

        imgProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            pickImage.launch(intent);

        });

        btn_Update.setOnClickListener(v -> {


            HashMap<String,Object> updates = new HashMap<>();
            updates.put(Constants.KEY_NAME,edtProfileUsername.getText().toString());
            if(encodedImage!=null && !encodedImage.isEmpty()){
                updates.put(Constants.KEY_IMAGE,encodedImage);
            }
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            database.collection(Constants.KEY_COLLECTION_USERS).document(preferenceManager.getString(Constants.KEY_USER_ID))
                    .update(updates)
                    .addOnSuccessListener(DocumentReference ->{
                        showToast("updated success");
                        preferenceManager.putString(Constants.KEY_NAME,edtProfileUsername.getText().toString());
                        if(encodedImage!=null && !encodedImage.isEmpty()){
                            preferenceManager.putString(Constants.KEY_IMAGE,encodedImage);
                        }
                    })
                    .addOnFailureListener(exception ->{
                        showToast("enable update");
                    });

        });

        btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });




        return view;
    }
    private Bitmap getUserImage(String encodedImage){
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

    private void signOut(){
        showToast("logging out...");
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
        database.collection(Constants.KEY_COLLECTION_USERS).document(
                preferenceManager.getString(Constants.KEY_USER_ID)
        );
        HashMap<String ,Object> updates = new HashMap<>();
        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates)
                .addOnSuccessListener(unused -> {
                    preferenceManager.clear();
                    startActivity(new Intent(getContext(),Login.class));
                })
                .addOnFailureListener(e -> showToast("unable to sign out"));
    }
    private void showToast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }
    private void loadUserDetails(){
        edtProfileUsername.setText(preferenceManager.getString(Constants.KEY_NAME));
        edtEmail.setText(preferenceManager.getString(Constants.KEY_EMAIL));
        String imageProfile = preferenceManager.getString(Constants.KEY_IMAGE);
        if (imageProfile != null && !imageProfile.isEmpty()) {
            imgProfile.setImageBitmap(getUserImage(preferenceManager.getString(Constants.KEY_IMAGE)));
        }
        else imgProfile.setImageResource(R.drawable.img);
    }

}
