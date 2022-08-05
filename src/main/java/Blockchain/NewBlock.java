package Blockchain;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class NewBlock {
//    private String previousHash;
    private String hash;
    private String sender, recipient;
    private long comparableValueOfHash;
    private int nonce;
    private double amount;
    private JSONObject transactions;
    public NewBlock(JSONObject transactions){
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String timeStamp = String.valueOf(date);
        this.amount = amount;
        this.recipient = recipient;
        this.sender = sender;
//        this.previousHash=previousHash;
        this.nonce =0;
        this.hash="";
        this.transactions = transactions;


    }
    public void createHash() {
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            System.out.println("mine stuff: "+this.previousHash+" "+this.data+" "+this.nonce);
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
//        return result;
    }


    public void mine() throws ParseException {
        createHash();
        long startTime = System.currentTimeMillis();
        int counter = 1;
        comparableValueOfHash = hash.hashCode();

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
//            System.out.println(hash);
        }

//        Block y = new Block("this","that","thaitah",1);
//        Chain.addBlock(y);
//        System.out.println("hash in mine: "+Chain.getPreviousHash());
//        System.out.println(transaction);
        Block x = new Block(Chain.getPreviousHash(),hash,transactions,String.valueOf(nonce),Chain.getChainSize());
//        System.out.println(x.getBlockJSON());
        String line = x.getPreviousHash()+","+x.getHash()+","+x.getSender()+","+x.getRecipient()+","+x.getAmount()+","+x.getNonce();
//        String line = ""{"Block 0":{"Nonce":"3095","Transactions":{"From":"Phillip","To":"Emanuel","Amount $":10.0},"Block#":1,"Previous Hash":"Genesis Block hash","Current Hash":"00042096a4aae945c84d7062f4033c549aedc6559dcd7c14fdb9bf0160e85964"}}";
        ;
//            System.out.println(line);
//        System.out.println(line);
        Scanner s = new Scanner(line);
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


