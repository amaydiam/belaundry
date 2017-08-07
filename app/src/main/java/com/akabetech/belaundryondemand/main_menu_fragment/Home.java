package com.akabetech.belaundryondemand.main_menu_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akabetech.belaundryondemand.MainActivity;
import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.main_menu_fragment.history_transaction_fragment.CompleteHistoryTransaction;
import com.akabetech.belaundryondemand.model.MenuHome;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends Fragment implements ViewPager.OnPageChangeListener {
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    RecyclerView rcListview;
    ImageView btn_next;
    LinearLayout pager_indicator;
    private int[] mImageResources = {
            R.drawable.slide1,
            R.drawable.slide2,
            R.drawable.slide3
    };
    private int[] mImageResourcesMenu={
            R.drawable.ic_clothes,
            R.drawable.ic_shoes,
            R.drawable.ic_bag,
            R.drawable.ic_carpet,
            R.drawable.ic_pickup,
            R.drawable.ic_sofa,
            R.drawable.ic_bed,
            R.drawable.ic_stoller
    };
    private String[] labelMenu={
            "Clothes",
            "Shoes",
            "Bag",
            "Carpet",
            "Pick Up",
            "Sofa",
            "Bed",
            "Stoller"
    };
    private AutoScrollViewPager intro_images;
    private ViewPagerAdapter mAdapter;
    private int dotsCount;
    private ImageView[] dots;
    private boolean mfocus=false;
    private float touchX;
    private View v;
    private CardView cardViewClohes;
    private RecyclerView rv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.item, container, false);
        return v;

    }
    public static Home newInstance() {
        Home fragment = new Home();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pager_indicator = (LinearLayout) view.findViewById(R.id.viewPagerCountDots);
        intro_images = (AutoScrollViewPager) view.findViewById(R.id.vp_contentAds);
        rv=(RecyclerView)view.findViewById(R.id.rv);
        List<MenuHome> menuHomeList = new ArrayList<>();
        for(int pos=0;pos<mImageResourcesMenu.length;)
        {
            MenuHome objMenuHome=new MenuHome();
            objMenuHome.setIcon(mImageResourcesMenu[pos]);
            objMenuHome.setLabel(labelMenu[pos]);
            objMenuHome.setId(labelMenu[pos]);
            menuHomeList.add(objMenuHome);
            Log.e("data",pos+"");
            pos++;
        }
        rv.setAdapter(new adapter(menuHomeList));
        rv.setLayoutManager(new GridLayoutManager(getContext(),3));
        cardViewClohes=(CardView)view.findViewById(R.id.cardViewClothes);
        mAdapter = new ViewPagerAdapter(getContext(), mImageResources);
        cardViewClohes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        intro_images.setAdapter(mAdapter);
        intro_images.setCurrentItem(0);
        intro_images.addOnPageChangeListener(this);
        intro_images.setInterval(2000);
        intro_images.startAutoScroll();

        intro_images.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        intro_images.stopAutoScroll();
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        float deltaX = x2 - x1;
                        if (Math.abs(x2) > x1)
                        {
                            int selectpos=(intro_images.getCurrentItem()+1 == 1)
                                    ? dotsCount-1 : intro_images.getCurrentItem()-1;
                            intro_images.setCurrentItem(selectpos);
                        }
                        else
                        {
                            int selectpos=(intro_images.getCurrentItem()+1 < dotsCount)
                                    ? intro_images.getCurrentItem() + 1 : 0;
                            intro_images.setCurrentItem(selectpos);
                        }
                        intro_images.startAutoScroll(2000);
                        break;
                }
                return true;


                /**
                 * current index is first one and slide to right or current index is last one and slide to left.<br/>
                 * if slide border mode is to parent, then requestDisallowInterceptTouchEvent false.<br/>
                 * else scroll to last one when current item is first one, scroll to first one when current item is last
                 * one.
                 */


            }
        });
        setUiPageViewController();
    }


//    @Override
//    public void onPause() {
//        super.onPause();
//        // stop auto scroll when onPause
//        intro_images.stopAutoScroll();
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        // start auto scroll when onResume
//        intro_images.startAutoScroll();
//    }

    public class viewHolder extends RecyclerView.ViewHolder{

        private final CircleImageView iconMenu;
        private final TextView labelMenu;

        public viewHolder(View itemView) {
            super(itemView);
            iconMenu=(CircleImageView)itemView.findViewById(R.id.iconMenu);
            labelMenu=(TextView)itemView.findViewById(R.id.labelMenu);
        }
        public void bind(MenuHome object)
        {
           iconMenu.setImageResource(object.getIcon());
           labelMenu.setText(object.getLabel());
        }
    }
    public class adapter extends RecyclerView.Adapter<viewHolder>
    {

        private final List<MenuHome> list;

        public adapter(List<MenuHome> menuHomeList)
        {
            this.list=menuHomeList;
        }
        private viewHolder holder;

        @Override
        public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvhome,parent,false);
            holder = new viewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(viewHolder holder, int position) {
            final MenuHome obj = list.get(position);
            holder.bind(obj);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionMenu(obj.getId());

                }
            });
        }

        private void actionMenu(String id) {
            switch (id.toLowerCase())
            {
                case "clothes":
                    startActivity(new Intent(getContext(), MainActivity.class));
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return list==null?0:list.size();
        }
    }
    private void setUiPageViewController() {
        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        boolean fc = intro_images.getFilterTouchesWhenObscured();



    }

    @Override
    public void onPageSelected(int position) {
        String uri = "@drawable/myresource";
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageResource(R.drawable.nonselecteditem_dot);
        }

        dots[position].setImageResource(R.drawable.selecteditem_dot);


    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private class ViewPagerAdapter extends PagerAdapter {
        private final Context mContext;
        private final int[] mResources;

        public ViewPagerAdapter(Context applicationContext, int[] mImageResources) {
            this.mContext = applicationContext;
            this.mResources = mImageResources;
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ConstraintLayout) object);
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.newitem, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
            imageView.setImageResource(mResources[position]);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ConstraintLayout) object);
        }
    }
}

