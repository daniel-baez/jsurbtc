package cl.daplay.jsurbtc.model.withdrawal;

import cl.daplay.jsurbtc.model.Currency;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.Instant;

public class Account implements Serializable {

    private static final long serialVersionUID = 2017_10_22;

    @JsonProperty("id")
    private final long id;
    @JsonProperty("account_number")
    private final String accountNumber;
    @JsonProperty("account_type")
    private final String accountType;
    @JsonProperty("bank_id")
    private final long bankId;
    @JsonProperty("created_at")
    private final Instant createdAt;
    @JsonProperty("currency")
    private final Currency currency;
    @JsonProperty("document_number")
    private final String documentNumber;
    @JsonProperty("email")
    private final String email;
    @JsonProperty("full_name")
    private final String fullName;
    @JsonProperty("national_number_identifier")
    private final String nationalNumberIdentifier;
    @JsonProperty("phone")
    private final String phone;
    @JsonProperty("updated_at")
    private final Instant updatedAt;
    @JsonProperty("bank_name")
    private final String bankName;
    @JsonProperty("pe_cci_number")
    private final String peCciNumber;

    @JsonCreator
    public Account(@JsonProperty("id") long id,
                   @JsonProperty("account_number") String accountNumber,
                   @JsonProperty("account_type") String accountType,
                   @JsonProperty("bank_id") long bankId,
                   @JsonProperty("created_at") Instant createdAt,
                   @JsonProperty("currency") Currency currency,
                   @JsonProperty("document_number") String documentNumber,
                   @JsonProperty("email") String email,
                   @JsonProperty("full_name") String fullName,
                   @JsonProperty("national_number_identifier") String nationalNumberIdentifier,
                   @JsonProperty("phone") String phone,
                   @JsonProperty("updated_at") Instant updatedAt,
                   @JsonProperty("bank_name") String bankName,
                   @JsonProperty("pe_cci_number") String peCciNumber) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.bankId = bankId;
        this.createdAt = createdAt;
        this.currency = currency;
        this.documentNumber = documentNumber;
        this.email = email;
        this.fullName = fullName;
        this.nationalNumberIdentifier = nationalNumberIdentifier;
        this.phone = phone;
        this.updatedAt = updatedAt;
        this.bankName = bankName;
        this.peCciNumber = peCciNumber;
    }

    public long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public long getBankId() {
        return bankId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getNationalNumberIdentifier() {
        return nationalNumberIdentifier;
    }

    public String getPhone() {
        return phone;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public String getBankName() {
        return bankName;
    }

    public String getPeCciNumber() {
        return peCciNumber;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Account account = (Account) o;

        if (id != account.id) return false;
        if (bankId != account.bankId) return false;
        if (accountNumber != null ? !accountNumber.equals(account.accountNumber) : account.accountNumber != null)
            return false;
        if (accountType != null ? !accountType.equals(account.accountType) : account.accountType != null) return false;
        if (createdAt != null ? !createdAt.equals(account.createdAt) : account.createdAt != null) return false;
        if (currency != account.currency) return false;
        if (documentNumber != null ? !documentNumber.equals(account.documentNumber) : account.documentNumber != null)
            return false;
        if (email != null ? !email.equals(account.email) : account.email != null) return false;
        if (fullName != null ? !fullName.equals(account.fullName) : account.fullName != null) return false;
        if (nationalNumberIdentifier != null ? !nationalNumberIdentifier.equals(account.nationalNumberIdentifier) : account.nationalNumberIdentifier != null)
            return false;
        if (phone != null ? !phone.equals(account.phone) : account.phone != null) return false;
        if (updatedAt != null ? !updatedAt.equals(account.updatedAt) : account.updatedAt != null) return false;
        if (bankName != null ? !bankName.equals(account.bankName) : account.bankName != null) return false;
        return peCciNumber != null ? peCciNumber.equals(account.peCciNumber) : account.peCciNumber == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        result = 31 * result + (accountType != null ? accountType.hashCode() : 0);
        result = 31 * result + (int) (bankId ^ (bankId >>> 32));
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (documentNumber != null ? documentNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (nationalNumberIdentifier != null ? nationalNumberIdentifier.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (bankName != null ? bankName.hashCode() : 0);
        result = 31 * result + (peCciNumber != null ? peCciNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountType='" + accountType + '\'' +
                ", bankId=" + bankId +
                ", createdAt=" + createdAt +
                ", currency=" + currency +
                ", documentNumber='" + documentNumber + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", nationalNumberIdentifier='" + nationalNumberIdentifier + '\'' +
                ", phone='" + phone + '\'' +
                ", updatedAt=" + updatedAt +
                ", bankName='" + bankName + '\'' +
                ", peCciNumber='" + peCciNumber + '\'' +
                '}';
    }
}


