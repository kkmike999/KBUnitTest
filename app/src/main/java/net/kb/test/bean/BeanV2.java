package net.kb.test.bean;

import net.tsz.afinal.annotation.sqlite.Id;

/**
 * Created by kkmike999 on 2017/06/07.
 */
public class BeanV2 {

    private static final int TYPE_AD = 1;

    @Id
    int id;

    int    uid;
    String name;

    public BeanV2() {
    }

    public BeanV2(int uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
