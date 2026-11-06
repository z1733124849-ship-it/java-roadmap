# Java 后端学习路径

## 项目清单

按难度排序。星数和活跃度数据取自 2026-11-06。

### 入门

| 项目 | 星数 | Java 文件 | 协议 | 技术栈 |
|---|---|---|---|---|
| [spring-boot-jwt](https://github.com/murraco/spring-boot-jwt) | 1.7k | 极简 | MIT | Spring Security + JWT + MySQL |
| [spring-boot-online-exam](https://github.com/lsgwr/spring-boot-online-exam) | 1.7k | 32 | MIT | Spring Boot + Spring Security + JWT + MySQL + Vue |
| [AI-Meeting](https://github.com/lishuangqiang/AI-Meeting) | 1.1k | 349 | MIT | Spring Boot 3 + Spring AI + MySQL + MongoDB + Redis + WebSocket |

spring-boot-jwt 只有一个认证服务，用来把登录、签发 token、拦截校验这条链路搞清楚。

spring-boot-online-exam 是最小且完整的项目，业务是考试、题库、判分。32 个 Java 文件，适合从头读到尾。

AI-Meeting 纯 Java 后端，不用碰前端。有 CONTRIBUTING、SECURITY 这类规范文件，说明工程习惯还行。功能包括 AI 对话、实时语音转写。

### 进阶

| 项目 | 星数 | Java 文件 | 协议 | 技术栈 |
|---|---|---|---|---|
| [novel](https://github.com/201206030/novel) | 5.8k | 190 | Apache-2.0 | Spring Boot 3 + Vue 3 + MyBatis-Plus + Redis |
| [oauth2-server](https://github.com/jobmission/oauth2-server) | 547 | 97 | MIT | Spring Boot 3 + OAuth2 + JWT |
| [ujcms](https://github.com/dromara/ujcms) | 670 | - | GPL-3.0 | Spring Boot + Lucene + FreeMarker |
| [wgcloud](https://github.com/tianshiyeben/wgcloud) | 5.2k | - | Apache-2.0 | Spring Boot + 运维监控 |
| [SurveyKing](https://github.com/javahuang/SurveyKing) | 4.0k | - | MIT | Spring Boot + TypeScript |

novel 的包结构值得单独看：

```
controller/author      作家端
controller/front       读者端
core/annotation        自定义注解
core/aspect            AOP
core/auth              认证
core/filter            过滤器
core/interceptor       拦截器
core/task              定时任务
manager/cache          缓存
manager/dao            数据访问
manager/mq             消息队列
manager/redis          Redis 封装
```

这套分层是大多数 Java 后端的通用结构，先读透再去碰微服务会轻松很多。

oauth2-server 专攻认证授权，97 个文件里大部分和 OAuth2 相关，带测试。

ujcms 用 Lucene 做全文检索，不想上 ElasticSearch 的话可以看它。

wgcloud 做服务器监控，采集 CPU、内存、磁盘、进程。这块内容做的人少。

SurveyKing 是问卷系统，完成度接近真实产品，但前端是 TypeScript，不熟的话工作量会翻倍。

### 挑战

| 项目 | 星数 | Java 文件 | 协议 | 技术栈 |
|---|---|---|---|---|
| [interview-guide](https://github.com/Snailclimb/interview-guide) | 3.5k | 280 | AGPL-3.0 | Spring Boot 4.1 + Java 25 + Spring AI + pgvector |
| [smart-sso](https://github.com/a466350665/smart-sso) | 2.3k | - | MIT | Spring Boot + OAuth2 单点登录 |
| [continew-admin](https://github.com/continew-org/continew-admin) | 2.2k | - | Apache-2.0 | Spring Boot 3.4 + Vue3 + Sa-Token |
| [damai](https://github.com/java-up-up/damai) | 1.2k | 565 | Apache-2.0 | Spring Cloud + Kafka + Redis + Sentinel + ES + ShardingSphere |

damai 是抢票系统，模块拆得比较干净：

```
damai-server/damai-order-service     订单
damai-server/damai-pay-service       支付
damai-server/damai-program-service   演出库存
damai-server/damai-user-service      用户
damai-server/damai-gateway-service   网关
damai-captcha-manage-framework       验证码
damai-id-generator-framework         分布式 ID
damai-redisson-framework             分布式锁
damai-thread-pool-framework          线程池
```

秒杀和抢票天然会碰到超卖、重复下单、限流这些问题，面试问得也多，而且有代码可以对着讲。项目自己做过量级测试，你可以复现。

不好跑，8 个服务加一堆中间件，别一上来全启。先跑 order、program、gateway 三个。

interview-guide 是《JavaGuide》作者的项目，圈内认识的人多。技术栈很新，用到了 Java 25 和向量检索。注意 AGPL 协议，自己学习没问题，线上对外提供服务就要开源修改。

continew-admin 的提交质量不错，能看到类似「修复 UserContext 线程本地跨请求残留导致的越权」这种真实修复，本身也是学习材料。但它偏脚手架，业务代码薄。

### 不建议选的

| 类型 | 例子 | 原因 |
|---|---|---|
| 微服务全家桶 | RuoYi-Cloud、matecloud、lamp-cloud | 校招重点在单体，配置复杂容易卡住 |
| 低代码平台 | RuoYi-Vue-Plus、diboot、mfish-nocode | 业务代码少，框架代码多 |
| 脚手架和组件库 | mini-spring、dynamic-tp | 不是完整系统，简历上没业务可写 |
| 超大项目 | PublicCMS、skyeye、erp-pro | 体积一两百 MB，读不完 |
| 停更项目 | newbee-mall、spring-boot-plus | 最后提交在 2024-2025 年，技术栈过时 |

---

## 三条路线

### 路线一：稳一点，三个月

```
spring-boot-jwt        1 天
    ↓
spring-boot-online-exam 1 周
    ↓
novel                  1 个月
    ↓
damai                  挑 order 和 program 两个服务深入
```

适合完全没项目经验的情况。每一步都有可以写进简历的东西，三段时间递进也好看。

先读 novel 再去碰 damai 的理由：novel 那套 controller/core/manager 分层是所有 Java 项目的骨架，看懂之后 dispatch 到微服务，其实只是把同一套东西拆到了不同进程里。

### 路线二：快一点，一个到一个半月

```
spring-boot-jwt        1 天
    ↓
AI-Meeting             3 周
    ↓
部署上线，在此基础上改两个功能
```

适合马上要投简历的情况。AI-Meeting 纯 Java，上手快，MIT 协议也没什么顾虑，一个月能拿出一个可以点开看的东西。

### 路线三：冲后端岗，两个月

```
spring-boot-jwt        1 天
    ↓
spring-boot-online-exam 3 天，快速过一遍
    ↓
damai                  1.5 个月
```

适合目标明确的 Java 后端岗位。damai 的高并发和分布式组件是能拉开差距的地方，但必须先把基础项目读完，直接上会看不懂。

---

## 怎么读一个项目

不管选哪个，步骤是一样的。

**第一步，跑起来。**

照 README 走。这一步基本都会出问题，依赖版本对不上、环境变量没配、SQL 脚本执行失败之类的，把遇到的每个问题记下来。面试的时候这些就是可以讲的具体细节。

**第二步，挑几个 issue 修。**

去 Issues 里找标了 good first issue 的，或者边界条件的 bug。修完提 PR，合不合并不重要，重要的是这个过程。

**第三步，改两个功能。**

选原项目没有、但你觉得该有的功能。要能说清楚为什么需要、为什么这么实现、有没有对比过别的方案。

比如选课系统，原项目只有选课，你可以加选课冲突检测和退课的候补队列。这就涉及业务理解、并发处理和边界条件，比单纯加一个 CRUD 页面有内容得多。

> 一句话：项目要能撑住追问。面试官问「这个为什么这么设计」的时候答不上来，比没项目还糟。