package edu.school21.app;

import edu.school21.models.Car;
import edu.school21.models.User;

import java.lang.reflect.*;
import java.sql.SQLOutput;
import java.util.*;
import java.util.logging.FileHandler;

public class Program {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {

        firstMessage();
        printDelimiter();
        Class clazz = classSelection();
        printDelimiter();
        printFieldsAndMetods(clazz);
        printDelimiter();
        Object object = createObject(clazz);
        printDelimiter();
        updateObject(object);
        printDelimiter();
        metodCall(object);

    }

    private static void metodCall(Object object) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = object.getClass().getMethods();
        List<Object> parsedParam = new LinkedList<>();
        Method methodForCall = null;
        Scanner scanner = new Scanner(System.in);
        while (methodForCall == null) {
            System.out.print("Enter name of the method for changing(without args): \n-> ");
            String methodName = scanner.nextLine();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    methodForCall = method;
                }
            }
            if (methodForCall == null){
                System.out.println("Wrong methodName, let's try again!");
            }
        }
        Parameter[] parameters = methodForCall.getParameters();
        for (Parameter parameter:parameters){
            System.out.print("Enter " + parameter.getType().getSimpleName() + " value: \n-> ");
            parsedParam.add(scannerGetType(parameter.getType().getSimpleName().toLowerCase()));
        }
        Object ret = methodForCall.invoke(object, parsedParam.toArray());
        System.out.println("Method returned: " + ret);
    }

    private static void updateObject(Object object) throws IllegalAccessException {

        Field[] fields = object.getClass().getDeclaredFields();
        Field changeField = null;
        Scanner scanner = new Scanner(System.in);
        while (changeField == null) {
            System.out.print("Enter name of the field for changing: \n-> ");
            String fieldName = scanner.nextLine();
            for (Field field : fields) {
                if (field.getName().equals(fieldName))
                    changeField = field;
            }
            if (changeField == null){
                System.out.println("Wrong fieldName, let's try again!");
            }
        }
        changeField.setAccessible(true);
        System.out.print("Enter " + changeField.getType().getSimpleName() + " value:\n-> ");
        changeField.set(object, scannerGetType(changeField.getType().getSimpleName().toLowerCase()));
        changeField.setAccessible(false);
        System.out.println("Object updated:" + object);
    }

    private static Object createObject(Class clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {

        System.out.println("Let’s create an object.");
        Constructor[] constructors = clazz.getDeclaredConstructors();
        Constructor needfulConst = null;
        Object ret = null;
        for (Constructor constructor: constructors) {
            if (constructor.getParameterCount() > 0){
                needfulConst = constructor;
            }
        }
        List<Object> paramForNewObject = new ArrayList<>();
        if (needfulConst != null) {
            for (Parameter parameter : needfulConst.getParameters()) {
                System.out.print(parameter.getName() + "(" + parameter.getType().getSimpleName() + "): ");
                paramForNewObject.add(scannerGetType(parameter.getType().getSimpleName().toLowerCase()));
            }
            ret = needfulConst.newInstance(paramForNewObject.toArray());
        }
        else{
            throw new RuntimeException();
        }
        System.out.println("Object created:" + ret);
        return ret;
    }

    private static Object scannerGetType(String type) {
        Scanner scanner = new Scanner(System.in);
        return switch (type) {
            case "string" -> scanner.nextLine();
            case "int", "integer" -> scanner.nextInt();
            case "long" -> scanner.nextLong();
            case "double" -> scanner.nextDouble();
            case "float" -> scanner.nextFloat();
            case "char", "character" -> scanner.next();
            case "boolean" -> scanner.nextBoolean();
            default -> throw new RuntimeException("Unrecognized type");
        };
    }


    private static void printFieldsAndMetods(Class clazz) {
        Class object = Object.class;

        System.out.println("Fields:");

        Field[] fields = clazz.getDeclaredFields();
        for(Field field: fields){
            System.out.println("\t" + field.getType().getSimpleName() + " " + field.getName());
        }

        System.out.println("Methods:");

        Method[] methods = clazz.getMethods();
        Method[] objMetods = object.getMethods();
        for(Method method: methods){
            boolean decision = true;
            for (Method objMetod: objMetods) {
                if (objMetod.getName().equals(method.getName())) {
                    decision = false;
                    break;
                }
            }
            if (decision){
                System.out.print("\t" + method.getReturnType().getSimpleName() + " "+ method.getName() + "(");
                Class<?>[] parameters = method.getParameterTypes();
                for (int i = 0; i < parameters.length; i++) {
                    System.out.print(parameters[i].getSimpleName());
                    if (i < parameters.length - 1)
                        System.out.print(", ");
                }
                System.out.println(")");
            }
        }
    }

    public static void firstMessage(){
        System.out.println("Classes:");
        System.out.println("\tUser");
        System.out.println("\tCar");
    }

    public static void printDelimiter(){
        System.out.println("---------------------");
    }

    public static Class classSelection(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter class name:");
        System.out.print("-> ");
        String className = scanner.next();
        if (className.equals("Car")){
            return Car.class;
        }
        else if (className.equals("User")){
            return User.class;
        }
        else{
            System.out.println("Wrong choice!!!!");
            throw new RuntimeException();
        }


    }
}
