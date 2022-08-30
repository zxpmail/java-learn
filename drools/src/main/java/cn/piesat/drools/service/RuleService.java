package cn.piesat.drools.service;

import cn.piesat.drools.model.Calculation;
import cn.piesat.drools.model.CreditCardApplyInfo;
import cn.piesat.drools.model.InsuranceInfo;
import cn.piesat.drools.utils.KieSessionUtils;
import lombok.RequiredArgsConstructor;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouxp
 */
@Service
@RequiredArgsConstructor
public class RuleService {
    private final KieBase kieBase;

    /**
     * 调用Drools规则引擎实现个人所得税计算
     * @param calculation
     * @return
     */
    public Calculation calculate(Calculation calculation){
        KieSession session = kieBase.newKieSession();
        session.insert(calculation);
        session.fireAllRules();
        session.dispose();
        return calculation;
    }

    /**
     * 调用Drools规则引擎实现信用卡申请
     * @param creditCardApplyInfo
     * @return
     */
    public CreditCardApplyInfo creditCardApply(CreditCardApplyInfo creditCardApplyInfo){
        KieSession session = kieBase.newKieSession();
        session.insert(creditCardApplyInfo);
        session.fireAllRules();
        session.dispose();
        return creditCardApplyInfo;
    }

    public List<String> insuranceInfoCheck(InsuranceInfo insuranceInfo) throws Exception{
        KieSession session = KieSessionUtils.getKieSessionFromXLS( ResourceUtils.getURL("classpath:").getPath()+"insuranceInfoCheck.xls");
        session.getAgenda().getAgendaGroup("sign").setFocus();

        session.insert(insuranceInfo);

        List<String> listRules = new ArrayList<>();
        session.setGlobal("listRules", listRules);

        session.fireAllRules();
        session.dispose();

        return listRules;
    }
}
