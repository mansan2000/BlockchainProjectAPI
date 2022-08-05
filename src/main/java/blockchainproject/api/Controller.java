package blockchainproject.api;

//import Blockchain.Block;
//import Blockchain.Chain;

import Blockchain.*;
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
    public Object test(@RequestParam String transaction) {
        Chain.updateFromLedger();
        NewBlock block = new NewBlock("Phillip","Emanuel", 10.0);
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