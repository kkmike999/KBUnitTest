[![](https://www.bintray.com/docs/images/bintray_badge_color.png)](https://bintray.com/kkmike999/maven/kb_unit_test?source=watch)

## KBUnitTest介绍

**KBUnitTest**是一款轻量级DAO单元测试框架，开发者可以通过此框架，在Android Studio运行SQLiteDatabase、SharedPreference单元测试。

KBUnitTest支持原生SQLiteDatabase操作及GreenDAO、Afinal、XUtils、DbFlow第三方库。

## 版本说明

[CHANGELOG](CHANGELOG.md)

## 引用

在工程**build.gradle**添加maven url:

```
allprojects {
    repositories {
        jcenter()
        maven { url 'https://dl.bintray.com/kkmike999/maven' }
    }
}
```

在module的**build.gradle**添加依赖：
```
dependencies {
    testCompile 'net.kb.test:kb_unit_test:0.2.2'
}
```

### exclude

如果KBUnitTest引用的第三方库，与你工程引用的第三方库有版本不同，会引起冲突。这时，需要`compile`的时候传递依赖：

(例如，工程引用了`org.jsoup:jsoup`和`com.android.support:support-annotations`)
```
dependencies {
    testCompile ('net.kb.test:kb_unit_test:0.2.2'){
        transitive = true

        exclude module: 'jsoup', group: 'org.jsoup'
        exclude module: 'support-annotations', group: 'com.android.support'
    }
}
```

## 配置

在Android Studio操作栏，`Run`->`EditConfigurations`，双击`Defaults`，选择`Android JUnit`窗口，找到`Working directory`参数栏，点击最右边的`...`选择`MODULE_DIR`。

![Run->EditConfigurations](http://linked-runner-file.b0.upaiyun.com/github/kb_unit_test/edit_configurations.png)
![Android Junit](http://linked-runner-file.b0.upaiyun.com/github/kb_unit_test/configuration_android_junit.png)
![选择MODULE_DIR](http://linked-runner-file.b0.upaiyun.com/github/kb_unit_test/module_dir.png)

操作示范：
![](http://linked-runner-file.b0.upaiyun.com/github/kb_unit_test/module_dir_operation.gif)

## 使用方法

### 原生SQLiteDatabase

```
public class SQLiteDatabaseTest extends KBCase {

    SQLiteDatabase db;

    @Before
    public void setUp() throws Exception {
        // 使用KBUnitTest提供的Context，获取SQLiteDatabase实例
        db = getContext().openOrCreateDatabase("build/test.db", 0, null);
    }

    @Test
    public void testCreateTable() {
        String sql = "CREATE TABLE person (id INTEGER, name VARCHAR)";

        db.execSQL(sql);
    }
}
```

### GreenDAO

[GreenDAO单元测试示例](readme/GreenDAO.md)

### AFinal

[AFinal单元测试示例](readme/AFinal.md)

### XUtils

[XUtils3单元测试示例](readme/XUtils.md)

### DbFlow

[DbFlow单元测试示例](readme/DbFlow.md)

### SQL语句输出配置

在运行单元测试时，可能会出现执行的SQLite语句：

![](http://linked-runner-file.b0.upaiyun.com/github/kb_unit_test/sql_output.png)

为了使用者清晰地知道，运行的单元测试执行了哪些SQL语句，KBUnitTest默认是输出SQL语句。如果你不需要，可以如下配置：

```
// 设置是否输出SQL语句
DebugHook.setDebug(true);

// 设置是否输出SQLite PRAGMA语句
DebugHook.setPragmaDebug(false);
```

有的ORM框架，会自带SQL输出（例如Afinal），作者建议，打开KBUnitTest的SQL输出，关闭你ORM框架的SQL输出。由于ORM框架最终会调用Android Api的SQLiteDatabase，而KBUnitTest则是对Android Api进行改造，ORM框架必然会调用KBUnitTest的代码，因此ORM框架执行的SQL，都可以在KBUnitTest输出。

关于PRAGMA语句，参考 [《SQLite PRAGMA》](http://www.runoob.com/sqlite/sqlite-pragma.html)。

### SharedPreference单元测试

KBUnitTest引用了作者另外的SharedPreference单元测试库 [SPTestFramework](https://github.com/kkmike999/SPTestFramework)。详细使用，可以查阅该库github。

----

## 不支持

KBUnitTest还**不支持子查询和联表查询**，作者有计划以后支持。

## 兼容问题

由于KBUnitTest重写了部分Android Api，例如`Log`、`TextUtils`，如果你的项目在`src/test/java`或者其他地方也重写了该类，可能会与KBUnitTest冲突。

作者建议你，把KBUnitTest fork到你的仓库，修改后，发布到bintray，项目里直接引用修改后的库。如果笔者更新了KBUnitTest的代码，你可以在本地git pull KBUnitTest，合并后，再更改版本发布到bintray。

## 提bug

作者希望，你能使用KBUnitTest执行更多测试用例，并把发现的bug在github issue提出。

----

## 关于作者

我是键盘男。
在广州生活，悦跑圈Android工程师，猥琐文艺码农。每天谋划砍死产品经理。喜欢科学、历史，玩玩投资，偶尔旅行。

简书：[键盘男](http://www.jianshu.com/u/0ef3dc77079c)
