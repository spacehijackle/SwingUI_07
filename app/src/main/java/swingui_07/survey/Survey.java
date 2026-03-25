package swingui_07.survey;

import javax.swing.JOptionPane;

import com.swingui.constant.UIAlignmentX;
import com.swingui.front.Frame;
import com.swingui.front.button.Button;
import com.swingui.front.choice.CheckBox;
import com.swingui.front.choice.RadioButton;
import com.swingui.front.choice.RadioButtonGroup;
import com.swingui.front.layout.Spacer;
import com.swingui.front.layout.VStack;
import com.swingui.front.text.Text;
import com.swingui.value.UIValue;
import com.swingui.value.gap.UIGap.Left;
import com.swingui.value.size.UILength.Height;
import com.swingui.value.size.UILength.Width;

/**
 * チェック・ボックスとラジオ・ボタンを使ったアンケートのサンプル
 * 
 * @author t.yoshida
 */
public class Survey
{
    //
    // 趣味の選択値（チェック・ボックス）
    //
    private final UIValue<Boolean> isPassbookGazingChecked = new UIValue<>(false);
    private final UIValue<Boolean> isNappingChecked = new UIValue<>(false);
    private final UIValue<Boolean> isZoningOutChecked = new UIValue<>(false);
    private final UIValue<Boolean> isPeopleWatchingChecked = new UIValue<>(false);
    private final UIValue<Boolean> isOtherChecked = new UIValue<>(false);

    //
    // 趣味の選択肢の活性/非活性状態（チェック・ボックス）
    //
    private final UIValue<Boolean> isPassbookGazingEnabled = new UIValue<>(true);
    private final UIValue<Boolean> isNappingEnabled = new UIValue<>(true);
    private final UIValue<Boolean> isZoningOutEnabled = new UIValue<>(true);
    private final UIValue<Boolean> isPeopleWatchingEnabled = new UIValue<>(true);

    // 年齢の選択値（ラジオボタン）
    private final UIValue<String> age = UIValue.of(null);

    public Survey()
    {
        build();
    }

    private void build()
    {
        Frame.of
        (
            "アンケート",

            VStack.of
            (
                UIAlignmentX.Leading,

                Text.of("趣味を選択してください（複数回答可）"),

                VStack.of
                (
                    UIAlignmentX.Leading,

                    CheckBox.of(isPassbookGazingChecked, "通帳を眺める")
                        .enabled(isPassbookGazingEnabled),
                    CheckBox.of(isNappingChecked, "ひたすら寝る")
                        .enabled(isNappingEnabled),
                    CheckBox.of(isZoningOutChecked, "ボーっとする")
                        .enabled(isZoningOutEnabled),
                    CheckBox.of(isPeopleWatchingChecked, "人間観察")
                        .enabled(isPeopleWatchingEnabled),
                    CheckBox.of(isOtherChecked, "その他")
                        .onCheckChanged(isChecked -> syncHobbyWhenOthersChanged(isChecked))
                )
                .padding(Left.of(8)),

                Spacer.of(Height.of(8)),

                Text.of("年齢を選択してください（単一回答）"),

                VStack.of
                (
                    UIAlignmentX.Leading,

                    RadioButtonGroup.of
                    (
                        age,
                        VStack.of
                        (
                            UIAlignmentX.Leading,

                            RadioButton.of("お子ちゃま"),
                            RadioButton.of("20代"),
                            RadioButton.of("30代"),
                            RadioButton.of("40代"),
                            RadioButton.of("50代"),
                            RadioButton.of("60代以上")
                        )
                    )
                )
                .padding(Left.of(8)),

                Spacer.of(Height.of(8)),

                Button.of("送 信")
                    .frame(Width.Infinite, Height.of(32))
                    .onClicked(self ->
                    {
                        checkBeforeSubmit();
                        JOptionPane.showMessageDialog(self.getRootPane(), "送信完了");
                    })
            )
            .padding(24)
            .frame(Width.of(300))
        );
    }

    /**
     * 趣味の「その他」の選択状態がした時、他の選択肢との同期を行う。
     * 
     * @param isChecked 「その他」の選択状態
     */
    private void syncHobbyWhenOthersChanged(boolean isChecked)
    {
        if(isChecked)
        {
            // 「その他」が選択された場合、他の選択肢を全てオフにし、選択できないようにする
            isPassbookGazingChecked.set(false);
            isNappingChecked.set(false);
            isZoningOutChecked.set(false);
            isPeopleWatchingChecked.set(false);

            isPassbookGazingEnabled.set(false);
            isNappingEnabled.set(false);
            isZoningOutEnabled.set(false);
            isPeopleWatchingEnabled.set(false);
        }
        else
        {
            // 「その他」の選択が外された場合、他の選択肢を選択できるようにする
            isPassbookGazingEnabled.set(true);
            isNappingEnabled.set(true);
            isZoningOutEnabled.set(true);
            isPeopleWatchingEnabled.set(true);
        }
    }

    // 送信前の入力値チェック処理
    private void checkBeforeSubmit()
    {
        // 趣味の選択がない場合、適当に選択
        if(!isPassbookGazingChecked.get() && !isNappingChecked.get()
        && !isZoningOutChecked.get() && !isPeopleWatchingChecked.get() && !isOtherChecked.get())
        {
            isNappingChecked.set(true);
        }

        // 年齢の選択がない場合、適当に選択
        if(age.get() == null)
        {
            age.set("60代以上");
        }
    }
}
