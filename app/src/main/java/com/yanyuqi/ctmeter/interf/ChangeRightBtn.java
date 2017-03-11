package com.yanyuqi.ctmeter.interf;

/**
 * Created by yanyuqi on 2017/2/17.
 */

public interface ChangeRightBtn {

    /**
     * 根据实验不同改变对用的结果按钮
     * @param testType
     */
    void changeRightbtn(int testType);

    /**
     * 根据级别不同设置结果界面
     * @param jbNum
     */
    void setResultView(int jbNum);
}
