package cl.daplay.jsurbtc.jackson.model.withdrawal;

import cl.daplay.jsurbtc.model.Withdrawal;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonTypeIdResolver(WithdrawalDataTypeIdResolver.class)
public class JacksonWithdrawalData implements Withdrawal.WithdrawalData, Serializable {

    private static final long serialVersionUID = 2017_08_06;

}
