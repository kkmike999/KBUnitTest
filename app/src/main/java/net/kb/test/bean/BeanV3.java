package net.kb.test.bean;

import net.tsz.afinal.annotation.sqlite.Id;

/**
 * Created by kkmike999 on 2017/06/07.
 */
public class BeanV3 {

    @Id
    int id;

    int     uid;
    String  name;
    boolean isPeople;

    public BeanV3() {}

    public BeanV3(int uid, String name, boolean isPeople) {
        this.uid = uid;
        this.name = name;
        this.isPeople = isPeople;
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

    public boolean isPeople() {
        return isPeople;
    }

    public void setPeople(boolean people) {
        isPeople = people;
    }
}
