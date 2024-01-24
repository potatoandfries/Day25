package Day25.Day25.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import Day25.Day25.Model.LineItem;
import Day25.Day25.Model.PurchaseOrder;
import Day25.Day25.Repo.LineItemRepo;
import Day25.Day25.Repo.PurchaseOrderRepo;

@Service
public class PurchaseOrderService {
    
    @Autowired
    LineItemRepo repo1;

    @Autowired
    PurchaseOrderRepo repo2;

    @Autowired @Qualifier("registrationCache")
    private RedisTemplate<String,String> template;

    @Autowired @Qualifier("poPubSub")
    private ChannelTopic topic;
    
    
    public boolean createPurchaseOrderManualTx(PurchaseOrder po){
        template.convertAndSend(
            topic.getTopic(),
            po.toJSON().toString());
            return false;

    }

    public String[] getAllRegisteredCustomer(){
        return template.opsForList().range("registration",0,-1).toArray(new String[0]);
    }

    public boolean createOrder(PurchaseOrder po){

        String oId = UUID.randomUUID().toString().substring(0, 8);
        System.out.println(oId);
        po.setPo_id(oId);


            boolean POInserted = repo2.insertPurchaseOrder(po.getPo_id(),po.getCreated_on(),po.getName(),po.getAddress(),po.getLast_update());
            if (!POInserted) {
                return false; // Handle the case where inserting the POInserted failed
            }

            // Insert each order detail
            for (LineItem LineItem : po.getLIList()) {
                boolean LineItemInserted = repo1.insertLineItem(LineItem.getItem(),LineItem.getQuantity(),po.getPo_id());
                if (!LineItemInserted) {
                
                    return false; // Handle the case where inserting an Line Item failed
                }
            }

                return true; // If everything is inserted successfully
            }

}

