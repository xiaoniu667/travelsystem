package cn.ag.domain;

import java.util.Date;

public class MyFavorite {
    private int uid;
    private Date date;
    private int rid;

    @Override
    public String toString() {
        return "MyFavorite{" +
                "uid=" + uid +
                ", date=" + date +
                ", rid=" + rid +
                '}';
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }


}
