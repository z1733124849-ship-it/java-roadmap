# Java 基础笔记 2：继承、多态、抽象类、接口、包、内部类

2026-11-18　对照项目：`jwt-lab`

接在《java基础-1-类型与修饰符》后面。这六个话题是连着的：继承是多态的前提，抽象类和接口是"半成品"和"合同"两种写法，包和内部类管的是代码怎么放、怎么组织。每条都配了生活类比和项目里的落点，最后有自测题。

## 1. 继承

一句话：子类用 `extends` 继承父类，白拿父类的字段和方法，再补自己的。

生活类比：家谱。儿子随父姓，继承家产（字段和方法），也可以有自己的新手艺（新方法），还能把父辈的老手艺做出自己的改进（重写）。

```java
class Person {
    String name;
    String hello() { return "我是" + name; }
}

class Student extends Person {
    String school;
    @Override
    String hello() { return "我是学生" + name; }   // 重写
}
```

**三条硬规矩：**

- 一个类只能有一个直接父类（单继承）。接口可以多实现，类不行。所有类往上追都是 `Object`。
- 构造器不被继承。子类构造器的第一行默认调 `super()`；父类没有无参构造器时，不写 `super(参数)` 就编译不过。
- 父类 `private` 的成员，子类看不见也用不了；`final` 类不能继承，`final` 方法不能重写。

**判断标准**：两个类是不是 is-a（"学生是人"）。如果是 has-a（"车有发动机"），用组合——把发动机当字段，别继承。

**项目对照**：`JwtAuthFilter extends OncePerRequestFilter`。框架类把"每个请求只过滤一次"的流程写好了，只留 `doFilterInternal` 这个缺口。

**坑**：继承层级别超过两三层；父类改一处，所有子类跟着受影响。

## 2. 向上转型与向下转型

- 子类对象赋值给父类变量，自动完成，叫向上转型：`Person p = new Student();` 永远安全，因为学生一定是人。
- 反过来把父类变量赋给子类变量，叫向下转型，必须写 `(Student)` 强转，编译器才放行。
- 强转能不能成功，看**实际对象**：`p` 指向的真是 Student 就成功，真是 Person 就抛 `ClassCastException`。
- 不写括号直接 `Student s = new Person();`，编译就报"不兼容的类型"，根本跑不起来。

**先判断再强转：**

```java
if (o instanceof Person p) {   // Java 16+ 写法，判断通过顺带把 o 转好
    return this.name.equals(p.name);
}
```

`instanceof` 对 null 永远返回 false，所以 equals 里不用另外判空。

**项目对照**：`AuthFilter` 里 `(HttpServletRequest) request` 是父接口转子接口。安全的原因只有一个：Tomcat 传进来的实际对象本来就是 `HttpServletRequest`。

## 3. 重载与重写

签名 = 方法名 + 参数列表（类型、个数、顺序）。返回值不算签名。

| | 重载 overload | 重写 override |
|---|---|---|
| 发生位置 | 同一个类里，或父子类之间 | 子类里 |
| 要求 | 名字相同，参数不同 | 名字相同，参数相同，返回值相同或是子类型 |
| 什么时候定 | 编译时按实参类型挑 | 运行时按实际对象挑 |
| 干什么用 | 一个名字多种用法，省名字 | 换掉父类的实现 |

```java
class Calc {
    String add(int a, int b) { return "int"; }
    String add(double a, double b) { return "double"; }
}
```

`@Override` 是给编译器的检查：签名对不上就报错，防止"想重写、结果写成了重载"。

## 4. 多态

一句话：同一个调用，落到不同对象上，执行不同的实现。

生活类比：老板喊"发货"，顺丰员工按顺丰发，中通员工按中通发。老板只喊一句，不用知道来的是谁。

**三要素，缺一不可：**

1. 有父子关系，或实现了同一个接口；
2. 子类重写了方法；
3. 父类引用指向子类对象。

```java
Animal a1 = new Dog();
Animal a2 = new Cat();
a1.speak();   // 汪
a2.speak();   // 喵
```

**判断标准**：方法看实际对象，字段看声明类型。多态只对"可被重写的实例方法"生效；字段、static 方法、final 方法、private 方法都不参与。

**好处**：调用方只认父类或接口，将来加新实现，调用方一行不用改。

**项目对照**：`List<String> list = new ArrayList<>()`；`userRepository.findByUsername(...)` 声明的是接口，运行时落到 Spring 生成的实现上；过滤器链上每个 Filter 都实现 `doFilter`，Tomcat 挨个调，各干各的。

## 5. 抽象类

一句话：写了一半的类。不能直接 `new`，但可以有字段、构造器、普通方法，也可以留几个没写的"抽象方法"逼子类填。

生活类比：图纸。图纸本身不是机器，但每台机器按图纸造出来，图纸上规定的零件都有。

```java
abstract class Shape {
    protected String color;                              // 共有状态
    Shape(String color) { this.color = color; }
    String describe() { return color + "的" + name(); }  // 共用逻辑
    abstract double area();                              // 留给子类
    abstract String name();
}
class Circle extends Shape {
    double r;
    Circle(String color, double r) { super(color); this.r = r; }
    @Override double area() { return Math.PI * r * r; }
    @Override String name() { return "圆形"; }
}
```

**为什么抽象类要定义实例字段**：它自己不能 new，但子类能 new；`new Circle("红", 2)` 造出来的对象里，Circle 的 `r` 和 Shape 的 `color` 都在。字段是给子类对象用的，`super(color)` 负责赋值。

**判断标准**：几个子类需要同一份状态、同一段逻辑，又各有各的差异，用抽象类。

**项目对照**：`OncePerRequestFilter` 是抽象类，字段和 `doFilter` 的模板都写好了，`doFilterInternal` 留给你。直接 `new Shape(...)` 编译报"是抽象的，无法实例化"。

**坑**：抽象方法子类必须实现；不实现，子类自己也必须声明成抽象类。

## 6. 接口

一句话：一份"必须做到什么"的合同，不规定内部怎么做。

生活类比：插座标准。只要插头符合国标，什么电器都能插；你不用知道电器内部电路。

```java
public interface Filter {
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException;
}
```

- 类用 `implements` 实现接口，要交出所有抽象方法；少一个就编译不过。
- 一个类可以实现多个接口；接口之间也能 `extends` 多个接口。
- 接口里的字段只能是 `public static final` 常量；Java 8 起可以有 `default` 方法（带默认实现，子类可选重写）。

**抽象类 vs 接口：**

| | 抽象类 | 接口 |
|---|---|---|
| 能不能 new | 不能 | 不能 |
| 实例字段 | 可以有 | 不能，只有常量 |
| 构造器 | 有，子类 `super()` 调 | 没有 |
| 方法实现 | 可以写好普通方法 | Java 8 前全是抽象；现在可有 default |
| 数量 | 类只能继承一个 | 类可以实现多个 |
| 表达 | is-a，"是什么" | can-do，"能做什么" |
| 什么时候用 | 有共享状态和逻辑 | 只要行为约定 |

**项目对照**：`AuthFilter implements Filter` 必须写 `doFilter`；`UserRepository extends JpaRepository<User, Long>` 只写了方法名，Spring 运行时生成实现——接口 + 多态 + 动态代理。`Runnable` 也是接口，匿名类和 lambda 都是它的实现。

## 7. 包

一句话：给类分组的文件夹，同时是命名空间。

生活类比：楼里的房间号。同一个小区（根包）下，301 和 302 里都可以有个"张三"，报全名（全类名）才不混淆。

- 包名反写域名 + 项目名：`com.example.jwt_lab`。全类名 `com.example.jwt_lab.User`。
- 目录必须和包名一一对应：`src/main/java/com/example/jwt_lab/User.java`。
- 同包的类互相直接用；不同包要 `import`。
- 权限里的"包内可见"（不写修饰符）就是以包划范围。

**项目对照**：启动类上的 `@SpringBootApplication` 只扫自己所在的包及子包。类放到 `com.example.jwt_lab` 外面，接口就 404——这个坑之前踩过。

## 8. 内部类

内部类就是写在另一个类里面的类。三种常见形态：成员内部类、静态内部类、匿名内部类。

### 8.1 成员内部类

写在类里，位置跟普通字段一样，前面不加 `static`。

```java
class Outer {
    private String name = "outer";
    class Inner {                       // 成员内部类
        String read() { return name; }  // 直接访问外部 private
    }
}
// 创建：必须先有外部对象
Outer o = new Outer();
Outer.Inner i = o.new Inner();
```

它持有外部对象的引用，所以能直接用外部字段和方法。

### 8.2 静态内部类

加了 `static` 的内部类。

```java
class Outer {
    private static String version = "1.0";
    static class Config {                  // 静态内部类
        String read() { return version; }  // 只能访问外部 static 成员
    }
}
// 创建：不需要外部对象
Outer.Config c = new Outer.Config();
```

它不持有外部对象引用，所以只能碰外部的 static 成员，`Outer.this` 也不能用。

**项目对照**：`TokenStore.Entry` 就是 `public static class Entry`。`Entry` 只是个装数据的小结构，不需要回头看 `TokenStore` 对象，所以用静态内部类。

### 8.3 匿名内部类

没有名字，定义的同时就 `new` 出对象。

```java
class Outer {
    private String name;
    Outer(String name) { this.name = name; }

    void asyncHello() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello, " + Outer.this.name);
            }
        };
        new Thread(r).start();
    }
}
```

- `new` 后面跟接口名或父类名，花括号里写实现，最后分号别丢。
- 编译器给它起编号：Outer 里第一个匿名类叫 `Outer$1`。类是真的有，只是没名字。
- 能访问外部 private，因为它持有外部对象的引用。
- 只有一个抽象方法的接口可以简写成 lambda：`Runnable r = () -> System.out.println("Hi");`

**项目对照**：`SecurityConfig` 里 `.csrf(csrf -> csrf.disable())` 这些 lambda，本质就是匿名内部类的简写。

**三种内部类对比：**

| | 成员内部类 | 静态内部类 | 匿名内部类 |
|---|---|---|---|
| 有没有名字 | 有 | 有 | 没有 |
| 持有外部引用 | 是 | 否 | 是 |
| 能访问外部实例成员 | 能 | 只能 static | 能 |
| 创建方式 | `o.new Inner()` | `new Outer.Inner()` | `new 接口或父类() {...}` |
| 典型用途 | 紧贴外部对象的辅助逻辑 | 独立的小结构（如 Entry） | 用一次就扔的实现 |

## 9. 自测

1. `Person p = new Student();` 和 `Student s = (Student) p;` 各自什么时候安全，什么时候会崩？
2. 重载和重写，怎么用一句话区分？
3. 多态的三要素是哪三条？为什么说"字段不参与多态"？
4. 抽象类不能 new，为什么还要定义实例字段和构造器？
5. 什么时候用抽象类、什么时候用接口？各举一个项目里的例子。
6. 包名 `com.example.jwt_lab` 对应哪个目录？类放错包会怎样？
7. `TokenStore.Entry` 为什么写成 static？去掉 static 会有什么变化？
8. 匿名类的 `Outer.this.name` 里的 `Outer.this` 指什么？

## 10. 下一步

- 项目练习还挂着：`User` 字段改 `private` + 加构造器，`DataSeeder` 里三行赋值改成一行。做完再往下走。
- 下一站按学习清单第 2 周：HTTP 与 Filter。
