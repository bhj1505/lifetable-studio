package com.lifetable.studio.service;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;

import java.util.Map;

public class JexlCalculator {

    private final JexlEngine jexl;

    // 생성자 : 계산 엔진 생성
    public JexlCalculator() {

        this.jexl = new JexlBuilder()
                .strict(true)
                .silent(false)
                .namespaces(Map.of("math", Math.class))
                .create();
    }

    // 계산식과 변수값을 전달받아 계산
    public Object calculate(
            String formula,
            Map<String, Object> variables) {

        // 1. 문자열 계산식을 JEXL 표현식으로 변환
        JexlExpression expression =
                jexl.createExpression(formula);

        // 2. 계산에 사용할 변수 저장 공간 생성
        JexlContext context = new MapContext();

        // 3. 전달받은 변수들을 JEXL에 등록
        for (Map.Entry<String, Object> entry
                : variables.entrySet()) {

            context.set(
                    entry.getKey(),
                    entry.getValue()
            );
        }

        // 4. 계산식 실행 후 결과 반환
        return expression.evaluate(context);
    }
}