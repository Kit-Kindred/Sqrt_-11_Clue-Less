package Client.App.views;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogPanel extends JPanel
{
    JTextPane logDisplay;
    JScrollPane logPane;

    LogPanel()
    {
        setLayout(new BorderLayout());
        logDisplay = new JTextPane();
        logDisplay.setEditable(false);

        logPane = new JScrollPane(logDisplay);
        logPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        add(logPane);
    }

    public void log(Color color, String msg)
    {
        try
        {
            SimpleDateFormat date_format = new SimpleDateFormat("HH:mm");
            Date resultDate = new Date(System.currentTimeMillis());  // Not real log time, but whatevs
            String prefix = date_format.format(resultDate);
            String logString = prefix + "> " + msg + "\n";

            SimpleAttributeSet attributeSet = new SimpleAttributeSet();
            StyleConstants.setForeground(attributeSet, color);

            Document doc = logDisplay.getStyledDocument();
            doc.insertString(doc.getLength(), logString, attributeSet);
        }
        catch (BadLocationException e)
        {
            e.printStackTrace();
        }
    }
}
