package com.swingui.front.layout;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

import com.swingui.constant.UIAlignmentX;
import com.swingui.value.Spacing;
import com.swingui.widget.PanelWT;

/**
 * コンポーネントを縦に並べるレイアウト提供クラス
 * 
 * @author t.yoshida
 */
public class VStack
{
    /**
     * 指定されたコンポーネントを縦に並べる。
     *
     * @param components 並べるコンポーネント群
     * @return {@link PanelWT}
     */
    public static PanelWT of(JComponent... components)
    {
        return init(UIAlignmentX.Center, Spacing.defaults(), components);
    }

    /**
     * 水平方向の位置指定をし、指定されたコンポーネントを縦に並べる。
     *
     * @param alignmentX 水平方向の位置
     * @param components 並べるコンポーネント群
     * @return {@link PanelWT}
     */
    public static PanelWT of(UIAlignmentX alignmentX, JComponent... components)
    {
        return init(alignmentX, Spacing.defaults(), components);
    }

    /**
     * 指定された間隔でコンポーネントを縦に並べる。
     * 
     * @param spacing コンポーネント間の間隔
     * @param components 並べるコンポーネント群
     * @return {@link PanelWT}
     */
    public static PanelWT of(Spacing spacing, JComponent... components)
    {
        return init(UIAlignmentX.Center, spacing, components);
    }

    /**
     * 水平方向の位置指定をし、指定されたコンポーネントを縦に並べる。
     *
     * @param alignmentX 水平方向の位置
     * @param spacing コンポーネント間の間隔
     * @param components 並べるコンポーネント群
     * @return {@link PanelWT}
     */
    public static PanelWT of(UIAlignmentX alignmentX, Spacing spacing, JComponent... components)
    {
        return init(alignmentX, spacing, components);
    }

    /**
     * 指定されたリストを基にしてコンポーネントを生成し、縦に並べる。
     * 
     * @param list コンポーネントを生成する基になるリスト
     * @param generator リストの要素からコンポーネントを生成する関数
     * @return {@link PanelWT}
     * 
     * @param <E> リストの要素型
     * @param <T> コンポーネント型
     */
    public static <E, T extends JComponent> PanelWT forEach
    (
        List<E> list, BiFunction<Integer, E, T> generator
    )
    {
        return forEach(UIAlignmentX.Center, Spacing.defaults(), list, generator);
    }

    /**
     * 水平方向の位置指定をし、指定されたリストを基にしてコンポーネントを生成し、縦に並べる。
     * 
     * @param alignmentX 水平方向の位置指定
     * @param list コンポーネントを生成する基になるリスト
     * @param generator リストの要素からコンポーネントを生成する関数
     * @return {@link PanelWT}
     * 
     * @param <E> リストの要素型
     * @param <T> コンポーネント型
     */
    public static <E, T extends JComponent> PanelWT forEach
    (
        UIAlignmentX alignmentX, List<E> list, BiFunction<Integer, E, T> generator
    )
    {
        return forEach(alignmentX, Spacing.defaults(), list, generator);
    }

    /**
     * 指定されたリストを基にしてコンポーネントを生成し、指定された間隔で縦に並べる。
     * 
     * @param spacing コンポーネント間の間隔
     * @param list コンポーネントを生成する基になるリスト
     * @param generator リストの要素からコンポーネントを生成する関数
     * @return {@link PanelWT}
     * 
     * @param <E> リストの要素型
     * @param <T> コンポーネント型
     */
    public static <E, T extends JComponent> PanelWT forEach
    (
        Spacing spacing, List<E> list, BiFunction<Integer, E, T> generator
    )
    {
        return forEach(UIAlignmentX.Center, spacing, list, generator);
    }

    /**
     * 水平方向の位置指定をし、指定されたリストを基にしてコンポーネントを生成し、指定された間隔で縦に並べる。
     * 
     * @param alignmentX 水平方向の位置指定
     * @param spacing コンポーネント間の間隔
     * @param list コンポーネントを生成する基になるリスト
     * @param generator リストの要素からコンポーネントを生成する関数
     * @return {@link PanelWT}
     * 
     * @param <E> リストの要素型
     * @param <T> コンポーネント型
     */
    public static <E, T extends JComponent> PanelWT forEach
    (
        UIAlignmentX alignmentX, Spacing spacing, List<E> list, BiFunction<Integer, E, T> generator
    )
    {
        List<T> components = new ArrayList<>();
        for(int i=0; i<list.size(); i++)
        {
            T child = generator.apply(i, list.get(i));
            if(child != null)
            {
                components.add(child);
            }
        }
        return of(alignmentX, spacing, components.toArray(new JComponent[components.size()]));
    }

    /**
     * 初期化処理
     * 
     * @param alignmentX 水平方向の位置指定
     * @param spacing コンポーネント間の間隔
     * @param components 並べるコンポーネント群
     * @return {@link PanelWT}
     */
    private static PanelWT init(UIAlignmentX alignmentX, Spacing spacing, JComponent... components)
    {
        /*
         * Box.createGlue() について
         *
         * これを追加するとウィンドウを拡大した際、子コンポーネントのサイズが拡大に合わせて
         * いっぱいに広がらないため（JList, ListBoxWT等）、使用しない方針である。
         */

        PanelWT panel = new PanelWT();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //panel.add(Box.createGlue());  // フレームが広がった場合の上部調整用スペース
        for(int i=0; i<components.length; i++)
        {
            JComponent c = components[i];

            c.setAlignmentX(alignmentX.value);
            c.setAlignmentY(Component.CENTER_ALIGNMENT);
            //panel.add(Box.createGlue());  // フレームが広がった場合のコンポーネント間調整用スペース
            panel.add(c);

            // コンポーネント間の間隔設定
            boolean isTail = (i == (components.length - 1));
            if(!isTail)
            {
                panel.add(Box.createRigidArea(new Dimension(0, spacing.gap)));
            }
        }
        //panel.add(Box.createGlue());  // フレームが広がった場合の下部調整用スペース

        return panel;
    }
}