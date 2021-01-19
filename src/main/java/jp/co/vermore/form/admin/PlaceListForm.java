package jp.co.vermore.form.admin;

/**
 * PlaceListForm
 * Created by winbin.
 *
 * DateTime: 2018/04/18 17:06
 * Copyright: sLab, Corp
 */

public class PlaceListForm extends DatatablesBaseForm{

    private String name;

    private String location;

    private String tel;

    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
