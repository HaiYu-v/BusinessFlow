#### ▮前言

业务繁琐而又复杂，业务之间耦合度极高。
一个看似简单的业务里，嵌套着十几个关键方法的调用。
更为致命的是：
1. 多人共同编写，各有风格，十分混乱
2. 业务代码没有完善的注释和接口文档，阅读起来很费劲
3. 业务嵌套，日志和异常输出混乱，排查问题只能步步调试
   
对此，想开发出一套完善的业务流框架，以达到如下需求：
1. 业务流以链条展现，将业务流切成一个个业务点。
2. 开发业务点时，规范注释和日志输出，业务流程更直观
3. 业务更加细分，代码解耦，提高复用

#### ▮效果展示

> 1. 责任链的链式调用 + 强制的注释

```java
@PostMapping("/test")
public String testController(){
    try {
        boolean isSuccess = BusinessFlow
                //整个业务流的描述
                .build("数电红字确认单申请开具")

                //add参数：方法提供bean（业务处理者），方法名（处理方法），描述，方法返回值key
                .add(VtChBillService.class,"converDto"
                        ,"Dto转销项开票明细","vtChBill")
                .add(VtChBillService.class,"converRedApply"
                        ,"数电红字确认单申请主表","redApply")
                .add(VtChBillService.class,"converRedApply_B"
                        ,"数电红字确认单申请明细","redApply_bList")
                .add(VtChBillService.class,"combine"
                        ,"数电红字确认单申请主子表合并","redApplyAsso")
                .add(AelecbillRedapplyService.class,"redBill_upLoad"
                        ,"上传红字确认单","isSuccess")

                //启动业务流，并输入此业务流的返回类型
                .start(Boolean.class);
        if(isSuccess){
            return "成功，红字确认单申请开具成功";
        }
        return "失败，失败原因是：...";
    } catch (BusinessFlowException e) {
        return "失败，异常是：...";
    }
}
```

当然，上面的看起来，还是太过于复杂，要输入的东西很多。或许，我们可以使用注解来简化这一过程

> 2. 方法加上自定义注解@Chain

```java
@Service
public class VtChBillService {

    @Chain(desc = "Dto转销项开票明细",retCode = "vtChBill")
    public void converDto() throws InterruptedException {
        Thread.sleep(300);
        System.out.println("converDto");
    }

    @Chain(desc = "数电红字确认单申请主表",retCode = "redApply")
    public void converRedApply() throws InterruptedException {
        Thread.sleep(300);
        System.out.println("converRedApply");

    }

    @Chain(desc = "数电红字确认单申请明细",retCode = "redApply_bList")
    public void converRedApply_B() throws InterruptedException {
        Thread.sleep(300);
        System.out.println("converRedApply_B");

    }

    @Chain(desc = "数电红字确认单申请主子表合并",retCode = "redApplyAsso")
    public void combine() throws InterruptedException {
        Thread.sleep(300);
        System.out.println("combine");

    }
}
```

> 3. 更简洁的链式调用

```java
    public String testController(){
        try {
            BusinessFlow flow = BusinessFlow
                    //整个业务流的描述
                    .build("数电红字确认单申请开具");
            businessFlow
                    //add参数：方法提供bean（业务处理者），方法名（处理方法），描述，方法返回值key
                    .add(VtChBillService.class,"converDto")
                    .add(VtChBillService.class,"converRedApply")
                    .add(VtChBillService.class,"converRedApply_B")
                    .add(VtChBillService.class,"combine")
                    .add(AelecbillRedapplyService.class,"redBill_upLoad")
                    //启动业务流，并输入此业务流的返回类型
                    .start();
            //日志打印
            System.out.println(businessFlow.visualJSON());
            return "成功，红字确认单申请开具成功";
        } catch (BusinessFlowException e) {
            e.printStackTrace();
            return "失败，异常是：...";
        }
    }
```

有了更简洁, 更直观的链式调用后, 就该有更加完善和完备的日志输出了

> 4. 日志输出

```json
{
    "desc": "数电红字确认单申请开具",
    "chainCount": 5,
    "startTime": "2024-11-26 16:24:11",
    "endTime": "2024-11-26 16:24:13",
    "runningTime": 1562,
    "ret": "null",
    "chainInfo": [
        {
            "parameters": [
            ],
            "ret": "null",
            "____________________________________________": "",
            "desc": "Dto转销项开票明细",
            "id": 1,
            "state": "SUCCESS",
            "startTime": "2024-11-26 16:24:11",
            "endTime": "2024-11-26 16:24:12",
            "runningTime": 309,
            "countExecute": 1
        },
        {
            "parameters": [
            ],
            "ret": "null",
            "____________________________________________": "",
            "desc": "数电红字确认单申请主表",
            "id": 2,
            "state": "SUCCESS",
            "startTime": "2024-11-26 16:24:12",
            "endTime": "2024-11-26 16:24:12",
            "runningTime": 310,
            "countExecute": 1
        },
        {
            "parameters": [
            ],
            "ret": "null",
            "____________________________________________": "",
            "desc": "数电红字确认单申请明细",
            "id": 3,
            "state": "SUCCESS",
            "startTime": "2024-11-26 16:24:12",
            "endTime": "2024-11-26 16:24:12",
            "runningTime": 312,
            "countExecute": 1
        },
        {
            "parameters": [
            ],
            "ret": "null",
            "____________________________________________": "",
            "desc": "数电红字确认单申请主子表合并",
            "id": 4,
            "state": "SUCCESS",
            "startTime": "2024-11-26 16:24:12",
            "endTime": "2024-11-26 16:24:13",
            "runningTime": 315,
            "countExecute": 1
        },
        {
            "parameters": [
            ],
            "ret": "null",
            "____________________________________________": "",
            "desc": "上传红字确认单",
            "id": 5,
            "state": "SUCCESS",
            "startTime": "2024-11-26 16:24:13",
            "endTime": "2024-11-26 16:24:13",
            "runningTime": 314,
            "countExecute": 1
        }
    ]
}
```

#### ▮仅此而已了吗?

有了一种更直观, 更易解的业务流后? 还能再开发出一些什么功能?

- 日志收集 -> 图表
日志是以标准的json格式打印的, 便于使用ELK等工具来收集日志, 转成类似如下的业务图
<img width="677" alt="{4D3E59FF-CAC9-4BCB-9657-B03AAD3203D3}" src="https://github.com/user-attachments/assets/0f9a7ed0-9ea3-44a0-ac1a-d4c5dc23eddb">


- 日志截断 -> 快速定位异常

```json
{
    "desc": "数电红字确认单申请开具",
    "chainCount": 2,
    "startTime": "2024-11-26 16:55:38",
    "endTime": "2024-11-26 16:55:38",
    "runningTime": 315,
    "ret": "null",
    "chainInfo": [
        {
            "parameters": [
            ],
            "ret": "null",
            "____________________________________________": "",
            "desc": "Dto转销项开票明细",
            "id": 1,
            "state": "SUCCESS",
            "startTime": "2024-11-26 16:55:38",
            "endTime": "2024-11-26 16:55:38",
            "runningTime": 309,
            "countExecute": 1
        },
        {
            "parameters": [
            ],
            "ret": "null",
            "____________________________________________": "",
            "desc": "数电红字确认单申请主表",
            "id": 2,
            "state": "EXCEPTION",//这个地方就直接截断了
            "startTime": "2024-11-26 16:55:38",
            "endTime": "2024-11-26 16:55:38",
            "runningTime": 5,
            "countExecute": 1
        }
    ]
}
```

- 代码收集和统计 -> 提高代码复用
业务流被切成一个个业务点,且每个业务点上面都有一个@Chain 标注。所以，我们是不是可以使用一些工具，比如Logstash来在项目里匹配, 并且捕获业务点的这段代码呢?
将捕获到的代码存入ES, 因为desc(注释)的存在而又更容易被搜索。
整个流程都可以使用程序自动匹配上传，不麻烦的同时，还提高了代码的复用率


#### ▮存在什么难点和问题？


- 方法之间如何传参？ ->  链式调用的拦路虎
把参数提前准备好不就行了？假如，这个业务点需要上个业务点的返回值作为参数，那该如何？
> 自动传参 + 自动类型转换

- 方法如何调用?  -> 责任链
直接new对象不就行了? 假如,这个方法里有个mapper, 怎么通过依赖注入来获取呢?
> 责任链 + 方法字符串

- 方法字符串错写 -> lambda表达式
通过 Object::function 来传入方法名,
> 失败, lambda没法传参,暂时不可行, 没想到其它好方法
