package Day25.Day25.Repo;

public class Queries {
    
    //Crud --  err I think you only need Insert and select all here*
    // if udk what you need to add in  just look at the schemaa they give.
    //<done>
    public static final String  SQL_INSERT_LI=""" 
        insert into line_item (item,quantity,po_id)
        values(?, ?, ?)
    """;
    //<done>
    public static final String  SQL_INSERT_PO="""
        insert into purchase_order (po_id, created_on, name, address, last_update)
        values(?,?,?,?,?)
    """;
    //<done>
    public static final String SQL_GET_PO_FROM_POID="""
        select po_id
                from purchase_order
                where po_id = ?
                """;
    //<done>
    public static final String SQL_GET_ALL_PO="""
        select * from purchase_order

            """;
    //<done>
    public static final String SQL_GET_ALL_IT_FROM_POID="""
        select * from line_item
            where po_id= ?
            """;
                 
 
}
