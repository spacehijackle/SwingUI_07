package swingui_07.gender;

import javax.swing.SwingUtilities;

import com.swingui.constant.UIAlignmentX;
import com.swingui.front.Frame;
import com.swingui.front.choice.CheckBox;
import com.swingui.front.choice.RadioButton;
import com.swingui.front.choice.RadioButtonGroup;
import com.swingui.front.layout.HStack;
import com.swingui.front.layout.Spacer;
import com.swingui.front.layout.VStack;
import com.swingui.value.UIValue;
import com.swingui.value.gap.Symmetry.Horizontal;
import com.swingui.value.size.UILength.Height;
import com.swingui.value.size.UILength.Width;

/**
 * 動的に選択用テキストを変更するサンプル
 * 
 * @author t.yoshida
 */
public class GenderChoice
{
    // 言語の選択値（ラジオ・ボタン）
    private final UIValue<Language> lang = UIValue.of(Language.English);

    // 人間確認の選択値（チェックボックス）
    private final UIValue<Boolean> isHuman = new UIValue<>(false);

    // 人間確認のラベル
    private UIValue<String> humanOrNot = UIValue.of(Translator.getLabel(lang.get()));

    // 性別選択ラジオ・ボタンの活性/非活性状態
    private final UIValue<Boolean> isGenderEnabled = new UIValue<>(false);

    // 性別選択の選択値（ラジオ・ボタン）
    //private final UIValue<String> gender = UIValue.of(null);
    private final UIValue<String> gender = UIValue.of("その他");

    public GenderChoice()
    {
        build();
    }

    private void build()
    {
        Frame.of
        (
            "性別選択サンプル",

            VStack.of
            (
                UIAlignmentX.Leading,

                HStack.of
                (
                    Spacer.fill(),

                    RadioButtonGroup.of
                    (
                        lang,
                        HStack.of
                        (
                            RadioButton.of(Language.English, (l) -> l.getName()),
                            RadioButton.of(Language.Japanese, (l) -> l.getName())
                        )
                    )
                    .onCheckChanged((lang) -> humanOrNot.set(Translator.getLabel(lang)))
                ),

                Spacer.of(Height.of(8)),

                // 人間確認のチェックボックス
                CheckBox.of(isHuman, humanOrNot)
                    .self(self ->
                    {
                        // アプリ起動時のフォーカス獲得
                        SwingUtilities.invokeLater(() -> self.requestFocusInWindow());
                    })
                    .onCheckChanged(isChecked -> isGenderEnabled.set(isChecked)),

                // 性別選択のラジオ・ボタン
                RadioButtonGroup.of
                (
                    gender,
                    HStack.of
                    (
                        RadioButton.of(Gender.Male, (g) -> Translator.getGender(lang.get(), g))
                            .frame(Width.of(68)),
                        RadioButton.of(Gender.Female, (g) -> Translator.getGender(lang.get(), g))
                            .frame(Width.of(68)),
                        RadioButton.of(Gender.Other, (g) -> Translator.getGender(lang.get(), g))
                            .frame(Width.of(68))
                    )
                )
                .enabled(isGenderEnabled)
                .padding(Horizontal.of(8))
            )
            .padding(24)
        );
    }
}
