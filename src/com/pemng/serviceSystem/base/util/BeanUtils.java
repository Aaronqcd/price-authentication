package com.pemng.serviceSystem.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

/**
 * apache BeanUtils
 * 
 * @author admin
 * 
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

	protected static final Log logger = LogFactory.getLog(BeanUtils.class);

	private BeanUtils() {
	}

	public static Field getDeclaredField(Object object, String propertyName)
			throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		return getDeclaredField(object.getClass(), propertyName);
	}

	public static Field getDeclaredField(Class clazz, String propertyName)
			throws NoSuchFieldException {
		Assert.notNull(clazz);
		Assert.hasText(propertyName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(propertyName);
			} catch (NoSuchFieldException e) {
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName()
				+ '.' + propertyName);
	}

	public static Object forceGetProperty(Object object, String propertyName)
			throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);

		boolean accessible = field.isAccessible();
		field.setAccessible(true);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.info("error wont' happen");
		}
		field.setAccessible(accessible);
		return result;
	}

	public static void forceSetProperty(Object object, String propertyName,
			Object newValue) throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		try {
			field.set(object, newValue);
		} catch (IllegalAccessException e) {
			logger.info("Error won't happen");
		}
		field.setAccessible(accessible);
	}

	public static Object invokePrivateMethod(Object object, String methodName,
			Object[] params) throws NoSuchMethodException {
		Assert.notNull(object);
		Assert.hasText(methodName);
		Class[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			types[i] = params[i].getClass();
		}

		Class clazz = object.getClass();
		Method method = null;
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				method = superClass.getDeclaredMethod(methodName, types);
				break;
			} catch (NoSuchMethodException e) {
			}
		}

		if (method == null)
			throw new NoSuchMethodException("No Such Method:" + clazz.getName()
					+ methodName);

		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object result = null;
		try {
			result = method.invoke(object, params);
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		method.setAccessible(accessible);
		return result;
	}

	public static List getFieldsByType(Object object, Class type) {
		List list = new ArrayList();
		Field[] fields = object.getClass().getDeclaredFields();
		for (int i = 0, len = (fields == null ? 0 : fields.length); i < len;) {
			if (fields[i].getType().isAssignableFrom(type)) {
				list.add(fields[i]);
			}
		}
		return list;
	}

	public static Class getPropertyType(Class type, String name)
			throws NoSuchFieldException {
		return getDeclaredField(type, name).getType();
	}

	public static String getGetterName(Class type, String fieldName) {
		Assert.notNull(type, "Type required");
		Assert.hasText(fieldName, "FieldName required");

		if (type.getName().equals("boolean")) {
			return "is" + StringUtils.capitalize(fieldName);
		} else {
			return "get" + StringUtils.capitalize(fieldName);
		}
	}

	public static Method getGetterMethod(Class type, String fieldName) {
		try {
			return type.getMethod(getGetterName(type, fieldName), null);
		} catch (NoSuchMethodException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static boolean listIsEmpty(List list) {
		if (list == null || list.size() == 0)
			return true;
		return false;
	}

	public static boolean collectionIsEmpty(Collection col) {
		if (col == null || col.size() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 将相同bean对象的值转换成数组对象
	 * @param objects
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public Object[] reverseValue(List objects, String fieldName) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		
		if(objects == null || objects.isEmpty())
			return new Object[objects.size()];
		
		Object[] retVals = new Object[objects.size()];
		Field field = BeanUtils.getDeclaredField(objects.get(0).getClass(), fieldName);
		for(int i=0; i<retVals.length; i++){
			retVals[i] = field.get(objects.get(i));
		}
		
		return retVals;
	}

	// ==================================================================================================

	/**
	 * 转换oralce数据库字段为char类型对象(从数据库中读出的对象) 只对string类型对象进行处理
	 * 
	 * @param obj
	 * @return
	 */
	public static void convertCharDbObject(Object obj) {
		if (obj == null || obj.getClass() == Object.class) {
			return;
		}

		Class objClass = obj.getClass();
		while (true) {
			Field[] fields = objClass.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				if (field.getType() == java.lang.String.class) { // string类型
					try {
						String propertyName = field.getName();
						Object oVal = forceGetProperty(obj, propertyName);
						if (oVal != null) {
							forceSetProperty(obj, propertyName, ((String) oVal)
									.trim());
						}
					} catch (NoSuchFieldException e) {
						logger.error("", e);
					}
				}
			}
			Class nextClass = objClass.getSuperclass();
			if (nextClass == Object.class || nextClass == null) {
				break;
			}
			objClass = nextClass;
		}

	}
	
	/**
	 * 将 List 转换为 SET
	 * @param 	list
	 * @return 	java.utils.Set
	 */
	public static Set<?> convertList2Set(List<?> list){
		if(null == list){
			return null;
		}
		Set<Object> set = new HashSet<Object>(0);
		for(Object o : list){
			set.add(o);
		}
		return set;
	}
	
	public static List<?> converSet2List(Set<?> set){
		if(null == set){
			return null;
		}
		List<Object> list = new ArrayList<Object>();
		for(Iterator<?> iter = set.iterator(); iter.hasNext();){
			list.add(iter.next());
		}
		return list;
	}

	private static class TestReflect {
		private String f1 = "f111   ";

		private String f2;

		public String f3;

		public void setF1(String f1) {
			this.f1 = f1;
		}

		public void setF2(String f2) {
			this.f2 = f2;
		}

		public void setF3(String f3) {
			this.f3 = f3;
		}
	}

	private static class TestReflect2 extends TestReflect {

		private String f4 = "abc   ";

		private String f5 = "  ddd";

		public String f6;

		public void setF4(String f4) {
			this.f4 = f4;
		}

		public void setF5(String f5) {
			this.f5 = f5;
		}

		public void setF6(String f6) {
			this.f6 = f6;
		}

	}

	public static void main(String[] args) {

		Object oo = new Object();
		System.out.println(oo.getClass().getSuperclass());

		System.out.println(TestReflect2.class.getSuperclass().getSuperclass());

		Field[] fields1 = TestReflect2.class.getFields(); // public field
															// 包含父类的public
		for (int i = 0; i < fields1.length; i++) {
			System.out.println("getFields:" + fields1[i].getName());
			System.out.println("fieldType:" + fields1[i].getType());
			System.out.println("fieldType:"
					+ (fields1[i].getType() == java.lang.String.class));
		}

		Field[] fields2 = TestReflect2.class.getDeclaredFields(); // 自己本身所有的field
		for (int i = 0; i < fields2.length; i++) {
			System.out.println("getDeclaredFields:" + fields2[i].getName());
		}

		TestReflect2 o = new TestReflect2();

		convertCharDbObject(o);

		System.out.println(o);
	}
}
