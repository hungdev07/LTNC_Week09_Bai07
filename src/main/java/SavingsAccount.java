import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tai khoan tiet kiem - Lop nay thuc thi cac quy dinh ve rut tien và nap tien.
 */
public class SavingsAccount extends Account {
    private static final Logger logger = LoggerFactory.getLogger(SavingsAccount.class);
    private static final double MAX_WITHDRAW = 1000.0;
    private static final double MIN_BALANCE = 5000.0;
    /**
     * Khởi tạo tài khoản tiết kiệm
     * @param accountNumber số tài khoản
     * @param balance số dư ban đầu
     */
    public SavingsAccount(long accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void deposit(double amount) {
        logger.info("Đang xử lý giao dịch nạp tiền cho tài khoản: {}", getAccountNumber());
        double initialBalance = getBalance();
        try {
            doDepositing(amount);
            double finalBalance = getBalance();

            Transaction t = new Transaction(
                Transaction.TYPE_DEPOSIT_SAVINGS,
                amount,
                initialBalance,
                finalBalance
            );
            addTransaction(t);
            System.out.println("Nap tien vao tai khoan " + getAccountNumber() + " thanh cong: +" + amount);
            logger.info("Nạp tiền thành công. STK: {}, Số tiền: +{}", getAccountNumber(), amount);
        } catch (BankException e) { // Vi phạm: Catch Exception chung chung
            logger.error("Lỗi nạp tiền vào tài khoản: {}: {}", getAccountNumber(), e.getMessage());
        }
    }

    @Override
    public void withdraw(double amount) {
        double initialBalance = getBalance();
        try {
            if (amount > MAX_WITHDRAW) {
                throw new InvalidFundingAmountException(amount);
            }
            if (initialBalance - amount < MIN_BALANCE) {
                throw new InsufficientFundsException(amount);
            }
            
            doWithdrawing(amount);
            double finalBalance = getBalance();

            Transaction t = new Transaction(
                Transaction.TYPE_WITHDRAW_SAVINGS,
                amount,
                initialBalance,
                finalBalance
            );
            addTransaction(t);
            
            logger.info("[SAVINGS] Rút tiền thành công. Số tiền: {}, số dư mới {}", amount, finalBalance);
        } catch (BankException e) {
            logger.warn("Giao dịch rút tiền từ tài khoản {} bị từ chối: {}",
                getAccountNumber(), e.getMessage());
        }
    }
}