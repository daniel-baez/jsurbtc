package cl.daplay.jsurbtc.model.withdrawal;

public interface BitcoinWithdrawalData extends WithdrawalData {

    String getTargetAddress();

    String getTxHash();

}
