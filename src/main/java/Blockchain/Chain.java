package Blockchain;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
    private static final String ledgerFile = "src/main/resources/static/ledger.json";
    private Chain(){
    }
    public static void updateFromLedger(){
            try {
                Scanner inputFile = new Scanner(new File(ledgerFile));
                inputFile.nextLine();
                while (inputFile.hasNextLine()) {
                    String line = inputFile.nextLine();
                    if (!line.equals("]")) {

                        Scanner s = new Scanner(line);
                        String text = s.useDelimiter("\\A").next();
                        text = text.substring(0, text.length() - 1);
                        System.out.println("logger" + text);
                        JSONObject json = (JSONObject) new JSONParser().parse(text);
                        String previousHash = (String) json.get("Previous Hash");
                        String currentHash = (String) json.get("Current Hash");
                        String nonce = (String) json.get("Nonce");
                        Object transactionsString = json.get("Transactions");
//                    JSONObject transactions = (JSONObject) new JSONParser().parse(transactionsString);
                        Block b = new Block(previousHash, currentHash, (JSONObject) transactionsString, nonce, chain.size());
                        createBlockFromLedger(b);
                    } else {

                    }

                }

                inputFile.close();
            } catch (FileNotFoundException  ff) {
                System.out.println("\n\n\n\n\nException " + ff);
            } catch (ParseException e) {
                e.printStackTrace();
            }
    }
    public static void createBlockFromLedger(Block s) throws ParseException {


        try {

//            System.out.println(s.getBlockJSON());
            String previousHash = String.valueOf(s.getBlockJSON().get("Previous Hash"));
//            System.out.println(previousHash);
            String currentHash = (String) s.getBlockJSON().get("Current Hash");
            JSONObject transactionsString = (JSONObject) s.getBlockJSON().get("Transactions");
            String nonce = (String) s.getBlockJSON().get("Nonce");
                Block x = new Block(previousHash,currentHash,transactionsString,nonce, chain.size());
                Chain.tryToAddBlockToChain(x);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Exception   "+e);
        }
    }
    public static void writeToLedger(){
        try {
            PrintWriter ledger = new PrintWriter(ledgerFile);
//            ledger.println("PREVIOUS HASH, CURRENT HASH, SENDER,RECIPIENT,AMOUNT, NONCE");
//            System.out.println("logger");
            ledger.println("[");

            for (Block x : chain) {
//                System.out.println("nonce: "+x.getAmount());
//                        ledger.println(x.getPreviousHash()+","+x.getHash()+","+x.getSender()+","+x.getRecipient()+","+x.getAmount()+","+x.getNonce());
                ledger.println(x.getBlockJSON()+",");

            }
            ledger.println("]");
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
        System.out.println("preHash  "+  createHash(prospectiveBlock.getTransactions(), prospectiveBlock.getNonce(),prospectiveBlock.getPreviousHash()));
        System.out.println("thisHash  "+prospectiveBlock.getHash());

//        return false;
        return prospectiveBlock.getHash().equals(createHash(prospectiveBlock.getTransactions(), prospectiveBlock.getNonce(),preHash));
    }
    public static String createHash(JSONObject transactionsObject, String nonce,String previousHash) {
        String result = null;
        String transactions = transactionsObject.toJSONString();
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
//    public static String getPreviousTransactions(){
//        return chain.get(chain.size()-1).getTransactions();
//    }
    public static int getChainSize(){
        return chain.size()+1;
    }
}
