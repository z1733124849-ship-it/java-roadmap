# Java 基础笔记 1：类型、传参、修饰符

2026-11-17　对照项目：`jwt-lab`

---

## 1. 基本类型 vs 引用类型

**基本类型只有 8 个**，变量里直接装数值：

```
byte short int long float double char boolean
```

**其余都是引用类型**：类、接口、数组、`String`、`Map`、`User`、`Entry`。

引用类型的变量里装的不是对象本身，是"对象在哪"（门牌号）。对象本体在堆内存里。

### 三个实际差别

**① 能不能是 null**

```java
User user = null;   // 可以
int n = null;       // 编译不过
```

**② 传参时**

- 基本类型：传数值的副本，方法里改了，外面不变
- 引用类型：传门牌号的副本。照着复印件去改房子里的家具，房主看得见；换复印件上的门牌号，房主不知道

**③ `==` 的意思不同**

- 基本类型：比数值
- 引用类型：比是不是同一个对象 → 所以字符串必须用 `equals`

**项目对照**

- `Entry.issuedAt` 是 `long`（基本类型）
- `Entry.username` 是 `String`（引用类型）

---

## 2. 引用传参：Bob → Alice

```java
Person p = new Person();
String bob = "Bob";
p.setName(bob);
System.out.println(p.getName());  // "Bob"

bob = "Alice";
System.out.println(p.getName());  // 还是 "Bob"
```

**为什么不是 Alice**

- `p.setName(bob)` 复制的是地址，`p.name` 和 `bob` 指向同一个 "Bob"
- `bob = "Alice"` 不是改 "Bob"，是让 `bob` 指向新的 "Alice"
- `p.name` 原地不动

**反例（可变对象）**

```java
List<String> list = new ArrayList<>();
p.setList(list);
list.add("Alice");     // 这会改到 p 里那个 list
```

因为 `add` 改的是**对象内容**，不是改指向。

### 一句话记法

传参传的是门牌号的复印件。

- 照着复印件进去改家具 → 房主看得见（可变对象）
- 把复印件上的门牌号改掉 → 房主不知道（重新赋值）

---

## 3. 访问修饰符（四个）

| 写法 | 谁能访问 |
|---|---|
| `public` | 任何地方 |
| `protected` | 同包 + 子类 |
| 不写（默认） | 只有同包 |
| `private` | 只有本类 |

范围：`public` > `protected` > 默认 > `private`。

**项目对照**

- `TokenStore.TOKENS` 是 private：外面碰不到表，只能走 `save` / `getUsername`
- `Entry.username` 是 public：所以别的类能直接读
- `SecurityConfig` 是 public class：Spring 要扫描到

---

## 4. static / final / abstract

### static：属于类，不属于对象

```java
TokenStore.save(token, username);   // 直接类名调，不用 new
```

**项目对照**：`TOKENS`、`SECRET`、`TTL`、`save`、`getUsername`。

**为什么必须 static**：不是 static 的话，每次 `new TokenStore()` 都是一张新表，登录存进 A 表、过滤器查 B 表，永远查不到。

**坑**：static 方法里不能用 `this`，也不能直接用实例字段。

### final：不能再改

| 加在哪 | 意思 |
|---|---|
| 变量 | 不能重新赋值 |
| 方法 | 子类不能重写 |
| 类 | 不能被继承（`String` 就是） |

**最容易误会**：final 锁的是"指向"，不是内容。

```java
private static final Map<String, String> TOKENS = new HashMap<>();
TOKENS.put("a", "b");        // 可以
TOKENS = new HashMap<>();    // 报错
```

### abstract：做了一半，等子类填

- 抽象方法：只有声明，没有 `{}`
- 抽象类：含抽象方法就必须 `abstract`，不能直接 new

**项目对照**：`JwtAuthFilter extends OncePerRequestFilter`。

`OncePerRequestFilter` 是抽象类，它把 `doFilter` 写好了（保证一次请求只跑一次），只留下 `doFilterInternal` 让你实现。不实现就编译不过，所以你的类上有 `@Override`。

**接口 vs 抽象类**：接口是"必须做到什么"的合同；抽象类是"已经帮你做了一半"的父类。`Filter` 是接口，`OncePerRequestFilter` 是抽象类，后者实现了前者。

### 三个一起出现

```java
public static final String SECRET = "...";
```

`public` 管谁能看，`static` 管归类还是归对象，`final` 管能不能再改。互不冲突。

---

## 5. 自测（先别看答案，自己想）

1. `int a = 1;` 和 `String s = "a";` 两个变量里，分别装的是什么？
2. 下面这段，最后打印什么？为什么？

```java
List<String> names = new ArrayList<>();
names.add("Bob");
p.setNames(names);
names.add("Alice");
System.out.println(p.getNames().size());
```

3. `private` 和"不写修饰符"有什么区别？
4. `static` 方法里为什么不能用 `this`？
5. 为什么 `OncePerRequestFilter` 里必须由我们实现 `doFilterInternal`？

---

## 下一步学什么

按 `labs/学习清单.md` 第 1 周往下走：封装、构造器、this，然后是接口与多态。

对应的项目练习：

- 给 `User` 加构造器 `User(String username, String password)`，把 `DataSeeder` 里三行赋值改成一行
- 把 `TokenStore` 的 `save`/`getUsername` 改成实例方法（去掉 static），在 `main` 里 new 出来调用
