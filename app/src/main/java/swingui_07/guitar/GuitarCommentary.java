package swingui_07.guitar;

import java.awt.Color;

import com.swingui.constant.UIAlignmentX;
import com.swingui.front.Frame;
import com.swingui.front.choice.RadioButton;
import com.swingui.front.choice.RadioButtonGroup;
import com.swingui.front.layout.Spacer;
import com.swingui.front.layout.VStack;
import com.swingui.front.text.Text;
import com.swingui.value.UIValue;
import com.swingui.value.gap.Symmetry.Horizontal;
import com.swingui.value.size.UILength.Height;

/**
 * ラジオ・ボタンを使ってギターの種類を解説するサンプル
 * 
 * @author t.yoshida
 */
public class GuitarCommentary
{
    // ギターのタイプの選択値
    private final UIValue<Guitar> selected = UIValue.of(Guitar.ELECTRIC);

    // ギタータイプの解説テキストのフォントカラー更新クラス
    private CommentColorUpdater fgUpdater = new CommentColorUpdater();

    public GuitarCommentary()
    {
        fgUpdater.update();
        build();
    }

    private void build()
    {
        Frame.of
        (
            "ギターの種類",

            RadioButtonGroup.of
            (
                selected,
                VStack.of
                (
                    UIAlignmentX.Leading,  // 左寄せ

                    ///
                    /// エレキ・ギター
                    /// 
                    RadioButton.of(Guitar.ELECTRIC),

                    Text.of(Guitar.ELECTRIC.comment)
                        .foreground(fgUpdater.electric)
                        .padding(Horizontal.of(16)),

                    Spacer.of(Height.of(8)),

                    ///
                    /// アコースティック・ギター
                    /// 
                    RadioButton.of(Guitar.ACOUSTIC),

                    Text.of(Guitar.ACOUSTIC.comment)
                        .foreground(fgUpdater.acoustic)
                        .padding(Horizontal.of(16)),

                    Spacer.of(Height.of(8)),

                    ///
                    /// クラシック・ギター
                    /// 
                    RadioButton.of(Guitar.CLASSICAL),

                    Text.of(Guitar.CLASSICAL.comment)
                        .foreground(fgUpdater.classical)
                        .padding(Horizontal.of(16)),

                    Spacer.of(Height.of(8)),

                    ///
                    /// エア・ギター
                    /// 
                    RadioButton.of(Guitar.AIR),

                    Text.of(Guitar.AIR.comment)
                        .foreground(fgUpdater.airy)
                        .padding(Horizontal.of(16))
                )
            )
            .padding(24)
            .onCheckChanging
            (
                // AIRギターは選択をキャンセル
                (guitar, isCanceling) -> { if(guitar.equals(Guitar.AIR)) isCanceling.set(true); }
            )
            .onCheckChanged((guitar) -> fgUpdater.update())  // 選択値の変更の度に解説テキストのフォント色更新
        );
    }

    /**
     * ギタータイプ解説テキストのフォントカラー更新クラス
     */
    class CommentColorUpdater
    {
        // 選択状態のフォントカラー
        private final Color SELECTED = Color.darkGray;

        // 非選択状態のフォントカラー
        private final Color UNSELECTED = Color.gray;

        //
        // ギタータイプ別の解説テキストのフォントカラー
        //
        private UIValue<Color> electric = UIValue.of(UNSELECTED);
        private UIValue<Color> acoustic = UIValue.of(UNSELECTED);
        private UIValue<Color> classical = UIValue.of(UNSELECTED);
        private UIValue<Color> airy = UIValue.of(UNSELECTED);

        /**
         * 各ギタータイプ解説のフォントカラーを選択状態に応じて更新する。
         */
        void update()
        {
            electric.set(Guitar.ELECTRIC.equals(selected.get()) ? SELECTED : UNSELECTED);
            acoustic.set(Guitar.ACOUSTIC.equals(selected.get()) ? SELECTED : UNSELECTED);
            classical.set(Guitar.CLASSICAL.equals(selected.get()) ? SELECTED : UNSELECTED);
            airy.set(Guitar.AIR.equals(selected.get()) ? SELECTED : UNSELECTED);
        }
    }
}
