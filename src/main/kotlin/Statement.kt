import org.apache.commons.csv.*
import java.awt.*
import java.io.*
import javax.swing.*


class Statement(title: String) : JFrame() {

    init {
        createUI(title)
    }

    private fun createUI(title: String) {

        setTitle(title)

        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(500, 200)
        setLocationRelativeTo(null)

        rootPane.contentPane = JPanel(BorderLayout(10, 10)).apply {
            add(dialog(), BorderLayout.CENTER)
            border = BorderFactory.createEmptyBorder(5, 5, 5, 5)
        }
    }

    private val mainInformation: ArrayList<String> = mutableListOf<String>() as ArrayList<String>
    private val listStudents: ArrayList<String> = mutableListOf<String>() as ArrayList<String>
    private var nameColumn = mutableListOf(
        "Контрольная работа 1", "Контрольная работа 2", "Контрольная работа 3",
        "ИДЗ 1", "ИДЗ 2", "ИДЗ 3", "Дифф. зачёт"
    )
    private var gradeList = mutableListOf("1", "2", "3", "4", "5")
    private val numCol = 7
    private lateinit var gradeStudent: Array<Array<String>>
    private fun dialog(): Component {
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
            val numOfStudents = tf4.text.toInt()
            if (numOfStudents == 0)
            {
                val a = JFrame("")
                a.setLocationRelativeTo(null)
                val l = JLabel("Введите хотя бы 1 студента")
                l.horizontalAlignment = JLabel.CENTER;
                a.add(l)
                a.setSize(250, 100)
                a.isVisible = true

            }
            else {
                gradeStudent = Array(numOfStudents) { Array(numCol) { "0" } }
                val startNum = 1
                mainInformation.add(tf.text)
                mainInformation.add(tf2.text)
                mainInformation.add(tf3.text)
                mainInformation.add(tf4.text)
                dispose()
                enter(numOfStudents, startNum)
            }
        }

        val button2 = JButton("Импорт из файла")
        button2.preferredSize = Dimension(1000, 30)
        button2.addActionListener()
        {
            readFromFile()
        }

        panel.add(name)
        panel.add(tf)
        panel.add(subject)
        panel.add(tf2)
        panel.add(group)
        panel.add(tf3)
        panel.add(numberStudents)
        panel.add(tf4)
        panel.add(button2)
        panel.add(button)
        return panel
    }

    private fun readFromFile() {
        val csvReader = BufferedReader(FileReader("test.csv"))
        val csvParser = CSVParser(csvReader, CSVFormat.DEFAULT)
        for (csvRecord in csvParser) {
            val row = csvReader.readLine()
            println(row)
        }
        csvReader.close()
    }

    private fun enter(numOfStudents: Int, startNum: Int) {
        val frame = JFrame("Ввод студентов")
        frame.setSize(500, 200)
        frame.setLocationRelativeTo(null)
        frame.defaultCloseOperation = EXIT_ON_CLOSE;
        frame.isVisible = true

        val mainPanel = JPanel(GridLayout(0, 2, 5, 5))
        val font = Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 15)

        val name = JLabel("Введите ФИО $startNum студента")
        val tf = JTextField(15)
        name.font = font

        val button = JButton("Ввести следующего студента")
        button.preferredSize = Dimension(100, 25)
        var temp = startNum
        if (temp == 1) listStudents.add("Список студентов")
        temp += 1
        button.addActionListener()
        {
            listStudents.add(tf.text)
            frame.isVisible = false
            if (temp > numOfStudents) {
                test(numOfStudents)
            } else enter(numOfStudents, temp)
        }

        mainPanel.add(name)
        mainPanel.add(tf, BorderLayout.CENTER)

        frame.contentPane.add(mainPanel)
        frame.contentPane.add(button, BorderLayout.SOUTH)
    }

    private fun test(numOfStudents: Int) {
        val font = Font(Font.MONOSPACED, Font.TYPE1_FONT, 12)
        val frame = JFrame("Ведомость")
        frame.setSize(1200, 500)
        frame.setLocationRelativeTo(null)
        frame.defaultCloseOperation = EXIT_ON_CLOSE;

        val labelSize = Dimension(200, 80)
        val solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1)

        val mb = JMenuBar()
        val m1 = JMenu("FILE")
        mb.add(m1)
        val m11 = JMenuItem("Сохранить в формате csv")
        m11.addActionListener()
        {
            writeToCSV(numOfStudents)
        }

        m1.add(m11)

        val mainPanel = JPanel()
        mainPanel.layout = BorderLayout()

        val alignmentPanel = JPanel(FlowLayout())
        alignmentPanel.border = BorderFactory.createTitledBorder("Главная информация")

        val teacherName = mainInformation[0]
        val centerLabel = JLabel(
            "<html> <p align=\"center\">Преподаватель: <br>" +
                    "$teacherName</p> </html>"
        )
        centerLabel.verticalAlignment = JLabel.CENTER
        centerLabel.horizontalAlignment = JLabel.CENTER
        centerLabel.preferredSize = labelSize
        centerLabel.border = solidBorder
        centerLabel.font = font
        alignmentPanel.add(centerLabel)

        val objectName = mainInformation[1]
        val centerLabel2 = JLabel(
            "<html> <p align=\"center\"> Название предмета: <br>" +
                    "$objectName</p> </html>"
        )
        centerLabel2.verticalAlignment = JLabel.CENTER
        centerLabel2.horizontalAlignment = JLabel.CENTER
        centerLabel2.preferredSize = labelSize
        centerLabel2.border = solidBorder
        centerLabel2.font = font
        alignmentPanel.add(centerLabel2)

        val groupNumber = mainInformation[2]
        val centerLabel3 = JLabel(
            "<html> <p align=\"center\"> Номер группы: <br>" +
                    "$groupNumber</p> </html>"
        )
        centerLabel3.verticalAlignment = JLabel.CENTER
        centerLabel3.horizontalAlignment = JLabel.CENTER
        centerLabel3.preferredSize = labelSize
        centerLabel3.border = solidBorder
        centerLabel3.font = font
        alignmentPanel.add(centerLabel3)

        mainPanel.add(alignmentPanel, BorderLayout.NORTH)

        val tab = JPanel(GridLayout((numOfStudents + 1), numCol, 1, 1))
        val buttons = mutableListOf<MutableList<JButton>>()
        for (i in 0 until (numOfStudents + 1)) {
            val buttonsRow = mutableListOf<JButton>()
            val column = mutableListOf<JLabel>()
            for (j in 0 until numCol) {
                if (j == 0) {
                    val temp = listStudents[i]
                    val cell = JLabel("<html> <p align=\"center\"> $temp </p> </html>")
                    cell.border = solidBorder
                    cell.verticalAlignment = JLabel.CENTER
                    cell.horizontalAlignment = JLabel.CENTER
                    column.add(cell)
                    tab.add(cell)
                }
                if (i == 0) {
                    val temp2 = nameColumn[j]
                    val cell1 = JLabel("<html> <p align=\"center\"> $temp2 </p> </html>")
                    cell1.border = solidBorder
                    cell1.verticalAlignment = JLabel.CENTER
                    cell1.horizontalAlignment = JLabel.CENTER
                    column.add(cell1)
                    tab.add(cell1)
                    continue
                }
                val cellButton = JButton("")
                //выставление оценки
                cellButton.addActionListener()
                {
                    val d = JDialog(frame, "Оценка")
                    val tempPanel = JPanel()
                    tempPanel.layout = GridLayout(5, 1, 1, 1)
                    d.add(tempPanel, BorderLayout.CENTER)
                    d.setSize(100, 250)
                    d.setLocationRelativeTo(null)
                    for (w in 0 until 5) {
                        val grade = JButton(gradeList[w])
                        tempPanel.add(grade)
                        grade.addActionListener()
                        {
                            if (gradeList[w] == "1") {
                                val a = JDialog(frame, "poop")
                                a.setLocationRelativeTo(null)
                                val l = JLabel("Неужели настолько всё плохо?")
                                l.horizontalAlignment = JLabel.CENTER;
                                a.add(l)
                                a.setSize(250, 100)
                                a.isVisible = true
                            }
                            cellButton.text = gradeList[w]
                            gradeStudent[i - 1][j] = gradeList[w]
                            d.isVisible = false
                        }
                    }
                    d.isVisible = true
                }
                buttonsRow.add(cellButton)
                tab.add(cellButton)
            }
            buttons.add(buttonsRow)
        }


        mainPanel.add(tab, BorderLayout.CENTER)

        frame.contentPane.add(BorderLayout.SOUTH, mb)
        frame.contentPane.add(BorderLayout.CENTER, JScrollPane(mainPanel))
        frame.isVisible = true
    }

    private fun writeToCSV(a: Int) {
        val writer = BufferedWriter(FileWriter("test.csv", false));
        val csvPrinter = CSVPrinter(
            writer, CSVFormat.DEFAULT
                .withHeader(
                    "Список студентов", "Контрольная работа 1", "Контрольная работа 2", "Контрольная работа 3",
                    "ИДЗ 1", "ИДЗ 2", "ИДЗ 3", "Дифф. зачёт"
                )
        );
        for (i in 0 until a) {
            val studentInformation: ArrayList<String> = mutableListOf<String>() as ArrayList<String>
            if (i == 0) studentInformation.add(listStudents[1])
            else studentInformation.add(listStudents[i + 1])
            for (j in 0 until numCol) {
                studentInformation.add(gradeStudent[i][j])
            }
            csvPrinter.printRecord(studentInformation);
        }
        val infoList: ArrayList<String> = mutableListOf<String>() as ArrayList<String>
        for (i in 0 until mainInformation.size) infoList.add(mainInformation[i])
        csvPrinter.printRecord(infoList)

        csvPrinter.flush();
        csvPrinter.close();
    }
}