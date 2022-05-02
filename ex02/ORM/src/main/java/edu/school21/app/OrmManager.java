package edu.school21.app;

import edu.school21.models.OrmColumn;
import edu.school21.models.OrmColumnId;
import edu.school21.models.OrmEntity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrmManager implements Manager {
    private Map<String, Map<String, List<Object>>> BD = new HashMap<>();


    @Override
    public void save(Object entity) {
        String tableName = null;
        Map<String, List<Object>> currentTable;

        try {
            tableName = findTableName(entity);
        } catch (RuntimeException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            System.out.println("Save is fail");
            return ;
        }
        currentTable = returnTable(tableName, entity);
        try {
            addInfo(currentTable, entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Object entity) {

    }

    @Override
    public <T> T findById(Long id, Class<T> aClass) {
        return null;
    }

    @Override
    public void showTable(String tableName) {
        Map<String, List<Object>> currentTable;
        //int i = 0;
        int size;
        Object[] keys;

        if (!BD.containsKey(tableName)){
            System.out.println("Table " + tableName + " not found.");
            return ;
        }
        currentTable = BD.get(tableName);
        size = currentTable.keySet().toString().length();
        keys = currentTable.keySet().toArray();
        printHeader(tableName, size);
        //System.out.println(currentTable.keySet());
        System.out.print("| ");
        for (int i = 0; i < keys.length; i++) {
            System.out.print((String) keys[i]);
            if (i < keys.length - 1){
                System.out.print("  ");
            }
        }
        System.out.println(" |");
        printDelimiter(size, 1);
        for(int i = 0; i < currentTable.get(keys[0]).size(); i++){
            System.out.print("| ");
            for(int j = 0; j < currentTable.size(); j++) {
                System.out.print(currentTable.get(keys[j]).get(i));
                if (j < currentTable.size() - 1){
                    System.out.print("   ");
                }
            }
            System.out.print(" |");
            System.out.println();
            printDelimiter(size, 0);
        }

    }

    private void printHeader(String tableName, int size) {
        printDelimiter(size, 1);
        int tableNameSize = tableName.length();
        if (tableNameSize > size){
            System.out.println(tableName);
        }
        else{
            for(int i = 0; i < (size - tableNameSize) / 2 + 1; i++){
                if (i == 0){
                    System.out.print("|");
                }else{
                    System.out.print(" ");
                }

            }
            System.out.print(tableName);
            for(int i = 0; i < (size - tableNameSize) / 2 + 1; i++){
                if (i == (size - tableNameSize) / 2){
                    System.out.print("|");
                }else{
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        printDelimiter(size, 1);
    }

    private void printDelimiter(int size, int flag) {
        String delimiter;
        if (flag == 1){
            delimiter = "=";
        }
        else{
            delimiter = "-";
        }
        for(int i = 0; i < size + 2; i++){
            System.out.print(delimiter);
        }
        System.out.println();
    }

    private String findTableName(Object entity) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class clazz = entity.getClass();
        Annotation clazzAnnotation = clazz.getAnnotation(OrmEntity.class);
        if (clazzAnnotation == null){
            throw new RuntimeException();
        }
        Method clazzAnnotationMethod = clazzAnnotation.annotationType().getMethod("table");
        String tableName = clazzAnnotationMethod.invoke(clazzAnnotation).toString();
        return tableName;
    }

    private Map<String, List<Object>> returnTable(String tableName, Object entity){
        Map<String, List<Object>> currentTable;

        if (!BD.containsKey(tableName)){
            currentTable = new HashMap<>();
            BD.put(tableName, currentTable);

            addAllColumns(entity, currentTable);
        }
        else{
            currentTable = BD.get(tableName);
        }
        return currentTable;
    }

    private void addAllColumns(Object entity, Map<String, List<Object>> currentTable) {
        Class clazz = entity.getClass();

        //System.out.println(clazz);
        Field[] clazzFields = clazz.getDeclaredFields();
        for (Field field:
             clazzFields) {
            if (field.getAnnotation(OrmColumn.class) != null || field.getAnnotation(OrmColumnId.class) != null) {
                //System.out.println(field.getType().getSimpleName() + " " + field.getName());
                List<Object> list = new LinkedList<>();
                currentTable.put(field.getName(), list);
            }
        }
    }


    private void addInfo(Map<String, List<Object>> currentTable, Object entity) throws IllegalAccessException {
        //System.out.println(currentTable.keySet());
        Class clazz = entity.getClass();

        //System.out.println(clazz);
        Field[] clazzFields = clazz.getDeclaredFields();
        for (Field field:
                clazzFields) {
            List currentList = (List<Object>)currentTable.get(field.getName());
            field.setAccessible(true);
            currentList.add(field.get(entity));
            field.setAccessible(false);
        }


    }
}
