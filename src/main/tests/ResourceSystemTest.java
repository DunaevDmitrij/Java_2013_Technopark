import Global.ResSystem.ResourceSystem;
import Global.ResSystem.XML_Convertable;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 14.12.13
 * Time: 9:15
 * Тестирование для ресурсной системы
 */

public class ResourceSystemTest {
    // Объект для тестирования
    private final TestSystem testInstance = TestSystem.getRS();
    // Файл с xml-данными, для тестирования
    private static final String TEST_XML = "test.xml";
    private static final String TEST_DIR = "src/main/tests";
    private static final String TEST_CLASS = "ResourceSystemTest$Student";

    public ResourceSystemTest () {
        super();
        // Загрузка файла через ресурсную систему
        this.testInstance.<Student> loadResourceRemote(TEST_DIR, TEST_XML, TEST_CLASS);
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testStudent_1() throws Exception {
        final String ErrText = "Файл xml прочитан неправильно: ";

        // Выбираем запись по уникальному ключу
        Student testRecord = this.testInstance.getRecord(TEST_XML, "Makarov Dmitriy");
        Assert.assertEquals(ErrText, testRecord.getAge(),  19);
    }

    @Test
    public void testStudent_2() throws Exception {
        final String ErrText = "Файл xml прочитан неправильно: ";

        Student testRecord = this.testInstance.getRecord(TEST_XML, "Myagotina Olya");
        // Ей и правда 20 лет. См. test.xml
        Assert.assertEquals(ErrText, testRecord.getAge(),  20);
    }

    @Test
    public void testStudent_3() throws Exception {
        final String ErrText = "Файл xml прочитан неправильно: ";

        // На самом деле Ионову Алексею 20 лет
        Student testRecord = this.testInstance.getRecord(TEST_XML, "Ionov Aleksey");
        Assert.assertNotSame(ErrText, testRecord.getAge(),  22);
    }

    @Test
    public void testStudent_4() throws Exception {
        final String ErrText = "Файл xml прочитан неправильно: ";

        // Записи Секирин Павел в тестовом файле не существует
        Student testRecord = this.testInstance.getRecord(TEST_XML, "Sekirin Pavel");
        Assert.assertEquals(ErrText, testRecord,  null);
    }

    // Класс представления xml записи в тестовом примере.
    // Абстракция студента
    public static class Student implements XML_Convertable {
        private String name;
        private String surname;
        private int age;

        // Полное имя студента "Фамилия Имя"
        public String getFullName() {
            return this.surname + " " + this.name;
        }

        public int getAge() {
            return this.age;
        }

        // Уникальный ключ - полное имя студента
        @Override
        public String getUnique() {
            return this.getFullName();
        }

        // Проверка внутри обработчика xml ищет имена ключевых полей
        // как вхождения подстрок в результат этого метода
        @Override
        public String getUniqueFields() {
            return "name surname";
        }
    }

    // Класс - обертка над ресурсной системой для тестирования
    private static class TestSystem extends ResourceSystem {
        // тоже синглтон
        private static final TestSystem instance = new TestSystem();

        public static TestSystem getRS() {
            return instance;
        }

        protected TestSystem() {
            super();
        }

        // Метод - обертка для загрузки xml данных
        public <ContentType extends XML_Convertable>
        void loadResourceRemote(String xmlDir, String xmlFile, String className) {
            this.<ContentType> loadResource(xmlDir, xmlFile, className);
        }
    }
}
