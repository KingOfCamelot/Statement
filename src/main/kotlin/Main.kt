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
        val students : ArrayList<String> = mutableListOf<String>() as ArrayList<String>
        val font = Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 15)
        val panel = JPanel(GridLayout(0, 2, 1, 1))
        panel.isOpaque = false;

        val name = JLabel("ФИО преподавателя")
        val tf = JTextField(15)
        name.font = font

        val subject = JLabel("Название предмета")
        val tf2 = JTextField(15)
        subject.font = font

        val group = JLabel("Номер группы")
        val tf3 = JTextField(15)
        group.font = font

        val numberStudents = JLabel("Количество студентов")
        val tf4 = JTextField(15)
        numberStudents.font = font

        val button = JButton("Отправить")
        button.preferredSize = Dimension(1000, 30)
        button.addActionListener()
        {
            val num = tf4.text.toInt()
            val qw: Int = 1
            text1.add(tf.text)
            text1.add(tf2.text)
            text1.add(tf3.text)
            text1.add(tf4.text)
            dispose()
            enter(text1, students, num, qw)
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

    private fun enter(w: ArrayList<String>, a: ArrayList<String>, num: Int, qw: Int)
    {
        val frame = JFrame("Ввод студентов")
        frame.setSize(500,200)
        frame.setLocationRelativeTo(null)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE;
        frame.isVisible = true

        val mainPanel = JPanel(GridLayout(0, 2, 5, 5))
        val font = Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 15)
        //mainPanel.layout = BorderLayout()
        //mainPanel.isOpaque = false;

        val name = JLabel("Введите ФИО $qw студента")
        val tf = JTextField(15)
        name.font = font

        val button = JButton("Ввести следующего студента")
        button.preferredSize = Dimension(100, 25)
        var z = qw
        z += 1
        button.addActionListener()
        {
            a.add(tf.text)
            frame.isVisible = false
            if (z > num) {
                test(w, a)
            }
            else enter(w, a, num, z)
        }

        mainPanel.add(name)
        mainPanel.add(tf, BorderLayout.CENTER)

        frame.contentPane.add(mainPanel)
        frame.contentPane.add(button, BorderLayout.SOUTH)
    }

    private fun test(w: ArrayList<String>, student: ArrayList<String>)
    {
        val font = Font(Font.MONOSPACED, Font.TYPE1_FONT, 12)
        val frame = JFrame("Ведомость")
        frame.setSize(1000,500)
        frame.setLocationRelativeTo(null)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE;

        val labelSize = Dimension(200,80)
        val solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1)

        val mb = JMenuBar()
        val m1 = JMenu("FILE")
        mb.add(m1)
        val m11 = JMenuItem("Сохрнаить в формате json")
        val m12 = JMenuItem("Сохранить в формате excel")
        m1.add(m11)
        m1.add(m12)

        val mainPanel = JPanel()
        mainPanel.layout = BorderLayout()

        val alignmentPanel = JPanel(FlowLayout())
        alignmentPanel.border = BorderFactory.createTitledBorder("Главная информация")

        val prepod = w[0]
        val centerLabel = JLabel("<html> <p align=\"center\">Преподаватель: <br>" +
                "$prepod</p> </html>")
        centerLabel.verticalAlignment = JLabel.CENTER
        centerLabel.horizontalAlignment = JLabel.CENTER
        centerLabel.preferredSize = labelSize
        centerLabel.border = solidBorder
        centerLabel.font = font
        alignmentPanel.add(centerLabel)

        val name = w[1]
        val centerLabel2 = JLabel("<html> Название предмета: <br>" +
                "<p align=\"center\">$name</p> </html>")
        centerLabel2.verticalAlignment = JLabel.CENTER
        centerLabel2.horizontalAlignment = JLabel.CENTER
        centerLabel2.preferredSize = labelSize
        centerLabel2.border = solidBorder
        centerLabel2.font = font
        alignmentPanel.add(centerLabel2)

        val num = w[2]
        val centerLabel3 = JLabel("<html> Номер группы: <br>" +
                "<p align=\"center\">$num</p> </html>")
        centerLabel3.verticalAlignment = JLabel.CENTER
        centerLabel3.horizontalAlignment = JLabel.CENTER
        centerLabel3.preferredSize = labelSize
        centerLabel3.border = solidBorder
        centerLabel3.font = font
        alignmentPanel.add(centerLabel3)

        mainPanel.add(alignmentPanel, BorderLayout.NORTH)

        frame.contentPane.add(BorderLayout.SOUTH, mb)
        frame.contentPane.add(BorderLayout.NORTH, mainPanel)
        frame.isVisible = true
    }
}

private fun createAndShowGUI() {
    val frame = KotlinSwingSimpleEx("Информация")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE;
    frame.isVisible = true
}

fun main() {
    EventQueue.invokeLater(::createAndShowGUI)
}
/*val d = JDialog(frame, "poop")
val l = JLabel()
l.horizontalAlignment = JLabel.CENTER;
l.font = font
d.add(l)
d.setSize(1000, 1000)
d.isVisible = true*/