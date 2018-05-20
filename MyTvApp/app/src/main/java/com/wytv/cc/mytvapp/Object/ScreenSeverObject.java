package com.wytv.cc.mytvapp.Object;

import com.google.gson.Gson;

public class ScreenSeverObject {
    //    {"data":{"status":{"id":3548,"server_id":1,"cpu_usage":12,"mem_total":7950,"mem_used":1547,"mem_usage":0.19,
    // "hd_total":35,"hd_used":6,"hd_usage":"1.00","tast_running":0,"detection_time":"2018-05-19 17:30:02","create_time":1526722202},
    // "ping":false,"ip":"10.17.91.196","time":"2018-05-19 17:49:07","timeago":"9分钟前","intranet":"正常","outernet":"正常",
    // "screen_time":"2018-05-19 17:48:16"},"status":1,"msg":"获取成功"}

    private String screen_time;// 最后查询时间
    private String timeago;// 听盾时间
    private String intranet;// 内网
    private String outernet;// 外网
    private Status status;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getScreen_time() {
        return screen_time;
    }

    public void setScreen_time(String screen_time) {
        this.screen_time = screen_time;
    }

    public String getTimeago() {
        return timeago;
    }

    public void setTimeago(String timeago) {
        this.timeago = timeago;
    }

    public String getIntranet() {
        return intranet;
    }

    public void setIntranet(String intranet) {
        this.intranet = intranet;
    }

    public String getOuternet() {
        return outernet;
    }

    public void setOuternet(String outernet) {
        this.outernet = outernet;
    }

    public static ScreenSeverObject getObj(String jsStr) {
        Gson gson = new Gson();
        ScreenSeverObject result = gson.fromJson(jsStr, ScreenSeverObject.class);
        return result;
    }

    public class Status {
        private String cpu_usage;// cpu用量
        private String hd_used;// 磁盘已用
        private String hd_total;// 磁盘总
        private float mem_usage;// 内存已用

        public String getCpu_usage() {
            return cpu_usage;
        }

        public void setCpu_usage(String cpu_usage) {
            this.cpu_usage = cpu_usage;
        }

        public String getHd_used() {
            return hd_used;
        }

        public void setHd_used(String hd_used) {
            this.hd_used = hd_used;
        }

        public String getHd_total() {
            return hd_total;
        }

        public void setHd_total(String hd_total) {
            this.hd_total = hd_total;
        }

        public float getMem_usage() {
            return mem_usage;
        }

        public void setMem_usage(float mem_usage) {
            this.mem_usage = mem_usage;
        }
    }
}
