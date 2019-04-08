package com.example.nminhanh.spacesharing.Fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nminhanh.spacesharing.Model.Space;
import com.example.nminhanh.spacesharing.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    RecyclerView mRecycleView;
    RecyclerView.LayoutManager mLinearLayoutManager;
    DatabaseReference mFirebaseReference;

    static final String SPACE_CHILD = "space";

    FirebaseRecyclerAdapter<Space, SpaceViewHolder> mFirebaseAdapter;

    public class SpaceViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewSpaceTitle;
        TextView mTextViewAddress;
        ImageView mImageView;
        TextView mTextViewPrice;

        public SpaceViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewSpaceTitle = itemView.findViewById(R.id.item_title);
            mTextViewAddress = itemView.findViewById(R.id.item_address);
            mImageView = itemView.findViewById(R.id.item_image);
            mTextViewPrice = itemView.findViewById(R.id.item_textview_price);
        }
    }


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = getLayoutInflater().inflate(R.layout.fragment_search, container, false);
        mRecycleView = view.findViewById(R.id.recycleView);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecycleView.setLayoutManager(mLinearLayoutManager);

        mFirebaseReference = FirebaseDatabase.getInstance().getReference();
        SnapshotParser<Space> parser = new SnapshotParser<Space>() {
            @NonNull
            @Override
            public Space parseSnapshot(@NonNull DataSnapshot snapshot) {
                Space s = snapshot.getValue(Space.class);
                if (s != null) {
                    s.setId(snapshot.getKey());
                }
                return s;
            }
        };

        DatabaseReference mSpaceRef = mFirebaseReference.child(SPACE_CHILD);
        FirebaseRecyclerOptions<Space> options =
                new FirebaseRecyclerOptions.Builder<Space>()
                        .setQuery(mSpaceRef, parser)
                        .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Space, SpaceViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final SpaceViewHolder holder, int position, @NonNull Space model) {
                holder.mTextViewSpaceTitle.setText(model.getTieuDe());
                holder.mTextViewAddress.setText("phường " + model.getPhuongId() + ", " + model.getQuanId() + ", " + model.getThanhPhoId());
                holder.mTextViewPrice.setText(model.getDienTich() + " - " + model.getGia());
//                if (!model.getImagePath().isEmpty()) {
//                    String imageURl = model.getImagePath().get(0);
//                    if (imageURl.startsWith("gs://")) {
//                        StorageReference storageRef = FirebaseStorage.getInstance()
//                                .getReferenceFromUrl(imageURl);
//                        storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Uri> task) {
//                                if (task.isSuccessful()) {
//                                    String downloadUrl = task.getResult().toString();
//                                    Glide.with(holder.mImageView.getContext())
//                                            .load(downloadUrl).into(holder.mImageView);
//                                } else {
//                                    Toast.makeText(holder.mImageView.getContext(), "Error in loading image by Glide", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    }else{
//                        Glide.with(holder.mImageView.getContext())
//                                .load(model.getImagePath()).into(holder.mImageView);
//                    }
//                }
            }
            //TODO: tạo Activity thêm dữ liệu, phải làm Authentication trước

            @NonNull
            @Override
            public SpaceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new SpaceViewHolder(inflater.inflate(R.layout.layout_item_recyclerview_search, viewGroup, false));
            }
        };
        mRecycleView.setAdapter(mFirebaseAdapter);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        mFirebaseAdapter.startListening();
        super.onResume();
    }

    @Override
    public void onStop() {
        mFirebaseAdapter.stopListening();
        super.onStop();
    }
}
