package com.example.calculate24points.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.calculate24points.R;
import com.example.calculate24points.bean.PokerCard;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PokerCardAdapter extends BaseAdapter {
    private Context context;
    private List<PokerCard> pokerCards;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<PokerCard> getPokerCards() {
        return pokerCards;
    }

    public void setPokerCards(List<PokerCard> pokerCards) {
        this.pokerCards = pokerCards;
    }

    public PokerCardAdapter(Context context, List<PokerCard> pokerCards) {
        this.context = context;
        this.pokerCards = pokerCards;
    }

    @Override
    public int getCount() {
        return pokerCards.size();
    }

    @Override
    public Object getItem(int position) {
        return pokerCards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.poker_image, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.poker_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PokerCard pokerCard = pokerCards.get(position);
        String name = pokerCard.getSuit().name().toLowerCase(Locale.ROOT) + pokerCard.getNumber();
        int resId = convertView.getResources().getIdentifier(name, "drawable", context.getPackageName());
        viewHolder.imageView.setImageResource(resId);
        return convertView;
    }

    private final class ViewHolder {
        public ImageView imageView;
    }
}
