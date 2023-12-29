package com.example.panda.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper( Context context) {
        super(context, "PANDA", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String dbNguoiDung = "CREATE TABLE NGUOIDUNG(mand text primary key, hoten text, matkhau text, loai text)";
        db.execSQL(dbNguoiDung);

        String dbThongTinKH = "CREATE TABLE THONGTINKH(makh integer primary key autoincrement, mand text references NGUONDUNG(mand), sdt text, diachi text)";
        db.execSQL(dbThongTinKH);

        String dbLoaiMon = "CREATE TABLE LOAIMON(maloai integer primary key autoincrement, tenloai text)";
        db.execSQL(dbLoaiMon);

        String dbMonAn = "CREATE TABLE MONAN(mamon integer primary key autoincrement, maloai integer references LOAIMON(maloai), tenmon text, gia integer, mota text, anh text)";
        db.execSQL(dbMonAn);

        String dbDonHang = "CREATE TABLE DONHANG(madh integer primary key autoincrement, mand text references NGUONDUNG(mand), tongsoluong integer, ngay text, trangthai integer, tien integer)";
        db.execSQL(dbDonHang);

        String dbChiTietDH = "CREATE TABLE CHITIETDH(macthd integer primary key autoincrement, madh integer references DONHANG(madh), mamon integer references MONAN(mamon), soluong integr,tien integer)";
        db.execSQL(dbChiTietDH);

        String dbDanhGia = "CREATE TABLE DANHGIA(madg integer primary key autoincrement, mand text references NGUONDUNG(mand), mamon integer references MONAN(mamon), ngay text, danhgia text)";
        db.execSQL(dbDanhGia);

        String dbGioHang = "CREATE TABLE GIOHANG(magh integer primary key autoincrement, mamon integer references MONAN(mamon), mand integer references NGUOIDUNG(mand), soluong integer)";
        db.execSQL(dbGioHang);

        db.execSQL("INSERT INTO NGUOIDUNG VALUES ('admin','Admin','123','admin'),('kh1','Trần Trọng Nhân','123','kh'),('kh2','Nguyễn Quốc Nam','123','kh'), ('110032531946494','Will Alhhaibdcahje Valtchanovsen','123','kh'),('kh3','Lê Phạm Khánh Dương','123','kh')," +
                "('kh4','Cao Ngọc Hoàng Linh','123','kh'),('kh5','Lê Chính Bảo','123','kh'),('kh6','Nguyễn Thanh Tùng','123','kh'),('kh7','Lê Đức Minh','123','kh'),('kh8','Nguyễn Ngọc Hiền','123','kh'),('kh9','Phan Gia Khiêm','123','kh'),('kh10','Huỳnh Ngọc Long Nhật','123','kh')");
        db.execSQL("INSERT INTO THONGTINKH VALUES (1,'kh1','02456875','HCM'),(2,'kh2','0221584','HN')");
        db.execSQL("INSERT INTO LOAIMON VALUES (1,'Xào'),(2,'Nướng'),(3,'Hấp'),(4,'Sup'),(5,' Hải sản'),(6,'Mì'),(7,'DimSum'),(8,'Nước Uống'),(9,'Bia')");
        db.execSQL("INSERT INTO MONAN VALUES (1,2,'Heo quay da giòn ăn kèm sốt',188000,'Da heo được quay giòn theo công thức bí truyền', 'https://d1-concepts.com/wp-content/uploads/2021/03/Screenshot-2021-03-25-171729.jpg')," +
                "(2,2,'Sườn non quay mật ong',188000,'Sườn mềm mọng nước cùng mật ong thượng hạng','https://d1-concepts.com/wp-content/uploads/2020/06/su%CC%9Bo%CC%9B%CC%80n-non-quay-ma%CC%A3%CC%82t-ong-01-300x300.jpg')," +
                "(3,2,'Bồ câu quay kim bài',198000,'Bồ câu được quay với công thức đặc biệt','https://d1-concepts.com/wp-content/uploads/2020/06/bo%CC%82%CC%80-ca%CC%82u-quay-kim-ba%CC%80i-01-1-300x300.jpg')," +
                "(4,1,'Rau cải rổ xào tỏi bí truyền',88000,'Cải xanh xào cùng tỏi với công thức đặt trưng','https://d1-concepts.com/wp-content/uploads/2020/06/ca%CC%89i-ro%CC%82%CC%89-xa%CC%80o-to%CC%89i-01-300x300.jpg')," +
                "(5,1,'Cải xào sốt bào ngư',88000,'Cải xanh, sốt bào ngư trứ danh','https://d1-concepts.com/wp-content/uploads/2020/06/ca%CC%89i-xa%CC%80o-so%CC%82%CC%81t-ba%CC%80o-ngu%CC%9B-01-300x300.jpg')," +
                "(6,3,'Há cảo tôm và thanh cua',98000,'Há cảo thơm ngon','https://d1-concepts.com/wp-content/uploads/2020/06/ha%CC%81-ca%CC%89o-to%CC%82m-va%CC%80-thanh-cua-01-300x300.jpg')," +
                "(7,3,'Xíu mại nấm hương thịt cua trứng cá',98000,'Hương thơm không thể cưỡng','https://d1-concepts.com/wp-content/uploads/2021/03/Screenshot-2021-03-25-174021.jpg')," +
                "(8,4,'Sup hải sản chua cay',88000,'vị chua cay khó tả','https://d1-concepts.com/wp-content/uploads/2020/06/su%CC%81p-ha%CC%89i-sa%CC%89n-chua-cay-300x300.jpg')," +
                "(9,4,'Sup vi cá tiềm hải vị',388000,'vi cá thượng hạn','https://d1-concepts.com/wp-content/uploads/2020/06/su%CC%81p-vi-ca%CC%81-tie%CC%82%CC%80m-ha%CC%89i-vi%CC%A3-01-300x300.jpg')," +
                "(10,5,'Hải xâm gân heo sốt XO',288000,'Sốt XO trứ danh','https://d1-concepts.com/wp-content/uploads/2020/06/ha%CC%89i-sa%CC%82m-ga%CC%82n-heo-so%CC%82%CC%81t-xo-01-300x300.jpg')," +
                "(11,5,'Tôm sú hoàng kim trứng muối',238000,'Tôm sú thượng hạn','https://d1-concepts.com/wp-content/uploads/2020/06/to%CC%82m-su%CC%81-chie%CC%82n-hoa%CC%80ng-kim-tru%CC%9B%CC%81ng-muo%CC%82%CC%81i-01-300x300.jpg')," +
                "(12,2,'Bắp bò kiểu Quảng Đông',128000,'món ăn được gọi nhiều nhất','https://d1-concepts.com/wp-content/uploads/2022/11/Screen-Shot-2022-11-21-at-12.15.44.png')," +
                "(13,3,'Tai heo sốt trứ danh ',88000,'tai heo dai giòn','https://d1-concepts.com/wp-content/uploads/2020/06/tai-heo-so%CC%82%CC%81t-tru%CC%9B%CC%81-danh-SFL-scaled.jpg')," +
                "(14,4,'Súp hải sản Ngọc Bích',98000,'món ngon với màu xanh độc đáo','https://d1-concepts.com/wp-content/uploads/2022/11/Sup-hai-san-Ngoc-Bich-.jpg')," +
                "(15,2,'Gà quay sả sốt mè',198000,'Gà vườn được quay bằng than hồng','https://d1-concepts.com/wp-content/uploads/2020/06/ga%CC%80-quay-sa%CC%89-so%CC%82%CC%81t-me%CC%80-01-scaled.jpg')," +
                "(16,2,'Xá xíu PanDa Food',168000,'món ăn độc quyền','https://d1-concepts.com/wp-content/uploads/2020/06/xa%CC%81-xi%CC%81u-SFL-01-scaled.jpg')," +
                "(17,2,'Vịt quay kiểu Hong Kong',228000,'Vịt quay với công thức gia truyền','https://d1-concepts.com/wp-content/uploads/2020/06/vi%CC%A3t-quay-kie%CC%82%CC%89u-ho%CC%82%CC%80ng-ko%CC%82ng-01-scaled-e1614671634427.jpg')," +
                "(18,2,'Món quay tổng hợp FPD',318000,'combo 3 món cho nhóm người','https://d1-concepts.com/wp-content/uploads/2020/06/Screen-Shot-2022-11-21-at-12.26.16.png')," +
                "(19,6,'Mì xào hải sản sốt cay',288000,'Mì xào cực ngon','https://d1-concepts.com/wp-content/uploads/2022/11/Screen-Shot-2022-11-21-at-12.48.39.png')," +
                "(20,6,'Mì khô thịt ba rọi quay sốt hải sản',118000,'món mì nhưng được ăn kèm với thịt','https://d1-concepts.com/wp-content/uploads/2021/03/Screenshot-2021-03-25-172848.jpg')," +
                "(21,1,'Hoành thánh tôm thịt',108000,'món ngon hảo hạng','https://d1-concepts.com/wp-content/uploads/2020/06/hoa%CC%80nh-tha%CC%81nh-to%CC%82m-thi%CC%A3t-gia%CC%82%CC%81m-%C4%91en-da%CC%82%CC%80u-o%CC%9B%CC%81t-01-1-300x300.jpg')," +
                "(22,6,'Mì khô gà quay sốt tôm khô',118000,'mì ngon với sốt','https://d1-concepts.com/wp-content/uploads/2020/06/mi%CC%80-kho%CC%82-ga%CC%80-quay-so%CC%82%CC%81t-to%CC%82m-kho%CC%82-01-300x300.jpg')," +
                "(23,6,'Mì khô xá xíu sốt tương đen',98000,'món ngon đặc trưng','https://d1-concepts.com/wp-content/uploads/2020/06/mi%CC%80-kho%CC%82-xa%CC%81-xi%CC%81u-tu%CC%9Bo%CC%9Bng-%C4%91en-01-scaled.jpg')," +
                "(24,6,'Mì khô sủi cảo tôm thịt sốt sa tế',118000,'Mì ngon với sốt sa tế','https://d1-concepts.com/wp-content/uploads/2020/06/mi%CC%80-kho%CC%82-su%CC%89i-ca%CC%89o-to%CC%82m-thi%CC%A3t-so%CC%82%CC%81t-sa-te%CC%82%CC%81-01-300x300.jpg')," +
                "(25,6,'Mì khô vịt quay sốt X.O',118000,'món ngon kèm sốt','https://d1-concepts.com/wp-content/uploads/2020/06/mi%CC%80-kho%CC%82-vi%CC%A3t-quay-so%CC%82%CC%81t-ox-01-300x300.jpg')," +
                "(26,3,'Bánh bao uyên ương',68000,'bánh ngon với thịt băm','https://d1-concepts.com/wp-content/uploads/2022/11/Banh-bao-uyen-uong-300x300.jpg')," +
                "(27,3,'Há cảo bách hoa',68000,'Vị ngon đấm vào vị giác','https://d1-concepts.com/wp-content/uploads/2022/11/Ha-cao-bach-hoa-6-cai-300x300.jpg')," +
                "(28,3,'Há cảo mực',75000,'Vị mực đậm đà trên đầu lưỡi','https://d1-concepts.com/wp-content/uploads/2022/11/Ha-cao-muc-6-cai-300x300.jpg')," +
                "(29,3,'Xíu mại nấm hương thịt cua trứng cá',118000,'Hương vị đậm đà','https://d1-concepts.com/wp-content/uploads/2021/03/Screenshot-2021-03-25-174021.jpg')," +
                "(30,3,'Há cảo tôm và thanh cua',68000,'Hương vị biển cả','https://d1-concepts.com/wp-content/uploads/2020/06/ha%CC%81-ca%CC%89o-to%CC%82m-va%CC%80-thanh-cua-01-300x300.jpg')," +
                "(31,3,'Há cảo thịt vịt gói kiểu trái lựu',75000,'Thịt vịt băm nhuyễn cùng vỏ bánh giày đặc','https://d1-concepts.com/wp-content/uploads/2020/06/ha%CC%81-ca%CC%89o-thi%CC%A3t-vi%CC%A3t-go%CC%81i-kie%CC%82%CC%89u-tra%CC%81i-lu%CC%9B%CC%A3u-01-300x300.jpg')," +
                "(32,3,'Há cảo tôm bó xôi',75000,'Tôm tươi ngon bọc xôi và há cảo','https://d1-concepts.com/wp-content/uploads/2020/06/ha%CC%81-ca%CC%89o-to%CC%82m-bo%CC%81-xo%CC%82i-01-300x300.jpg'), "+
                "(33,3,'Há cảo tôm sò điệp',88000,'Sò điệp tan chảy cùng há cảo','https://d1-concepts.com/wp-content/uploads/2020/06/ha%CC%81-ca%CC%89o-to%CC%82m-so%CC%80-%C4%91ie%CC%A3%CC%82p-tu%CC%9Bo%CC%9Bi-01-300x300.jpg')," +
                "(34,3,'Há cảo tôm',68000,'Há cảo bọc tôm giòn rụm','https://d1-concepts.com/wp-content/uploads/2020/06/ha%CC%81-ca%CC%89o-to%CC%82m-01-300x300.jpg')," +
                "(35,3,'Chân gà hấp tàu xì',68000,'Chân gà bùng nổ vị đậm đà','https://d1-concepts.com/wp-content/uploads/2020/06/cha%CC%82n-ga%CC%80-ha%CC%82%CC%81p-ta%CC%80u-xi%CC%80-01-300x300.jpg')," +
                "(36,3,'Chả giò phô mai',98000,'Vị phô mai béo ngậy','https://d1-concepts.com/wp-content/uploads/2022/11/Cha-gio-pho-mai-300x300.jpg')," +
                "(37,7,'Bánh cuốn cuộn tôm',88000,'Bánh cuốn tôm kèm nước mắm ngọt','https://d1-concepts.com/wp-content/uploads/2020/06/ba%CC%81nh-cuo%CC%82%CC%81n-cuo%CC%A3%CC%82n-to%CC%82m-01-300x300.jpg')," +
                "(38,7,'Bánh bao xá xíu',58000,'Bánh bao mặn mà đậm đà','https://d1-concepts.com/wp-content/uploads/2020/06/ba%CC%81nh-bao-xa%CC%81-xi%CC%81u-01-1-300x300.jpg')," +
                "(39,7,'Đậu hủ ki cuộn tôm thịt chiên',98000,'Đậu hủ giòn rụm bọc tôm thịt băm','https://d1-concepts.com/wp-content/uploads/2020/06/%C4%91a%CC%A3%CC%82u-hu%CC%89-ki-cuo%CC%A3%CC%82n-to%CC%82m-thi%CC%A3t-chie%CC%82n-01-300x300.jpg')," +
                "(40,7,'Bánh bao phong sa',68000,'Bánh bao nóng hổi','https://d1-concepts.com/wp-content/uploads/2020/06/ba%CC%81nh-bao-phong-sa-01-300x300.jpg')," +
                "(41,1,'Mực xào sốt Sambal',288000,'Mực xào siêu cay','https://d1-concepts.com/wp-content/uploads/2022/11/Muc-xao-sot-Sambal-300x300.jpg')," +
                "(42,1,'Tôm sào sốt Singapore',288000,'Vị chua ngọt đậm đà trên từng con tôm','https://d1-concepts.com/wp-content/uploads/2022/11/Tom-xao-sot-Singapore-300x300.jpg'),"+
                "(43,8,'PepSi',20000,'','https://d1-concepts.com/wp-content/uploads/2020/06/pepsi-01-300x300.jpg'),"+
                "(44,8,'7 Up',20000,'','https://d1-concepts.com/wp-content/uploads/2022/11/7-Up-300x300.jpg'),"+
                "(45,8,'PepSi Black',20000,'','https://d1-concepts.com/wp-content/uploads/2022/11/Pepsi-Black-300x300.jpg'),"+
                "(46,8,'Trà lê hoa nhài', 42000,'','https://d1-concepts.com/wp-content/uploads/2022/11/Tra-le-hoa-nhai--300x300.jpg'),"+
                "(47,8,'Trà ổi hồng dâu tây', 42000,'','https://d1-concepts.com/wp-content/uploads/2022/11/Tra-oi-hong-dau-tay-300x300.jpg'),"+
                "(48,8,'Trà tứ vị', 42000,'','https://d1-concepts.com/wp-content/uploads/2022/11/Tra-tu-vi--300x300.jpg'),"+
                "(49,8,'Trà táo đỏ kỷ tử',42000,'','https://d1-concepts.com/wp-content/uploads/2022/11/Tra-tao-do-ky-tu-300x300.jpg'),"+
                "(50,8,'Cà phê đá',30000,'','https://d1-concepts.com/wp-content/uploads/2020/06/tra%CC%80-sa%CC%89-ta%CC%86%CC%81c-01-300x300.jpg'),"+
                "(51,8,'Cà phê sữa đá',35000,'','https://d1-concepts.com/wp-content/uploads/2020/06/ca%CC%80-phe%CC%82-su%CC%9B%CC%83a-%C4%91a%CC%81-01-300x300.jpg'),"+
                "(52,8,'Sữa đậu nành', 32000,'','https://d1-concepts.com/wp-content/uploads/2020/06/su%CC%9B%CC%83a-%C4%91a%CC%A3%CC%82u-na%CC%80nh-SFL-01-300x300.jpg'),"+
                "(53,8,'Nước cam',30000,'','https://d1-concepts.com/wp-content/uploads/2020/06/nu%CC%9Bo%CC%9B%CC%81c-cam-e%CC%81p-01-300x300.jpg'),"+
                "(54,8,'Trà đào',40000,'','https://d1-concepts.com/wp-content/uploads/2022/11/Screen-Shot-2022-11-19-at-22.41.40-280x300.png'),"+
                "(55,8,'Trà xanh',40000,'','https://d1-concepts.com/wp-content/uploads/2020/06/tra%CC%80-xanh-tra%CC%81i-va%CC%89i-nha-%C4%91am-01-300x300.jpg'),"+
                "(56,8,'Nước suối',20000,'','https://d1-concepts.com/wp-content/uploads/2020/06/nu%CC%9Bo%CC%9B%CC%81c-suo%CC%82%CC%81i-01-300x300.jpg'),"+
                "(57,9,'Bia Heineken',50000,'','https://d1-concepts.com/wp-content/uploads/2022/11/Heineken--300x300.jpg'),"+
                "(58,9,'Bia Heineken 0.0',58000,'','https://d1-concepts.com/wp-content/uploads/2022/11/yx9uc7aberuwx-300x300.jpeg'),"+
                "(59,9,'Bia Heineken Bạc',62000,'','https://d1-concepts.com/wp-content/uploads/2022/11/Heineken-Silver-300x300.jpg'),"+
                "(60,9,'Strongbow',50000,'','https://d1-concepts.com/wp-content/uploads/2022/11/Strongbow-Cider-300x300.jpg'),"+
                "(61,9,'Bia Tiger', 40000,'','https://d1-concepts.com/wp-content/uploads/2022/11/Tiger--300x300.jpg'),"+
                "(62,9,'Bia Tiger Crystal',45000,'','https://d1-concepts.com/wp-content/uploads/2022/11/Tiger-Crystal-300x300.jpg'),"+
                "(63,9,'Bia Tiger Platinum',52000,'','https://d1-concepts.com/wp-content/uploads/2022/11/Tiger-Platinum-300x300.jpg')");

        db.execSQL("INSERT INTO DANHGIA VALUES (1,'kh1',3,'11/12/2022','quá ngon')," +
                "(2,'kh2',2,'09/02/2022','ngon tuyệt vời')," +
                "(3,'kh3',3,'05/01/2022','ngon cực')," +
                "(4,'kh4',4,'07/09/2022','giao nhanh')," +
                "(5,'kh5',5,'22/11/2022','món ngon nên thử')," +
                "(6,'kh6',6,'27/06/2022','quá ngon')," +
                "(7,'kh7',7,'12/03/2022','ngon cực')," +
                "(8,'kh8',8,'29/10/2022','ngon tuyệt !')," +
                "(9,'kh9',9,'10/12/2022','phục vụ tuyệt vời')," +
                "(10,'kh10',10,'06/12/2022','sốt quá ngon')," +
                "(11,'kh1',11,'09/1/2022','Ngon bá cháy !')," +
                "(12,'kh2',12,'08/02/2002','Uống cùng với bia thì hết sãy !')," +
                "(13,'kh3',13,'23/11/2022','Một món ăn đẳng cấp')," +
                "(14,'kh4',14,'09/01/2022','Lần đầu được ăn món ngon thế này')," +
                "(15,'kh5',15,'08/04/2022','Ngon tuyệt vời')," +
                "(16,'kh6',16,'25/05/2022','Ngon bá cháy luôn !')," +
                "(17,'kh7',17,'30/04/2022','Đượm vị ngọt bùng vị ngon')," +
                "(18,'kh8',18,'18/04/2022','Đơn giản nhưng ngon')," +
                "(19,'kh9',19,'07/07/2022','Rẻ mà ngon')," +
                "(20,'kh10',20,'21/12/2022','Delicious !!!')," +
                "(21,'kh1',21,'12/12/2022','So yummy !!!')," +
                "(22,'kh2',22,'05/08/2022','Chin chá ma chịt ta !')," +
                "(23,'kh3',23,'06/02/2022','Ù Mai !!')," +
                "(24,'kh4',24,'04/05/2022','Bét tè lè nhè ')," +
                "(25,'kh3',2,'03/02/2022','ngon thật đấy')," +
                "(26,'kh4',2,'09/03/2022','ngon hết sảy')," +
                "(27,'kh5',3,'15/7/2022','Oh my god so good')," +
                "(28,'kh6',3,'14/04/2022','Một món ăn ngon')," +
                "(29,'kh7',41,'01/02/2022','ngon tuyệt vời')," +
                "(30,'kh8',40,'02/02/2022','ngon đó mọi người')," +
                "(31,'kh9',5,'03/02/2022','Lần sau sẽ rút kinh nghiệm mua nhiều thêm')," +
                "(32,'kh10',5,'04/02/2022','Quá đậm đà vị ngon')," +
                "(33,'kh1',6,'07/02/2022','Bùng nổ hương vị luôn')," +
                "(34,'kh2',6,'06/02/2022','Ngon thiệt mấy má ')," +
                "(35,'kh3',7,'05/02/2022','Siuuuuuuuuuuuuu')," +
                "(36,'kh4',7,'08/02/2022','Dữ thiệt chứ')," +
                "(37,'kh5',8,'10/02/2022','Ngon kinh khủng ')," +
                "(38,'kh6',8,'11/02/2022','tui vote quán này 5 sao')," +
                "(39,'kh7',9,'12/02/2022','tui muốn cưới đầu bếp nhà hàng này')," +
                "(40,'kh8',9,'13/02/2022','ngon như body blackpink vậy ')," +
                "(41,'kh9',10,'14/02/2022','ngon tuyệt vời')," +
                "(42,'kh10',10,'15/02/2022','Vị ngon đấm thẳng vào lưỡi tôi mọi người ơi ')," +
                "(43,'kh1',11,'16/02/2022','Thơm ngon mời bạn ăn nha')," +
                "(44,'kh2',11,'17/02/2022','Tôi đây không chờ bạn nữa giờ tôi ăn liền')," +
                "(45,'kh3',12,'18/02/2022','Vị ngon tuyệt phẩm')," +
                "(46,'kh4',12,'19/02/2022','ngon ghê')," +
                "(47,'kh5',13,'20/02/2022','ăn món này nghe bài yummy Justin bieber hết sãy')," +
                "(48,'kh6',13,'21/02/2022','Ngon ngọt đủ vị')," +
                "(49,'kh7',14,'22/02/2022','Ngon bá cháy bọ chét')," +
                "(50,'kh8',14,'23/02/2022','Ngon quá đi thôi')," +
                "(51,'kh9',15,'24/02/2022','tH13^.t 1A` nG0n Wa ik ')," +
                "(52,'kh10',15,'25/02/2022','Siêu phẩm')," +
                "(53,'kh1',16,'26/02/2022','Đầu bếp linh nấu đúng đỉnh')," +
                "(54,'kh2',16,'27/02/2022','Bếp trưởng Hoàng Linh nấu gì cũng ngon')," +
                "(55,'kh3',17,'01/12/2022','Đúng là cái bóng của Gordon Ramsey')," +
                "(56,'kh4',17,'02/12/2022','Gordon Linhsey bếp trưởng số 1 Việt Nam')," +
                "(57,'kh5',18,'03/12/2022','Món này đúng là độc nhất vô nhị')," +
                "(58,'kh6',18,'04/12/2022','Tuyệt Phẩm')," +
                "(59,'kh7',19,'05/12/2022','Chắc chắn tôi sẽ ăn lại món này')," +
                "(60,'kh8',19,'06/12/2022','Đẳng cấp đồ ăn Việt là đây')," +
                "(61,'kh9',20,'07/12/2022','Ngon thiệt mấy thím')," +
                "(62,'kh10',20,'08/12/2022','Ăn xong vài ngày vị vẫn còn đọng lại')," +
                "(63,'kh1',21,'09/12/2022','Nhà hàng này xứng đáng 5 sao Michelin')," +
                "(64,'kh2',21,'10/12/2022','Mãi mới tìm được quán chuẩn vị của món')," +
                "(65,'kh3',22,'11/12/2022','Tôi sẽ lên kế hoạch hẹn hò cùng người yêu tôi tại quán này')," +
                "(66,'kh4',22,'12/12/2022','Ngon zữ zội')," +
                "(67,'kh5',23,'13/12/2022','Nhìn như mấy phim ẩm thực vậy !')," +
                "(68,'kh6',23,'14/12/2022','Ngon đó ')," +
                "(69,'kh7',24,'15/12/2022','Hình như IU cũng từng khen nhà hàng này')," +
                "(70,'kh8',24,'16/12/2022','Nghe nói G dragon là cái bóng của bếp trưởng hoàng linh'),"+
                "(71,'kh11',1,'30/04/2022','Ăn thấy vừa miệng'),"+
                "(72,'kh3',2,'01/05/2022','Zǎoshang hǎo zhōngguó xiànzài wǒ yǒu BING CHILLING'),"+
                "(73,'kh11',3,'01/06/2022','Nice Xừ'),"+
                "(74,'kh8',4,'01/01/2022','Giá cả hợp lí với món ăn'),"+
                "(75,'kh11',5,'14/02/2022','Thằng bạn tôi nó bị liệt nửa năm nay đã hết liệt sau khi ăn những món ăn tại đây'),"+
                "(76,'kh5',6,'15/07/2022','Ngon như chưa bao giờ được ăn ngon'),"+
                "(77,'kh4',7,'24/12/2022','Ba tôi bị suy nhược cơ thể, sau khi ăn món tại đây ba tôi đã khỏe mạnh hẳn'),"+
                "(78,'kh11',8,'25/12/2022','Ông hàng xóm tui đã mê ăn ở đây khi tui giới thiệu cho ổng '),"+
                "(79,'kh6',9,'26/12/2022','Ước gì ông bà dà cho tiền nhiều để ăn ở đây mỗi ngày'),"+
                "(80,'kh9',10,'01/12/2022','Ngon sau cơm mẹ nấu'),"+
                "(81,'kh8',11,'02/07/2022','Quá Ngon^^ cho 9 rưỡi'),"+
                "(82,'kh7',12,'19/05/2022','Siuuuuuuuuuuuuuuuuuuuuuuu'),"+
                "(83,'kh1',13,'08/03/2022','Xuất xắc mấy má ơi'),"+
                "(84,'kh4',14,'09/03/2022','Cực kì thích món này'),"+
                "(85,'kh11',15,'13/06/2022','Ngon hơn Hadlao nhìu'),"+
                "(86,'kh3',16,'15/06/2022','Hình như đầu bếp giỏi nhất tụ về đây hay sao mà đồ ăn ngon z'),"+
                "(87,'kh2',17,'16/06/2022','Ngon như anh Dương Lâm Đồng Nai z'),"+
                "(88,'kh11',18,'06/01/2022','Tuyệt zời'),"+
                "(89,'kh3',19,'12/12/2022','Tôi đã phục hồi thể trạng sau khi ăn món này'),"+
                "(90,'kh11',20,'28/12/2022','Món ăn này rất là SIUUUUUUUUUU')," +
                "(91,'kh8',1,'23/02/2022','Ngon quá đi thôi')," +
                "(92,'kh9',1,'24/02/2022','tH13^.t 1A` nG0n Wa ik ')," +
                "(93,'kh10',1,'25/02/2022','Siêu phẩm')," +
                "(94,'kh1',2,'26/02/2022','Đầu bếp linh nấu đúng đỉnh')," +
                "(95,'kh2',2,'27/02/2022','Bếp trưởng Hoàng Linh nấu gì cũng ngon')," +
                "(96,'kh3',2,'01/12/2022','Đúng là cái bóng của Gordon Ramsey')," +
                "(97,'kh4',3,'02/12/2022','Gordon Linhsey bếp trưởng số 1 Việt Nam')," +
                "(98,'kh5',3,'03/12/2022','Món này đúng là độc nhất vô nhị')," +
                "(99,'kh6',4,'04/12/2022','Tuyệt Phẩm')," +
                "(100,'kh7',4,'05/12/2022','Chắc chắn tôi sẽ ăn lại món này')," +
                "(101,'kh8',5,'06/12/2022','Đẳng cấp đồ ăn Việt là đây')," +
                "(102,'kh9',5,'07/12/2022','Ngon thiệt mấy thím')," +
                "(103,'kh10',6,'08/12/2022','Ăn xong vài ngày vị vẫn còn đọng lại')," +
                "(104,'kh1',6,'09/12/2022','Nhà hàng này xứng đáng 5 sao Michelin')," +
                "(105,'kh2',7,'10/12/2022','Mãi mới tìm được quán chuẩn vị của món')," +
                "(106,'kh3',7,'11/12/2022','Tôi sẽ lên kế hoạch hẹn hò cùng người yêu tôi tại quán này')," +
                "(107,'kh4',8,'12/12/2022','Ngon zữ zội')," +
                "(108,'kh5',8,'13/12/2022','Nhìn như mấy phim ẩm thực vậy !')," +
                "(109,'kh6',8,'14/12/2022','Ngon đó ')," +
                "(110,'kh7',9,'15/12/2022','Hình như IU cũng từng khen nhà hàng này')," +
                "(111,'kh8',9,'16/12/2022','Nghe nói G dragon là cái bóng của bếp trưởng hoàng linh')," +
                "(112,'kh2',2,'09/02/2022','ngon tuyệt vời')," +
                "(113,'kh3',40,'05/01/2022','ngon cực')," +
                "(114,'kh4',41,'07/09/2022','giao nhanh')," +
                "(115,'kh5',25,'22/11/2022','món ngon nên thử')," +
                "(116,'kh6',36,'27/06/2022','quá ngon')," +
                "(117,'kh7',37,'12/03/2022','ngon cực')," +
                "(118,'kh8',38,'29/10/2022','ngon tuyệt !')," +
                "(119,'kh9',39,'10/12/2022','phục vụ tuyệt vời')," +
                "(120,'kh10',40,'06/12/2022','sốt quá ngon')," +
                "(121,'kh1',41,'09/1/2022','Ngon bá cháy !')," +
                "(122,'kh2',42,'08/02/2002','Uống cùng với bia thì hết sãy !')," +
                "(123,'kh3',33,'23/11/2022','Một món ăn đẳng cấp')," +
                "(124,'kh4',34,'09/01/2022','Lần đầu được ăn món ngon thế này')," +
                "(125,'kh5',35,'08/04/2022','Ngon tuyệt vời')," +
                "(126,'kh6',36,'25/05/2022','Ngon bá cháy luôn !')," +
                "(127,'kh7',37,'30/04/2022','Đượm vị ngọt bùng vị ngon')," +
                "(128,'kh8',38,'18/04/2022','Đơn giản nhưng ngon')," +
                "(129,'kh9',39,'07/07/2022','Rẻ mà ngon')," +
                "(130,'kh10',42,'21/12/2022','Delicious !!!')," +
                "(131,'kh1',21,'12/12/2022','So yummy !!!')," +
                "(132,'kh2',22,'05/08/2022','Chin chá ma chịt ta !')," +
                "(133,'kh3',23,'06/02/2022','Ù Mai !!')," +
                "(134,'kh4',24,'04/05/2022','Bét tè lè nhè ')," +
                "(135,'kh3',24,'03/02/2022','ngon thật đấy')," +
                "(136,'kh4',24,'09/03/2022','ngon hết sảy')," +
                "(137,'kh5',35,'15/7/2022','Oh my god so good')," +
                "(138,'kh6',38,'14/04/2022','Một món ăn ngon')," +
                "(139,'kh7',40,'01/02/2022','ngon tuyệt vời')," +
                "(140,'kh8',39,'02/02/2022','ngon đó mọi người')," +
                "(150,'kh8',14,'23/02/2022','Ngon quá đi thôi')," +
                "(151,'kh9',15,'24/02/2022','tH13^.t 1A` nG0n Wa ik ')," +
                "(152,'kh10',15,'25/02/2022','Siêu phẩm')," +
                "(153,'kh1',16,'26/02/2022','Đầu bếp linh nấu đúng đỉnh')," +
                "(154,'kh2',16,'27/02/2022','Bếp trưởng Hoàng Linh nấu gì cũng ngon')," +
                "(155,'kh3',17,'01/12/2022','Đúng là cái bóng của Gordon Ramsey')," +
                "(156,'kh4',17,'02/12/2022','Gordon Linhsey bếp trưởng số 1 Việt Nam')," +
                "(157,'kh5',18,'03/12/2022','Món này đúng là độc nhất vô nhị')," +
                "(158,'kh6',18,'04/12/2022','Tuyệt Phẩm')," +
                "(159,'kh7',19,'05/12/2022','Chắc chắn tôi sẽ ăn lại món này')," +
                "(160,'kh8',19,'06/12/2022','Đẳng cấp đồ ăn Việt là đây')," +
                "(161,'kh9',20,'07/12/2022','Ngon thiệt mấy thím')," +
                "(162,'kh10',20,'08/12/2022','Ăn xong vài ngày vị vẫn còn đọng lại')," +
                "(163,'kh1',21,'09/12/2022','Nhà hàng này xứng đáng 5 sao Michelin')," +
                "(164,'kh2',30,'10/12/2022','Mãi mới tìm được quán chuẩn vị của món')," +
                "(165,'kh3',29,'11/12/2022','Tôi sẽ lên kế hoạch hẹn hò cùng người yêu tôi tại quán này')," +
                "(166,'kh4',26,'12/12/2022','Ngon zữ zội')," +
                "(167,'kh5',23,'13/12/2022','Nhìn như mấy phim ẩm thực vậy !')," +
                "(168,'kh6',23,'14/12/2022','Ngon đó ')," +
                "(169,'kh7',31,'15/12/2022','Hình như IU cũng từng khen nhà hàng này')," +
                "(170,'kh8',32,'16/12/2022','Nghe nói G dragon là cái bóng của bếp trưởng hoàng linh')"
        );

//        db.execSQL("INSERT INTO DONHANG VALUES (1,'kh1',5,'20/10/2022',0,1000000),(2,'kh2',2,'11/11/2022',0,555555),(3,'kh1',8,'20/11/2022',0,45521)");// trạng thái: 0 chờ xác nhận, 1 xác nhận đơn hàng, 2 giao hàng, 3 giao hàng thành công

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
            db.execSQL("DROP TABLE IF EXISTS THONGTINKH");
            db.execSQL("DROP TABLE IF EXISTS LOAIMON");
            db.execSQL("DROP TABLE IF EXISTS MONAN");
            db.execSQL("DROP TABLE IF EXISTS DONHANG");
            db.execSQL("DROP TABLE IF EXISTS CHITIETDH");
            db.execSQL("DROP TABLE IF EXISTS GIOHANG");
            onCreate(db);
        }
    }
}
