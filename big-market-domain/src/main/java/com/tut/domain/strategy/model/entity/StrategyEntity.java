package com.tut.domain.strategy.model.entity;

import com.tut.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyEntity {


    /**
     * 抽奖策略ID
     **/
    private Long strategyId;
    /**
     * 抽奖策略描述
     **/
    private String strategyDesc;
    /**
     * 规则模型，rule配置的模型同步到此表，便于使用
     **/
    private String ruleModels;

    /**
     * 规则模型转换
     * @return 规则模型
     */
    public String[] ruleModels(){
        if(StringUtils.isBlank(ruleModels))  return null;
        return ruleModels.split(Constants.SPLIT);
    }


    public String getRuleWeight() {
        String[] ruleModels = ruleModels();
        for(String ruleModel : ruleModels) {
            if("rule_weight".equals(ruleModel)) return ruleModel;
        }
        return null;
    }
}
