package Day25.Day25.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import Day25.Day25.Model.*;
import Day25.Day25.Service.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping
public class PurchaseOrderController {

    public static final String ATTR_PURCHASE_ORDER = "po";
    public static final String ATTR_CUSTOMER_NAMES = "names";

    public static final String CTRL_ITEM = "item";
    public static final String CTRL_QUANTITY = "quantity";

    @Autowired
    PurchaseOrderService poSvc;

    @GetMapping(path = { "/", "/index.html" })
    public ModelAndView getIndex(HttpSession sess) {
        ModelAndView mav = new ModelAndView("index.html");
        PurchaseOrder po = getPurchaseOrder(sess);
        String[] names = poSvc.getAllRegisteredCustomer();
        mav.addObject(ATTR_PURCHASE_ORDER, po);
        mav.addObject(ATTR_CUSTOMER_NAMES, names);
        return mav;
    }

    @PostMapping("/checkout")
    public ModelAndView posCheckout(HttpSession sess) {

        ModelAndView mav = new ModelAndView("index.html");

        PurchaseOrder po = getPurchaseOrder(sess);

        if (poSvc.createPurchaseOrderManualTx(po)) {
            mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            mav.addObject(ATTR_PURCHASE_ORDER, po);
        } else {
            mav.setStatus(HttpStatus.OK);
            sess.invalidate();
            mav.addObject(ATTR_PURCHASE_ORDER, new PurchaseOrder());
        }

        /*
        try {
            if (!poSvc.createPurchaseOrder(po)) {
                mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                mav.addObject(ATTR_PURCHASE_ORDER, po);
            } else {
                mav.setStatus(HttpStatus.OK);
                sess.invalidate();
                mav.addObject(ATTR_PURCHASE_ORDER, new PurchaseOrder());
            }
        } catch (PurchaseOrderException ex) {
            mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            mav.addObject(ATTR_PURCHASE_ORDER, po);
        }
        */

        return mav;
    }

    @PostMapping("/order")
    public ModelAndView postOrder(HttpSession sess, @ModelAttribute PurchaseOrder inputPo,
            @RequestBody MultiValueMap<String, String> form) {

        PurchaseOrder po = getPurchaseOrder(sess);
        String[] names = poSvc.getAllRegisteredCustomer();
        po.setName(inputPo.getName());
        po.setAddress(inputPo.getAddress());

        LineItem li = new LineItem();
        li.setItem(form.getFirst(CTRL_ITEM));
        li.setQuantity(Integer.parseInt(form.getFirst(CTRL_QUANTITY)));
        po.getLIList().add(li);

        ModelAndView mav = new ModelAndView("index.html");

        System.out.printf(">>>> form %s\n", form);
        System.out.printf(">>>> inputPo %s\n", inputPo);

        mav.addObject(ATTR_PURCHASE_ORDER, po);
        mav.addObject(ATTR_CUSTOMER_NAMES, names);
        return mav;
    }

    private PurchaseOrder getPurchaseOrder(HttpSession sess) {
        Object o = sess.getAttribute(ATTR_PURCHASE_ORDER);
        PurchaseOrder po;
        if (null == o) {
            po = new PurchaseOrder();
            sess.setAttribute(ATTR_PURCHASE_ORDER, po);
        } else
            po = (PurchaseOrder) o;
        return po;
    }
}
