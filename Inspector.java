
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
        //System.out.print("test");

        Field[] fields = c.getDeclaredFields();
        Method[] methods = c.getMethods();
        String name = c.getName(); //The name of the declaring class
        String super_ = c.getSuperclass().toString(); // The name of the declaring class
        List<String> actualFieldNames = getFieldNames(fields);
        System.out.println(name);
        System.out.println(actualFieldNames);
        System.out.println(super_);
        //System.out.println(actualFieldNames);

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
