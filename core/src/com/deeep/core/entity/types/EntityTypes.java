package com.deeep.core.entity.types;

import com.deeep.core.entity.abstraction.Entity;
import com.deeep.core.util.Logger;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 11/1/13
 * Time: 11:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class EntityTypes {
    private static int type = 1;
    private static HashMap<Integer, Class<? extends Entity>> integerEntityHashMap = new HashMap<Integer, Class<? extends Entity>>();
    private static HashMap<Class<? extends Entity>, Integer> entityIntegerHashMap = new HashMap<Class<? extends Entity>, Integer>();

    static {
        Logger.getInstance().system(EntityTypes.class, "Registering entities. . . ");
        registerEntity(Wall.class);
        registerEntity(TestEntity.class);
        registerEntity(MoveCross.class);
        registerEntity(MoveZero.class);
    }

    private static void registerEntity(Class<? extends Entity> claz) {
        integerEntityHashMap.put(type, claz);
        entityIntegerHashMap.put(claz, type);
        type++;
    }

    public static int getType(Class<? extends Entity> entity) {
        if (!entityIntegerHashMap.containsKey(entity))
            return 0;
        return entityIntegerHashMap.get(entity);
    }

    public static Class<? extends Entity> getEntity(int type) {
        return integerEntityHashMap.get(type);
    }
}
