package helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Defensive Copy: www.javapractices.com/topic/TopicAction.do?Id=15

public class DefensiveCopyHelper {

    // to get a defensive copy of a map object
    public static <T1, T2> Map<T1, T2> getDefensiveCopyMap(Map<T1, T2> originalMap){
        Map<T1, T2> copyMap = new LinkedHashMap<>();
        for(T1 key: originalMap.keySet())
            copyMap.put(key, originalMap.get(key));
        return copyMap;
    }

    // to get a defensive copy of a list object
    public static <T> List<T> getDefensiveCopyList(List<T> originalList){
        List<T> copyList = new ArrayList<>();
        for(T val: originalList)
            copyList.add(val);
        return copyList;
    }

}
