package jp.co.vermore.form;

import jp.co.vermore.jsonparse.GoodsPurchaseCartJsonParse;

import java.util.List;

public class PurchaseEditForm {
    private List<GoodsPurchaseCartJsonParse> cartList;

    public List<GoodsPurchaseCartJsonParse> getCartList() {
        return cartList;
    }

    public void setCartList(List<GoodsPurchaseCartJsonParse> cartList) {
        this.cartList = cartList;
    }
}
