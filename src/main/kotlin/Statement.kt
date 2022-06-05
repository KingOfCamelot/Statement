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
            add(enterMainInformation(), BorderLayout.CENTER)
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

    private fun smallMessageBox(text: String) {
        val a = JFrame("Сообщение")
        a.setLocationRelativeTo(null)
        val l = JLabel(text)
        l.horizontalAlignment = JLabel.CENTER;
        a.add(l)
        a.setSize(350, 100)
        a.isVisible = true
    }

    private fun enterMainInformation(): Component {
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
            val limit = 1000000
            if (numOfStudents > limit)
            {
                smallMessageBox("У вас точно больше МИЛЛИОНА человек в классе?")
            }
            else {
                if (numOfStudents <= 0) smallMessageBox("Введите хотя бы 1 студента.")
                else {
                    gradeStudent = Array(numOfStudents) { Array(numCol) { "0" } }
                    val startNum = 1
                    mainInformation.add(tf.text)
                    mainInformation.add(tf2.text)
                    mainInformation.add(tf3.text)
                    mainInformation.add(tf4.text)
                    dispose()
                    enterMainInformation(numOfStudents, startNum)
                }
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
        val reader = BufferedReader(FileReader("test.csv"));
        val csvParser = CSVParser(
            reader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim()
        )
        for (record in csvParser) {
            for (field in record) {
                print("$field ")
            }
            println()
        }
    }

    private fun enterMainInformation(numOfStudents: Int, startNum: Int) {
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
                grading(numOfStudents)
            } else enterMainInformation(numOfStudents, temp)
        }

        mainPanel.add(name)
        mainPanel.add(tf, BorderLayout.CENTER)

        frame.contentPane.add(mainPanel)
        frame.contentPane.add(button, BorderLayout.SOUTH)
    }

    private fun grading(numOfStudents: Int) {
        val font = Font(Font.MONOSPACED, Font.TYPE1_FONT, 12)
        val frame = JFrame("Ведомость")
        frame.setSize(1200, 500)
        frame.setLocationRelativeTo(null)
        frame.defaultCloseOperation = EXIT_ON_CLOSE;

        val labelSize = Dimension(200, 80)
        val solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1)

        val menuBar = JMenuBar()
        val function = JMenu("FILE")
        menuBar.add(function)
        val menuBarFunction1 = JMenuItem("Сохранить в формате csv")
        menuBarFunction1.addActionListener()
        {
            writeToCSV(numOfStudents)
        }
        function.add(menuBarFunction1)

        val mainPanel = JPanel()
        mainPanel.layout = BorderLayout()

        val panelForMainInformation = JPanel(FlowLayout())
        panelForMainInformation.border = BorderFactory.createTitledBorder("Главная информация")

        val teacherName = mainInformation[0]
        val nameLabel = JLabel(
            "<html> <p align=\"center\"><font face='consoles'>Преподаватель: <br>" +
                    "$teacherName</font></p> </html>"
        )
        nameLabel.verticalAlignment = JLabel.CENTER
        nameLabel.horizontalAlignment = JLabel.CENTER
        nameLabel.preferredSize = labelSize
        nameLabel.border = solidBorder
        nameLabel.font = font
        panelForMainInformation.add(nameLabel)

        val objectName = mainInformation[1]
        val objectNameLabel = JLabel(
            "<html> <p align=\"center\"><font face='calibre'> Название предмета: <br>" +
                    "$objectName</font></p> </html>"
        )
        objectNameLabel.verticalAlignment = JLabel.CENTER
        objectNameLabel.horizontalAlignment = JLabel.CENTER
        objectNameLabel.preferredSize = labelSize
        objectNameLabel.border = solidBorder
        objectNameLabel.font = font
        panelForMainInformation.add(objectNameLabel)

        val groupNumber = mainInformation[2]
        val groupNumLabel = JLabel(
            "<html> <p align=\"center\"><font face='calibre'> Номер группы: <br>" +
                    "$groupNumber</font></p> </html>"
        )
        groupNumLabel.verticalAlignment = JLabel.CENTER
        groupNumLabel.horizontalAlignment = JLabel.CENTER
        groupNumLabel.preferredSize = labelSize
        groupNumLabel.border = solidBorder
        groupNumLabel.font = font
        panelForMainInformation.add(groupNumLabel)

        mainPanel.add(panelForMainInformation, BorderLayout.NORTH)

        val tab = JPanel(GridLayout((numOfStudents + 1), numCol, 1, 1))
        val buttons = mutableListOf<MutableList<JButton>>()
        for (i in 0 until (numOfStudents + 1)) {
            val buttonsRow = mutableListOf<JButton>()
            for (j in 0 until numCol) {
                if (j == 0) {
                    val tempListStudents = listStudents[i]
                    val cell = JLabel("<html> <p align=\"center\"> $tempListStudents </p> </html>")
                    cell.border = solidBorder
                    cell.verticalAlignment = JLabel.CENTER
                    cell.horizontalAlignment = JLabel.CENTER
                    tab.add(cell)
                }
                if (i == 0) {
                    val tempNameColumn = nameColumn[j]
                    val cell = JLabel("<html> <p align=\"center\"> $tempNameColumn </p> </html>")
                    cell.border = solidBorder
                    cell.verticalAlignment = JLabel.CENTER
                    cell.horizontalAlignment = JLabel.CENTER
                    tab.add(cell)
                    continue
                }
                val cellButton = JButton("")
                //выставление оценки
                cellButton.addActionListener()
                {
                    val box = JDialog(frame, "Оценка")
                    val tempPanel = JPanel()
                    tempPanel.layout = GridLayout(5, 1, 1, 1)
                    box.add(tempPanel, BorderLayout.CENTER)
                    box.setSize(100, 250)
                    box.setLocationRelativeTo(null)
                    for (w in 0 until gradeList.size) {
                        val grade = JButton(gradeList[w])
                        tempPanel.add(grade)
                        grade.addActionListener()
                        {
                            if (gradeList[w] == "1") smallMessageBox("Неужели настолько всё плохо?")
                            cellButton.text = gradeList[w]
                            gradeStudent[i - 1][j] = gradeList[w]
                            box.isVisible = false
                        }
                    }
                    box.isVisible = true
                }
                buttonsRow.add(cellButton)
                tab.add(cellButton)
            }
            buttons.add(buttonsRow)
        }

        mainPanel.add(tab, BorderLayout.CENTER)

        frame.contentPane.add(BorderLayout.SOUTH, menuBar)
        frame.contentPane.add(BorderLayout.CENTER, JScrollPane(mainPanel))
        frame.isVisible = true
    }

    private fun writeToCSV(num: Int) {
        val writer = BufferedWriter(FileWriter("test.csv", false));
        val csvPrinter = CSVPrinter(
            writer, CSVFormat.DEFAULT
                .withHeader(
                    "Список студентов", "Контрольная работа 1", "Контрольная работа 2", "Контрольная работа 3",
                    "ИДЗ 1", "ИДЗ 2", "ИДЗ 3", "Дифф. зачёт"
                )
        );
        for (i in 0 until num) {
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