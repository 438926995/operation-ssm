package com.eleme.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunwei on 16/1/14.
 */
public class TestList {

    /**
     *
     */
    @Test
    public void test() {
        TestA test = new TestA();
        TestA test2 = null;
        test.equals(test2);
        System.out.println("1111111");
//        List<TestA> testAList1 = getList(50000);
//        List<TestA> testAList2 = getList(50001);
//        List<TestA> testAList3 = getList(10002);
//        List<TestA> testAList4 = getList(10003);
//        long startTime = System.currentTimeMillis();
//        Stream<TestA> testss = testAList1.stream().filter(p -> !testAList2.contains(p));
////        System.out.println(testss.toArray().length);
//        List<TestA> testAlistRes = testss.collect(Collectors.toList());
//        System.out.println(testAlistRes.size());
//        System.out.println(System.currentTimeMillis() - startTime);
//        long startTime2 = System.currentTimeMillis();
//        testAList2.retainAll(testAList1);
//        System.out.println(testAList2.size());
//        System.out.println(System.currentTimeMillis() - startTime2);

    }

    public List<TestA> getList(int max) {
        List<TestA> list = new ArrayList<TestA>();
        for (int i = 0; i < max; i++) {
            TestA testA = new TestA();
            testA.setId(i);
            testA.setName("name" + i);
            list.add(testA);
        }
        return list;
    }

    public class TestA {
        int id;
        String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof TestA) {
                System.out.println("wqweqweqw");
                TestA o1 = (TestA) o;

                return o1.getId() == this.getId();
            }
            return false;

        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            return result;
        }
    }

}
