package Day25.Day25;


import org.springframework.jdbc.support.rowset.SqlRowSet;

import Day25.Day25.Model.LineItem;
import Day25.Day25.Model.PurchaseOrder;

public class Utils {
    //this is purely for APP config* can ignore
    public static final String REDIS="redis";

    public static LineItem toLI(SqlRowSet rs) {
        LineItem Li = new LineItem();
        Li.setId(rs.getInt("order_id"));
        Li.setItem(rs.getString("item"));
        Li.setQuantity(rs.getInt("quantity"));
        Li.setPo_id(rs.getString("po_id"));

        return Li;
    }

    public static PurchaseOrder toPO(SqlRowSet rs) {
        PurchaseOrder Po = new PurchaseOrder();
        Po.setPo_id(rs.getString("po_id"));
        Po.setCreated_on(rs.getDate("created_on"));
        Po.setName(rs.getString("name"));
        Po.setAddress(rs.getString("address"));
        Po.setLast_update(rs.getDate("last_update"));

        return Po;
    }
  
}