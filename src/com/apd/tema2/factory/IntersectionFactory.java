package com.apd.tema2.factory;

import com.apd.tema2.entities.Intersection;
import com.apd.tema2.intersections.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Prototype Factory: va puteti crea cate o instanta din fiecare tip de implementare de Intersection.
 */
public class IntersectionFactory {
    private static Map<String, Intersection> cache = new HashMap<>();

    static {
        String[] intersection_types;
        intersection_types = new String[]{
                "simple_semaphore",
                "simple_n_roundabout",
                "simple_strict_1_car_roundabout",
                "simple_strict_x_car_roundabout",
                "simple_max_x_car_roundabout",
                "priority_intersection",
                "crosswalk",
                "simple_maintenance",
                "complex_maintenance",
                "railroad",
        };
        for(String intersection_type : intersection_types)
        {
            cache.put(intersection_type,IntersectionFactory.getIntersection(intersection_type));
        }
    }

    public static Intersection getIntersection(String handlerType) {
        return cache.get(handlerType);
    }

}
