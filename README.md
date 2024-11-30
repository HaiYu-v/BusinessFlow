#### ▮前言

业务繁琐而又复杂，业务之间耦合度极高。

一个看似简单的业务里，嵌套着十几个关键方法的调用。

更为致命的是：

>1. 多人共同编写，各有风格，十分混乱
>2. 业务代码没有完善的注释和接口文档，阅读起来很费劲
>3. 业务嵌套，日志和异常输出混乱，排查问题只能步步调试

对此，想开发出一套完善的业务流框架，以达到如下需求：

> 1.业务流以链条展现，将业务流切成一个个业务点。
>
> 2.开发业务点时，规范注释和日志输出，业务流程更直观
>
> 3.业务更加细分，代码解耦，提高复用

如图所示：

> 1.链式调用 + 强制的注释

```java
@PostMapping("/test")
public String test(){
    try {
        BusinessFlow flow = BusinessFlow
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
        
        	//日志打印
            System.out.println(businessFlow.getVisualLog());
            return "成功，红字确认单申请开具成功";
    } catch (BusinessFlowException e) {
        //日志打印
        e.printStackTrace();
        System.out.println(businessFlow.getVisualLog());
        return "失败，异常是：...";
    }
}
```

当然，这样看起来，还是太过于复杂，要输入的东西很多，我们完全可以使用注解来简化这一过程

> 2.方法加上自定义注解`@Chain`

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

``` java
    public String test(){
        try {
            BusinessFlow flow = BusinessFlow
                    //整个业务流的描述
                    .build("数电红字确认单申请开具");
                    //add参数：方法提供bean（业务处理者），方法名（处理方法），描述，方法返回值key
                    .add(VtChBillService.class,"converDto")
                    .add(VtChBillService.class,"converRedApply")
                    .add(VtChBillService.class,"converRedApply_B")
                    .add(VtChBillService.class,"combine")
                    .add(AelecbillRedapplyService.class,"redBill_upLoad")
                    //启动业务流，并输入此业务流的返回类型
                    .start();
            //日志打印
            System.out.println(businessFlow.getVisualLog());
            return "成功，红字确认单申请开具成功";
        } catch (BusinessFlowException e) {
            e.printStackTrace();
            System.out.println(businessFlow.getVisualLog());
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
    "startTime": "2024-11-29 15:43:30",
    "endTime": "2024-11-29 15:43:31",
    "runningTime": 1563,
    "ret": "null",
    "chainInfo": [
        {
            "parameters": [
            ],
            "ret": "java.lang.Integer",
            "____________________________________________": "",
            "desc": "Dto转销项开票明细",
            "id": 1,
            "state": "SUCCESS",
            "startTime": "2024-11-29 15:43:30",
            "endTime": "2024-11-29 15:43:30",
            "runningTime": 313,
            "countExecute": 1
        },
        {
            "parameters": [
                "int vtChBill"
            ],
            "ret": "java.lang.Integer",
            "____________________________________________": "",
            "desc": "数电红字确认单申请主表",
            "id": 2,
            "state": "SUCCESS",
            "startTime": "2024-11-29 15:43:30",
            "endTime": "2024-11-29 15:43:30",
            "runningTime": 308,
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
            "startTime": "2024-11-29 15:43:30",
            "endTime": "2024-11-29 15:43:31",
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
            "startTime": "2024-11-29 15:43:31",
            "endTime": "2024-11-29 15:43:31",
            "runningTime": 313,
            "countExecute": 1
        },
        {
            "parameters": [
            ],
            "ret": "null",
            "____________________________________________": "",
            "desc": "Dto转销项开票明细",
            "id": 5,
            "state": "SUCCESS",
            "startTime": "2024-11-29 15:43:31",
            "endTime": "2024-11-29 15:43:31",
            "runningTime": 312,
            "countExecute": 1
        }
    ]
}
```





#### ▮BusinessFlow——业务流

  | 核心属性            | 描述                                 |
  | -------- | ------------------------------------ |
  | desc | 业务流描述，理解成对业务流的注释就行 |
  | context             | bean容器，非Spring框架也能使用       |
  | globalCache    | 全局数据缓存，数据在整个业务流里都存在  |
  | temporaryCache | 临时数据缓存，数据仅传入到下一业务点 |
  | ruleCache           | 规则缓存，储存数据转换所依据的规则   |
  | chainQueue | 业务点队列，存储业务点 |
  
  | 核心API | 方法 |
  | ---- | ---- |
  | 创建一个业务流 | build(String desc) |
  | 添加业务点 | add(Object bean, String methodCode, String desc, String retCode) |
  | 启动业务流 | start(Class<T> retType) |
  | 获得业务流日志 | getInfoLog() |
  | 获得可视化日志 | getVisualLog() |

一个业务流是由一个个的业务点串联而成，每个`业务点本质上都是一个方法`。业务流使用的设计模式是`责任链`, 它最主要的作用是`规范和优化业务方法的调用`。对业务方法进行解耦, 提供代码的可读性。

`BusinessFlow`是业务流实体类，使用`build`方法能创建一个业务流对象，此方法需要传入一个对业务流的描述desc。

> 对于`desc`，它的作用就是`强制性的注释，用于日志打印`。




#### ▮ChainDesc-业务点
  | 核心属性 | 描述 |
  |--------|------|
  | bean   | 有对象才能去执行方法 |
  | method | 业务点的本质就是一个方法 |
  | desc   | 业务点描述，理解成对业务点的注释就行                         |
  | retCode| 方法返回值的key（存入缓存时的key，使后续业务点能获取到此数据） |

  有了业务流对象后，就需要添加业务点`ChainDesc`了。 一个`ChainDesc`需要提供四个属性: 对象, 对象的public方法, desc, 返回值retCode。

  在`businessFlow.start()`后，就会顺序的去启动每个ChainDesc里的方法。方法所需的参数是可以自动传入的，方法的返回值也会以`retCode`为Key，然后存入临时缓存`temporaryValueCache`

  ```java
  businessFlow.add(VtChBillService.class,"converDto"
                        ,"Dto转销项开票明细","vtChBill")

> 支持匿名类 ，不需要传入实体bean

  ```java
  businessFlow.add("匿名类测试", "ret", new IChain() {
      @Override
      public Object method(TemporaryValueCache temporaryCache, GlobalValueCache globalCache) {
          return "匿名类执行";
      }
  })
  ```




#### ▮@Chain——业务点注解

  | 参数名  | 描述                               | 备注 |
  | ------- | ---------------------------------- | ---- |
  | desc    | 业务点描述                         | 必传 |
  | retCode | 返回值的缓存Key                    | 必传 |
  | code    | 方法的code，为空时则会按方法名匹配 | 可选 |

  有了注解后，`businessFlow.add()`就不需要我们传入desc和code了，如果你传入了，就会覆盖掉注解的信息

  如果出现同名方法，就需要使用code来区分了，不然会直接取第一个方法。
  ```java
@Chain(desc = "Dto转销项开票明细",retCode = "vtChBill")
public void converDto() throws InterruptedException {
    Thread.sleep(300);
    System.out.println("converDto");
}
```


#### ▮业务点方法传参
  | 核心            | 描述                                 |
  | -------- | ------------------------------------ |
  | sourceCode |  规则或数据缓存里的key|
  | @Source | 指定key |
  
  有三种传参类型
  
  一、直接传参，相当于x = y
  
  二、缓存传入，`GlobalValueCache`和`TemporaryValueCache`会自动传入此类型的参数
  
  三、规则转换，根据一定规则转换成新的数据
  
  
  
#### ▮直接传参

  ```java
  @Chain(desc = "提供test1",retCode = "test1")
  public  Test1 start(){
      return new Test1(1,"xiaoming",1.0);
  }
  
  @Chain(desc = "接收test1",retCode = "test2")
  public Test1 conver(Test1 test1) throws InterruptedException {
      return test1;
  }
  ```

  start（）的返回值会以“test1”为key存入临时缓存，然后在传给conver（）里的test1。参数test1的sourceCode就是参数名“test1”

  ```java
  @Chain(desc = "转换为test2",retCode = "test2",code = "converTest2")
  public Test1 conver2(@Source("test1") Test1 test2) throws InterruptedException {
      return test2;
  }
  ```

  也可以使用`@Source`显示指定sourceCode，来自动传入对应的值



#### ▮@Source注解——业务点方法参数的注解

  一、显示指定参数的sourceCode
  ```java
@Chain(desc = "转换为test2",retCode = "test2",code = "converTest2")
public Test1 conver2(@Source("test1") Test1 test2) throws InterruptedException {
    return test2;
}
```
​      使用`@Source`显示指定sourceCode，来自动传入对应的值

​	  二、从全局缓存`globalCache`里读值

​     使用`@Source`不传值或者传值。都会优先的从全局缓存里取寻找数据，如果没找到才会取临时缓存里找值。



#### ▮缓存传入

  业务流里的`temporaryCache`和`globalValueCache`会自动传入参数，如果方法里有这两个之一

  ```java
  public Object method(TemporaryCache temporaryCache, GlobalCache globalCache) {
      return "null";
  }
```

#### ▮规则转换

  ```json
  {
      "sourceCode":"sourceCode",
      "rules":{
          "file1":{"type":"常量","formula":"1"},
          "file2":{"type":"映射","source":["sourceCode"]},
          "file3":{"type":"映射","source":["sourceCode.file"]},
          "file4":{"type":"公式","source":["sourceCode.file","x"],"formula":"#bean.file + #x"}
      }
  }
```
这就是一个简单的转换规则JSON，能够被默认的解析器解析，并进行转换。支持映射，常量，公式三种转换方式。其中转换使用的是SpEL，想了解的可以去学学

`rules`上面的`sourceCode`就是方法传参时，参数匹配用的`sourceCode`。参数在传参的时候，会先去匹配规则转换，如果没有才会走`直接传参`
