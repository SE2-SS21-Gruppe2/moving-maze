package se2.gruppe2.moving_maze.network;

import com.esotericsoftware.kryo.Kryo;

import java.util.ArrayList;

public class Registry {
    private static ArrayList<Class<String>> classRegistry = new ArrayList<>();

    /**
     * Registers all classes that are known to the registry
     * @param kryo to registrate the classes on
     */
    public static void registerClassesOnKryo(Kryo kryo) {
        if(classRegistry.size() == 0)
            throw new IllegalStateException("Must add classes before registering them");

        for(Class<String> c : classRegistry) {
            kryo.register(c);
        }
    }

    /**
     * Initializes the class-registry with a predefined set of classes.
     */
    public static void initializeClassRegistry() {

    }

    // GETTER & SETTER section
    public static int addClassToRegistry(Class<String> newClass) {
        classRegistry.add(newClass);
        return classRegistry.size();
    }

    public static ArrayList<Class<String>> getRegistry() {
        return classRegistry;
    }
}
