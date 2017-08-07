package com.akabetech.belaundryondemand.main_menu_fragment.home_fragment;

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

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.RecyclerItemClickListener;
import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.retrofit.adapter.BeasliPromoAdapter;
import com.akabetech.belaundryondemand.retrofit.model.beans.Promo;
import com.akabetech.belaundryondemand.retrofit.model.beans.Store;
import com.akabetech.belaundryondemand.ui.dialog.StoreDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PromoFragment extends Fragment {
    @BindView(R.id.promo_image_recycler)
    RecyclerView promoImageRecyclerView;
    private Unbinder unbinder;
    private List<Promo> list_promo;
    PromoAdapter adapterPromo;
    private Store myStore;
    @BindView(R.id.detailPromo)
    LinearLayout detailPromo;
    @BindView(R.id.promoImageView) ImageView promoImageView;
    @BindView(R.id.pgLoading)
    ProgressBar progressBar;
    @BindView(R.id.closePreviewPromo)
    TextView closePreviewPromo;
    @BindView(R.id.descPromo)
    TextView descPromo;
    public PromoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_promo, container, false);
        ButterKnife.bind(this, v);
        unbinder = ButterKnife.bind(this,v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list_promo=new ArrayList<>();
        detailPromo.setVisibility(View.GONE);
        promoImageRecyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        myStore = (Store)getArguments().getParcelable(StoreDialog.STORE);
        if (myStore == null)
            return;
        getPromoBystore();

    }

    private void getPromoBystore() {
        AdapterComponent.getAdapter(BeasliPromoAdapter.class).getPromosByStoreCode(myStore.getCode()).enqueue(new Callback<List<Promo>>() {
            @Override
            public void onResponse(Call<List<Promo>> call, Response<List<Promo>> response) {

                if(response.code() == 200){
                    if(response.body()!=null)
                    {
                        if(response.body().size()>0)
                        {
                            list_promo=response.body();
                        }
                        setAdapterViewHolder();
                    }

                }

            }

            @Override
            public void onFailure(Call<List<Promo>> call, Throwable t) {

            }
        });
    }

    private void setAdapterViewHolder() {
        progressBar.setVisibility(View.GONE);
        adapterPromo=new PromoAdapter(list_promo);
        promoImageRecyclerView.setAdapter(new PromoAdapter(list_promo));
        promoImageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        promoImageRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), promoImageRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Picasso.with(view.getContext()).load("http://owner.belaundry.co.id/assets/images/promo/"+list_promo.get(position).getPoster()).placeholder(R.mipmap.placeholder).into(promoImageView);
                descPromo.setText(list_promo.get(position).getDescription());
                detailPromo.setVisibility(View.VISIBLE);
                promoImageRecyclerView.setVisibility(View.GONE);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }
    @OnClick(R.id.closePreviewPromo)
    void onPromoClicked() {
        detailPromo.setVisibility(View.GONE);
        promoImageRecyclerView.setVisibility(View.VISIBLE);
    }
    @Override
    public void onPause() {
        super.onPause();
    }


    private Callback<List<Promo>> onPromoInvoked(){
        return new Callback<List<Promo>>() {
            @Override
            public void onResponse(Call<List<Promo>> call, Response<List<Promo>> response) {

            }

            @Override
            public void onFailure(Call<List<Promo>> call, Throwable t) {

            }
        };
    }



    public class PromoViewHolder extends RecyclerView.ViewHolder {

        ImageView promoImageView;
        TextView descPromo;
        private View v;
        public PromoViewHolder(View itemView) {
            super(itemView);
            descPromo =(TextView)itemView.findViewById(R.id.descPromo);
            promoImageView = (ImageView)itemView.findViewById(R.id.imageView);
            this.v=itemView;
        }
        public void bind(Promo promoHistory){
            Picasso.with(v.getContext()).load("http://owner.belaundry.co.id/assets/images/promo/"+promoHistory.getPoster()).placeholder(R.mipmap.placeholder).into(this.promoImageView);
            descPromo.setText(promoHistory.getDescription());
            promoImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }

    }
    public class PromoAdapter extends RecyclerView.Adapter<PromoViewHolder> {
        PromoViewHolder viewHolder ;
        List<Promo> list_promo;
        private AdapterView.OnItemClickListener onItemClickListener;
        public PromoAdapter(List<Promo> transactionHistories) {
            this.list_promo = transactionHistories;
        }


        @Override
        public PromoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.promo_row,parent,false);
            viewHolder = new PromoViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(PromoViewHolder holder, int position) {
            Promo currentHist =list_promo.get(position);
            holder.bind(currentHist);
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
