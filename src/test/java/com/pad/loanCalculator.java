package com.pad;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class loanCalculator {
    @Test
    public void test(){
        double principal = 5000000;
        double rate=4.6 ;
        int term=5;
        double totalMoney;
        double totalInterests;
        double detail[][];

        double mIns = rate / 100 / 12; //月利率
        //term 还款所需月份
        double pow = Math.pow(1 + mIns,term);//计算次幂 x的y次方
        double remains = principal;
        totalMoney = (term * principal * mIns * pow) / (pow - 1);  //总还款金额
        totalMoney = Math.floor(totalMoney * 100 + 0.5) / 100;  //floor函数 保留两位小数
        totalInterests = totalMoney - principal;
        totalInterests = Math.floor(totalInterests * 100 + 0.5) / 100;

        System.out.println("还款总额"+totalMoney);
        System.out.println("利息总额"+totalInterests);
        double temp[][] = new double[term][3];
        for (int i = 0; i < term; i++) {
            if(i == term - 1) {
                temp[i][1] = remains * mIns;
                temp[i][1] = Math.floor(temp[i][1] * 100 + 0.5) / 100;
                temp[i][0] = remains;
                temp[i][0] = Math.floor(temp[i][0] * 100 + 0.5) / 100;
                temp[i][2] = temp[i][0] + temp[i][1];
                temp[i][2] = Math.floor(temp[i][2] * 100 + 0.5) / 100;
                break;
            }
            //由于精度问题 最后一个月实际的本金会有差别 需要单独计算
            temp[i][1] = remains * mIns;
            temp[i][1] = Math.floor(temp[i][1] * 100 + 0.5) / 100;
            temp[i][2] = totalMoney / term;
            temp[i][2] = Math.floor(temp[i][2] * 100 + 0.5) / 100;
            temp[i][0] = temp[i][2] - temp[i][1];
            temp[i][0] = Math.floor(temp[i][0] * 100 + 0.5) / 100;
            remains -= temp[i][0];
        }
        //temp[][0]为每月还款本金 temp[][1]为每月还款利息 temp[][2]为每月还款总额
        detail = temp;
        System.out.println(Arrays.deepToString(detail));

        /*for (int i = 0; i < detail.length; i++) {

        }*/
    }
}
