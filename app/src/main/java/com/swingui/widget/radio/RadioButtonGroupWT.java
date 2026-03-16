package com.swingui.widget.radio;

import java.awt.Color;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.swingui.value.UIValue;
import com.swingui.value.gap.UIGap;
import com.swingui.value.size.UILength;
import com.swingui.widget.Widget;
import com.swingui.widget.util.WidgetHelper;

/**
 * ラジオ・ボタン {@link RadioButtonWT} の排他選択を可能にする {@link Widget} 実装クラス
 * 
 * @author t.yoshida
 */
public class RadioButtonGroupWT<T> extends JPanel implements Widget<RadioButtonGroupWT<T>>
{
    // 活性/非活性状態
    private UIValue<Boolean> isEnabled = UIValue.of(true);

    // 背景色
    private UIValue<Color> bgColor = new UIValue<>(getBackground());

	// 選択値
	private UIValue<T> selected;
	
    // 管理下のラジオボタン
	private List<RadioButtonWT<T>> radios;

	// 選択値の変更前通知
	private BiConsumer<T, AtomicBoolean> onCheckChanging;

	// 選択値の変更通知
	private Consumer<T> onCheckChanged;

    public RadioButtonGroupWT(UIValue<T> selected, List<RadioButtonWT<T>> radios)
    {
        super();

        this.selected = selected;
		this.selected.addValueChangeListener
		(
			() -> WidgetHelper.invokeToRefresh(RadioButtonGroupWT.this)
		);

        this.radios = radios;

		// ラジオボタンのグループ追加と選択値に合わせてラジオボタンの選択
		ButtonGroup group = new ButtonGroup();
		for(RadioButtonWT<T> radio : radios)
		{
			group.add(radio);
			radio.setSelected(Objects.equals(selected.get(), radio.getItem().get()));
		}

        monitorRadioState();
    }

    @Override
    public void dispose()
    {
        isEnabled = UIValue.of(null);
		bgColor = UIValue.of(null);
		selected = UIValue.of(null);
		radios = null;
        onCheckChanging = null;
		onCheckChanged = null;
    }

	private void monitorRadioState()
	{
        // ラジオボタンの選択変更を監視するリスナー
        ChangeListener listener = new RadioButtonChangeListener();

		// 各ラジオボタンに変更リスナーを設定
		for(RadioButtonWT<T> radio : radios)
		{
			radio.addChangeListener(listener);
		}
    }

    @Override
    public void refreshWT()
    {
		// 選択状態→グループ配下ラジオボタンへ同期
		syncSelectedValueToGroup();

		// グループ配下のラジオボタンの活性/非活性設定
		syncEnabledSettingsInGroup();
    }

    @Override
    public RadioButtonGroupWT<T> padding(UIGap... gaps)
    {
		return WidgetHelper.padding(this, gaps);
    }

    @Override
    public RadioButtonGroupWT<T> enabled(UIValue<Boolean> isEnabled)
    {
        this.isEnabled = isEnabled;
        this.isEnabled.addValueChangeListener
		(
			() -> WidgetHelper.invokeToRefresh(RadioButtonGroupWT.this)
		);
        syncEnabledSettingsInGroup();
        return this;
    }

    @Override
    public RadioButtonGroupWT<T> frame(UILength... lengths)
    {
        return WidgetHelper.frame(this, lengths);
    }

    @Override
    public RadioButtonGroupWT<T> focus(UIValue<Boolean> hasFocus)
    {
        return this;
    }

    @Override
    public RadioButtonGroupWT<T> background(UIValue<Color> bgColor)
    {
        if(!isOpaque()) setOpaque(true);  // 背景色を表示するために不透明化

        this.bgColor = bgColor;
        this.bgColor.addValueChangeListener(() -> WidgetHelper.invokeToRefresh(RadioButtonGroupWT.this));
        setBackground(bgColor.get());
        return this;
    }

    @Override
    public RadioButtonGroupWT<T> self(Consumer<RadioButtonGroupWT<T>> self)
    {
		self.accept(this);
        return this;
    }

    /**
	 * 選択値の変更を受け入れるか否かを判断する。
	 * 
	 * @param onCheckChanging 選択値変更前通知
	 *                        ※変更予定値を基に選択変更の取消判断をし、その結果を {@code AtomicBoolean} に設定する。
	 * @return 自身のインスタンス
	 */
	public RadioButtonGroupWT<T> onCheckChanging(BiConsumer<T, AtomicBoolean> onCheckChanging)
	{
		this.onCheckChanging = onCheckChanging;
		return this;
	}

	/**
	 * 選択値の変更を通知する。
	 * 
	 * @param onCheckChanged 選択値変更通知
	 * @return 自身のインスタンス
	 */
	public RadioButtonGroupWT<T> onCheckChanged(Consumer<T> onCheckChanged)
	{
		this.onCheckChanged = onCheckChanged;
		return this;
	}

	/**
	 * グループ配下のラジオボタンの活性/非活性設定の同期
	 */
	private void syncEnabledSettingsInGroup()
	{
		for(RadioButtonWT<T> radio : radios)
		{
			radio.enabled(isEnabled);
		}
	}

	/**
	 * 選択状態に合わせたグループ配下のラジオボタン選択の同期
	 */
	private void syncSelectedValueToGroup()
	{
		// 選択値の更新（選択値の変更がトリガーの場合）
		// ※更新された選択値に対し、ラジオボタンを同期させる。
		for(RadioButtonWT<T> radio : radios)
		{
			if(Objects.equals(selected.get(), radio.getItem().get()) && !radio.isSelected())
			{
				radio.setSelected(true);
				if(onCheckChanged != null)
				{
					onCheckChanged.accept(selected.get());
				}
			}
			else
			{
				radio.setSelected(false);
			}
		}
	}

    private class RadioButtonChangeListener implements ChangeListener
    {
	    // 選択変更の取消確認中フラグ
	    boolean isAskingToChange = false;

        @Override
        public void stateChanged(ChangeEvent e)
        {
			// 選択値の更新（ラジオボタンの選択イベントがトリガーの場合）
			// ※選択されたラジオボタンに対し、選択値を同期させる。
			for(RadioButtonWT<T> radio : radios)
			{
				if(!radio.isSelected()) continue;

				if(!Objects.equals(selected.get(), radio.getItem().get()))
				{
					if(isAskingToChange) continue;  // 選択変更の取り消し確認中はブロック

					// 選択値の変更を取り消すか否かの確認処理
                    if(onCheckChanging != null)
                    {
                        boolean isCancel = cancelToChangeValueOrNot(radio);
                        if(isCancel) break;  // 取り消す場合、処理終了
                    }

                    // ラジオボタン押下に応じて、対応する選択値を更新
					selected.set(radio.getItem().get());
					if(onCheckChanged != null)
					{
						// 選択値の変更通知
						onCheckChanged.accept(selected.get());
					}
				}
			}
        }

		// 選択値の変更を取り消すか否かを判定する。
		private boolean cancelToChangeValueOrNot(RadioButtonWT<T> radio)
		{
			isAskingToChange = true;  // 変更取り消し確認開始

			AtomicBoolean cancelOrNot = new AtomicBoolean(false);  // 取消フラグ
			onCheckChanging.accept(radio.getItem().get(), cancelOrNot);
			if(cancelOrNot.get())
			{
				// 取消の場合、選択値は更新せず、更にラジオボタンの選択を元に戻す
				syncSelectedValueToGroup();
			}

			isAskingToChange = false;  // 変更取り消し確認終了

			return (cancelOrNot.get());
		}
    }
}
