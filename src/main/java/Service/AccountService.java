package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private final AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    
    // Register a user 
    public Account registerUser(Account account) {
        if (account.getUsername() == null || account.getUsername().trim().isEmpty() ||
            account.getPassword() == null || account.getPassword().length() < 4 ||
            accountDAO.getUserByUsername(account.getUsername()) != null) {
                return null;
        }
        return accountDAO.registerUser(account);
    }

    // Login user
    public Account loginUser(Account account) {
        return accountDAO.authenticateUser(account.getUsername(), account.getPassword());
    }
}
