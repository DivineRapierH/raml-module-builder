
# Raml-Module-Builder

Copyright (C) 2016-2020 The Open Library Foundation

This software is distributed under the terms of the Apache License, Version 2.0.
See the file ["LICENSE"](https://github.com/folio-org/raml-module-builder/raw/master/LICENSE) for more information.

- [Raml-Module-Builder](#raml-module-builder)
  - [介绍](#介绍)
  - [升级](#升级)
  - [总览](#总览)
  - [基础](#基础)
    - [实现接口](#实现接口)
    - [设置pom.xml](#设置pomxml)
    - [构建并运行](#构建并运行)
  - [开始使用示例工作模块](#开始使用示例工作模块)
  - [命令行选项](#命令行选项)
  - [环境变量](#环境变量)
  - [本地开发服务器](#本地开发服务器)
  - [创建一个新模块](#创建一个新模块)
    - [步骤1: 创建新的项目目录布局](#步骤1-创建新的项目目录布局)
    - [步骤2: 将jar包含在您的项目pom.xml中](#步骤2-将jar包含在您的项目pomxml中)
    - [步骤3: 将插件添加到pom.xml](#步骤3-将插件添加到pomxml)
    - [步骤4: 构建项目](#步骤4-构建项目)
    - [步骤5: 实现生成的接口](#步骤5-实现生成的接口)
    - [步骤6: 设计RAML文件](#步骤6-设计raml文件)
  - [添加一个init() 实现](#添加一个init-实现)
  - [添加代码以定期运行](#添加代码以定期运行)
  - [添加一个钩子以在Verticle部署后立即运行](#添加一个钩子以在verticle部署后立即运行)
  - [添加关闭钩子](#添加关闭钩子)
  - [实现文件上传](#实现文件上传)
    - [文件上传选项一](#文件上传选项一)
    - [文件上传选项二](#文件上传选项二)
  - [实现分块批量下载](#实现分块批量下载)
  - [PostgreSQL 整合](#postgresql-整合)
    - [Saving binary data](#saving-binary-data)
    - [凭据](#凭据)
    - [保护数据库配置文件](#保护数据库配置文件)
    - [外键约束](#外键约束)
  - [CQL (上下文查询语言)](#cql-上下文查询语言)
    - [CQL2PgJSON: CQL 到PostgreSQL JSON转换器](#cql2pgjson-cql-到postgresql-json转换器)
    - [CQL2PgJSON: 用法](#cql2pgjson-用法)
    - [CQL: 关系](#cql-关系)
    - [CQL: 修饰符](#cql-修饰符)
    - [CQL: 匹配、比较和排序数字](#cql-匹配比较和排序数字)
    - [CQL: 匹配ID和外键字段](#cql-匹配id和外键字段)
    - [CQL: 匹配全文](#cql-匹配全文)
    - [CQL: 匹配所有记录](#cql-匹配所有记录)
    - [CQL: 匹配未定义或空值](#cql-匹配未定义或空值)
    - [CQL: 匹配数组元素](#cql-匹配数组元素)
    - [CQL: 用于数组搜索的@-relation 修饰符](#cql-用于数组搜索的-relation-修饰符)
    - [CQL2PgJSON: 多字段索引](#cql2pgjson-多字段索引)
    - [CQL2PgJSON: 外键交叉表索引查询](#cql2pgjson-外键交叉表索引查询)
    - [CQL2PgJSON: 外键tableAlias和targetTableAlias](#cql2pgjson-外键tablealias和targettablealias)
    - [CQL2PgJSON: 异常](#cql2pgjson-异常)
    - [CQL2PgJSON: 单元测试](#cql2pgjson-单元测试)
  - [租户 API](#租户-api)
  - [RAMLs API](#ramls-api)
  - [JSON Schemas API](#json-schemas-api)
  - [查询语法](#查询语法)
  - [Metadata](#metadata)
  - [Facet 支持](#facet-支持)
  - [JSON Schema 字段](#json-schema-字段)
  - [覆盖 RAML (特征) / 查询参数](#覆盖-raml-特征-查询参数)
  - [Drools 集成](#drools-集成)
  - [Messages](#messages)
  - [API 文档](#api-文档)
  - [日志](#日志)
  - [监控](#监控)
  - [覆写开箱即用的RMB API](#覆写开箱即用的rmb-api)
  - [客户端生成器](#客户端生成器)
  - [通过HTTP查询多个模块](#通过http查询多个模块)
  - [有关校验的一些补充](#有关校验的一些补充)
  - [高级功能](#高级功能)
  - [其他工具](#其他工具)
  - [一些REST示例](#一些rest示例)
  - [附加信息](#附加信息)

<!-- ../okapi/doc/md2toc -l 2 -h 3 README.md -->

## 介绍

本文档包含有关Raml-Module-Builder（RMB）框架的信息以及使用方法的示例。

该项目的目标是尽可能多地抽象出样板功能，并使开发人员可以专注于实现业务功能。换句话说：简化开发微服务模块的过程。该框架由RAML驱动，这意味着开发人员/分析人员声明“待开发”模块将公开的API（通过RAML文件），并声明该API使用和公开的对象（通过JSON模式）。架构和RAML文件到位后，框架将生成代码并提供许多工具来帮助实现模块。请注意，此框架既是构建库，也是运行时库。

该框架包含许多工具：

- `domain-models-api-interfaces` -- 项目公开了一些工具，这些工具接收这些RAML文件和这些JSON模式作为输入，并生成Java POJO和Java接口。

- `domain-models-api-aspects` -- 项目公开了通过公开验证功能来严格遵守任何API调用的RAML声明的工具。

  - 例如：RAML文件可能指示特定参数是强制性的，或者查询参数值必须是特定正则表达式模式。Aspects项目为开发人员处理这种类型的验证，因此不需要一遍又一遍地重新开发它。有关验证的更多信息，请参见[下文](#有关校验的一些补充).

- `domain-models-runtime` -- 项目公开了运行时库，该库应用于运行模块。它基于Vert.x。当开发人员实现接口项目生成的接口时，运行时库应包含在开发的项目中并运行。运行时库将自动将URL映射到正确的实现函数，以便开发人员仅需要实现API，并进行所有连接，验证，参数/标头/正文解析，日志记录（每个请求都以apache之类的格式记录）由框架处理。其目标是抽象化所有样板功能，并允许模块实现专注于实现业务功能。

  - 运行时框架还公开了允许开发人员实现一次性作业，计划任务等的钩子（Hook）。

  - 提供工具（PostgreClient 等）供开发人员在开发模块时使用。

  - Runtime库运行Vert.x的Verticle。

- `rules` -- 基本的Drools功能，允许模块开发人员通过*.drl对象文件（JSON模式）创建验证规则。

## 升级

请参阅单独的[升级说明](https://github.com/folio-org/raml-module-builder/blob/master/doc/upgrading.md).

注意：此版本的自述文件适用于RMB v20 +版本。如果仍在使用旧版本，请参见[分支b19](https://github.com/folio-org/raml-module-builder/tree/b19)自述文件。

## 总览

请按照上面的[简介](#简介)部分大致了解RMB框架。
查看单独的[Okapi参考和指南](https://github.com/folio-org/okapi/blob/master/doc/guide.md)。
阅读下面的[基础知识](#基础)部分，以大致了解RMB。然后按照[示例工作模块](#开始使用示例工作模块)部分进行演示，该示例演示了已经构建的示例。理解了这一点之后，请继续阅读[创建一个新模块](#创建一个新模块)以开始您的项目。

请注意，实际上不需要构建此RAML Module Builder框架。（下面的某些图像已过时。）已经发布的RMB artifacts将从存储库中[合并](#步骤2-将jar包含在您的项目pomxml中)到您的项目中。

## 基础

![](https://github.com/folio-org/raml-module-builder/raw/master/images/build.png)
![](https://github.com/folio-org/raml-module-builder/raw/master/images/generate.png)
![](https://github.com/folio-org/raml-module-builder/raw/master/images/what.png)

### 实现接口

举个例子，请注意基于RAML中的约束生成的验证注释。

![](https://github.com/folio-org/raml-module-builder/raw/master/images/interface_example.png)

- 在实现接口时，必须添加 `@Validate` 注解以强制执行接口声明的带注解的约束。

- 注意，将Bib实体作为参数传递。 运行时框架将在Body中传递的JSON转换为正确的POJO。

### 设置pom.xml

- 添加 `exec-maven-plugin`。这将基于RAML文件生成POJO和接口。

- 添加 `aspectj-maven-plugin`。如果您希望运行时框架验证所有URL，则这是必需的。

- 添加 `maven-shade-plugin`, 以指示要运行的主类，将 `RestLauncher` 主顶点运行为 `RestVerticle`。这将创建一个可运行的jar，以运行时 `RestVerticle` 作为主要类.

- 添加 `maven-resources-plugin`. 这会将您的RAML文件复制到/ apidocs目录，运行时框架将在其中使它们在线（html视图）可见。

这些将在下面进一步说明。

### 构建并运行

执行 `mvn clean install` 并运行。

Runtime框架会将您的RAML中的URL路由到正确的方法实现。它将验证（如果 `@Validate` 已使用），写日志并公开各种工具。

注意，在实现模块中没有配置或引用任何Web服务器-所有这些都由Runtime框架处理。

一些示例项目：

- <https://github.com/folio-org/mod-configuration>
- <https://github.com/folio-org/mod-notes>

和[其他模块](https://dev.folio.org/source-code/#server-side) (并非所有人都使用RMB).

## 开始使用示例工作模块

[`mod-notify`](https://github.com/folio-org/mod-notify)是使用RMB一个完整的例子, clone下来然后研究：

```bash
git clone --recursive https://github.com/folio-org/mod-notify.git
cd mod-notify
mvn clean install
```

- 可以在 `ramls` 目录中找到RAMLs 和JSON schema。
这些也显示为[API文档](#api-文档).

- 打开pom.xml文件-注意 `dependencies` 节点以及 `plugins` 结点部分。该 `ramls` 目录在pom.xml中声明，并通过maven exec插件传递到interface和POJO生成工具。该工具将源文件生成到 `target/generated-sources/raml-jaxrs` 目录中。生成的接口在 `org.folio.rest.impl` 包中的项目内实现。

- 调查 `src/main/java/org/folio/rest/impl/NotificationsResourceImpl.java` 类。注意，有一个函数代表在RAML文件中声明的每个端点。适当的参数（如RAML中所述）作为参数传递给这些函数，因此开发人员无需参数解析。请注意，该类包含整个模块的所有代码。URL，验证，对象等的所有处理都在RMB部分中，或者由RMB在构建时为此模块生成。

- **重要说明:** 任何模块的每个接口的实现都必须驻留在 `org.folio.rest.impl` 包中。Runtime框架在运行时会扫描这个包，在这个包中寻找自动生成的接口的实现。

现在以独立模式运行模块：

```bash
java -jar target/mod-notify-fat.jar embed_postgres=true
```

现在使用 '[curl](https://curl.haxx.se)' 或 '[httpie](https://httpie.org)' 发送一些请求。

在此阶段，尚无可查询的内容，因此现在停止该快速演示。在解释了常规的命令行选项等之后，我们将帮助您运行本地开发服务器并填充测试数据。

## 命令行选项

- `-Dhttp.port=8080` (可选-默认为8081)

- `-Ddebug_log_package=*` (可选-设置日志级别以调试所有程序包。或 `org.folio.rest.*` 用于特定程序包中的所有类或 `org.folio.rest.RestVerticle` 特定类。)

- `embed_postgres=true` (可选-强制启动嵌入式postgreSQL，默认为false)

- `db_connection=[path]` (可选-带有连接参数到PostgreSQL DB的外部JSON配置文件的路径)

  - for example Postgres:

    ```json
    {
      "host":"localhost",
      "port":5432,
      "maxPoolSize":50,
      "username":"postgres",
      "password":"mysecretpassword",
      "database":"postgres",
      "charset":"windows-1252",
      "queryTimeout" : 10000
    }
    ```

- `drools_dir=[path]` (可选-外部drools文件的路径。默认情况下， `*.drl` 目录中的 `resources/rules` 文件已加载)

-可以通过命令行以 key=value 格式传递其他模块特定的参数。实施模块可以通过 `RestVerticle.MODULE_SPECIFIC_ARGS` map 访问这些内容。

- 可以在 `-jar` 参数之前传递可选的JVM参数，例如：
`-XX:+HeapDumpOnOutOfMemoryError`
`-XX:+PrintGCDetails`
`-XX:+PrintGCTimeStamps`
`-Xloggc:C:\Git\circulation\gc.log`

## 环境变量

RMB实现的模块希望在模块启动时传递一组环境变量。RMB模块期望的环境变量有：

- DB_HOST
- DB_PORT
- DB_USERNAME
- DB_PASSWORD
- DB_DATABASE
- DB_QUERYTIMEOUT
- DB_CHARSET
- DB_MAXPOOLSIZE
- DB_CONNECTIONRELEASEDELAY
- DB_EXPLAIN_QUERY_THRESHOLD

前五个是必需的，其他是可选的。

名称中带有句点/点的环境变量以RMB弃用，因为句点不符合[POSIX](http://pubs.opengroup.org/onlinepubs/9699919799/basedefs/V1_chap08.html)，因此某些外壳程序（特别是Alpine Linux中包含的BusyBox / bin / sh）将其剥离（[OpenJDK文档中的警告](https://hub.docker.com/_/openjdk/)）。

有关详细信息请参见[Vert.x异步PostgreSQL客户端配置文档](https://vertx.io/docs/vertx-mysql-postgresql-client/java/#_configuration)。

环境变量 `DB_CONNECTIONRELEASEDELAY` 以毫秒为单位设置延迟，在此延迟之后，空闲连接将关闭。使用0可以使空闲连接永远打开。RMB的默认值为一分钟（60000毫秒）。

`DB_EXPLAIN_QUERY_THRESHOLDPostgres` 本身不会观察到环境变量，而是触发查询执行分析的一个值（以毫秒为单位）。如果单个查询超过此阈值，将使用进行分析 `EXPLAIN ANALYZE` 。请注意，这反过来又增加了查询时间，因此只应对需要进一步注意的慢查询执行此操作。可以通过将其设置为较高的值（例如300000〜5分钟）来有效关闭该分析。像DB环境变量一样，这与每个RMB模块（流程）相关。默认值为 `DB_EXPLAIN_QUERY_THRESHOLD1000` （1秒）。

EXPLAIN ANALYZE - 仅对PostgresClient.get，PostgresClient.select和PostgresClient.join执行。不适用于PostgresClient.getById或PostgresClient.streamGet之类的方法。

有关如何通过Okapi将环境变量部署到RMB模块的更多信息，请参见Okapi指南的[环境变量](https://github.com/folio-org/okapi/blob/master/doc/guide.md#environment-variables)部分。

## 本地开发服务器

要快速运行Okapi的本地实例，添加租户和一些测试数据并部署一些模块，请参阅[运行本地FOLIO系统](https://dev.folio.org/guides/run-local-folio/)。

## 创建一个新模块

### 步骤1: 创建新的项目目录布局

使用文件的[常规布局](https://dev.folio.org/guides/commence-a-module/)和基本POM文件创建新项目。

添加 `/ramls` 目录, RAML、schemas和示例文件都放在此目录。对于Maven子项目，目录只能位于父项目中。

为了快速入门，请从[mod-notify](https://github.com/folio-org/mod-notify)复制“ ramls”目录和POM文件 。（在下面的[第6步](#step-6-design-the-raml-files)中，将替换这些以满足您项目的需要。）

调整POM文件以匹配您的项目，例如artifactID，版本等。

### 步骤2: 将jar包含在您的项目pom.xml中

```xml
  <repositories>
    <repository>
      <id>folio-nexus</id>
      <name>FOLIO Maven repository</name>
      <url>https://repository.folio.org/repository/maven-folio</url>
    </repository>
  </repositories>
  <dependencies>
    <dependency>
      <groupId>org.folio</groupId>
      <artifactId>domain-models-runtime</artifactId>
      <version>29.0.0</version>
    </dependency>
    ...
    ...
  </dependencies>
```

### 步骤3: 将插件添加到pom.xml

需要在POM文件中声明四个插件：

- `exec-maven-plugin` - 基于RAML文件生成POJOs和接口。

- `aspectj-maven-plugin` - 在验证层面预编译带有 `@Validate` 注解的代码。框架可以验证headers传递是否正确、参数类型是否正确并包含了RAML文件指示的正确内容。

- `maven-shade-plugin` - 生成可运行的fat-jar。
很重要一点是运行模块时将运行主类。注意shade插件配置中的 `Main-class` 和 `Main-Verticle` 。

- `maven-resources-plugin` - 复制RAML文件到 `/apidocs` 路径下以便Runtime框架使用RAML文件显示HTML文档。

添加 `ramlfiles_path` 指示RAML目录位置的属性。

```xml
  <properties>
    <ramlfiles_path>${basedir}/ramls</ramlfiles_path>
  </properties>
```

将POM与其他基于FOLIO RMB 的模块进行比较。

### 步骤4: 构建项目

执行 `mvn clean install`

这一步将：

- 为每个添加的RAML文件创建Java接口。

- 每个接口将包含需要实现的函数（每个函数代表在RAML中声明的API端点）。

- 每个功能接口中的参数将使用在RAML中声明的验证注解进行注解。因此，如果一个特征被标记为必需的，它将被标记为@NOT_NULL。这不是实现者需要处理的事情。这由处理验证的框架处理。

- POJOs -- JSON schemas 将生成到Java对象中。

- 所有生成的代码都可以在 `target/generated-sources/raml-jaxrs/` 路径下的 `org.folio.rest.jaxrs` 包中找到。

### 步骤5: 实现生成的接口

实现生成的与RAML文件关联的接口。RAML文件的每个端点都生成了一个接口。
例如，`org.folio.rest.jaxrs.resource.Ebooks` 接口会被创建。
注意每个生成的接口都在 `org.folio.rest.jaxrs.resource` 包里。

所有的实现都必须在 `org.folio.rest.impl` 包中，因为RMB的
[RestVerticle](https://github.com/folio-org/raml-module-builder/blob/master/domain-models-runtime/src/main/java/org/folio/rest/RestVerticle.java)会在这个包中扫描实现接口所需的类。这个类可以叫任意名字。RMB会使用反射来调用构造函数和方法。

有关示例请参见[mod-notify 的 org.folio.rest.impl 包](https://github.com/folio-org/mod-notify/tree/master/src/main/java/org/folio/rest/impl)。

### 步骤6: 设计RAML文件

在这个阶段花费一些时间来为项目设计和准备RAML文件是大有裨益的。我们来研究别的FOLIO模块作参考。[mod-notify](https://github.com/folio-org/mod-notify) 是一个示范.

删除步骤1中的临时 `/raml` 路径，用你自己的代替。

第一个地址段相同的端点(end-points)必须在同一个.raml文件中，因为第一个地址段决定了resource java 接口的名字。比如， `/foo/bar` 和 `/foo/baz` 应该在foo.raml中，并且foo.raml会生成 `org/folio/rest/jaxrs/resource/Foo.java` 。但是，你也可以通过实现GeneratorExtension[否决命名资源接口的约定](https://github.com/mulesoft-labs/raml-for-jax-rs/issues/111)。

将共享的[RAML utility](https://github.com/folio-org/raml) 文件加到"ramls"路径下的"raml-util"路径中。

```bash
git submodule add https://github.com/folio-org/raml ramls/raml-util
```

"raml1.0" 分支是当前分支和默认分支。

为该模块暴露的对象创建JSON schemas。

使用该 `description` 字段和 `type` 字段来解释内容和用法并添加文档。

使用 `"javaType": "org.folio.rest.jaxrs.model.MyEntity"` 更改生成的Java类的名称。

参见 [jsonschema2pojo Reference](https://github.com/joelittlejohn/jsonschema2pojo/wiki/Reference)来了解JSON schema 的详细信息。

GenerateRunner会自动取消引用schema文件并将其放入 `target/classes/ramls/` 目录中。它会扫描 `{basedir}/ramls/` 包含子目录的目录，如果找不到）就扫描 `{basedir}/../ramls/` 使通用ramls目录支持maven子模块。

HTTP响应代码的文档位于[HttpStatus.java](https://github.com/folio-org/raml-module-builder/blob/master/util/src/main/java/org/folio/HttpStatus.java)中。

使用[RAML 200 教程](https://raml.org/developers/raml-200-tutorial#resource-types)中说明的[collection resource type](https://github.com/folio-org/raml/tree/raml1.0/rtypes)提供的collection/collection-item模式。

RMB会在编译时对RAML文件进行一些验证。有一些有用的工具可帮助命令行验证，而有些则可以与文本编辑器集成在一起，例如[raml-cop](https://github.com/thebinarypenguin/raml-cop).

参考 [Use raml-cop to assess RAML, schema, and examples](https://dev.folio.org/guides/raml-cop/)
和 [Primer for RAML and JSON Schema](https://dev.folio.org/start/primer-raml/) 快速上手文档。

支持RAML的文本编辑器非常有用，例如Atom的
[api-workbench](https://github.com/mulesoft/api-workbench)。

记住，通过POM配置可以查看RAML并通过本地[API文档](#api-文档)与应用程序进行交互。

## 添加一个init() 实现

通过实现 `InitAPIs` ，可以添加在部署应用程序之前将运行一次的自定义代码（例如，初始化数据库，创建缓存，创建静态变量等）。您必须实现 `init(Vertx vertx, Context context, Handler<AsyncResult<Boolean>> resultHandler)` 。每个模块仅支持一种实现。当前，实施应放在实施项目的 `org.folio.rest.impl` 包中。该实现将在Verticle部署期间运行。在init（）完成之前，该Verticle版本不会完成部署。init（）函数基本上可以执行任何操作，但是它必须回调Handler。例如：

```java
public class InitAPIs implements InitAPI {

  public void init(Vertx vertx, Context context, Handler<AsyncResult<Boolean>> resultHandler){
    try {
      sayHello();
      resultHandler.handle(io.vertx.core.Future.succeededFuture(true));
    } catch (Exception e) {
      e.printStackTrace();
      resultHandler.handle(io.vertx.core.Future.failedFuture(e.getMessage()));
    }
  }
}
```

## 添加代码以定期运行

可以添加将定期运行的自定义代码。例如，要不断检查系统中某物的状态并对起进行一些操作。需要实现PeriodicAPI接口：

```java
public interface PeriodicAPI {
  /** this implementation should return the delay in which to run the function */
  public long runEvery();
  /** this is the implementation that will be run every runEvery() milliseconds*/
  public void run(Vertx vertx, Context context);

}
```

例如：

```java
public class PeriodicAPIImpl implements PeriodicAPI {

  @Override
  public long runEvery() {
    return 45000;
  }

  @Override
  public void run(Vertx vertx, Context context) {
    try {
      InitAPIs.amIMaster(vertx, context, v-> {
        if(v.failed()){
          //TODO - what should be done here?
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
```

定期钩子（periodic hook)可以有多个实现，所有这些都将由RMB框架调用。

## 添加一个钩子以在Verticle部署后立即运行

可以添加自定义代码，这些代码将在部署运行模块的verticle后立即运行。

```java
public interface PostDeployVerticle {

  /** this implementation will be run immediately after the verticle is initially deployed. Failure does not stop
   * deployment success. The implementing function MUST call the resultHandler to pass back
   * control to the verticle, like so: resultHandler.handle(io.vertx.core.Future.succeededFuture(true));
   * if not, this function will hang the verticle during deployment */
  public void init(Vertx vertx, Context context, Handler<AsyncResult<Boolean>> resultHandler);

}
```

一个实现示例：

```java
public class InitConfigService implements PostDeployVerticle {

  @Override
  public void init(Vertx vertx, Context context, Handler<AsyncResult<Boolean>> handler) {

    System.out.println("Getting secret key to decode DB password.");
    /** hard code the secret key for now - in production env - change this to read from a secure place */
    String secretKey = "b2%2BS%2BX4F/NFys/0jMaEG1A";
    int port = context.config().getInteger("http.port");
    AdminClient ac = new AdminClient("http://localhost:" + port, null);
    ac.postSetAESKey(secretKey, reply -> {
      if(reply.statusCode() == 204){
        handler.handle(io.vertx.core.Future.succeededFuture(true));
      }
      else{
        handler.handle(io.vertx.core.Future.failedFuture(reply.statusCode() + ", " + reply.statusMessage()));
      }
    });
  }

}
```

## 添加关闭钩子

可以添加自定义代码，这些代码将在取消部署Verticle版本和JVM停止之前运行。这将在正常关闭时发生，但是如果强制关闭JVM，则不能保证运行。

需要实现的接口：

```java
public interface ShutdownAPI {

  public void shutdown(Vertx vertx, Context context, Handler<AsyncResult<Void>> handler);

}
```

一个实现示例：

```java
public class ShutdownImpl implements ShutdownAPI {

  @Override
  public void shutdown(Vertx vertx, Context context, Handler<AsyncResult<Void>> handler) {
    try {
      AuditLogger.getInstance().publish(new LogRecord(Level.INFO, "closing audit logger"));
      AuditLogger.getInstance().close();
      handler.handle(io.vertx.core.Future.succeededFuture());
    }
    catch (Exception e) {
      e.printStackTrace();
      handler.handle(io.vertx.core.Future.failedFuture(e.getMessage()));
    }
  }
}
```

请注意，在实现生成的接口时，可以向实现类添加构造函数。每次API调用都会调用此构造函数。这是您为每个请求运行的自定义代码的另一种方式。

## 实现文件上传

RMB支持多种上传文件和数据的方法。实现模块可以使用 `multipart/form-data` header 或 `application/octet-stream` header 来表示HTTP请求是上载内容请求。

### 文件上传选项一

一个由多部分组成的RAML声明可能看起来像这样：

```raml
/uploadmultipart:
    description: Uploads a file
    post:
      description: |
          Uploads a file
      body:
        multipart/form-data:
          formParameters:
            file:
              description: The file to be uploaded
              required: true
              type: file
```

Body content 看起来像这样：

```sh
------WebKitFormBoundaryNKJKWHABrxY1AdmG
Content-Disposition: form-data; name="config.json"; filename="kv_configuration.sample"
Content-Type: application/octet-stream

<file content 1>

------WebKitFormBoundaryNKJKWHABrxY1AdmG
Content-Disposition: form-data; name="sample.drl"; filename="Sample.drl"
Content-Type: application/octet-stream

<file content 2>

------WebKitFormBoundaryNKJKWHABrxY1AdmG
```

将有一个 `MimeMultipart` 参数传递到生成的接口中。实现模块可以通过以下方式访问其内容：

```sh
int parts = entity.getCount();
for (int i = 0; i < parts; i++) {
        BodyPart part = entity.getBodyPart(i);
        Object o = part.getContent();
}
```

Body中每个部分都是一个"part"。

一个八位位组/流看起来像这样：

```raml
 /uploadOctet:
    description: Uploads a file
    post:
      description: |
          Uploads a file
      body:
        application/octet-stream:
```

从上面生成的接口将包含一个 `java.io.InputStream` 类型的参数，代表上载文件。

### 文件上传选项二

RMB允许将内容流式传输到特定的已实现接口。例如，上传大文件可以不用将其全部保存在内存中：

- 用 `org.folio.rest.annotations.Stream` 注解 `@Stream` 标记要处理的函数。
- 声明RAML为接收方 `application/octet-stream`（请参见上面的选项一）

每当收到大量数据时，RMB就会调用该函数。这意味着对于每个数据块，RMB都会实例化一个新对象，并使用对象中包含的部分数据在一个 `java.io.InputStream` 对象中调用该对象的函数。

对于每次调用，RMB添加的header `streamed_id` 在当前流中将是唯一的。对于最后一次调用， `complete` 提供了标头以指示“流结束”。

从RMB 23.12.0起，如果HTTP客户端在完成之前过早关闭上传，则将使用调用 `streamed_abort` handler。

## 实现分块批量下载

RMB支持使用按主键ID排序的CQL批量下载块（自版本25开始）。

- 1st CQL query: `cql.allRecords=1 sortBy id`
- 2nd CQL query: `id > [last id from 1st CQL query] sortBy id`
- 3rd CQL query: `id > [last id from 2nd CQL query] sortBy id`
- ...

块大小是使用API的limit参数设置的，例如 `limit=10000` ，每个块有10000条记录。

## PostgreSQL 整合

PostgreSQL连接参数位置会按以下顺序搜索：

- [DB_* 环境变量](#环境变量)
- 配置文件，默认为 `resources/postgres-conf.json` 但可以通过[命令行选项](#命令行选项)设置
- 嵌入式PostgreSQL 使用[默认凭据](#凭据)

默认情况下，嵌入式PostgreSQL包含在运行时中，但仅当DB_ *环境变量和postgres配置文件均不存在时才运行。要使用环境变量或配置文件中的连接参数启动嵌入式PostgreSQL，请添加 `embed_postgres=true` 到命令行(`java -jar mod-notify-fat.jar embed_postgres=true`)。使用PostgresClient.setEmbeddedPort（int）来覆写端口。

运行时框架公开了PostgreSQL异步客户端，该客户端以ORM类型的方式提供CRUD操作。
<https://github.com/folio-org/raml-module-builder/blob/master/domain-models-runtime/src/main/java/org/folio/rest/persist/PostgresClient.java>

**重要说明：** 当前实现的PostgreSQL客户端假定PostgreSQL中使用JSONB表。这不是强制性的，开发人员可以使用常规PostgreSQL表，但需要实现自己的数据访问层。

**重要说明：** 出于性能方面的考虑，Postgres客户端将返回结果数少于50,000的结果集的准确计数。超过50,000个结果的查询将返回估计的计数。

**I重要说明：** 嵌入式Postgres不能以root用户身份运行。

**重要说明：** 嵌入式Postgres依赖 `en_US.UTF-8` (*nix) / `american_usa` (win)语言环境。如果未安装此语言环境，Postgres将无法正常启动。

**重要说明：** 当前，我们仅支持Postgres版本10。由于减少了对postgresql嵌入式平台的支持（[postgresql嵌入式支持的版本](https://github.com/yandex-qatools/postgresql-embedded/commit/15685611972bacd8ba61dd7f11d4dbdcb3ba8dc1)，[PostgreSQL数据库下载](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads)），因此无法使用版本11 。

PostgresClient要求使用以下格式的表：

```sql
create table <schema>.<table_name> (
  id UUID PRIMARY KEY,
  jsonb JSONB NOT NULL
);
```

这意味着JSON模式中的所有字段（代表JSON对象）**都是** Postgres表中的“ jsonb”（列）。

#### 保存二进制数据

如所示，PostgresClient是面向jsonb的。如果需要以二进制形式存储数据，可以通过以下方式完成（当前仅支持基于id的upsert）：

```java
byte[] data = ......;
JsonArray jsonArray = new JsonArray().add(data);
client.upsert(TABLE_NAME, id, jsonArray, false, replyHandler -> {
.....
});
```

### 凭据

在嵌入式模式下运行时，将从 `resources/postgres-conf.json` 中读取凭据。如果找不到文件，则默认情况下将使用以下配置：

```text
port: 6000
host: 127.0.0.1
username: username
password: password
database: postgres
```

### 保护数据库配置文件

如前所述，RMB提供的Postgres Client查找名为 `postgres-conf.json` 的文件。但是，在服务器上以纯文本形式将包含DB密码的文件留给超级用户不是一个好主意。可以加密文件中的密码。加密应该是AES加密（对称块密码）。这种加密是通过密钥完成的。

含义：纯文本密码+密钥=加密密码

RMB带有一个AES类，该类支持生成秘密密钥，对其进行加密和解密, <https://github.com/folio-org/raml-module-builder/blob/master/domain-models-runtime/src/main/java/org/folio/rest/security/AES.java>

注意，是否使用该类是可选的。

为了使用加密密码，RMB公开了可用于设置密钥（仅存储在内存中）的API。创建数据库连接时，RMB将检查是否已设置密钥。如果设置了密钥，则RMB将使用密钥对密码进行解密，然后使用解密后的密码连接到数据库。否则，它将采用未加密的密码，并按原样使用该密码进行连接。模块也可以通过静态方法 `AES.setSecretKey(mykey)` 设置密钥AES。

所需步骤为：

- 产生密钥
- 加密密码
- 在配置文件中包含该密码
- 调用 `AES.setSecretKey(mykey)` 或 `admin/set_AES_key` API（将密钥加载到内存中）

模块设置密钥的一个好方法是使用RMB中的post post钩子（Hook)接口。

```java
public class InitConfigService implements PostDeployVerticle {
  @Override
  public void init(Vertx vertx, Context context, Handler<AsyncResult<Boolean>> handler) {
    System.out.println("Getting secret key to decode DB password.");
    //** hard code the secret key  - in production env - read from a secure place *//
    String secretKey = "b2%2BS%2BX4F/NFys/0jMaEG1A";
    int port = context.config().getInteger("http.port");
    AdminClient ac = new AdminClient("http://localhost:" + port, null);
    ac.postSetAESKey(secretKey, reply -> {
      if(reply.statusCode() == 204){
        handler.handle(io.vertx.core.Future.succeededFuture(true));
      }
      else{
        handler.handle(io.vertx.core.Future.failedFuture(reply.statusCode() + ", " + reply.statusMessage()));
      }
    });
    handler.handle(io.vertx.core.Future.succeededFuture(true));
  }
}
```

### 外键约束

一个 `foreignKeys` 条目在租户API的schema.json自动创建外键下面的列，触发器和索引。

需要此附加列，因为PostgreSQL不直接支持JSONB内部字段的外键约束（参照完整性）。

例如，类似于 `foreignKeys` 条目产生的SQL：

```sql
CREATE TABLE item (
  id UUID PRIMARY KEY,
  jsonb JSONB NOT NULL,
  permanentLoanTypeId UUID REFERENCES loan_type,
  temporaryLoanTypeId UUID REFERENCES loan_type
);
CREATE OR REPLACE FUNCTION update_item_references()
RETURNS TRIGGER AS $$
BEGIN
  NEW.permanentLoanTypeId = NEW.jsonb->>'permanentLoanTypeId';
  NEW.temporaryLoanTypeId = NEW.jsonb->>'temporaryLoanTypeId';
  RETURN NEW;
END;
$$ language 'plpgsql';
CREATE TRIGGER update_item_references
  BEFORE INSERT OR UPDATE ON item
  FOR EACH ROW EXECUTE PROCEDURE update_item_references();
CREATE INDEX IF NOT EXISTS ON item (permanentLoanTypeId);
CREATE INDEX IF NOT EXISTS ON item (temporaryLoanTypeId);
```

每当使用外键时，CQL2PgJSON都会自动使用此提取的列及其索引。

此触发器和外键约束的开销使此表上每秒的UPDATE事务数减少约10％（在针对外部独立Postgres数据库进行测试时）。有关性能测试，请参阅
<https://github.com/folio-org/raml-module-builder/blob/master/domain-models-runtime/src/test/java/org/folio/rest/persist/ForeignKeyPerformanceIT.java>
通过发送其他SELECT查询手动进行外键检查所花费的时间要比10％多得多。

另外请参见[CQL外键支持](#cql2pgjson-外键交叉表索引查询).

## CQL (上下文查询语言)

进一步的[CQL](https://dev.folio.org/reference/glossary/#cql)信息。

### CQL2PgJSON: CQL 到PostgreSQL JSON转换器

源码位于 [./cql2pgjson](cql2pgjson) 和 [./cql2pgjson-cli](cql2pgjson-cli)

### CQL2PgJSON: 用法

像这样调用

```java
// users.user_data is a JSONB field in the users table.
CQL2PgJSON cql2pgJson = new CQL2PgJSON("users.user_data");
String cql = "name=Miller";
String where = cql2pgJson.cql2pgJson(cql);
String sql = "select * from users where " + where;
// select * from users
// where CAST(users.user_data->'name' AS text)
//       ~ '(^|[[:punct:]]|[[:space:]])Miller($|[[:punct:]]|[[:space:]])'
```

或者用 `toSql(String cql)` 单独获得 `ORDER BY` 子句：

```java
CQL2PgJSON cql2pgJson = new CQL2PgJSON("users.user_data");
String cql = "name=Miller";
SqlSelect sqlSelect = cql2pgJson.toSql(cql);
String sql = "select * from users where " + sqlSelect.getWhere()
+ " order by " + sqlSelect.getOrderBy();
```

可以设置服务器选择索引，下一个例子展示了搜索 `name=Miller or email=Miller` :

```java
CQL2PgJSON cql2pgJson = new CQL2PgJSON("users.user_data", Arrays.asList("name", "email"));
String cql = "Miller";
String where = cql2pgJson.cql2pgJson(cql);
String sql = "select * from users where " + where;
```

跨多个JSONB字段进行搜索是这样的：构造函数中指定的*第一个* json字段将应用于任何没有应用适当前缀字段名称的查询参数：

```java
// Instantiation
CQL2PgJSON cql2pgJson = new CQL2PgJSON(Arrays.asList("users.user_data","users.group_data"));

// Query processing
where = cql2pgJson.cql2pgJson( "users.user_data.name=Miller" );
where = cql2pgJson.cql2pgJson( "users.group_data.name==Students" );
where = cql2pgJson.cql2pgJson( "name=Miller" ); // implies users.user_data
```

### CQL: 关系

只有以下关系已经实现：

- `=` ( `==` 用于匹配数字， `adj` 用于匹配字符串。
       例1: `height =/number 3.4` 例2: `title = Potter`)
- `==` (精确的字段匹配, 例如 `barcode == 883746123` 或者精确的前缀匹配 `title == "Harry Pott*"`
        可以匹配 "Harry Potter and the chamber of secrets" 但不能匹配 "Sience of Harry Potter";
        `==/number` 匹配任何格式的数字: 3.4 = 3.400 = 0.34e1)
- `all` (查询字符串的每个单词都存在于某个位置, `title all "Potter Harry"` 匹配 "Harry X. Potter")
- `any` (查询字符串的任何单词存在于某个位置， `title any "Potter Foo"` 匹配 "Harry Potter")
- `adj` (子字符串词组匹配：查询字符串中的所有单词都以此顺序连续存在，中间可能有空格和标点， `title adj "Harry Potter"` 匹配 "The Harry - . - Potter Story")
- `>` `>=` `<` `<=` `<>` (比较字符串和数字)

注意，CQL的特殊字符 `* ? ^ " \` 在使用时需要用 `\` 转义。

如果搜索字符串包含空格，请使用引号，例如：`title = "Harry Potter"`.

### CQL: 修饰符

匹配修饰符: 只有 `masked` 生效，`unmasked`, `regexp`,
`honorWhitespace`, `substring` 都不生效。

JSON中的单词开头和单词结尾只通过空格和ASCII字符集中的标点来检测，其他Unicode字符不适用。

### CQL: 匹配、比较和排序数字

添加 /number 修饰符来启用数字匹配、比较和排序，例如 `age ==/number 18`,
`age >=/number 21` 和 `sortBy age/number`。

使用 `==/number` 时，3.4、3.400和0.34e1相互匹配，并且2小于19（与字符串比较时“ 2”>“ 19”相反）。

这要求该值已存储为JSONB数字 ( `{"age": 19}` ) 而不是 ( `{"age": "19"}` )。

### CQL: 匹配ID和外键字段

id字段和任何外键字段都是UUID字段，不会在JSONB中搜索，而是在提取的适当数据库表字段中搜索。此类字段得索引将自动创建，请勿在schema.json中手动添加索引条目。

有效的UUID支持 `=`, `==`, `<>`, `>`, `>=`, `<`,  `<=` 比较。

`=`, `==`, 和 `<>` 关系支持用 `*` 作万能匹配。

禁止使用修饰符。

### CQL: 匹配全文

请查阅 [PostgreSQL's tsvector full text parser documentation](https://www.postgresql.org/docs/current/textsearch-parsers.html)
了解使用全文索引时分词的工作方式。以下是一些值得注意的情形：

CQL `field adj "bar"` 匹配 `bar`、`bar-baz`、`foo-bar-baz`。

CQL `field adj "bar baz"` 匹配 `bar baz`、`bar-baz`、`foo-bar-baz`、`foo-bar baz`、`bar-baz-foo`。

CQL `field adj "bar-baz"` 匹配 `bar-baz`，但不匹配 `bar baz` 、 `foo-bar-baz` 、 `foo-bar baz` 和 `bar-baz-foo`。

CQL `field adj "123 456"` 匹配 `123 456`，但不匹配 `123-456`。

CQL `field adj "123-456"` 匹配 `123-456`，但不匹配 `123 456` 。

`foo/bar/baz` 是一个单词，而 `foo//bar//baz` 、 `foo///bar///baz` 、 `foo////bar////baz` 等被分为了三个单词 `foo`, `/bar`, `/baz` (总是缩减到一个单一的"/")。

### CQL: 匹配所有记录

可以通过 `cql.allRecords=1` 查询执行匹配目标索引中所有记录的搜索。 `cql.allRecords=1` 可以单独使用，也可以作为更复杂查询的一部分使用，例如
`cql.allRecords=1 NOT name=Smith sortBy name/sort.ascending` 。

- `cql.allRecords=1 NOT name=Smith` 匹配名称不包含Smith或名称未定义的所有记录。
- `name="" NOT name=Smith` 与定义了名称但不包含Smith的所有记录匹配。
- 出于性能原因， `*` 在任何全文字段中搜索也将匹配所有记录。

### CQL: 匹配未定义或空值

如果左侧的值未定义，则关系不匹配。（但请参见 `*` 上面的全文匹配情形）。如果未定义左侧的值或已定义但不匹配，则使用关系的否定（NOT）匹配。

- `name=""` 匹配定义了名称的所有记录。
- `cql.allRecords=1 NOT name=""` 匹配未定义名称的所有记录。
- `name==""` 匹配名称已定义且为空的所有记录。
- `cql.allRecords=1 NOT name==""` 匹配名称已定义但不为空或名称未定义的所有记录。
- `name="" NOT name==""` 匹配名称已定义且不为空的所有记录。

### CQL: 匹配数组元素

为了匹配数组的元素，请使用以下查询（假设lang是数组或未定义，并假定数组元素值不包含双引号）：

- `lang ==/respectAccents []` 用于匹配定义了lang和空数组的记录
- `cql.allRecords=1 NOT lang <>/respectAccents []` 用于匹配未定义lang或空数组的记录
- `lang =/respectCase/respectAccents \"en\"` 用于匹配记录，其中lang被定义并包含值en
- `cql.allRecords=1 NOT lang =/respectCase/respectAccents \"en\"` 用于lang不包含值en的匹配记录（包括未定义lang的记录）
- `lang = "" NOT lang =/respectCase/respectAccents \"en\"` 用于定义lang且不包含值en的匹配记录
- `lang = ""` 用于定义lang的匹配记录
- `cql.allRecords=1 NOT lang = ""` 用于未定义lang的匹配记录
- `identifiers == "*\"value\": \"6316800312\", \"identifierTypeId\": \"8261054f-be78-422d-bd51-4ed9f33c3422\"*"`
（请注意使用了 `==` 而不是 `=` ）使用ISBN的identifierTypeId来匹配ISBN 6316800312，其中，标识符数组的每个元素都是一个具有两个键值和identifierTypeId的JSON对象，例如：

  ```json
  "identifiers": [ {
    "value": "(OCoLC)968777846", "identifierTypeId": "7e591197-f335-4afb-bc6d-a6d76ca3bace"
  }, {
    "value": "6316800312", "identifierTypeId": "8261054f-be78-422d-bd51-4ed9f33c3422"
  } ]
  ```

为了避免复杂的语法，可以提取所有ISBN值或所有值，并将其用于创建视图或索引：

```sql
SELECT COALESCE(jsonb_agg(value), '[]')
    FROM jsonb_to_recordset(jsonb->'identifiers')
      AS y(key text, value text)
    WHERE key='8261054f-be78-422d-bd51-4ed9f33c3422'

SELECT COALESCE(jsonb_agg(value), '[]')
  FROM jsonb_to_recordset(jsonb->'identifiers')
    AS x(key text, value text)
  WHERE value IS NOT NULL
```

### CQL: 用于数组搜索的@-relation 修饰符

RMB 26或更高版本支持带有关系修饰符的数组搜索，这些修饰符特别适用于以下结构：

```json
"property" : [
  {
    "type1" : "value1",
    "type2" : "value2",
    "subfield": "value"
  },
  ...
]
```

这种结构的一个例子是mod-inventory-storage中的 `contributors` (property)。 `contributorTypeId` 是contributor(type1)的类型.

With CQL you can limit searches to `property1` with regular match in
`subfield`, with type1=value2 with

```text
property =/@type1=value1 value
```

请注意，关系修饰符前面带有@-字符，以避免与其他CQL关系修饰符发生冲突。

type1，type2和子字段都必须在schema.json中定义，因为JSON模式是未知的。而且还因为关系修饰符不幸地被cqljava小写。请使用全文匹配来匹配value1与type1的属性内容。

具有值的多个关系修饰符将AND在一起。所以

```text
    property =/@type1=value1/@type2=value2 value
```

仅当两个type1都具有value1并且type2都具有value2时才会匹配。

也可以指定没有值的关系修饰符。这本质上是一种覆盖要搜索的子字段的方法。在这种情况下，右侧项是匹配的。多个关系修饰符一起进行“或”运算。例如：

```text
    property =/@type1 value
```

为了匹配任何子属性type1，type2，可以使用：

```text
    property =/@type1/@type2 value
```

在schema.json中，有两个新属性 `arraySubfield` 和 `arrayModifiers` ，分别指定了子字段和修饰符列表。这可以应用于 `ginIndex` 和 `fullTextIndex` 。schema.json示例：

```json
{
  "fieldName": "property",
  "tOps": "ADD",
  "caseSensitive": false,
  "removeAccents": true,
  "arraySubfield": "subfield",
  "arrayModifiers": ["type1", "type2"]
}
```

对于标识符示例，我们可以使用以下命令在schema.json中定义内容：

```json
{
  "fieldName": "identifiers",
  "tOps": "ADD",
  "arraySubfield": "value",
  "arrayModifiers": ["identifierTypeId"]
}
```

这将允许您执行搜索，例如：

```text
identifiers = /@identifierTypeId=7e591197-f335-4afb-bc6d-a6d76ca3bace 6316800312
```

### CQL2PgJSON: 多字段索引

CQL2PGjson允许生成和查询包含多列的索引。现在，索引json对象支持以下属性：

- sqlExpression
  允许用户显式定义他们希望在索引中使用的表达式

  ```json
  "fieldName": "address",
  "sqlExpression": "concat_space_sq(jsonb->>'city', jsonb->>'state')",
  ```

- multiFieldNames
  这是逗号分隔的json字段列表，这些字段将通过concat_ws与空格字符连接在一起。例如：

  ```json
  "fieldName": "address",
  "multiFieldNames": "city,state",
  ```

这两个示例是等效的，可以通过使用fieldName来查询，例如：

```text
address = Boston MA
```

这些字段是可选但互斥的，您只需要其中一个。

### CQL2PgJSON: 外键交叉表索引查询

CQL2PgJSON支持通过基于外键的子查询进行跨表联接。这允许在子对父和父子对两个方向上的任意深度关系。

关系示例： item → holdings_record → instance

该示例的join情况:

- item.holdingsRecordId = holdings_record.id
- holdings_record.instanceId = instance.id

子表中的字段指向父表的主键字段 `id` ；父表也称为目标表。

- 在camelCase中将要搜索的索引与表名放在前面，例如： `instance.title = "bee"`。
-子表字段没有变化，请按常规方式使用它们，无需使用表名前缀。
- schema.json中的 `foreignKey` 条目会自动在外键字段上创建索引。
- 为了快速查询，可以在其他需要搜索的字段（例如schema.json文件中的 `title` 字段）中声明一个索引。
- 对于多表连接，请使用 `targetPath` 代替 `fieldName` 并将字段名称列表放入 `targetPath` 数组中。
- 使用 `= *` 以检查join记录是否已经存在。这将运行交叉索引联接而没有其他限制，例如 `instance.id = *`。
- Use `= *` to check whether a join record exists. This runs a cross index join with no further restriction, e.g. `instance.id = *`.
- 上面例子的架构如下:

  ```json
  {
    "tables": [
      {
        "tableName": "instance",
        "index": [
          {
            "fieldName": "title",
            "tOps": "ADD",
            "caseSensitive": false,
            "removeAccents": true
          }
        ]
      },
      {
        "tableName": "holdings_record",
        "foreignKeys": [
          {
            "fieldName": "instanceId",
            "targetTable":      "instance",
            "targetTableAlias": "instance",
            "tableAlias": "holdingsRecord",
            "tOps": "ADD"
          }
        ]
      },
      {
        "tableName": "item",
        "foreignKeys": [
          {
            "fieldName": "holdingsRecordId",
            "targetTable":      "holdings_record",
            "targetTableAlias": "holdingsRecord",
            "tableAlias": "item",
            "tOps": "ADD"
          },
          {
            "targetPath": ["holdingsRecordId", "instanceId"],
            "targetTable":      "instance",
            "targetTableAlias": "instance",
            "tableAlias": "item"
          }
        ]
      }
    ]
  }
  ```

### CQL2PgJSON: 外键tableAlias和targetTableAlias

`targetTableAlias` 属性在当前子表的CQL查询中启用该父表名称。
The property `targetTableAlias` enables that parent table name in CQL queries against the current child table.

`tableAlias` 属性在目标/父表的CQL查询中启用该子表名称。
The property `tableAlias` enables that child table name in CQL queries against the target/parent table.

如果缺少这两个属性中的任何一个，则将禁用相应的外键联接语法。

该名称可能与表名称（`tableName`, `targetTable`）不同。一种用例是更改为camelCase，例如 `"targetTable": "holdings_record"` 和 `"targetTableAlias": "holdingsRecord"`。另一个用例是当两个外键指向同一目标表时解决歧义，例如：

```json
{
  "tableName": "item",
  "foreignKeys": [
    {
      "fieldName": "permanentLoanTypeId",
      "tableAlias""itemWithPermanentLoanType",
      "targetTable": "loan_type",
      "targetTableAlias": "loanType",
      "tOps": "ADD"
    },
    {
      "fieldName": "temporaryLoanTypeId",
      "tableAlias""itemWithTemporaryLoanType",
      "targetTable": "loan_type",
      "targetTableAlias""temporaryLoanType",
      "tOps": "ADD"
    }
  ]
}
```

在 item 端点运行 CQL `loanType.name == "Can circulate"` 会返回所有 permanentLoanTypeId 指向名字等于 "Can circulate" 的 loan_type 的items。

在 item 端点运行 CQL `temporaryLoanType.name == "Can circulate"` 会返回所有 temporaryLoanTypeId 指向名字等于 "Can circulate" 的 loan_type 的items。

在 loan_type 端点运行 CQL `itemWithPermanentLoanType.status == "In transit"` 会返回所有存在有状态为 "In transit" 的item将其作为permanentLoanType的loan_type。

在 loan_type 端点运行 CQL `itemWithTemporaryLoanType.status == "In transit"` 会返回所有存在有状态为 "In transit" 的item将其作为temporarytLoanType的loan_type。

### CQL2PgJSON: 异常

所有本地产生的异常均来自单一父来源，因此它们可以被整体或单独地捕获。加载JSON数据对象模型的方法将模型的身份作为资源文件名传递，并且也可能抛出原生的 `java.io.IOException`。

```text
CQL2PgJSONException
  ├── FieldException
  ├── SchemaException
  ├── ServerChoiceIndexesException
  ├── CQLFeatureUnsupportedException
  └── QueryValidationException
        └── QueryAmbiguousException
```

### CQL2PgJSON: 单元测试

为了在您的IDE中运行单元测试，必须先通过运行maven提供Unicode 输入文件。
在 Eclipse 中你可以使用 "Run as ... Maven Build" 来运行。

## 租户 API

RMB中的Postgres Client 支持是基于特定schema的，这意味着它希望每个租户都由自己的schema表示。RMB暴露了三个接口以方便每个租户创建schema（租户的一种配置）。Post, Delete, 和 'check existence' 租户schema。注意，此API并非强制要求使用。

这些API的RAML定义:

   <https://github.com/folio-org/raml/blob/raml1.0/ramls/tenant.raml>

默认情况下，RMB包含Tenant API的实现，该实现假定存在Postgres。在[TenantAPI.java](https://github.com/folio-org/raml-module-builder/blob/master/domain-models-runtime/src/main/java/org/folio/rest/impl/TenantAPI.java)文件中实现 。您可能会想要扩展/覆写它，因为：

1. 您完全不想调用它（您的模块未使用Postgres）。
2. 您想提供进一步的租户控制，例如加载参考和/或样本数据。

#### 拓展租户初始化

为了实现您的租户API，请继承 `TenantAPI` 类：

```java
package org.folio.rest.impl;
import javax.ws.rs.core.Response;
import org.folio.rest.jaxrs.model.TenantAttributes;

public class MyTenantAPI extends TenantAPI {
 @Override
  public void postTenant(TenantAttributes ta, Map<String, String> headers,
    Handler<AsyncResult<Response>> hndlr, Context cntxt) {

    ..
    }
  @Override
  public void getTenant(Map<String, String> map, Handler<AsyncResult<Response>> hndlr, Context cntxt) {
    ..
  }
  ..
}

```

如果要调用Post Tenant API（使用Postgres），则只需调用相应的超类，例如：

```java
@Override
public void postTenant(TenantAttributes ta, Map<String, String> headers,
  Handler<AsyncResult<Response>> hndlr, Context cntxt) {
  super.postTenant(ta, headers, hndlr, cntxt);
}
```

（不过，这没有多大意义-和完全不定义它没有区别）。

如果希望为模块加载数据，则应在成功初始化数据库之后执行此操作，例如，执行以下操作：

```java
public void postTenant(TenantAttributes ta, Map<String, String> headers,
  super.postTenant(ta, headers, res -> {
    if (res.failed()) {
      hndlr.handle(res);
      return;
    }
    // load data here
    hndlr.handle(io.vertx.core.Future.succeededFuture(PostTenantResponse
      .respond201WithApplicationJson("")));
  }, cntxt);
}
```

没有正确的方式加载数据，但是请考虑在模块的首次租户使用和升级过程中都会发生数据加载。您的数据加载应该是幂等的。如果文件存储为资源和JSON文件，则可以使用TenantLoading utility。

```java
import org.folio.rest.tools.utils.TenantLoading;

public void postTenant(TenantAttributes ta, Map<String, String> headers,
  super.postTenant(ta, headers, res -> {
    if (res.failed()) {
      hndlr.handle(res);
      return;
    }
    TenantLoading tl = new TenantLoading();
    // two sets of reference data files
    // resources ref-data/data1 and ref-data/data2 .. loaded to
    // okapi-url/instances and okapi-url/items respectively
    tl.withKey("loadReference").withLead("ref-data")
      .withIdContent().
      .add("data1", "instances")
      .add("data2", "items");
    tl.perform(ta, headers, vertx, res1 -> {
      if (res1.failed()) {
        hndlr.handle(io.vertx.core.Future.succeededFuture(PostTenantResponse
          .respond500WithTextPlain(res1.cause().getLocalizedMessage())));
        return;
      }
      hndlr.handle(io.vertx.core.Future.succeededFuture(PostTenantResponse
        .respond201WithApplicationJson("")));
    });
  }, cntxt);
}
```

如果数据已在资源中，则没有问题。例如，如果不在项目的根目录中，则使用maven-resource-plugin复制它。例如，要复制 `reference-data` 到 `ref-data` 资源中：

```xml
<execution>
  <id>copy-reference-data</id>
  <phase>process-resources</phase>
  <goals>
    <goal>copy-resources</goal>
  </goals>
  <configuration>
    <outputDirectory>${basedir}/target/classes/ref-data</outputDirectory>
    <resources>
      <resource>
        <directory>${basedir}/reference-data</directory>
        <filtering>true</filtering>
      </resource>
    </resources>
  </configuration>
</execution>
```

#### 发布租户 API

基于Postgres的Tenant API实现将在 `/resources/templates/db_scripts/` 名为**schema.json**的文件中查找。

该文件包含要在注册时为租户创建的表和视图的数组（tenant api post）

一个示例:

- <https://github.com/folio-org/raml-module-builder/blob/master/domain-models-runtime/src/main/resources/templates/db_scripts/examples/schema.json.example.json>

schema.json顶层特性（其中一些是可选的）是 `scripts` ,  `tables` ,  `views` 和 `exactCount` 。

json文件中要注意的条目：

每张**表**中的 `tables` 属性：

1. `tableName` - 要生成的表的名称-这是应从代码中引用的表
2. `generateId` - 不再受支持了。该功能在 Pgpool-II 中不稳定，详见 <https://www.pgpool.net/docs/latest/en/html/restrictions.html>。  解决方案是使用与 <https://github.com/folio-org/raml-module-builder/blob/v23.11.0/domain-models-runtime/src/main/java/org/folio/rest/persist/PgUtil.java#L358> 相同的解决方案来在java中生成UUID。
3. `fromModuleVersion` - 此字段指示在其中创建/更新表的版本。当有租户更新请求时-只有早于指定版本的版本才会生成声明的表。这样可以确保在模块从较旧的版本升级时可以为其生成所需的表。但是，从等于或晚于该表指定的版本的版本进行的后续升级将不会重新生成该表。
    - 注意，这是对所有表，视图，索引，FK，触发器等强制执行的（通过 `IF NOT EXISTS` Postgres SQL 语句）。
4. `mode` - 只应在表示 `delete` 时使用。
5. `withMetadata` - 将在更新/插入时生成所需的触发器以填充json中的metadata部分。
6. `likeIndex` - 指示哪些字段会被用 `LIKE` 查询。会被切割成多个部分的字段需要这个。
    - `fieldName` 为其创建索引的json中的字段名称。
    - `tOps` 表示表操作-ADD表示创建这个索引，DELETE表示该索引应被删除。
    - `caseSensitive` 在您有一个拥有不同大小写格式的字符串字段并希望该值无论大小写都唯一时，允许创建大小写不敏感的索引 (boolean true / false)，默认为false。
    - `removeAccents` - 规范化重音符号或保留重音字符。默认为true。
    - `whereClause` 允许创建部分索引，例如：  "whereClause": "WHERE (jsonb->>'enabled')::boolean = true"。
    - `stringType` - 默认为true - 如果将其设置为false，则该字段不是文本类型，因此忽略removeAccents和caseSensitive参数。
    - `arrayModifiers` - 指定某些索引支持的数组关系修饰符。修饰符必须与数组中JSON对象中的属性名称完全匹配。
    - `arraySubfield` - 是使用数组关系修饰符时用于主要术语的对象的键。通常在定义 `arrayModifiers` 时同时进行定义。
    - `multiFieldNames` - 详见之前的 [CQL2PgJSON: 多字段索引](#cql2pgjson-多字段索引)。
    - `sqlExpression` - 详见之前的 [CQL2PgJSON: 多字段索引](#cql2pgjson-多字段索引)。
    - 不要手动为 `id` 字段或外键字段添加索引，它们会自动建立索引。
7. `ginIndex` - 使用 `gin_trgm_ops` 扩展在JSON上生成反向索引。允许左右截断 LIKE 查询和正则表达式查询来以最佳方式运行（类似与简单的搜索引擎）。注意，生成的索引很大且不支持全文匹配（不带通配符的SQL `=` 运算符和CQL `==` 运算符）。参考 `likeIndex` 来获得可用选项。
8. `uniqueIndex` - 在JSON中的字段上创建唯一索引
    - `tOps` 表示表操作-ADD表示创建这个索引，DELETE表示该索引应被删除。
    - `whereClause` 允许创建部分索引，例如：  "whereClause": "WHERE (jsonb->>'enabled')::boolean = true"。
    - 如果b树索引超过了2712 byte 限制，增加一条记录会失败。请考虑为字段增加长度限制，比如在RAML声明的时候增加一个600字符限制（multi-byte characters）。
    - 在上方的likeIndex部分中查看其他额外选项
9. `index` - 在JSON中的字段上创建b树索引
    - `tOps` 表示表操作-ADD表示创建这个索引，DELETE表示该索引应被删除。
    - `whereClause` 允许创建部分索引，例如：  "whereClause": "WHERE (jsonb->>'enabled')::boolean = true"。
    - 在上方的likeIndex部分中查看其他额外选项
    - 表达式被包装到左边(..., 600)来防止超过b树的2712 byte 限制。特殊情况： sqlExpression不会被包装。
10. `fullTextIndex` - 使用postgres的tsvector功能创建全文索引。
    - `removeAccents` 可以使用, 但默认选项 `caseSensitive: false` 不能更改，因为tsvector 始终会转换成小写字母。
    - 参阅 [CQL: 匹配全文](#cql-匹配全文) 来了解分词的工作原理。
    - `tOps` 是可选的 (例如所有索引)，并且默认为添加索引。
    - `whereClause` 和 `stringType` 和上面的 `likeIndex` 一样工作。
11. `withAuditing` - 创建审计表和触发器，每当发生插入、更新或删除操作时，触发器就会使用表记录的历史记录填充审计表。`"withAuditing": true` 为启用, `false` 或未定义为禁用。
    - `auditingTableName` 审计表的名称
    - `auditingFieldName` 在审计记录中包含原始记录副本的字段（JSON属性）。
    - `"withAuditing": true` 自动创建审计表。 在schema.json的“表”中，审核表条目是可选的，比如用于创建索引。
    - `auditingSnippet` 部分允许在声明部分和正文中使用自定义SQL对审核功能进行一些自定义（用于插入/更新/删除）。
    - 审计表的jsonb列有三个字段： `$auditingFieldName` 为原始记录（原始表的jsonb），`id` 为一个新的唯一ID，`operation` 包括 `I`, `U`, `D` 分别代表插入(Insert)、更新(Update)、删除(Delete)， `createdDate` 为创建审计记录的时间。
12. `foreignKeys` - 添加/删除外键（触发器根据JSON中的字段填充列中的数据并创建FK约束。
13. `customSnippetPath` - 存放本表中自定义的一些SQL命令的文件的相对路径。
14. `deleteFields` / `addFields` - 为所有JSON条目删除（或使用默认值添加）指定路径处的一个字段
15. `populateJsonWithId` - 不再支持此schema.json条目和disable选项。在每次插入和更新时主键始终会被复制到 `jsonb->'id'` 。
16. `pkColumnName` - 不再受支持。主键列的名称始终为 `id` 不变，在每次插入和更新时主键始终会被复制到 `jsonb->'id'` 。 `PostgresClient.setIdField(String)` 方法不再存在了。

**视图**部分需要多一点解释。它表示和两个表（和每表中的列）join的viewName。除此之外，您还可以指示两个表之间的join类型。例如：

```json
"views": [
  {
    "viewName": "items_mt_view",
    "join": [
      {
        "table": {
          "tableName": "item",
          "joinOnField": "materialTypeId"
        },
        "joinTable": {
          "tableName": "material_type",
          "joinOnField": "id",
          "jsonFieldAlias": "mt_jsonb"
        }
      }
    ]
  }
]
```

在幕后，作为schema生成的过程以下的语句会被执行：

```sql
CREATE OR REPLACE VIEW ${tenantid}_${module_name}.items_mt_view AS
  SELECT u.id, u.jsonb as jsonb, g.jsonb as mt_jsonb
  FROM ${tenantid}_${module_name}.item u
  JOIN ${tenantid}_${module_name}.material_type g
    ON lower(f_unaccent(g.jsonb->>'id')) = lower(f_unaccent(u.jsonb->>'materialTypeId'))
```

注意这些 `lower(f_unaccent()` 函数，当前默认情况下，所有字符串字段都将包装在这些函数中（将来会更改）。

三表连接看起来像这样：

```json
{
  "viewName": "instance_holding_item_view",
  "join": [
    {
      "table": {
        "tableName": "instance",
        "joinOnField": "id"
      },
      "joinTable": {
        "tableName": "holdings_record",
        "joinOnField": "instanceId",
        "jsonFieldAlias": "ho_jsonb"
      }
    },
    {
      "table": {
        "tableName": "holdings_record",
        "joinOnField": "id",
        "jsonFieldAlias": "ho2_jsonb"
      },
      "joinTable": {
        "tableName": "item",
        "joinOnField": "holdingsRecordId",
        "jsonFieldAlias": "it_jsonb"
      }
    }
  ]
}
```

该**脚本**部分允许一个模块在表或视图创建/更新的之前或之后运行自定义SQL。

**脚本**部分中的字段包括：

1. `run` - 在表或视图创建/更新之前或之后
2. `snippet` - 需要运行的SQL
3. `snippetPath` - 需要运行的SQL脚本的相对路径。如果设置了 `snippetPath` ，那么 `snippet` 中的内容会被忽略。
4. `fromModuleVersion` - 和表中的 `fromModuleVersion` 一致。

**exactCount**部分是可选的，该属性的值是一个简单的整数，默认值为1000。get-familify方法返回的hit counts 会基于默认值使用实际的hit count；除此之外还会返回一个估计的hit count。但是，对于省略查询参数（过滤器为null）的情况，仍然返回精确计数。

表或视图会在名为 tenantid_modulename 的schema中生成。

调用API传入的x-okapi-tenant header 将用于获取租户ID。模块名称使用的值是在pom.xml中找到的artifactId（如果找到了父artifactId，则使用其父id）。

#### 重要信息

现在，应将jsonb中字符串字段上的所有索引声明均为小写并且大小写不敏感。这就是[CQL 到 Postgres 转换器](#cql-上下文查询语言)如何生成SQL查询的方法。因此，为了在查询期间使用生成的索引，必须以类似的方式声明索引。

```json
{
  "fieldName": "title",
  "tOps": "ADD",
  "caseSensitive": false,
  "removeAccents": true
}
```

在后台，CQL到Postgres的转换器将为 `=` 查询生成正则表达式查询。例如： `?query=fieldA=ABC` 将生成一个SQL正则表达式查询，这将需要gin索引才能在大型表上执行。

转换器将为 `==` 查询生成LIKE查询。例如， `?query=fieldA==ABC` 将生成一个使用btree索引（如果存在）的SQL LIKE查询。对于仅查找特定id的查询等等，首选方法是使用 `==` 查询，因此，需要声明一个常规btree（索引）。

##### Post 信息

Post新的租户必须有一个body。body应包含符合[moduleInfoSchema](https://github.com/folio-org/raml/blob/master/schemas/moduleInfo.schema)的JSON 。 `module_to` 条目是必填项，指示此租户的版本模块。 `module_from` 条目是可选的，表示租户升级到新的模块版本的升级。

body还可以拥有一个 `parameters` 属性，以指定在创建/更新租户期间要执行的每个租户操作/信息。

##### 加密租户密码

到目前为止（将来可能会发生变化）可以通过以下方式实现通过加密密码保护租户与数据库的连接：

- 设置密钥（如[保护数据库配置文件](#保护数据库配置文件)部分所述）

  密码将被以下替换：
  encrypt(tenant id with secret key) = **new tenant's password**
  **new tenant's password**（新的租户密码）会替换默认的密码（默认密码为 tenantid_modulename）
  The **new tenant's password** will replace the default PASSWORD value (which is the tenantid_modulename)
  RMB Postgres 客户端在租户需要DB连接的时候会使用密钥和传入的tenant id 来计算租户的密码。注意如果您使用了租户API并设置了密钥，Postgres 客户端会在每个租户连接的时候完成密码解密。

RMB附带一个 TenantClient 来方便通过URL调用API。
通过clinet发布租户：

```java
TenantClient tClient = null;
tClient = new TenantClient("http://localhost:" + port, "mytenantid", "sometoken");
tClient.post( response -> {
  response.bodyHandler( body -> {
    System.out.println(body.toString());
    async.complete();
  });
});
```

#### 删除租户 API

调用此API时，RMB会直接删除租户的schema（CASCADE）并删除用户。

##### 一些 Postgres Client 示例

示例:

在事务中保存POJOs

```java
PoLine poline = new PoLine();

...

postgresClient.save(beginTx, TABLE_NAME_POLINE, poline , reply -> {...
```

记得调用 beginTx 和 endTx

在数据库中查询类似的POJO（有或者没有其他条件）：

```java
Criterion c = new Criterion(new Criteria().addField("id").setJSONB(false).setOperation("=").setValue("'"+entryId+"'"));

postgresClient.get(TABLE_NAME_POLINE, PoLine.class, c,
              reply -> {...
```

生成 `where` 语句的 `Criteria` 对象也可以接收 JSON Schema，以便将值转换为 `where` 子句中的正确类型。

```java
Criteria idCrit = new Criteria("ramls/schemas/userdata.json");
```

## RAMLs API

RAMLs API是一个多重接口，可让RMB模块以机器可读的方式暴露其RAML文件。要启用该接口，模块必须将以下内容添加到其module descriptor 的providers数组中：

```JSON
{
  "id": "_ramls",
  "version": "1.0",
  "interfaceType" : "multiple",
  "handlers" : [
    {
      "methods" : [ "GET" ],
      "pathPattern" : "/_/ramls"
    }
  ]
}
```

该接口具有单个GET端点，该端点具有可选的查询参数路径。如果没有路径查询参数，则响应将是可用RAML的 application/json 数组。这是模块可以立即提供的RAML。如果提供了查询参数路径，它将在该路径（如果存在）上返回RAML。RAML将具有HTTP可解析的引用。这些引用是模块提供的JSON Schemas 和RAMLs 或是共享的JSON Schemas 和RAMLs。共享的JSON Schemas 和RAMLs 通过 `raml_util` 路径下的git子模块包含在每个模块中。使用path查询参数可以解析这些路径。

定义API的RAML：

<https://github.com/folio-org/raml/blob/eda76de6db681076212e20c7f988c3913764b9b0/ramls/ramls.raml>

## JSON Schemas API

JSON Schemas API是一个多重接口，可为RMB模块提供以机器可读的方式公开其JSON Schema 文件的功能。要启用该接口，模块必须将以下内容添加到其module descriptor 的providers数组中：

```json
{
  "id": "_jsonSchemas",
  "version": "1.0",
  "interfaceType" : "multiple",
  "handlers" : [
    {
      "methods" : [ "GET" ],
      "pathPattern" : "/_/jsonSchemas"
    }
  ]
}
```

该接口具有单个GET端点，该端点具有可选的查询参数路径。如果没有路径查询参数，则响应将是可用JSON Schema 的 application/json 数组。默认情况下，这将是存储在模块ramls目录中的JSON Schema 中。可以在模块pom.xml文件中自定义返回的模Schemas列表。添加schema_paths系统属性来在pom.xml中使用“ exec-maven-plugin”运行 `<mainClass>org.folio.rest.tools.GenerateRunner</mainClass>` 指定的逗号分隔目录列表，应在目录中搜索schema文件。要以递归方式搜索目录，请以glob表达式的形式指定目录（例如 "raml-util/**"）。
例如：

```xml
<systemProperty>
  <key>schema_paths</key>
  <value>schemas/**,raml-util/**</value>
</systemProperty>
```

如果提供了查询参数路径，它将在该路径（如果存在）上返回JSON Schema。JSON Schema将具有HTTP可解析的引用。这些引用是模块提供的JSON Schema或RAML或共享的JSON Schema和RAML。共享的JSON Schemas 和RAMLs 通过 `raml_util` 路径下的git子模块包含在每个模块中。使用path查询参数可以解析这些路径。

定义API的RAML：

<https://github.com/folio-org/raml/blob/eda76de6db681076212e20c7f988c3913764b9b0/ramls/jsonSchemas.raml>

## 查询语法

RMB可以接收不同类型的参数。模块可以声明查询参数，并在生成的API函数中将其作为字符串参数接收。

RMB提供了使用[CQL (上下文查询语言)](#cql-上下文查询语言)进行查询的简便方法。这使得从查询参数到准备查询的 "where"子句无缝集成。

```java
//create object on table.field
CQL2PgJSON cql2pgJson = new CQL2PgJSON("tablename.jsonb");
//cql wrapper based on table.field and the cql query
CQLWrapper cql = new CQLWrapper(cql2pgJson, query);
//query the db with the cql wrapper object
PostgresClient.getInstance(context.owner(), tenantId).get(CONFIG_COLLECTION, Config.class,
          cql, true,
```

CQLWrapper也可以使用offset 和 limit：

```java
new CQLWrapper(cql2pgJson, query).setLimit(new Limit(limit)).setOffset(new Offset(offset));
```

一个CQL查询示例：

```sh
http://localhost:<port>/configurations/entries?query=scope.institution_id=aaa%20sortBy%20enabled
```

## Metadata

RMB关注[metadata.schema](https://github.com/folio-org/raml/blob/raml1.0/schemas/metadata.schema)。
当请求（POST / PUT）进入RMB模块时，RMB将检查传入的JSON Schema 是否声明了对metadata schema 的引用。如果是这样，RMB将使用当前用户和当前时间用metadata部分填充JSON。RMB会将更新和创建值设置为相同的日期/时间和相同的用户，因为从请求中接受此信息可能不可靠。模块应在初始POST之后保持creation date 和create by 的值不变。有关使用SQL触发器的示例，请参见[metadata.ftl](https://github.com/folio-org/raml-module-builder/blob/master/domain-models-runtime/src/main/resources/templates/db_scripts/metadata.ftl)。[将withMeatadata添加到schema.json](https://github.com/folio-org/raml-module-builder#the-post-tenant-api)来创建该触发器。

## Facet 支持

RMB还可以轻松划分返回结果的集合。分组/切片在数据库中完成。
为您的API添加切片需要以下步骤：

1. 将[faceting RAML trait](https://github.com/folio-org/raml/blob/master/traits/facets.raml) 添加到您的RAML并从端点引用它 (使用 is:[])
    - facet query 参数格式: `facets=a.b.c` or `facets=a.b.c:10` (they are repeating)。例如 `?facets=active&facets=personal.lastName`
2. 将[resultInfo.schema](https://github.com/folio-org/raml/blob/master/schemas/resultInfo.schema)添加到您的RAML并从您的collection schemas 引用它。
例如：

    ```json
    "type": "object",
    "properties": {
      "items": {
        "id": "items",
        "type": "array",
        "items": {
          "type": "object",
          "$ref" : "item.json"
        }
      },
      "resultInfo": {
        "type": "object",
        "$ref": "raml-util/schemas/resultInfo.schema"
      }
    }
    ```

3. 构建模块时，会将附加参数添加到切片端点生成的接口中。`List<String> facets`。您可以使用RMB工具简单地将此列表转换为Facet对象列表，`List<FacetField> facetList = FacetManager.convertFacetStrings2FacetFields(facets, "jsonb");`，并将返回的facetList传递给 `postgresClient` 的 `get()` 方法。您可以调用 `FacetManager.setCalculateOnFirst(20000);` 来自定义切片大小（默认为10000）。注意更大的数字可能会影响性能。

4. 可以通过以下方式对数组字段进行切片：
`personal.secondaryAddress[].postalCode`
`personal.secondaryAddress[].address[].postalCode`

注意：可能需要在潜在的切片字段上创建索引来避免严重的性能问题。

## JSON Schema 字段

可以通过在schema中声明 `"readonly": true` 来指定JSON中的一个字段为只读字段。例如：

```json
"resultInfo": {
  "$ref": "raml-util/schemas/resultInfo.schema",
  "readonly" : true
}
```

一个 `readonly` 字段中不允许作为请求的一部分被传递。包含有声明为 `readonly` 的字段数据的请求在传入RMB的时候RMB会删除其只读字段（该数据将被传递到不具有只读字段的实现函数中）。

这是RMB暴露的一个框架的一部分，该框架允许创建一个字段并对该字段关联验证约束。

如果需要添加一个自定义字段，请在插件定义处(pom.xml中)增加一个系统属性(在配置中) `<mainClass>org.folio.rest.tools.GenerateRunner</mainClass>`。

例如：

```xml
<systemProperty>
  <key>jsonschema.customfield</key>
  <value>{"fieldname" : "readonly" , "fieldvalue": true , "annotation" : "javax.validation.constraints.Null"}</value>
</systemProperty>
```

`jsonschema.customfield` 键可以包含多个JSON值(由 `;` 分开)。每个JSON表示一个字段名称+一个要匹配的字段值-以及要应用的验证注解。因此，回到只读字段，上面的示例表明，在JSON模式中已被该readonly字段标记的字段在作为请求的一部分传入时不能包含数据。可用的注解列表：
<https://docs.oracle.com/javaee/7/api/javax/validation/constraints/package-summary.html>

要自定义Java类的生成，请在插件定义中添加主入口的系统属性 `<mainClass>org.folio.rest.tools.GenerateRunner</mainClass>`。以 `jsonschema2pojo.config` 开头的属性将传递给生成Java类的基础库。不完整的可用属性列表：

- jsonschema2pojo.config.includeHashcodeAndEquals - adds hashCode and equals methods
- jsonschema2pojo.config.includeToString - adds toString method
- jsonschema2pojo.config.serializable - makes classes serializable

有关更多的可用属性，详见：
 <https://joelittlejohn.github.io/jsonschema2pojo/site/1.0.0/generate-mojo.html>
 <https://github.com/mulesoft-labs/raml-for-jax-rs/blob/master/raml-to-jaxrs/jaxrs-code-generator/src/main/java/org/raml/jaxrs/generator/RamlToJaxRSGenerationConfig.java>

## 覆盖 RAML (特征) / 查询参数

模块可能需要对现有的RAML特性进行些微调。
例如： `limit` 特征可以用下面的方式定义：

 ``` raml
    limit:
      description: Limit the number of elements returned in the response
      type: integer
      required: false
      example: 10
      default: 10
      minimum: 1
      maximum: 2147483647
```

但是，模块可能不希望允许这么高的最大值，因为这可能会导致崩溃。模块可以创建 `raml_overrides.json` 文件并将其放置在 `/resources/overrides/` 目录中。

该文件在schema中定义：
`domain-models-interface-extensions/src/main/resources/overrides/raml_overrides.schema`

请注意，`DEFAULTVALUE` 仅允许使用字符串值。`SIZE` 需要范围，例如 `"15, 20"` 。 `REQUIRED` 不接受 `"value"` ，表示是否必需。

示例：
`domain-models-interface-extensions/src/main/resources/overrides/raml_overrides.json`

## Drools 集成

RMB框架自动扫描 `/resources/rules` 已实现项目中的 `/resources/rules` 路径来查找 `*.drl` 文件。也可以通过命令行命令 `drools_dir` 手动传递路径。Runtime 框架会自动加载应用这些规则文件到所有的对象(post, put)。它以以下方式工作：

- Body中带有 POST / PUT 请求
- 请求的body映射到生成的POJO
- POJO插入RMB的Drools会话中
- 所有规则都针对POJO运行

这允许对对象进行更复杂的验证。

- 例如，两个特定字段在逻辑上可以为null，但不能同时为null。使用Drool可以轻松实现这一点，但很难在RAML文件中创建这些类型的验证。

-该 `rules` 项目还公开了Drools会话，并允许在已实现的API中进行验证。详见 `rules` 项目的 `tests` 。

例如： (Sample.drl)

```drools
package com.sample

import org.folio.rest.jaxrs.model.Patron;

rule "Patron needs one ID at the least"

    no-loop

    when
        p : Patron( patronBarcode  == null, patronLocalId == null )
    then
        throw new java.lang.Exception("Patron needs one ID field populated at the least");
end
```

也可以在代码中创建Drools会话，并以更动态的方式将规则加载到该会话中。例如：

```java
import org.folio.rulez.Rules;
...
List<String> ruleList = generateDummyRule();
Rules rules = new Rules(ruleList);
ksession = rules.buildSession();
...
Messages message = new Messages();
ksession.insert(message);
ksession.fireAllRules();
Assert.assertEquals("THIS IS A TEST", message.getMessage());
```

在RMB中使用Drools框架的另一个选择是动态加载规则。例如，模块可以决定将Drool `.drl` 文件存储在数据库中。这使模块可以允许管理员用户更新数据库中的规则，然后将其加载到RMB验证机制中以在运行时使用。

```java
      Rules rules = new Rules(List<String> rulesLoaded);
      ksession = rules.buildSession();
      RestVerticle.updateDroolsSession(ksession);
```

## 消息

Runtime框架附带了一组消息，这些消息会打印到日志中/作为对错误API调用的错误响应发送回。这些消息是特定于语言的。为了添加您自己的消息文件，请将文件放在项目中的 `/resources/messages` 目录下。

请注意，文件名的格式应为以下两种之一：

- `[name]_[lang_2_letters].properties` (例如: `APIMessages_de.properties`)
- `[lang_2_letters]_messages.prop` (例如: `en_messages.prop`)

示例：
在流通模块中，可以在 `/circulation/src/main/resources/en_messages.prop` 位置下找到消息文件：

```sh
20002=Operation can not be calculated on a Null Amount
20003=Unable to pay fine, amount is larger than owed
20004=The item {0} is not renewable
20005=Loan period must be greater than 1, period entered: {0}
```

流通模块将这些消息暴露为枚举，以便在代码中更轻松地使用：

```java
package org.folio.utils;

import org.folio.rest.tools.messages.MessageEnum;

public enum CircMessageConsts implements MessageEnum {

  OperationOnNullAmount("20002"),
  FinePaidTooMuch("20003"),
  NonRenewable("20004"),
  LoanPeriodError("20005");

  private String code;
  private CircMessageConsts(String code){
    this.code = code;
  }
  public String getCode(){
    return code;
  }
}
```

用法:

`private final Messages messages = Messages.getInstance();`

`messages.getMessage(lang, CircMessageConsts.OperationOnNullAmount);`

注意：相关参数也可以传递。raml-module-builder运行时还公开了通用错误消息枚举，可以在 `/domain-models-runtime/src/main/java/org/folio/rest/tools/messages/MessageConsts.java` 找到。

## API 文档

Runtime框架包括一个Web应用程序，该Web应用程序以易于查看的HTML格式公开RAML。这使用[api-console](https://github.com/mulesoft/api-console) (Powered by [MuleSoft](http://www.MuleSoft.org) for RAML
Copyright (c) 2013 MuleSoft, Inc.)

前面描述的 `maven-resources-plugin` 插件将RAML文件复制到项目中的正确目录中，以便Runtime框架可以访问它并显示本地API文档。

举个例子，当运行 [sample working module](#开始使用示例工作模块)时，其API文档位于：

```url
http://localhost:8081/apidocs/index.html?raml=raml/configuration/config.raml
```

相反，如果您的 [新模块](#创建一个新模块) 在默认端口上运行，则其API文档位于：

```url
http://localhost:8081/apidocs/index.html?raml=raml/my-project.raml
```

并记住声明 "X-Okapi-Tenant：diku" header。

RMB还自动提供其他文档，例如 "Admin API":

```url
http://localhost:8081/apidocs/index.html?raml=raml/admin.raml
```

所有当前的API文档也可在[dev.folio.org/doc/api](https://dev.folio.org/reference/api/)找到。

## 日志

RMB使用Log4J日志记录库。RMB生成的日志将以以下格式打印所有日志条目：
 `%d{dd MMM yyyy HH:mm:ss:SSS} %-5p %C{1} %X{reqId} %m%n`

想要以不同格式生成log4J日志的模块可以在 `/resources` 目录中创建 `log4j.properties` 文件。

日志级别也可以通过框架提供的 `/admin` API进行更改。例如：

- 获取所有类的日志级别：

  (GET) `http://localhost:8081/admin/loglevel`

- 将所有类的日志级别更改为FINE：

  (PUT) `http://localhost:8081/admin/loglevel?level=FINE`

`java_package`参数也可以通过更改特定包的日志级别。例如：

 `http://localhost:8081/admin/loglevel?level=INFO&java_package=org.folio.rest.persist.PostgresClient`

 `http://localhost:8081/admin/loglevel?level=INFO&java_package=org.folio.rest.persist`

## 监控

Runtime框架通过 `/admin` API（如之前所述）暴露了一些APIs来帮助监控服务（设置日志级别，数据库信息）。下面列出了一些（查看[完整的API文档](#api-文档)）

- `/admin/jstack` -- 栈JVM中所有线程的跟踪信息，以帮助找到较慢和遇到瓶颈的方法。
- `/admin/memory` -- 一种jstat回复类型，指示每个池中JVM内的内存使用情况（幸存者，旧版本，新版本，元数据等）以及使用百分比。
- `/admin/slow_queries` -- 查询时间超过X秒。
- `/admin/cache_hit_rates` -- Postgres中的缓存命中率。
- `/admin/table_index_usage` -- 每个表的索引使用情况。
- `/admin/postgres_table_size` -- 每个表使用的磁盘空间。
- `/admin/postgres_table_access_stats` -- 有关如何访问表的信息。
- `/admin/postgres_load` -- 在Postgres中加载信息。
- `/admin/postgres_active_sessions` -- Postgres中的活动会话。
- `/admin/health` -- 只要服务启动，就会返回状态码200。
- `/admin/module_stats` -- 最近2分钟内所有select/update/delete/insert数据库查询的摘要统计信息（count, sum, min, max, average）。

## 覆写开箱即用的RMB API

可以用自定义实现覆写RMB提供的API。
例如：
要覆盖 `/health` API以返回特定模块的相关业务逻辑运行状况检查，请执行以下操作：

1. `extend` RMB框架的AdminAPI类 - `public class CustomHealthCheck extends AdminAPI` 然后覆写 `getAdminHealth` 方法。RMB会将相关的URL端点路由到模块的自定义实现。

例如：

```java
public class CustomHealthCheck extends AdminAPI {

  @Override
  public void getAdminHealth(Map<String, String> okapiHeaders,
      Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {

    super.getAdminHealth(okapiHeaders,  res -> {
      System.out.println(" --- this is an over ride of the health API by the config module "+res.result().getStatus());
      asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetAdminHealthResponse.withOK()));
    }, vertxContext);
  }

  @Override
  public void getAdminModuleStats(Map<String, String> okapiHeaders,
      Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {

    super.getAdminModuleStats(okapiHeaders,  res -> {

      JsonObject o = new JsonObject(res.result().getEntity().toString());

      System.out.println(" --- this is an over ride of the Module Stats API by the config module ");
      asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetAdminModuleStatsResponse.
        withPlainOK( o.encodePrettily() )));
    }, vertxContext);
  }
}
```

## 客户端生成器

该框架可以为每个RAML文件生成一个Client类，并为RAML中的每个API端点提供一个函数。

要从您的RAML生成客户端API，请将以下插件添加到pom.xml中。

```xml
<plugin>
  <groupId>org.codehaus.mojo</groupId>
  <artifactId>exec-maven-plugin</artifactId>
  <version>1.5.0</version>
  <executions>
    <execution>
      <id>generate_client</id>
      <phase>process-classes</phase>
      <goals>
        <goal>java</goal>
      </goals>
      <configuration>
        <mainClass>org.folio.rest.tools.ClientGenerator</mainClass>
        <cleanupDaemonThreads>false</cleanupDaemonThreads>
        <systemProperties>
          <systemProperty>
            <key>client.generate</key>
            <value>true</value>
          </systemProperty>
          <systemProperty>
            <key>project.basedir</key>
            <value>${basedir}</value>
          </systemProperty>
          <systemProperty>
            <key>json.type</key>
            <value>postgres</value>
          </systemProperty>
        </systemProperties>
      </configuration>
    </execution>
  </executions>
</plugin>
```

对于Runtime框架公开的监视API，通过客户端更改日志级别，如下所示：

```java
    AdminClient aClient = new AdminClient("http://localhost:" + 8083, "myuniversityId", "sometoken");
    aClient.putLoglevel(Level.FINE, "org.folio",  apiResponse -> {
      System.out.println(apiResponse.statusCode());
    });
```

请求堆栈跟踪如下所示：

```java
    AdminClient aClient = new AdminClient("http://localhost:" + 8083, "myuniversityId", "sometoken");
    aClient.getJstack( trace -> {
      trace.bodyHandler( content -> {
        System.out.println(content);
      });
    });
```

## 通过HTTP查询多个模块

RMB有一些工具可以帮助您：

- 向其他模块发出HTTP请求
- 解析收到的JSON响应（以及与此相关的任何JSON）
- 合并/加入来自多个模块的JSON响应
- 根据JSON中的值构建简单的CQL查询字符串

#### HTTP 请求

`HttpModuleClient2` 类暴露了一个基本的HTTP客户端。完整的构造函数采用以下参数：

- host
- port
- tenantId
- keepAlive - 连接数（默认值：true）
- connTO - 连接超时（默认值：2秒）
- idleTO - 空闲超时（默认值：5秒）
- autoCloseConnections - 请求完成时关闭连接（默认值：true）
- cacheTO - 端点结果超时缓存（以分钟为单位，默认值：30）

```java
HttpModuleClient hc = new HttpModuleClient("localhost", 8083, "myuniversity_new2", false);
Response response = hc.request("/groups");
```

建议使用 `HttpClientFactory` 来获取 `HttpModuleClient2` 的实例。然后，工厂将返回的实际 `HttpModuleClient2` 类或 `HttpClientMock2` 实例。要返回模拟客户端的实例，请在vertx配置中设置模拟模式标志。一种执行此方法的方法： `new DeploymentOptions().setConfig(new JsonObject().put(HttpClientMock2.MOCK_MODE, "true"));` 有关如何将url与模拟的数据和标头关联的示例，请参见[mock_content.json](https://github.com/folio-org/raml-module-builder/blob/master/domain-models-runtime/src/test/resources/mock_content.json)

客户端返回一个 `Response` 对象。 `Response` 类具有以下成员：

- endpoint - 响应来自的url
- code - HTTP请求返回的状态码
- (JsonObject) body - 响应数据
- (JsonObject) error - 错误 - 如果发生错误- `error` 将填充该成员。Error对象将包含 `endpoint` 、 `statusCode` 和 `errorMessage` 。
- (Throwable) exception - 如果在API调用期间抛出了异常

`HttpModuleClient2 request` 函数可以接收以下参数：

- `HttpMethod` - (默认: GET)
- `endpoint` - API 端点
- `headers` - 如果未填充默认header，则将其传入：Content-type = application/json，接受：plain/text
- `RollBackURL` - 不支持 - 请求失败（返回非2xx代码）时要调用的URL。请注意，如果回滚URL调用不成功，响应错误的对象将包含更多的错误信息( `rbEndpoint` , `rbStatusCode` ,  `rbErrorMessage` )。
- `cachable` - 是否缓存响应
- `BuildCQL` 对象 - 这使您可以根据JSON对象中的内容构建简单的CQL查询字符串。例如：

  ```java
  Response userResponse =
  hc.request("/users", new BuildCQL(groupsResponse, "usergroups[*].id", "patron_group"));
  ```

  这将创建一个查询字符串，其中包含路径中找到的JSON中的所有值 `usergroups[*].id` ，并将生成一个CQL查询字符串，其外观如下所示： `?query=patron_group==12345+or+patron+group==54321+or+patron_group==09876...` 请参阅 `BuildCQL` 以获取配置选项。

`Response` 类也暴露了一个joinOn功能，使您可以加入/合并接收到的JSON从多个请求的对象。

`public Response joinOn(String withField, Response response, String onField, String insertField, String intoField, boolean allowNulls)`

Join的响应是启动joinOn调用的：

- `withField` - 响应中其值将用于联接的字段
- `response` - 加入此`response`的`response`
- `onField` - 传入响应中的字段，其值将用于连接
- `insertField` - 传入`response`以推送到当前响应的字段（`onField`如果未传入，则默认为该值）
- `intoField` - 在此响应中填充的字段
- `allowNulls` - 是否填充`null`。请求推送到当前响应中的字段是否为 `null` - 如果设置为false - 则该字段不会填充为null。

例如：

join:

(response1) `{"a": "1","b": "2"}`

with:

(response2) `{"arr2":[{"id":"1","a31":"1"},{"id":"1","a31":"2"},{"id":"1","a32":"4"},{"id":"2","a31":"4"}]}`

returns:
`{"a":"1","b":["1","2"]}`

with the following call:
`response1.joinOn("a", response2, "arr2[*].id", "a31", "b", false)`

说明：
将字段"a"上的response1与字段"arr2[\*].id"上的response2连接起来（这意味着arr2数组中的所有ID。如果找到匹配项，则将在字段"a31"中找到的值放在字段中由于在这种情况下，单个条目（response1）与response2中的多个条目匹配-创建并填充数组。如果这是一对一匹配，则仅单个值（无论是JSON对象还是JSON）数组，任何值）将被插入。
Join response1 on field "a" with response2 on field "arr2[\*].id" (this means all IDs in the arr2 array. If a match is found take the value found in field "a31" and place it in field "b".
Since in this case a single entry (response1) matches multiple entries from response2 - an array is created and populated. If this was a one-to-one match, then only the single value (whether a JSON object, JSON array, any value) would have been inserted.

#### 解析

RMB公开了一个用于vert.x JSONObject的简单JSON解析器。解析器允许获取和设置嵌套的JSON值。解析器允许以更简单的方式检索值/嵌套值。例如：

`a.b` -- 获取嵌套在名为'a'的JSONObject中的字段'b'的值

`a.c[1].d` -- 获取出现在数组'c [1]'中的'd'

`a.'bb.cc'` -- 获取名为'bb.cc'的字段（名称中使用'.'时使用''）

`a.c[*].a2` -- 获取所有'a2'值作为'c'数组中每个条目的列表

更多信息请参见 `JsonPathParser` 类。

#### An example HTTP request

```java
//create a client
HttpClientInterface client = HttpClientFactory.getHttpClient(okapiURL, tenant);
//make a request
CompletableFuture<Response> response1 = client.request(url, okapiHeaders);
//chain a request to the previous request, the placeholder {users[0].username}
//means that the value appearing in the first user[0]'s username in the json returned
//in response1 will be injected here
//the handlePreviousResponse() is a function you code and will receive the response
//object (containing headers / body / etc,,,) of response1 so that you can decide what to do
//before the chainedRequest is issued - see example below
//the chained request will not be sent if the previous response (response1) has completed with
//an error
response1.thenCompose(client.chainedRequest("/authn/credentials/{users[0].username}",
    okapiHeaders, null, handlePreviousResponse());

    Consumer<Response> handlePreviousResponse(){
        return (response) -> {
            int statusCode = response.getCode();
            boolean ok = Response.isSuccess(statusCode);
            //if not ok, return error
        };
    }

//if you send multiple chained Requests based on response1 you can use the
//CompletableFuture.allOf() to wait till they are all complete
//or you can chain one request to another in a pipeline manner as well

//you can also generate a cql query param as part of the chained request based on the
//response of the previous response. the below will create a username=<value> cql clause for
//every value appearing in the response1 json's users array -> username
response1.thenCompose(client.chainedRequest("/authn/credentials", okapiHeaders, new BuildCQL(null, "users[*].username", "username")),...

//join the values within 2 responses - injecting the value from a field in one json into the field of another json when a constraint between the two jsons exists (like field a from json 1 equals field c from json 2)
//compare all users->patron_groups in response1 to all usergroups->id in groupResponse, when there is a match, push the group field in the specific entry of groupResonse into the patron_group field in the specific entry in the response1 json
response1.joinOn("users[*].patron_group", groupResponse, "usergroups[*].id", "group", "patron_group", false);
//close the http client
hClient.closeClient();
```

## 有关校验的一些补充

查询参数和header验证
![](https://github.com/folio-org/raml-module-builder/raw/master/images/validation.png)

#### 对象校验

![](https://github.com/folio-org/raml-module-builder/raw/master/images/object_validation.png)

#### 方法示例

```java
@Validate
@Override
public void getConfigurationsEntries(String query, int offset, int limit,
    String lang,java.util.Map<String, String>okapiHeaders,
    Handler<AsyncResult<Response>> asyncResultHandler, Context context) throws Exception {

  CQLWrapper cql = getCQL(query,limit, offset);
  /**
  * http://host:port/configurations/entries
  */
  context.runOnContext(v -> {
    try {
      System.out.println("sending... getConfigurationsTables");
      String tenantId = TenantTool.calculateTenantId( okapiHeaders.get(RestVerticle.OKAPI_HEADER_TENANT) );

      PostgresClient.getInstance(context.owner(), tenantId).get(CONFIG_TABLE, Config.class,
        new String[]{"*"}, cql, true,
          reply -> {
            try {
              if(reply.succeeded()){
                Configs configs = new Configs();
                List<Config> config = (List<Config>) reply.result()[0];
                configs.setConfigs(config);
                configs.setTotalRecords((Integer)reply.result()[1]);
                asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetConfigurationsEntriesResponse.withJsonOK(
                  configs)));
              }
              else{
                log.error(reply.cause().getMessage(), reply.cause());
                asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetConfigurationsEntriesResponse
                  .withPlainBadRequest(reply.cause().getMessage())));
              }
            } catch (Exception e) {
              log.error(e.getMessage(), e);
              asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetConfigurationsEntriesResponse
                .withPlainInternalServerError(messages.getMessage(
                  lang, MessageConsts.InternalServerError))));
            }
          });
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      String message = messages.getMessage(lang, MessageConsts.InternalServerError);
      if(e.getCause() != null && e.getCause().getClass().getSimpleName().endsWith("CQLParseException")){
        message = " CQL parse error " + e.getLocalizedMessage();
      }
      asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetConfigurationsEntriesResponse
        .withPlainInternalServerError(message)));
    }
  });
}
```

## 高级功能

1. RMB处理所有路由，因此这是从开发人员那里抽象出来的。但是，在某些情况下，第三方功能可能需要访问路由信息。并且，这不是直接使用路由，而是为了将路由信息传递给第三方（一个这样的示例是pac4j vertx saml client）。RMB通过在pom.xml中指示该函数表示的端点（使用逗号分隔符用于多个路径），使开发人员可以将Vertx RoutingContext对象作为生成函数的参数来接收。

```xml
<properties>
  <generate_routing_context>/rmbtests/test</generate_routing_context>
</properties>
```

## 其他工具

#### 反序列化

在运行时，RMB将在PUT和POST请求的body中接收到的JSON序列化/反序列化为POJO，并将其传递给实现函数，并将实现函数返回的POJO传递给JSON。一个模块可以实现它自己的版本。例如，下面将注册一个反序列化器，该序列化器将告诉RMB如果到期日期已过，则将User设置为非活动状态。当用户JSON作为请求的一部分传入时，它将运行。

```java
ObjectMapperTool.registerDeserializer(User.class, new UserDeserializer());

public class UserDeserializer extends JsonDeserializer<User> {

  @Override
  public User deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
    ObjectMapper mapper = ObjectMapperTool.getDefaultMapper();
    ObjectCodec objectCodec = parser.getCodec();
    JsonNode node = objectCodec.readTree(parser);
    User user = mapper.treeToValue(node, User.class);
    Optional<Date> expirationDate = Optional.ofNullable(user.getExpirationDate());
    if (expirationDate.isPresent()) {
      Date now = new Date();
      if (now.compareTo(expirationDate.get()) > 0) {
        user.setActive(false);
      }
    }
    return user;
  }
}
```

#### 错误处理工具

对PostgresClient进行异步调用需要处理不同的失败状况。RMB公开了一种可以处理基本错误情况的工具，并将他们作为422错误返回。当错误不是标准DB错误之一时，会将它们作为500错误状态返回。

用法:

```java
if(reply.succeeded()){
  ..........
}
else{
   ValidationHelper.handleError(reply.cause(), asyncResultHandler);
}
```

RMB 会向客户端返回的如下：

- 无效的UUID - 422 状态
- 重复密钥冲突 - 422状态
- 违反外键 - 422状态
- 租户不存在/对数据库的身份验证错误 - 401状态
- 各种CQL错误 - 422状态
- 其他不包含在以上的任何情况都会退回到500状态

RMB不会交叉检查raml以查看是否已为端点定义了这些错误状态。这是开发人员的责任。

## 一些REST示例

将这些放在headers中 - 当前尚未验证，因此不是必需的：

- Accept: application/json,text/plain
- Content-Type: application/json;

#### 示例1: 为读者添加罚款 (post)

```text
http://localhost:8080/patrons/56dbe25ea12958478cec42ba/fines
{
  "fine_amount": 10,
  "fine_outstanding": 0,
  "fine_date": 1413879432,
  "fine_pay_in_full": true,
  "fine_pay_in_partial": false,
  "fine_note": "aaaaaa",
  "item_id": "56dbe160a129584dc8de7973",
  "fine_forgiven": {
 "user": "the cool librarian",
 "amount": "none"
  },
  "patron_id": "56dbe25ea12958478cec42ba"
}
```

#### 示例2: 通过读者id获取读者的罚款

```text
http://localhost:8080/patrons/56dbe25ea12958478cec42ba/fines
```

#### 示例3: 获取指定的读者

```text
http://localhost:8080/patrons/56dbe25ea12958478cec42ba
```

#### 示例4: 获取所有读者

```text
http://localhost:8080/patrons
```

#### 示例5: 删除一个读者 (delete)

```text
http://localhost:8080/patrons/56dbe791a129584a506fb41a
```

#### 示例6: 添加一个读者 (post)

```text
http://localhost:8080/patrons
{
 "status": "ACTIVE",
 "patron_name": "Smith,John",
 "patron_barcode": "00007888",
 "patron_local_id": "abcdefd",
 "contact_info": {
  "patron_address_local": {
   "line1": "Main Street 1",
   "line2": "Nice building near the corner",
   "city": "London",
   "state_province": "",
   "postal_code": "",
   "address_note": "",
   "start_date": "2013-12-26Z"
  },
  "patron_address_home": {
   "line1": "Main Street 1",
   "line2": "Nice building near the corner",
   "city": "London",
   "state_province": "",
   "postal_code": "",
   "address_note": "",
   "start_date": "2013-12-26Z"
  },
  "patron_address_work": {
   "line1": "Main Street 1",
   "line2": "Nice building near the corner",
   "city": "London",
   "state_province": "",
   "postal_code": "",
   "address_note": "",
   "start_date": "2013-12-26Z"
  },
  "patron_email": "johns@mylib.org",
  "patron_email_alternative": "johns@mylib.org",
  "patron_phone_cell": "123456789",
  "patron_phone_home": "123456789",
  "patron_phone_work": "123456789",
  "patron_primary_contact_info": "patron_email"
 },
 "total_loans": 50,
 "total_fines": "100$",
 "total_fines_paid": "0$",
 "patron_code": {
  "value": "CH",
  "description": "Child"
 }
}
```

## 附加信息

其他 [RMB 文档](https://github.com/folio-org/raml-module-builder/blob/master/doc/) (例如：数据库schema迁移, 升级说明).

其他[模块](https://dev.folio.org/source-code/#server-side).

专用的 [FOLIO Slack](https://wiki.folio.org/display/COMMUNITY/FOLIO+Communication+Spaces#FOLIOCommunicationSpaces-slackSlack) 频道 [#raml-module-builder](https://folio-project.slack.com/archives/CC0PHKEMT)

在 [FOLIO issue tracker](https://dev.folio.org/guidelines/issue-tracker/) 中查看 [RMB](https://issues.folio.org/browse/RMB) 项目

其他 FOLIO Developer 文档在 [dev.folio.org](https://dev.folio.org/)
