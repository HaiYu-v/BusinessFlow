package org.example.designs.chain;

import java.util.Map;

/**
 * @Program: 8.mindmap
 * @Author: 海里的鱼
 * @Create: 2024-09-10 09:20
 * @Name: 链路处理的策略
 * @Desc: TODO
 */
public interface IChainNode {
    Map<String,Object> process(Map<String,Object> request, Map<String,Object> response);
}
