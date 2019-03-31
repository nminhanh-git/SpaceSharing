package com.example.nminhanh.spacesharing.Model;

import java.io.Serializable;

public class Space implements Serializable {
    private String id;
    private String idChu;
    private String tieuDe;
    private String diaChiPho;
    private String phuong;
    private String quan;
    private String thanhPho;
    private double dienTich;
    private double gia;
    private int soPhongNgu;
    private int soPhongVeSinh;
    private String huongCua;
    private String imagePath;
    private String moTa;
    private String thongTinPhapLy;
    private boolean khaDung;


    public Space() {
    }

    public Space(String id,
                 String idChu,
                 String tieuDe,
                 String diaChiPho,
                 String phuong,
                 String quan,
                 String thanhPho,
                 double dienTich,
                 double gia,
                 int soPhongNgu,
                 int soPhongVeSinh,
                 String huongCua,
                 String imagePath,
                 String moTa,
                 String thongTinPhapLy,
                 boolean khaDung
    ) {
        this.id = id;
        this.idChu = idChu;
        this.tieuDe = tieuDe;
        this.diaChiPho = diaChiPho;
        this.phuong = phuong;
        this.quan = quan;
        this.thanhPho = thanhPho;
        this.dienTich = dienTich;
        this.gia = gia;
        this.soPhongNgu = soPhongNgu;
        this.soPhongVeSinh = soPhongVeSinh;
        this.huongCua = huongCua;
        this.imagePath = imagePath;
        this.moTa = moTa;
        this.thongTinPhapLy = thongTinPhapLy;
        this.khaDung = khaDung;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdChu() {
        return idChu;
    }

    public void setIdChu(String idChu) {
        this.idChu = idChu;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getDiaChiPho() {
        return diaChiPho;
    }

    public void setDiaChiPho(String diaChiPho) {
        this.diaChiPho = diaChiPho;
    }

    public String getPhuong() {
        return phuong;
    }

    public void setPhuong(String phuong) {
        this.phuong = phuong;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }

    public String getThanhPho() {
        return thanhPho;
    }

    public void setThanhPho(String thanhPho) {
        this.thanhPho = thanhPho;
    }

    public double getDienTich() {
        return dienTich;
    }

    public void setDienTich(double dienTich) {
        this.dienTich = dienTich;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getSoPhongNgu() {
        return soPhongNgu;
    }

    public void setSoPhongNgu(int soPhongNgu) {
        this.soPhongNgu = soPhongNgu;
    }

    public int getSoPhongVeSinh() {
        return soPhongVeSinh;
    }

    public void setSoPhongVeSinh(int soPhongVeSinh) {
        this.soPhongVeSinh = soPhongVeSinh;
    }

    public String getHuongCua() {
        return huongCua;
    }

    public void setHuongCua(String huongCua) {
        this.huongCua = huongCua;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getThongTinPhapLy() {
        return thongTinPhapLy;
    }

    public void setThongTinPhapLy(String thongTinPhapLy) {
        this.thongTinPhapLy = thongTinPhapLy;
    }

    public boolean isKhaDung() {
        return khaDung;
    }

    public void setKhaDung(boolean khaDung) {
        this.khaDung = khaDung;
    }
}
