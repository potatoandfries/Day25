package Day25.Day25.Repo;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import Day25.Day25.Utils;
import Day25.Day25.Model.LineItem;
import Day25.Day25.Model.PurchaseOrder;

@Repository
public class LineItemRepo {
    
    @Autowired
    JdbcTemplate template;
    
    // insert line item
    public boolean insertLineItem(String item,int quantity, String po_id){
        return template.update(Queries.SQL_INSERT_LI,item,quantity,po_id)>=1;
    }

    //<not used but good to know?
    // get all line items from purchase order* >> this makes more sense no?
    public List<LineItem> GetAllLineItem(PurchaseOrder po){
        SqlRowSet rs = template.queryForRowSet(Queries.SQL_GET_ALL_IT_FROM_POID,po.getPo_id());
        List<LineItem> results = new LinkedList<>();

        while (rs.next()){
            results.add(Utils.toLI(rs));
        }  
        // if no task is found , you can return empty list 
        return results; 
    }
}

