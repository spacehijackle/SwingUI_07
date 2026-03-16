package swingui_07.guitar;

/**
 * ギター・クラス
 * 
 * @author t.yoshida
 */
public class Guitar
{
    /** エレキ・ギター */
    public static final Guitar ELECTRIC = new Guitar
    (
        "Electric Guitar", "金属の弦を使い、ピックアップで振動を電気信号に変えてアンプから出音"
    );

    /** アコースティック・ギター*/
    public static final Guitar ACOUSTIC = new Guitar
    (
        "Acoustic Guitar", "木製のボディと金属製の弦を使い、弦の振動をボディで共鳴させて出音"
    );

    /** クラシック・ギター */
    public static final Guitar CLASSICAL = new Guitar
    (
        "Classical Guitar", "木製のボディとナイロン弦を使い、弦の振動をボディで共鳴させて出音"
    );

    /** エア・ギター */
    public static final Guitar AIR = new Guitar
    (
        "Air Guitar", "どんなにかき鳴らしても無音"
    );

    /** ギターのタイプ */
    public final String type;

    /** ギターの解説 */
    public final String comment;

    /**
     * ギターのタイプと解説を指定してギターを生成する。
     * 
     * @param type ギターのタイプ
     * @param comment ギターの解説
     */
    private Guitar(String type, String comment)
    {
        this.type = type;
        this.comment = comment;
    }

    @Override
    public int hashCode()
    {
        return type.hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Guitar guitar = (Guitar)o;
        return type.equals(guitar.type);
    }

    @Override
    public String toString()
    {
        return type;
    }
}
