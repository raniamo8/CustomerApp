package com.example.customerapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.MapView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Represents an adapter for the RecyclerView in the ExploreFragment, responsible for displaying a list of store details.
 * When a store details item is clicked, it navigates to the StoreDetailsFragment to show more information about the selected store.
 */
public class StoreDetailsAdapter extends RecyclerView.Adapter<StoreDetailsAdapter.StoreViewHolder> {
    private ArrayList<StoreDetails> storeList;
    private Context context;

    public StoreDetailsAdapter(Context context, ArrayList<StoreDetails> storeList) {
        this.context = context;
        this.storeList = storeList;
    }


    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_details, parent, false);
        return new StoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        StoreDetails store = storeList.get(position);
        holder.storeNameTextView.setText(store.getName());

        String logoUrl = store.getLogo();
        Picasso.get().load(logoUrl).into(holder.storeLogoImageView);

        Picasso.get().load(logoUrl).into(holder.storeLogoImageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.d("Picasso", "Logo-Bild erfolgreich im Adapter geladen");
            }

            @Override
            public void onError(Exception e) {
                Log.e("Picasso", "Fehler beim Laden des Logo-Bildes im Adapter", e);
            }
        });

        holder.openDetails.setOnClickListener(view -> goToStoreDetailsFragment(store));
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    static class StoreViewHolder extends RecyclerView.ViewHolder {
        ImageView storeLogoImageView;
        TextView storeNameTextView;
        ImageView openStoreDetails;
        LinearLayout openDetails;


        StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            storeLogoImageView = itemView.findViewById(R.id.storeLogoImageView);
            storeNameTextView = itemView.findViewById(R.id.storeNameTextView);
            openStoreDetails = itemView.findViewById(R.id.openStoreDetails);
            openDetails = itemView.findViewById(R.id.openDetails);


        }
    }

    private void goToStoreDetailsFragment(StoreDetails store) {
        FragmentManagerHelper.goToFragment(
                ((AppCompatActivity) context).getSupportFragmentManager(),
                R.id.frame_layout,
                StoreDetailsFragment.newInstance(store),
                R.anim.slide_in,
                R.anim.slide_out,
                true
        );
    }

}