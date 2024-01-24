package Day25.Day25.Model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class PurchaseOrder {
   private String po_id;
   private Date created_on;
   private String name;
   private String address;
   private Date last_update;
   private List<LineItem> LIList;

    
    public List<LineItem> getLIList() {
    return LIList;
    }
    public void setLIList(List<LineItem> lIList) {
    LIList = lIList;
    }
    public PurchaseOrder() {
    }
    public String getPo_id() {
        return po_id;
    }
    public void setPo_id(String po_id) {
        this.po_id = po_id;
    }
    public Date getCreated_on() {
        return created_on;
    }
    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Date getLast_update() {
        return last_update;
    }
    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }
    @Override
    public String toString() {
        return "PurchaseOrder [po_id=" + po_id + ", created_on=" + created_on + ", name=" + name + ", address="
                + address + ", last_update=" + last_update + "]";
    }
    
     public JsonObject toJSON(){
       JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        List<JsonObject> lineItems = this.getLIList()
            .stream()
            .map(t -> t.toJSON())
            .toList();
         
         for(JsonObject x: lineItems)
            arrBuilder.add(x);
        return Json.createObjectBuilder()
                    .add("customer_name", getName())
                    .add("ship_address", getAddress())
                    .add("line_items", arrBuilder)
                    .build();
   }

   public static PurchaseOrder createJson(JsonObject o){
      PurchaseOrder po = new PurchaseOrder();
      List<LineItem> result = new LinkedList<LineItem>();
      po.setName(o.getString("customer_name"));
      po.setAddress(o.getString("ship_address"));
      JsonArray liarr = o.getJsonArray("line_items");
      for(int i=0; i < liarr.size(); i++){
         JsonObject x = liarr.getJsonObject(i);
         LineItem t = LineItem.createJson(x);
         result.add(t);
      }
      po.setLIList(result);
      return po;
   }
   
}


 
