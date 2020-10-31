
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
        print("CLASS" + "\n" + "Class: " + name, depth);

        getSuperClassNames(c, obj, recursive, depth); //The name of the immediate super-class
        getInterfacesNames(c, obj, recursive, depth);
        getConstructors(c, depth);
        getMethods(c, depth);
        getFields(c, depth);

        // List<String> actualFieldNames = getFieldNames(fields); // The name of the filed
        // print("ActualFieldNames: " + actualFieldNames);
    }

    public void getFields(Class c, int depth) {
      depth += 1;
      Field[] fields = c.getDeclaredFields();
      String modifier = Integer.toString(c.getModifiers());
      print("modifier: " + modifier, depth);

      if (fields.length > 0) {
          for (Field field : fields) {
            String fieldName = field.getName();
            Object fieldType = field.getType();
            print("FieldName: " + fieldName, depth);
            print("FieldType: " + fieldType.toString(), depth);
          }
      }
    }

    public void getConstructors(Class c, int depth) {
      depth += 1;
      Constructor[] constructors = c.getConstructors();
      String modifier = Integer.toString(c.getModifiers());
      print("modifier: " + modifier, depth);
      if (constructors.length > 0) {
          for (Constructor constructor : constructors) {
            print("CONSTRUCTOR" + "\n" + "Constructors Name: " + constructor.getName().toString(), depth);
            //print("parameterTypes: " + parameterTypes.toString());

            Class[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length > 0) {
                for (Class parameterType : parameterTypes) {
                    print("Constructor Parameter Types: " + parameterType.getName().toString(), depth);
                }
            }
          }
      }
    }

    public void getMethods(Class c, int depth) {
      depth += 1;
      Method[] methods = c.getMethods();
      String modifier = Integer.toString(c.getModifiers());
      print("modifier: " + modifier, depth);
      if (methods.length > 0) {
          for (Method method : methods) {
            //Class[] parameterTypes = method.getParameterTypes();
            Class returnType = method.getReturnType();
            print("MethodsName: " + method.getName().toString(), depth);
            //print("parameterTypes: " + parameterTypes.toString());
            print("ReturnType: " + returnType.toString(), depth);

            Class[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length > 0) {
                for (Class parameterType : parameterTypes) {
                    print("Method Parameter Types: " + parameterType.getName().toString(), depth);
                }
            }
          }
      }
    }

    private void getSuperClassNames(Class c, Object obj, boolean recursive, int depth) {
      depth += 1;
      Class superClass = c.getSuperclass();

      if (superClass != null) {
          print("\t" + "SUPERCLASS --> Recursively Insepct" +"\n" + "\t" + "SuperClass: " + superClass.getName().toString(), depth);
          inspectClass(superClass, obj, recursive, depth);
      }
    }

    private void getInterfacesNames(Class c, Object obj, boolean recursive, int depth) {
      depth += 1;
      Class[] interfaces = c.getInterfaces();

      if (interfaces != null) {
        for(Class interfaceName : interfaces) {
          print("INTERFACE" + "\n" + "Interfaces: " + interfaceName.toString(), depth);
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

    private void print(String string, int depth) {
      for (int i = 0; i < depth; i++)
          System.out.print("\t");
      System.out.println(string);
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
