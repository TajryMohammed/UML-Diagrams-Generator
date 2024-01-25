package org.mql.java;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.mql.java.controller.XmlExporter;
import org.mql.java.controller.processing.ProjectParser;
import org.mql.java.models.ModProject;
import org.mql.java.ui.classdiagram.ClassDiagram;
import org.mql.java.ui.packagediagram.PackageDiagram;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainFrame() {
		initializeUI();
	}

	private void initializeUI() {
		setTitle("UML Diagram Viewer");
		setSize(508, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		add(createHeaderPanel(), BorderLayout.NORTH);
		add(createMainPanel(), BorderLayout.CENTER);

		setVisible(true);
	}

	private JPanel createHeaderPanel() {
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new BorderLayout());

		JLabel logoLabel = new JLabel();
		ImageIcon logoIcon = new ImageIcon("resources/images/MQLuml.png");
		logoIcon = new ImageIcon(logoIcon.getImage().getScaledInstance(492, 369, Image.SCALE_DEFAULT));
		logoLabel.setIcon(logoIcon);

		headerPanel.add(logoLabel, BorderLayout.CENTER);

		return headerPanel;
	}

	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel buttonsPanel = new JPanel();

		JButton selectProjectButton = new JButton("Select Project");
		selectProjectButton.addActionListener(e -> selectProject());

		buttonsPanel.add(selectProjectButton);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

		return mainPanel;
	}

	private void selectProject() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int result = fileChooser.showOpenDialog(this);

		if (result == JFileChooser.APPROVE_OPTION) {
			String selectedPath = fileChooser.getSelectedFile().getAbsolutePath();
			processProject(selectedPath);
		}
	}

	private void processProject(String path) {
		ProjectParser project = new ProjectParser(path);
		ModProject modProject = project.getParsedProject();
		XmlExporter.exportToXml(modProject, "resources/project_structure.xml");

		JButton classDiagramButton = new JButton("Diagramme de Classe");
		JButton packageDiagramButton = new JButton("Diagramme de Package");

		classDiagramButton.addActionListener(e -> showClassDiagram());
		packageDiagramButton.addActionListener(e -> showPackageDiagram());

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(classDiagramButton);
		buttonsPanel.add(packageDiagramButton);

		add(buttonsPanel, BorderLayout.SOUTH);
		revalidate();
		repaint();
	}

	private void showClassDiagram() {
		new ClassDiagram().setVisible(true);
	}

	private void showPackageDiagram() {
		new PackageDiagram().setVisible(true);
	}

	public static void main(String[] args) {
		new MainFrame();
	}
}
