package com.microlan.rushhandingoffline.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.microlan.rushhandingoffline.R;

import java.util.ArrayList;

public class FAQAdapter extends BaseAdapter {
    Context context;
    ArrayList <String> FAQQuestion, FAQDescription;

    public FAQAdapter (Context context1, ArrayList<String> fAQQuestion, ArrayList<String> fAQDescription)
    {
        this.context = context1;
        this.FAQQuestion = fAQQuestion;
        this.FAQDescription = fAQDescription;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.single_grid_item_faq, null);
            //holder = new RecyclerView.ViewHolder();
        } else {
            gridView = (View) convertView;
        }

        TextView FAQNameText = (TextView)gridView.findViewById(R.id.faqTitleText);
        TextView FAQDescriptionText = (TextView)gridView.findViewById(R.id.faqDescriptionText);

        FAQNameText.setText(FAQQuestion.get(position));
        FAQDescriptionText.setText(FAQDescription.get(position));

        return gridView;
    }

    @Override
    public int getCount() {
        return FAQQuestion.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}

