import junit.framework.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.logging.Logger;

/**
 * Created by prakashbhanu57 on 4/12/2015.
 */
public class CompanyTest {

    Logger logger = Logger.getLogger(Company.class.getName());

    @org.junit.Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGettingClassInfo() {
        Class<Company> companyReflectClass = Company.class;
        logger.info(companyReflectClass.getName());

        int classModifiers = companyReflectClass.getModifiers();
        logger.info("is it public " + classModifiers + " " + Modifier.isPublic(classModifiers));
        logger.info("is it final" + classModifiers + " " + Modifier.isFinal(classModifiers));
        logger.info("is it private" + classModifiers + " " + Modifier.isPrivate(classModifiers));

    }

    @Test
    public void testGettingPrivateFieldInfo() {
        Class<Company> companyReflectClass = Company.class;
        try {
            Field field = companyReflectClass.getDeclaredField("companyName");
            field.setAccessible(true);
            String valueOfComapnyName = (String) field.get(new Company());
            logger.info("value of private String  " + valueOfComapnyName);

        } catch (NoSuchFieldException e) {
            logger.severe("Filed doesn't exist" + e.getMessage());
        } catch (IllegalAccessException e) {
            logger.severe("Pass a valid parameter" + e.getMessage());
        }
    }

    @Test
    public void testPrivateMethodsWithOutParameters() {
        Class<Company> companyReflectClass = Company.class;
        try {
            Method method = companyReflectClass.getDeclaredMethod("companySecretCodes");
            method.setAccessible(true);
            String companySecret = (String) method.invoke(new Company(), null);
            Assert.assertEquals("Even though we are product based company we don't provide you hike", companySecret);

        } catch (IllegalAccessException e) {
            logger.severe("method is not accessible" + e.getMessage());
        } catch (InvocationTargetException | NoSuchMethodException e) {
            logger.severe("no method found" + e.getMessage());
        }
    }

    @Test
    public void testPrivateMethodsWithParameters() {
        Class<Company> companyReflectClass = Company.class;
        Method method;
        try {

            Class[] parameterTypes = new Class[]{Integer.TYPE, String.class};
            method = companyReflectClass.getDeclaredMethod("companysEmployeSecurityToken", parameterTypes);
            method.setAccessible(true);

            Object[] parameterValues = new Object[]{123, "Raju"};
            String companySecretToken = (String) method.invoke(new Company(), parameterValues);
            logger.info(companySecretToken);
            Assert.assertEquals("123Raju", companySecretToken);

        } catch (IllegalAccessException e) {
            logger.severe("method is not accessible" + e.getMessage());
        } catch (InvocationTargetException | NoSuchMethodException e) {
            logger.severe("no method found" + e.getMessage());
        }
    }

}