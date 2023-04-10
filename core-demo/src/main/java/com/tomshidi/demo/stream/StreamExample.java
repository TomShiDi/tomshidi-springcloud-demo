package com.tomshidi.demo.stream;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * jdk stream api 用例
 * @author tangshili
 * @since 2023/4/10 17:02
 */
public class StreamExample {
    public static void main(String[] args) {
        nonSeqCollectionInStream();
    }

    /**
     * 无序列表在parallel流中使用skip失效，因为终端流是无序的，无法顺序切分
     */
    public static void nonSeqCollectionInStream() {
        int maxSize = 100;
        int subCollectionSize = 50;
        // [0,1,2,...100]
        Set<Integer> set = Stream.iterate(0, n -> n + 1).limit(maxSize).collect(Collectors.toSet());
        List<Integer> list = new ArrayList<>(set);
        // 按照每个列表50个元素拆分
        List<List<Integer>> collectList = Stream.iterate(0, n -> n + 1)
                .limit(maxSize/subCollectionSize).parallel()
                .map(a -> set.stream().skip(a * subCollectionSize).limit(subCollectionSize).parallel().collect(Collectors.toList()))
                .collect(Collectors.toList());
        // 集合交集，正常情况交集是空的，当错误使用skip时拆分的分段数据中有重复值
        Sets.SetView<Integer> intersection = Sets.intersection(new HashSet<>(collectList.get(0)), new HashSet<>(collectList.get(1)));
        System.out.println(intersection);
    }
}
