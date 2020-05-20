package comp5216.sydney.edu.au.garbagecollection;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PopUpUtil {
    final String PREFERENCES = "user info";
    public PopUpUtil(){
        super();
    }

    void editFormPopUp(final Context context, String name, String phone, String address, String postcode,
                       final TextView nameView, final TextView phoneView, final TextView addressView,
                       final TextView postcodeView, LinearLayout contactLayout)
    {
        EditContactForm form = new EditContactForm(context, null,
                name, phone, address, postcode);
        final PopupWindow mPopupWindow = new PopupWindow(form, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);

        final EditText newName = (EditText) form.findViewById(R.id.username_value);
        final EditText newPhone = (EditText) form.findViewById(R.id.phone_value);
        final EditText newAddress = (EditText) form.findViewById(R.id.address_value);
        final EditText newPostcode = (EditText) form.findViewById(R.id.postcode_value);

        TextView closeButton = (TextView) form.findViewById(R.id.btn_cancel);
        TextView confirmButton = (TextView) form.findViewById(R.id.btn_confirm);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                String name = newName.getText().toString();
                String phone = newPhone.getText().toString();
                String address = newAddress.getText().toString();
                String postcode = newPostcode.getText().toString();

                nameView.setText(name);
                phoneView.setText(phone);
                addressView.setText(address);
                postcodeView.setText(postcode);

                SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String[] nameArray = name.split(" ");
                editor.putString("firstname", nameArray[0]);
                if(nameArray.length>1){
                    editor.putString("lastname", nameArray[1]);
                }
                editor.putString("phone", phone);
                editor.putString("address", address);
                editor.putString("postcode",postcode);
                editor.apply();
                // TODO save to firebase

                mPopupWindow.dismiss();
            }
        });

        mPopupWindow.showAtLocation(contactLayout, Gravity.CENTER, 0,0);
    }

    void collectionFormPopUp(Context context, final TextView timeView, final TextView dateView, LinearLayout contactLayout){
        CollectionTimeForm form = new CollectionTimeForm(context, null);
        final PopupWindow mPopupWindow = new PopupWindow(form, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);

        final EditText newTime = (EditText) form.findViewById(R.id.time_value);
        final EditText newDate = (EditText) form.findViewById(R.id.date_value);

        TextView closeButton = (TextView) form.findViewById(R.id.btn_cancel);
        TextView confirmButton = (TextView) form.findViewById(R.id.btn_confirm);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = newTime.getText().toString();
                String date = newDate.getText().toString();
                Log.e("ConfirmRequest", time);
                if(!time.equals("")){
                    timeView.setText(time);
                }

                if(!date.equals("")){
                    dateView.setText(date);
                }
                mPopupWindow.dismiss();
            }
        });

        mPopupWindow.showAtLocation(contactLayout, Gravity.CENTER, 0,0);
    }
}
