package com.example.panda.Admin.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.panda.R;
import com.example.panda.dao.MonAnDAO;
import com.example.panda.model.MonAn;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.ViewHoler>{
    private Context context;
    private ArrayList<MonAn> list;
    private ArrayList<HashMap<String,Object>> listHM;
    private MonAnDAO monAnDAO;
    AlertDialog alertDialog;

    public MonAnAdapter(Context context, ArrayList<MonAn> list, ArrayList<HashMap<String, Object>> listHM, MonAnDAO monAnDAO) {
        this.context = context;
        this.list = list;
        this.listHM = listHM;
        this.monAnDAO = monAnDAO;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_monan_admin,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        holder.txtMamon.setText(Integer.toString(list.get(position).getMamon()));
        holder.txtTenmon.setText(list.get(position).getTenmon());
        String str = NumberFormat.getNumberInstance(Locale.US).format(list.get(position).getGia());
        holder.txtGia.setText(str+" VNĐ");
        holder.txtMota.setText(list.get(position).getMota());
        Glide.with(context).load(list.get(position).getAnh()).into(holder.imgAnh);

        holder.item_monan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_xoa_sua,null);
                builder.setView(view);

                TextView txtXoa = view.findViewById(R.id.txtXoa);
                TextView txtSua = view.findViewById(R.id.txtSua);

                txtXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                       AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                       builder1.setTitle("Thông báo");
                       builder1.setMessage("Bạn có chắc muốn xóa");

                       builder1.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               boolean check = monAnDAO.xoaMonAn(list.get(holder.getAdapterPosition()).getMamon());
                               if (check){
                                   Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                                   reLoadData();
                               }else {
                                   Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                               }
                           }
                       });

                       builder1.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                           }
                       });

                       AlertDialog alertDialog1 = builder1.create();
                       alertDialog1.show();
                    }
                });

                txtSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        capNhapMonAn(list.get(holder.getAdapterPosition()));
                        alertDialog.dismiss();
                    }
                });

                alertDialog = builder.create();
                alertDialog.show();

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        TextView txtMamon, txtTenmon, txtGia, txtMota;
        ImageView imgAnh;
        CardView item_monan;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);

            txtMamon = itemView.findViewById(R.id.txtMamon);
            txtTenmon = itemView.findViewById(R.id.txtTenmon);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtMota = itemView.findViewById(R.id.txtMota);
            imgAnh = itemView.findViewById(R.id.imgAnh);
            item_monan = itemView.findViewById(R.id.item_monan);
        }
    }

    private void reLoadData(){
        list.clear();
        list = monAnDAO.getDSMonAn();
        notifyDataSetChanged();
    }

    private void capNhapMonAn(MonAn monAn){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sua_monan,null);
        builder.setView(view);

        ImageView imgAnh = view.findViewById(R.id.imgAnh);
        EditText edtTenMon = view.findViewById(R.id.edtTenmon);
        EditText edtGia = view.findViewById(R.id.edtGia);
        EditText edtMota = view.findViewById(R.id.edtMota);
        TextView txtMaMon = view.findViewById(R.id.txtMamon);
        Spinner spinnerLoai = view.findViewById(R.id.spinner);

        txtMaMon.setText(Integer.toString(monAn.getMamon()));
        edtTenMon.setText(monAn.getTenmon());
        edtGia.setText(Integer.toString(monAn.getGia()));
        edtMota.setText(monAn.getMota());
        Glide.with(context).load(monAn.getAnh()).into(imgAnh);


        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1}
        );
        spinnerLoai.setAdapter(simpleAdapter);

        int index = 0;
        int postion = -1;
        for (HashMap<String,Object> item : listHM){
            if ((int)item.get("maloai") == monAn.getMamon()){
                postion = index;
            }
            index++;
        }
        spinnerLoai.setSelection(postion);

        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenMon = edtTenMon.getText().toString();
                int gia = Integer.parseInt(edtGia.getText().toString());
                String mota = edtMota.getText().toString();
                HashMap<String, Object> hs = (HashMap<String, Object>) spinnerLoai.getSelectedItem();
                int maloai = (int) hs.get("maloai");

                 monAn.setGia(gia);
                 monAn.setMota(mota);
                 monAn.setTenmon(tenMon);
                 monAn.setMaloai(maloai);
                boolean check = monAnDAO.capNhatMonAn(monAn);

                if (check){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    reLoadData();
                }else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog1 = builder.create();
        alertDialog1.show();
    }
}
