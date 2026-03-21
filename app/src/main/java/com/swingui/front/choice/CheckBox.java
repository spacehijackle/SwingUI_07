package com.swingui.front.choice;

import java.util.function.Function;

import com.swingui.value.UIValue;
import com.swingui.widget.choice.CheckBoxWT;

/**
 * チェックボックス部品提供クラス
 * 
 * @author t.yoshida
 */
public class CheckBox<T>
{
    /**
     * 指定された選択状態と選択対象でチェックボックスを生成する。
     * 
     * @apiNote チェックボックス横に表示するテキストは、選択対象データの {@code toString()}
     * 
     * @param <T> 選択対象データの型
     * @param isChecked 選択状態
     * @param item 選択対象データ
     * @return 生成された {@code CheckBoxWT} インスタンス
     */
    public static <T> CheckBoxWT<T> of(UIValue<Boolean> isChecked, T item)
    {
        return of(isChecked, UIValue.of(item));
    }

    /**
     * 指定された選択状態と選択対象でチェックボックスを生成する。
     * 
     * @apiNote チェックボックス横に表示するテキストは、{@code labeling} で指定された関数の戻り値
     * 
     * @param <T> 選択対象データの型
     * @param isChecked 選択状態
     * @param item 選択対象データ
     * @param labeling ラベル生成関数
     * @return 生成された {@code CheckBoxWT} インスタンス
     */
    public static <T> CheckBoxWT<T> of(UIValue<Boolean> isChecked, T item, Function<T, String> labeling)
    {
        return of(isChecked, UIValue.of(item), labeling);
    }

    /**
     * 指定された選択状態と選択対象でチェックボックスを生成する。
     * 
     * @apiNote チェックボックス横に表示するテキストは、選択対象データの {@code toString()}
     * 
     * @param <T> 選択対象データの型
     * @param isChecked 選択状態
     * @param item 選択対象データ
     * @return 生成された {@code CheckBoxWT} インスタンス
     */
    public static <T> CheckBoxWT<T> of(UIValue<Boolean> isChecked, UIValue<T> item)
    {
        return (new CheckBoxWT<>(isChecked, item));
    }

    /**
     * 指定された選択状態と選択対象でチェックボックスを生成する。
     * 
     * @apiNote チェックボックス横に表示するテキストは、{@code labeling} で指定された関数の戻り値
     * 
     * @param <T> 選択対象データの型
     * @param isChecked 選択状態
     * @param item 選択対象データ
     * @param labeling ラベル生成関数
     * @return 生成された {@code CheckBoxWT} インスタンス
     */
    public static <T> CheckBoxWT<T> of
    (
        UIValue<Boolean> isChecked, UIValue<T> item, Function<T, String> labeling
    )
    {
        return (new CheckBoxWT<>(isChecked, item, labeling));
    }
}
