
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
        indent("CLASS", depth);
        indent("Class: " + name, depth);

        getSuperClassNames(c, obj, recursive, depth + 1); //The name of the immediate super-class
        getInterfacesNames(c, obj, recursive, depth + 1);
        getConstructors(c, depth + 1);
        getMethods(c, depth + 1);
        getFields(c, depth + 1);

        // List<String> actualFieldNames = getFieldNames(fields); // The name of the filed
        // indent("ActualFieldNames: " + actualFieldNames);
    }

    public void getFields(Class c, int depth) {
      int depthNum = depth + 1;
      Field[] fields = c.getDeclaredFields();
      String modifier = Integer.toString(c.getModifiers());
      indent("modifier: " + modifier, depth);

      if (fields.length > 0) {
        for (Field field : fields) {
          String fieldName = field.getName();
          Object fieldType = field.getType();
          indent("FieldName: " + fieldName, depth);
          indent("FieldType: " + fieldType.toString(), depth);
        }
      }else {
        indent("FieldName -> NONE", depth);
      }
    }

    public void getConstructors(Class c, int depth) {
      int depthNum = depth + 1;
      Constructor[] constructors = c.getConstructors();
      String modifier = Integer.toString(c.getModifiers());
      if (constructors.length > 0) {
        indent("CONSTRUCTORS" + " ( " + "   " + " ) ", depth);
        indent("Constructors->", depth);
        for (Constructor constructor : constructors) {
          indent("Constructors Name: " + constructor.getName().toString(), depth);
          indent("modifier: " + modifier, depthNum);

          Class[] parameterTypes = constructor.getParameterTypes();
          if (parameterTypes.length > 0) {
              for (Class parameterType : parameterTypes) {
                  indent("Constructor Parameter Types: " + parameterType.getName().toString(), depthNum);
              }
          }
        }
      }else {
        indent("Constructor -> NONE", depth);
      }
    }

    public void getMethods(Class c, int depth) {
      int depthNum = depth + 1;
      Method[] methods = c.getMethods();
      String modifier = Integer.toString(c.getModifiers());
      indent("Modifier: " + modifier, depth);
      if (methods.length > 0) {
          indent("METHODS" + " ( " + "   " + " ) ", depth);
          indent("Methods->", depth);
          for (Method method : methods) {
            Class[] exceptions = method.getExceptionTypes();
            if (exceptions.length > 0) {
                for (Class exception: exceptions) {
                    indent("Exceptions: " + exception.getName().toString(), depthNum);
                }
            }
            Class[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length > 0) {
                for (Class parameterType : parameterTypes) {
                    indent("Method Parameter Types: " + parameterType.getName().toString(), depthNum);
                }
            }
            Class returnType = method.getReturnType();
            indent("MethodsName: " + method.getName().toString(), depth);
            indent("ReturnType: " + returnType.toString(), depthNum);
          }
      }else {
        indent("Method -> NONE", depth);
      }

    }

    public void getSuperClassNames(Class c, Object obj, boolean recursive, int depth) {
      int depthNum = depth + 1;
      Class superClass = c.getSuperclass();

      if (superClass != null) {
          indent("SUPERCLASS --> Recursively Insepct", depthNum);
          indent("SuperClass: " + superClass.getName().toString(), depthNum);
          inspectClass(superClass, obj, recursive, depthNum);
      }else {
        indent("SuperClass -> NONE", depth);
      }
    }

    public void getInterfacesNames(Class c, Object obj, boolean recursive, int depth) {
      int depthNum = depth + 1;
      Class[] interfaces = c.getInterfaces();

      if (interfaces != null) {
        for(Class interfaceName : interfaces) {
          indent("INTERFACE", depth);
          indent("Interfaces: " + interfaceName.toString(), depth);
          inspectClass(interfaceName, obj, recursive, depthNum);
        }
      }else {
        indent("Interface -> NONE", depth);
      }
    }

    public static List<String> getFieldNames(Field[] fields) {
      List<String> fieldNames = new ArrayList<>();
      for (Field field : fields)
        fieldNames.add(field.getName());
      return fieldNames;
    }

    private void indent(String string, int depth) {
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
// i. If the field is an object reference, and recursive is set to false, then simply indent out the “reference value” directly (this will be the name of the object’s class plus the object’s “identity hash code” ex. java.lang.Object@7d4991ad).
// ii. If the field is an object reference, and recursive is set to true, then immediately recurse on the object.
