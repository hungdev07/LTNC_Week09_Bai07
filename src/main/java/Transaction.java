import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * Đại diện cho một giao dịch.
 */
public class Transaction {
    private static final Logger logger = LoggerFactory.getLogger(Transaction.class);
    private static final java.util.Locale DEFAULT_LOCALE = Locale.US;

    public static final int TYPE_DEPOSIT_CHECKING = 1;
    public static final int TYPE_WITHDRAW_CHECKING = 2;
    public static final int TYPE_DEPOSIT_SAVINGS = 3;
    public static final int TYPE_WITHDRAW_SAVINGS = 4;

    private int type;
    private double amount;
    private double initialBalance;
    private double finalBalance;

    public Transaction(int type, double amount, double initialBalance, double finalBalance) {
        this.type = type;
        this.amount = amount;
        this.initialBalance = initialBalance;
        this.finalBalance = finalBalance;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public double getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(double finalBalance) {
        this.finalBalance = finalBalance;
    }

    /**
     * Chuyển đổi mã giao dịch thành chuỗi mô tả tiếng Việt
     *
     * @param typeId mã định danh loại giao dịch
     * @return tên loại giao dịch tương ứng
     */
    public static String getTypeString(int typeId) {
        switch (typeId) {
            case TYPE_DEPOSIT_CHECKING:
                return "Nạp tiền vãng lai";
            case TYPE_WITHDRAW_CHECKING:
                return "Rút tiền vãng lai";
            case TYPE_DEPOSIT_SAVINGS:
                return "Nạp tiền tiết kiệm";
            case TYPE_WITHDRAW_SAVINGS:
                return "Rút tiền tiết kiệm";
            default:
                return "Không rõ";
        }
    }

    public String getTransactionSummary() {
        logger.debug("Bắt đầu tạo tóm tắt cho giao dịch loại: {}", this.type);

        String typeDescription = getTypeString(type);
        String initialFormatted = String.format(DEFAULT_LOCALE, "%.2f", initialBalance);
        String amountFormatted = String.format(DEFAULT_LOCALE, "%.2f", amount);
        String finalFormatted = String.format(DEFAULT_LOCALE, "%.2f", finalBalance);
        String ansFormatted = "- Kiểu giao dịch: %s. Số dư ban đầu: $%s. Số tiền: $%s. Số dư cuối: $%s.";

        return String.format(ansFormatted,
            typeDescription, initialFormatted, amountFormatted, finalFormatted
        );
    }
}
