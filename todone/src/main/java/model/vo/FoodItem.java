package model.vo;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-6-17
 * Time: 下午3:15
 * To change this template use File | Settings | File Templates.
 */
public class FoodItem {

    private Integer id = 1;
    private String title = "美食";
    private String category = "南方菜";
    private String price = "17.9";
    private String detail = "没啥";

    public FoodItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
