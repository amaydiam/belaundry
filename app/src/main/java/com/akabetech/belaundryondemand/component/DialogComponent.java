package com.akabetech.belaundryondemand.component;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.provider.Settings;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.model.SimpleActionSheetItem;

/**
 * Created by akbar.pambudi on 8/20/2016.
 */
public class DialogComponent {
    private static DialogComponent instance;
    private DialogComponent(){

    }

    public static DialogComponent getInstance() {
        if(instance== null) instance = new DialogComponent();
        return instance;
    }

    public AlertDialog getEnableGpsDialog(String title, String message, final Activity activity){
        return   new AlertDialog
                      .Builder(activity)
                      .setTitle(title)
                      .setMessage(message)
                      .setNegativeButton("close App", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            activity.finishAffinity();
                                        }
                        })
                      .setPositiveButton("enable gps", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                                        }
                      })
                      .create();


    }

    public AlertDialog getActionSheetDialog(String title,final SimpleActionSheetItem[] simpleActionSheetItems, final Activity activity){
        ListView listView = new ListView(activity);
        String[] contents = new String[simpleActionSheetItems.length];
        int index = 0;
        for(SimpleActionSheetItem i : simpleActionSheetItems){
            contents[index++] = i.getItemText();

        }
        listView.setAdapter(new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,contents));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if(!simpleActionSheetItems[position].isNeedResultCallback())
                activity.startActivity(simpleActionSheetItems[position].getDirectionIntent());
                else{
                   activity.startActivityForResult(simpleActionSheetItems[position].getDirectionIntent(),simpleActionSheetItems[position].getCallback());
               }
            }
        });

        return new AlertDialog.Builder(activity).setView(listView).setTitle(title).create();
    }


    public AlertDialog createDialogMapQuestion(Activity activity, DialogInterface.OnClickListener positive, DialogInterface.OnClickListener negative) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);
        alertBuilder.setMessage("Do you want to start turn by turn navigation?").setPositiveButton("Yes", positive).setNegativeButton("No", negative);

        return alertBuilder.create();
    }

    public AlertDialog createDialog(Activity activity, String title, String message, String positiveBtnText, String negativeBtnText, final View.OnClickListener onPositive, View.OnClickListener onNegative){
        final AlertDialog.Builder aleBuilder =new  AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View v = inflater.inflate(R.layout.alert_dialog,null);
        final Button positiveBtn = (Button)v.findViewById(R.id.alert_dialog_btn_ok);
        Button negativeBtn = (Button)v.findViewById(R.id.alert_dialog_btn_no);

        positiveBtn.setOnClickListener(onPositive);
        negativeBtn.setOnClickListener(onNegative);
        return aleBuilder.setView(v).create();
    }


    public int starRating = 3;
    private ImageButton[] buttons;
    public AlertDialog createReviewDialog(Activity context, TextWatcher textWatcher, final View.OnClickListener onPositive, View.OnClickListener onNegative) {
        starRating = 3;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(R.layout.review_dialog, null);


        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        EditText reviewEdittext = (EditText) v.findViewById(R.id.reviewEdittext);
        reviewEdittext.getLayoutParams().height = (int)(height * 0.2);
        ((EditText)v.findViewById(R.id.reviewEdittext)).addTextChangedListener(textWatcher);
        v.findViewById(R.id.alert_dialog_btn_no).setOnClickListener(onPositive);
        v.findViewById(R.id.alert_dialog_btn_ok).setOnClickListener(onNegative);

        buttons = new ImageButton[]{
                (ImageButton)v.findViewById(R.id.first_star_button),
                (ImageButton)v.findViewById(R.id.second_star_button),
                (ImageButton)v.findViewById(R.id.third_star_button),
                (ImageButton)v.findViewById(R.id.fourth_star_button),
                (ImageButton)v.findViewById(R.id.fifth_star_button)};

        for (ImageButton button: buttons) {
            button.setOnClickListener(new RatingClickListener());
        }

        return builder.setView(v).create();
    }

    class RatingClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.first_star_button) {
                starRating = 1;
            } else if (view.getId() == R.id.second_star_button ) {
                starRating = 2;
            } else if (view.getId() == R.id.third_star_button) {
                starRating = 3;
            } else if (view.getId() == R.id.fourth_star_button ) {
                starRating = 4;
            } else if (view.getId() == R.id.fifth_star_button) {
                starRating = 5;
            }

            for (int i = 0; i < buttons.length; i++) {
                ImageButton button = buttons[i];
                button.setImageResource(i < starRating ? R.drawable.star : R.drawable.star_hole);
            }
        }
    }
    public interface GetStarRating {
        void getRating(int rating);
    }
}
