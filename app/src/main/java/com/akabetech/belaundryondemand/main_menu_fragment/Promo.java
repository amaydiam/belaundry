package com.akabetech.belaundryondemand.main_menu_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.RecyclerItemClickListener;
import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.main_menu_fragment.history_transaction_fragment.ActivityHistoryDetailItemTransaksi;
import com.akabetech.belaundryondemand.main_menu_fragment.home_fragment.PromosFragment;
import com.akabetech.belaundryondemand.main_menu_fragment.promo_fragment.DetailPromo;
import com.akabetech.belaundryondemand.retrofit.adapter.BeasliPromoAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mrari on 5/17/2017.
 */

public class Promo extends Fragment {
    public static Promo newInstance() {
        Promo fragment = new Promo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.pgLoading)
    ProgressBar progressBar;
    private List<com.akabetech.belaundryondemand.retrofit.model.beans.Promo> promos;
    @BindView(R.id.detailPromo)
    LinearLayout detailPromo;
    @BindView(R.id.promoImageView)
    ImageView promoImageView;
    @BindView(R.id.closePreviewPromo)
    TextView closePreviewPromo;
    @BindView(R.id.descPromo)
    TextView descPromo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_promos,container,false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        promos=new ArrayList<>();
        detailPromo.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        AdapterComponent.getAdapter(BeasliPromoAdapter.class).getAllPromo().enqueue(new Callback<List<com.akabetech.belaundryondemand.retrofit.model.beans.Promo>>() {
            @Override
            public void onResponse(Call<List<com.akabetech.belaundryondemand.retrofit.model.beans.Promo>> call, Response<List<com.akabetech.belaundryondemand.retrofit.model.beans.Promo>> response) {
                if (response.code() == 200) {
                    if(response.body()!=null)
                    {
                        if(response.body().size()>0)
                        {
                            promos=response.body();
                            progressBar.setVisibility(View.GONE);
                            Promo.PromoAdapter adap=new Promo.PromoAdapter(promos);
                            recyclerView.setAdapter(adap);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//                            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(View view, int position) {
//                                    Picasso.with(view.getContext()).load("http://owner.belaundry.co.id/assets/images/promo/"+promos.get(position).getPoster()).placeholder(R.mipmap.placeholder).into(promoImageView);
//                                    descPromo.setText(promos.get(position).getDescription());
//                                    detailPromo.setVisibility(View.VISIBLE);
//                                    recyclerView.setVisibility(View.GONE);
//                                }
//
//                                @Override
//                                public void onLongItemClick(View view, int position) {
//
//                                }
//                            }));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<com.akabetech.belaundryondemand.retrofit.model.beans.Promo>> call, Throwable t) {
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(),"Have trouble with connection to server",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @OnClick(R.id.closePreviewPromo)
    void onPromoClicked() {
        detailPromo.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public class PromoViewHolder extends RecyclerView.ViewHolder {

        ImageView promoImageView;
        TextView detailPromo;
        TextView descPromo;
        private View v;
        public PromoViewHolder(View itemView) {
            super(itemView);
            promoImageView = (ImageView)itemView.findViewById(R.id.imageView);
            detailPromo = (TextView)itemView.findViewById(R.id.detailPromo);
            descPromo = (TextView)itemView.findViewById(R.id.descPromo);
            this.v=itemView;
        }
        public void bind(final com.akabetech.belaundryondemand.retrofit.model.beans.Promo promoHistory){
            descPromo.setText(promoHistory.getDescription());
            Picasso.with(v.getContext()).load("http://owner.belaundry.co.id/assets/images/promo/"+promoHistory.getPoster()).placeholder(R.mipmap.placeholder).into(this.promoImageView);
            promoImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            detailPromo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailPromo.setInstance(promoHistory);
                    startActivity(new Intent(getContext(), DetailPromo.class));
                }
            });
        }

    }
    public class PromoAdapter extends RecyclerView.Adapter<Promo.PromoViewHolder> {
        Promo.PromoViewHolder viewHolder ;
        List<com.akabetech.belaundryondemand.retrofit.model.beans.Promo> list_promo;
        private AdapterView.OnItemClickListener onItemClickListener;
        public PromoAdapter(List<com.akabetech.belaundryondemand.retrofit.model.beans.Promo> transactionHistories) {
            this.list_promo = transactionHistories;
        }


        @Override
        public Promo.PromoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.promo_row,parent,false);
            viewHolder = new Promo.PromoViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(Promo.PromoViewHolder holder, int position) {
            final com.akabetech.belaundryondemand.retrofit.model.beans.Promo currentHist =list_promo.get(position);
            holder.bind(currentHist);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
//            if(onItemClickListener!=null) holder.setOnItemClickListener(position,onItemClickListener);
        }

        public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public int getItemCount() {
            return list_promo.size();
        }


    }
}
