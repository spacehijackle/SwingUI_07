package swingui_07.gender;

/**
 * 言語を表す列挙型
 * 
 * @author t.yoshida
 */
public enum Language
{
    /** 英語 */
    English("English"),

    /** 日本語 */
    Japanese("日本語");

    private String name;

    private Language(String name)
    {
        this.name = name;
    }

    /**
     * 言語名を取得する。
     * 
     * @return 言語名
     */
    public String getName() { return name; }
}
