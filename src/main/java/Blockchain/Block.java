package Blockchain;

import org.json.simple.JSONObject;

public class Block {
    private String previousHash, hash, transactions, nonce, sender, recipient;
    private double amount;
    private int blockNumber;
    private Transaction transaction;
    public Block(String previousHash, String newHash,  Transaction transaction, String nonce, int blockNumber){
        this.previousHash = previousHash;
        this.nonce = nonce;
        this.hash = newHash;
        this.transaction = transaction;
        this.sender = transaction.getSender();
        this.recipient = transaction.getRecipient();
        this.amount = transaction.getAmount();
        this.blockNumber = blockNumber+1;

    }
    public Object getBlockJSON(){

//        Transaction transactionList = new Transaction(sender,recipient,amount);

        JSONObject block = new JSONObject();
        block.put("Previous Hash", this.previousHash);
        block.put("Current Hash", this.hash);
        block.put("Transactions",transaction.getTransactionJSON());
        block.put("Nonce", this.nonce);
        block.put("Block#", this.blockNumber);
        return block;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getTransactions() {
        return transactions;
    }

    public void setTransactions(String transactions) {
        this.transactions = transactions;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
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
