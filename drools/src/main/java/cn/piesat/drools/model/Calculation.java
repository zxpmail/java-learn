package cn.piesat.drools.model;

import lombok.Data;

/**
 * @author zhouxp
 */
@Data
public class Calculation {
    /**
     * 税前工资
     */
    private double wage;
    /**
     * 应纳税所得额
     */
    private double wagemore;
    /**
     * 税率
     */
    private double cess;
    /**
     * 速算扣除数
     */
    private double preminus;
    /**
     * 扣税额
     */
    private double wageminus;
    /**
     * 税后工资
     */
    private double actualwage;

}
