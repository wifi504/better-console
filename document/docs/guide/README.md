---
sidebarDepth: 2
---

# 开 始

## 1.1 介 绍

###  🔔 前 言

控制台一般指的是一个程序与使用者交互的工具，它还有一个名字叫做“终端”，普遍是以纯文本流输入输出的形式进行工作。通常情况下，一个程序要使用控制台与用户交互信息，都是“你输入一行，我打(print)一行”，使用体验极其糟糕！作为程序开发者，如果想展示的数据过于复杂、动态、结构化，光使用控制台的解决方案只会徒增工作量，费力不讨好，但是对于小型的工具类程序、测试程序、以及一些新手开发者来说，专门为此开发图形用户界面并不是一个低成本且轻而易举的选择，回想一下，你有多少有趣的小作品因为人机交互不够优雅而胎死腹中了呢，现在，你可以有一个新的选择！

**正如我的名字所述：“Better Console”，直译为“更好的控制台”，简称为 BConsole。**

BConsole 是一款用于**在控制台中构建图形用户界面**的框架。它**完全基于 Java SE 构建**，并提供了一套**声明式的、组件化的编程模型**，帮助你高效地开发用户界面。无论是简单还是复杂的界面，BConsole 都可以胜任，丢掉你的 `System.out.println("");` 吧！

BConsole 代码[完全开源](https://github.com/wifi504/better-console) ，如果你正在学习 Java SE ，阅读本框架源代码应该可以辅助你学习与实践有关面向对象、封装继承多态、设计模式、集合、泛型、IO流、线程、流API、函数式接口等知识点。

::: tip 作者留言

BConsole 现处于孵化阶段，也算是作者本人在系统学习完 Java SE 后开发的第一个贯穿了大量 Java SE 基础知识的项目，也欢迎大家在方方面面提供宝贵意见，如果有错误需要修正，或者算法需要优化等，欢迎大家提 [Issues](https://github.com/wifi504/better-console/issues) 与 [PR](https://github.com/wifi504/better-console/pulls) ! 作者会长期活跃，并且会将参与共创的同学附在此处或首页等地方致谢🎈

:::

### ✨ 特 色

- **BConsole 没有使用任何第三方依赖**，所有功能完全内置实现，开箱即用！
- **上手几乎没有任何门槛**，甚至如果你正是一位刚刚开始学习 Java SE 的新手，这个框架有助于逐渐提升你对整套 Java SE 的理解！
- **声明式编程范式**：它关注于描述 “做什么” 而非 “怎么做” 。在声明式编程模型中，你表述程序的逻辑，而不必详细说明其控制流和状态管理！
- **组件化编程范式**：它强调将软件系统分解为独立的、可复用的模块（即组件）。每个组件封装了特定的功能或逻辑，具有明确的接口和依赖关系，可以在不同的上下文中独立使用和组合！
- **渐进式框架**：它允许开发者按需采用框架的各个特性，从而逐步增加应用程序的复杂性和功能。BConsole 可以在你几乎不需要修改项目源代码的情况下，直接给项目中需要展示的任何内容按你所想显示到控制台中，可拓展、灵活、易用、模块化、适应性强！

::: tip

说到这里，相信你已经想起了一位故人：Vue，不认识它也没什么关系，没错，本框架的开发大量学习了 Vue 的思想

:::

## 1.2 快速上手

### 前置条件

1. 只要你会使用 Java 打印 Hello World，那么应该就可以玩儿转本框架去显示各种有意思的内容
2. 你的电脑上正确安装了 JDK（推荐版本 17），以及对应集成开发环境（推荐IntelliJ IDEA）

### 引入 BConsole 

- **IDEA 手动导入 Jar 包依赖**
  - 前往 [Releases](https://github.com/wifi504/better-console/releases) 下载 [better-console.jar](https://github.com/wifi504/better-console/releases/download/v1.0.0/better-console.jar)
  - 打开项目并进入 `File > Project Structure`
  - 在 `Modules` 中选择你要使用 BConsole 的模块，然后进入 `Dependencies` 选项卡
  - 点击下方的 `+`，选择 `JARs or directories`，然后选择你的 Jar 包
  - 应用更改，完成，尝试在项目中使用 `BConsole.getScreen()` 判断是否导入成功
- **使用 maven 引入**
  - 目前还没有导入到 maven 仓库，敬请期待……
- **直接下载源代码将对应类复制到自己的项目目录下**
  - 前往 [Releases](https://github.com/wifi504/better-console/releases) 下载 [sources_release.zip](https://github.com/wifi504/better-console/releases/download/v1.0.0/sources_release.zip)
  - 直接把 `src` 目录下的 `top` 文件夹复制到你项目的 `src` 目录下
  - 尝试在项目中使用 `BConsole.getScreen()` 判断是否导入成功

::: tip

方式多种多样，选择最适合你的一种即可~

- 方式一（ `IDEA 手动导入 Jar 包依赖` ）与方式二（ `使用 maven 引入` ）都是直接使用 BConsole 编译好的类直接运行，如果你是在实际开发中使用，推荐使用这种方式
- 方式三（ `直接下载源代码将对应类复制到自己的项目目录下` ）适用于需要阅读源码学习的情况，此种方式导入可以查看到原始代码中的所有注释，在 `Debug` 调试模式下也更好探究 BConsole 的运行机制

:::

### 创建第一个基于 BConsole 驱动的小程序

在本节中，我们来试着在自己电脑上把黑不溜秋的控制台变成一个酷炫的屏幕时钟吧

**</> 代码示例**

```java
public static void main(String[] args) throws InterruptedException {
    // 启用控制台显示屏，并显示一个数字时钟
    BConsole.getScreen().reg(PresetViews.getClockView()).turnON();
    // 等待一分钟后
    Thread.sleep(60 * 1000);
    // 关闭控制台显示屏，退出程序
    BConsole.getScreen().turnOFF();
}
```

🟢 让我们运行它！

![1.2.1.gif](/images/1.2.1.gif)

什么？控制台为什么在循环打印奇奇怪怪的东西？这是正确的吗？

原来，我们能在控制台上看到的文本内容都是程序“流式输出”的，你现在不懂也无妨，你只需要知道这些内容一旦被打印，他就会被永远记录在控制台上，一般情况下会直到这个终端会话结束。

::: tip KNOWLEDGE CARD

**流式输出**（Stream Output）

一种计算机输出数据的方式，数据被连续发送至输出设备（如控制台、文件、网络等），而不需要等待所有数据都准备好才进行输出。这种方式特别适用于数据量大或者实时性要求高的场景，如实时日志系统、视频流处理或实时数据传输。

知识点来自：Java I/O 流 `package java.io`

:::

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

![1.2.2.gif](/images/1.2.2.gif)

恭喜，现在你已经成功使用 BConsole 完成了一个酷炫的屏幕时钟！在后续章节中，我们将逐步开始学习 BConsole 的各种使用方式与运行机制。

::: tip KNOWLEDGE CARD

**如何使用 IDEA 编译 Jar 包**

1. **配置项目的主类**

   在打包之前，确保你的项目已经设置了正确的主类（即包含 `main` 方法的类）。

   1. 打开项目。
   2. 进入到 **File** > **Project Structure**（或者使用快捷键 `Ctrl+Alt+Shift+S`）。
   3. 在打开的窗口中选择 **Modules** 下的 **Sources** 选项卡。
   4. 确保你的源代码文件夹被标记为 **Sources**，并且资源文件夹（如 `res` 或 `resources`）被标记为 **Resources**。
   5. 点击 **Artifacts** 选项。
   6. 点击左上角的 `+` 按钮，选择 **JAR** > **From modules with dependencies...**。

2. **设置 JAR 包构建选项**

   1. 在 **Main Class** 字段中选择你的应用程序入口点类（主类）。
   2. 确定提取到 JAR 的库处理方式。常用的选项是 **extract to the target JAR**（将依赖库解压到目标 JAR 中），这样做可以确保所有依赖都包含在一个 JAR 文件内。
   3. 调整其它选项如需要（例如包含 JavaFX 应用时的特殊处理）。
   4. 点击 **OK** 保存你的设置。

3. **构建 JAR 文件**

   1. 打开 **Build** 菜单。
   2. 选择 **Build Artifacts...**。
   3. 选择你刚配置的 Artifact。
   4. 点击 **Build**。

4. **运行打包的 JAR 文件**

   在成功构建 JAR 文件后，你可以在命令行中使用 `java -jar` 命令来运行它：

   ```bash
   java -jar path/to/your_jar_file.jar
   ```

   确保 Java 运行时环境已经安装，并且版本与你的项目兼容。

:::
