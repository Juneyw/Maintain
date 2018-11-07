package com.yantang.juney.maintain.bean;

/**
 * Created by wangqing on 2017/7/28.
 * 激活页面获取Ip和端口号等信息
 */

public class ActiviteIPBean {
    /**
     * result : success
     * info : {"I":"15","N":"南京121","F":"南京云计趟信息技术有限公司","W":"115.29.49.79:8008","A":"121.41.25.156:9601","T":"","L":"","X":"115.29.49.79:19551"}
     */

    private String result;
    private InfoBean info;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * I : 15
         * N : 南京121
         * F : 南京云计趟信息技术有限公司
         * W : 115.29.49.79:8008
         * A : 121.41.25.156:9601
         * T :
         * L :
         * X : 115.29.49.79:19551
         * ER : -1 识别码为空，-2 出现异常，-3 无效的识别码
         */

        private String I;
        private String N;
        private String F;
        private String W;
        private String A;
        private String T;
        private String L;
        private String X;
        private String ER;

        public String getER() {
            return ER;
        }

        public void setER(String ER) {
            this.ER = ER;
        }

        public String getI() {
            return I;
        }

        public void setI(String I) {
            this.I = I;
        }

        public String getN() {
            return N;
        }

        public void setN(String N) {
            this.N = N;
        }

        public String getF() {
            return F;
        }

        public void setF(String F) {
            this.F = F;
        }

        public String getW() {
            return W;
        }

        public void setW(String W) {
            this.W = W;
        }

        public String getA() {
            return A;
        }

        public void setA(String A) {
            this.A = A;
        }

        public String getT() {
            return T;
        }

        public void setT(String T) {
            this.T = T;
        }

        public String getL() {
            return L;
        }

        public void setL(String L) {
            this.L = L;
        }

        public String getX() {
            return X;
        }

        public void setX(String X) {
            this.X = X;
        }
    }



}
