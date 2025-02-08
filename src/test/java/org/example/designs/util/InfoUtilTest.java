package org.example.designs.util;

import org.example.designs.Info.InfoCache;
import org.example.designs.Info.InfoUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2025-02-08
 */
class LogTest{
    private String name = "test1";
    private int[] arr = new int[]{1,2,3,4,5};
    private List<Integer> list = List.of(1,2,3,4,5);
    private Map<String,String> map = Map.of("key1","value1","key2","value2");

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getArr() {
        return arr;
    }

    public void setArr(int[] arr) {
        this.arr = arr;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
@SpringBootTest
public class InfoUtilTest {

    @Test
    public void test() throws InterruptedException {
        LogTest logTest = new LogTest();
        Map<String,Object> map = Map.of("key1","value1","key2","value2");
        System.out.println(InfoUtil.start("test"));
        Thread.sleep(200);
        System.out.println(InfoUtil.end("test"));
        System.out.println(InfoUtil.count("test"));
        System.out.println(InfoUtil.putInfo("test", "key", "value"));
        System.out.println(InfoUtil.putInfo("test",map));
        System.out.println(InfoUtil.putInfo("test",logTest));
        System.out.println(InfoUtil.infoJson("test"));
        System.out.println(InfoUtil.infoPrettyStr("test"));
        System.out.println(InfoUtil.getDesc("test"));
        System.out.println(InfoUtil.getCount("test"));
        System.out.println(InfoUtil.getStartTimestamp("test"));
        System.out.println(InfoUtil.getRunningTime("test"));
        System.out.println(InfoUtil.getStart("test"));
        System.out.println(InfoUtil.getStartDateTime("test"));
        System.out.println(InfoUtil.getStr("test","key"));
        System.out.println();
        System.out.println();
        InfoCache test2 = InfoUtil.build("test2");
        System.out.println(test2.start());
        Thread.sleep(200);
        System.out.println(test2.end());
        System.out.println(test2.count());
        System.out.println(test2.putInfo( "key", "value"));
        System.out.println(test2.putInfo(map));
        System.out.println(test2.putInfo(logTest));
        System.out.println(test2.infoJson());
        System.out.println(test2.infoPrettyStr());
        System.out.println(test2.getDesc());
        System.out.println(test2.getCount());
        System.out.println(test2.getStartTimestamp());
        System.out.println(test2.getRunningTime());
        System.out.println(test2.getStart());
        System.out.println(test2.getStartDateTime());
        System.out.println(test2.getStr("key"));
    }
}
