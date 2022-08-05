package blockchainproject.api;

//import Blockchain.Block;
//import Blockchain.Chain;

import Blockchain.*;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;
import org.json.simple.JSONObject;

@RestController

public class Controller {
    @RequestMapping
    public String helloWorld() {
        return "Hello World from Spring Boot";
    }
    @RequestMapping("/test")
    @CrossOrigin(origins = "http://localhost:3000")
    public Object test(@RequestParam String transaction) throws ParseException {
        Chain.updateFromLedger();
        JSONObject blockT = new JSONObject();
        blockT.put("Transaction 1", new Transaction("Phillip", "Emanuel",20.0).getTransactionJSON());
        blockT.put("Transaction 2", new Transaction("Phillip", "Emanuel",25.0).getTransactionJSON());

        NewBlock block = new NewBlock(blockT);
//        JSONObject obj = new JSONObject();
////        block.createHash();
        block.mine();
//        obj.put("hash",block.getHash());
//        obj.put("Previous Transactions",Chain.getPreviousTransactions());
        return Chain.getChainJSON();
    }
//    @GetMapping("/block")
//    @CrossOrigin(origins = "http://localhost:3000")
//    public Object block() {
////        Block block1 = new Block("this","this","this","this");
////        JSONObject obj = new JSONObject();
////        obj.put("hash", block1.getHash());
////        System.out.println(obj.toJSONString());
////
////        return obj;
//
//    }

}