package swingui_07;

import javax.swing.SwingUtilities;

import swingui_07.gender.GenderChoice;
import swingui_07.guitar.GuitarCommentary;
import swingui_07.guitar.GuitarCommentary2;
import swingui_07.survey.Survey;

public class Startup
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new Survey());
        SwingUtilities.invokeLater(() -> new GenderChoice());
        SwingUtilities.invokeLater(() -> new GuitarCommentary());
        SwingUtilities.invokeLater(() -> new GuitarCommentary2());
    }
}
