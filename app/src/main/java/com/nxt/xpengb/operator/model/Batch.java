package com.nxt.xpengb.operator.model;

import java.util.List;

/**
 * @author: xpengb@outlook.com
 * @date: 2017/11/17
 * @version: v1.0
 * @desc:
 */

public class Batch {
    /**
     * rows : [{"value":"20150102002","text":"圆白菜","ProjectCode":"201306030001","grade":"箱装","manuFacturer":"自产原料"},{"value":"20150506002","text":"牛奶1","ProjectCode":"201306030001","grade":"箱装","manuFacturer":"自产原料"}]
     * total : 11
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
         * value : 20150102002
         * text : 圆白菜
         * ProjectCode : 201306030001
         * grade : 箱装
         * manuFacturer : 自产原料
         */

        private String value;
        private String text;
        private String ProjectCode;
        private String grade;
        private String manuFacturer;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getProjectCode() {
            return ProjectCode;
        }

        public void setProjectCode(String ProjectCode) {
            this.ProjectCode = ProjectCode;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getManuFacturer() {
            return manuFacturer;
        }

        public void setManuFacturer(String manuFacturer) {
            this.manuFacturer = manuFacturer;
        }
    }
}
