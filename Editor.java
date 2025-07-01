import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;
import javax.swing.text.*;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;


class Editor extends WindowAdapter implements ActionListener,TextListener
{	
	Frame f; MenuBar mb;Menu m1,m2; MenuItem nw,open,save,saveas,ext,find,fandr; TextArea ta;FileDialog fd;TextField tf,tf1,tf2;Highlighter h;Frame f1;Frame f3;int countr=0;
	public Editor()
	{
		f=new Frame("Editor");
		f.setSize(500,500);
		ta=new TextArea();
		ta.addTextListener(this);tf1=new TextField(10);
		tf1.addTextListener(this);tf=new TextField(10);
		tf.addTextListener(this);
		
		//WindowCloser wc=new WindowCloser();
		f.addWindowListener(this);
		mb=new MenuBar();
		m1=new Menu("File");
		m2=new Menu("Edit");
		nw=new MenuItem("New");
		open=new MenuItem("Open");
		save=new MenuItem("Save");
		saveas=new MenuItem("SaveAs");
		ext=new MenuItem("Exit");
		find=new MenuItem("Find");
		fandr=new MenuItem("Find&Replace");
		nw.addActionListener(this);
		open.addActionListener(this);
		save.addActionListener(this);
		saveas.addActionListener(this);
		ext.addActionListener(this);
		find.addActionListener(this);
		fandr.addActionListener(this);
		
		m1.add(nw);
		m1.add(open);
		m1.add(save);
		m1.add(saveas);
		m1.addSeparator();
		m1.add(ext);
		
		mb.add(m1);
		m2.add(find);
		m2.add(fandr);
		mb.add(m2);
		
		f.setMenuBar(mb);
		f.add(ta);
		f.setVisible(true);
		//h = ta.getHighlighter();
		r=Pattern.compile(" ");
		m=r.matcher(ta.getText());
	}
	boolean saveFlag=false;
	String filename;
	Pattern r;Matcher m;
	boolean change=true;
	boolean last=false;

	public void windowClosing(WindowEvent e)
	{	if(e.getSource()==f && change)
		{	cflag=true;
			f3=new Frame();
			f3.setSize(500,50);
			f3.setLayout(new GridLayout(1,3));
			Button bc1=new Button("Save");
			Button bc2=new Button("Don't Save");
			Button bc3=new Button("Cancel");
			bc1.addActionListener(this);
			bc2.addActionListener(this);
			bc3.addActionListener(this);
			
			f3.add(bc1);
			f3.add(bc2);
			f3.add(bc3);
			f3.setVisible(true);
			
		
			//actionPerformed(new ActionEvent(save,1,"Save"));
			
			
		}
			
		if(e.getSource()!=f)
		{	
		Window w=e.getWindow();
		w.setVisible(false);
		w.dispose();}
		if(e.getSource()==f && !change)
		{
		System.exit(1);}
	}
	public void textValueChanged(TextEvent te)
	{	
		
		if(te.getSource()==tf)
		{	//countr=0;
			rflag =false;
			r=Pattern.compile(tf.getText());
			m=r.matcher(ta.getText());
			System.out.println("text tf");
			
		}
		else if(te.getSource()==tf1)
		{	countr=0;
			rflag =false;
			r=Pattern.compile(tf1.getText());
			m=r.matcher(ta.getText());
			System.out.println("text tf1");
		}
		else if(te.getSource()==ta)
		{change=true;
		if(!last)
			{m=r.matcher(ta.getText());last=false;}
		System.out.println("text else");
		}
		
	}
			
		
	boolean rflag=false;	
	boolean cflag=false;
	
		
	public void actionPerformed(ActionEvent e)
	{
		String str=e.getActionCommand();
		if(str.equals("Exit"))
		{	if(change)
			{	
			cflag=true;
			f3=new Frame();
			f3.setSize(500,50);
			f3.setLayout(new GridLayout(1,3));
			Button bc1=new Button("Save");
			Button bc2=new Button("Don't Save");
			Button bc3=new Button("Cancel");
			bc1.addActionListener(this);
			bc2.addActionListener(this);
			bc3.addActionListener(this);
			
			f3.add(bc1);
			f3.add(bc2);
			f3.add(bc3);
			f3.setVisible(true);
			
		
			//actionPerformed(new ActionEvent(save,1,"Save"));
			
			
			}
		}
		else if(str.equals("Don't Save"))
		{
			System.exit(1);
			}
		else if(str.equals("Cancel"))
		{	cflag=false;
			f3.setVisible(false);
			f3.dispose();
			}
		else if(str.equals("Close"))
		{	
			f1.setVisible(false);
			f1.dispose();
		}
			
		else if(str.equals("Open"))
		{	saveFlag=true;
			//ta.append(" fileDialog");
			fd=new FileDialog(f,"select",FileDialog.LOAD);
			fd.setVisible(true);
			filename=fd.getDirectory()+fd.getFile();
			try{File file=new File(filename);
			FileInputStream fis=new FileInputStream(file);
			int ch;
			ta.setText("");
			while((ch=fis.read())!=-1)
			ta.append(""+(char)ch);
			fis.close();
			}
			catch(Exception e1)
			{
			ta.setText(" File not found");
			}
				
		}
		
		else if(str.equals("New"))
		{	saveFlag=false;
			ta.setText("");
		}
		
		else if(str.equals("Save"))
		{	
			if(saveFlag)
			{
				try{
					File fo=new File(filename);
			FileOutputStream fos=new FileOutputStream(fo);
			DataOutputStream dos=new DataOutputStream(fos);
			//dos.writeUTF("");
			str=ta.getText();
			//System.out.print(str);
			for(int i=0;i<str.length();i++)
				fos.write(str.charAt(i));
			//dos.writeUTF(str);
			dos.close();
			fos.close();
			if(cflag)
			{ f3.dispose();
			  cflag=false;
			}
			change=false;
			
				}
			catch(Exception e1)
			{
			ta.setText(" File not found");
			}
			}
			
			else{
				
				fd=new FileDialog(f,"select",FileDialog.SAVE);
				fd.setVisible(true);
				if(fd.getFile()==null)
					return;
				saveFlag=true;
				filename=fd.getDirectory()+fd.getFile();
				try{
					File fo=new File(filename);
			FileOutputStream fos=new FileOutputStream(fo);
			DataOutputStream dos=new DataOutputStream(fos);
			//dos.writeUTF("");
			str=ta.getText();
			//System.out.print(str);
			for(int i=0;i<str.length();i++)
				fos.write(str.charAt(i));
			//dos.writeUTF(str);
			dos.close();
			fos.close();
			if(cflag)
			{ f3.dispose();
			  cflag=false;
			}
			change=false;
			
				}
			catch(Exception e1)
			{
			ta.setText(" File not found");
			}
			}
		}
				
			
		else if(str.equals("SaveAs"))
		{
			saveFlag=true;
				fd=new FileDialog(f,"select",FileDialog.SAVE);
				fd.setVisible(true);
				filename=fd.getDirectory()+fd.getFile();
				try{
					File fo=new File(filename);
			FileOutputStream fos=new FileOutputStream(fo);
			//dos.writeUTF("");
			str=ta.getText();
			//System.out.print(str);
			for(int i=0;i<str.length();i++)
				fos.write(str.charAt(i));
			fos.close();
			if(cflag)
			{ f3.dispose();
			  cflag=false;
			}
			change=false;
			
				}
			catch(Exception e1)
			{
			ta.setText(" File not found");
			}
			}
		
		else if(str.equals("Find"))
		{	
			f1=new Frame();
			f1.setSize(250,150);
			f1.setLayout(new GridLayout(2,2));
			Label lf=new Label("Find What");
			f1.add(lf);
			
			f1.add(tf);
			Button bf1=new Button("Find Next");
			Button bf2=new Button("Close");
			bf1.addActionListener(this);
			bf2.addActionListener(this);
			f1.add(bf1);
			f1.add(bf2);
			f1.setVisible(true);
			//WindowCloser wc1=new WindowCloser();
			f1.addWindowListener(this);
			
		}
			
			
		else if(str.equals("Find Next"))
		{	
			if(!m.find())
			{	rflag=false;m=r.matcher(ta.getText());
			if(m.find())
				{ta.select(m.start(),m.end()); rflag=true;}
			}
			else {int start = m.start();
            int end = m.end();
			rflag=true;countr++;
			ta.select(start,end);
			//ta.setSelectedTextColor(Color.red);
			//ta.append(" ("+ta.getSelectedText()+") ");
			}
			
				
		}
		
		else if(str.equals("Replace"))
		{	
			
			if(rflag)
			{
				ta.replaceText(tf2.getText(),m.start(),m.end());m=r.matcher(ta.getText());
				rflag=false;
				countr--;
			}
			for(int i=0;i<countr;i++)
				{last=true;m.find();}
			
		}
			
		else if(str.equals("Replace All"))
		{		m=r.matcher(ta.getText());
				while(m.find())
				{	
					ta.replaceText(tf2.getText(),m.start(),m.end());
					m=r.matcher(ta.getText());
					/*try{Thread.sleep(1000);}
					catch(Exception ee)
					{}*/
				}
			
		}
		
		else if(str.equals("Find&Replace"))
		{	
			f1=new Frame();
			f1.setSize(250,150);
			f1.setLayout(new GridLayout(4,2));
			Label lf=new Label("Find What");
			f1.add(lf);
			
			f1.add(tf1);
			Label lf1=new Label("Replace What");
			f1.add(lf1);
			tf2=new TextField(10);
			f1.add(tf2);
			Button bf3=new Button("Find Next");
			Button bf4=new Button("Replace");
			f1.add(bf3);
			f1.add(bf4);
			bf3.addActionListener(this);
			bf4.addActionListener(this);
			
			Button bf11=new Button("Close");
			Button bf21=new Button("Replace All");
			bf11.addActionListener(this);
			bf21.addActionListener(this);
			f1.add(bf11);
			f1.add(bf21);
			f1.setVisible(true);
			//WindowCloser wc1=new WindowCloser();
			f1.addWindowListener(this);
			
		}
		
		
		
		
		
		else
			ta.append(" "+str);
		
	}
	
	public static void main(String args[])
	{
		new Editor();
	}
}

