package com.github.yoojia.inputs;

import com.github.yoojia.inputs.impl.*;

/**
 * @author 陈小锅 (yoojia.chen@gmail.com)
 */
public class ValuePattern {

    public static final int PRIORITY_REQUIRED = StaticPattern.PRIORITY_REQUIRED;
    public static final int PRIORITY_GENERAL = StaticPattern.PRIORITY_GENERAL;

    /**
     * 必要项，输入内容不能为空
     * @return Pattern
     */
    public static Pattern Required(){
        return StaticPattern.Required();
    }

    /**
     * 输入内容不能小于最小长度
     * @param min 最小长度
     * @return Pattern
     */
    public static Pattern MinLength(final int min) {
        return new Pattern(new MinLengthVerifier(min)).msg("输入内容至少" + min + "个字符");
    }

    /**
     * 输入内容不能大于最大长度
     * @param max 最大长度
     * @return Pattern
     */
    public static Pattern MaxLength(final int max) {
        return new Pattern(new MaxLengthVerifier(max)).msg("输入内容最多" + max + "个字符");
    }

    /**
     * 输入内容在长度范围内
     * @param min 最小长度
     * @param max 最大长度
     * @return Pattern
     */
    public static Pattern RangeLength(final int min, final int max) {
        return new Pattern(new RangeLengthVerifier(min, max)).msg("输入内容字符数量必须在[" + min + "," + max + "]之间");
    }

    /**
     * 输入数值不能小于最小值
     * @param min 最小值
     * @return Pattern
     */
    public static Pattern MinValue(final int min) {
        return ABTest(new MinValueBridge(min)).msg("输入数值最小为：" + min);
    }

    /**
     * 输入数值不能小于最小值
     * @param min 最小值
     * @return Pattern
     */
    public static Pattern MinValue(final long min) {
        return ABTest(new MinValueBridge(min)).msg("输入数值最小为：" + min);
    }

    /**
     * 输入数值不能小于最小值
     * @param min 最小值
     * @return Pattern
     */
    public static Pattern MinValue(final float min) {
        return ABTest(new MinValueBridge(min)).msg("输入数值最小为：" + min);
    }

    /**
     * 输入数值不能小于最小值
     * @param min 最小值
     * @return Pattern
     */
    public static Pattern MinValue(final double min) {
        return ABTest(new MinValueBridge(min)).msg("输入数值最小为：" + min);
    }

    /**
     * 输入数值不能大于最大值
     * @param max 最大值
     * @return Pattern
     */
    public static Pattern MaxValue(final int max) {
        return ABTest(new MaxValueBridge(max)).msg("输入数值最大为：" + max);
    }

    /**
     * 输入数值不能大于最大值
     * @param max 最大值
     * @return Pattern
     */
    public static Pattern MaxValue(final long max) {
        return ABTest(new MaxValueBridge(max)).msg("输入数值最大为：" + max);
    }

    /**
     * 输入数值不能大于最大值
     * @param max 最大值
     * @return Pattern
     */
    public static Pattern MaxValue(final float max) {
        return ABTest(new MaxValueBridge(max)).msg("输入数值最大为：" + max);
    }

    /**
     * 输入数值不能大于最大值
     * @param max 最大值
     * @return Pattern
     */
    public static Pattern MaxValue(final double max) {
        return ABTest(new MaxValueBridge(max)).msg("输入数值最大为：" + max);
    }

    /**
     * 输入数值必须在最值区间
     * @param min 最小值
     * @param max 最大值
     * @return Pattern
     */
    public static Pattern RangeValue(final int min, final int max) {
        return ABTest(new RangeValueBridge(max, max)).msg("输入数值大小必须在[" + min + "," + max + "]之间");
    }

    /**
     * 输入数值必须在最值区间
     * @param min 最小值
     * @param max 最大值
     * @return Pattern
     */
    public static Pattern RangeValue(final long min, final long max) {
        return ABTest(new RangeValueBridge(max, max)).msg("输入数值大小必须在[" + min + "," + max + "]之间");
    }

    /**
     * 输入数值必须在最值区间
     * @param min 最小值
     * @param max 最大值
     * @return Pattern
     */
    public static Pattern RangeValue(final float min, final float max) {
        return ABTest(new RangeValueBridge(max, max)).msg("输入数值大小必须在[" + min + "," + max + "]之间");
    }

    /**
     * 输入数值必须在最值区间
     * @param min 最小值
     * @param max 最大值
     * @return Pattern
     */
    public static Pattern RangeValue(final double min, final double max) {
        return ABTest(new RangeValueBridge(max, max)).msg("输入数值大小必须在[" + min + "," + max + "]之间");
    }

    /**
     * 输入内容与加载器的内容相同
     * @param lazyLoader 加载器
     * @return Pattern
     */
    public static Pattern EqualsTo(final LazyLoader<String> lazyLoader){
        return ABTest(new EqualsBridge(lazyLoader)).msg("输入内容与要求不一致");
    }

    /**
     * 输入内容必须与指定内容相同
     * @param fixedValue 指定内容
     * @return Pattern
     */
    public static Pattern EqualsTo(final String fixedValue) {
        return EqualsTo(new LazyLoader<String>() {
            @Override
            public String getValue() {
                return fixedValue;
            }
        });
    }

    /**
     * 输入内容必须与加载器的内容不相同
     * @param lazyLoader 加载器
     * @return Pattern
     */
    public static Pattern NotEquals(final LazyLoader<String> lazyLoader){
        return ABTest(new NotEqualsBridge(lazyLoader)).msg("输入内容不能与要求的相同");
    }

    /**
     * 输入内容必须与指定内容不相同
     * @param fixedValue 指定内容
     * @return Pattern
     */
    public static Pattern NotEquals(final String fixedValue) {
        return NotEquals(new LazyLoader<String>() {
            @Override
            public String getValue() {
                return fixedValue;
            }
        });
    }

    public static <T> Pattern ABTest(final ABBridge<T> bridge) {
        return new Pattern(new BridgeVerifier<>(bridge));
    }

}
