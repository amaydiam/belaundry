package com.akabetech.belaundryondemand.main_menu_fragment.home_fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliReplyReviewAdapter;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliReviewAdapter;
import com.akabetech.belaundryondemand.retrofit.model.beans.ReplyReview;
import com.akabetech.belaundryondemand.retrofit.model.beans.Review;
import com.akabetech.belaundryondemand.retrofit.model.beans.Store;
import com.akabetech.belaundryondemand.ui.dialog.StoreDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {

    @BindView(R.id.pgLoading)
    ProgressBar progressBar;
    @BindView(R.id.reviewRecyclerView) RecyclerView reviewRecyclerView;
    @BindView(R.id.first_star_image) ImageView firstStarImage;
    @BindView(R.id.second_star_image) ImageView secondStarImage;
    @BindView(R.id.third_star_image) ImageView thirdStarImage;
    @BindView(R.id.fourth_star_image) ImageView fourthStarImage;
    @BindView(R.id.fifth_star_image) ImageView fifthStarImage;
    @BindView(R.id.img_logo_title_review) ImageView img_logo_title_review;
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;
    ImageView[] stars;

    public ReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        ButterKnife.bind(this, view);



        stars = new ImageView[]{firstStarImage, secondStarImage, thirdStarImage, fourthStarImage, fifthStarImage};

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Store store = (Store)getArguments().getParcelable(StoreDialog.STORE);

        if (store == null)
            return;
        mainLayout.setVisibility(View.GONE);
        Log.i("INFO", store.getCode());
        Picasso.with(getContext()).load("http://owner.belaundry.co.id/assets/images/logo/"+store.getLogo()).placeholder(R.mipmap.placeholder).into(this.img_logo_title_review);
        AdapterComponent.getAdapter(BeAsliReviewAdapter.class).get(store.getCode()).enqueue(new Callback<List<Review>>() {

            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if (response.code() == 200) {
                    mainLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    reviewRecyclerView.setAdapter(new ReviewRecyclerAdapter(response.body()));
                    int averageRating = 0;

                    for(Review review:response.body()) {
                        averageRating += Integer.valueOf(review.getRating());
                    }

                    if (averageRating != 0)
                        averageRating = averageRating / response.body().size();

                    for (int i = 0; i < stars.length; i++) {
                        stars[i].setImageResource(i < averageRating ? R.drawable.star : R.drawable.star_hole);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                for (int i = 0; i < stars.length; i++) {
                    stars[i].setImageResource(R.drawable.star_hole);
                }
            }
        });

        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    class ReplyReviewRecylerAdapter extends RecyclerView.Adapter<ReplyReviewHolder>{
        private final String nmOutlets;
        List<ReplyReview> replyReviews;
        ReplyReviewRecylerAdapter(List<ReplyReview> replyReviewList,String name_outlets) {
            this.replyReviews = replyReviewList;
            this.nmOutlets=name_outlets;
        }
        @Override
        public ReplyReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_reply_review, parent, false);
            return new ReplyReviewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ReplyReviewHolder holder, int position) {
            ReplyReview replyReview=replyReviews.get(position);
            holder.nmOutlets_reply.setText(nmOutlets);
            holder.outlets_reply.setText(replyReview.getReview());
        }

        @Override
        public int getItemCount() {
            return this.replyReviews.size();
        }
    }
    class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
        List<ReplyReview> replyReviews;
        List<Review> reviews;

        ReviewRecyclerAdapter(List<Review> reviews) {
            this.reviews = reviews;
        }

        @Override
        public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.review_list_row, parent, false);

            return new ReviewViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ReviewViewHolder holder, int position) {
            Review review = reviews.get(position);

            holder.reviewTextView.setText(review.getReview());
            String userId = review.getIdUserBeasli();
            Picasso.with(getContext())
                    .load("http://teikin.com/webservices/public/get-picture/" + userId)
                    .error(R.drawable.prof_pic).placeholder(R.drawable.prof_pic)
                    .into(holder.userImageView);
            int rating = Integer.valueOf(review.getRating());

            for (int i = 0; i < stars.length; i++) {
                holder.stars[i].setImageResource(i < rating ? R.drawable.star : R.drawable.star_hole);
            }
            AdapterComponent.getAdapter(BeAsliReplyReviewAdapter.class).get(review.getIdReview()).enqueue(new Callback<List<ReplyReview>>() {
                @Override
                public void onResponse(Call<List<ReplyReview>> call, Response<List<ReplyReview>> response) {
                    Store store = (Store)getArguments().getParcelable(StoreDialog.STORE);
                    holder.rv_list_reply.setAdapter(new ReplyReviewRecylerAdapter(response.body(),store.getName()));
                    holder.rv_list_reply.setLayoutManager(new LinearLayoutManager(getActivity()));
                }

                @Override
                public void onFailure(Call<List<ReplyReview>> call, Throwable t) {

                }
            });

        }

        @Override
        public int getItemCount() {
            return this.reviews.size();
        }
    }

    class ReplyReviewHolder extends RecyclerView.ViewHolder{
        ImageView profpic_reply;
        TextView nmOutlets_reply;
        TextView outlets_reply;

        public ReplyReviewHolder(View itemView) {
            super(itemView);
            profpic_reply = (ImageView)itemView.findViewById(R.id.profpic_reply);
            nmOutlets_reply = (TextView)itemView.findViewById(R.id.nmOutlets_reply);
            outlets_reply = (TextView)itemView.findViewById(R.id.outlets_reply);
        }
    }
    class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView reviewTextView;
        ImageView userImageView;
        ImageView firstStar;
        ImageView secondStar;
        ImageView thirdStar;
        ImageView fourthStar;
        ImageView fifthStar;
        ImageView[] stars;
        RecyclerView rv_list_reply;
        public ReviewViewHolder(View view) {
            super(view);

            nameTextView = (TextView)view.findViewById(R.id.nameTextView);
            reviewTextView = (TextView)view.findViewById(R.id.reviewTextView);
            userImageView = (ImageView)view.findViewById(R.id.userImageView);
            firstStar = (ImageView)view.findViewById(R.id.first_star_image);
            secondStar = (ImageView)view.findViewById(R.id.second_star_image);
            thirdStar = (ImageView)view.findViewById(R.id.third_star_image);
            fourthStar = (ImageView)view.findViewById(R.id.fourth_star_image);
            fifthStar = (ImageView)view.findViewById(R.id.fifth_star_image);
            rv_list_reply = (RecyclerView) view.findViewById(R.id.rv_list_reply);

            stars = new ImageView[]{firstStar, secondStar, thirdStar, fourthStar, fifthStar};
        }
    }
}
