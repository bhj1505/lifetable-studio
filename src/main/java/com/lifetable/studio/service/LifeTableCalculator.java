package com.lifetable.studio.service;

import com.lifetable.studio.model.LifeTableRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LifeTableCalculator {

    private final RuleSelector ruleSelector;
    private final JexlCalculator jexlCalculator;

    // 생성자
    public LifeTableCalculator() {
        this.ruleSelector = new RuleSelector();
        this.jexlCalculator = new JexlCalculator();
    }

    // 여러 경과기간에 대한 생명표 산출
    public void calculate(
            List<LifeTableRule> allRules,
            int entryAge,
            double interestRate,
            double[] riskRates) {

        // 할인계수
        double v = 1.0 / (1.0 + interestRate);

        // 이전 연령의 생존자수 전달용
        Double prevLxNext = null;

        // 경과기간별 반복 계산
        for (int elapsedYear = 0;
             elapsedYear < riskRates.length;
             elapsedYear++) {

            // 1. 해당 경과기간 산출 기준 선택
            List<LifeTableRule> selectedRules =
                    ruleSelector.selectByYear(
                            allRules, elapsedYear
                    );

            // 2. 이번 연도 계산 변수 저장 공간
            Map<String, Object> variables =
                    new HashMap<>();

            // 3. 기본 입력값 설정
            int x = entryAge + elapsedYear;

            variables.put("x", x);
            variables.put("v", v);
            variables.put("riskRate", riskRates[elapsedYear]);

            // 4. 이전 연도 산출 결과 전달
            if (prevLxNext != null) {
                variables.put("prevLxNext", prevLxNext);
            }

            System.out.println();
            System.out.println(
                    "===== 경과기간 " + elapsedYear
                            + "년 / 도달연령 " + x + "세 ====="
            );

            // 5. 단계·차수 순서대로 계산
            for (LifeTableRule rule : selectedRules) {

                String itemName = rule.getItemName();
                String formula = rule.getFormula();

                // 6. JEXL 계산식 실행
                Object result = jexlCalculator.calculate(
                        formula, variables
                );

                if (!(result instanceof Number)) {
                    throw new IllegalStateException(
                            "숫자가 아닌 산출 결과: " + itemName
                    );
                }

                // 7. 산출 결과를 변수 Map에 저장
                variables.put(itemName, result);

                System.out.println(
                        "[단계 " + rule.getStep()
                                + " / 차수 " + rule.getOrder() + "] "
                                + itemName
                                + " = " + formula
                                + "  →  " + result
                );
            }

            // 8. 다음 연도에 전달할 생존자수 저장
            Object nextValue = variables.get("lxNext");

            if (!(nextValue instanceof Number)) {
                throw new IllegalStateException(
                        "lxNext 산출 결과가 없습니다."
                );
            }

            prevLxNext = ((Number) nextValue).doubleValue();
        }
    }
}