package cl.daplay.jsurbtc.model.withdrawal;

import cl.daplay.jsurbtc.jackson.WithdrawalDataTypeIdResolver;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonTypeIdResolver(WithdrawalDataTypeIdResolver.class)
public class WithdrawalData implements Serializable {

    private static final long serialVersionUID = 2017_08_06;

}
