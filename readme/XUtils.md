# XUtils3单元测试示例

[XUtils3 Github](https://github.com/wyouflf/xUtils3)

```
@Table(name = "Parent")
public class Parent {

    @Column(name = "ID", isId = true, autoGen = true)
    int id;

    @Column(name = "name")
    String name;
}
```

单元测试：
```
public class XUtilsTest extends XUtilsCase {

    protected DbManager db;

    @Before
    public void setUp() throws Exception {
        x.Ext.setDebug(true); // 是否输出debug日志, 开启debug会影响性能.

        // 本地数据的初始化
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig().setDbName("xutils3_db") //设置数据库名
                                                                 // 设置数据库版本,每次启动应用时将会检查该版本号,
                                                                 // 发现数据库版本低于这里设置的值将进行数据库升级并触发DbUpgradeListener
                                                                 .setDbVersion(1) //
                                                                 // .setDbDir(new File("build/db"))//设置数据库.db文件存放的目录,默认为包名下databases目录下
                                                                 .setAllowTransaction(true)//设置是否开启事务,默认为false关闭事务
                                                                 .setTableCreateListener(new DbManager.TableCreateListener() {
                                                                     @Override
                                                                     public void onTableCreated(DbManager db, TableEntity<?> table) {

                                                                     }
                                                                 })//设置数据库创建时的Listener
                                                                 .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                                                                     @Override
                                                                     public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                                                                         //balabala...
                                                                     }
                                                                 });//设置数据库升级时的Listener,这里可以执行相关数据库表的相关修改,比如alter语句增加字段等

        db = x.getDb(daoConfig);
    }

    @Test
    public void testSave() throws DbException {
        Parent parent = new Parent("name0");

        db.save(parent);

        Parent parent = db.selector(Parent.class)
                          .where("name", "LIKE", "name0")
                          .findFirst();

        Assert.assertEquals("name0", parent.getName());
    }
}
```

[XUtils单元测试Java](https://github.com/kkmike999/KBUnitTest/blob/master/app/src/test/java/net/kb/test/xutils/XUtilsTest.java)




