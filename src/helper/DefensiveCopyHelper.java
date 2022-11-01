package helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DefensiveCopyHelper {

    public static <T1, T2> Map getDefensiveCopyMap(Map<T1, T2> originalMap){
        Map<T1, T2> copyMap = new LinkedHashMap<>();
        for(T1 key: originalMap.keySet())
            copyMap.put(key, originalMap.get(key));
        return copyMap;
    }

    public static <T> List getDefensiveCopyList(List<T> originalList){
        List<T> copyList = new ArrayList<>();
        for(T val: originalList)
            copyList.add(val);
        return copyList;
    }

}
