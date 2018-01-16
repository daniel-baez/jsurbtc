package cl.daplay.jsurbtc.jackson.model.withdrawal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class FiatWithdrawalData extends WithdrawalData {

    private static final long serialVersionUID = 2017_08_06;

    @JsonProperty("id")
    private final long id;
    @JsonProperty("created_at")
    private final Instant createdAt;
    @JsonProperty("updated_at")
    private final Instant updatedAt;
    @JsonProperty("transacted_at")
    private final Instant transactedAt;
    @JsonProperty("statement_ref")
    private final String statementRef;
    @JsonProperty("fiat_account")
    private final Account account;
    @JsonProperty("source_account")
    private final Account sourceAccount;

    @JsonCreator
    public FiatWithdrawalData(@JsonProperty("type") String type,
                              @JsonProperty("id") long id,
                              @JsonProperty("created_at") Instant createdAt,
                              @JsonProperty("updated_at") Instant updatedAt,
                              @JsonProperty("transacted_at") Instant transactedAt,
                              @JsonProperty("statement_ref") String statementRef,
                              @JsonProperty("fiat_account") Account account,
                              @JsonProperty("source_account") Account sourceAccount) {
        super();
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.transactedAt = transactedAt;
        this.statementRef = statementRef;
        this.account = account;
        this.sourceAccount = sourceAccount;
    }

    public long getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getTransactedAt() {
        return transactedAt;
    }

    public String getStatementRef() {
        return statementRef;
    }

    public Account getAccount() {
        return account;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FiatWithdrawalData that = (FiatWithdrawalData) o;

        if (id != that.id) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;
        if (transactedAt != null ? !transactedAt.equals(that.transactedAt) : that.transactedAt != null) return false;
        if (statementRef != null ? !statementRef.equals(that.statementRef) : that.statementRef != null) return false;
        if (account != null ? !account.equals(that.account) : that.account != null) return false;
        return sourceAccount != null ? sourceAccount.equals(that.sourceAccount) : that.sourceAccount == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (transactedAt != null ? transactedAt.hashCode() : 0);
        result = 31 * result + (statementRef != null ? statementRef.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (sourceAccount != null ? sourceAccount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FiatWithdrawalData{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", transactedAt=" + transactedAt +
                ", statementRef='" + statementRef + '\'' +
                ", account=" + account +
                ", sourceAccount=" + sourceAccount +
                '}';
    }
}
