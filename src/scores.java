import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class scores implements Comparable<scores>, Serializable {
    static ArrayList<scores> sc=new ArrayList<>();
    String name;
    int score;
    scores(String name,int score){
        this.name=name;
        this.score=score;
        sc.add(this);
    }

    @Override
    public int compareTo(scores o) {
        return o.score-this.score;
    }
    public static void writeScores(){
        try{
            ObjectOutputStream writeStream = new ObjectOutputStream(new FileOutputStream("scores.dat"));
            writeStream.writeObject(sc);
            writeStream.flush();
            writeStream.close();

        }catch (Exception e) {
        }
    }
    public static void readScores(){
        try{
            ObjectInputStream readStream = new ObjectInputStream(new FileInputStream("scores.dat"));

            sc = (ArrayList<scores>) readStream.readObject();
            readStream.close();
        }catch (Exception e) {
        }
    }
    public static void addScores(){
        new scores("a",42);
        new scores("a",12);
        new scores("a",15);
        new scores("a",1);
        new scores("a",100);
        new scores("a",20);
        new scores("a",5);
        new scores("a",10);
        new scores("a",42);
        new scores("a",12);
        new scores("a",15);
        new scores("a",1);
        new scores("a",100);
        new scores("a",20);
        new scores("a",5);
        new scores("a",10);
        new scores("a",42);
        new scores("a",12);
        new scores("a",15);
        new scores("a",1);
        new scores("a",100);
        new scores("a",20);
        new scores("a",5);
        new scores("a",10);
        new scores("a",42);
        new scores("a",12);
        new scores("a",15);
        new scores("a",1);
        new scores("a",100);
        new scores("a",20);
        new scores("a",5);
        new scores("a",10);
        new scores("a",42);
        new scores("a",12);
        new scores("a",15);
        new scores("a",1);
        new scores("a",100);
        new scores("a",20);
        new scores("a",5);
        new scores("a",10);
        new scores("a",42);
        new scores("a",12);
        new scores("a",15);
        new scores("a",1);
        new scores("a",100);
        new scores("a",20);
        new scores("a",5);
        new scores("a",10);
    }
    public static JScrollPane makePan(){
        Collections.sort(sc);
        ArrayList<Integer> tops=new ArrayList<>();
        String[][] data=new String[sc.size()][3];
        for(int i=0;i<sc.size();i++)
        {
            data[i][0]=sc.get(i).name;
            data[i][1]="\t";
            data[i][2]=Integer.toString(sc.get(i).score);
            if(tops.indexOf(sc.get(i).score)==-1)
                tops.add(sc.get(i).score);
        }
        Collections.sort(tops);
        Collections.reverse(tops);
        JTable board=new JTable(data, new String[]{"Name", "","Score"});
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        board.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
        board.setTableHeader(null);
        board.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
        board.setShowGrid(false);
        board.setFocusable(false);
        board.setRowSelectionAllowed(false);
        board.setRowHeight(30);
        board.setBounds(0,0,1000,400);
        board.setForeground(Color.BLACK);
        board.setBackground(new Color(0,0,0,0));
        board.setOpaque(false);
        board.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                if((tops.size()>=1)&&((String)table.getModel().getValueAt(row,2)).equals(Integer.toString(tops.get(0))))
                    setForeground(Color.YELLOW);
                else if((tops.size()>=2)&&((String)table.getModel().getValueAt(row,2)).equals(Integer.toString(tops.get(1))))
                        setForeground(Color.ORANGE);
                else if((tops.size()>=3)&&((String)table.getModel().getValueAt(row,2)).equals(Integer.toString(tops.get(2))))
                        setForeground(Color.LIGHT_GRAY);
                else
                    setForeground(Color.BLACK);
                return this;
            }
        });
        JScrollPane scroller = new JScrollPane(board);
        scroller.setOpaque(false);
        scroller.getViewport().setOpaque(false);
        scroller.setBorder(BorderFactory.createEmptyBorder());
        JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL) {

            @Override
            public boolean isVisible() {
                return true;
            }
        };
        scroller.setVerticalScrollBar(scrollBar);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        return scroller;
    }
}
