package blockchainproject.api.Blockchain;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class NewBlock {
    private String hash;
    private String sender, recipient;
    private int nonce;
    private double amount;
    private final JSONObject transactions;
    public NewBlock(JSONObject transactions){
        Date date = new Date();
        String timeStamp = String.valueOf(date);
        this.amount = amount;
        this.nonce =0;
        this.hash="";
        this.transactions = transactions;


    }
    public void createHash() {
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String contents = Chain.getPreviousHash()+transactions.toJSONString()+this.nonce;
            byte[] hash = digest.digest(contents.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02X", b));
            }
            result = String.valueOf(sb).toLowerCase(Locale.ROOT);
        }catch (Exception e){
            System.out.println(e);
        }
        this.hash = result;
    }


    public void mine() throws ParseException {
        createHash();
        long startTime = System.currentTimeMillis();
        int counter = 1;
        long comparableValueOfHash = hash.hashCode();

//      Creates a BigInteger with a value of 2 so that it can be used with the compareTo method that requires BigInteger
        BigInteger baseTwo = new BigInteger("2");

//      Exponent that sets the difficulty of mining
        int exponent = 245;

//      Number value of hash so that it can be mathematically compared
        BigInteger hashValue = new BigInteger(hash,16);

//      A BigInteger with the value of 2^ exponent
        BigInteger powValue = baseTwo.pow(exponent);

//      rehashes hash increasing the nonce by one each time until the value of hash is less than powValue
        while (hashValue.compareTo(powValue)>=0){
            counter++;
            this.nonce++;
            createHash();
            hashValue = new BigInteger(hash,16);
            comparableValueOfHash = hash.hashCode();
        }

        Block x = new Block(Chain.getPreviousHash(),hash,transactions,String.valueOf(nonce),Chain.getChainSize());
        Chain.createBlockFromLedger((x));
        long endTime = System.currentTimeMillis();

        long time = endTime - startTime;
        double i = (double) time;
        System.out.println("That took " + (endTime - startTime) + " milliseconds"+"\nNumber of hashes tried: "+ nonce +"\ncalculations per second:   "+counter/(i/1000)+"\nMined hash:  "+hash);
    }
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}


