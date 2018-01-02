package com.nxt.xpengb.operator.model;

import java.util.List;

/**
 * @author: xpengb@outlook.com
 * @date: 2017/11/20
 * @version: v1.0
 * @desc:
 */

public class FarmWork {
    /**
     * rows : [{"id":"5d589b0c43274256a3d7265e4da33e62","landNumber":"1号地","workarea":"14","date":"2015-09-09T00:00:00","note":"除草","operator1":"李大秋","batchId":"20150506002","type":"321","userid":"59e3a3665b824dd69b3f5bd14cbec5e9","palntName":"","varietyName":"","proId":0,"ProjectCode":"201306030001","Adduser":null,"latitude":null,"longitude":null,"farmWorkImgList":"/Upload/201306030001/20171116090851737.jpg"},{"id":"6aa2a2d712dd4d1f85bcee7b13412fb8","landNumber":"1号地","workarea":"20","date":"2015-09-23T00:00:00","note":"浇水","operator1":"张亮","batchId":"20150506002","type":"321","userid":"11","palntName":"","varietyName":"","proId":0,"ProjectCode":"201306030001","Adduser":null,"latitude":null,"longitude":null,"farmWorkImgList":"/Upload/201306030001/20171116090851737.jpg"}]
     * total : 45
     */

    private int total;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 5d589b0c43274256a3d7265e4da33e62
         * landNumber : 1号地
         * workarea : 14
         * date : 2015-09-09T00:00:00
         * note : 除草
         * operator1 : 李大秋
         * batchId : 20150506002
         * type : 321
         * userid : 59e3a3665b824dd69b3f5bd14cbec5e9
         * palntName :
         * varietyName :
         * proId : 0
         * ProjectCode : 201306030001
         * Adduser : null
         * latitude : null
         * longitude : null
         * farmWorkImgList : /Upload/201306030001/20171116090851737.jpg
         */

        private String id;
        private String landNumber;
        private String workarea;
        private String date;
        private String note;
        private String operator1;
        private String batchId;
        private String type;
        private String userid;
        private String palntName;
        private String varietyName;
        private int proId;
        private String ProjectCode;
        private Object Adduser;
        private Object latitude;
        private Object longitude;
        private String farmWorkImgList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLandNumber() {
            return landNumber;
        }

        public void setLandNumber(String landNumber) {
            this.landNumber = landNumber;
        }

        public String getWorkarea() {
            return workarea;
        }

        public void setWorkarea(String workarea) {
            this.workarea = workarea;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getOperator1() {
            return operator1;
        }

        public void setOperator1(String operator1) {
            this.operator1 = operator1;
        }

        public String getBatchId() {
            return batchId;
        }

        public void setBatchId(String batchId) {
            this.batchId = batchId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getPalntName() {
            return palntName;
        }

        public void setPalntName(String palntName) {
            this.palntName = palntName;
        }

        public String getVarietyName() {
            return varietyName;
        }

        public void setVarietyName(String varietyName) {
            this.varietyName = varietyName;
        }

        public int getProId() {
            return proId;
        }

        public void setProId(int proId) {
            this.proId = proId;
        }

        public String getProjectCode() {
            return ProjectCode;
        }

        public void setProjectCode(String ProjectCode) {
            this.ProjectCode = ProjectCode;
        }

        public Object getAdduser() {
            return Adduser;
        }

        public void setAdduser(Object Adduser) {
            this.Adduser = Adduser;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public String getFarmWorkImgList() {
            return farmWorkImgList;
        }

        public void setFarmWorkImgList(String farmWorkImgList) {
            this.farmWorkImgList = farmWorkImgList;
        }
    }
}
