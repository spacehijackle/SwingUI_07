package swingui_07.guitar;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;

import com.swingui.constant.HorizontalAlignment;
import com.swingui.constant.UIAlignmentX;
import com.swingui.front.Frame;
import com.swingui.front.button.Button;
import com.swingui.front.choice.CheckBox;
import com.swingui.front.choice.RadioButton;
import com.swingui.front.choice.RadioButtonGroup;
import com.swingui.front.layout.HStack;
import com.swingui.front.layout.Spacer;
import com.swingui.front.layout.VStack;
import com.swingui.front.text.Text;
import com.swingui.value.Spacing;
import com.swingui.value.UIValue;
import com.swingui.value.gap.Symmetry.Horizontal;
import com.swingui.value.size.UILength.Height;
import com.swingui.value.size.UILength.Width;
import com.swingui.widget.text.renderer.LineAttr.LineColor;
import com.swingui.widget.text.renderer.LineAttr.LineStyle;

/**
 * 動的にテキスト装飾するサンプル
 * 
 * @author t.yoshida
 */
public class DynamicTextDecoration
{
    //
    private final UIValue<Language> lang = UIValue.of(Language.English);

    // 人間確認の選択値
    private final UIValue<Boolean> isHuman = new UIValue<>(false);

    private final UIValue<HumanConfirmer> humanConfirmer = UIValue.of(new HumanConfirmer(Translator.getLabel(lang.get())));

    private final UIValue<Boolean> isGenderEnabled = new UIValue<>(false);
    //private final UIValue<String> selectedGender = UIValue.of(null);
    private final UIValue<String> selectedGender = UIValue.of("その他");

    public void test()
    {
        Frame.of
        (
            "ラベル修飾テスト",

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
                    .onCheckChanged((lang) -> humanConfirmer.get().setLabel(Translator.getLabel(lang)))
                ),

                Spacer.of(Height.of(8)),

                // 人間確認のチェックボックス
                CheckBox.<HumanConfirmer>of(isHuman, humanConfirmer, (h) -> h.getLabel())
                    .onCheckChanged(isChecked -> isGenderEnabled.set(isChecked)),

                // 性別選択のラジオ・ボタン
                RadioButtonGroup.of
                (
                    selectedGender,
                    HStack.of
                    (
                        RadioButton.of("♂ 男性"),
                        RadioButton.of("♀ 女性"),
                        RadioButton.of("その他")
                    )
                )
                .enabled(isGenderEnabled)
                .padding(Horizontal.of(8))
            )
            .padding(24)
        );
    }

    static class Translator
    {
        public static String getLabel(Language lang)
        {
            String label = "";
            switch(lang)
            {
                case English:
                    label = "I'm human, not a robot.";
                    break;
                case Japanese:
                    label = "私はロボットではありません。";
                    break;
            }
            return label;
        }

        public static String getGenderLabel(Language lang, Gender gender)
        {
            String label = "";
            switch(lang)
            {
                case English:
                    switch(gender)
                    {
                        case Male:
                            label = "Male";
                            break;
                        case Female:
                            label = "Female";
                            break;
                        case Other:
                            label = "Other";
                            break;
                    }
                    break;
                case Japanese:
                    switch(gender)
                    {
                        case Male:
                            label = "♂男性";
                            break;
                        case Female:
                            label = "♀女性";
                            break;
                        case Other:
                            label = "その他";
                            break;
                    }
                    break;
            }
            return label;
        }
    }

    enum Language
    {
        English("English"),
        Japanese("日本語");

        private String name;

        private Language(String name)
        {
            this.name = name;
        }

        public String getName() { return name; }
    }

    enum Gender
    {
        Male,
        Female,
        Other;
    }

    class HumanConfirmer
    {
        private String label;

        public HumanConfirmer(String label)
        {
            this.label = label;
        }

        public String getLabel()
        {
            return label;
        }

        public void setLabel(String label)
        {
            this.label = label;
        }
    }
}
