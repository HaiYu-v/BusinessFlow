package org.example.designs.state_machine;

/**
 * 获取打卡表格
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-19
 */
public class DateIntended {
    private static String HEAD =
            "|    一    |    二    |    三    |    四    |    五    |    六    |    七    |\n" +
            "| :-----: | :-----: | :-----: | :-----: | :-----: | :-----: | :-----: |\n|";

    public static void main(String[] args) {

        System.out.println(getTable(20,6000));
    }

    private static String getVoid(){
        return new StringBuilder("         |").toString();
    }

    private static String getCell(int left,int right){
        return new StringBuilder("   ").append(left).append("~").append(right).append("   |").toString();
    }

    private static String getTable(int start,int day,int dayCount){
        start -= 1;
        StringBuilder dateTable = new StringBuilder(HEAD);

        int count = dayCount/day + (dayCount%day > 0? 0:1);
        if(count %7 > 0){
            count+= 7 - count%7;
        }
        for (int i = 0; i < count; i++) {

//            dateTable.append("|");
            if(start>=dayCount){
                dateTable.append(getVoid());
            }else {
                dateTable.append(getCell(start+1,( (start+=day)>dayCount ? dayCount : start )));
            }
            if((i+1)%7 ==0){
                dateTable.append("\n");
                dateTable.append("|         |         |         |         |         |         |         |\n|");
            }
        }
        dateTable.delete(dateTable.length()-1,dateTable.length());
        return dateTable.toString();
    }

    private static String getTable(int day,int dayCount){
        return getTable(1,day,dayCount);
    }



}
