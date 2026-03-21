package swingui_07.guitar;

import java.awt.Color;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

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
public class GuitarCommentary2
{
    // ギターのタイプ一覧
    private final List<Guitar> guitars = Arrays.asList
    (
        Guitar.ELECTRIC, Guitar.ACOUSTIC, Guitar.CLASSICAL, Guitar.AIR
    );

    // ギターのタイプ選択値
    private final UIValue<Guitar> selected = UIValue.of(Guitar.ELECTRIC);

    // ギタータイプの解説テキストのフォントカラー更新クラス
    private CommentColorUpdater fgUpdater = new CommentColorUpdater();

    public GuitarCommentary2()
    {
        fgUpdater.update();
        build();
    }

    private void build()
    {
        Frame.of
        (
            "ギターの種類 - VStack#forEach(...)",

            RadioButtonGroup.of
            (
                selected,
                VStack.forEach
                (
                    UIAlignmentX.Leading,
                    guitars,  // ギターのタイプ一覧
                    (idx, guitar) ->
                    {
                        // ギターのタイプ毎のラジオ・ボタン選択とその説明テキストを追加
                        return VStack.of
                        (
                            UIAlignmentX.Leading,

                            RadioButton.of(guitar),

                            Text.of(guitar.comment)
                                .foreground(fgUpdater.colorMap.get(guitar))
                                .padding(Horizontal.of(16)),

                            // 末尾以外、余白を追加
                            (idx != (guitars.size() - 1) ? Spacer.of(Height.of(8)) : Spacer.empty())
                        );
                    }
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
        final Color SELECTED = Color.darkGray;

        // 非選択状態のフォントカラー
        final Color UNSELECTED = Color.gray;

        //
        // ギタータイプ別の解説テキストのフォントカラー
        //
        private UIValue<Color> electric = UIValue.of(UNSELECTED);
        private UIValue<Color> acoustic = UIValue.of(UNSELECTED);
        private UIValue<Color> classical = UIValue.of(UNSELECTED);
        private UIValue<Color> airy = UIValue.of(UNSELECTED);

        // ギタータイプと解説テキストのフォントカラーの紐づけを保持するマップ
        final Map<Guitar, UIValue<Color>> colorMap = new HashMap<>();

        CommentColorUpdater()
        {
            // ギタータイプと解説テキストのフォントカラーの紐づけ
            colorMap.put(Guitar.ELECTRIC, electric);
            colorMap.put(Guitar.ACOUSTIC, acoustic);
            colorMap.put(Guitar.CLASSICAL, classical);
            colorMap.put(Guitar.AIR, airy);
        }

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
