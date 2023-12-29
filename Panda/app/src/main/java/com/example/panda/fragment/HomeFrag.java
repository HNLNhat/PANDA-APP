package com.example.panda.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.panda.R;
import com.example.panda.adapter.NoiBatAdapter;
import com.example.panda.model.MonAn;

import java.util.ArrayList;

public class  HomeFrag extends Fragment {

    RecyclerView recyclerViewNoibat;
    ViewFlipper viewFlipper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_home,null);
        viewFlipper = view.findViewById(R.id.viewFlipper);
        recyclerViewNoibat = view.findViewById(R.id.recyclerNoibat);

        BannerFliper();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerViewNoibat.setLayoutManager(gridLayoutManager);
        NoiBatAdapter noiBatAdapter = new NoiBatAdapter(Noibat(),getContext());
        recyclerViewNoibat.setAdapter(noiBatAdapter);

        return view;
    }

    private  void BannerFliper(){
        ArrayList<String> list = new ArrayList<>();
        list.add("https://sanfulou.com/wp-content/uploads/2018/01/18-2-Copy-1024x683.jpg");
        list.add("https://sanfulou.com/wp-content/uploads/2018/01/DSC6633-Copy-1024x683.jpg");
        list.add("https://sanfulou.com/wp-content/uploads/2018/01/DSC08880-Copy-1024x683.jpg");
        list.add("https://sanfulou.com/wp-content/uploads/2018/01/CVP08895-Edit-Copy.jpg");



        for (int i = 0; i < list.size(); i++){
            ImageView imageView = new ImageView(getContext());
            Glide.with(getContext()).load(list.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewFlipper.addView(imageView);
        }

        viewFlipper.setFlipInterval(3500);
        viewFlipper.setAutoStart(true);

        Animation slideIn = AnimationUtils.loadAnimation(getContext(),R.anim.slide_in_right);
        Animation slideOut = AnimationUtils.loadAnimation(getContext(),R.anim.slide_out_right);

        viewFlipper.setInAnimation(slideIn);
        viewFlipper.setOutAnimation(slideOut);
    }


    private ArrayList<MonAn> Noibat(){

        ArrayList<MonAn> list = new ArrayList<>();

        list.add(new MonAn("Thưởng nguyệt trăng rằm – Đoàn viên hội ngộ ","https://d1-concepts.com/wp-content/uploads/2022/08/viber_image_2022-08-10_16-40-12-148-e1660210066301-300x300.jpg"));
        list.add(new MonAn("NIHAO COMBO | BỮA SÁNG BỔ DƯỠNG CHỈ VỚI 99.000+ ","https://d1-concepts.com/wp-content/uploads/2022/07/viber_image_2022-07-13_23-12-45-122-300x300.jpg"));
        list.add(new MonAn("SUMMER MENU | MÙA HÈ TẠI PANDA CÓ VỊ GÌ?","https://d1-concepts.com/wp-content/uploads/2022/07/SM_Menu_PostSocial-01-300x300.jpg"));
        list.add(new MonAn("Trường Sinh Vạn Phúc","https://d1-concepts.com/wp-content/uploads/2022/04/SFL_SetMenu_final-12-scaled-e1655359716796-300x300.jpg"));
        list.add(new MonAn("Chào Mùa Trăng 2022","https://d1-concepts.com/wp-content/uploads/2022/06/99207463942657780e37-300x300.jpg"));
        list.add(new MonAn("Ghé San Fu Lou Nhận Ngay 30% Ưu Đãi Từ VIB","https://d1-concepts.com/wp-content/uploads/2022/02/D1-x-VIB-500x500.jpg"));
        list.add(new MonAn("ƯU ĐÃI 10% DÀNH RIÊNG CHO CƯ DÂN VINHOMES CENTRAL PARK","https://d1-concepts.com/wp-content/uploads/2022/07/DiMai_Flyer-03-300x300.jpg"));
        list.add(new MonAn("LUNCH COMBO | BỮA TRƯA RÔM RẢ CHỈ VỚI 99.000+","https://d1-concepts.com/wp-content/uploads/2022/07/Mailchimp-DM-05.jpg-300x300.png"));

        return list;
    }

}
