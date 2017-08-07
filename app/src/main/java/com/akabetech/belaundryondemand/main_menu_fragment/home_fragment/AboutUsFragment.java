package com.akabetech.belaundryondemand.main_menu_fragment.home_fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliReviewAdapter;
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
public class AboutUsFragment extends Fragment {
    @BindView(R.id.about_us_logo) ImageView logoImageView;
    @BindView(R.id.webview) WebView webView;
    @BindView(R.id.first_star_image) ImageView firstStarImage;
    @BindView(R.id.second_star_image) ImageView secondStarImage;
    @BindView(R.id.third_star_image) ImageView thirdStarImage;
    @BindView(R.id.fourth_star_image) ImageView fourthStarImage;
    @BindView(R.id.fifth_star_image) ImageView fifthStarImage;

    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;
    @BindView(R.id.pgLoading)
    ProgressBar progressBar;
    ImageView[] stars;

    private Store myStore;

    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_about_us, container, false);
        ButterKnife.bind(this,v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        provideMyStoreData();

        stars = new ImageView[]{firstStarImage, secondStarImage, thirdStarImage, fourthStarImage, fifthStarImage};
    }
    private void provideMyStoreData(){
        myStore = (Store)getArguments().getParcelable(StoreDialog.STORE);
        mainLayout.setVisibility(View.GONE);

        if(myStore!=null){
            progressBar.setVisibility(View.GONE);
            mainLayout.setVisibility(View.VISIBLE);
            Picasso.with(getContext()).load("http://owner.belaundry.co.id/assets/images/logo/"+myStore.getLogo()).placeholder(R.mipmap.placeholder).into(this.logoImageView);
            webView.loadData(myStore.getAboutUs(), null, null);
            AdapterComponent.getAdapter(BeAsliReviewAdapter.class).get(myStore.getCode()).enqueue(new Callback<List<Review>>() {

                @Override
                public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                    if (response.code() == 200) {

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
        }
    }
}
