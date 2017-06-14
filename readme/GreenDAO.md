# GreenDAO单元测试示例

[GreenDAO github](https://github.com/greenrobot/greenDAO)

User：
```
@Entity
public class User {

    // 不能用int
    @Id(autoincrement = true)
    private Long id;

    @Unique
    private int uid;

    private String name;

    public User(int uid, String name) {
        this.uid = uid;
        this.name = name;
    }
}
```

单元测试：
```
public class GreenDAOTest extends GreenDAOCase {

    private DaoSession mDaoSession;
    private UserDao    mUserDAO;

    @Before
    public void setUp() throws Exception {
        Context context = getContext();

        // 创建数据库 build/test.db，数据库名就是路径
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "test.db", null);
        // 获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();

        // 获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        // 获取Dao对象管理者
        mDaoSession = daoMaster.newSession();

        mUserDAO = mDaoSession.getUserDao();
    }

    @Test
    public void testInsert() {
        User user = new User(uid, name);

        mUserDAO.insert(user);

        List<User> users = mUserDAO.loadAll();

        Assert.assertEquals(1, users.size());

        Assert.assertEquals(1, users.get(0).getUid());
        Assert.assertEquals("kk1", users.get(0).getName());
    }
}
```

[GreenDAO单元测试Java](https://github.com/kkmike999/KBUnitTest/blob/master/app/src/test/java/net/kb/test/greenDAO/GreenDAOTest.java)


