package com.yantang.juney.maintain.bean;

import java.util.List;

public class CarListInfoBean {

    /**
     * ID : 39809
     * LICENSEPLATENUMBER : 苏AU9763
     * YEAEINSPDATE : 2018/10/31 0:00:00
     * ORGANATION_NAME : 南京江北演示
     * EQUIP_ID : 17798837568
     * EQUIP_NAME : 17798837568
     * EQUIP_SIM : 17798837568
     * VEHICLETYPENAME : 新车
     * FUELTANKTYPENAME :
     * CARRIAGETYPENAME :
     * ATTLIST : [{"type":"10","path":""},{"type":"11","path":""},{"type":"12","path":""},{"type":"13","path":""},{"type":"14","path":""}]
     */

    private String ID;
    private String LICENSEPLATENUMBER;
    private String YEAEINSPDATE;
    private String ORGANATION_NAME;
    private String EQUIP_ID;
    private String EQUIP_NAME;
    private String EQUIP_SIM;
    private String VEHICLETYPENAME;
    private String FUELTANKTYPENAME;
    private String CARRIAGETYPENAME;
    private List<ATTLISTBean> ATTLIST;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLICENSEPLATENUMBER() {
        return LICENSEPLATENUMBER;
    }

    public void setLICENSEPLATENUMBER(String LICENSEPLATENUMBER) {
        this.LICENSEPLATENUMBER = LICENSEPLATENUMBER;
    }

    public String getYEAEINSPDATE() {
        return YEAEINSPDATE;
    }

    public void setYEAEINSPDATE(String YEAEINSPDATE) {
        this.YEAEINSPDATE = YEAEINSPDATE;
    }

    public String getORGANATION_NAME() {
        return ORGANATION_NAME;
    }

    public void setORGANATION_NAME(String ORGANATION_NAME) {
        this.ORGANATION_NAME = ORGANATION_NAME;
    }

    public String getEQUIP_ID() {
        return EQUIP_ID;
    }

    public void setEQUIP_ID(String EQUIP_ID) {
        this.EQUIP_ID = EQUIP_ID;
    }

    public String getEQUIP_NAME() {
        return EQUIP_NAME;
    }

    public void setEQUIP_NAME(String EQUIP_NAME) {
        this.EQUIP_NAME = EQUIP_NAME;
    }

    public String getEQUIP_SIM() {
        return EQUIP_SIM;
    }

    public void setEQUIP_SIM(String EQUIP_SIM) {
        this.EQUIP_SIM = EQUIP_SIM;
    }

    public String getVEHICLETYPENAME() {
        return VEHICLETYPENAME;
    }

    public void setVEHICLETYPENAME(String VEHICLETYPENAME) {
        this.VEHICLETYPENAME = VEHICLETYPENAME;
    }

    public String getFUELTANKTYPENAME() {
        return FUELTANKTYPENAME;
    }

    public void setFUELTANKTYPENAME(String FUELTANKTYPENAME) {
        this.FUELTANKTYPENAME = FUELTANKTYPENAME;
    }

    public String getCARRIAGETYPENAME() {
        return CARRIAGETYPENAME;
    }

    public void setCARRIAGETYPENAME(String CARRIAGETYPENAME) {
        this.CARRIAGETYPENAME = CARRIAGETYPENAME;
    }

    public List<ATTLISTBean> getATTLIST() {
        return ATTLIST;
    }

    public void setATTLIST(List<ATTLISTBean> ATTLIST) {
        this.ATTLIST = ATTLIST;
    }

    public static class ATTLISTBean {
        /**
         * type : 10
         * path :
         */

        private String type;
        private String path;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
