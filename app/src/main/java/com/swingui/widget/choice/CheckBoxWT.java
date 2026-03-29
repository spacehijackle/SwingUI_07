package com.swingui.widget.choice;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.JCheckBox;

import com.swingui.value.UIValue;
import com.swingui.value.UIValue.ValueChangeListener;
import com.swingui.value.gap.UIGap;
import com.swingui.value.size.UILength;
import com.swingui.widget.Widget;
import com.swingui.widget.util.WidgetHelper;

/**
 * {@link JCheckBox} を拡張した {@link Widget} 実装クラス
 * 
 * @author t.yoshida
 *
 * @param <T> 選択対象データの型
 */
public class CheckBoxWT<T> extends JCheckBox implements Widget<CheckBoxWT<T>>
{
    // 選択対象保持データ
    private UIValue<T> item;

    // ラベル生成関数
    private Function<T, String> labeling;

    // 活性/非活性状態
    private UIValue<Boolean> isEnabled = new UIValue<>(true);

    // 選択状態
    private UIValue<Boolean> isChecked = new UIValue<>(false);

    // UIフォーカス状態
    private UIValue<Boolean> hasFocus = new UIValue<>(false);

    // 背景色
    private UIValue<Color> bgColor = new UIValue<>(getBackground());

    // チェック状態変更通知
    private Consumer<Boolean> onCheckChanged;

    // 対象値変更リスナー（ウィジェット更新の呼出）
    private final ValueChangeListener valChgListener = () -> WidgetHelper.invokeToRefresh(CheckBoxWT.this);

    /**
     * 指定された選択状態と選択対象でチェックボックスを生成する。
     * 
     * @param isChecked 選択状態
     * @param item 選択対象データ
     */
    public CheckBoxWT(UIValue<Boolean> isChecked, UIValue<T> item)
    {
        this(isChecked, item, (t) -> t.toString());
    }

    /**
     * 指定された選択状態と選択対象でチェックボックスを生成する。
     * 
     * @param isChecked 選択状態
     * @param item 選択対象データ
     * @param labeling ラベル生成関数
     */
    public CheckBoxWT(UIValue<Boolean> isChecked, UIValue<T> item, Function<T, String> labeling)
    {
        super(labeling.apply(item.get()), isChecked.get());

        this.isChecked = isChecked;
        this.isChecked.addValueChangeListener(valChgListener);

        this.item = item;
        this.item.addValueChangeListener(valChgListener);
        this.labeling = labeling;

        installFocusListener();
        installCheckStateListener();
    }

    @Override
    public void dispose()
    {
        item.removeValueChangeListener(valChgListener);
        isEnabled.removeValueChangeListener(valChgListener);
        isChecked.removeValueChangeListener(valChgListener);
        hasFocus.removeValueChangeListener(valChgListener);
        bgColor.removeValueChangeListener(valChgListener);

        item = UIValue.of(null);
        labeling = null;
        isEnabled = UIValue.of(null);
        isChecked = UIValue.of(null);
        hasFocus = UIValue.of(null);
        bgColor = UIValue.of(null);
        onCheckChanged = null;
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

    /**
     * チェック状態の監視をする。
     */
    private void installCheckStateListener()
    {
        addChangeListener(evt ->
        {
            boolean isSelected = super.isSelected();
            if(isSelected != isChecked.get())
            {
                isChecked.set(isSelected);
                if(onCheckChanged != null)
                {
                    // チェック状態の変更通知
                    onCheckChanged.accept(isSelected);
                }
            }
        });
    }

    @Override
    public void refreshWT()
    {
        // ラベルの更新
        setText(item != null ? labeling.apply(item.get()) : "");

        // 選択状態設定（活性/非活性設定より先に実施しないと反映しない）
        setSelected(isChecked.get());

        // 活性/非活性設定
        setEnabled(isEnabled.get());

        // 背景色設定
        setBackground(bgColor.get());

        // フォーカス取得
        if(hasFocus.get()) requestFocusInWindow();
    }

    @Override
    public CheckBoxWT<T> padding(UIGap... gaps)
    {
        return WidgetHelper.padding(this, gaps);
    }

    @Override
    public CheckBoxWT<T> enabled(UIValue<Boolean> isEnabled)
    {
        this.isEnabled.removeValueChangeListener(valChgListener);

        this.isEnabled = isEnabled;
        this.isEnabled.addValueChangeListener(valChgListener);
        setEnabled(isEnabled.get());
        return this;
    }

    @Override
    public CheckBoxWT<T> frame(UILength... lengths)
    {
        return WidgetHelper.frame(this, lengths);
    }

    @Override
    public CheckBoxWT<T> focus(UIValue<Boolean> hasFocus)
    {
        this.hasFocus.removeValueChangeListener(valChgListener);

        this.hasFocus = hasFocus;
        this.hasFocus.addValueChangeListener(valChgListener);
        if(hasFocus.get()) requestFocusInWindow();
        return this;
    }

    @Override
    public CheckBoxWT<T> background(UIValue<Color> bgColor)
    {
        this.bgColor.removeValueChangeListener(valChgListener);

        this.bgColor = bgColor;
        this.bgColor.addValueChangeListener(valChgListener);
        setBackground(bgColor.get());
        return this;
    }

    @Override
    public CheckBoxWT<T> self(Consumer<CheckBoxWT<T>> self)
    {
        self.accept(this);
        return this;
    }

    /**
     * チェック状態の変更を通知する。
     * 
     * @param onCheckChanged チェック状態変更通知
     * @return 自身のインスタンス
     */
    public CheckBoxWT<T> onCheckChanged(Consumer<Boolean> onCheckChanged)
    {
        this.onCheckChanged = onCheckChanged;
        return this;
    }
}
