package cn.piesat.drools.model;

import lombok.Data;

/**
 * @author zhouxp
 */
@Data
public class Order {
    /**
     * 订单原始价格，即优惠前价格
     */
    private Double originalPrice;
    /**
     * 订单真实价格，即优惠后价格
     */
    private Double realPrice;
}
