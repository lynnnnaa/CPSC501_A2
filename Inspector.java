
/**
 * CPSC 501 - Assignemt 2
 * Reflective Object Inspector
 *
 * @author Jiarong Xu
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
        getFields(c, obj, recursive, depth + 1);

    }

    public void getFields(Class c, Object obj, boolean recursive, int depth) {
      int depthNum = depth + 1;
      Field[] fields = c.getDeclaredFields();
      indent("modifier: " + Integer.toString(c.getModifiers()), depth);

      if (fields.length > 0) {
        for (Field field : fields) {
          Object fieldObj = null;
          field.setAccessible(true);

          try {
              fieldObj = field.get(obj);
          } catch (IllegalAccessException e) {
              System.out.println("ERROR: Unable to access the field");
          }

          if(fieldObj == null){
            indent("Value: null", depthNum);
          }else if(field.getType().isPrimitive()){
            indent("Value: " + fieldObj, depthNum);
          }else if(field.getType().isArray()){
            getArray(fieldObj, recursive, depthNum);
          }else{
            if(recursive){
              inspectClass(fieldObj.getClass(), fieldObj, true, depthNum);
            }else{
              indent("Value: " + obj.getClass().getName() + "@" + obj.getClass().hashCode(), depthNum);
            }
         }

          indent("FieldName: " + field.getName(), depthNum);
          indent("FieldType: " + field.getType().toString(), depthNum);
        }
      }else {
        indent("Field -> NONE", depthNum);
      }
    }

    public void getArray(Object obj, boolean recursive, int depth) {
      int depthNum = depth + 1;
      if(recursive){
        Class componentType  = obj.getClass().getComponentType();
        int length = Array.getLength(obj);
        indent("Array Type: " + componentType.getName(), depthNum);
        indent("Array Length: " + length, depthNum);
        for (int i = 0; i < length; i++) {
          Object arrayInfo = Array.get(obj,i);
            if(arrayInfo == null){
              indent("Array Value: Null", depthNum);
            }else if(componentType.isPrimitive()){
              indent("Array Value: " + arrayInfo, depthNum);
            }else if(componentType.isArray()){
              getArray(obj, recursive, depthNum);
            }else{
              if(recursive){
                inspectClass(obj.getClass(), obj, true, depthNum);
              }else{
                indent("Value: " + obj.getClass().getName() + "@" + obj.getClass().hashCode(), depthNum);
              }
           }
        }
      }else{
        indent("Value: " + obj.getClass().getName() + "@" + obj.getClass().hashCode(), depthNum);
      }
    }

    public void getConstructors(Class c, int depth) {
      int depthNum = depth + 1;
      Constructor[] constructors = c.getConstructors();
      if (constructors.length > 0) {
        indent("CONSTRUCTORS" + " ( " + c.getClass().getName() + " ) ", depth);
        indent("Constructors->", depth);
        for (Constructor constructor : constructors) {
          indent("Name: " + constructor.getName().toString(), depthNum);

          Class[] exceptions = constructor.getExceptionTypes();
          if (exceptions.length > 0) {
              for (Class exception: exceptions) {
                  indent("Exceptions: " + exception.getName().toString(), depthNum);
              }
          }

          Class[] parameterTypes = constructor.getParameterTypes();
          if (parameterTypes.length > 0) {
              for (Class parameterType : parameterTypes) {
                  indent("Constructor Parameter Types: " + parameterType.getName().toString(), depthNum);
              }
          }
          String modifier = Integer.toString(c.getModifiers());
          indent("Modifiers: " + modifier, depthNum);
        }
      }else {
        indent("Constructor -> NONE", depth);
      }
    }

    public void getMethods(Class c, int depth) {
      int depthNum = depth + 1;
      Method[] methods = c.getMethods();
      if (methods.length > 0) {
        indent("METHODS" + " ( " + c.getClass().getName() + " ) ", depth);
        indent("Methods->", depth);
        for (Method method : methods) {
          indent("Name: " + method.getName().toString(), depthNum);
          Class[] exceptions = method.getExceptionTypes();
          if (exceptions.length > 0) {
              for (Class exception: exceptions) {
                  indent("Exceptions: " + exception.getName().toString(), depthNum);
              }
          }
          Class[] parameterTypes = method.getParameterTypes();
          if (parameterTypes.length > 0) {
              for (Class parameterType : parameterTypes) {
                  indent("Parameter types: " + parameterType.getName().toString(), depthNum);
              }
          }else {
            indent("Parameter -> NONE", depthNum);
          }
          Class returnType = method.getReturnType();
          indent("Return types: " + returnType.toString(), depthNum);
          String modifier = Integer.toString(c.getModifiers());
          indent("Modifiers: " + modifier, depthNum);
        }

      }else {
        indent("Method -> NONE", depth);
      }

    }

    public void getSuperClassNames(Class c, Object obj, boolean recursive, int depth) {
      int depthNum = depth + 1;
      Class superClass = c.getSuperclass();
      if(c.equals(Object.class))return;
      if (superClass != null) {
          indent("SUPERCLASS --> Recursively Insepct", depthNum);
          indent("SuperClass: " + superClass.getName(), depthNum);
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
          indent("INTERFACES" + " ( " + c.getClass().getName() + " ) ", depth);
          indent("Interfaces->", depth);
          indent("Name: " + interfaceName.getName(), depth);
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
