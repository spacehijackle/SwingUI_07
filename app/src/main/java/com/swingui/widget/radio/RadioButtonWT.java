package com.swingui.widget.radio;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.JRadioButton;

import com.swingui.value.UIValue;
import com.swingui.value.gap.UIGap;
import com.swingui.value.size.UILength;
import com.swingui.widget.Widget;
import com.swingui.widget.util.WidgetHelper;

/**
 * {@link JRadioButton} を拡張した {@link Widget} 実装クラス
 * 
 * @author t.yoshida
 *
 * @param <T> 選択対象データの型
 */
public class RadioButtonWT<T> extends JRadioButton implements Widget<RadioButtonWT<T>>
{
    // 選択対象保持データ
    private UIValue<T> item;

    // ラベル生成関数
    private Function<T, String> labeling;

    // 活性/非活性状態
    private UIValue<Boolean> isEnabled = new UIValue<>(true);

    // UIフォーカス状態
    private UIValue<Boolean> hasFocus = new UIValue<>(false);

    // フォント色
    private UIValue<Color> fgColor = new UIValue<>(getForeground());

    // 背景色
    private UIValue<Color> bgColor = new UIValue<>(getBackground());

    /**
     * 指定された選択対象でラジオボタンを生成する。
     * 
     * @param item 選択対象データ
     */
    public RadioButtonWT(UIValue<T> item)
    {
        super(item != null ? item.get().toString() : "");

        this.item = item;
        this.labeling = (t) -> t != null ? t.toString() : "";

        installFocusListener();
    }

    /**
     * 指定された選択対象とラベルでラジオボタンを生成する。
     * 
     * @param item 選択対象データ
     * @param labeling ラベル生成関数
     */
    public RadioButtonWT(UIValue<T> item, Function<T, String> labeling)
    {
        super(item != null ? labeling.apply(item.get()) : "");

        this.item = item;
        this.labeling = labeling;

        installFocusListener();
    }

    @Override
    public void dispose()
    {
        item = UIValue.of(null);
        labeling = null;
        isEnabled = UIValue.of(null);
        hasFocus = UIValue.of(null);
        fgColor = UIValue.of(null);
        bgColor = UIValue.of(null);
    }

    /**
     * 選択対象保持データを返す。
     * 
     * @return 選択対象保持データ
     */
    UIValue<T> getItem()
    {
        return item;
    }

    /**
     * フォーカスの監視をする。
     */
    protected void installFocusListener()
    {
        addFocusListener(new FocusListener()
        {
            @Override public void focusLost(FocusEvent e) { hasFocus.set(false); }

            @Override public void focusGained(FocusEvent e) { }
        });
    }

    @Override
    public void refreshWT()
    {
        // ラベルの更新
        setText(item != null ? labeling.apply(item.get()) : "");

        // 活性/非活性設定
        setEnabled(isEnabled.get());

        // フォント・背景色設定
        setForeground(fgColor.get());
        setBackground(bgColor.get());

        // フォーカス取得
        if(hasFocus.get()) requestFocusInWindow();
    }

    @Override
    public RadioButtonWT<T> padding(UIGap... gaps)
    {
        return WidgetHelper.padding(this, gaps);
    }

    @Override
    public RadioButtonWT<T> enabled(UIValue<Boolean> isEnabled)
    {
        this.isEnabled = isEnabled;
        this.isEnabled.addValueChangeListener(() -> WidgetHelper.invokeToRefresh(RadioButtonWT.this));
        setEnabled(isEnabled.get());
        return this;
    }

    @Override
    public RadioButtonWT<T> frame(UILength... lengths)
    {
        return WidgetHelper.frame(this, lengths);
    }

    @Override
    public RadioButtonWT<T> focus(UIValue<Boolean> hasFocus)
    {
        this.hasFocus = hasFocus;
        this.hasFocus.addValueChangeListener(() -> WidgetHelper.invokeToRefresh(RadioButtonWT.this));
        if(hasFocus.get()) requestFocusInWindow();
        return this;
    }

    @Override
    public RadioButtonWT<T> background(UIValue<Color> bgColor)
    {
        this.bgColor = bgColor;
        this.bgColor.addValueChangeListener(() -> WidgetHelper.invokeToRefresh(RadioButtonWT.this));
        setBackground(bgColor.get());
        return this;
    }

    @Override
    public RadioButtonWT<T> self(Consumer<RadioButtonWT<T>> self)
    {
        self.accept(this);
        return this;
    }

    /**
     * フォント色を設定する。
     * 
     * @param fgColor フォント色
     * @return 自身のインスタンス
     */
    public RadioButtonWT<T> foreground(UIValue<Color> fgColor)
    {
        this.fgColor = fgColor;
        this.fgColor.addValueChangeListener(() -> WidgetHelper.invokeToRefresh(RadioButtonWT.this));
        setForeground(fgColor.get());
        return this;
    }
}
