package com.example.accidentapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AccDtoAdapter extends BaseAdapter {

    ArrayList<AccDto> arrayList = new ArrayList<>();

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        AccDto item = arrayList.get(position);

        TextView sub_title = (TextView)convertView.findViewById(R.id.subTitle);
        TextView distance = (TextView)convertView.findViewById(R.id.distance);
        TextView occur = (TextView)convertView.findViewById(R.id.occur);
        TextView gyung = (TextView)convertView.findViewById(R.id.gyung);
        TextView joong = (TextView)convertView.findViewById(R.id.joong);
        TextView samang = (TextView)convertView.findViewById(R.id.samang);

        String dis = String.format("%.2f", item.getDistance());

        sub_title.setText("위치 : " + item.getSubtitle());
        distance.setText(dis + " 미터");
        occur.setText("발생건수 : " +(int)item.getOccrrnc_cnt_txt() + " 건");
        gyung.setText("경상자 : "+(int)item.getSl_dnv_cnt_txt() + " 명");
        joong.setText("중상자 : " +(int)item.getSe_dnv_cnt_txt() + " 명");
        samang.setText("사망자 : " +(int)item.getDth_dnv_cnt_txt() + " 명");


        return convertView;
    }

    public void addItem(String sub_title, double distance, double occrrnc_cnt_txt, double dth_dnv_cnt_txt, double se_dnv_cnt_txt, double sl_dnv_cnt_txt){
        AccDto accDto = new AccDto();
        accDto.setSubtitle(sub_title);
        accDto.setDistance(distance);
        accDto.setOccrrnc_cnt_txt(occrrnc_cnt_txt);
        accDto.setDth_dnv_cnt_txt(dth_dnv_cnt_txt);
        accDto.setSe_dnv_cnt_txt(se_dnv_cnt_txt);
        accDto.setSl_dnv_cnt_txt(sl_dnv_cnt_txt);

        arrayList.add(accDto);
    }
}
