package com.yantang.juney.maintain.bean;

import java.util.List;

public class RecordsBean {

    /**
     * recordList : [{"faultList":[{"type_fault_id":"117","type_fault_name":"不通电","type_phenomenon_id":"118","type_phenomenon_name":"就不通电","type_reason_id":"119","type_reason_name":"接线","type_exclude_id":"120","type_exclude_name":"排线","remarks":"","attlist":[{"ID":75,"FILEPATH":"Imgs\\\\500\\\\1811\\\\1811021621547048795.jpg","TYPE":500},{"ID":75,"FILEPATH":"Imgs\\\\500\\\\1811\\\\1811021621592504006.jpg","TYPE":500},{"ID":75,"FILEPATH":"Imgs\\\\500\\\\1811\\\\1811021621582975164.jpg","TYPE":500},{"ID":75,"FILEPATH":"Imgs\\\\500\\\\1811\\\\1811021621575476793.jpg","TYPE":500},{"ID":75,"FILEPATH":"Imgs\\\\501\\\\1811\\\\1811021621572827107.jpg","TYPE":501}]},{"type_fault_id":"121","type_fault_name":"z1","type_phenomenon_id":"124","type_phenomenon_name":"p？1","type_reason_id":"146","type_reason_name":"jj","type_exclude_id":"150","type_exclude_name":"更换","remarks":"","attlist":[{"ID":76,"FILEPATH":"Imgs\\\\500\\\\1811\\\\1811021622544692237.jpg","TYPE":500},{"ID":76,"FILEPATH":"Imgs\\\\501\\\\1811\\\\1811021622517506967.jpg","TYPE":501}]}],"maintenance_equipment_id":"116","maintenance_equipment_name":"TP2"}]
     * mid : 48
     * user_names : njscg
     * editdatetime : 2018/11/2 16:22:55
     */

    private String mid;
    private String user_names;
    private String editdatetime;
    private List<RecordListBean> recordList;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getUser_names() {
        return user_names;
    }

    public void setUser_names(String user_names) {
        this.user_names = user_names;
    }

    public String getEditdatetime() {
        return editdatetime;
    }

    public void setEditdatetime(String editdatetime) {
        this.editdatetime = editdatetime;
    }

    public List<RecordListBean> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<RecordListBean> recordList) {
        this.recordList = recordList;
    }

    public static class RecordListBean {
        /**
         * faultList : [{"type_fault_id":"117","type_fault_name":"不通电","type_phenomenon_id":"118","type_phenomenon_name":"就不通电","type_reason_id":"119","type_reason_name":"接线","type_exclude_id":"120","type_exclude_name":"排线","remarks":"","attlist":[{"ID":75,"FILEPATH":"Imgs\\\\500\\\\1811\\\\1811021621547048795.jpg","TYPE":500},{"ID":75,"FILEPATH":"Imgs\\\\500\\\\1811\\\\1811021621592504006.jpg","TYPE":500},{"ID":75,"FILEPATH":"Imgs\\\\500\\\\1811\\\\1811021621582975164.jpg","TYPE":500},{"ID":75,"FILEPATH":"Imgs\\\\500\\\\1811\\\\1811021621575476793.jpg","TYPE":500},{"ID":75,"FILEPATH":"Imgs\\\\501\\\\1811\\\\1811021621572827107.jpg","TYPE":501}]},{"type_fault_id":"121","type_fault_name":"z1","type_phenomenon_id":"124","type_phenomenon_name":"p？1","type_reason_id":"146","type_reason_name":"jj","type_exclude_id":"150","type_exclude_name":"更换","remarks":"","attlist":[{"ID":76,"FILEPATH":"Imgs\\\\500\\\\1811\\\\1811021622544692237.jpg","TYPE":500},{"ID":76,"FILEPATH":"Imgs\\\\501\\\\1811\\\\1811021622517506967.jpg","TYPE":501}]}]
         * maintenance_equipment_id : 116
         * maintenance_equipment_name : TP2
         */

        private String maintenance_equipment_id;
        private String maintenance_equipment_name;
        private List<FaultListBean> faultList;

        public String getMaintenance_equipment_id() {
            return maintenance_equipment_id;
        }

        public void setMaintenance_equipment_id(String maintenance_equipment_id) {
            this.maintenance_equipment_id = maintenance_equipment_id;
        }

        public String getMaintenance_equipment_name() {
            return maintenance_equipment_name;
        }

        public void setMaintenance_equipment_name(String maintenance_equipment_name) {
            this.maintenance_equipment_name = maintenance_equipment_name;
        }

        public List<FaultListBean> getFaultList() {
            return faultList;
        }

        public void setFaultList(List<FaultListBean> faultList) {
            this.faultList = faultList;
        }

        public static class FaultListBean {
            /**
             * type_fault_id : 117
             * type_fault_name : 不通电
             * type_phenomenon_id : 118
             * type_phenomenon_name : 就不通电
             * type_reason_id : 119
             * type_reason_name : 接线
             * type_exclude_id : 120
             * type_exclude_name : 排线
             * remarks :
             * attlist : [{"ID":75,"FILEPATH":"Imgs\\\\500\\\\1811\\\\1811021621547048795.jpg","TYPE":500},{"ID":75,"FILEPATH":"Imgs\\\\500\\\\1811\\\\1811021621592504006.jpg","TYPE":500},{"ID":75,"FILEPATH":"Imgs\\\\500\\\\1811\\\\1811021621582975164.jpg","TYPE":500},{"ID":75,"FILEPATH":"Imgs\\\\500\\\\1811\\\\1811021621575476793.jpg","TYPE":500},{"ID":75,"FILEPATH":"Imgs\\\\501\\\\1811\\\\1811021621572827107.jpg","TYPE":501}]
             */

            private String type_fault_id;
            private String type_fault_name;
            private String type_phenomenon_id;
            private String type_phenomenon_name;
            private String type_reason_id;
            private String type_reason_name;
            private String type_exclude_id;
            private String type_exclude_name;
            private String remarks;
            private List<AttlistBean> attlist;

            public String getType_fault_id() {
                return type_fault_id;
            }

            public void setType_fault_id(String type_fault_id) {
                this.type_fault_id = type_fault_id;
            }

            public String getType_fault_name() {
                return type_fault_name;
            }

            public void setType_fault_name(String type_fault_name) {
                this.type_fault_name = type_fault_name;
            }

            public String getType_phenomenon_id() {
                return type_phenomenon_id;
            }

            public void setType_phenomenon_id(String type_phenomenon_id) {
                this.type_phenomenon_id = type_phenomenon_id;
            }

            public String getType_phenomenon_name() {
                return type_phenomenon_name;
            }

            public void setType_phenomenon_name(String type_phenomenon_name) {
                this.type_phenomenon_name = type_phenomenon_name;
            }

            public String getType_reason_id() {
                return type_reason_id;
            }

            public void setType_reason_id(String type_reason_id) {
                this.type_reason_id = type_reason_id;
            }

            public String getType_reason_name() {
                return type_reason_name;
            }

            public void setType_reason_name(String type_reason_name) {
                this.type_reason_name = type_reason_name;
            }

            public String getType_exclude_id() {
                return type_exclude_id;
            }

            public void setType_exclude_id(String type_exclude_id) {
                this.type_exclude_id = type_exclude_id;
            }

            public String getType_exclude_name() {
                return type_exclude_name;
            }

            public void setType_exclude_name(String type_exclude_name) {
                this.type_exclude_name = type_exclude_name;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public List<AttlistBean> getAttlist() {
                return attlist;
            }

            public void setAttlist(List<AttlistBean> attlist) {
                this.attlist = attlist;
            }

            public static class AttlistBean {
                /**
                 * ID : 75.0
                 * FILEPATH : Imgs\\500\\1811\\1811021621547048795.jpg
                 * TYPE : 500.0
                 */

                private String ID;
                private String FILEPATH;
                private String TYPE;

                public String getID() {
                    return ID;
                }

                public void setID(String ID) {
                    this.ID = ID;
                }

                public String getFILEPATH() {
                    return FILEPATH;
                }

                public void setFILEPATH(String FILEPATH) {
                    this.FILEPATH = FILEPATH;
                }

                public String getTYPE() {
                    return TYPE;
                }

                public void setTYPE(String TYPE) {
                    this.TYPE = TYPE;
                }
            }
        }
    }
}
