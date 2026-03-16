package swingui_07;

import javax.swing.SwingUtilities;

import swingui_07.guitar.DynamicTextDecoration;
import swingui_07.guitar.GuitarCommentary;

public class Startup
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new GuitarCommentary().test());
        //SwingUtilities.invokeLater(() -> new DynamicTextDecoration().test());
    }
}
