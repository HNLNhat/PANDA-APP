package com.example.panda.Admin.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panda.R;
import com.example.panda.Admin.adapter.MonAnAdapter;
import com.example.panda.dao.LoaiDAO;
import com.example.panda.dao.MonAnDAO;
import com.example.panda.model.LoaiMon;
import com.example.panda.model.MonAn;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;



public class MonAnFrag extends Fragment {

    RecyclerView recyclerViewMonAn;
    ImageView imgAnh;
    MonAnDAO monAnDAO;
    ArrayList<MonAn> list;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef;
    String hinhAnh = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_monan_frag,container,false);

        storageRef = storage.getReferenceFromUrl("gs://duan1-89dc4.appspot.com");

        recyclerViewMonAn = view.findViewById(R.id.recylerViewMon);
        FloatingActionButton fabMonAn = view.findViewById(R.id.fabMon);
        monAnDAO = new MonAnDAO(getContext());

        loadData();

        fabMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });



        return view;
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themmon, null);
        builder.setView(view);

        Button btnThemAnh = view.findViewById(R.id.btnThemAnh);
        imgAnh = view.findViewById(R.id.imgAnh);
        EditText edtTenMon = view.findViewById(R.id.edtTenmon);
        EditText edtGia = view.findViewById(R.id.edtGia);
        EditText edtMota = view.findViewById(R.id.edtMota);
        Spinner spinnerLoai = view.findViewById(R.id.spinner);

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),
                getDSLoai(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1}
        );
        spinnerLoai.setAdapter(simpleAdapter);

        btnThemAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               chonAnh();
            }
        });

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenMon = edtTenMon.getText().toString();
                int gia = Integer.parseInt(edtGia.getText().toString());
                String mota = edtMota.getText().toString();
                HashMap<String, Object> hs = (HashMap<String, Object>) spinnerLoai.getSelectedItem();
                int maloai = (int) hs.get("maloai");


                Calendar calendar = Calendar.getInstance();

                StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");

                // Get the data from an ImageView as bytes
                imgAnh.setDrawingCacheEnabled(true);
                imgAnh.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imgAnh.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                hinhAnh = uri + "";
                                boolean check = monAnDAO.themMonAn(maloai,tenMon,gia,mota, hinhAnh);

                                if (check){
                                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    loadData();
                                }else {
                                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Tải không thành công", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        Toast.makeText(getContext(), "Đang tải", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK){
            if (data.getExtras() != null){ // lấy từ camera
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                imgAnh.setImageBitmap(bitmap);

            }else { // lấy từ thư viện
                Uri duongdan =  data.getData();
                imgAnh.setImageURI(duongdan);

            }
        }
    }

    public void chonAnh(){
        //intent truy xuất thư mục hình ảnh trong máy
        Intent thuvien = new Intent(Intent.ACTION_GET_CONTENT);
        thuvien.setType("image/*");

        // intent dùng camera của máy để chụp hình
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // intent tích hợp 2 intent trên thành 1 hop
        Intent tuychon = Intent.createChooser(thuvien,"Chon 1 muc ");
        tuychon.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{camera});

        startActivityForResult(tuychon,999);
    }

    private ArrayList<HashMap<String, Object>> getDSLoai(){
        LoaiDAO loaiDAO = new LoaiDAO(getContext());
        ArrayList<LoaiMon> list = loaiDAO.getDSLoai();
        ArrayList<HashMap<String,Object>> listHM = new ArrayList<>();

        for (LoaiMon loai :list){
            HashMap<String,Object> hs = new HashMap<>();
            hs.put("maloai", loai.getMaloai());
            hs.put("tenloai", loai.getTenloai());
            listHM.add(hs);
        }

        return listHM;
    }

    private void loadData(){
        monAnDAO = new MonAnDAO(getContext());
        list = monAnDAO.getDSMonAn();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewMonAn.setLayoutManager(linearLayoutManager);
        MonAnAdapter adapter = new MonAnAdapter(getContext(),list, getDSLoai(),monAnDAO);
        recyclerViewMonAn.setAdapter(adapter);
    }
}
