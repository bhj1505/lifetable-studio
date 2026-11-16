package com.lifetable.studio.repository;

import com.lifetable.studio.model.LifeTableRule;

import java.util.ArrayList;
import java.util.List;

public class SampleRuleRepository {
    public List<LifeTableRule> findAll() {

        List<LifeTableRule> rules = new ArrayList<>();

        /*
         * 1단계 : 기초위험률
         *
         * qx : 해당 연령의 1년간 사망률
         *
         * riskRate : 외부에서 전달받는 연령별 사망률
         */
        rules.add(new LifeTableRule(
                1, 1, 0, 999,
                "qx",
                "riskRate"
        ));

        /*
         * 2단계 : 생존자수
         *
         * 최초 경과년도(0년) :
         *   lx = 100000
         *
         * 이후 경과년도(1년 이상) :
         *   lx = 이전 연령의 lxNext
         *
         * prevLxNext : 이전 연령의 생존자수 산출 결과
         */
        rules.add(new LifeTableRule(
                2, 1, 0, 0,
                "lx",
                "100000"
        ));

        rules.add(new LifeTableRule(
                2, 1, 1, 999,
                "lx",
                "prevLxNext"
        ));

        /*
         * 3단계 : 사망자수
         *
         * dx = lx × qx
         */
        rules.add(new LifeTableRule(
                3, 1, 0, 999,
                "dx",
                "lx * qx"
        ));

        /*
         * 4단계 : 다음 연령 생존자수
         *
         * lxNext = lx - dx
         */
        rules.add(new LifeTableRule(
                4, 1, 0, 999,
                "lxNext",
                "lx - dx"
        ));

        /*
         * 5단계 : 현가기수
         *
         * Dx = lx × v^x
         * Cx = dx × v^(x+1)
         *
         * v : 할인계수 = 1 / (1 + i)
         * x : 도달연령
         *
         * pow() : 향후 JEXL에서 등록할 거듭제곱 함수
         */
        rules.add(new LifeTableRule(
                5, 1, 0, 999,
                "Dx",
                "math:pow(v, x) * lx"
        ));

        rules.add(new LifeTableRule(
                5, 2, 0, 999,
                "Cx",
                "math:pow(v, x + 1) * dx"
        ));

        //3. JexlCalculator.java 수정 할차례

        return rules;
    }
}
