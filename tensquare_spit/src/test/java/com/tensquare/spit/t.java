package com.tensquare.spit;

import java.util.Date;

/**
 * @Author Rem
 * @Date 2019-06-19
 */

public class t {

    public static void main(String[] args){
        int i=1;
        int j=i++;
        if(i==(++j)&&(i++)==j){
            i+=j;
        }
        System.out.println(new Date());
    }
}
