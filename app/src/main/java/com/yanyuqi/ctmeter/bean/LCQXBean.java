package com.yanyuqi.ctmeter.bean;

/**
 * 励磁曲线数据bean
 * Created by yanyuqi on 2017/2/23.
 */

public class LCQXBean {

    private String E_No;//序号
    private float E_fu;//电压
    private float E_fi;//电流
    private float E_fUc;//电热
    private float E_fA;//UC与fi的夹角
    private float E_fIpe;//峰值
    private float E_fUKr;//Kr

    public LCQXBean(String e_No, float e_fu, float e_fi, float e_fUc, float e_fA, float e_fIpe, float e_fUKr) {
        E_No = e_No;
        E_fu = e_fu;
        E_fi = e_fi;
        E_fUc = e_fUc;
        E_fA = e_fA;
        E_fIpe = e_fIpe;
        E_fUKr = e_fUKr;
    }

    public String getE_No() {
        return E_No;
    }

    public void setE_No(String e_No) {
        E_No = e_No;
    }

    public float getE_fu() {
        return E_fu;
    }

    public void setE_fu(float e_fu) {
        E_fu = e_fu;
    }

    public float getE_fi() {
        return E_fi;
    }

    public void setE_fi(float e_fi) {
        E_fi = e_fi;
    }

    public float getE_fUc() {
        return E_fUc;
    }

    public void setE_fUc(float e_fUc) {
        E_fUc = e_fUc;
    }

    public float getE_fA() {
        return E_fA;
    }

    public void setE_fA(float e_fA) {
        E_fA = e_fA;
    }

    public float getE_fIpe() {
        return E_fIpe;
    }

    public void setE_fIpe(float e_fIpe) {
        E_fIpe = e_fIpe;
    }

    public float getE_fUKr() {
        return E_fUKr;
    }

    public void setE_fUKr(float e_fUKr) {
        E_fUKr = e_fUKr;
    }

    @Override
    public String toString() {
        return "LCQXBean{" +
                "E_No='" + E_No + '\'' +
                ", E_fu=" + E_fu +
                ", E_fi=" + E_fi +
                ", E_fUc=" + E_fUc +
                ", E_fA=" + E_fA +
                ", E_fIpe=" + E_fIpe +
                ", E_fUKr=" + E_fUKr +
                '}';
    }
}
