# Java + Spring Boot 后端实习项目路线图

> 面向：软件工程专业，无过硬项目，目标 Java 后端实习
> 制定日期：2026-11-06
> 项目数据均来自 GitHub API 实时查询（截至 2026-11-06）

---

## 目录

- [一、核心认知：先纠正一个思维误区](#一核心认知先纠正一个思维误区)
- [二、硬件与资源盘点](#二硬件与资源盘点)
- [三、项目总清单（由易到难）](#三项目总清单由易到难)
- [四、三条执行路线](#四三条执行路线)
- [五、通用方法论：如何"吃透"和"变成自己的"](#五通用方法论如何吃透和变成自己的)
- [六、简历怎么写](#六简历怎么写)
- [七、避坑清单](#七避坑清单)
- [八、部署与运维](#八部署与运维)
- [九、执行检查表](#九执行检查表)

---

## 一、核心认知：先纠正一个思维误区

### 你的原始想法

> "找一个完整的项目，吃透它，写成自己的项目。"

### 对了一半

**对的**：没有项目，确实需要先有一个能讲的项目。这是最省力的起点。

**不对的**：**"把别人的项目吃透后当成自己的"，在面试里会被看穿，代价极高。**

原因：

1. **深挖会露馅** —— 面试官问"这个模块为什么这么设计""你当时踩了什么坑"，原作者的回答深度和你不是一回事
2. **见过的人太多** —— 热门学习型项目（RuoYi 系列、各种 mall）面试官看过无数遍
3. **最致命的是** —— 你讲不出"为什么"，因为这些决策不是你做的

### 正确的目标

> **不是"我有一个项目"，而是"我亲手解决过几个真实问题"。**

这两件事可以同时做到：**拿开源项目当底座和脚手架，在它上面做只有你能讲的增量。**

---

## 二、硬件与资源盘点

| 设备 | 配置 | 用途定位 |
|---|---|---|
| **MacBook Air M1** | 8GB 内存 | 日常敲代码、写文档。**跑不了重项目** |
| **游戏本** | 性能足够（配置待确认） | **主力开发机**：跑 Damai 这类 565 文件的微服务集群 |
| **腾讯云服务器** | 2核2G / 50GB / Ubuntu，IP `49.233.132.110`，2027-10-22 到期 | **部署演示**：给面试官一个能点开的线上地址 |

### 云服务器实测结果（2026-11-03）

```
22  端口  OPEN     ← SSH 可用
80  端口  OPEN
443 端口  关闭     ← 还没配 HTTPS
8080 端口 关闭     ← 安全组未放行
ping 延迟 30-70ms  （腾讯云大陆节点）
```

### ⚠️ 关键风险

**国内云服务器（大陆节点）的 80/443 端口，域名未备案会被拦截。**

- 大陆节点 → 需要域名备案才能用 80/443；否则只能用 `http://IP:8080` 演示
- 境外节点（香港/新加坡）→ 无备案要求，80/443 直接可用

**建议做法**：买个域名（10-30 元/年），要么备案，要么用非标准端口先演示。

### 服务器配置能力对照

| 项目 | 2核2G 能否跑 | 说明 |
|---|---|---|
| spring-boot-online-exam | ✅ 轻松 | MySQL + Redis + 应用 + Nginx |
| AI-Meeting | ✅ 可以 | 需给 JVM 限内存 `-Xmx512m` |
| novel | ⚠️ 勉强 | MySQL 限内存，JVM `-Xmx512m` |
| interview-guide | ⚠️ 勉强 | 需要 pgvector |
| damai | ❌ 跑不动 | 8 个微服务 + Kafka + ES，**必须升配到 4核8G** |

---

## 三、项目总清单（由易到难）

共 **11 个**，分 4 个难度档。

### 🟢 第 0 档：热身级（1 天内跑通）

**目标：先熟悉 Spring Boot 骨架，再碰任何大项目。**

| # | 项目 | Star | 规模 | 协议 | 技术点 |
|---|---|---|---|---|---|
| **0** | [murraco/spring-boot-jwt](https://github.com/murraco/spring-boot-jwt) | **1.7k** | 极简（0MB） | ✅ MIT | Spring Security + JWT + MySQL |

**说明**：就一个 JWT 认证服务。文件极少，但把"登录 → 签发 Token → 拦截校验"整条链路讲透了。**校招面试必考，先读熟这个再读任何大项目都不慌。**

**完成标准**：能不看代码画出 JWT 校验流程图，说清 `OncePerRequestFilter` 在链路中的位置。

---

### 🔵 第 1 档：入门级（30-100 个 Java 文件 / 3-7 天读完）

**目标：第一个完整项目，100% 读懂每一个文件。**

| # | 项目 | Star | Java 文件 | 协议 | 技术栈 |
|---|---|---|---|---|---|
| **1** | [lsgwr/spring-boot-online-exam](https://github.com/lsgwr/spring-boot-online-exam) | **1.7k** | **32** | ✅ MIT | Spring Boot + Spring Security + JWT + MySQL + Vue |
| **2** | [lishuangqiang/AI-Meeting](https://github.com/lishuangqiang/AI-Meeting) | **1.1k** | **349** | ✅ MIT | Spring Boot 3 + Java 17 + Spring AI + MySQL + MongoDB + Redis + SSE/WebSocket |

**项目 1 特点**：
- **规模最小且又完整的项目**，业务清晰（考试 / 题库 / 判分）
- 自带 `docker-compose.yml`、`doc/` 下 66 个文档
- 包结构：`api/config/dto/entity/repo/security/service/web`
- **最适合作为"第一个从头读到尾"的项目**

**项目 2 特点**：
- Java 占比 98%，纯后端，**不用学前端**
- **工程素养最高**：有 `CONTRIBUTING.md`、`SECURITY.md`、`CODE_OF_CONDUCT.md`、`.github/` 工作流
- 有真实外部 PR（`LazySheep-dev` 的 `fix/runtime-rehydrate`），更容易找到真实 bug
- 功能：AI 对话、智能体、AI 模拟面试、**实时语音转写**、长文本语音合成、Docker 一键部署
- 技术点全是热点：Spring AI、SSE 流式输出、WebSocket 实时、MongoDB 存对话

**怎么选**：
- 想"先把一个完整系统吃透" → 选 **1**
- 想"1 个月出成果投简历" → 选 **2**

---

### 🟡 第 2 档：进阶级（150-300 个 Java 文件 / 2-4 周）

**目标：接触真实工业架构，但仍可控。**

| # | 项目 | Star | Java 文件 | 协议 | 核心看点 |
|---|---|---|---|---|---|
| **3** | [201206030/novel](https://github.com/201206030/novel) | **5.8k** | **190** | ✅ Apache-2.0 | 工程结构范本（详见下） |
| **4** | [jobmission/oauth2-server](https://github.com/jobmission/oauth2-server) | 547 | **97** | ✅ MIT | OAuth2 / SSO 单点登录认证中心 |
| **5** | [dromara/ujcms](https://github.com/dromara/ujcms) | 670 | 中等 | ⚠️ GPL-3.0 | Lucene 全文检索 + FreeMarker |
| **6** | [tianshiyeben/wgcloud](https://github.com/tianshiyeben/wgcloud) | **5.2k** | 中等 | ✅ Apache-2.0 | Linux 运维监控平台 |
| **7** | [javahuang/SurveyKing](https://github.com/javahuang/SurveyKing) | **4.0k** | 大 | ✅ MIT | 对标 SurveyMonkey 的问卷系统 |

#### 项目 3：novel（**强烈推荐**）

**技术栈**：Spring Boot 3 + Vue 3 + MyBatis-Plus + Redis + MySQL

**包结构 —— 标准工业界范本**：
```
io/github/xxyopen/novel/
├── controller/
│   ├── author/          ← 作家端
│   └── front/           ← 读者端
├── core/
│   ├── annotation/      ← 自定义注解
│   ├── aspect/          ← AOP 切面
│   ├── auth/            ← 认证
│   ├── config/          ← 配置
│   ├── filter/          ← 过滤器
│   ├── interceptor/     ← 拦截器
│   ├── listener/        ← 事件监听
│   ├── task/            ← 定时任务
│   └── wrapper/         ← 包装器
├── manager/
│   ├── cache/           ← 缓存管理
│   ├── dao/             ← 数据访问
│   ├── message/         ← 消息
│   ├── mq/              ← 消息队列
│   └── redis/           ← Redis 封装
├── dao/ (entity, mapper)
├── dto/ (req, resp, es)
└── service/ (impl)
```

**为什么它是第 2 档的"必读"**：这套 `controller / core / manager / dao / dto / service` 分层是**所有工业级 Java 项目的通用骨架**。读透它之后再看任何微服务项目，你会发现"原来就是把这些东西拆成独立服务"。

**其他信息**：Apache-2.0 可商用修改，最近提交 2026-11-01（极其活跃），`doc/` 下有保姆级教程

---

### 🔴 第 3 档：挑战级（280-565 个 Java 文件 / 1-2 个月）

**有游戏本才能选这一档。这是"能不能在 Java 后端校招里拉开差距"的分水岭。**

| # | 项目 | Star | Java 文件 | 协议 | 核心看点 | 风险 |
|---|---|---|---|---|---|---|
| **8** | [Snailclimb/interview-guide](https://github.com/Snailclimb/interview-guide) | **3.5k** | **280** | ⚠️ **AGPL-3.0** | Java 25 + Spring AI 2.0 + pgvector 向量检索 | AGPL 传染性；技术栈太新 |
| **9** | [a466350665/smart-sso](https://github.com/a466350665/smart-sso) | **2.3k** | 中等（42MB） | ✅ MIT | 跨域 + 分布式 SSO，1218 fork / 49 issue | 领域偏专精，业务面窄 |
| **10** | [continew-org/continew-admin](https://github.com/continew-org/continew-admin) | **2.2k** | 中等（28MB） | ✅ Apache-2.0 | 提交质量最高 | 属于"脚手架类"，业务薄 |
| **11** | [java-up-up/damai](https://github.com/java-up-up/damai) | **1.2k** | **565** | ✅ Apache-2.0 | 🏆 高并发抢票系统 | 复杂度暴力碾压 |

#### 项目 11：damai（**第 3 档首选**）

**技术栈**：Spring Cloud + Kafka + Redis + Sentinel + ElasticSearch + ShardingSphere

**模块结构**：
```
damai-server/                        ← 业务微服务群
  ├── damai-order-service            (订单 - 高并发核心)
  ├── damai-pay-service              (支付 - 幂等/对账)
  ├── damai-program-service          (演出 - 库存)
  ├── damai-user-service             (用户)
  ├── damai-gateway-service          (网关)
  └── damai-admin-service            (后台)
damai-common/
damai-captcha-manage-framework/      ← 验证码（防刷）
damai-id-generator-framework/        ← 分布式 ID
damai-redisson-framework/            ← 分布式锁
damai-elasticsearch-framework/
damai-thread-pool-framework/
damai-spring-cloud-framework/
```

**为什么最值得选**：

1. **业务场景天然带难度** —— 秒杀 / 抢票天然涉及**超卖、幂等、限流、缓存一致性**，全是面试官最爱问的题，且你能拿真实代码举证
2. **技术点拆成独立 framework 模块** —— 分布式锁、ID 生成、验证码、线程池，读源码等于把 Java 高并发核心组件全学一遍
3. **能量化** —— 项目自身做了压测（下单延迟优化、多级缓存、限流策略），你可以自己复现压测，**简历上写"QPS 从 X 提升到 Y"有据可查**
4. **稀缺性** —— 1.2k star 不算烂大街，比 RuoYi 系列好太多

**缺点**：微服务架构，8 个服务 + 网关 + 一堆中间件，上手慢（约 1 个月才跑顺）

**入门路径（重要）**：不要一上来跑全部服务。先跑 **order + program + gateway 三个核心服务**。

#### 项目 8/9/10 补充说明

- **interview-guide**：Snailclimb 是《JavaGuide》作者，**在 Java 圈有很高认知度**，懂行面试官会认得。技术最前沿（Spring Boot 4.1 + Java 25 + pgvector）。有 `AGENTS.md`、`CLAUDE.md`、`.githooks` —— **AI 辅助开发时代的工程实践，很新，可以讲**。⚠️ AGPL 协议：学习/简历/私有部署没问题，**不要拿去商用或暗示是自己的原创产品**。
- **smart-sso**：跨域 + 分布式 SSO（OAuth 2.0），社区活跃（1218 fork / 49 open issue），有真实 issue 可修。
- **continew-admin**：提交质量是这批里最高的，能看到真实安全修复（如 `fix: UserContext 线程本地跨请求残留导致的越权 #249`）—— **这种提交本身就是很好的学习材料**。但属于脚手架，业务代码薄。

---

### 📈 难度总览

```
难度 ↑
  ↑
第3档 │  damai ★                   ← 565 文件，高并发天花板
      │  interview-guide           ← 280 文件，技术最前沿
      │  smart-sso / continew      ← 专业领域
      │
第2档 │  novel ★                   ← 190 文件，工程结构范本
      │  SurveyKing / wgcloud / ujcms / oauth2-server
      │
第1档 │  AI-Meeting ★★             ← 349 文件但纯 Java，上手快
      │  spring-boot-online-exam ★★★  ← 32 文件，最小最完整
      │
第0档 │  spring-boot-jwt ★★★        ← 极简，先练手
      └────────────────────────────→ 学习收益
```

---

## 四、三条执行路线

### 路线 A：稳扎稳打（3 个月）—— **最推荐**

```
第 0 档  jwt        (1 天）
   ↓
第 1 档  exam       (1 周）
   ↓
第 2 档  novel      (1 个月）
   ↓
第 3 档  damai      (挑 2 个服务深挖，剩余时间）
```

**适合**：完全没项目基础
**优势**：每一步都有完整交付物，简历可以写三段递进经历
**核心理由**：novel 的工程结构是所有工业级 Java 项目的通用骨架，读透后再看 damai 的微服务，学习成本大幅下降

### 路线 B：快速出成果（1-1.5 个月）

```
第 0 档  jwt        (1 天）
   ↓
第 1 档  AI-Meeting (3 周）
   ↓
部署到腾讯云 → 做 2 个增量功能
```

**适合**：近期就要投简历
**成果**：1 个月拿到一个能点开的线上 AI 系统

### 路线 C：差异化冲击大厂后端（2 个月）

```
第 0 档  jwt        (1 天）
   ↓
第 1 档  exam       (3 天，快速过）
   ↓
第 3 档  damai      (1.5 个月，主攻）
```

**适合**：目标腾讯 / 阿里 / 字节 Java 后端
**前提**：必须先读完基础项目，否则读 damai 会看不懂

### 路线对比

| 维度 | 路线 A | 路线 B | 路线 C |
|---|---|---|---|
| 周期 | 3 个月 | 1-1.5 个月 | 2 个月 |
| 学习曲线 | 平滑 | 陡 | 陡 |
| 简历深度 | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |
| 面试难度应对 | 全面 | AI 方向 | 高并发方向 |
| 失败风险 | 低 | 中 | 高 |

---

## 五、通用方法论：如何"吃透"和"变成自己的"

**不管选哪个项目，流程都一样。**

### 第 1 步：跑起来（1-2 天）

照着 README 跑通。

**这一步会暴露 5-10 个真实问题**（依赖版本、环境变量、SQL 脚本、端口冲突）—— **每一个都是面试素材，务必全部记录**。

**硬门槛**：如果 15 分钟内在本机跑不起来，换项目或先解决环境问题。

### 第 2 步：修真实 Issue（1 周）

去项目的 Issues 里挑：
- `good first issue` 标签
- 或"文档错误 / 边界条件 bug"

**修完提 PR 最好** —— 哪怕没合并，手里就有一个"真实开源协作经历"。

**价值**：讲的是"我发现 XX 场景下 XX 逻辑有 bug，因为 XX，我这样改"—— **任何面试官都愿意听的深度**。

### 第 3 步：做 2 个自己的增量功能（2-3 周）

这才是"写成自己的项目"的正确姿势。**必须同时满足四条**：

1. 原项目**没有**这个功能（不是你编的）
2. 能说清**为什么需要**（用户/场景层面，不是"我想加个功能"）
3. 能说清**为什么这样实现**（对比过至少一个替代方案）
4. 有可展示的东西（截图 / 性能数据 / 交互演示）

**示例**（教务/选课类系统）：
- 原项目只有"选课"
- 你加"选课冲突自动检测 + 退课时候补队列"
- 结果：你懂业务、懂并发、懂边界条件 —— **这才是真正的 project，而不是 fork**

**示例**（damai 类抢票系统）：
- 原项目有基础限流
- 你加"基于用户行为的动态限流 + 黑名单自动降级"
- 结果：有压测数据对比，能讲清楚触发阈值怎么定

### 第 4 步：部署上线

部署到腾讯云，拿到一个**能访问的地址**。这在校招里的加分**远超一个 GitHub 链接**（详见第八节）。

---

## 六、简历怎么写

### ❌ 不要这样写

```
独立开发 XX 系统（基于 Spring Boot）
```

### ✅ 要这样写

```
基于开源项目 XXX（GitHub 1.2k star）进行二次开发：
修复 N 个边界 bug（含 PR 链接），设计并实现了 YY 模块，
解决了 ZZ 场景下的并发/性能问题，QPS 从 A 提升到 B。
项目演示：https://xxx.your-domain.com
```

### 这样写的好处

| 特质 | 体现 |
|---|---|
| **诚实** | 明确说明是二次开发 |
| **有深度** | 有数据、有 PR、有量化指标 |
| **可验证** | 面试官点链接就能看 |

**比假装"独立开发"强一百倍，而且反而更容易拿到信任。**

---

## 七、避坑清单

### ⛔ 主动排除的项目（我筛的时候已经踢掉）

| 类别 | 举例 | 为什么避开 |
|---|---|---|
| **微服务全家桶** | RuoYi-Cloud、matecloud、lamp-cloud、novel-cloud | 校招 **Spring Cloud 不是重点**，把单体讲透更有价值；配置复杂容易劝退 |
| **低代码 / 快速开发平台** | RuoYi-Vue-Plus、continew-admin(注)、diboot、mfish-nocode | **业务代码少，框架代码多**，学不到东西；面试官会问"这不就是脚手架吗" |
| **纯脚手架 / 组件库** | mini-spring、skrstop-components、dynamic-tp | 这是研究型项目，不是"完整系统"，简历上没法写业务 |
| **超大项目** | PublicCMS（177MB）、skyeye（234MB）、erp-pro（222MB） | 光 clone 就慢，读不完 |
| **已停更** | newbee-mall（最后 2025-10）、spring-boot-plus（最后 2024-02） | 技术栈过时，学不到现代工程实践 |

> 注：`continew-admin` 虽在排除类别里，但因其提交质量特别高，保留在第 3 档并标注了风险。

### ⚠️ 协议注意事项

| 协议 | 能干什么 | 不能干什么 |
|---|---|---|
| **MIT / Apache-2.0** | 随便改、随便商用、可闭源 | 几乎无限制（Apache 需保留版权声明） |
| **GPL-3.0** | 可改、可自用 | 分发时必须开源你的修改 |
| **AGPL-3.0** | 可改、可自用 | **只要通过网络提供服务，就必须开源**。⚠️ interview-guide 就是这个 |

### 🔐 服务器安全须知

1. **22 端口对外暴露** —— 扫站机器人早就在扫了，必须配 SSH 密钥登录 + 禁用密码登录
2. 建议装 **fail2ban** 防暴力破解
3. 云服务器安全组只放行必要端口（22 / 80 / 443 / 8080）
4. 定期 `apt upgrade`

---

## 八、部署与运维

### 为什么云服务器价值很大

1. **8GB M1 的限制消失** —— 重项目放服务器
2. **有了可访问的线上地址** —— 校招最大加分项，面试官当场能点开验证
3. **本地开发 + 服务器部署** —— 标准工程流程，本身就能讲

### 部署清单（选项 A：我来帮你全配）

- [ ] 装 JDK 21、Maven、MySQL 8、Redis、Nginx
- [ ] 配置 SSH 密钥登录、禁用密码登录
- [ ] 配置防火墙 + fail2ban
- [ ] 部署项目，跑通
- [ ] 配置 Nginx 反向代理
- [ ] HTTPS 证书（Let's Encrypt 免费）
- [ ] systemd 服务守护（开机自启、崩溃重启）

### 部署清单（选项 B：给你步骤自己敲）

每个命令带注释和坑点。适合想学部署过程的。

### 需要确认的信息

1. 服务器 SSH 登录方式（用户名 + 密码 / 密钥文件路径）
2. Ubuntu 版本（登录后 `lsb_release -a`）
3. 安全组开了哪些端口
4. 有没有域名

---

## 九、执行检查表

### 准备阶段

- [ ] 确定目标岗位（后端 / 全栈 / AI）
- [ ] 确认游戏本配置（内存 / Windows or macOS / 有无 WSL）
- [ ] 确认距离投简历还有多久
- [ ] 确认云服务器是否有域名、是否备案

### 学习阶段（以路线 A 为例）

- [ ] **第 0 档**：读完 spring-boot-jwt，能手画 JWT 校验流程
- [ ] **第 1 档**：exam 项目 32 个 Java 文件全部读完，记录跑通过程中的每个问题
- [ ] **第 1 档**：修 3 个 exam 的 issue，提 PR
- [ ] **第 2 档**：novel 跑通，画出核心链路（登录 → 阅读 → 缓存）
- [ ] **第 2 档**：修 3-5 个 novel 的真实 issue
- [ ] **第 2 档**：给 novel 加 2 个自己的功能
- [ ] **第 3 档**：damai 只跑 order + program + gateway 三个服务
- [ ] **第 3 档**：复现压测，记录优化前后数据

### 交付阶段

- [ ] 项目部署到腾讯云，拿到可访问地址
- [ ] 配好 HTTPS（或先用非标准端口）
- [ ] 写 3 段简历描述（对应三个项目）
- [ ] 准备面试讲述稿：每个项目 3 分钟版本 + 10 分钟深挖版本

### 面试准备（每个项目都要能回答）

- [ ] 这个功能为什么这么设计？
- [ ] 你踩过什么坑？怎么解决的？
- [ ] 如果流量翻 10 倍会怎么样？
- [ ] 这个模块如果让你重写，会怎么改？
- [ ] 你对比过哪些替代方案？为什么选这个？

---

## 附：快速决策表

| 你的情况 | 推荐 |
|---|---|
| 完全没基础，时间充裕（3 个月） | **路线 A**：jwt → exam → novel → damai |
| 时间紧（1-1.5 个月），想快速出成果 | **路线 B**：jwt → AI-Meeting → 部署 |
| 目标大厂后端，有 2 个月 | **路线 C**：jwt → exam → damai |
| 想专精认证授权方向 | 路线 A 变体：jwt → oauth2-server → smart-sso |
| 想学搜索引擎 | 路线 A 变体：jwt → exam → ujcms |
| 想差异化（没人做的方向） | 路线 A 变体：jwt → wgcloud（运维监控） |
| 对 AI 方向感兴趣 | AI-Meeting 或 interview-guide |
| 想快速补知识点而非做项目 | javahongxi/whatsmars（技术栈示例集合） |

---

*本文档数据来源：GitHub API 实时查询，所有 Star 数、最近提交时间、语言占比均截至 2026-11-06。*