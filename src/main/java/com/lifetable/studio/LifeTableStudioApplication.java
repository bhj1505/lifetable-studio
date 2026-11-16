package com.lifetable.studio;

import com.lifetable.studio.model.LifeTableRule;
import com.lifetable.studio.repository.SampleRuleRepository;
import com.lifetable.studio.service.LifeTableCalculator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class LifeTableStudioApplication {

    public static void main(String[] args) {
        SpringApplication.run(LifeTableStudioApplication.class, args);
        //LifeTableRule rule = new LifeTableRule(
        //       1, 1, 0, 999, "q1x", "qx"
        //);

       // System.out.println("생명표 항목 : " + rule.getItemName());
       // System.out.println("계산식 : " + rule.getFormula());

        // 산출 기준 저장소 객체 생성
        SampleRuleRepository repository =
                new SampleRuleRepository();

        // 산출 기준 목록 조회
        List<LifeTableRule> rules = repository.findAll();

        // 2. 가입연령
        int entryAge = 30;

        // 3. 예정이율 2.5%
        double interestRate = 0.025;

        // 4. 도달연령 30~32세의 가상 사망률
        double[] riskRates = {
                0.0010,
                0.0012,
                0.0015
        };

        // 5. 생명표 산출기 생성
        LifeTableCalculator calculator =
                new LifeTableCalculator();

        // 6. 생명표 산출 실행
        calculator.calculate(
                rules,
                entryAge,
                interestRate,
                riskRates
        );

    }

}
