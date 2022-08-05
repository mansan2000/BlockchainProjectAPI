package Blockchain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public final class Chain {
    private static ArrayList<Block> chain = new ArrayList<>();
    private static final String ledgerFile = "src/main/resources/static/ledgerCopy.csv";
    private Chain(){
    }
    public static void updateFromLedger(){
            try {
                Scanner inputFile = new Scanner(new File(ledgerFile));
                inputFile.nextLine();
                while (inputFile.hasNextLine()) {

                    Scanner s = new Scanner(inputFile.nextLine());
                    createBlockFromLedger(s);

                }

                inputFile.close();
            } catch (FileNotFoundException ff) {
                System.out.println("\n\n\n\n\nException " + ff);
            }
    }
    public static void createBlockFromLedger(Scanner s){
        s.useDelimiter(",");

        try {

            String previousHash = s.next();
            String currentHash = s.next();
            Transaction transaction = new Transaction(s.next(),s.next(),Double.parseDouble(s.next()));
//            System.out.println("o"+transaction.getTransactionJSON());
            String nonce = s.next();
                Block x = new Block(previousHash,currentHash,transaction,nonce, chain.size());
                Chain.tryToAddBlockToChain(x);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Exception   "+e);
        }
    }
    public static void writeToLedger(){
        try {
            PrintWriter ledger = new PrintWriter(ledgerFile);
            ledger.println("PREVIOUS HASH, CURRENT HASH, SENDER,RECIPIENT,AMOUNT, NONCE");
            for (Block x : chain) {
//                System.out.println("nonce: "+x.getAmount());
                ledger.println(x.getPreviousHash()+","+x.getHash()+","+x.getSender()+","+x.getRecipient()+","+x.getAmount()+","+x.getNonce());

            }
            ledger.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public static void tryToAddBlockToChain(Block x){
        if(chain.size()<1){
            chain.add(x);
            writeToLedger();

        }
//        chain.add(x);
        else if (validateMinedBlock(x)) {
            chain.add(x);
            writeToLedger();

        }
        else {
            System.out.println("Not a valid Block");
        }
    }
    public static boolean validateMinedBlock(Block prospectiveBlock){
        String preHash;
        if (chain.size()<1){
            preHash = "Genesis Block hash";
        }else {
//            System.out.println("I: "+chain.indexOf(prospectiveBlock));
            preHash = chain.get(chain.size()-1).getHash();

        }
//        System.out.println(preHash);
//        System.out.println("preHash  "+  createHash(prospectiveBlock.getTransactions(), prospectiveBlock.getNonce(),prospectiveBlock.getPreviousHash()));
//        System.out.println("thisHash  "+prospectiveBlock.getHash());

        return prospectiveBlock.getHash().equals(createHash(String.valueOf(prospectiveBlock.getTransaction().getTransactionJSON()), prospectiveBlock.getNonce(),preHash));
    }
    public static String createHash(String transactions, String nonce,String previousHash) {
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String contents = previousHash+transactions+nonce;
            byte[] hash = digest.digest(contents.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02X", b));
            }
            result = String.valueOf(sb).toLowerCase(Locale.ROOT);
        }catch (Exception e){
            System.out.println(e);
        }
        return result;
    }

    public ArrayList<Block> getChain() {
        return chain;
    }
    public static String getChainJSON(){
        String chainJSON = "[";
        for (Block b : chain){
            chainJSON += b.getBlockJSON()+",";
        }
        return chainJSON+"]";
    }

    public void setChain(ArrayList<Block> chain) {
        Chain.chain = chain;
    }
    public static String getPreviousHash(){
        if (chain.size()<1){
            return "Genesis Block hash";
        }
        else {
        return chain.get(chain.size()-1).getHash();
        }
    }
    public static String getPreviousTransactions(){
        return chain.get(chain.size()-1).getTransactions();
    }
    public static int getChainSize(){
        return chain.size()+1;
    }
}
