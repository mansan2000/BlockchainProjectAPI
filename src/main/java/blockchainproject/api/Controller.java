package blockchainproject.api;

//import Blockchain.Block;
//import Blockchain.Chain;

//import Blockchain.*;

import blockchainproject.api.Blockchain.Block;
import blockchainproject.api.Blockchain.Chain;
import blockchainproject.api.Blockchain.NewBlock;
import blockchainproject.api.Blockchain.Transaction;
import com.google.gson.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

@RestController

public class Controller {
    @RequestMapping
    public String helloWorld() {
        return "Hello World from Spring Boot";
    }
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:3000")
    public Object test() throws ParseException {
        Chain.updateFromLedger();
        JSONObject blockT = new JSONObject();
        blockT.put("Transaction 1", new Transaction("Phillip", "Emanuel",20.0).getTransactionJSON());
        blockT.put("Transaction 2", new Transaction("Phillip", "Emanuel",25.0).getTransactionJSON());
        blockT.put("Transaction 3", new Transaction("Ethan", "Gibbie",25.0).getTransactionJSON());
        blockT.put("Transaction 4", new Transaction("Ethan", "Gibbie",25.0).getTransactionJSON());

        NewBlock block = new NewBlock(blockT);
//        JSONObject obj = new JSONObject();
////        block.createHash();
        block.mine();
//        obj.put("hash",block.getHash());
//        obj.put("Previous Transactions",Chain.getPreviousTransactions());
        return Chain.getChainJSON();
    }

    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:3000")
    public int blockTwo(@RequestParam String name) throws ParseException {

        Chain.updateFromLedger();
        int total = 0;
        for (int i = 0; i < Chain.getChainSize()-1; i++) {
            Block x = Chain.getBlock(i);
            JsonElement jelement = new JsonParser().parse((String.valueOf(x.getBlockJSON())));
            JsonObject  jobject = jelement.getAsJsonObject();
            jobject = jobject.getAsJsonObject("Transactions");
            for (int j = 0; j < 4; j++) {
                JsonObject jarray = jobject.getAsJsonObject("Transaction "+String.valueOf(j+1));
                            try {
                if (jarray.get("To").getAsString().equals(name)) {
                    total += (double) jarray.get("Amount $").getAsDouble();
                }
            } catch (Exception ignored) {
            }
                System.out.println(jarray);
            }
//            jobject = jarray.get(0).getAsJsonObject();
//            JsonElement jelement = new JsonParser().parse(String.valueOf(x.getBlockJSON()));
//            JsonObject jobject = jelement.getAsJsonObject();
////            JsonArray ra = jobject.getAsJsonArray("Transactions");
//            jobject = jobject.getAsJsonObject("Transactions");
//            System.out.println(jobject);
//            JsonArray jarray = jobject.getAsJsonArray("Transaction");
//            jobject = jarray.get(0).getAsJsonObject();
//            System.out.println(jobject);
//            j = ra.get(0).getAsJsonObject();
//            System.out.println(j);
//            JsonArray ra = j.get(0).getAsJsonArray();
//            JsonArray jarray = j.getAsJsonArray("Transacions");
//            jobject = jarray.get(0).getAsJsonObject();

//            System.out.println(gson.toJson(x.getBlockJSON()));



//            JSONObject Transactions = (JSONObject) x.getBlockJSON().get("Transactions");
//            System.out.println(Transactions.toJSONString());
//
//            JSONObject Transaction = (JSONObject) Transactions.get("Transactions");
//            try {
//                if (Transaction.get("To").equals(name)) {
//                    total += (double) Transaction.get("Amount $");
//                }
//            } catch (Exception ignored) {
//            }

        }
        return total;
    }

}