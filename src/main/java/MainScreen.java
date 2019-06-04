import models.Cabinet;
import models.Doctor;
import models.Location;
import repositories.CabinetRepository;
import repositories.DoctorRepository;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class SpringUtilities {
    /**
     * A debugging utility that prints to stdout the component's minimum,
     * preferred, and maximum sizes.
     */
    public static void printSizes(Component c) {
        System.out.println("minimumSize = " + c.getMinimumSize());
        System.out.println("preferredSize = " + c.getPreferredSize());
        System.out.println("maximumSize = " + c.getMaximumSize());
    }

    /**
     * Aligns the first <code>rows</code>*<code>cols</code> components of
     * <code>parent</code> in a grid. Each component is as big as the maximum
     * preferred width and height of the components. The parent is made just big
     * enough to fit them all.
     *
     * @param rows
     *            number of rows
     * @param cols
     *            number of columns
     * @param initialX
     *            x location to start the grid at
     * @param initialY
     *            y location to start the grid at
     * @param xPad
     *            x padding between cells
     * @param yPad
     *            y padding between cells
     */
    public static void makeGrid(Container parent, int rows, int cols,
                                int initialX, int initialY, int xPad, int yPad) {
        SpringLayout layout;
        try {
            layout = (SpringLayout) parent.getLayout();
        } catch (ClassCastException exc) {
            System.err
                    .println("The first argument to makeGrid must use SpringLayout.");
            return;
        }

        Spring xPadSpring = Spring.constant(xPad);
        Spring yPadSpring = Spring.constant(yPad);
        Spring initialXSpring = Spring.constant(initialX);
        Spring initialYSpring = Spring.constant(initialY);
        int max = rows * cols;

        //Calculate Springs that are the max of the width/height so that all
        //cells have the same size.
        Spring maxWidthSpring = layout.getConstraints(parent.getComponent(0))
                .getWidth();
        Spring maxHeightSpring = layout.getConstraints(parent.getComponent(0))
                .getWidth();
        for (int i = 1; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(parent
                    .getComponent(i));

            maxWidthSpring = Spring.max(maxWidthSpring, cons.getWidth());
            maxHeightSpring = Spring.max(maxHeightSpring, cons.getHeight());
        }

        //Apply the new width/height Spring. This forces all the
        //components to have the same size.
        for (int i = 0; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(parent
                    .getComponent(i));

            cons.setWidth(maxWidthSpring);
            cons.setHeight(maxHeightSpring);
        }

        //Then adjust the x/y constraints of all the cells so that they
        //are aligned in a grid.
        SpringLayout.Constraints lastCons = null;
        SpringLayout.Constraints lastRowCons = null;
        for (int i = 0; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(parent
                    .getComponent(i));
            if (i % cols == 0) { //start of new row
                lastRowCons = lastCons;
                cons.setX(initialXSpring);
            } else { //x position depends on previous component
                cons.setX(Spring.sum(lastCons.getConstraint(SpringLayout.EAST),
                        xPadSpring));
            }

            if (i / cols == 0) { //first row
                cons.setY(initialYSpring);
            } else { //y position depends on previous row
                cons.setY(Spring.sum(lastRowCons
                        .getConstraint(SpringLayout.SOUTH), yPadSpring));
            }
            lastCons = cons;
        }

        //Set the parent's size.
        SpringLayout.Constraints pCons = layout.getConstraints(parent);
        pCons.setConstraint(SpringLayout.SOUTH, Spring.sum(Spring
                .constant(yPad), lastCons.getConstraint(SpringLayout.SOUTH)));
        pCons.setConstraint(SpringLayout.EAST, Spring.sum(
                Spring.constant(xPad), lastCons
                        .getConstraint(SpringLayout.EAST)));
    }

    /* Used by makeCompactGrid. */
    private static SpringLayout.Constraints getConstraintsForCell(int row,
                                                                  int col, Container parent, int cols) {
        SpringLayout layout = (SpringLayout) parent.getLayout();
        Component c = parent.getComponent(row * cols + col);
        return layout.getConstraints(c);
    }

    /**
     * Aligns the first <code>rows</code>*<code>cols</code> components of
     * <code>parent</code> in a grid. Each component in a column is as wide as
     * the maximum preferred width of the components in that column; height is
     * similarly determined for each row. The parent is made just big enough to
     * fit them all.
     *
     * @param rows
     *            number of rows
     * @param cols
     *            number of columns
     * @param initialX
     *            x location to start the grid at
     * @param initialY
     *            y location to start the grid at
     * @param xPad
     *            x padding between cells
     * @param yPad
     *            y padding between cells
     */
    public static void makeCompactGrid(Container parent, int rows, int cols,
                                       int initialX, int initialY, int xPad, int yPad) {
        SpringLayout layout;
        try {
            layout = (SpringLayout) parent.getLayout();
        } catch (ClassCastException exc) {
            System.err
                    .println("The first argument to makeCompactGrid must use SpringLayout.");
            return;
        }

        //Align all cells in each column and make them the same width.
        Spring x = Spring.constant(initialX);
        for (int c = 0; c < cols; c++) {
            Spring width = Spring.constant(0);
            for (int r = 0; r < rows; r++) {
                width = Spring.max(width, getConstraintsForCell(r, c, parent,
                        cols).getWidth());
            }
            for (int r = 0; r < rows; r++) {
                SpringLayout.Constraints constraints = getConstraintsForCell(r,
                        c, parent, cols);
                constraints.setX(x);
                constraints.setWidth(width);
            }
            x = Spring.sum(x, Spring.sum(width, Spring.constant(xPad)));
        }

        //Align all cells in each row and make them the same height.
        Spring y = Spring.constant(initialY);
        for (int r = 0; r < rows; r++) {
            Spring height = Spring.constant(0);
            for (int c = 0; c < cols; c++) {
                height = Spring.max(height, getConstraintsForCell(r, c, parent,
                        cols).getHeight());
            }
            for (int c = 0; c < cols; c++) {
                SpringLayout.Constraints constraints = getConstraintsForCell(r,
                        c, parent, cols);
                constraints.setY(y);
                constraints.setHeight(height);
            }
            y = Spring.sum(y, Spring.sum(height, Spring.constant(yPad)));
        }

        //Set the parent's size.
        SpringLayout.Constraints pCons = layout.getConstraints(parent);
        pCons.setConstraint(SpringLayout.SOUTH, y);
        pCons.setConstraint(SpringLayout.EAST, x);
    }
}

public class MainScreen extends JFrame{
    private JPanel Main;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MedicalManager");
        frame.setContentPane(new MainScreen().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setTitle("MedicalManager");
        frame.setSize(850, 550);

        JTabbedPane tabbedPane = new JTabbedPane();

        ImageIcon icon = createImageIcon("images/midsdle.gif");

        JComponent panel1 = makeInfoPanel();
        tabbedPane.addTab("Cabinet Info", icon, panel1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent panel2 = makeDoctorsPanel();
        tabbedPane.addTab("Doctors", icon, panel2);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        JComponent panel3 = makeAddDoctorPanel();
        tabbedPane.addTab("Add Doctors", icon, panel3);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

//        JComponent panel4 = makeTextPanel();
//        tabbedPane.addTab("Invoices", icon, panel4,
//                "Still does nothing");
//        tabbedPane.setMnemonicAt(2, KeyEvent.VK_4);

        frame.setContentPane(tabbedPane);
    }

    static protected JComponent makeInfoPanel() {
        // Components
        JPanel panel = new JPanel();
        JPanel infoPanel = new JPanel();
        JPanel logoPanel = new JPanel();
        JButton saveButton = new JButton("Save changes");

        // Layouts
        infoPanel.setLayout(new SpringLayout());
        logoPanel.setLayout(new GridLayout(2,1, 20, 10));

        List<JTextField> textFieldList = new ArrayList<JTextField>();

        // Form Setup
        String[] labels = {"Name: ", "Address: ", "Phone: ", "CUI: ", "IBAN: "};
        int numPairs = labels.length;
        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            l.setFont(l.getFont().deriveFont(16.0f));
            infoPanel.add(l);
            JTextField textField = new JTextField(10);
            l.setLabelFor(textField);
            textField.setFont(l.getFont().deriveFont(16.0f));
            textFieldList.add(textField);
            infoPanel.add(textField);
        }
        SpringUtilities.makeCompactGrid(infoPanel,
                numPairs, 2, //rows, cols
                6, 6,        //initX, initY
                6, 60);       //xPad, yPad

        saveButton.addActionListener(e -> {
            CabinetRepository cabinetRepository = new CabinetRepository();
            String name = textFieldList.get(0).getText();
            String location = textFieldList.get(1).getText();
            String phone = textFieldList.get(2).getText();
            String cui = textFieldList.get(3).getText();
            String iban = textFieldList.get(4).getText();
            Location locObj = new Location("Colentina", location, "Bucharest", 4);
            cabinetRepository.saveCabinetInfo(new Cabinet(name, locObj, phone, cui, iban));
        });

        // Add components to final layout
        logoPanel.add(new JLabel(createImageIcon("images/middle.gif")));
        logoPanel.add(saveButton);
        panel.setLayout(new GridLayout(1,2, 20, 20));
        panel.add(infoPanel);
        panel.add(logoPanel);

        return panel;
    }

    static protected JComponent makeDoctorsPanel() {
        JList<Doctor> list = new JList<>();
        DefaultListModel<Doctor> model = new DefaultListModel<>();

        DoctorRepository doctorRepository = new DoctorRepository();

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Doctor Info", SwingConstants.CENTER);
        JSplitPane splitPane = new JSplitPane();

        panel.setLayout(new GridLayout(1,1));
        panel.add(label);
        list.setModel(model);

        List<Doctor> doctorList = doctorRepository.getDoctors();

        for (Doctor d : doctorList) {
            model.addElement(d);
        }

        list.getSelectionModel().addListSelectionListener(e -> {
            Doctor d = list.getSelectedValue();
            label.setText("<html>FirstName: " + d.getFirstName() +
                    "<br/> LastName: " + d.getLastName() +
                    "<br/> Age: " + d.getAge() +
                    "<br/> Sex: " + d.getSex() +
                    "<br/> Type: " + d.getType() +
                    "<br/> Email: " + d.getEmail() +
                    "<br/> Phone: " + d.getPhoneNumber() +
                    "</html>");
        });

        splitPane.setLeftComponent(new JScrollPane(list));
        splitPane.setRightComponent(panel);

        panel.setLayout(new GridLayout(1,1));
        return splitPane;
    }

    static protected JComponent makeAddDoctorPanel() {
        // Components
        JPanel panel = new JPanel();
        JPanel infoPanel = new JPanel();
        JPanel logoPanel = new JPanel();
        JButton saveButton = new JButton("Add Doctor");

        // Layouts
        infoPanel.setLayout(new SpringLayout());
        logoPanel.setLayout(new GridLayout(2,1, 20, 10));

        List<JTextField> textFieldList = new ArrayList<JTextField>();

        // Form Setup
        String[] labels = {"FirstName: ", "LastName: ", "Age: ", "Sex: ", "Type: ", "Email: ", "Pass: ", "Phone: "};
        int numPairs = labels.length;
        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            l.setFont(l.getFont().deriveFont(16.0f));
            infoPanel.add(l);
            JTextField textField = new JTextField(10);
            l.setLabelFor(textField);
            textField.setFont(l.getFont().deriveFont(16.0f));
            textFieldList.add(textField);
            infoPanel.add(textField);
        }
        SpringUtilities.makeCompactGrid(infoPanel,
                numPairs, 2, //rows, cols
                6, 6,        //initX, initY
                6, 60);       //xPad, yPad

        saveButton.addActionListener(e -> {
            DoctorRepository doctorRepository = new DoctorRepository();
            String firstName = textFieldList.get(0).getText();
            String lastName = textFieldList.get(1).getText();
            int age = Integer.parseInt(textFieldList.get(2).getText());
            String sex = textFieldList.get(3).getText();
            String type = textFieldList.get(4).getText();
            String email = textFieldList.get(5).getText();
            String pass = textFieldList.get(6).getText();
            String phone = textFieldList.get(7).getText();
            doctorRepository.saveDoctor(new Doctor(firstName, lastName, age, sex, type, email, pass, phone));
        });

        // Add components to final layout
        logoPanel.add(new JLabel(createImageIcon("images/middle.gif")));
        logoPanel.add(saveButton);
        panel.setLayout(new GridLayout(1,2, 20, 20));
        panel.add(infoPanel);
        panel.add(logoPanel);

        return panel;
    }


    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = MainScreen.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

}
