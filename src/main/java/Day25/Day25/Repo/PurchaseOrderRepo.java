package Day25.Day25.Repo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import Day25.Day25.Utils;
import Day25.Day25.Model.PurchaseOrder;

@Repository
public class PurchaseOrderRepo {
    
    @Autowired
    JdbcTemplate template;

    // insert purchase order item
    public boolean insertPurchaseOrder(String po_id, Date created_on,String name,String address,Date last_update){
        return template.update(Queries.SQL_INSERT_PO,po_id,created_on,name,address,last_update)>=1;
    }
    //<not used but good to know?
    //get purchase order from poid
    public PurchaseOrder getPurchaseOrder(String po_id){
        SqlRowSet rs = template.queryForRowSet(Queries.SQL_GET_PO_FROM_POID, po_id); 

        while (rs.next()){
            return Utils.toPO(rs);
        }
        return null; //return null if no match is found
    }
    //<not used but good to know?
     // get all purchase orders
    public List<PurchaseOrder> GetAllPurchaseOrder(){
        SqlRowSet rs = template.queryForRowSet(Queries.SQL_GET_ALL_PO);
        List<PurchaseOrder> results = new LinkedList<>();

        while (rs.next()){
            results.add(Utils.toPO(rs));
        }  
        // if no task is found , you can return empty list 
        return results; 
    }
}
