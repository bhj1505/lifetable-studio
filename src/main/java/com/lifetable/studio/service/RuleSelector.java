package com.lifetable.studio.service;

import com.lifetable.studio.model.LifeTableRule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RuleSelector {

    public List<LifeTableRule> selectByYear(
            List<LifeTableRule> rules,
            int elapsedYear) {

        // 1. 선택된 산출 기준을 저장할 목록
        List<LifeTableRule> selectedRules =
                new ArrayList<>();

        // 2. 전체 산출 기준을 하나씩 확인
        for (LifeTableRule rule : rules) {

            // 3. 경과기간이 적용 범위에 포함되는지 확인
            if (elapsedYear >= rule.getStartYear()
                    && elapsedYear <= rule.getEndYear()) {

                // 4. 조건에 맞으면 목록에 추가
                selectedRules.add(rule);
            }
        }

        // 5. 단계 → 차수 순서로 정렬
        selectedRules.sort(
                Comparator.comparingInt(LifeTableRule::getStep)
                        .thenComparingInt(LifeTableRule::getOrder)
        );

        // 6. 선택된 산출 기준 반환
        return selectedRules;
    }
}