package Day25.Day25.Model;



import jakarta.json.Json;
import jakarta.json.JsonObject;

public class LineItem {

    private Integer id;
    private String  item;
    private Integer quantity;
    private String po_id;

    

    public LineItem() {
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getItem() {
        return item;
    }
    public void setItem(String item) {
        this.item = item;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public String getPo_id() {
        return po_id;
    }
    public void setPo_id(String po_id) {
        this.po_id = po_id;
    }

    @Override
    public String toString() {
        return "LineItem [id=" + id + ", item=" + item + ", quantity=" + quantity + ", po_id=" + po_id + "]";
    }

    public JsonObject toJSON(){
        return Json.createObjectBuilder()
                    .add("product", getItem())
                    .add("quantity", getQuantity())
                    .build();
   }

   public static LineItem createJson(JsonObject o){
      LineItem li = new LineItem();
      li.setItem(o.getString("product"));
      li.setQuantity(o.getJsonNumber("quantity").intValue());
      return li;
   }
}
    


