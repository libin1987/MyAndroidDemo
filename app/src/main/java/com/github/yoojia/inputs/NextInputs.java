package com.github.yoojia.inputs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * NextInputs
 *
 * @author 陈小锅 (yoojia.chen@gmail.com)
 */
public class NextInputs {

    private static final Comparator<Pattern> ORDERING = new Comparator<Pattern>() {
        @Override
        public int compare(Pattern lhs, Pattern rhs) {
            return lhs.orderPriority - rhs.orderPriority;
        }
    };

    private final ArrayList<VerifierMeta> mVerifiers = new ArrayList<>();

    private MessageDisplay mMessageDisplay = new MessageDisplay() {
        @Override
        public void show(Input input, String message) {
            System.err.println("Check input fail: " + message);
        }
    };

    /**
     * 默认情况下，校验测试失败即停止其它校验
     */
    private boolean mStopIfFail = true;

    /**
     * 执行校验测试，并返回测试结果。
     * @return 校验测试结果是否成功
     */
    public boolean test(){
        VerifierMeta current = null;
        try{
            for (VerifierMeta meta : mVerifiers) {
                current = meta;
                if ( ! performTest(meta) && mStopIfFail) {
                    return false;
                }
            }
            return true;
        }catch (Throwable thr) {
            mMessageDisplay.show(current.input, thr.getMessage());
            return false;
        }
    }

    /**
     * 添加输入条目及测试模式。
     * @param input 输入条目
     * @param patterns 测试模式
     * @return NextInputs
     */
    public NextInputs add(Input input, Pattern...patterns){
        if (patterns == null || patterns.length == 0){
            throw new IllegalArgumentException("Patterns is required !");
        }
        Arrays.sort(patterns, ORDERING);
        mVerifiers.add(new VerifierMeta(input, patterns));
        return this;
    }

    /**
     * 在校验测试遇到失败时，是否停止校验
     * @param stopOnFail 是否停止
     */
    public NextInputs setStopIfFail(boolean stopOnFail){
        mStopIfFail = stopOnFail;
        return this;
    }

    /**
     * 设置校验测试结果消息显示接口
     * @param display 消息显示接口。
     * @throws NullPointerException 当参数为Null时，抛出异常。
     */
    public NextInputs setMessageDisplay(MessageDisplay display){
        if (display == null) {
            throw new NullPointerException();
        }
        mMessageDisplay = display;
        return this;
    }

    /**
     * 流式API
     * @param input Input对象
     * @return 流式API接口
     */
    public Fluent on(Input input) {
        return new Fluent(input, this);
    }

    private boolean performTest(VerifierMeta meta) throws Exception {
        final String value = meta.input.getValue();
        for (Pattern pattern : meta.patterns) {
            if ( ! pattern.mVerifier.perform(value)) {
                mMessageDisplay.show(meta.input, pattern.message);
                return false;
            }
        }
        return true;
    }

}
