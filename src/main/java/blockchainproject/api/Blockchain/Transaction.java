package blockchainproject.api.Blockchain;


import org.json.simple.JSONObject;

public class Transaction {
private String sender, recipient;
private double amount;

    public Transaction(String sender,String recipient,double amount){
        this.sender = sender;
        this.amount = amount;
        this.recipient = recipient;
    }

    public JSONObject getTransactionJSON(){
        JSONObject transactions = new JSONObject();
        transactions.put("From", this.sender);
        transactions.put("To", this.recipient);
        transactions.put("Amount $", this.amount);
        return transactions;
    }
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
