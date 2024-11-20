package org.example.designs;

import org.example.designs.chain.ChainHandler;
import org.example.designs.chain.IChainNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Program: 11.designs
 * @Author: 海里的鱼
 * @Create: 2024-11-12 18:28
 * @Name: TODO
 * @Desc: TODO
 */
class  Print {


    public String printInfo(){
        return "打印";
    }

    public Object process(String request, String response) {
        return null;
    }



}

public class ChainTest {
    public static void main(String[] args) throws Exception {
        Print print = new Print();
        ChainHandler handler = new ChainHandler();
    }
}
