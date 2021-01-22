package com.example.accidentapp;

public class AccDto {
    private double xpos; // x 좌표
    private double ypos; // y 좌표
    private String title; // 시도
    private String subtitle; // 지역명

    private double occrrnc_cnt_txt; // 발생건수
    private double caslt_cnt_txt; // 사상자 수
    private double dth_dnv_cnt_txt; // 사망자 수
    private double se_dnv_cnt_txt; // 중상자 수
    private double sl_dnv_cnt_txt; // 경상자 수
    double distance;


    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getXpos() {
        return xpos;
    }

    public void setXpos(double xpos) {
        this.xpos = xpos;
    }

    public double getYpos() {
        return ypos;
    }

    public void setYpos(double ypos) {
        this.ypos = ypos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public double getOccrrnc_cnt_txt() {
        return occrrnc_cnt_txt;
    }

    public void setOccrrnc_cnt_txt(double occrrnc_cnt_txt) {
        this.occrrnc_cnt_txt = occrrnc_cnt_txt;
    }

    public double getCaslt_cnt_txt() {
        return caslt_cnt_txt;
    }

    public void setCaslt_cnt_txt(double caslt_cnt_txt) {
        this.caslt_cnt_txt = caslt_cnt_txt;
    }

    public double getDth_dnv_cnt_txt() {
        return dth_dnv_cnt_txt;
    }

    public void setDth_dnv_cnt_txt(double dth_dnv_cnt_txt) {
        this.dth_dnv_cnt_txt = dth_dnv_cnt_txt;
    }

    public double getSe_dnv_cnt_txt() {
        return se_dnv_cnt_txt;
    }

    public void setSe_dnv_cnt_txt(double se_dnv_cnt_txt) {
        this.se_dnv_cnt_txt = se_dnv_cnt_txt;
    }

    public double getSl_dnv_cnt_txt() {
        return sl_dnv_cnt_txt;
    }

    public void setSl_dnv_cnt_txt(double sl_dnv_cnt_txt) {
        this.sl_dnv_cnt_txt = sl_dnv_cnt_txt;
    }

    public AccDto() {
    }

    public AccDto(double xpos, double ypos, String title, String subtitle, double occrrnc_cnt_txt, double caslt_cnt_txt, double dth_dnv_cnt_txt, double se_dnv_cnt_txt, double sl_dnv_cnt_txt, double distance) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.title = title;
        this.subtitle = subtitle;
        this.occrrnc_cnt_txt = occrrnc_cnt_txt;
        this.caslt_cnt_txt = caslt_cnt_txt;
        this.dth_dnv_cnt_txt = dth_dnv_cnt_txt;
        this.se_dnv_cnt_txt = se_dnv_cnt_txt;
        this.sl_dnv_cnt_txt = sl_dnv_cnt_txt;
        this.distance = distance;
    }
}
