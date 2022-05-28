import java.awt.*
import javax.swing.*


class KotlinSwingSimpleEx(title: String) : JFrame() {

    init {
        createUI(title)
    }

    private fun createUI(title: String) {

        setTitle(title)

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(500, 200)
        setLocationRelativeTo(null)

        rootPane.contentPane = JPanel(BorderLayout(10, 10)).apply {
            add(dialog(), BorderLayout.CENTER)
            border = BorderFactory.createEmptyBorder(5, 5, 5, 5)
        }
    }

    private fun dialog(): Component
    {
        val text1 : ArrayList<String> = mutableListOf<String>() as ArrayList<String>
        val font = Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 15)
        val panel = JPanel(GridLayout(0, 2, 1, 1))
        panel.isOpaque = false;

        val name = JLabel("Teacher's name")
        val tf = JTextField(15)
        name.font = font

        val subject = JLabel("Subject")
        val tf2 = JTextField(15)
        subject.font = font

        val group = JLabel("Group number")
        val tf3 = JTextField(15)
        group.font = font

        val numberStudents = JLabel("Number of students")
        val tf4 = JTextField(15)
        numberStudents.font = font

        val button = JButton("Send this fucking information")
        button.preferredSize = Dimension(20, 20)
        button.addActionListener()
        {
            text1.add(tf.text)
            text1.add(tf2.text)
            text1.add(tf3.text)
            text1.add(tf4.text)
            dispose()
            test(text1)
        }

        panel.add(name)
        panel.add(tf)
        panel.add(subject)
        panel.add(tf2)
        panel.add(group)
        panel.add(tf3)
        panel.add(numberStudents)
        panel.add(tf4)
        panel.add(button, BorderLayout.SOUTH)
        return panel
    }

    private fun test(w: ArrayList<String>)
    {
        val font = Font(Font.MONOSPACED, Font.TYPE1_FONT, 15)
        val frame = JFrame()
        frame.setSize(500,500)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE;
        /*val d = JDialog(frame, "poop")
        val l = JLabel()
        l.horizontalAlignment = JLabel.CENTER;
        l.font = font
        d.add(l)
        d.setSize(1000, 1000)
        d.isVisible = true*/
        val mb = JMenuBar()
        val m1 = JMenu("FILE")
        mb.add(m1)
        val m11 = JMenuItem("Save like a bich")
        val m12 = JMenuItem("Save like exceeeel")
        m1.add(m11)
        m1.add(m12)

        val mainPanel = JPanel()
        mainPanel.layout = BorderLayout()

        val alignmentPanel = JPanel(FlowLayout())
        alignmentPanel.border = BorderFactory.createTitledBorder("Alignment")
        val topLabel = JLabel("Top")
        alignmentPanel.add(topLabel)

        mainPanel.add(alignmentPanel, BorderLayout.NORTH)

        frame.contentPane.add(BorderLayout.NORTH, mainPanel)
        frame.contentPane.add(BorderLayout.SOUTH, mb)
        frame.isVisible = true
    }
}

private fun createAndShowGUI() {
    val frame = KotlinSwingSimpleEx("statement")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE;
    frame.isVisible = true
}

fun main() {
    EventQueue.invokeLater(::createAndShowGUI)
}