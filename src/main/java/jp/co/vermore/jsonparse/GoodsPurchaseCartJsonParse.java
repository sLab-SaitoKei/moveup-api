package jp.co.vermore.jsonparse;


import jp.co.vermore.common.mvc.BaseJsonParse;

public class GoodsPurchaseCartJsonParse extends BaseJsonParse {

    private Long id;

    private Integer price;

    private Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}