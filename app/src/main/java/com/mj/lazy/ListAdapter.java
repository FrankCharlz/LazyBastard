package com.mj.lazy;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Frank on 3/29/2016.
 *
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private final Context context;
    private final List<UssdCode> ussdCodes;

    public ListAdapter(Context context, List<UssdCode> ussdCodes) {
        this.context = context;
        this.ussdCodes = ussdCodes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(ussdCodes.get(position).getName());
        holder.code.setText(ussdCodes.get(position).getCode());

        if (position % 2 == 0 ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.itemView.setBackground(new ColorDrawable(ContextCompat.getColor(context, R.color.table)));
            }
        }


    }


    @Override
    public int getItemCount() {
        return ussdCodes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final int TAG_DELETE = 0xf9;
        private final TextView name, code;
        private final ImageView delete;


        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_name);
            code = (TextView) view.findViewById(R.id.tv_code);
            delete = (ImageView) view.findViewById(R.id.imgv_delete);

            name.setOnClickListener(this);
            code.setOnClickListener(this);
            delete.setOnClickListener(this);

            delete.setTag(TAG_DELETE);

        }

        @Override
        public void onClick(View view) {
            //uses tag and position to determine right method to call
            int pos = getLayoutPosition();
            if (view.getTag() != null && view.getTag().equals(TAG_DELETE)) {
                //delete clicked...
                ussdCodes.remove(pos);
                notifyItemRemoved(pos);
            } else {
                //dial the bitch..
                dialCode(ussdCodes.get(pos));
            }
        }
    }

    private void dialCode(UssdCode ussdCode) {
        ussdCode.incrementFrequency();
        String number = ussdCode.getCode();
        number = number.replaceAll("#", "%23");
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        context.startActivity(intent);
    }
}
