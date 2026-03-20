package swingui_07;

import javax.swing.SwingUtilities;

import swingui_07.guitar.DynamicTextDecoration;
import swingui_07.guitar.GuitarCommentary;
import swingui_07.guitar.GuitarCommentary2;

public class Startup
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new GuitarCommentary());
        SwingUtilities.invokeLater(() -> new GuitarCommentary2());
        //SwingUtilities.invokeLater(() -> new DynamicTextDecoration().test());
    }
}
