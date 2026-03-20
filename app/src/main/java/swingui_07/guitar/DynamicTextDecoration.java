package swingui_07.guitar;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JCheckBox;

import com.swingui.constant.HorizontalAlignment;
import com.swingui.front.Frame;
import com.swingui.front.button.Button;
import com.swingui.front.layout.HStack;
import com.swingui.front.layout.Spacer;
import com.swingui.front.layout.VStack;
import com.swingui.front.radio.RadioButton;
import com.swingui.front.radio.RadioButtonGroup;
import com.swingui.front.text.Text;
import com.swingui.value.UIValue;
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
    private final UIValue<Boolean> isHuman = new UIValue<>(false);

    private final UIValue<String> selectedGender = UIValue.of("男性");

    public void test()
    {
        Frame.of
        (
            "ラベル修飾テスト",

            VStack.of
            (
                new JCheckBox("人間である"),

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
            )
            .padding(24)
        );
    }
}
