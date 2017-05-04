package com.gmy.bus.sql;

import org.joda.time.DateTime;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.ToIntFunction;

/**
 * Created by Gmy on 2017/4/6.
 */
public class TablePolicy {

    private int totalCount = 0;

    private int totalPage = 0;

    public int getTotalCount() {
        return totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    /**
     * startTimeStr yyyy-MM
     * endTimeStr yyyy-MM
     *
     * @param startTimeStr 起始时间
     * @param endTimeStr 结束时间
     * @return 时间序列
     * @throws ParseException 编译检测抛出格式化参数不正确/运行如果起始时间小于结束事件抛出不可用参数异常
     */
    private static List<String> duringDateList(String startTimeStr, String endTimeStr) throws ParseException {
        if (endTimeStr.compareToIgnoreCase(startTimeStr) < 0) {
            throw new InvalidParameterException("起始时不能大于结束时间");
        }
        List<String> duringDateList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Date e = simpleDateFormat.parse(endTimeStr);
        Date s = simpleDateFormat.parse(startTimeStr);
        DateTime startTime = new DateTime(s);
        DateTime endTime = new DateTime(e);
        while (true) {
            duringDateList.add(endTime.toString("yyMM"));
            if (endTime.compareTo(startTime) <= 0) {
                break;
            } else {
                endTime = endTime.plusMonths(-1);
            }
        }
        return duringDateList;
    }

    /**
     * 切割查询任务为多个任务(以表为单位)
     *
     * @param page        >=1
     * @param pageSize    >0
     * @param startTime   yyyy-MM
     * @param endTime     yyyy-MM
     * @param intFunction 根据条件进行个数统计的方法实现,分页统计的每一个表的满足条件条数的逻辑必须要与条件查询的逻辑一致
     *                    目前经过测试的方式是，时间条件查询+根据创建订单时间进行倒序排序
     * @return
     * @throws Exception
     */
    public List<SearchTask> cuttingSearchTaskList(int page, int pageSize, String startTime, String endTime, ToIntFunction<String> intFunction) throws Exception {
        int index = (page - 1) * pageSize;
        //过滤掉所有没有数据的表
        class Table {

            private Table(int count, int index, String tableSuffix) {
                this.count = count;
                this.index = index;
                this.tableSuffix = tableSuffix;
            }

            private int count;
            private int index;
            private String tableSuffix;

            public int getCount() {
                return count;
            }

            public int getIndex() {
                return index;
            }

            public String getTableSuffix() {
                return tableSuffix;
            }
        }
        List<Table> tableList = new ArrayList<>();
        List<String> tableSuffixList = duringDateList(startTime, endTime);
        for (String tableSuffix : tableSuffixList) {
            //查出最近一张表的所有满足条件的数据总数
            Integer count = intFunction.applyAsInt(tableSuffix);
            if (count == 0) {
                continue;
            }
            totalCount += count;
            tableList.add(new Table(count, totalCount, tableSuffix));
        }
        totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        //计算出要从哪些表中，查出多少条数据tableList本身是已经是按照表后缀倒叙的表，如果不是请排序后做上述处理
        //获取要参与查询的任务
        int indexTmp = index;
        List<SearchTask> searchTaskList = new ArrayList<>();
        for (Table table : tableList) {
            if (indexTmp <= table.getIndex()) {
                if (index + pageSize >= table.getIndex()) {
                    int size = table.getIndex() - indexTmp;
                    int start = table.getCount() - size;
                    searchTaskList.add(new SearchTask(start, size, table.getTableSuffix()));
                    indexTmp = table.getIndex();
                } else {
                    int size = pageSize + index - indexTmp;
                    searchTaskList.add(new SearchTask(0, size, table.getTableSuffix()));
                    break;
                }

            }
        }
        return searchTaskList;
    }

    public static class SearchTask {
        private int start;
        private int size;
        private String tableSuffix;

        private SearchTask(int start, int size, String tableSuffix) {
            this.start = start;
            this.size = size;
            this.tableSuffix = tableSuffix;
        }

        public int getStart() {
            return start;
        }

        public int getSize() {
            return size;
        }

        public String getTableSuffix() {
            return tableSuffix;
        }
    }

}
