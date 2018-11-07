package com.yantang.juney.maintain.bean;

import java.util.List;

public class BKRecordBean {
//    [  // 维修记录
//    {
//        "maintenance_equipment_id":"1",   // 维修设备id
//            "maintenance_equipment_name":"D5",  // 维修设备名称
//            "faultList":[  // 故障信息
//        {
//            "type_fault_id":"2",  // 故障类型id
//                "type_fault_name":"故障类型test",   // 故障类型名称
//                "type_phenomenon_id":"3",    // 故障现象id
//                "type_phenomenon_name":"现象test",  // 故障现象名称
//                "type_reason_id":"4",  // 故障原因id
//                "type_reason_name":"原因test",  // 故障原因名称
//                "type_exclude_id":"5",  // 故障排除id
//                "type_exclude_name":"排除test",  // 故障排除名称
//                "attlist":[  // 附件照片信息
//            {
//                "path":"Imgs\10\1807\1114.jpg",  // 路径
//                    "type":"500"   // 500：表示维修前
//            },
//            {
//                "path":"Imgs\10\1807\1112.jpg",  // 路径
//                    "type":"501"  // 501：表示维修后
//            }
//                    ],
//            "remarks":"1"   // 备注
//        }, 。。。。
//    ]

    private String maintenance_equipment_id;
    private String maintenance_equipment_name;
    private List<FaultListBean> faultList;

    public String getMaintenance_equipment_id() {
        return this.maintenance_equipment_id;
    }

    public void setMaintenance_equipment_id(String maintenance_equipment_id) {
        this.maintenance_equipment_id = maintenance_equipment_id;
    }

    public String getMaintenance_equipment_name() {
        return this.maintenance_equipment_name;
    }

    public void setMaintenance_equipment_name(String maintenance_equipment_name) {
        this.maintenance_equipment_name = maintenance_equipment_name;
    }

    public List<FaultListBean> getFaultList() {
        return this.faultList;
    }

    public void setFaultList(List<FaultListBean> faultList) {
        this.faultList = faultList;
    }

    public static class FaultListBean{
//        {
//            "type_fault_id":"2",  // 故障类型id
//                "type_fault_name":"故障类型test",   // 故障类型名称
//                "type_phenomenon_id":"3",    // 故障现象id
//                "type_phenomenon_name":"现象test",  // 故障现象名称
//                "type_reason_id":"4",  // 故障原因id
//                "type_reason_name":"原因test",  // 故障原因名称
//                "type_exclude_id":"5",  // 故障排除id
//                "type_exclude_name":"排除test",  // 故障排除名称
//                "attlist":[  // 附件照片信息
//            {
//                "path":"Imgs\10\1807\1114.jpg",  // 路径
//                    "type":"500"   // 500：表示维修前
//            },
//            {
//                "path":"Imgs\10\1807\1112.jpg",  // 路径
//                    "type":"501"  // 501：表示维修后
//            }
//                    ],
//            "remarks":"1"   // 备注
//        }

        private String type_fault_id;
        private String type_fault_name;
        private String type_phenomenon_id;
        private String type_phenomenon_name;
        private String type_reason_id;
        private String type_reason_name;
        private String type_exclude_id;
        private String type_exclude_name;
        private List<BKRecordBean.FaultListBean.AttlistBean> attlist;
        private String remarks;

        public String getType_fault_id() {
            return this.type_fault_id;
        }

        public void setType_fault_id(String type_fault_id) {
            this.type_fault_id = type_fault_id;
        }

        public String getType_fault_name() {
            return this.type_fault_name;
        }

        public void setType_fault_name(String type_fault_name) {
            this.type_fault_name = type_fault_name;
        }

        public String getType_phenomenon_id() {
            return this.type_phenomenon_id;
        }

        public void setType_phenomenon_id(String type_phenomenon_id) {
            this.type_phenomenon_id = type_phenomenon_id;
        }

        public String getType_phenomenon_name() {
            return this.type_phenomenon_name;
        }

        public void setType_phenomenon_name(String type_phenomenon_name) {
            this.type_phenomenon_name = type_phenomenon_name;
        }

        public String getType_reason_id() {
            return this.type_reason_id;
        }

        public void setType_reason_id(String type_reason_id) {
            this.type_reason_id = type_reason_id;
        }

        public String getType_reason_name() {
            return this.type_reason_name;
        }

        public void setType_reason_name(String type_reason_name) {
            this.type_reason_name = type_reason_name;
        }

        public String getType_exclude_id() {
            return this.type_exclude_id;
        }

        public void setType_exclude_id(String type_exclude_id) {
            this.type_exclude_id = type_exclude_id;
        }

        public String getType_exclude_name() {
            return this.type_exclude_name;
        }

        public void setType_exclude_name(String type_exclude_name) {
            this.type_exclude_name = type_exclude_name;
        }

        public List<BKRecordBean.FaultListBean.AttlistBean> getAttlist() {
            return this.attlist;
        }

        public void setAttlist(List<BKRecordBean.FaultListBean.AttlistBean> attlist) {
            this.attlist = attlist;
        }

        public String getRemarks() {
            return this.remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public static class AttlistBean{
            private String path;
            private String type;

            public String getPath() {
                return this.path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getType() {
                return this.type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }



    }
}
