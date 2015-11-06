package training.editor.eduUI;

import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.Gray;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;
import training.lesson.exceptons.BadCourseException;
import training.lesson.exceptons.BadLessonException;
import training.lesson.exceptons.LessonIsOpenedException;
import training.editor.EduEditor;
import training.lesson.Course;
import training.lesson.CourseManager;
import training.lesson.Lesson;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by karashevich on 26/06/15.
 */
public class EduPanel extends JPanel {

    private EduEditor eduEditor;
    private int width;

    //Lesson panel items
    private JPanel lessonPanel;
    private JLabel lessonNameLabel; //Name of the current lesson
    private LessonMessagePane lessonMessagePane;
    private JButton lessonNextButton;

    private JButton lessonNextTestButton;


    //UI Preferences
    private Color background;
    private Color defaultTextColor; //default text color
    private int insets;

    //lessonPanel UI
    private int fontSize;
    private int lessonNameGap;
    private int lessonNextButtonGap;
    private int messageGap;
    private Font messageFont;
    private Font lessonNameFont;
    private Color shortcutTextColor;
    private Color passedColor;
    private Color lessonPassedColor;
    private Color lessonCodeColor;
    private Color lessonLinkColor;


    //separator UI
    private Color separatorColor;
    private int separatorGap;

    //Course panel stuff
    private JPanel coursePanel;
    private ArrayList<JLabel> lessonsLabels;

    //coursePanel UI
    private int lessonGap;
    private Color lessonActiveColor;
    private Color lessonInactiveColor;
    private Font lessonsFont;
    private Font allLessonsFont;


    public EduPanel(EduEditor eduEditor, int width){
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //TODO: Don't forget to change it back
        setFocusable(false);
        this.width = width;
        this.eduEditor = eduEditor;

        //Obligatory block
        generalizeUI();
        this.setBackground(background);
        initLessonPanel();
        initCoursePanel();

        lessonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(lessonPanel);
        final Box.Filler filler = new Box.Filler(
                new Dimension(5, separatorGap),    //minimum size of filler
                new Dimension(5, separatorGap),    //preferable size of filler
                new Dimension(5, Short.MAX_VALUE)     //maximum size of filler
        );
        filler.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(filler);
        coursePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(coursePanel);

        //set EduPanel UI
        this.setPreferredSize(new Dimension(width, 100));
        this.setBorder(new EmptyBorder(insets, insets, insets, insets));


        //message test
//        addMessage("<html>Comment out any line 23u5 35" +
//                "23 45" +
//                "23 4" +
//                " 234" +
//                "2346 34 623462346236 346234 6 2346with <b>Ctrl + /</b> </html>");
//        setPreviousMessagesPassed();
//        addMessage("<html>Comment the line with</html>");
//        setPreviousMessagesPassed();
//        addMessage("<html>Comment the line with the same shortcut</html>");
//        setPreviousMessagesPassed();
//        addMessage("<html>Comment the line with the same shortcut</html>");
//        addMessage("<html>Comment the line with the same shortcut</html>");


    }

    private void generalizeUI() {
        //generalize fonts, colors and sizes
        //TODO: change size to UiUtil size
        fontSize = 12;
        insets = 10;
        lessonNameGap = 10;
        lessonNextButtonGap = 10;
        messageGap = 10;

        //separator UI
        separatorGap = 10;

        //course UI
        lessonGap = 10;

        //UI colors and fonts
        background = new JBColor(Gray._250, Gray._50);
        lessonNameFont = new Font(JBUI.Fonts.label().getName(), Font.BOLD, fontSize + 1);
        messageFont = JBUI.Fonts.label();
        defaultTextColor = new JBColor(Gray._30, Gray._208);
        passedColor = new JBColor(Gray._105, Gray._103);
        lessonPassedColor = new JBColor(new Color(49, 140, 64), new Color(7, 140, 45));
        lessonCodeColor = new JBColor(new Color(27, 78, 128), new Color(85, 161, 255));
        lessonLinkColor = new JBColor(new Color(17, 96, 166), new Color(104, 159, 220));

        //shortcut UI
        shortcutTextColor = new JBColor(Gray._12, Gray._200);

        //separator UI
        separatorColor = new JBColor(Gray._222, Gray._149);

        //course UI
        lessonActiveColor = new JBColor(Gray._167, Gray._202);
        lessonInactiveColor = lessonLinkColor;
//        lessonsFont = new Font(UIUtil.getFont(fontSize, ), 0, fontSize);
        lessonsFont = JBUI.Fonts.label(fontSize);
        allLessonsFont = JBUI.Fonts.label(fontSize - 1);

    }



    private void initLessonPanel(){
        lessonPanel = new JPanel();
        lessonPanel.setLayout(new BoxLayout(lessonPanel, BoxLayout.Y_AXIS));
        lessonPanel.setBackground(background);
        lessonPanel.setFocusable(false);

        lessonNameLabel = new JLabel();
        lessonNameLabel.setFont(lessonNameFont);
        lessonNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        lessonNameLabel.setFocusable(false);;

//        lessonMessageContainer = new JPanel();
//        messages = new ArrayList<LessonMessage>();
//        lessonMessageContainer.setLayout(new BoxLayout(lessonMessageContainer, BoxLayout.Y_AXIS));
//        lessonMessageContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        lessonMessagePane = new LessonMessagePane();
        lessonMessagePane.setFocusable(false);
        //Set lessonMessagePane UI
        lessonMessagePane.setBackground(background);
        lessonMessagePane.setUI(defaultTextColor, shortcutTextColor, lessonCodeColor, lessonLinkColor, passedColor);
        lessonMessagePane.setAlignmentX(Component.LEFT_ALIGNMENT);
        lessonMessagePane.setMargin(new Insets(0, 0, 0, 0));
        lessonMessagePane.setBorder(new EmptyBorder(0, 0, 0, 0));

        //Set Next Button UI
        lessonNextButton = new JButton("Next");
//        lessonNextButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        lessonNextButton.setVisible(false);
        lessonNextButton.setBackground(background);
        lessonNextButton.setMargin(new Insets(0, 0, 0, 0));
        lessonNextButton.setFocusable(false);;


        //Add new NextButton TEST
//        lessonNextTestButton = new JButton("Test Next");
//        lessonNextTestButton.setVisible(true);
//        lessonNextTestButton.setBackground(background);
//        lessonNextTestButton.setMargin(new Insets(0, 0, 0, 0));


        lessonPanel.add(lessonNameLabel);
//        lessonPanel.add(Box.createRigidArea(new Dimension(0, lessonNameGap)));
        lessonPanel.add(lessonMessagePane);
        lessonPanel.add(Box.createRigidArea(new Dimension(0, lessonNextButtonGap)));
        lessonPanel.add(lessonNextButton);
        lessonPanel.add(Box.createRigidArea(new Dimension(0, lessonNextButtonGap)));
    }


    public void setLessonName(String lessonName){
        lessonNameLabel.setText(lessonName);
        lessonNameLabel.setForeground(defaultTextColor);
        lessonNameLabel.setFocusable(false);
        this.revalidate();
        this.repaint();
    }

//    public void addMessage(String text){
//        JLabel newLabel = new JLabel(XmlUtil.addHtmlTags(text));
//        newLabel.setFont(messageFont);
//        final LessonMessage lessonMessage = new LessonMessage(newLabel, null);
//        messages.add(lessonMessage);
//        final JPanel lessonMessagePanel = lessonMessage.getPanel();
//        lessonMessagePanel.setBackground(background);
//        lessonMessagePanel.setBorder(new EmptyBorder(0, 0, messageGap, 0));
//
//        lessonMessageContainer.add(lessonMessagePanel);
//
//        //Pack lesson panel
//        lessonPanel.revalidate();
//        this.revalidate();
//        this.repaint();
//    }

    public void addMessage(String text){
        lessonMessagePane.addMessage(text);

        //Pack lesson panel
        lessonPanel.revalidate();
        this.revalidate();
        this.repaint();
    }

    public void addMessage(Message[] messages) {

        for (final Message message : messages) {
            if(message.getType() == Message.MessageType.LINK)  {
                //add link handler
                message.setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        final Lesson lesson = CourseManager.getInstance().findLesson(message.getText());
                        if (lesson != null) {
                            try {
                                CourseManager.getInstance().openLesson(eduEditor.getEditor().getProject(), lesson);
                            } catch (BadCourseException e) {
                                e.printStackTrace();
                            } catch (BadLessonException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (FontFormatException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (LessonIsOpenedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }

        lessonMessagePane.addMessage(messages);

        //Pack lesson panel
        lessonPanel.revalidate();
        this.revalidate();
        this.repaint();

    }


//    public void addMessage(String text, String shortcut){
//        JLabel newLabel = new JLabel(XmlUtil.addHtmlTags(text));
//        newLabel.setFont(messageFont);
//        final LessonMessage lessonMessage = new LessonMessage(newLabel, new ShortcutLabel(shortcut,shortcutFont, shortcutTextColor, shortcutBckColor, shortcutBorderColor));
//        messages.add(lessonMessage);
//        final JPanel lessonMessagePanel = lessonMessage.getPanel();
//        lessonMessagePanel.setBackground(background);
//        lessonMessagePanel.setBorder(new EmptyBorder(0, 0, messageGap, 0));
//
//        lessonMessageContainer.add(lessonMessagePanel);
//        this.revalidate();
//        this.repaint();
//    }

    public void setPreviousMessagesPassed(){
        try {
            lessonMessagePane.passPreviousMessages();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
//        if (messages == null) return;
//        for (LessonMessage message : messages) {
////            message.setForeground(passedColor);
//            if (message.getLabel().getForeground() != passedColor) {
//                message.getLabel().setForeground(passedColor);
//            }
//        }
//
//        //add to last message check mark
//        LessonMessage lastMessage = messages.get(messages.size() - 1);
//        String text = XmlUtil.removeHtmlTags(lastMessage.getLabel().getText());
//        text += " ✔";
//        lastMessage.getLabel().setText(XmlUtil.addHtmlTags(text));
//
    }

    public void setLessonPassed(){

        lessonNameLabel.setForeground(lessonPassedColor);
        showNextButton();
        this.repaint();
    }

    private void showNextButton() {
        lessonNextButton.setVisible(true);
        lessonPanel.revalidate();
        lessonPanel.repaint();
//        lessonNextButton.requestFocus(true); focus requesting is danger here, may interfere with windows like File Structure
    }


    private void initCoursePanel(){
        coursePanel = new JPanel();
        coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS));
        coursePanel.setBackground(background);
        coursePanel.setFocusable(false);

        //define separator
        coursePanel.setBorder(new MatteBorder(1, 0, 0, 0, separatorColor));

        lessonsLabels = new ArrayList<JLabel>();

    }


    public void setAllLessons(final Lesson lesson){
        Icon checkmarkIcon = IconLoader.getIcon(EduIcons.CHECKMARK_BLUE);
        Icon checkmarkIconSelected = IconLoader.getIcon(EduIcons.CHECKMARK_GRAY);

        if(lesson == null) return;
        if(lesson.getCourse() == null) return;
        Course course = lesson.getCourse();
        final ArrayList<Lesson> myLessons = course.getLessons();

        //if course contains one lesson only
        if (myLessons.size() == 1) return;
        //cleat AllLessons region
        if(lessonsLabels.size() > 0) {
            while (lessonsLabels.size() > 0){
                coursePanel.remove(lessonsLabels.get(0));
                lessonsLabels.remove(0);
            }


        } else {
            JLabel allLessons = new JLabel();
            allLessons.setText("ALL LESSONS");
            allLessons.setFont(allLessonsFont);
            allLessons.setFocusable(false);

            coursePanel.add(Box.createRigidArea(new Dimension(0, 5)));
            coursePanel.add(allLessons);
            coursePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        for (int i = 0; i < myLessons.size(); i++) {
            Lesson currentLesson = myLessons.get(i);
            String id = currentLesson.getName();

            if (lesson.equals(currentLesson)){
                //selected lesson
                final JLabel e = new JLabel(id);
                if (currentLesson.getPassed()) e.setIcon(checkmarkIconSelected);
                e.setHorizontalTextPosition(SwingConstants.LEFT);
                e.setForeground(lessonActiveColor);
                e.setBorder(new EmptyBorder(0, 0, lessonGap, 0));
                e.setFont(lessonsFont);
                e.setFocusable(false);
                lessonsLabels.add(e);
                coursePanel.add(e);
            } else {
                final JLabel e = new JLabel(id);
                if (currentLesson.getPassed()) e.setIcon(checkmarkIcon);
                e.setHorizontalTextPosition(SwingConstants.LEFT);
                e.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                e.setForeground(lessonInactiveColor);
                e.setBorder(new EmptyBorder(0, 0, lessonGap, 0));
                e.setFont(lessonsFont);
                e.setFocusable(false);
                final int index = i;
                e.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        try {
                            CourseManager.getInstance().openLesson(eduEditor.getEditor().getProject(), myLessons.get(index));
                        } catch (BadCourseException e1) {
                            e1.printStackTrace();
                        } catch (BadLessonException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (FontFormatException e1) {
                            e1.printStackTrace();
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        } catch (ExecutionException e1) {
                            e1.printStackTrace();
                        } catch (LessonIsOpenedException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                lessonsLabels.add(e);
                coursePanel.add(e);
            }
        }
        coursePanel.setMaximumSize(new Dimension(width - insets * 2, coursePanel.getPreferredSize().height));
    }

    public void hideButtons(){
        if (lessonNextButton.isVisible()) lessonNextButton.setVisible(false);
        this.repaint();
    }

    public void clearLessonPanel() {
//        while (messages.size() > 0){
//            lessonMessageContainer.remove(messages.get(0).getPanel());
//            messages.remove(0);
//        }
//        lessonMessageContainer.removeAll();
        lessonNameLabel.setIcon(null);
        lessonMessagePane.clear();
        //remove links from lessonMessagePane
        final MouseListener[] mouseListeners = lessonMessagePane.getMouseListeners();
        for (int i = 0; i < mouseListeners.length; i++) {
            lessonMessagePane.removeMouseListener(mouseListeners[i]);
        }
//        messages.clear();
        this.revalidate();
        this.repaint();
    }


    public void updateLessonPanel(Lesson lesson){
        setAllLessons(lesson);
    }

    public void setNextButtonAction(final Runnable runnable) {

        Action buttonAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                runnable.run();
            }
        };

        int mnemonic_int = 'N';
        buttonAction.putValue(Action.MNEMONIC_KEY, mnemonic_int);
        buttonAction.putValue(Action.NAME, "Next");
        buttonAction.setEnabled(true);
        lessonNextButton.setAction(buttonAction);
    }

    public void hideNextButton() {
        lessonNextButton.setVisible(false);
    }

    public static void main(String[] args) {
        EduPanel eduPanel = new EduPanel(null, 350);
        JFrame frame = new JFrame("Test EduPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(eduPanel);
        frame.setSize(350, 800);
        frame.setVisible(true);

        eduPanel.addMessage("Hey!");
    }


}
