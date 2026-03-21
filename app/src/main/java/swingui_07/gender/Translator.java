package swingui_07.gender;

/**
 * 翻訳クラス
 * 
 * @author t.yoshida
 */
public class Translator
{
    /**
     * 指定された言語に応じた人間確認のラベル・テキストを返す。
     * 
     * @param lang 言語
     * @return テキスト
     */
    public static String getLabel(Language lang)
    {
        switch(lang)
        {
            case English:
                return "I'm human, not a robot.";
            case Japanese:
                return "私はロボットではありません。";
            default:
                return "";
        }
    }

    /**
     * 指定された言語に応じた性別のテキストを返す。
     * 
     * @param lang 言語
     * @param gender 性別
     * @return テキスト
     */
    public static String getGender(Language lang, Gender gender)
    {
        switch(lang)
        {
            case English:
                switch(gender)
                {
                    case Male:
                        return "Male";
                    case Female:
                        return "Female";
                    case Other:
                        return "Other";
                }
                break;
            case Japanese:
                switch(gender)
                {
                    case Male:
                        return "♂男性";
                    case Female:
                        return "♀女性";
                    case Other:
                        return "その他";
                }
                break;
        }
        return "";
    }
}
