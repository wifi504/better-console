# Better Console Document

> “更好的控制台” —— 使用说明文档

## 一、开 始 🎉

### 1.1 介 绍

####  🔔 前 言

控制台一般指的是一个程序与使用者交互的工具，它还有一个名字叫做“终端”，普遍是以纯文本流输入输出的形式进行工作。通常情况下，一个程序要使用控制台与用户交互信息，都是“你输入一行，我打(print)一行”，使用体验极其糟糕！作为程序开发者，如果想展示的数据过于复杂、动态、结构化，光使用控制台的解决方案只会徒增工作量，费力不讨好，但是对于小型的工具类程序、测试程序、以及一些新手开发者来说，专门为此开发图形用户界面并不是一个低成本且轻而易举的选择，回想一下，你有多少有趣的小作品因为人机交互不够优雅而胎死腹中了呢，现在，你可以有一个新的选择！

**正如我的名字所述：“Better Console”，直译为“更好的控制台”，简称为 BConsole。**

BConsole 是一款用于**在控制台中构建图形用户界面**的框架。它**完全基于 Java SE 构建**，并提供了一套**声明式的、组件化的编程模型**，帮助你高效地开发用户界面。无论是简单还是复杂的界面，BConsole 都可以胜任，丢掉你的 `System.out.println("");` 吧！

#### ✨ 特 色

- **BConsole 没有使用任何第三方依赖**，所有功能完全内置实现，开箱即用！
- **上手几乎没有任何门槛**，甚至如果你正是一位刚刚开始学习 Java SE 的新手，这个框架有助于逐渐提升你对整套 Java SE 的理解！
- **声明式编程范式**：它关注于描述 “做什么” 而非 “怎么做” 。在声明式编程模型中，你表述程序的逻辑，而不必详细说明其控制流和状态管理！
- **组件化编程范式**：它强调将软件系统分解为独立的、可复用的模块（即组件）。每个组件封装了特定的功能或逻辑，具有明确的接口和依赖关系，可以在不同的上下文中独立使用和组合！
- **渐进式框架**：它允许开发者按需采用框架的各个特性，从而逐步增加应用程序的复杂性和功能。BConsole 可以在你几乎不需要修改项目源代码的情况下，直接给项目中需要展示的任何内容按你所想显示到控制台中，可拓展、灵活、易用、模块化、适应性强！

> 说到这里，相信你已经想起了一位故人：Vue，不认识它也没什么关系，没错，本框架的开发大量学习了 Vue 的思想

### 1.2 快速上手 👨‍💻

#### 前置条件

1. 只要你会使用 Java 打印 Hello World，那么应该就可以玩儿转本框架去显示各种有意思的内容
2. 你的电脑上正确安装了 JDK（推荐版本 17），以及对应集成开发环境（推荐IntelliJ IDEA）

#### 引入 BConsole 

> 方式多种多样，选择最适合你的一种即可~

- IDEA 手动导入 Jar 包依赖
  - 步骤……（还没写）
- 使用 maven 引入
  - 步骤……（还没写）
- 直接下载源代码将对应类复制到自己的项目目录下
  - 步骤……（还没写）

#### 创建第一个基于 BConsole 驱动的小程序

在本节中，我们来试着在自己电脑上把黑不溜秋的控制台变成一个酷炫的屏幕时钟吧

> **</>** 代码示例

```java
public class Test {
    public static void main(String[] args) throws InterruptedException {
        // 启用控制台显示屏，并显示一个数字时钟
        BConsole.getScreen().reg(PresetViews.getClockView()).turnON();
        // 等待一分钟后
        Thread.sleep(60 * 1000);
        // 关闭控制台显示屏，退出程序
        BConsole.getScreen().turnOFF();
    }
}
```

🟢 让我们运行它！

![](images/1.2.1.gif)

**（这里放图，还没写）**

什么？控制台为什么在循环打印奇奇怪怪的东西？这是正确的吗？

原来，我们能在控制台上看到的文本内容都是程序“流式输出”的，你现在不懂也无妨，你只需要知道这些内容一旦被打印，他就会被永远记录在控制台上，一般情况下会直到这个终端会话结束。

> KNOWLEDGE CARD
>
> **流式输出**（Stream Output）
>
> 一种计算机输出数据的方式，数据被连续发送至输出设备（如控制台、文件、网络等），而不需要等待所有数据都准备好才进行输出。这种方式特别适用于数据量大或者实时性要求高的场景，如实时日志系统、视频流处理或实时数据传输。
>
> 知识点来自：Java I/O 流 `package java.io`

对于简单的文本输出，这可能不是问题，但如果我们希望实现更动态的效果，比如一个实时更新的时钟，单纯的流式输出就可能导致输出内容杂乱无章。因此，我们需要一种方式来刷新已有的输出，以保持屏幕内容的整洁和组织性。

这正是为什么在开发类似应用时，我们常常需要在命令行界面（CMD）中运行程序，因为它允许我们利用特定的命令来清除或更新控制台的输出内容。例如，我们可以使用 `cls` 命令在Windows的CMD中清屏，或是在UNIX系统（包括Linux和MacOS）中使用 `clear` 命令。

还记得学习使用IDE开发 Java 之前，你是如何运行 Java 程序的吗？

没错，还是那个CMD！(Windows)，基于 Java 跨平台的特性，无论你使用什么平台，你只需要把你的 Java 代码编译成 Jar 包，再使用系统控制台运行这个 Jar 包就行了。

为了方便后续测试，你可以在你的 Jar 包目录下新建一个 `start.bat` 文本文件，并填入以下内容：

```bash
echo off
title BConsole Test
# 注意把 your_jar_name.jar 替换成你实际的Jar包名字
java -jar -DfileEncoding=UTF-8 your_jar_name.jar
pause
```

这样一来，以后我们打算测试程序的时候，编译好后直接双击这个 `start.bat` 批处理文件就可以运行程序了。

🟢 现在让我们再次运行它！

![](images/1.2.2.gif)

恭喜，现在你已经成功使用 BConsole 完成了一个酷炫的屏幕时钟！在后续章节中，我们将逐步开始学习 BConsole 的各种使用方式与运行机制。

> KNOWLEDGE CARD
>
> **如何使用 IDEA 编译 Jar 包**
>
> ###### 1. 配置项目的主类
>
> 在打包之前，确保你的项目已经设置了正确的主类（即包含 `main` 方法的类）。
>
> 1. 打开项目。
> 2. 进入到 **File** > **Project Structure**（或者使用快捷键 `Ctrl+Alt+Shift+S`）。
> 3. 在打开的窗口中选择 **Modules** 下的 **Sources** 选项卡。
> 4. 确保你的源代码文件夹被标记为 **Sources**，并且资源文件夹（如 `res` 或 `resources`）被标记为 **Resources**。
> 5. 点击 **Artifacts** 选项。
> 6. 点击左上角的 `+` 按钮，选择 **JAR** > **From modules with dependencies...**。
>
> ###### 2. 设置 JAR 包构建选项
>
> 1. 在 **Main Class** 字段中选择你的应用程序入口点类（主类）。
> 2. 确定提取到 JAR 的库处理方式。常用的选项是 **extract to the target JAR**（将依赖库解压到目标 JAR 中），这样做可以确保所有依赖都包含在一个 JAR 文件内。
> 3. 调整其它选项如需要（例如包含 JavaFX 应用时的特殊处理）。
> 4. 点击 **OK** 保存你的设置。
>
> ###### 3. 构建 JAR 文件
>
> 1. 打开 **Build** 菜单。
> 2. 选择 **Build Artifacts...**。
> 3. 选择你刚配置的 Artifact。
> 4. 点击 **Build**。
>
> ###### 4. 运行打包的 JAR 文件
>
> 在成功构建 JAR 文件后，你可以在命令行中使用 `java -jar` 命令来运行它：
>
> ```bash
> java -jar path/to/your_jar_file.jar
> ```
>
> 确保 Java 运行时环境已经安装，并且版本与你的项目兼容。

## 二、基 础

### 2.1 BConsole 屏幕控制器

相信你已经发现了 BConsole 把控制台变身为 "UI" 的原理，其实就是把一些基础的文本内容按照你想要的方式排版组织，最后拼凑成一个大字符串，然后直接打印到控制台里，那么如何管理 BConsole 在控制台的输出行为呢？

在 `class BConsole` 中提供了一个静态方法：

> **</>** 获取单例的屏幕对象

```java
public static BConsole getScreen();
```

你可以在任何地方调用这个方法去获取控制台的屏幕操控对象，这个对象是懒汉式单例模式的，因此你无需担心代码中多次使用这个方法会造成控制台混乱输出的问题！

有了屏幕控制器 `screen` 之后，你就可以通过它提供的实例方法来管理你的控制台了。

（你可以想象成电视遥控器可以控制电视机的各种行为，比如开关、音量调节等，这些都是电视机自身的设置，你无法通过电视遥控器来让本该去抓光头强砍树的熊大熊二跑到灰太狼家里去救懒羊羊，如果你需要管理屏幕上显示的内容，显然不应该在这里研究）

### 2.2 启用和停止控制台输出

为了保证数据的实时性，控制台里的这些字符也面临着不断地清空和打印，显然在这个期间，如果还有其他程序在控制台打印内容，就不仅会导致当前显示错乱，而且在下一轮数据刷新的时候这些内容也会随之消失，因此你需要根据你的需要来控制 BConsole 什么时候去接管控制台。

**screen 实例方法**

> **</>** 启用控制台输出

```java
public void turnON();
```

> **</>** 停止控制台输出

```java
public void turnOFF();
```

当你启用了控制台输出，那么 BConsole 就会按照你对它的配置去接管控制台，在此期间如果发生了其他程序使用 `System.out.println("");` 等打印信息的行为、或者抛出异常的行为时，**这些信息并不会干扰 BConsole 在屏幕上渲染内容**，因为一旦调用了 `screen.turnON()` 方法后，BConsole 就会立即劫持系统的标准输出流，直到你使用 `screen.turnOFF()` 方法结束控制台输出为止！

让我们来试试看！

> **</>** 代码示例

```java
public class Test {
    public static void main(String[] args) throws InterruptedException {
        // 拿到屏幕控制器
        BConsole screen = BConsole.getScreen();
        // 启用控制台显示屏
        screen.turnON();
        // 等待3秒后
        Thread.sleep(3 * 1000);
        // 关闭控制台显示屏，退出程序
        screen.turnOFF();
    }
}
```

🟢 让我们运行它！

![](images/2.2.1.gif)

### 2.3 在屏幕上输出内容

在启用 `screen` 往控制台上输出内容前，你肯定需要告诉它你要输出什么内容，因此我们要先来认识一个用于承载文本内容的类与对象：`CompText`

> **</>** 使用CompText来存储文本内容

```java
CompText text = new CompText("Hello BConsole!\n你好，更好的控制台！");
```

这样一来，`text` 这个 `CompText` 类型的变量就保存了如下的文本内容：

```
Hello BConsole!
你好，更好的控制台！
```

接下来，你需要让 `screen` 来显示你的内容，这个操作叫做 “注册（register）”

> **</>** 向 `screen` 注册要显示的内容

```
screen.reg(text);
```

然后就可以启用控制台输出看看效果了

> **</>** 代码示例

```java
public class Test {
    public static void main(String[] args) throws InterruptedException {
        // 拿到屏幕控制器
        BConsole screen = BConsole.getScreen();
        // 使用 CompText 存储文本内容
        CompText text = new CompText("Hello BConsole!\n你好，更好的控制台！");
        // 注册文本内容到屏幕
        screen.reg(text);
        // 启用控制台显示屏
        screen.turnON();
        // 等待3秒后
        Thread.sleep(3 * 1000);
        // 关闭控制台显示屏，退出程序
        screen.turnOFF();
    }
}
```

🟢 让我们运行它！

![](images/2.3.1.gif)

### 2.4 响应式系统

如果只是为了显示静态文本内容，显然没必要大费周章去使用这个框架，**BConsole 拥有一套单向响应式系统，当一个变量发生了更改，BConsole 就能在下一次屏幕渲染时拿到这个变量的最新值并且把它输出到屏幕上！**

还记得刚开始学习使用 Java 打印 “Hello World” 时学过的 `System.out.println()` 方法吗，它还有一个亲兄弟： `System.out.printf()` ，相信用过的朋友都知道，如果要输出的是一个长字符串，并且里面有很多变量的时候，使用 `字符串 + 变量 + 字符串 ... ` 的方式拼接会非常繁琐，因此我们会使用 `printf()` 来格式化输出：

> **</>** println() 与 printf()

```java
public static void main(String[] args) {
  String name = "张三";
  int age = 5;
  System.out.println("我叫" + name + "，今年" + age + "岁了！"); // 传统字符串拼接输出
  System.out.printf("我叫%s，今年%d岁了！%n", name, age); // 格式化输出
}
```

在 `printf()` 中有许多格式化符号，常见的有：

- `%d` 用于整数
- `%f` 用于浮点数
- `%s` 用于字符串
- `%n` 表示换行

而在 BConsole 当中，也有一套类似的机制，不过我们并没有那么多符号，当你需要插入某个变量的时候，你只需要使用魔法变量 `$v$` 来占位，然后使用 `ref()` 方法给这些魔法变量对应地绑定变量，BConsole 会在未来渲染的时候去找到这个变量的最新值

 **`ref()` 的语法格式为：** `要绑定变量的对象.ref(v -> v.bind(对应的变量, 可变参数, 按照顺序填写, ...))`

> **</>** 将上面的例子改用 BConsole 来输出

```java
public class Test {
    public static void main(String[] args) throws InterruptedException {
        // 拿到屏幕控制器
        BConsole screen = BConsole.getScreen();
        // 使用 CompText 存储带变量的文本内容
        CompText text = new CompText("我叫$v$, 今年$v$岁了！");
        String name = "张三";
        int age = 5;
        // 绑定变量
        text.ref(v -> v.bind(name, age));
        // 注册文本内容到屏幕
        screen.reg(text);
        // 启用控制台显示屏
        screen.turnON();
        // 等待3秒后
        Thread.sleep(3 * 1000);
        // 关闭控制台显示屏，退出程序
        screen.turnOFF();
    }
}
```

🟢 让我们运行它！

![](images/2.4.1.gif)

既然 BConsole 是具备单向响应式能力的，那我们不妨让里面的变量动起来！

> **</>** 让张三每隔 2 秒就长大一岁，长大到 8 岁就改名叫张老三

```java
public class Test {
    public static void main(String[] args) throws InterruptedException {
        // 拿到屏幕控制器
        BConsole screen = BConsole.getScreen();
        // 使用 CompText 存储带变量的文本内容
        CompText text = new CompText("我叫$v$, 今年$v$岁了！");
        String name = "张三";
        int age = 5;
        // 绑定变量
        text.ref(v -> v.bind(name, age));
        // 注册文本内容到屏幕
        screen.reg(text);
        // 启用控制台显示屏
        screen.turnON();
        // 让张三每隔 2 秒就长大一岁，长大到 8 岁就改名叫张老三
        do {
            Thread.sleep(2 * 1000);
        } while (++age < 8);
        name = "张老三";
        // 等待3秒后
        Thread.sleep(3 * 1000);
        // 关闭控制台显示屏，退出程序
        screen.turnOFF();
    }
}
```

🟢 让我们运行它！

```
Test.java:21:30
java: 从lambda 表达式引用的本地变量必须是最终变量或实际上的最终变量
```

发现程序在编译时报错，Lambda 表达式是在 JDK8 之后引入的新特性，原来在 Java 中，Lambda 表达式引用的局部变量必须是 `final` 或 `effectively final` 的，换句话来说，你必须保证这个变量是有效"final"的，解决这个问题非常简单，就是给这些变量进行一层封装，比如用数组或是容器类，他们的引用是 `final` 的，他们的内容修改了，自身的引用地址并没有修改。但是仔细想想我们的需求，要绑定的变量都是会在未来变更的值，如果你在绑定变量的域定义了变量，就算他们是引用类型，不在此作用域的代码是拿不到这些变量的，并不方便在后期进行更改，尤其是如果是多线程情况下对各类数据进行实时统计，因此，一个好的建议就是使用类变量，类变量是有效"final"的，并且为了能随时随地使用和更改这些变量，我们希望这些变量都是静态变量，这样无论程序执行到何处，都一定可以通过类名访问到同一个变量，在 Lambda 表达式内部亦是如此。

**因此，在使用 `ref()` 绑定变量时，该变量必须是可访问的 <span style="color: red;">静态变量 (static) </span>！**

> **</>** 完善上面的代码

```java
public class Test {
    
    static String name;  // 静态变量
    static int age;  // 静态变量

    public static void main(String[] args) throws InterruptedException {
        // 拿到屏幕控制器
        BConsole screen = BConsole.getScreen();
        // 使用 CompText 存储带变量的文本内容
        CompText text = new CompText("我叫$v$, 今年$v$岁了！");
        name = "张三";
        age = 5;
        // 绑定变量
        text.ref(v -> v.bind(name, age));
        // 注册文本内容到屏幕
        screen.reg(text);
        // 启用控制台显示屏
        screen.turnON();
        // 让张三每隔 2 秒就长大一岁，长大到 8 岁就改名叫张老三
        do {
            Thread.sleep(2 * 1000);
        } while (++age < 8);
        name = "张老三";
        // 等待3秒后
        Thread.sleep(3 * 1000);
        // 关闭控制台显示屏，退出程序
        screen.turnOFF();
    }
}
```

🟢 让我们再次运行它！

![](images/2.4.2.gif)

### 2.5 屏幕刷新周期

在启用屏幕输出 `screen.turnON()` 后，会默认以 `每秒1次` 的刷新频率持续更新控制台里显示的内容，如果你希望变更这个刷新频率，可以使用以下方法来变更：

> **</>** 立即设置屏幕内容渲染间隔

```java
public BConsole setRefInterval(long interval);
```

- 接收值 `interval` 表示多少毫秒更新一次，默认值为1秒（default interval = 1000）
- 你可以将 `interval` 设置为 `Long.MAX_VALUE` 来禁用屏幕内容自动更新
- 请注意，这是大概的刷新频率，实际速度取决于刷新回调的处理时间，即设置1000，一分钟的实际刷新次数肯定小于60次

如果出于性能考虑，手动设置了较长的屏幕刷新周期或者干脆设置为禁用更新，但又需要在某个变量发生变更时去触发更新，可以使用以下方法来手动刷新：

> **</>** 手动刷新一次显示

```java
public BConsole doRefresh();
```

### 2.6 用户行为交互

由 BConsole 接管的控制台不仅能显示各种各样的内容，同时仍然具备获取用户输入交互的能力，还记得初学 Java 时，你的程序是如何在运行后去获取用户输入的吗？

> **</>** 使用 Scanner 扫描用户输入

```java
public static void main(String[] args) {
  System.out.print("请输入年龄：");
  Scanner scanner = new Scanner(System.in);
  String input = scanner.next();
  System.out.println("你的年龄是" + input + "岁！");
}
```

在 BConsole 启用屏幕输出后，一般情况下会不断刷新屏幕显示的内容，此时仍然像上面那样获取用户输入，会使用户非常痛苦，毕竟如果你没有在两次刷新之间的时间内完成输入，你所输入的内容会随着屏幕内容一并被更新，~~当然，这或许是训练用户手速的一个好办法！~~

**BConsole 提供了以下方法来获取用户输入：**

> **</>** 获取用户输入

```java
public String getUserInput();
public String getUserInput(Comp<?> tips);
```

- 当你调用此方法后，会阻塞当前线程，并暂停显示器刷新，等待用户输入；

  当用户输入一行文本后，按下 Enter 键，会将结果返回，并恢复显示器刷新，取消阻塞线程

- 本质上，方法内部封装了一个懒汉式 `Scanner` ，读取的是 `nextLine()`，并以此作为返回值

- 你不可以在获取用户输入期间去调用 `waitUserInterrupt()` 方法来等待用户中断（继续）

- 该方法提供了一个可选的形参，类型为 `Comp<?>` ，接收一个组件用作输入提示；

  如果你调用的是无参方法，会沿用上次的输入提示组件，并且会自动更新；如从未设置，则使用默认输入提示组件： `"请输入内容以继续：（输入结束使用 Enter 键确认）"`

> 前面你所使用过的 `CompText` 就是组件之一！显然，默认的输入提示组件本质上就是：
>
> ```
> new CompText("请输入内容以继续：（输入结束使用 Enter 键确认）");
> ```

**BConsole 还提供了以下方法来等待用户中断（继续）：**

> **</>** 等待用户中断（继续）

```java
public void waitUserInterrupt();
public void waitUserInterrupt(Comp<?> tips);
```

- 当你调用此方法后，会阻塞当前线程，但不会暂停显示器刷新，等待用户中断（继续）；

  当用户按下 Enter 键后，会取消阻塞当前线程

- 本质上，方法内部和 `getUserInput()` 是大同小异的，相当于传递了 `nextLine()` 的阻塞作用

- 你不可以在获取用户输入期间去调用 `getUserInput()` 方法来获取用户输入

- 该方法提供了一个可选的形参，类型为 `Comp<?>` ，接收一个组件用作中断（继续）提示；

  如果你调用的是无参方法，会沿用上次的中断（继续）提示组件，并且会自动更新；如从未设置，则使用默认中断（继续）提示组件： `"按下 Enter 键继续"`

接下来，我们在两个例子中分别体验这两种交互方式

> **</>** 获取用户输入：getUserInput();
>
> 8岁的张老三想让你帮他辅导作业，快告诉他你能辅导哪个学科吧

```java
public class Test {
    static String name = "张老三";
    static int age = 8;
    static String input = "什么学科呢";

    public static void main(String[] args) throws InterruptedException {
        // 拿到屏幕控制器
        BConsole screen = BConsole.getScreen();
        // 使用 CompText 存储带变量的文本内容
        CompText text = new CompText("我叫$v$, 今年$v$岁了！\n你可以辅导我的$v$");
        // 绑定变量
        text.ref(v -> v.bind(name, age, input));
        // 注册文本内容到屏幕
        screen.reg(text);
        // 启用控制台显示屏
        screen.turnON();
        // 等待3秒后
        Thread.sleep(3 * 1000);
        // 获取用户输入：getUserInput();
        input = screen.getUserInput();
        // 再等待3秒后
        Thread.sleep(3 * 1000);
        // 关闭控制台显示屏，退出程序
        screen.turnOFF();
    }
}
```

🟢 让我们运行它！

![](images/2.6.1.gif)

> **</>** 等待用户中断（继续）：waitUserInterrupt();
>
> 现在8岁的张老三一直在打王者荣耀，你快中断他打下去，不然他妈妈就要回来了！

```java
// 本例中，启动了一个新的线程，持续在做一项任务，而主线程利用了waitUserInterrupt()来管理子线程行为
public class Test {
    static String name = "张老三";
    static int age = 8;
    static int game = 0;
    static boolean allowPlayGames = true;
    static String msg = "你不管我我就继续玩！";

    public static void main(String[] args) throws InterruptedException {
        // 拿到屏幕控制器
        BConsole screen = BConsole.getScreen();
        // 使用 CompText 存储带变量的文本内容
        CompText text = new CompText("我叫$v$, 今年$v$岁了！\n我今天已经玩儿了$v$把王者了！\n$v$");
        // 绑定变量
        text.ref(v -> v.bind(name, age, game, msg));
        // 注册文本内容到屏幕
        screen.reg(text);
        // 启用控制台显示屏
        screen.turnON();
        // 等待3秒后
        Thread.sleep(3 * 1000);
        // 张老三去打王者了
        new Thread(() -> {
            // 张老三每200毫秒就能玩儿一把！
            while (allowPlayGames) {
                game++;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignore) {
                }
            }
        }).start(); 
        // 等待用户中断（继续）：waitUserInterrupt();
        BConsole.getScreen().waitUserInterrupt();
        // 张老三，快停下！
        allowPlayGames = false;
        // 再等待3秒后
        Thread.sleep(3 * 1000);
        // 关闭控制台显示屏，退出程序
        screen.turnOFF();
    }
}
```

🟢 让我们运行它！

![](images/2.6.2.gif)

### 2.7 系统标准输出流

不知道你是否还有印象，只要 `screen` 在运行状态，BConsole 就会劫持系统的标准输出流，换言之，你原来的代码中如果存在 `System.out.println()` 等类似方法，他们都将统统失效，那我们是否有办法能够在既使用 BConsole 管理控制台输出的同时，也能获取到系统原来的标准输出流呢？

**BConsole 提供了保存系统输出流到文件的方法！**

> **</>** 保存系统输出流到文件 包括 System.out 和 System.err

```java
public BConsole saveSystemOut();
public BConsole saveSystemOut(String savePath);
public BConsole saveSystemOut(File file);
```

- 如果你直接调用无参方法，那么系统会按照如下规则来为输出文件命名，并且存储在当前目录下

  ```
  system-log yyyy-MM-dd HH-mm-ss SSS.txt
  ```

- 如果你调用有参方法，那么你需要指定一个存储位置，可以直接输入他的路径，也可以传入一个 `java.io.File`

- 如果程序运行时，指定的文件已经存在，那么会默认在文件末尾开始追加输出

> **</>** 保存系统标准输出流 saveSystemOut();
>
> 张老三最近游戏状态很不好，于是他想统计一下每把游戏的输赢情况

```java
public class Test {
    static String name = "张老三";
    static int age = 8;
    static int game = 0;
    static boolean allowPlayGames = true;
    static String msg = "你不管我我就继续玩！";

    public static void main(String[] args) throws InterruptedException {
        // 拿到屏幕控制器
        BConsole screen = BConsole.getScreen();
        // 使用 CompText 存储带变量的文本内容
        CompText text = new CompText("我叫$v$, 今年$v$岁了！\n我今天已经玩儿了$v$把王者了！\n$v$");
        // 绑定变量
        text.ref(v -> v.bind(name, age, game, msg));
        // 注册文本内容到屏幕
        screen.reg(text);
        // 把显示期间的所有 System.out.println 存下来
        screen.saveSystemOut("game-log.txt");
        // 启用控制台显示屏
        screen.turnON();
        // 等待3秒后
        Thread.sleep(3 * 1000);
        // 张老三去打王者了
        new Thread(() -> {
            // 张老三每200毫秒就能玩儿一把！
            while (allowPlayGames) {
                game++;
                // 把这把游戏的情况打出来
                System.out.println("今天的第" + game + "把游戏，" + (Math.random() < 0.2 ? "赢了！" : "输了..."));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignore) {
                }
            }
        }).start();
        // 等待用户中断（继续）：waitUserInterrupt();
        BConsole.getScreen().waitUserInterrupt();
        // 张老三，快停下！
        allowPlayGames = false;
        // 再等待3秒后
        Thread.sleep(3 * 1000);
        // 关闭控制台显示屏，退出程序
        screen.turnOFF();
    }
}
```

🟢 让我们运行它！

![](images/2.7.1.gif)

### 2.8 全局守卫（全局渲染钩子）

相信前面的例子你已经体验到了 BConsole 强大的响应式系统与优雅的用户交互了，但是在实际开发使用中，并不是所有的变量都能简单抽离出来，比如你想在屏幕上展示的变量，需要从一个集合里取出，或者是这个变量想要获取他新的值，得在之前做些什么（比如一些初始化操作等），亦或是访问完某个变量后，得给他打上标记，或者重置某些状态（比如访问的是一个迭代器，拿完元素之后得把迭代器指针回退），如果环境中存在多线程的情况，某些变量还涉及到锁的概念……

因此，BConsole 也具备守卫的概念，顾名思义，就是在触发渲染动作时，有这么两个守卫默默地守在这个动作之前和之后，分别在这两个时机去执行相应的动作，直接使用 `screen` 的守卫，也叫做使用全局守卫，具有全局唯一性，它们的触发时机是在全部组件的整个渲染过程的之前和之后。

> **</>** 全局前置守卫

```java
public BConsole beforeEach(Refresh refresh);
```

- 设置每次屏幕显示更新前的回调
- 如果多次调用了这个方法，那么以最后一次调用为准（全局唯一性）
-  `Refresh` 是一个函数式接口，设置回调方法直接使用无参数无返回值的 Lambda 表达式即可

> **</>** 全局后置守卫

```java
public BConsole afterEach(Refresh refresh);
```

- 设置每次屏幕显示更新后的回调
- 如果多次调用了这个方法，那么以最后一次调用为准（全局唯一性）
- 与前置守卫相同，设置回调方法直接使用无参数无返回值的 Lambda 表达式即可

> **</>** 使用全局守卫
>
> 张老三才8岁，主要任务是学习，偷偷打游戏会被你打断，于是他在每次你开门检查之前都把手机藏起来了

```java
public class Test {
    static String name = "张老三";
    static int age = 8;
    static int game = 0;
    static int reallyGame = 0;
    static boolean allowPlayGames = true;
    static String msg = "你不管我我就继续玩！";

    public static void main(String[] args) throws InterruptedException {
        // 拿到屏幕控制器
        BConsole screen = BConsole.getScreen();
        // 使用 CompText 存储带变量的文本内容
        CompText text = new CompText("我叫$v$, 今年$v$岁了！\n我今天已经玩儿了$v$把王者了！\n$v$");
        // 绑定变量
        text.ref(v -> v.bind(name, age, game, msg));
        // 注册文本内容到屏幕
        screen.reg(text);
        // 把显示期间的所有 System.out.println 存下来
        screen.saveSystemOut("game-log.txt");
        // 启用控制台显示屏
        screen.turnON();
        // 等待3秒后
        Thread.sleep(3 * 1000);
        // 张老三委托全局前置守卫来让你每次看他的游戏情况都是0
        screen.beforeEach(() -> {
            reallyGame = game;
            game = 0;
        });
        // 张老三统计自己输赢的工作还得继续，于是委托全局后置守卫在你不看他的时候把游戏情况变回去
        screen.afterEach(() -> {
            game = reallyGame;
        });
        // 张老三去打王者了
        new Thread(() -> {
            // 张老三每200毫秒就能玩儿一把！
            while (allowPlayGames) {
                game++;
                // 把这把游戏的情况打出来
                System.out.println("今天的第" + game + "把游戏，" + (Math.random() < 0.2 ? "赢了！" : "输了..."));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignore) {
                }
            }
        }).start();
        // 等待用户中断（继续）：waitUserInterrupt();
        BConsole.getScreen().waitUserInterrupt();
        // 张老三，快停下！
        allowPlayGames = false;
        // 再等待3秒后
        Thread.sleep(3 * 1000);
        // 关闭控制台显示屏，退出程序
        screen.turnOFF();
    }
}
```

🟢 让我们运行它！

![](images/2.8.1.gif)

> 显然，张老三利用了全局守卫完美地掩盖了自己玩儿游戏，但是从他的游戏日志中发现，有一些对局的计数似乎是错误的，如果你恰好学到了 Java JUC 并发编程，你肯定发现这里引发了线程安全问题，试试看如何利用守卫机制和对象锁来改良代码，让张老三在藏手机的同时不会影响他的游戏！

### 2.9 链式调用

到这一步，你已经对 BConsole 的基础使用有了足够的了解，已经能让大部分基于控制台输入输出的程序变得优雅了，但是从本章的第一小节2.1开始到现在，你不难发现我们的示例代码因为需求的变更越来越长，甚至现在还只使用了一个 `CompText` ，我们有没有什么办法能让代码更加精简呢？

不知道你是否有留意，你前面所接触的所有方法，除了启用与停止，以及两个与用户交互的方法之外，他们都有着同样的返回值，就是 `screen` 它本身，这是声明式编程中的一种常见手段，通过链式调用增加代码可阅读性、可组织性、可维护性，除此之外，BConsole 的全部开发过程都体现了这个理念，链式调用，随处可用！

> **</>** 将前面张老三的代码使用链式调用进行重构

```java
public class Test {
    static String name = "张老三";
    static int age = 8;
    static int game = 0;
    static int reallyGame = 0;
    static boolean allowPlayGames = true;
    static String msg = "你不管我我就继续玩！";

    public static void main(String[] args) throws InterruptedException {
        // 使用 CompText 存储带变量的文本内容并且绑定变量
        CompText text = new CompText("我叫$v$, 今年$v$岁了！\n我今天已经玩儿了$v$把王者了！\n$v$")
                .ref(v -> v.bind(name, age, game, msg));
        // 让屏幕控制器显示 text，然后要保存输出，并且前后守卫都要有动作，打开屏幕
        BConsole.getScreen().reg(text).saveSystemOut("game-log.txt").beforeEach(() -> {
            reallyGame = game;
            game = 0;
        }).afterEach(() -> game = reallyGame).turnON();
        // 三秒后张老三开始玩游戏
        Thread.sleep(3 * 1000);
        new Thread(() -> {
            while (allowPlayGames) {
                game++;
                System.out.println("今天的第" + game + "把游戏，" + (Math.random() < 0.2 ? "赢了！" : "输了..."));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignore) {
                }
            }
        }).start();
        // 我们现在已经不能发现张老三偷偷玩游戏了！
        BConsole.getScreen().waitUserInterrupt();
        allowPlayGames = false;
        Thread.sleep(3 * 1000);
        BConsole.getScreen().turnOFF();
    }
}
```

🟢 让我们运行它！

![](images/2.9.1.gif)

> 运行效果与之前完全一致！

### 2.10 组件基础

一个页面往往不会只枯燥地显示几个字就结束了，随着要展示的信息变多，我们不仅要考虑排版设计的问题，还要考虑有哪些信息的展示是可重用的，哪些信息是独立的，并且对于每个单个的信息，会不会在不同的背景下他的作用与意义又不同了呢？

一般情况下，随着一个用户界面程序的深入开发的过程中，他的各个页面中的组件就会被组织排列成层层嵌套的树状结构，如果你接触过前端开发，你应该对这个概念再熟不过了，因为 HTML 文档的标签元素嵌套关系，恰恰就是组件化思想的一种体现，不同的标签有着不同的职责，当一堆标签组织成一个整体时（比如 `<div></div>` ），你只需要把这个整体再封装，再操作，而无需关心其内部的变化。BConsole 的职责是把开发者提供的各种信息组织渲染到控制台当中，因此也同样的实现了自己的组件模型，使我们可以在每个组件内封装自定义内容与逻辑。

**组件**：英文单词叫 Component，在 BConsole 中，被简称为 Comp。

你所能注意到的所有以 CompXXXX 开头的类，都是 BConsole 内置实现的组件类，事实上，BConsole 处理的一切内容皆为组件，即使你给他传入的是字符串，在他的底层实现，照样是帮你封装成了合适的组件以提供 BConsole 渲染。

- **如何使用组件呢**：

  - 要使用什么组件，就需要把相应的组件实例化成对象，显然，如果按照组件化思想来开发界面程序，我们肯定会把所有组件整理好并且把他们都放在一个组件当中，最后把这个组件交给 BConsole 去渲染，将组件挂载到某个组件下的操作我们称之为组件注册，相应的也会形成父子组件关系，最后整个页面必然会形成一个组件树，因此一个优秀的界面开发，在前期设计组件结构相当重要！

- **如何注册组件**：

  - 只要是 BConsole 的组件，那么每个组件都提供了注册方法：

    > **</>** 组件注册

    ```java
    public T reg(Comp<?> comp);
    ```

    > **</>** 例如

    ```java
    CompText text1 = new CompText("这是父组件");
    CompText text2 = new CompText("这是子组件");
    text1.reg(text2);
    ```

    调用 `text1` 的 `reg()` 方法就会把传入的 `text2` 作为 `text1` 的子组件，在渲染 `text1` 时，子组件 `text2` 也会被一并渲染

    现在让 BConsole 将 `text1` 渲染出来，你会得到如下的结果：

    ```
    这是父组件这是子组件
    ```

    > 对于组件 `CompText` 来说，它是行级组件，并不会自动在组件渲染结束后添加换行符！
    >
    > 这个在后面的章节会详细介绍

  - 组件可以注册任意多次，也可以被注册任意多次：

    > **</>** 例如

    ```java
    CompText text1 = new CompText("这是父组件");
    CompText text2 = new CompText("这是子组件");
    text1.reg(text2).reg(text2).reg(text2).reg(text2);
    ```

    没错，在 2.9 中我们提到过，链式调用，随处可用！

    现在让 BConsole 将 `text1` 渲染出来，你会得到如下的结果：

    ```
    这是父组件这是子组件这是子组件这是子组件这是子组件
    ```

  - 现在我们来“小试牛刀”一下，请分析下列代码中，BConsole 渲染后的结果是怎样的？（答案附在本节的最后）

    1. ```java
       public static void main(String[] args) {
           CompText lgTitle = new CompText("大标题\n");
           CompText smTitle = new CompText("小标题\n");
           CompText content = new CompText("文章的段落\n");
           CompText page = new CompText("一页纸\n");
           page.reg(lgTitle).reg(smTitle).reg(content).reg(smTitle).reg(content);
           // 渲染 page
           BConsole.getScreen().saveSystemOut().reg(page).turnON();
       }
       ```

    2. ```java
       public static void main(String[] args) {
           CompText text1 = new CompText("我是text1\n我是text1的第二行，我还有一个换行\n");
           CompText text2 = new CompText("我是text2\n我是text2的第二行，我就没有换行了。");
           CompText text3 = new CompText("我是text3，我只有一行！");
           text2.reg(text3);
           text1.reg(text3).reg(text2);
           // 渲染 text1
           BConsole.getScreen().saveSystemOut().reg(text1).turnON();
       }
       ```

    3. ```java
       public static void main(String[] args) {
       	CompText c1 = new CompText("这是组件1\n");
           CompText c2 = new CompText("这是组件2\n");
           CompText c3 = new CompText("这是组件3\n");
           CompText c4 = new CompText("这是组件4\n");
           c2.reg(c4);
           c4.reg(c3);
           c1.reg(c2).reg(c4);
           // 渲染 c1
           BConsole.getScreen().saveSystemOut().reg(c1).turnON();
       }
       ```

  - 组件注册的大忌！**不要相互注册！**

    你是否有过这样的设想：

    - 我把 `c1` 注册到 `c2` 上之后，我再把 `c2` 注册到 `c1` 上，会发生什么？
    - 我把 `c1` 注册到 `c2` 上，然后把 `c2` 注册到 `c3` 上，最后把 `c3` 注册到 `c1` 上，又会发生什么？

    > **</>** 组件相互注册（循环注册）

    ```java
    public static void main(String[] args) {
        CompText c1 = new CompText("c1");
        CompText c2 = new CompText("c2");
        c1.reg(c2);
        c2.reg(c1);
        BConsole.getScreen().saveSystemOut().reg(c1).turnON();
    }
    ```

    🟢 让我们运行它！

    ```
    Exception in thread "Console-Screen-Render" java.lang.StackOverflowError
    	at com.lhl.bconsole.Comp.lambda$childrenRender$1(Comp.java:149)
    	at java.base/java.util.concurrent.atomic.AtomicReference.updateAndGet(AtomicReference.java:210)
    	at com.lhl.bconsole.Comp.lambda$childrenRender$2(Comp.java:149)
    	at com.lhl.bconsole.VariablePool.forEach(VariablePool.java:91)
    	... (此处省略1026行堆栈记录)
    	at com.lhl.bconsole.Comp.lambda$childrenRender$2(Comp.java:149)
    	at com.lhl.bconsole.VariablePool.forEach(VariablePool.java:91)
    	at com.lhl.bconsole.Comp.childrenRender(Comp.java:149)
    	at com.lhl.bconsole.Comp.render(Comp.java:102)
    	at com.lhl.bconsole.Comp.toString(Comp.java:155)
    ```

    很明显，在这种组件相互注册的情况下，渲染动作一旦发生在它们任何一个组件上时，都会递归地去找到最后一个子组件来渲染，但是“子组件的子组件”又是它自己，这样就会导致无限找下去，最后导致栈溢出错误，从而造成惨烈的后果，在设计组件树时，也显然不应该去把两个或多个组件循环套用！

- **组件命名与管理**：

  在开发过程中，合理的组件命名和有效的组件管理策略将直接影响到整个项目的结构和未来的扩展性。组件要知名就只其意，在组件数量非常多，结构非常复杂的时候，一个好的取名习惯和管理习惯也会让你事半功倍！

  1. **命名清晰性**：选择具有描述性且易于理解的名称，例如 `UserLoginForm`、`NavigationHeader`、`ProfileCard` 等，这些名称直观地反映了组件的功能和用途。
  2. **保持一致性**：在整个项目中使用一致的命名规则。如果你用 `Panel` 结尾表示一个容器类组件，那么在项目中所有类似的组件都应遵循这一规则。
  3. **避免过度简化**：虽然诸如 `Btn`、`Nav` 这样的缩写可以简化命名，但过度使用可能会降低代码的可读性。在不影响清晰性的情况下，尽量使用完整的单词。
  4. **层次感表达**：在组件名称中表达出它们在 UI 结构中的层次关系，如 `SettingsButton` 可能位于 `SidebarMenu` 组件中。
  5. **适当使用前缀和后缀**：使用前缀和后缀来标识组件类型或特性，如 `EditableTable`、`SearchInput` 等。
  6. **创建组件库**：为常用的功能和布局创建可重用组件库，这不仅可以减少重复代码，还能通过统一的接口简化开发。
  7. **组件封装**：合理封装组件，确保组件的内部实现不会泄漏到外部使用。封装不仅包括数据的隐藏，还应该包括样式和行为的封装，使得组件可以在不同的环境下重复使用而不会产生冲突。

在接下来的章节中，我们将深入讲解 BConsole 中的各种组件，他们都有什么功能、什么作用、什么特性。

> 附：组件注册“小试牛刀”答案
>
> 1. ```
>    一页纸
>    大标题
>    小标题
>    文章的段落
>    小标题
>    文章的段落
>    ```
>
> 2. ```
>    我是text1
>    我是text1的第二行，我还有一个换行
>    我是text3，我只有一行！我是text2
>    我是text2的第二行，我就没有换行了。我是text3，我只有一行！
>    ```
>
> 3. ```
>    这是组件1
>    这是组件2
>    这是组件4
>    这是组件3
>    这是组件4
>    这是组件3
>    ```

## 三、深入组件

### 3.1 Comp - 根组件

……还没写

### 3.2 内置组件

- **文本** CompText 
- **艺术字** CompArtText
- **块** CompBlock 
- **水平线** CompHr
- **进度条** CompBar
- **表格** CompTable
- **视图** CompView

### 3.3 预设视图

- **屏幕时钟** clockView
- **系统输出** stdOutView
- **系统信息** sysInfoView
- ……还没开发完，敬请期待

### 3.3 自定义组件

……没写

## 四、路 由

……没写

## 五、渲染机制

……没写
