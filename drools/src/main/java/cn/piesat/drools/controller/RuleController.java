package cn.piesat.drools.controller;

import cn.piesat.drools.model.Calculation;
import cn.piesat.drools.model.CreditCardApplyInfo;
import cn.piesat.drools.model.InsuranceInfo;
import cn.piesat.drools.service.RuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouxp
 */
@RestController
@RequestMapping("/rule")
@RequiredArgsConstructor
public class RuleController {
    private final RuleService ruleService;


    @RequestMapping("/calculate")
    public Calculation calculate(double wage){
        Calculation calculation = new Calculation();
        calculation.setWage(wage);
        calculation = ruleService.calculate(calculation);
        System.out.println(calculation);
        return calculation;
    }

    @RequestMapping("/creditCardApply")
    public CreditCardApplyInfo creditCardApply(@RequestBody
                                               CreditCardApplyInfo creditCardApplyInfo){
        creditCardApplyInfo = ruleService.creditCardApply(creditCardApplyInfo);
        return creditCardApplyInfo;
    }

    @PostMapping("/insuranceInfoCheck")
    public Map<String,Object> insuranceInfoCheck(@RequestBody InsuranceInfo insuranceInfo){
        Map<String,Object>  map = new HashMap<>(3);

        //模拟数据，实际应为页面传递过来
        //InsuranceInfo insuranceInfo = new InsuranceInfo();
        //insuranceInfo.setParam1("picc");
        //insuranceInfo.setParam4("上海");
        //insuranceInfo.setParam5("1");
        //insuranceInfo.setParam6("20");
        //insuranceInfo.setParam7("3");
        //insuranceInfo.setParam8("1");
        //insuranceInfo.setParam13("5");

        try {
            List<String> list = ruleService.insuranceInfoCheck(insuranceInfo);
            if(list != null && list.size() > 0){
                map.put("checkResult",false);
                map.put("msg","准入失败");
                map.put("detail",list);
            }else{
                map.put("checkResult",true);
                map.put("msg","准入成功");
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("checkResult",false);
            map.put("msg","未知错误");
            return map;
        }
    }
}
