package com.vladooha.helpers;

import jdk.internal.jline.internal.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

public class ReflectionHelper {
    private static final Logger logger = LogManager.getLogger(ReflectionHelper.class);

    @Nullable
    public static Object newInstance(String classname) {
        try {
            return Class.forName("com.vladooha." + classname).getConstructor().newInstance();
        } catch (Exception e) {
            logger.error("Error occurred till creating '" + classname + "'", e);

            return null;
        }
    }

    public static boolean setPrimitiveField(Object object, String fieldname, String value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldname);
            field.setAccessible(true);

            Class type = field.getType();
            if (type.equals(String.class)) {
                field.set(object, value);
            } else if (type.equals(int.class)) {
                field.setInt(object, Integer.parseInt(value));
            } else if (type.equals(long.class)) {
                field.setLong(object, Long.parseLong(value));
            } else if (type.equals(double.class)) {
                field.setDouble(object, Double.parseDouble(value));
            } else if (type.equals(char.class)) {
                field.setChar(object, value.charAt(0));
            } else if (type.equals(boolean.class)) {
                field.setBoolean(object, Boolean.parseBoolean(value));
            } else if (type.equals(float.class)) {
                field.setFloat(object, Float.parseFloat(value));
            } else if (type.equals(byte.class)) {
                field.setByte(object, Byte.parseByte(value));
            } else if (type.equals(short.class)) {
                field.setShort(object, Short.parseShort(value));
            }

            return true;
        } catch (NoSuchFieldException e) {
            logger.error("No such field '" + fieldname + "' in '" + object + "'", e);

            return false;
        } catch (IllegalAccessException e) {
            logger.error("Illegal access to '" + fieldname + "' in '" + object + "'", e);

            return false;
        }
    }

    public static boolean setNonPrimitiveField(Object object, String fieldname, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldname);
            field.setAccessible(true);

            field.set(object, value);

            return true;
        } catch (NoSuchFieldException e) {
            logger.error("No such field '" + fieldname + "' in '" + object + "'", e);

            return false;
        } catch (IllegalAccessException e) {
            logger.error("Illegal access to '" + fieldname + "' in '" + object + "'", e);

            return false;
        }
    }
}
