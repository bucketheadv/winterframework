package org.winterframework.core.tool;

import java.util.stream.Stream;

/**
 * @author qinglin.liu
 * created at 2024/1/4 10:51
 */
public class ProcessorBarTool {

    public static void print(double curr) {
        print(null, curr);
    }

    /**
     * 打印进度条
     * @param label 进度条标题
     * @param curr 当前百分比
     */
    public static void print(String label, double curr) {
        char incomplete = '░';
        char complete = '█';

        int total = 100;
        StringBuilder sb = new StringBuilder();
        Stream.generate(() -> incomplete).limit(total).forEach(sb::append);

        for (int i = 0; i <= curr * 100; i++) {
            sb.replace(i, i + 1, String.valueOf(complete));
            String prefix = "";
            if (StringTool.isNotBlank(label)) {
                prefix = label + ": ";
            }
            String processBar = "\r" + prefix + sb;
            String percent = " " + i + "%";
            System.out.print(prefix + processBar + percent + "\t");
        }

        if (curr >= 1) {
            System.out.println();
        }
    }
}
