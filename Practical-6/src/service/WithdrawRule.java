package Project.src.service;




import Project.src.model.Account;

@FunctionalInterface
public interface WithdrawRule {
    boolean allow(Account account, long amount);
}
