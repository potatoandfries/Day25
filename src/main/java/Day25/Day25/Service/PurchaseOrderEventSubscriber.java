package Day25.Day25.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

@Service
public class PurchaseOrderEventSubscriber implements MessageListener{

    // need the transaction manager
    @Autowired
    private PlatformTransactionManager txMgr;

    // this is from the MessageListener interface
    @Override
    public void onMessage(Message message, byte[] pattern){
        
    }
    
    
    
}