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
    // 趣味の選択値（チェックボックス）
    //
    private final UIValue<Boolean> isPassbookGazing = new UIValue<>(false);
    private final UIValue<Boolean> isNapping = new UIValue<>(false);
    private final UIValue<Boolean> isZoningOut = new UIValue<>(false);
    private final UIValue<Boolean> isPeopleWatching = new UIValue<>(false);
    private final UIValue<Boolean> isOther = new UIValue<>(false);

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

                    CheckBox.of(isPassbookGazing, "通帳を眺める"),
                    CheckBox.of(isNapping, "ひたすら寝る"),
                    CheckBox.of(isZoningOut, "ボーっとする"),
                    CheckBox.of(isPeopleWatching, "人間観察"),
                    CheckBox.of(isOther, "その他")
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

    // 送信前の入力値チェック処理
    private void checkBeforeSubmit()
    {
        // 趣味の選択がない場合、適当に選択
        if(!isPassbookGazing.get() && !isNapping.get()
        && !isZoningOut.get() && !isPeopleWatching.get() && !isOther.get())
        {
            isNapping.set(true);
        }

        // 年齢の選択がない場合、適当に選択
        if(age.get() == null)
        {
            age.set("60代以上");
        }
    }
}
