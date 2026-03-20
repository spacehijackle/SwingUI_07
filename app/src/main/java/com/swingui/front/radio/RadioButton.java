package com.swingui.front.radio;

import java.util.function.Function;

import com.swingui.value.UIValue;
import com.swingui.widget.radio.RadioButtonWT;

/**
 * ラジオボタン部品提供クラス
 * 
 * @author t.yoshida
 */
public class RadioButton
{
    /**
     * 指定されたテキストでラジオ・ボタンを生成する。
     * 
     * @param text ラジオ・ボタン横に表示するテキスト
     * @return 生成された {@code RadioButtonWT} インスタンス
     */
    /*public static RadioButtonWT<String> of(String text)
    {
        RadioButtonWT<String> radio = new RadioButtonWT<>(new UIValue<>(text));
        return radio;
    }*/

    /**
     * 指定された選択対象データを基にラジオ・ボタンを生成する。
     * 
     * @apiNote ラジオ・ボタン横に表示するテキストは、選択対象データの {@code toString()}
     * 
     * @param <T> 選択対象データの型
     * @param item 選択対象データ
     * @return 生成された {@code RadioButtonWT} インスタンス
     */
    public static <T> RadioButtonWT<T> of(T item)
    {
        RadioButtonWT<T> radio = new RadioButtonWT<>(UIValue.of(item));
        return radio;
    }

    /**
     * 指定された選択対象データを基にラジオ・ボタンを生成する。
     * 
     * @apiNote ラジオ・ボタン横に表示するテキストは、{@code labeling} で指定された関数の戻り値
     * 
     * @param <T> 選択対象データの型
     * @param item 選択対象データ
     * @return 生成された {@code RadioButtonWT} インスタンス
     */
    public static <T> RadioButtonWT<T> of(T item, Function<T, String> labeling)
    {
        RadioButtonWT<T> radio = new RadioButtonWT<>(UIValue.of(item), labeling);
        return radio;
    }

    /**
     * 指定されたたた対象データを基にラジオ・ボタンを生成する。
     * 
     * @apiNote ラジオ・ボタン横に表示するテキストは、選択対象データの {@code toString()}
     * 
     * @param <T> 選択対象データの型
     * @param item 選択対象データ
     * @return 生成された {@code RadioButtonWT} インスタンス
     */
    public static <T> RadioButtonWT<T> of(UIValue<T> item)
    {
        RadioButtonWT<T> radio = new RadioButtonWT<>(item);
        return radio;
    }

    /**
     * 指定された選択対象データを基にラジオ・ボタンを生成する。
     * 
     * @apiNote ラジオ・ボタン横に表示するテキストは、{@code labeling} で指定された関数の戻り値
     * 
     * @param <T> 選択対象データの型
     * @param item 選択対象データ
     * @param labeling ラベル生成関数
     * @return 生成された {@code RadioButtonWT} インスタンス
     */
    public static <T> RadioButtonWT<T> of(UIValue<T> item, Function<T, String> labeling)
    {
        RadioButtonWT<T> radio = new RadioButtonWT<>(item, labeling);
        return radio;
    }
}
