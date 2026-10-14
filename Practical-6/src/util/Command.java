package Project.src.util;




import Project.src.model.TransactionType;

public class Command {
    private TransactionType type;
    private long amount;

    public Command(TransactionType type, long amount) {
        this.type = type;
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }
}

