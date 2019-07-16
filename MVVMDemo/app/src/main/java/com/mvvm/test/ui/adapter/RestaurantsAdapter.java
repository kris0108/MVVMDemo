package com.mvvm.test.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.mvvm.test.databinding.RestaurantListItemBinding;
import com.mvvm.test.R;
import com.mvvm.test.model.Results;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.RestaurantViewHolder> {

    private List<? extends Results> mRestaurantsList;

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RestaurantListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.restaurant_list_item,
                        viewGroup, false);
        return new RestaurantViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Results results = mRestaurantsList.get(position);
        Picasso.with(holder.imageView.getContext())
                .load(results.getImageUrl())
                .error(R.drawable.rest_error)
                .fit()
                .into(holder.imageView);

        holder.titleView.setText(results.getName());
        holder.cuisineView.setText(results.getDescription());
        holder.statusView.setText(results.getStatus());
    }

    @Override
    public int getItemCount() {
        return mRestaurantsList == null ? 0 : mRestaurantsList.size();
    }

    static class RestaurantViewHolder extends RecyclerView.ViewHolder {

        final RestaurantListItemBinding binding;
        public ImageView imageView;
        public TextView titleView;
        public TextView cuisineView;
        public TextView statusView;
        public RestaurantViewHolder(RestaurantListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            imageView = binding.thumbnailImg;
            titleView = binding.title;
            cuisineView = binding.cuisine;
            statusView = binding.status;
        }
    }

    public void setRestaurantsList(final List<? extends Results> restaurantsList) {
        Log.d("REST","setRestaurantsList");
        if (this.mRestaurantsList == null) {
            this.mRestaurantsList = restaurantsList;
            notifyItemRangeInserted(0, restaurantsList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return RestaurantsAdapter.this.mRestaurantsList.size();
                }

                @Override
                public int getNewListSize() {
                    return restaurantsList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return RestaurantsAdapter.this.mRestaurantsList.get(oldItemPosition).getId() ==
                            restaurantsList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Results result = restaurantsList.get(newItemPosition);
                    Results old = restaurantsList.get(oldItemPosition);
                    return result.getId() == old.getId()
                            && Objects.equals(result.getName(), old.getName());
                }
            });
            this.mRestaurantsList = restaurantsList;
            result.dispatchUpdatesTo(this);
        }
    }

}
