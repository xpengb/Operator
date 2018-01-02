package com.nxt.xpengb.operator.model;

import java.util.List;

/**
 * @author: xpengb@outlook.com
 * @date: 2017/11/20
 * @version: v1.0
 * @desc:
 */

public class FarmingAdd {
    /**
     * list : {"deleted":[],"inserted":[{"id":"","landNumber":"","workarea":"","date":"","note":"","operator1":"","batchId":"","palntName":"","projectCode":"","varietyName":"","proId":""}],"updated":[],"_changed":true}
     */

    private ListBean list;

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * deleted : []
         * inserted : [{"id":"","landNumber":"","workarea":"","date":"","note":"","operator1":"","batchId":"","palntName":"","projectCode":"","varietyName":"","proId":""}]
         * updated : []
         * _changed : true
         */

        private boolean _changed;
        private List<?> deleted;
        private List<InsertedBean> inserted;
        private List<?> updated;

        public ListBean(List<InsertedBean> inserted, List<?> deleted, List<?> updated, boolean _changed) {
            this.inserted = inserted;
            this.deleted = deleted;
            this.updated = updated;
            this._changed = _changed;
        }

        public boolean is_changed() {
            return _changed;
        }

        public void set_changed(boolean _changed) {
            this._changed = _changed;
        }

        public List<?> getDeleted() {
            return deleted;
        }

        public void setDeleted(List<?> deleted) {
            this.deleted = deleted;
        }

        public List<InsertedBean> getInserted() {
            return inserted;
        }

        public void setInserted(List<InsertedBean> inserted) {
            this.inserted = inserted;
        }

        public List<?> getUpdated() {
            return updated;
        }

        public void setUpdated(List<?> updated) {
            this.updated = updated;
        }

        public static class InsertedBean {
            /**
             * id :
             * landNumber :
             * workarea :
             * date :
             * note :
             * operator1 :
             * batchId :
             * palntName :
             * projectCode :
             * varietyName :
             * proId :
             */
            private String id;
            private String landNumber;
            private String workarea;
            private String date;
            private String note;
            private String operator1;
            private String batchId;
            private String palntName;
            private String projectCode;
            private String varietyName;
            private String proId;

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

            public String getPalntName() {
                return palntName;
            }

            public void setPalntName(String palntName) {
                this.palntName = palntName;
            }

            public String getProjectCode() {
                return projectCode;
            }

            public void setProjectCode(String projectCode) {
                this.projectCode = projectCode;
            }

            public String getVarietyName() {
                return varietyName;
            }

            public void setVarietyName(String varietyName) {
                this.varietyName = varietyName;
            }

            public String getProId() {
                return proId;
            }

            public void setProId(String proId) {
                this.proId = proId;
            }
        }
    }
}
