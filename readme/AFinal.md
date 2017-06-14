# AFinal单元测试示例

[AFinal Github](https://github.com/yangfuhai/afinal)

单元测试：
```
public class AfinalTest extends AFinalCase {

    FinalDb finalDb;

    @Before
    public void setUp() throws Exception {
        Context context = getContext();

        finalDb = FinalDb.create(context, false);
    }

    @Test
    public void testSave() {
        saveBean("kkmike999");

        List<Bean> beanRS = finalDb.findAll(Bean.class);
        Bean       b      = beanRS.get(0);

        Assert.assertEquals("kkmike999", b.getName());
    }

    @Test
    public void testDelete() {
        saveBean("kk");

        List<Bean> beanRS = finalDb.findAll(Bean.class);
        Bean       b      = beanRS.get(0);

        finalDb.delete(b);

        Assert.assertEquals(0, finalDb.findAll(Bean.class).size());
    }

    @Test
    public void testDeleteById() {
        saveBean("kk");

        List<Bean> beanRS = finalDb.findAll(Bean.class);
        Bean       b      = beanRS.get(0);

        finalDb.deleteById(Bean.class, b.getId());

        Assert.assertEquals(0, finalDb.findAll(Bean.class).size());
    }

    @Test
    public void testFindByWhere() {
        saveBean(0, "kk0");
        saveBean(1, "kk1");

        List<Bean> beanKKs = finalDb.findAllByWhere(Bean.class, "name LIKE 'kk%' ORDER BY uid");

        Assert.assertEquals(2, beanKKs.size());

        Bean bean0 = beanKKs.get(0);
        Bean bean1 = beanKKs.get(1);

        Assert.assertEquals("kk0", bean0.getName());
        Assert.assertEquals("kk1", bean1.getName());
    }

    @Test
    public void testFindByWhereOrderBy() {
        saveBean(0, "kk0");
        saveBean(1, "kk1");

        List<Bean> beanKKs = finalDb.findAllByWhere(Bean.class, "name LIKE 'kk%'", "uid DESC");

        Assert.assertEquals(2, beanKKs.size());

        Bean bean1 = beanKKs.get(0);
        Bean bean0 = beanKKs.get(1);

        // 倒叙排序
        Assert.assertEquals("kk1", bean1.getName());
        Assert.assertEquals("kk0", bean0.getName());
    }

    @Test
    public void testDropDb() {
        saveBean(0, "kk0");

        finalDb.dropDb();

        Assert.assertEquals(0, finalDb.findAll(Bean.class).size());
    }

    @Test
    public void testUpdate() {
        saveBean(0, "kk");

        Bean bean = finalDb.findAll(Bean.class).get(0);
        bean.setName("kk update");

        finalDb.update(bean);

        Bean beanUpdate = finalDb.findAll(Bean.class).get(0);

        Assert.assertEquals("kk update", beanUpdate.getName());
    }

    @Test
    public void testUpdateByWhere() {
        saveBean(1, "kk");

        Bean bean = new Bean(1, "kk update");

        finalDb.update(bean, "uid=1");

        Bean beanUpdate = finalDb.findAll(Bean.class).get(0);

        Assert.assertEquals("kk update", beanUpdate.getName());
    }

    private void saveBean(String name) {
        saveBean(new Random().nextInt(99999), name);
    }

    private void saveBean(int uid, String name) {
        Bean bean = new Bean(uid, name);

        finalDb.save(bean);
    }
}
```

[AFinal单元测试Java](https://github.com/kkmike999/KBUnitTest/blob/master/app/src/test/java/net/kb/test/afinal/AfinalTest.java)