package com.swingui.front.choice;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import com.swingui.value.UIValue;
import com.swingui.widget.Widget;
import com.swingui.widget.choice.RadioButtonGroupWT;
import com.swingui.widget.choice.RadioButtonWT;

/**
 * ラジオ・ボタンをグループ化し、排他選択を可能にするラジオ・ボタングループ提供クラス
 * 
 * @author t.yoshida
 */
public class RadioButtonGroup
{
    /**
     * ラジオ・ボタンをグループ化する。
     * 
     * @param <E> 選択対象データの型
     * @param <T> グループ対象のラジオ・ボタンが乗っかっているコンポーネントの型
     * @param selected 選択対象保持データ
     * @param container ラジオ・ボタンが乗っかっているコンポーネント<br>
     *                  ※{@code VStack}, {@code HStack} 等のコンテナ・コンポーネント
     * @return ラジオ・ボタングループ
     */
    public static <E, T extends JComponent> RadioButtonGroupWT<E> of(UIValue<E> selected, Widget<T> container)
    {
        // 指定コンテナ上のラジオ・ボタンを検索
        RadioButtonFinder<E> finder = new RadioButtonFinder<>();
        finder.find((JComponent)container);

        // 抽出したラジオ・ボタンをグループ化し、指定されたコンポーネントを乗せる
        RadioButtonGroupWT<E> panel = new RadioButtonGroupWT<>(selected, finder.radios);
        panel.add((JComponent)container);

        return panel;
    }

    /**
     * ラジオ・ボタンの検索クラスの
     */
    private static class RadioButtonFinder<E>
    {
        final List<RadioButtonWT<E>> radios = new ArrayList<>();

        /**
         * 指定されたコンポーネント上に配置されたラジオ・ボタンを再帰的に検索する。
         * 
         * @param target 検索対象のコンポーネント
         */
        @SuppressWarnings("unchecked")
        void find(JComponent target)
        {
            for(int i=0; i<target.getComponentCount(); i++)
            {
                Component c = target.getComponent(i);
                if(c instanceof RadioButtonWT)
                {
                    // ラジオ・ボタンを検出したら、リストに追加
                    radios.add((RadioButtonWT<E>)c);
                }
                else if(c instanceof JComponent)
                {
                    // コンポーネント内にさらにコンポーネントがある場合は、再帰的に検索
                    find((JComponent)c);
                }
            }
        }
    }

    /**
     * 指定されたコンポーネント上に配置されたラジオ・ボタンを再帰的に検索する。
     * 
     * @deprecated 再帰で呼び出す度にListを生成するため、リソースの有効利用の観点から
     *             {@code RadioButtonFinder} クラスの {@code find} メソッドに置き換え
     * 
     * @param <E> ラジオ・ボタンの選択対象データの型
     * @param target 検索対象のコンポーネント
     * @return 検出されたラジオ・ボタンのリスト
     */
    /*@Deprecated
    @SuppressWarnings("unchecked")
    private static <E> List<RadioButtonWT<E>> searchRadioButtons(JComponent target)
    {
        List<RadioButtonWT<E>> radios = new ArrayList<>();
        for(int i=0; i<target.getComponentCount(); i++)
        {
            Component c = target.getComponent(i);
            if(c instanceof RadioButtonWT)
            {
                // ラジオ・ボタンを検出したら、リストに追加
                radios.add((RadioButtonWT<E>)c);
            }
            else if(c instanceof JComponent)
            {
                // コンポーネント内にさらにコンポーネントがある場合は、再帰的に検索
                List<RadioButtonWT<E>> results = searchRadioButtons((JComponent)c);
                radios.addAll(results);
            }
        }

        return radios;
    }*/
}
