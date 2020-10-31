
/**
 * CPSC 501
 * Inspector starter class
 *
 * @author Jonathan Hudson
 */

import java.lang.reflect.*;
import java.util.*;

public class Inspector {

    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {

        String name = c.getName(); //The name of the declaring class
        System.out.println("ClassName: " + name);

        getSuperClassNames(c, obj, recursive, depth); //The name of the immediate super-class
        getInterfacesNames(c, obj, recursive, depth);
        getConstructors(c, depth);
        getMethods(c, depth);
        getFields(c, depth);

        // List<String> actualFieldNames = getFieldNames(fields); // The name of the filed
        // System.out.println("ActualFieldNames: " + actualFieldNames);
    }

    public void getFields(Class c, int depth) {
        Field[] fields = c.getDeclaredFields();
        String modifier = Integer.toString(c.getModifiers());
        System.out.println("modifier: " + modifier);

        if (fields.length > 0) {
            for (Field field : fields) {
              String fieldName = field.getName();
              Object fieldType = field.getType();
              System.out.println("fieldName: " + fieldName);
              System.out.println("fieldType: " + fieldType.toString());
            }
        }
    }

    public void getConstructors(Class c, int depth) {
        Constructor[] constructors = c.getConstructors();
        String modifier = Integer.toString(c.getModifiers());
        System.out.println("modifier: " + modifier);
        if (constructors.length > 0) {
            for (Constructor constructor : constructors) {
              System.out.println("ConstructorsName: " + constructor.getName().toString());
              //System.out.println("parameterTypes: " + parameterTypes.toString());

              Class[] parameterTypes = constructor.getParameterTypes();
              if (parameterTypes.length > 0) {
                  for (Class parameterType : parameterTypes) {
                      System.out.println("Constructor Parameter Types: " + parameterType.getName().toString());
                  }
              }
            }
        }
    }

    public void getMethods(Class c, int depth) {
        Method[] methods = c.getMethods();
        String modifier = Integer.toString(c.getModifiers());
        System.out.println("modifier: " + modifier);
        if (methods.length > 0) {
            for (Method method : methods) {
              //Class[] parameterTypes = method.getParameterTypes();
              Class returnType = method.getReturnType();
              System.out.println("MethodsName: " + method.getName().toString());
              //System.out.println("parameterTypes: " + parameterTypes.toString());
              System.out.println("returnType: " + returnType.toString());

              Class[] parameterTypes = method.getParameterTypes();
              if (parameterTypes.length > 0) {
                  for (Class parameterType : parameterTypes) {
                      System.out.println("Method Parameter Types: " + parameterType.getName().toString());
                  }
              }
            }
        }
    }

    private void getSuperClassNames(Class c, Object obj, boolean recursive, int depth) {
      Class superClass = c.getSuperclass();

      if (superClass != null) {
          depth =+ 1;
          System.out.println("super class: " + superClass.toString());
          inspectClass(superClass, obj, recursive, depth);
      }
    }

    private void getInterfacesNames(Class c, Object obj, boolean recursive, int depth) {
      Class[] interfaces = c.getInterfaces();

      if (interfaces != null) {
        for(Class interfaceName : interfaces) {
          depth =+ 1;
          System.out.println("Interfaces: " + interfaceName.toString());
          inspectClass(interfaceName, obj, recursive, depth);
        }
      }
    }

    private static List<String> getFieldNames(Field[] fields) {
    List<String> fieldNames = new ArrayList<>();
    for (Field field : fields)
      fieldNames.add(field.getName());
    return fieldNames;
    }

}


// 1. The name of the declaring class
// 2. The name of the immediate super-class
// a. Always explore super-class immediately and recursively (even if recursive=false)
// 3. The name of each interface the class implements
// a. Always explore interfaces immediately and recursively (even if recursive=false)
// 4. The constructors the class declares. For each, also find the following: a. The name
// b. The exceptions thrown c. The parameter types d. The modifiers
// 5. The methods the class declares. For each, also find the following: a. The name
// b. The exceptions thrown
// c. The parameter and types d. The return-type
// e. The modifiers
// 6. The fields the class declares. For each, also find the following: a. The name
// b. The type
// c. The modifiers
// d. The current value
// i. If the field is an object reference, and recursive is set to false, then simply print out the “reference value” directly (this will be the name of the object’s class plus the object’s “identity hash code” ex. java.lang.Object@7d4991ad).
// ii. If the field is an object reference, and recursive is set to true, then immediately recurse on the object.
