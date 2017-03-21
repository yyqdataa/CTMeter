package com.yanyuqi.ctmeter.utils;

import android.content.Context;

/**
 * Created by yanyuqi on 2017/3/21.
 */

public class ChangePixel {

    public static int dp2px(Context context,float dp){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp*density+0.5f);
    }
}
