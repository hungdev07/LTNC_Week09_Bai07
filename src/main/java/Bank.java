import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Quản lý danh sách khách hàng và nghiệp vụ liên quan
 */

public class Bank {
  private static final Logger logger = LoggerFactory.getLogger(Bank.class);
  private List<Customer> customerList;
  private static final String ID_REGREX = "\\d{9}";
  private static final String WHITESPACE_REGREX = "\\s+";

  public Bank() {
    this.customerList = new ArrayList<Customer>();
  }

  public List<Customer> getCustomerList() {
    return customerList;
  }

  /**
   * Gán danh sách khách hàng
   * @param customerList danh sách khách hàng mới
   */
  public void setCustomerList(List<Customer> customerList) {
    if (customerList == null) {
      this.customerList = new ArrayList<Customer>();
    } else {
      this.customerList = customerList;
    }
  }

  /**
   * Đọc danh sách khách từ luồng đầu vào
   * @param inputStream luồng dữ liệu đầu vào
   */
  public void readCustomerList(InputStream inputStream) {
    if (inputStream == null) {
      logger.warn("InputStream đầu vào bị null");
      return;
    }

    logger.info("Bắt đầu đọc dữ liệu khách hàng");

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      Customer current = null;
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (line.isEmpty()) {
          continue;
        }
        current = parseLine(line, current);
      }
    } catch (IOException e) {
      logger.error("Lỗi khi đọc dữ liệu từ đầu vào: {}", e.getMessage(), e);
    }
  }

  /**
   * Xử lý từng dòng để tạo Customer hoặc Account
   */
  private Customer parseLine(String line, Customer current) {
    int lastSpace = line.lastIndexOf(' ');
    if (lastSpace <= 0) {
      return current;
    }

    String lastToken = line.substring(lastSpace + 1).trim();

    // Nếu token cuối là CMND (9 chữ số) -> Tạo khách hàng mới
    if (lastToken.matches(ID_REGREX)) {
      String name = line.substring(0, lastSpace).trim();
      Customer newCustomer = new Customer(Long.parseLong(lastToken), name);
      customerList.add(new Customer());
      logger.debug("Đã thêm khách hàng: {}", name); // Dùng debug thay vì info. Vì info dùng cho các công việc lớn, thay đổi lớn
      return newCustomer;
    }

    // Nếu không phải CMND và đã có khách hàng hiện tại -> Thêm tài khoản
    if (current != null) {
      addAccountToCustomer(line, current);
    }
    return current;
  }

  private void addAccountToCustomer(String line, Customer customer) {
    String[] parts = line.split(WHITESPACE_REGREX);
    if (parts.length >= 3) {
      long accNum = Long.parseLong(parts[0]);
      String type = parts[1];
      double balance = Double.parseDouble(parts[2]);

      if (Account.CHECKING_TYPE.equals(type)) {
        customer.addAccount(new CheckingAccount(accNum, balance));
      } else if (Account.SAVINGS_TYPE.equals(type)) {
        customer.addAccount(new SavingsAccount(accNum, balance));
      }
    }
  }

  public String getCustomerInfo() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < customerList.size(); i++) {
      String currentCustomerInfo = customerList.get(i).getCustomerInfo();
      sb.append(currentCustomerInfo);
      if (i < customerList.size() - 1) {
        sb.append("\n");
      }
    }
    return sb.toString();
  }

  public String getCustomersInfoByIdOrder() {
    customerList.sort(Comparator.comparingLong(Customer::getIdNumber));

    return getCustomerInfo();
  }

  /**
   * Lấy thông tin khách hàng sắp xếp theo tên, sau đó theo CMND
   *
   */
  public String getCustomersInfoByNameOrder() {
    customerList.sort(Comparator.comparing(Customer::getFullName)
        .thenComparingLong(Customer::getIdNumber));

    return getCustomerInfo();
  }
}