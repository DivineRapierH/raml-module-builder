
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
  - [覆盖 RAML (traits) / 查询参数](#覆盖-raml-traits-查询参数)
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



<!-- * [介绍](#介绍)
* [升级](#升级)
* [总览](#总览)
* [基础](#基础)
  * [实现接口](#实现接口)
  * [设置pom.xml](#设置pomxml)
  * [构建并运行](#构建并运行)
* [开始使用示例工作模块](#开始使用示例工作模块)
* [命令行选项](#命令行选项)
* [环境变量](#环境变量)
* [本地开发服务器](#本地开发服务器)
* [创建一个新模块](#创建一个新模块)
  * [步骤1: 创建新的项目目录布局](#步骤1-创建新的项目目录布局)
  * [步骤2: 将jar包含在您的项目pom.xml中](#步骤2-将jar包含在您的项目pomxml中)
  * [步骤3: 将插件添加到pom.xml](#步骤3-将插件添加到pomxml)
  * [步骤4: 构建项目](#步骤4-构建项目)
  * [步骤5: 实现生成的接口](#步骤5-实现生成的接口)
  * [步骤6: 设计RAML文件](#步骤6-设计RAML文件)
* [添加一个init() 实现](#添加一个init-实现)
* [添加代码以定期运行](#添加代码以定期运行)
* [添加一个钩子以在Verticle部署后立即运行](#添加一个钩子以在Verticle部署后立即运行)
* [添加关闭钩子](#添加关闭钩子)
* [实现文件上传](#实现文件上传)
* [实现分块批量下载](#实现分块批量下载)
* [PostgreSQL 整合](#PostgreSQL-整合)
    * [Credentials](#credentials)
    * [Securing DB Configuration file](#securing-db-configuration-file)
    * [Foreign keys constraint](#foreign-keys-constraint)
* [CQL (Contextual Query Language)](#cql-contextual-query-language)
    * [CQL2PgJSON: CQL to PostgreSQL JSON converter](#cql2pgjson-cql-to-postgresql-json-converter)
    * [CQL2PgJSON: Usage](#cql2pgjson-usage)
    * [CQL: Relations](#cql-relations)
    * [CQL: Modifiers](#cql-modifiers)
    * [CQL: Matching, comparing and sorting numbers](#cql-matching-comparing-and-sorting-numbers)
    * [CQL: Matching id and foreign key fields](#cql-matching-id-and-foreign-key-fields)
    * [CQL: Matching full text](#cql-matching-full-text)
    * [CQL: Matching all records](#cql-matching-all-records)
    * [CQL: Matching undefined or empty values](#cql-matching-undefined-or-empty-values)
    * [CQL: Matching array elements](#cql-matching-array-elements)
    * [CQL: @-relation modifiers for array searches](#cql--relation-modifiers-for-array-searches)
    * [CQL2PgJSON: Multi Field Index](#cql2pgjson-multi-field-index)
    * [CQL2PgJSON: Foreign key cross table index queries](#cql2pgjson-foreign-key-cross-table-index-queries)
    * [CQL2PgJSON: Foreign key tableAlias and targetTableAlias](#cql2pgjson-foreign-key-tablealias-and-targettablealias)
    * [CQL2PgJSON: Exceptions](#cql2pgjson-exceptions)
    * [CQL2PgJSON: Unit tests](#cql2pgjson-unit-tests)
* [租户 API](#租户-api)
* [RAMLs API](#ramls-api)
* [JSON Schemas API](#json-schemas-api)
* [Query Syntax](#query-syntax)
* [Metadata](#metadata)
* [Facet Support](#facet-support)
* [JSON Schema fields](#json-schema-fields)
* [Overriding RAML (traits) / query parameters](#overriding-raml-traits--query-parameters)
* [Drools integration](#drools-integration)
* [Messages](#messages)
* [Documentation of the APIs](#documentation-of-the-apis)
* [Logging](#logging)
* [Monitoring](#monitoring)
* [Overriding Out of The Box RMB APIs](#overriding-out-of-the-box-rmb-apis)
* [Client Generator](#client-generator)
* [Querying multiple modules via HTTP](#querying-multiple-modules-via-http)
* [A Little More on Validation](#a-little-more-on-validation)
* [Advanced Features](#advanced-features)
* [Additional Tools](#additional-tools)
* [Some REST examples](#some-rest-examples)
* [Additional information](#additional-information) -->

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

- https://github.com/folio-org/mod-configuration
- https://github.com/folio-org/mod-notes

和[其他模块](https://dev.folio.org/source-code/#server-side) (并非所有人都使用RMB).

## 开始使用示例工作模块

[`mod-notify`](https://github.com/folio-org/mod-notify)是使用RMB一个完整的例子, clone下来然后研究：

```bash
$ git clone --recursive https://github.com/folio-org/mod-notify.git
$ cd mod-notify
$ mvn clean install
```

- 可以在 `ramls` 目录中找到RAMLs 和JSON schema。
这些也显示为[API文档](#api-文档).

- 打开pom.xml文件-注意 `dependencies` 节点以及 `plugins` 结点部分。该 `ramls` 目录在pom.xml中声明，并通过maven exec插件传递到interface和POJO生成工具。该工具将源文件生成到 `target/generated-sources/raml-jaxrs` 目录中。生成的接口在 `org.folio.rest.impl` 包中的项目内实现。

- 调查 `src/main/java/org/folio/rest/impl/NotificationsResourceImpl.java` 类。注意，有一个函数代表在RAML文件中声明的每个端点。适当的参数（如RAML中所述）作为参数传递给这些函数，因此开发人员无需参数解析。请注意，该类包含整个模块的所有代码。URL，验证，对象等的所有处理都在RMB部分中，或者由RMB在构建时为此模块生成。

- **重要说明:** 任何模块的每个接口的实现都必须驻留在 `org.folio.rest.impl`包中。Runtime框架在运行时会扫描这个包，在这个包中寻找自动生成的接口的实现。

现在以独立模式运行模块：

```bash
$ java -jar target/mod-notify-fat.jar embed_postgres=true
```

现在使用 '[curl](https://curl.haxx.se)' 或 '[httpie](https://httpie.org)' 发送一些请求。

在此阶段，尚无可查询的内容，因此现在停止该快速演示。在解释了常规的命令行选项等之后，我们将帮助您运行本地开发服务器并填充测试数据。

## 命令行选项

- `-Dhttp.port=8080` (可选-默认为8081)

- `-Ddebug_log_package=*` (可选-设置日志级别以调试所有程序包。或`org.folio.rest.*`用于特定程序包中的所有类或`org.folio.rest.RestVerticle`特定类。)

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

- `drools_dir=[path]` (可选-外部drools文件的路径。默认情况下，`*.drl`目录中的`resources/rules`文件已加载)

-可以通过命令行以 key=value 格式传递其他模块特定的参数。实施模块可以通过`RestVerticle.MODULE_SPECIFIC_ARGS` map 访问这些内容。

- 可以在`-jar`参数之前传递可选的JVM参数，例如：
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

环境变量`DB_CONNECTIONRELEASEDELAY`以毫秒为单位设置延迟，在此延迟之后，空闲连接将关闭。使用0可以使空闲连接永远打开。RMB的默认值为一分钟（60000毫秒）。

`DB_EXPLAIN_QUERY_THRESHOLDPostgres`本身不会观察到环境变量，而是触发查询执行分析的一个值（以毫秒为单位）。如果单个查询超过此阈值，将使用进行分析`EXPLAIN ANALYZE`。请注意，这反过来又增加了查询时间，因此只应对需要进一步注意的慢查询执行此操作。可以通过将其设置为较高的值（例如300000〜5分钟）来有效关闭该分析。像DB环境变量一样，这与每个RMB模块（流程）相关。默认值为`DB_EXPLAIN_QUERY_THRESHOLD1000`（1秒）。

EXPLAIN ANALYZE - 仅对PostgresClient.get，PostgresClient.select和PostgresClient.join执行。不适用于PostgresClient.getById或PostgresClient.streamGet之类的方法。

有关如何通过Okapi将环境变量部署到RMB模块的更多信息，请参见Okapi指南的[环境变量](https://github.com/folio-org/okapi/blob/master/doc/guide.md#environment-variables)部分。

## 本地开发服务器

要快速运行Okapi的本地实例，添加租户和一些测试数据并部署一些模块，请参阅[运行本地FOLIO系统](https://dev.folio.org/guides/run-local-folio/)。

## 创建一个新模块

### 步骤1: 创建新的项目目录布局

使用文件的[常规布局](https://dev.folio.org/guides/commence-a-module/)和基本POM文件创建新项目。

添加`/ramls` 目录, RAML、schemas和示例文件都放在此目录。对于Maven子项目，目录只能位于父项目中。

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

- `aspectj-maven-plugin` - 在验证层面预编译带有`@Validate`注解的代码。框架可以验证headers传递是否正确、参数类型是否正确并包含了RAML文件指示的正确内容。

- `maven-shade-plugin` - 生成可运行的fat-jar。
很重要一点是运行模块时将运行主类。注意shade插件配置中的`Main-class` 和 `Main-Verticle`。

- `maven-resources-plugin` - 复制RAML文件到`/apidocs`路径下以便Runtime框架使用RAML文件显示HTML文档。

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

删除步骤1中的临时`/raml`路径，用你自己的代替。

第一个地址段相同的端点(end-points)必须在同一个.raml文件中，因为第一个地址段决定了resource java 接口的名字。比如，`/foo/bar` 和 `/foo/baz`应该在foo.raml中，并且foo.raml会生成`org/folio/rest/jaxrs/resource/Foo.java`。但是，你也可以通过实现GeneratorExtension[否决命名资源接口的约定](https://github.com/mulesoft-labs/raml-for-jax-rs/issues/111)。

将共享的[RAML utility](https://github.com/folio-org/raml) 文件加到"ramls"路径下的"raml-util"路径中。

```bash
git submodule add https://github.com/folio-org/raml ramls/raml-util
```

"raml1.0" 分支是当前分支和默认分支。

为该模块暴露的对象创建JSON schemas。

使用该 `description` 字段和 `type` 字段来解释内容和用法并添加文档。

使用`"javaType": "org.folio.rest.jaxrs.model.MyEntity"` 更改生成的Java类的名称。

参见 [jsonschema2pojo Reference](https://github.com/joelittlejohn/jsonschema2pojo/wiki/Reference)来了解JSON schema 的详细信息。

GenerateRunner会自动取消引用schema文件并将其放入 `target/classes/ramls/` 目录中。它会扫描 `${basedir}/ramls/` 包含子目录的目录，如果找不到）就扫描 `${basedir}/../ramls/` 使通用ramls目录支持maven子模块。

HTTP响应代码的文档位于[HttpStatus.java](https://github.com/folio-org/raml-module-builder/blob/master/util/src/main/java/org/folio/HttpStatus.java)中。

使用[RAML 200 教程](https://raml.org/developers/raml-200-tutorial#resource-types)中说明的[collection resource type](https://github.com/folio-org/raml/tree/raml1.0/rtypes)提供的collection/collection-item模式。

RMB会在编译时对RAML文件进行一些验证。有一些有用的工具可帮助命令行验证，而有些则可以与文本编辑器集成在一起，例如[raml-cop](https://github.com/thebinarypenguin/raml-cop).

参考 [Use raml-cop to assess RAML, schema, and examples](https://dev.folio.org/guides/raml-cop/)
和 [Primer for RAML and JSON Schema](https://dev.folio.org/start/primer-raml/) 快速上手文档。

支持RAML的文本编辑器非常有用，例如Atom的
[api-workbench](https://github.com/mulesoft/api-workbench)。

记住，通过POM配置可以查看RAML并通过本地[API文档](#api-文档)与应用程序进行交互。

## 添加一个init() 实现

通过实现 `InitAPIs` ，可以添加在部署应用程序之前将运行一次的自定义代码（例如，初始化数据库，创建缓存，创建静态变量等）。您必须实现`init(Vertx vertx, Context context, Handler<AsyncResult<Boolean>> resultHandler)`。每个模块仅支持一种实现。当前，实施应放在实施项目的 `org.folio.rest.impl` 包中。该实现将在Verticle部署期间运行。在init（）完成之前，该Verticle版本不会完成部署。init（）函数基本上可以执行任何操作，但是它必须回调Handler。例如：

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

每当收到大量数据时，RMB就会调用该函数。这意味着对于每个数据块，RMB都会实例化一个新对象，并使用对象中包含的部分数据在一个`java.io.InputStream`对象中调用该对象的函数。

对于每次调用，RMB添加的header `streamed_id` 在当前流中将是唯一的。对于最后一次调用，`complete`提供了标头以指示“流结束”。

从RMB 23.12.0起，如果HTTP客户端在完成之前过早关闭上传，则将使用调用`streamed_abort` handler。

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
- 配置文件，默认为`resources/postgres-conf.json`但可以通过[命令行选项](#命令行选项)设置
- 嵌入式PostgreSQL 使用[默认凭据](#凭据)

默认情况下，嵌入式PostgreSQL包含在运行时中，但仅当DB_ *环境变量和postgres配置文件均不存在时才运行。要使用环境变量或配置文件中的连接参数启动嵌入式PostgreSQL，请添加 `embed_postgres=true` 到命令行(`java -jar mod-notify-fat.jar embed_postgres=true`)。使用PostgresClient.setEmbeddedPort（int）来覆写端口。

运行时框架公开了PostgreSQL异步客户端，该客户端以ORM类型的方式提供CRUD操作。
https://github.com/folio-org/raml-module-builder/blob/master/domain-models-runtime/src/main/java/org/folio/rest/persist/PostgresClient.java

**重要说明：** 当前实现的PostgreSQL客户端假定PostgreSQL中使用JSONB表。这不是强制性的，开发人员可以使用常规PostgreSQL表，但需要实现自己的数据访问层。

**重要说明：** 出于性能方面的考虑，Postgres客户端将返回结果数少于50,000的结果集的准确计数。超过50,000个结果的查询将返回估计的计数。

**I重要说明：** 嵌入式Postgres不能以root用户身份运行。

**重要说明：** 嵌入式Postgres依赖`en_US.UTF-8` (*nix) / `american_usa` (win)语言环境。如果未安装此语言环境，Postgres将无法正常启动。

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

在嵌入式模式下运行时，将从`resources/postgres-conf.json`中读取凭据。如果找不到文件，则默认情况下将使用以下配置：

```text
port: 6000
host: 127.0.0.1
username: username
password: password
database: postgres
```

### 保护数据库配置文件

如前所述，RMB提供的Postgres Client查找名为`postgres-conf.json`的文件。但是，在服务器上以纯文本形式将包含DB密码的文件留给超级用户不是一个好主意。可以加密文件中的密码。加密应该是AES加密（对称块密码）。这种加密是通过密钥完成的。

含义：纯文本密码+密钥=加密密码

RMB带有一个AES类，该类支持生成秘密密钥，对其进行加密和解密, https://github.com/folio-org/raml-module-builder/blob/master/domain-models-runtime/src/main/java/org/folio/rest/security/AES.java

注意，是否使用该类是可选的。

为了使用加密密码，RMB公开了可用于设置密钥（仅存储在内存中）的API。创建数据库连接时，RMB将检查是否已设置密钥。如果设置了密钥，则RMB将使用密钥对密码进行解密，然后使用解密后的密码连接到数据库。否则，它将采用未加密的密码，并按原样使用该密码进行连接。模块也可以通过静态方法`AES.setSecretKey(mykey)`设置密钥AES。

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

例如，类似于`foreignKeys`条目产生的SQL：

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
https://github.com/folio-org/raml-module-builder/blob/master/domain-models-runtime/src/test/java/org/folio/rest/persist/ForeignKeyPerformanceIT.java
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

可以设置服务器选择索引，下一个例子展示了搜索`name=Miller or email=Miller`:

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
- `adj` (substring phrase match: all words of the query string exist consecutively in that order, there may be any
          whitespace and punctuation in between, `title adj "Harry Potter"` matches "The Harry - . - Potter Story")
- `>` `>=` `<` `<=` `<>` (comparison for both strings and numbers)

Note to mask the CQL special characters by prepending a backslash: * ? ^ " \

Use quotes if the search string contains a space, for example `title = "Harry Potter"`.

### CQL: 修饰符

Matching modifiers: Only `masked` is implemented, not `unmasked`, `regexp`,
`honorWhitespace`, `substring`.

Word begin and word end in JSON is only detected at whitespace and punctuation characters
from the ASCII charset, not from other Unicode charsets.

### CQL: 匹配、比较和排序数字

Add the /number modifier to enable number matching, comparing and sorting, for example `age ==/number 18`,
`age >=/number 21` and `sortBy age/number`.

3.4, 3.400, and 0.34e1 match each other when using `==/number`, and 2 is smaller than 19
(in contrast to string comparison where "2" > "19").

This requires that the value has been stored as a JSONB number (`{"age": 19}`)
and not as a JSONB string (`{"age": "19"}`).

### CQL: 匹配ID和外键字段

The id field and any foreign key field is a UUID field and is not searched in the JSONB but in an
extracted proper database table field. An index is automatically created for such a field,
do not add an index entry in schema.json.

`=`, `==`, `<>`, `>`, `>=`, `<`, and `<=` relations are supported for comparison with a valid UUID.

`=`, `==`, and `<>` relations allow `*` for right truncation.

Modifiers are forbidden.

### CQL: 匹配全文

See [PostgreSQL's tsvector full text parser documentation](https://www.postgresql.org/docs/current/textsearch-parsers.html)
how word splitting works when using a full text index. Some notable consequences:

CQL `field adj "bar"` matches `bar`, `bar-baz`, `foo-bar-baz`.

CQL `field adj "bar baz"` matches `bar baz`, `bar-baz`, `foo-bar-baz`, `foo-bar baz`, `bar-baz-foo`.

CQL `field adj "bar-baz"` matches `bar-baz`, but neither `bar baz` nor `foo-bar-baz` nor `foo-bar baz` nor `bar-baz-foo`.

CQL `field adj "123 456"` matches `123 456`, but not `123-456`.

CQL `field adj "123-456"` matches `123-456`, but not `123 456`.

`foo/bar/baz` is a single word, while `foo//bar//baz`, `foo///bar///baz`, `foo////bar////baz`, etc.
are split into the three words `foo`, `/bar`, and `/baz` (always reduced to a single slash).

### CQL: 匹配所有记录

A search matching all records in the target index can be executed with a
`cql.allRecords=1` query. `cql.allRecords=1` can be used alone or as part of
a more complex query, for example
`cql.allRecords=1 NOT name=Smith sortBy name/sort.ascending`

* `cql.allRecords=1 NOT name=Smith` matches all records where name does not contain Smith
   as a word or where name is not defined.
* `name="" NOT name=Smith` matches all records where name is defined but does not contain
   Smith as a word.
* For performance reasons, searching for `*` in any fulltext field will match all records as well.

### CQL: 匹配未定义或空值

A relation does not match if the value on the left-hand side is undefined. (but see the fulltext
`*` case above).
A negation (using NOT) of a relation matches if the value on the left-hand side is
not defined or if it is defined but doesn't match.

* `name=""` matches all records where name is defined.
* `cql.allRecords=1 NOT name=""` matches all records where name is not defined.
* `name==""` matches all records where name is defined and empty.
* `cql.allRecords=1 NOT name==""` matches all records where name is defined and not empty or
   where name is not defined.
* `name="" NOT name==""` matches all records where name is defined and not empty.

### CQL: 匹配数组元素

For matching the elements of an array use these queries (assuming that lang is either an array or not defined, and assuming
an array element value does not contain double quotes):
* `lang ==/respectAccents []` for matching records where lang is defined and an empty array
* `cql.allRecords=1 NOT lang <>/respectAccents []` for matching records where lang is not defined or an empty array
* `lang =/respectCase/respectAccents \"en\"` for matching records where lang is defined and contains the value en
* `cql.allRecords=1 NOT lang =/respectCase/respectAccents \"en\"` for matching records where lang does not
  contain the value en (including records where lang is not defined)
* `lang = "" NOT lang =/respectCase/respectAccents \"en\"` for matching records where lang is defined and
  and does not contain the value en
* `lang = ""` for matching records where lang is defined
* `cql.allRecords=1 NOT lang = ""` for matching records where lang is not defined
* `identifiers == "*\"value\": \"6316800312\", \"identifierTypeId\": \"8261054f-be78-422d-bd51-4ed9f33c3422\"*"`
  (note to use `==` and not `=`) for matching the ISBN 6316800312 using ISBN's identifierTypeId where each element of
  the identifiers array is a JSON object with the two keys value and identifierTypeId, for example

      "identifiers": [ {
        "value": "(OCoLC)968777846", "identifierTypeId": "7e591197-f335-4afb-bc6d-a6d76ca3bace"
      }, {
        "value": "6316800312", "identifierTypeId": "8261054f-be78-422d-bd51-4ed9f33c3422"
      } ]

To avoid the complicated syntax all ISBN values or all values can be extracted and used to create a view or an index:

    SELECT COALESCE(jsonb_agg(value), '[]')
       FROM jsonb_to_recordset(jsonb->'identifiers')
         AS y(key text, value text)
       WHERE key='8261054f-be78-422d-bd51-4ed9f33c3422'

    SELECT COALESCE(jsonb_agg(value), '[]')
      FROM jsonb_to_recordset(jsonb->'identifiers')
        AS x(key text, value text)
      WHERE value IS NOT NULL

### CQL: 用于数组搜索的@-relation 修饰符

RMB 26 or later supports array searches with relation modifiers, that
are particular suited for structures like:

    "property" : [
      {
        "type1" : "value1",
        "type2" : "value2",
        "subfield": "value"
      },
      ...
    ]

An example of this kind of structure is `contributors ` (property) from
mod-inventory-storage . `contributorTypeId` is the type of contributor
(type1).

With CQL you can limit searches to `property1` with regular match in
`subfield`, with type1=value2 with

    property =/@type1=value1 value

Observe that the relation modifier is preceeded with the @-character to
avoid clash with other CQL relation modifiers.

The type1, type2 and subfield must all be defined in schema.json, because
the JSON schema is not known. And also because relation modifiers are
unfortunately lower-cased by cqljava. To match value1 against the
property contents of type1, full-text match is used.

Multiple relation modifiers with value are ANDed together. So

    property =/@type1=value1/@type2=value2 value

will only give a hit if both type1 has value1 AND type2 has value2.

It is also possible to specify relation modifiers without value. This
essentially is a way to override what subfield to search. In this case
the right hand side term is matched. Multiple relation modifiers
are OR'ed together. For example:

    property =/@type1 value

And to match any of the sub properties type1, type2, you could use:

    property =/@type1/@type2 value

In schema.json two new properties, `arraySubfield` and `arrayModifiers`,
specifies the subfield and the list of modifiers respectively.
This can be applied to `ginIndex` and `fullTextIndex`.
schema.json example:

    {
      "fieldName": "property",
      "tOps": "ADD",
      "caseSensitive": false,
      "removeAccents": true,
      "arraySubfield": "subfield",
      "arrayModifiers": ["type1", "type2"]
    }

For the identifiers example we could define things in schema.json with:

    {
      "fieldName": "identifiers",
      "tOps": "ADD",
      "arraySubfield": "value",
      "arrayModifiers": ["identifierTypeId"]
    }

This will allow you to perform searches, such as:

    identifiers = /@identifierTypeId=7e591197-f335-4afb-bc6d-a6d76ca3bace 6316800312
	
### CQL2PgJSON: 多字段索引

CQL2PGjson allows generating and querying indexes that contain multiple columns. The index json object now has support for the following properties:

* sqlExpression
  Allows the user to explicitly define the expression they wish to use in the index

  ```json
        "fieldName": "address",
        "sqlExpression": "concat_space_sql(jsonb->>'city', jsonb->>'state')",	
  ```

* multiFieldNames
  This is a comma separated list of json fields that are to be concatenated together via concat_ws with a space character.
  example:

  ```json
    "fieldName": "address",
    "multiFieldNames": "city,state",
  ```

these 2 examples are equivalent and would be queried by using the fieldName such as:

```
address = Boston MA
```

These fields are optional but mutually exclusive, you only need one of them.


### CQL2PgJSON: 外键交叉表索引查询

CQL2PgJSON supports cross table joins via subquery based on foreign keys.
This allows arbitrary depth relationships in both child-to-parent and parent-to-child direction.

Example relationship: item → holdings_record → instance

Join conditions of this example:
* item.holdingsRecordId = holdings_record.id
* holdings_record.instanceId = instance.id

The field in the child table points to the primary key `id` field of the parent table; the parent table is also called the target table.

* Precede the index you want to search with the table name in camelCase, e.g. `instance.title = "bee"`.
* There is no change with child table fields, use them in the regular way without table name prefix.
* The `foreignKey` entry in schema.json automatically creates an index on the foreign key field.
* For fast queries declare an index on any other searched field like `title` in the schema.json file.
* For a multi-table join use `targetPath` instead of `fieldName` and put the list of field names into the `targetPath` array.
* Use `= *` to check whether a join record exists. This runs a cross index join with no further restriction, e.g. `instance.id = *`.
* The schema for the above example:

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

The property `targetTableAlias` enables that parent table name in CQL queries against the current child table.

The property `tableAlias` enables that child table name in CQL queries against the target/parent table.

If any of these two properties is missing, then that respective foreign key join syntax is disabled.

The name may be different from the table name (`tableName`, `targetTable`). One use case is to change to camelCase, e.g.
`"targetTable": "holdings_record"` and `"targetTableAlias": "holdingsRecord"`. Another use case is
to resolve ambiguity when two foreign keys point to the same target table, example:
```
    {
      "tableName": "item",
      "foreignKeys": [
        {
          "fieldName": "permanentLoanTypeId",
          "tableAlias": "itemWithPermanentLoanType",
          "targetTable": "loan_type",
          "targetTableAlias": "loanType",
          "tOps": "ADD"
        },
        {
          "fieldName": "temporaryLoanTypeId",
          "tableAlias": "itemWithTemporaryLoanType",
          "targetTable": "loan_type",
          "targetTableAlias": "temporaryLoanType",
          "tOps": "ADD"
        }
      ]
    }
```
Running CQL `loanType.name == "Can circulate"` against the item endpoint returns all items where the item's permanentLoanTypeId points to a loan_type where the loan_type's name equals "Can circulate".

Running CQL `temporaryLoanType.name == "Can circulate"` against the item endpoint returns all items where the item's temporaryLoanTypeId points to a loan_type where the loan_type's name equals "Can circulate".

Running CQL `itemWithPermanentLoanType.status == "In transit"` against the loan_type endpoint returns all loan_types where there exists an item that has this loan_type as a permanentLoanType and where the item's status equals "In transit".

Running CQL `itemWithTemporaryLoanType.status == "In transit"` against the loan_type endpoint returns all loan_types where there exists an item that has this loan_type as a temporaryLoanType and where the item's status equals "In transit".

### CQL2PgJSON: 异常

All locally produced Exceptions are derived from a single parent so they can be caught collectively
or individually. Methods that load a JSON data object model pass in the identity of the model as a
resource file name, and may also throw a native `java.io.IOException`.

    CQL2PgJSONException
      ├── FieldException
      ├── SchemaException
      ├── ServerChoiceIndexesException
      ├── CQLFeatureUnsupportedException
      └── QueryValidationException
            └── QueryAmbiguousException

### CQL2PgJSON: 单元测试

To run the unit tests in your IDE, the Unicode input files must have been produced by running maven.
In Eclipse you may use "Run as ... Maven Build" for doing so.

## 租户 API

The Postgres Client support in the RMB is schema specific, meaning that it expects every tenant to be represented by its own schema. The RMB exposes three APIs to facilitate the creation of schemas per tenant (a type of provisioning for the tenant). Post, Delete, and 'check existence' of a tenant schema. Note that the use of this API is optional.

The RAML defining the API:

   https://github.com/folio-org/raml/blob/raml1.0/ramls/tenant.raml

By default RMB includes an implementation of the Tenant API which assumes Postgres being present. Implementation in
 [TenantAPI.java](https://github.com/folio-org/raml-module-builder/blob/master/domain-models-runtime/src/main/java/org/folio/rest/impl/TenantAPI.java) file. You might want to extend/override this because:

1. You want to not call it at all (your module is not using Postgres).
2. You want to provide further Tenant control, such as loading reference and/or sample data.

#### Extending the Tenant Init

In order to implement your tenant API, extend `TenantAPI` class:

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

If you wish to call the Post Tenant API (with Postgres) then just call the corresponding super-class, e.g.:
```java
@Override
public void postTenant(TenantAttributes ta, Map<String, String> headers,
  Handler<AsyncResult<Response>> hndlr, Context cntxt) {
  super.postTenant(ta, headers, hndlr, cntxt);
}
```
(not much point in that though - it would be the same as not defining it at all).

If you wish to load data for your module, that should be done after the DB has been successfully initialized,
e.g. do something like:
```
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

There is no right way to load data, but consider that data load will be both happening for first time tenant
usage of the module and during an upgrade process. Your data loading should be idempotent. If files are stored
as resources and as JSON files, you can use the TenantLoading utility.

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

If data is already in resources, then fine. If not, for example, if in root of
project in project, then copy it with maven-resource-plugin. For example, to
copy `reference-data` to `ref-data` in resources:

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


#### The Post Tenant API

The Postgres based Tenant API implementation will look for a file at `/resources/templates/db_scripts/`
called **schema.json**

The file contains an array of tables and views to create for a tenant on registration (tenant api post)

An example can be found here:

 - https://github.com/folio-org/raml-module-builder/blob/master/domain-models-runtime/src/main/resources/templates/db_scripts/examples/schema.json.example.json

The top level properties in schema.json (some of which are optional) are `scripts`, `tables`, `views` and `exactCount`.

Entries in the json file to be aware of:

For each **table** in `tables` property:

1. `tableName` - name of the table that will be generated - this is the table that should be referenced from the code
2. `generateId` - No longer supported.  This functionality is not stable in Pgpool-II see https://www.pgpool.net/docs/latest/en/html/restrictions.html.  The solution is to generate a UUID in java in the same manner as https://github.com/folio-org/raml-module-builder/blob/v23.11.0/domain-models-runtime/src/main/java/org/folio/rest/persist/PgUtil.java#L358
3. `fromModuleVersion` - this field indicates the version in which the table was created / updated in. When a tenant update is requested - only versions older than the indicated version will generate the declared table. This ensures that if a module upgrades from an older version, the needed tables will be generated for it, however, subsequent upgrades from versions equal or later than the version indicated for the table will not re-generate the table.
    * Note that this is enforced for all tables, views, indexes, FK, triggers, etc. (via the `IF NOT EXISTS` sql Postgres statement)
4. `mode` - should be used only to indicate `delete`
5. `withMetadata` - will generate the needed triggers to populate the metadata section in the json on update / insert
6. `likeIndex` - indicate which fields in the json will be queried using the `LIKE`. Needed for fields that will be faceted on.
    * `fieldName` the field name in the json for which to create the index
    * the `tOps` indicates the table operation - ADD means to create this index, DELETE indicates this index should be removed
    * the `caseSensitive` allows you to create case insensitive indexes (boolean true / false), if you have a string field that may have different casings and you want the value to be unique no matter the case. Defaults to false.
    *  `removeAccents` - normalize accents or leave accented chars as is. Defaults to true.
    * the `whereClause` allows you to create partial indexes, for example:  "whereClause": "WHERE (jsonb->>'enabled')::boolean = true"
    * `stringType` - defaults to true - if this is set to false than the assumption is that the field is not of type text therefore ignoring the removeAccents and caseSensitive parameters.
    * `arrayModifiers` - specifies array relation modifiers supported for some index. The modifiers must exactly match the name of the property in the JSON object within the array.
    * `arraySubfield` - is the key of the object that is used for the primary term when array relation modifiers are in use. This is typically also defined when `arrayModifiers` are also defined.
    * `multiFieldNames` - see [CQL2PgJSON: Multi Field Index](#cql2pgjson-multi-field-index) above
    * `sqlExpression` - see [CQL2PgJSON: Multi Field Index](#cql2pgjson-multi-field-index) above
    * Do not manually add an index for an `id` field or a foreign key field, they get indexed automatically.
7. `ginIndex` - generate an inverted index on the JSON using the `gin_trgm_ops` extension. Allows for left and right truncation LIKE queries and regex queries to run in an optimal manner (similar to a simple search engine). Note that the generated index is large and does not support the full field match (SQL `=` operator and CQL `==` operator without wildcards). See the `likeIndex` for available options.
8. `uniqueIndex` - create a unique index on a field in the JSON
    * the `tOps` indicates the table operation - ADD means to create this index, DELETE indicates this index should be removed
    * the `whereClause` allows you to create partial indexes, for example:  "whereClause": "WHERE (jsonb->>'enabled')::boolean = true"
    * Adding a record will fail if the b-tree index byte size limit of 2712 is exceeded. Consider enforcing a length limit on the field, for example by adding a 600 character limit (multi-byte characters) to the RAML specification.
    * See additional options in the likeIndex section above
9. `index` - create a btree index on a field in the JSON
    * the `tOps` indicates the table operation - ADD means to create this index, DELETE indicates this index should be removed
    * the `whereClause` allows you to create partial indexes, for example:  "whereClause": "WHERE (jsonb->>'enabled')::boolean = true"
    * See additional options in the likeIndex section above
    * The expression is wrapped into left(..., 600) to prevent exceeding the b-tree byte size limit of 2712. Special case: sqlExpression is not wrapped.
10. `fullTextIndex` - create a full text index using the tsvector features of postgres.
    * `removeAccents` can be used, the default `caseSensitive: false` cannot be changed because tsvector always converts to lower case.
    * See [CQL: Matching full text](#cql-matching-full-text) to learn how word splitting works.
    * The `tOps` is optional (like for all indexes), and defaults to ADDing the index.
    * `whereClause` and `stringType` work as for `likeIndex` above.
11. `withAuditing` - Creates an auditing table and a trigger that populates the audit table with the history of the table record whenever an insert, update, or delete occurs. `"withAuditing": true` for enabled, `false` or undefined for disabled.
    * `auditingTableName` The name of the audit table.
    * `auditingFieldName` The field (JSON property) in the audit record that contains the copy of the original record.
    * `"withAuditing": true` automatically creates the auditing table; an entry of the audit table in the "tables" section of schema.json is optional, for example to create indexes.
    * The `auditingSnippet` section allows some customizations to the auditing function with custom SQL in the declare section and the body (for either insert / update / delete).
    * The audit table jsonb column has three fields: `$auditingFieldName` contains the original record (jsonb from the original table), `id` contains a new unique id, `operation` contains `I`, `U`, `D` for insert, update, delete, and `createdDate` contains the time when the audit record was created.
12. `foreignKeys` - adds / removes foreign keys (trigger populating data in a column based on a field in the JSON and creating a FK constraint)
13. `customSnippetPath` - a relative path to a file with custom SQL commands for this specific table
14. `deleteFields` / `addFields` - delete (or add with a default value), a field at the specified path for all JSON entries in the table
15. `populateJsonWithId` - This schema.json entry and the disable option is no longer supported. The primary key is always copied into `jsonb->'id'` on each insert and update.
16. `pkColumnName` - No longer supported. The name of the primary key column is always `id` and is copied into `jsonb->'id'` in each insert and update. The method PostgresClient.setIdField(String) no longer exists.

The **views** section is a bit more self explanatory, as it indicates a viewName and the two tables (and a column per table) to join by. In addition to that, you can indicate the join type between the two tables. For example:
```
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
Behind the scenes this will produce the following statement which will be run as part of the schema creation:

    CREATE OR REPLACE VIEW ${tenantid}_${module_name}.items_mt_view AS
      SELECT u.id, u.jsonb as jsonb, g.jsonb as mt_jsonb
      FROM ${tenantid}_${module_name}.item u
      JOIN ${tenantid}_${module_name}.material_type g
        ON lower(f_unaccent(g.jsonb->>'id')) = lower(f_unaccent(u.jsonb->>'materialTypeId'))

Notice the `lower(f_unaccent(` functions, currently, by default, all string fields will be wrapped in these functions (will change in the future).

A three table join would look something like this:

```
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

The **script** section allows a module to run custom SQLs before table / view creation/updates and after all tables/views have been created/updated.

The fields in the **script** section include:

1. `run` - either `before` or `after` the tables / views are generated
2. `snippet` - the SQL to run
3. `snippetPath` - relative path to a file with SQL script to run. If `snippetPath` is set then `snippet` field will be ignored.
4. `fromModuleVersion` - same as `fromModuleVersion` for table

The **exactCount** section is optonal and the value of the property is
a simple integer with a default value of 1000. Hit counts returned by
get-familify of methods will use an exact hit count up to that value; beyond
that, en estimated hit count is returned. However, for cases when query
parameter is omitted (filter is null), an exact count is still returned.


The tables / views will be generated in the schema named tenantid_modulename

The x-okapi-tenant header passed in to the API call will be used to get the tenant id.
The value used for the module name is the artifactId found in the pom.xml (the parent artifactId is used if one is found).

#### Important information
Right now all indexes on string fields in the jsonb should be declared as case in-sensitive and lower cased. This is how the [CQL to Postgres converter](#cql-contextual-query-language) generates SQL queries, so in order for the indexes generated to be used during query time, the indexes must be declared in a similar manner
```
  {
    "fieldName": "title",
    "tOps": "ADD",
    "caseSensitive": false,
    "removeAccents": true
  }
```

Behind the scenes, the CQL to Postgres query converter will generate regex queries for `=` queries.
For example: `?query=fieldA=ABC` will generate an SQL regex query, which will require a gin index to perform on large tables.

The converter will generate LIKE queries for `==` queries. For example `?query=fieldA==ABC` will generate an SQL LIKE query that will use a btree index (if it exists). For queries that only look up specific ids, etc... the preferred approach would be to query with two equals `==` and hence, declare a regular btree (index).


##### Posting information

Posting a new tenant must include a body. The body should contain a JSON conforming to the [moduleInfoSchema](https://github.com/folio-org/raml/blob/master/schemas/moduleInfo.schema) schema. The `module_to` entry is mandatory, indicating the version module for this tenant. The `module_from` entry is optional and indicates an upgrade for the tenant to a new module version.

The body may also hold a `parameters` property to specify per-tenant
actions/info to be done during tenant creation/update.

##### Encrypting Tenant passwords

As of now (this may change in the future), securing a tenant's connection to the database via an encrypted password can be accomplished in the following way:

 - Set the secret key (as described in the Securing DB Configuration file section)

  The PASSWORD will be replaced with the following:
  encrypt(tenant id with secret key) = **new tenant's password**
  The **new tenant's password** will replace the default PASSWORD value (which is the tenantid_modulename)
  The RMB Postgres client will use the secret key and the passed in tenant id to calculate the tenant's password when DB connections are needed for that tenant. Note that if you use the tenant API and set the secret key - the decrypting of the password will be done by the Postgres Client for each tenant connection.


The RMB comes with a TenantClient to facilitate calling the API via URL.
To post a tenant via the client:

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

#### The Delete Tenant API

When this API is called RMB will basically drop the schema for the tenant (CASCADE) as well as drop the user


**Some Postgres Client examples**


Examples:

Saving a POJO within a transaction:

```java
PoLine poline = new PoLine();

...

postgresClient.save(beginTx, TABLE_NAME_POLINE, poline , reply -> {...
```
Remember to call beginTx and endTx

Querying for similar POJOs in the DB (with or without additional criteria):

```java
Criterion c = new Criterion(new Criteria().addField("id").setJSONB(false).setOperation("=").setValue("'"+entryId+"'"));

postgresClient.get(TABLE_NAME_POLINE, PoLine.class, c,
              reply -> {...
```

The `Criteria` object which generates `where` clauses can also receive a JSON Schema so that it can cast values to the correct type within the `where` clause.

```java
Criteria idCrit = new Criteria("ramls/schemas/userdata.json");
```

## RAMLs API

The RAMLs API is a multiple interface which affords RMB modules to expose their RAML files in a machine readable way. To enable the interface the module must add the following to the provides array of its module descriptor:

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

The interface has a single GET endpoint with an optional query parameter path. Without the path query parameter the response will be an application/json array of the available RAMLs. This will be the immediate RAMLs the module provides. If the query parameter path is provided it will return the RAML at the path if exists. The RAML will have HTTP resolvable references. These references are either to JSON Schemas or RAMLs the module provides or shared JSON Schemas and RAMLs. The shared JSON Schemas and RAMLs are included in each module via a git submodule under the path `raml_util`. These paths are resolvable using the path query parameter.

The RAML defining the API:

https://github.com/folio-org/raml/blob/eda76de6db681076212e20c7f988c3913764b9b0/ramls/ramls.raml

## JSON Schemas API

The JSON Schemas API is a multiple interface which affords RMB modules to expose their JSON Schema files in a machine readable way. To enable the interface the module must add the following to the provides array of its module descriptor:

```JSON
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

The interface has a single GET endpoint with an optional query parameter path.
Without the path query parameter the response will be an "application/json" array of the available JSON Schemas. By default this will be JSON Schemas that are stored in the root of ramls directory of the module. Returned list of schemas can be customized in modules pom.xml file.
Add schema_paths system property to "exec-maven-plugin" in pom.xml running the
`<mainClass>org.folio.rest.tools.GenerateRunner</mainClass>`
specify comma-separated list of directories that should be searched for schema files. To search directory recursively specify
directory in the form of glob expression (e.g. "raml-util/**")
 For example:
```
<systemProperty>
  <key>schema_paths</key>
  <value>schemas/**,raml-util/**</value>
</systemProperty>
```
If the query parameter path is provided it will return the JSON Schema at the path if exists. The JSON Schema will have HTTP resolvable references. These references are either to JSON Schemas or RAMLs the module provides or shared JSON Schemas and RAMLs. The shared JSON Schemas and RAMLs are included in each module via a git submodule under the path `raml_util`. These paths are resolvable using the path query parameter.

The RAML defining the API:

https://github.com/folio-org/raml/blob/eda76de6db681076212e20c7f988c3913764b9b0/ramls/jsonSchemas.raml

## 查询语法

The RMB can receive parameters of different types. Modules can declare a query parameter and receive it as a string parameter in the generated API functions.

The RMB exposes an easy way to query, using [CQL (Contextual Query Language)](#cql-contextual-query-language).
This enables a seamless integration from the query parameters to a prepared "where" clause to query with.

```java
//create object on table.field
CQL2PgJSON cql2pgJson = new CQL2PgJSON("tablename.jsonb");
//cql wrapper based on table.field and the cql query
CQLWrapper cql = new CQLWrapper(cql2pgJson, query);
//query the db with the cql wrapper object
PostgresClient.getInstance(context.owner(), tenantId).get(CONFIG_COLLECTION, Config.class,
          cql, true,
```

The CQLWrapper can also get an offset and limit:

```java
new CQLWrapper(cql2pgJson, query).setLimit(new Limit(limit)).setOffset(new Offset(offset));
```

A CQL querying example:

```sh
http://localhost:<port>/configurations/entries?query=scope.institution_id=aaa%20sortBy%20enabled
```

## Metadata

RMB is aware of the [metadata.schema](https://github.com/folio-org/raml/blob/raml1.0/schemas/metadata.schema). When a request (POST / PUT) comes into an RMB module, RMB will check if the passed-in JSON's schema declares a reference to the metadata schema. If so, RMB will populate the JSON with a metadata section with the current user and the current time. RMB will set both update and create values to the same date/time and to the same user, as accepting this information from the request may be unreliable. The module should persist the creation date and the created by values after the initial POST. For an example of this using SQL triggers see [metadata.ftl](https://github.com/folio-org/raml-module-builder/blob/master/domain-models-runtime/src/main/resources/templates/db_scripts/metadata.ftl). Add [withMetadata to the schema.json](https://github.com/folio-org/raml-module-builder#the-post-tenant-api) to create that trigger.

## Facet 支持

RMB also allows easy faceting of result sets. The grouping / faceting is done in the database.
To add faceting to your API.
1. Add the [faceting RAML trait](https://github.com/folio-org/raml/blob/master/traits/facets.raml) to your RAML and reference it from the endpoint (using the is:[])
    - facet query parameter format: `facets=a.b.c` or `facets=a.b.c:10` (they are repeating). For example `?facets=active&facets=personal.lastName`
2. Add the [resultInfo.schema](https://github.com/folio-org/raml/blob/master/schemas/resultInfo.schema) to your RAML and reference it within your collection schemas.
For example:
```
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
```
3. When building your module, an additional parameter will be added to the generated interfaces of the faceted endpoints. `List<String> facets`. You can simply convert this list into a List of Facet objects using the RMB tool as follows: `List<FacetField> facetList = FacetManager.convertFacetStrings2FacetFields(facets, "jsonb");` and pass the `facetList` returned to the `postgresClient`'s `get()` methods.

You can set the amount of results to facet on by calling (defaults to 10,000) `FacetManager.setCalculateOnFirst(20000);`
Note that higher numbers will potentially affect performance.

4. Faceting on array fields can be done in the following manner:
`personal.secondaryAddress[].postalCode`
`personal.secondaryAddress[].address[].postalCode`

NOTE: Creating an index on potential facet fields may be required so that performance is not greatly hindered

## JSON Schema 字段

It is possible to indicate that a field in the JSON is a readonly field when declaring the schema. `"readonly": true`. From example:
```
    "resultInfo": {
      "$ref": "raml-util/schemas/resultInfo.schema",
      "readonly" : true
    }
```
A `readonly` field is not allowed to be passed in as part of the request. A request that contains data for a field that was declared as `readonly` will have its read-only fields removed from the passed in data by RMB (the data will be passed into the implementing functions without the read-only fields)

This is part of a framework exposed by RMB which allows creating a field and associating a validation constraint on that field.

To add a custom field, add a system property (in the configuration) to the plugin definition (in the pom.xml) running the `<mainClass>org.folio.rest.tools.GenerateRunner</mainClass>`

for example:
```
<systemProperty>
    <key>jsonschema.customfield</key>
    <value>{"fieldname" : "readonly" , "fieldvalue": true , "annotation" : "javax.validation.constraints.Null"}</value>
</systemProperty>
```

the `jsonschema.customfield` key can contain multiple JSON values (delimited by a `;`). Each JSON indicates a field name + a field value to match against - and a validation annotation to apply. So, getting back to the readonly field, the example above indicates that a field in the JSON schema that has been tagged with the `readonly` field can not contain data when passed in as part of the request.
A list of available annotations:
https://docs.oracle.com/javaee/7/api/javax/validation/constraints/package-summary.html

To customize generation of java classes, add a system property to plugin definition running `<mainClass>org.folio.rest.tools.GenerateRunner</mainClass>`.
Properties that start with `jsonschema2pojo.config` will be passed to underlying library that generates java classes.
Incomplete list of available properties:
- jsonschema2pojo.config.includeHashcodeAndEquals - adds hashCode and equals methods
- jsonschema2pojo.config.includeToString - adds toString method
- jsonschema2pojo.config.serializable - makes classes serializable

For more available properties see:
 https://joelittlejohn.github.io/jsonschema2pojo/site/1.0.0/generate-mojo.html
 https://github.com/mulesoft-labs/raml-for-jax-rs/blob/master/raml-to-jaxrs/jaxrs-code-generator/src/main/java/org/raml/jaxrs/generator/RamlToJaxRSGenerationConfig.java

## 覆盖 RAML (traits) / 查询参数

A module may require slight changes to existing RAML traits.
For example, a `limit` trait may be defined in the following manner:
 ```
        limit:
          description: Limit the number of elements returned in the response
          type: integer
          required: false
          example: 10
          default: 10
          minimum: 1
          maximum: 2147483647
```
However, a module may not want to allow such a high maximum as this may cause a crash.
A module can create a `raml_overrides.json` file and place it in the `/resources/overrides/` directory.

The file is defined in the schema:
`domain-models-interface-extensions/src/main/resources/overrides/raml_overrides.schema`

Note that `DEFAULTVALUE` only allows string values. `SIZE` requires a range ex. `"15, 20"`. `REQUIRED` does not accept a `"value"`, meaning an optional parameter can become required but not vice versa.

example:
`domain-models-interface-extensions/src/main/resources/overrides/raml_overrides.json`

## Drools 集成

The RMB framework automatically scans the `/resources/rules` path in an implemented project for
`*.drl` files. A directory can also be passed via the command line `drools_dir`. The rule files are loaded and are applied automatically to all objects passed in the body (post,
put) by the runtime framework. This works in the following manner:
 - A POST / PUT request comes in with a body
 - The body for the request is mapped to a generated POJO
 - The POJO is inserted into the RMB's Drools session
 - All rules are run against the POJO

This allows for more complex validation of objects.

- For example, two specific fields can logically be null, but not at the
  same time. That can easily be implemented with a Drool, as those types of
  validations are harder to create in a RAML file.

- The `rules` project also exposes the drools session and allows validation
  within the implemented APIs. See the `tests` in the `rules` project.

For example: (Sample.drl)

```
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

It is also possible to create a Drools session in your code, and load rules into the session in a more dynamic way.
For example:

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

An additional option to use the Drools framework in the RMB is to load rules dynamically. For example, a module may decide to store Drool `.drl` files in a database. This enables a module to allow admin users to update rules in the database and then load them into the RMB validation mechanism for use at runtime.

```java
      Rules rules = new Rules(List<String> rulesLoaded);
      ksession = rules.buildSession();
      RestVerticle.updateDroolsSession(ksession);
```

## Messages

The runtime framework comes with a set of messages it prints out to the logs /
sends back as error responses to incorrect API calls. These messages are
language-specific. In order to add your own message files, place the files in
your project under the `/resources/messages` directory.

Note that the format of the file names should be either:
- `[name]_[lang_2_letters].properties` (e.g.: `APIMessages_de.properties`)
- `[lang_2_letters]_messages.prop` (e.g.: `en_messages.prop`)

For example:
In the circulation project, the messages file can be found at `/circulation/src/main/resources/en_messages.prop` with the following content:

```sh
20002=Operation can not be calculated on a Null Amount
20003=Unable to pay fine, amount is larger than owed
20004=The item {0} is not renewable
20005=Loan period must be greater than 1, period entered: {0}
```

The circulation project exposes these messages as enums for easier usage in the code:

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

Usage:

`private final Messages messages = Messages.getInstance();`

`messages.getMessage(lang, CircMessageConsts.OperationOnNullAmount);`

Note: parameters can also be passed when relevant. The raml-module-builder runtime also exposes generic error message enums which can be found at `/domain-models-runtime/src/main/java/org/folio/rest/tools/messages/MessageConsts.java`

## API 文档

The runtime framework includes a web application which exposes RAMLs in a
view-friendly HTML format.
This uses [api-console](https://github.com/mulesoft/api-console)
(Powered by [MuleSoft](http://www.MuleSoft.org) for RAML
Copyright (c) 2013 MuleSoft, Inc.)

The `maven-resources-plugin` plugin described earlier
copies the RAML files into the correct directory in your project, so that the
runtime framework can access it and show local API documentation.

So for example, when running the [sample working module](#get-started-with-a-sample-working-module)
then its API documentation is at:

```
http://localhost:8081/apidocs/index.html?raml=raml/configuration/config.raml
```

If instead your [new module](#creating-a-new-module) is running on the default port,
then its API documentation is at:

```
http://localhost:8081/apidocs/index.html?raml=raml/my-project.raml
```
and remember to specify the "X-Okapi-Tenant: diku" header.

The RMB also automatically provides other documentation, such as the "Admin API":

```
http://localhost:8081/apidocs/index.html?raml=raml/admin.raml
```

All current API documentation is also available at [dev.folio.org/doc/api](https://dev.folio.org/reference/api/)

## 日志

RMB uses the Log4J logging library. Logs that are generated by RMB will print all log entries in the following format:
`%d{dd MMM yyyy HH:mm:ss:SSS} %-5p %C{1} %X{reqId} %m%n`

A module that wants to generate log4J logs in a different format can create a log4j.properties file in the /resources directory.

The log levels can also be changed via the `/admin` API provided by the framework. For example:

Get log level of all classes:

(GET) `http://localhost:8081/admin/loglevel`

Change log level of all classes to FINE:

(PUT) `http://localhost:8081/admin/loglevel?level=FINE`

A `java_package` parameter can also be passed to change the log level of a specific package. For example:

 `http://localhost:8081/admin/loglevel?level=INFO&java_package=org.folio.rest.persist.PostgresClient`

 `http://localhost:8081/admin/loglevel?level=INFO&java_package=org.folio.rest.persist`

## 监控

The runtime framework via the `/admin` API exposes (as previously mentioned) some APIs to help monitor the service (setting log levels, DB information).
Some are listed below (and see the [full set](#documentation-of-the-apis)):

 - `/admin/jstack` -- Stack traces of all threads in the JVM to help find slower and bottleneck methods.
 - `/admin/memory` -- A jstat type of reply indicating memory usage within the JVM on a per pool basis (survivor, old gen, new gen, metadata, etc.) with usage percentages.
 - `/admin/slow_queries` -- Queries taking longer than X seconds.
 - `/admin/cache_hit_rates` -- Cache hit rates in Postgres.
 - `/admin/table_index_usage` -- Index usage per table.
 - `/admin/postgres_table_size` -- Disk space used per table.
 - `/admin/postgres_table_access_stats` -- Information about how tables are being accessed.
 - `/admin/postgres_load` -- Load information in Postgres.
 - `/admin/postgres_active_sessions` -- Active sessions in Postgres.
 - `/admin/health` -- Returns status code 200 as long as service is up.
 - `/admin/module_stats` -- Summary statistics (count, sum, min, max, average) of all select / update / delete / insert DB queries in the last 2 minutes.

## 覆写开箱即用的RMB API
It is possible to over ride APIs that the RMB provides with custom implementations.
For example:
To override the `/health` API to return a relevant business logic health check for a specific module do the following:

1. `extend` the AdminAPI class that comes with the RMB framework - `public class CustomHealthCheck extends AdminAPI` and over ride the `getAdminHealth` function. The RMB will route the URL endpoint associated with the function to the custom module's implementation.

Example:

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

The framework can generate a Client class for every RAML file with a function for every API endpoint in the RAML.

To generate a client API from your RAML add the following plugin to your pom.xml

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

For the monitoring APIs exposed by the runtime framework, changing the log level via the client would look like this:

```java
    AdminClient aClient = new AdminClient("http://localhost:" + 8083, "myuniversityId", "sometoken");
    aClient.putLoglevel(Level.FINE, "org.folio",  apiResponse -> {
      System.out.println(apiResponse.statusCode());
    });
```

Requesting a stack trace would look like this:

```java
    AdminClient aClient = new AdminClient("http://localhost:" + 8083, "myuniversityId", "sometoken");
    aClient.getJstack( trace -> {
      trace.bodyHandler( content -> {
        System.out.println(content);
      });
    });
```

## 通过HTTP查询多个模块

The RMB has some tools available to help:
 - Make HTTP requests to other modules
 - Parse JSON responses received (as well as any JSON for that matter)
 - Merge together / Join JSON responses from multiple modules
 - Build simple CQL query strings based on values in a JSON

#### HTTP Requests

The `HttpModuleClient2` class exposes a basic HTTP Client.
The full constructor takes the following parameters
 - host
 - port
 - tenantId
 - keepAlive - of connections (default: true)
 - connTO - connection timeout (default: 2 seconds)
 - idleTO - idle timeout (default: 5 seconds)
 - autoCloseConnections - close connection when request completes (default: true)
 - cacheTO - cache of endpoint results timeout (in minutes, default: 30)

```
    HttpModuleClient hc = new HttpModuleClient("localhost", 8083, "myuniversity_new2", false);
    Response response = hc.request("/groups");
```

It is recommended to use the `HttpClientFactory` to get an instance of the `HttpModuleClient2`.
The factory will then return either the actual `HttpModuleClient2` class or an instance of the `HttpClientMock2`. To return an instance of the mock client, set the mock mode flag in the vertx config. One way to do this:
`new DeploymentOptions().setConfig(new JsonObject().put(HttpClientMock2.MOCK_MODE, "true"));`
See [mock_content.json](https://github.com/folio-org/raml-module-builder/blob/master/domain-models-runtime/src/test/resources/mock_content.json) for an example of how to associate a url with mocked data and headers

The client returns a `Response` object. The `Response` class has the following members:
  - endpoint - url the response came from
  - code - http returned status code for request
  - (JsonObject) body - the response data
  - (JsonObject) error -  in case of an error - The `error` member will be populated. The
  error object will contain the `endpoint`, the `statusCode`, and the `errorMessage`
  - (Throwable) exception - if an exception was thrown during the API call


The `HttpModuleClient2 request` function can receive the following parameters:
 - `HttpMethod` - (default: GET)
 - `endpoint` - API endpoint
 - `headers` - Default headers are passed in if this is not populated: Content-type=application/json, Accept: plain/test
 - `RollBackURL` - NOT SUPPORTED - URL to call if the request is unsuccessful [a non 2xx code is returned]. Note that if the Rollback URL call is unsuccessful, the response error object will contain the following three entries with more info about the error (`rbEndpoint`, `rbStatusCode`, `rbErrorMessage`)
 - `cachable` - Whether to cache the response
 - `BuildCQL` object - This allows you to build a simple CQL query string from content within a JSON object. For example:
`
Response userResponse =
hc.request("/users", new BuildCQL(groupsResponse, "usergroups[*].id", "patron_group"));
`
This will create a query string with all values from the JSON found in the path `usergroups[*].id` and will generate a CQL query string which will look something like this:
`?query=patron_group==12345+or+patron+group==54321+or+patron_group==09876...`
See `BuildCQL` for configuration options.

The `Response` class also exposes a joinOn function that allow you to join / merge the received JSON objects from multiple requests.

`public Response joinOn(String withField, Response response, String onField, String insertField,
      String intoField, boolean allowNulls)`


The Join occurs with the response initiating the joinOn call:

 - `withField` - the field within the response whose value / values will be used to join
 - `response` - the response to join this response with
 - `onField` - the field in the passed in response whose value / values will be used to join
 - `insertField` - the field in the passed in `response` to push into the current response (defaults to the `onField` value if this is not passed in)
 - `intoField` - the field to populate within this response
 - `allowNulls` - whether to populate with `null` if the field requested to push into the current response is `null` - if set to false - then the field will not be populated with a null value.

Example:

join:

(response1) `{"a": "1","b": "2"}`

with:

(response2) `{"arr2":[{"id":"1","a31":"1"},{"id":"1","a31":"2"},{"id":"1","a32":"4"},{"id":"2","a31":"4"}]}`

returns:
`{"a":"1","b":["1","2"]}`

with the following call:
`response1.joinOn("a", response2, "arr2[*].id", "a31", "b", false)`

Explanation:
Join response1 on field "a" with response2 on field "arr2[*].id" (this means all IDs in the arr2 array. If a match is found take the value found in field "a31" and place it in field "b".
Since in this case a single entry (response1) matches multiple entries from response2 - an array is created and populated. If this was a one-to-one match, then only the single value (whether a JSON object, JSON array, any value) would have been inserted.

#### Parsing

The RMB exposes a simple JSON parser for the vert.x JSONObject. The parser allows getting and setting nested JSON values. The parser allows retrieving values / nested values in a simpler manner.
For example:

`a.b` -- Get value of field 'b' which is nested within a JSONObject called 'a'

`a.c[1].d` -- Get 'd' which appears in array 'c[1]'

`a.'bb.cc'` -- Get field called 'bb.cc' (use '' when '.' in name)

`a.c[*].a2` -- Get all 'a2' values as a List for each entry in the 'c' array


See the `JsonPathParser` class for more info.


#### An example HTTP request

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

## 有关校验的一些补充

Query parameters and header validation
![](images/validation.png)

#### Object validations

![](images/object_validation.png)

#### function example
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
1. RMB handles all routing, so this is abstracted from the developer. However, there are cases where third party functionality may need access to routing information. Once again, this is not to be used for routing, but in order to pass in routing information to a third party (one such example is the pac4j vertx saml client). RMB allows a developer to receive the Vertx RoutingContext object as a parameter to a generated function by indicating the endpoint represented by the function in the pom.xml (uses a comma delimiter for multiple paths).
```java
  <properties>
    <generate_routing_context>/rmbtests/test</generate_routing_context>
  </properties>
```

## 其他工具

#### De-Serializers
At runtime RMB will serialize/deserialize the received JSON in the request body of PUT and POST requests into a POJO and pass this on to an implementing function, as well as the POJO returned by the implementing function into JSON. A module can implement its own version of this. For example, the below will register a de-serializer that will tell RMB to set a User to not active if the expiration date has passed. This will be run when a User JSON is passed in as part of a request
```
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
#### Error handling tool

Making async calls to the PostgresClient requires handling failures of different kinds. RMB exposes a tool that can handle the basic error cases, and return them as a 422 validation error status falling back to a 500 error status when the error is not one of the standard DB errors.

Usage:

```
if(reply.succeeded()){
  ..........
}
else{
   ValidationHelper.handleError(reply.cause(), asyncResultHandler);
}
```
RMB will return a response to the client as follows:

- invalid UUID - 422 status
- duplicate key violation - 422 status
- Foreign key violation - 422 status
- tenant does not exist / auth error to db - 401 status
- Various CQL errors - 422 status
- Anything else will fall back to a 500 status error

RMB will not cross check the raml to see that these statuses have been defined for the endpoint. This is the developer's responsibility.



## 一些REST示例

Have these in the headers - currently not validated hence not mandatory:

- Accept: application/json,text/plain
- Content-Type: application/json;

#### Example 1: Add a fine to a patron (post)

```
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

#### Example 2: Get fines for patron with id

```
http://localhost:8080/patrons/56dbe25ea12958478cec42ba/fines
```

#### Example 3: Get a specific patron

```
http://localhost:8080/patrons/56dbe25ea12958478cec42ba
```

#### Example 4: Get all patrons

```
http://localhost:8080/patrons
```

#### Example 5: Delete a patron (delete)

```
http://localhost:8080/patrons/56dbe791a129584a506fb41a
```

#### Example 6: Add a patron (post)

```
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

Other [RMB documentation](doc/) (e.g. DB schema migration, Upgrading notes).

Other [modules](https://dev.folio.org/source-code/#server-side).

Dedicated [FOLIO Slack](https://wiki.folio.org/display/COMMUNITY/FOLIO+Communication+Spaces#FOLIOCommunicationSpaces-slackSlack)
channel [#raml-module-builder](https://folio-project.slack.com/archives/CC0PHKEMT).

See project [RMB](https://issues.folio.org/browse/RMB)
at the [FOLIO issue tracker](https://dev.folio.org/guidelines/issue-tracker/).

Other FOLIO Developer documentation is at [dev.folio.org](https://dev.folio.org/)