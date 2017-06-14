# DbFlow单元测试示例

DbFlow gradle配置，自行查阅：[DbFlow中文教程]( https://yumenokanata.gitbooks.io/dbflow-tutorials/content/index.html)。

```
@Table(database = DBFlowDatabase.class)
public class UserModel extends BaseModel {
    //自增ID
    @Column
    @PrimaryKey(autoincrement = true)
    public Long   id;
    @Column
    public String name;
    @Column
    public int    sex;

    public String getName() {
        return name;
    }

    public int getSex() {
        return sex;
    }
}
```

单元测试：
```
public class DbFlowTest extends DbFlowCase {

    @Before
    public void setUp() throws Exception {
        FlowManager.init(new FlowConfig.Builder(getApplication()).build());

        Assert.assertEquals(0, new Select(Method.count()).from(UserModel.class).count());
    }

    @Test
    public void onInsert() {
        UserModel people = new UserModel();

        people.name = "张三";
        people.sex = 1;
        people.save();// 添加对象，一条一条保存

        Assert.assertEquals(1, new Select(Method.count()).from(UserModel.class).count());
    }
}
```

[DbFlow单元测试Java](https://github.com/kkmike999/KBUnitTest/blob/master/app/src/test/java/net/kb/test/dbflow/DbFlowTest.java)





