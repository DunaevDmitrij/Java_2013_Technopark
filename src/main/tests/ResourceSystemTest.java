import Global.MessageSystem;
import Global.MsgSystem.MessageSystemImp;
import Global.ResSystem.ResourceSystemImp;
import Global.ResSystem.XML_Convertable;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 14.12.13
 * Time: 9:15
 */

public class ResourceSystemTest {
    private final TestSystem testInstance = new TestSystem();;
    private static final String TEST_XML = "test.xml";

    public ResourceSystemTest () {
        super();
        this.testInstance.<Student> loadResourceRemote(TEST_XML, "ResourceSystemTest$Student");
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testStudent_1() throws Exception {
        final String ErrText = "Файл xml прочитан неправильно: ";

        Student testRecord = this.testInstance.getRecord(TEST_XML, "Makarov Dmitriy");
        Assert.assertEquals(ErrText, testRecord.getAge(),  19);
    }

    @Test
    public void testStudent_2() throws Exception {
        final String ErrText = "Файл xml прочитан неправильно: ";

        Student testRecord = this.testInstance.getRecord(TEST_XML, "Myagotina Olya");
        Assert.assertEquals(ErrText, testRecord.getAge(),  20);
    }

    @Test
    public void testStudent_3() throws Exception {
        final String ErrText = "Файл xml прочитан неправильно: ";

        Student testRecord = this.testInstance.getRecord(TEST_XML, "Ionov Aleksey");
        Assert.assertNotSame(ErrText, testRecord.getAge(),  22);
    }

    @Test
    public void testStudent_4() throws Exception {
        final String ErrText = "Файл xml прочитан неправильно: ";

        Student testRecord = this.testInstance.getRecord(TEST_XML, "Sekirin Pavel");
        Assert.assertEquals(ErrText, testRecord,  null);
    }

    public static class Student implements XML_Convertable {
        private String name;
        private String surname;
        private int age;

        public String getFullName() {
            return this.surname + " " + this.name;
        }

        public int getAge() {
            return this.age;
        }

        @Override
        public String getUnique() {
            return this.getFullName();
        }

        @Override
        public String getUniqueFields() {
            return "name surname";
        }
    }

    private class TestSystem extends ResourceSystemImp {
        public TestSystem() {
            super();
        }

        public <ContentType extends XML_Convertable>
        void loadResourceRemote(String xmlFile, String className) {
            this.<ContentType> loadResource(xmlFile, className);
        }
    }
}
