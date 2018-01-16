package cl.daplay.jsurbtc.model.order;

import java.io.Serializable;
import java.util.List;

public interface OrderBook extends Serializable {

    List<Offer> getBids();

    List<Offer> getAsks();

}
