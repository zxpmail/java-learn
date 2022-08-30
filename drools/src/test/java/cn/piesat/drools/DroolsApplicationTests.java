package cn.piesat.drools;

import cn.piesat.drools.model.*;
import cn.piesat.drools.service.UserService;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.internal.utils.KieHelper;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DroolsApplicationTests {
   private KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
    @Test
    void test1() {
        //会话对象，用于和规则引擎交互
        KieSession kieSession = kc.newKieSession("BookKB");

        //构造订单对象，设置原始价格，由规则引擎根据优惠规则计算优惠后的价格
        Order order = new Order();
        order.setOriginalPrice(210D);

        //将数据提供给规则引擎，规则引擎会根据提供的数据进行规则匹配
        kieSession.insert(order);
        kieSession.insert(new Customer(21,"male"));
        //激活规则引擎，如果规则匹配成功则执行规则
        kieSession.fireAllRules();
        //关闭会话
        kieSession.dispose();

        System.out.println("优惠前原始价格：" + order.getOriginalPrice() +
                "，优惠后价格：" + order.getRealPrice());
    }

    @Test
    void test2() {
        //会话对象，用于和规则引擎交互
        KieSession kieSession = kc.newKieSession("ComparisonKB");

        ComparisonOperatorEntity comparisonOperatorEntity = new ComparisonOperatorEntity();
        comparisonOperatorEntity.setNames("张三1");
        List<String> list = new ArrayList<>();
        list.add("张三");
        list.add("李四");
        comparisonOperatorEntity.setList(list);

        //将数据提供给规则引擎，规则引擎会根据提供的数据进行规则匹配，如果规则匹配成功则执行规则
        kieSession.insert(comparisonOperatorEntity);

        kieSession.fireAllRules();
        kieSession.dispose();
    }

    @Test
    void test3() {
        //会话对象，用于和规则引擎交互
        KieSession kieSession = kc.newKieSession("StudentKB");

        Student student = new Student();
        student.setAge(10);

        //将数据提供给规则引擎，规则引擎会根据提供的数据进行规则匹配，如果规则匹配成功则执行规则
        kieSession.insert(student);

        kieSession.fireAllRules();
        kieSession.dispose();
    }

    @Test
    void test4() {
        //会话对象，用于和规则引擎交互
        KieSession kieSession = kc.newKieSession("AttributeKB");

        kieSession.fireAllRules();
        kieSession.dispose();
    }

    @Test
    void test5() {
        //会话对象，用于和规则引擎交互
        KieSession kieSession = kc.newKieSession("AttributeKB");


        Student student = new Student();
        student.setAge(50);

        kieSession.insert(student);

        //激活规则，由Drools框架自动进行规则匹配，如果规则匹配成功，则执行当前规则
        kieSession.fireAllRules();

        //关闭会话
        kieSession.dispose();
    }

    @Test
    void test6() {
        //会话对象，用于和规则引擎交互
        KieSession kieSession = kc.newKieSession("AttributeKB");


        Student student = new Student();
        student.setAge(50);

        kieSession.insert(student);

        //激活规则，由Drools框架自动进行规则匹配，如果规则匹配成功，则执行当前规则
        kieSession.fireAllRules();

        //关闭会话
        kieSession.dispose();
    }

    @Test
    void test7() {
        //会话对象，用于和规则引擎交互
        KieSession kieSession = kc.newKieSession("ActivationGroupKB");


        kieSession.fireAllRules();
        kieSession.dispose();
    }

    @Test
    void test8() {
        //会话对象，用于和规则引擎交互
        KieSession kieSession = kc.newKieSession("AgendaGroupKB");


        kieSession.fireAllRules();
        kieSession.dispose();
    }

    @Test
    void test9() throws InterruptedException {
        //会话对象，用于和规则引擎交互
        KieSession kieSession = kc.newKieSession("TimerKB");


        new Thread(new Runnable() {
            public void run() {
                //启动规则引擎进行规则匹配，直到调用halt方法才结束规则引擎
                kieSession.fireUntilHalt();
            }
        }).start();

        Thread.sleep(10000);
        //结束规则引擎
        kieSession.halt();
        kieSession.dispose();
    }

    @Test
    void test10() {
        //设置日期格式
        System.setProperty("drools.dateformat","yyyy-MM-dd HH:mm");
        //会话对象，用于和规则引擎交互
        KieSession kieSession = kc.newKieSession("DateKB");


        kieSession.fireAllRules();
        kieSession.dispose();
    }

    @Test
    void test11() {

        //会话对象，用于和规则引擎交互
        KieSession kieSession = kc.newKieSession("GlobalKB");
        //设置全局变量，名称和类型必须和规则文件中定义的全局变量名称对应
        kieSession.setGlobal("userService",new UserService());
        kieSession.setGlobal("count",5);
        List list = new ArrayList();//size为0
        kieSession.setGlobal("gList",list);

        kieSession.fireAllRules();
        kieSession.dispose();

        //因为在规则中为全局变量添加了两个元素，所以现在的size为2
        System.out.println(list.size());
    }


    @Test
    void test12() {
        //会话对象，用于和规则引擎交互
        KieSession kieSession = kc.newKieSession("QueryKB");
        Student student1 = new Student();
        student1.setName("张三");
        student1.setAge(12);

        Student student2 = new Student();
        student2.setName("李四");
        student2.setAge(8);

        Student student3 = new Student();
        student3.setName("王五");
        student3.setAge(22);

        //将对象插入Working Memory中
        kieSession.insert(student1);
        kieSession.insert(student2);
        kieSession.insert(student3);

        //调用规则文件中的查询
        QueryResults results1 = kieSession.getQueryResults("query_1");
        int size = results1.size();
        System.out.println("size=" + size);
        for (QueryResultsRow row : results1) {
            Student student = (Student) row.get("$student");
            System.out.println(student);
        }

        //调用规则文件中的查询
        QueryResults results2 = kieSession.getQueryResults("query_2","王五");
        size = results2.size();
        System.out.println("size=" + size);
        for (QueryResultsRow row : results2) {
            Student student = (Student) row.get("$student");
            System.out.println(student);
        }
        //kieSession.fireAllRules();
        kieSession.dispose();
    }

    @Test
    void test13() {
        //会话对象，用于和规则引擎交互
        KieSession kieSession = kc.newKieSession("FunctionKB");
        Student student = new Student();
        student.setName("小明");
        student.setAge(70);

        kieSession.insert(student);

        kieSession.fireAllRules();
        kieSession.dispose();
    }

    @Test
    void test14() {
        //会话对象，用于和规则引擎交互
        KieSession kieSession = kc.newKieSession("LHSKB");
        kieSession.insert(new Student());
        kieSession.insert(new Student());
        kieSession.fireAllRules();
    }
    @Test
    void test15() {
        //会话对象，用于和规则引擎交互
        KieSession kieSession = kc.newKieSession("LHSKB");
        Student s= new Student();
        s.setAge(22);
        kieSession.insert(s);
        kieSession.fireAllRules();
    }

    @Test
    void test16() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("testRule.xls");
        SpreadsheetCompiler compiler = new SpreadsheetCompiler();
        String drl = compiler.compile(is, InputType.XLS);

        System.out.println(drl);
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);
        KieSession session = kieHelper.build().newKieSession();

        PersonInfo personInfo = new PersonInfo();
        personInfo.setSex("男");
        personInfo.setAge(35);
        personInfo.setSalary(1000);

        List<String> list = new ArrayList<>();
        session.setGlobal("listRules",list);

        session.insert(personInfo);

        session.getAgenda().getAgendaGroup("sign").setFocus();

        session.fireAllRules();

        for (String s : list) {
            System.out.println(s);
        }
        session.dispose();
    }
}
