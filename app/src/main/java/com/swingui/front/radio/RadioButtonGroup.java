package com.swingui.front.radio;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import com.swingui.value.UIValue;
import com.swingui.widget.Widget;
import com.swingui.widget.radio.RadioButtonGroupWT;
import com.swingui.widget.radio.RadioButtonWT;

/**
 * ラジオ・ボタンの排他選択を可能にするラジオ・ボタングループ提供クラス
 * 
 * @author t.yoshida
 */
public class RadioButtonGroup
{
    public static <E, T extends JComponent> RadioButtonGroupWT<E> of(UIValue<E> selected, Widget<T> child)
    {
        List<RadioButtonWT<E>> radios = new ArrayList<>();
        searchRadioButtons((JComponent)child, radios);

        RadioButtonGroupWT<E> panel = new RadioButtonGroupWT<>(selected, radios);
        panel.add((JComponent)child);

        return panel;
    }

    @SuppressWarnings("unchecked")
    private static <E> void searchRadioButtons(JComponent target, List<RadioButtonWT<E>> radios)
    {
        for(int i=0; i<target.getComponentCount(); i++)
        {
            Component c = target.getComponent(i);
            if(c instanceof RadioButtonWT)
            {
                radios.add((RadioButtonWT<E>)c);
            }
            else if(c instanceof JComponent)
            {
                searchRadioButtons((JComponent)c, radios);
            }
        }
    }
}
