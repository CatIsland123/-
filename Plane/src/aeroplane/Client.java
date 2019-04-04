package aeroplane;
import java.util.*;
import java.io.*;
public class Client {//用户操作界面
    private String fightName=null;//航班名
    private int row=0;//座位排数
    private int rowLength=0;//每排座位数
    private Fighter flight; //本次航班对象
    private String cmdString=null;//命令串
    private BufferedReader br=new BufferedReader(new InputStreamReader(System.in));//获取控制台命令
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Client().commandShell();//命令shell

	}
	public void commandShell() {
		System.out.println("\n\n===============");
		System.out.println("CommandShell v2.01");
		System.out.println("type'exit'command to 'exit'.");
		while(true) {
			readCommand();//读命令
			processCommand();//处理命令
		}
		
	}

	private void readCommand() {//从控制台读入命令
		//若还没有航班，先创建航班
		if(fightName== null) {
			System.out.println("********************");
			System.out.println("Please Creat The Flight Data first");
			System.out.println("Use command:creat flight-name rows rowLength");
			System.out.println("********************\n");
		}
		System.out.println("\nComamd ");
		try {
			cmdString=br.readLine().trim();
		}catch(Exception e) {
			System.out.println("Command Error");
			cmdString=null;
		}
	}
	private void processCommand(){//分析命令串 
		//creat 放在cmds[0],"sk213"放在cmds[1]
        String []cmds;		
	    String cmd;
	    if(cmdString!=null) {
	    	cmds=command(cmdString);
	    	if(cmds!=null) {
	    		cmd=cmds[0].toLowerCase();
	    		if(cmd.equals("create")) {
	    			if(fightName==null)
	    				createCommand(cmds);
	    			else {
	    				System.out.println("Create Error:cant handle more flights");
	    			}
	    		}
	    			else if(cmd.equals("reserve")) {
	    				if(fightName!=null)
	    					reserveCommand(cmds);
	    			}
	    			else if(cmd.equals("cancel")) {
	    				if(fightName!=null)
	    					cancelCommand(cmds);
	    			}
	    			else if(cmd.equals("list")) {
	    				if(fightName!=null)
	    					listCommand(cmds);
	    			}
	    			else if(cmd.equals("exit")) {
	    				System.out.println("Thanks See you later");
	    				System.exit(0);
	    			}else {
	    				System.out.println("Bad Command");
	    				cmdString=null;
	    			}
	    		}
	    	}
	    }
	
	private String[] command(String cmdStr) {//把字符串类型转换为整型//分隔字符串
		int cc=0;
		String []cmd;
		StringTokenizer st=new StringTokenizer(cmdStr);
		if((cc=st.countTokens())==0)
			return null;
		cmd=new String[cc];
		for(int i=0;i<cc;i++) 
		     cmd[i]=st.nextToken();
		
		return cmd;
		
		
	}
	private int readInt(String valstr) {
		int val=0;
		try {
			val=Integer.parseInt(valstr);
		}catch(Exception e) {
			val=Integer.MIN_VALUE;
		}
		return val;
		
	}
	
	private void createCommand(String[] cmds){//判断命令是否正确
		
	   if(cmds.length!=4) {
		   System.out.println("create command error");
	   }	
	   else {
		   
			fightName=cmds[1];
		    row=readInt(cmds[2]);
	        rowLength=readInt(cmds[3]);
	   }
		   if(row<=0||rowLength<=0) {
			   System.out.println("create command Parameters error");
			   fightName=null;
			   row=0;
			   rowLength=0;
		   }
		   else{
			   try {
				   flight=new Fighter(fightName,row,rowLength);//创建航班
				   System.out.println("create Flight ok!");
			   }catch(Exception e) {
				   System.out.println(e);
				   flight=null;
				   row=0;
				   rowLength=0;
			   }
		   }
	   }
	
	
	private void reserveCommand(String[]cmds) {//用户名放在cmds[1],cmds[2]。。。
		if(cmds.length<=1) {
			System.out.println("reserve command error");
			return;
		}
		String[] names=new String[cmds.length-1];
		for(int i=0;i<names.length;i++) 
			names[i]=new String(cmds[i+1]);
		int [] bn=flight.reserve(names);
		if(bn[0]!=-1) {
			for(int i=0;i<bn.length;i++)
				System.out.println(names[i]+"s Booking Number is:"+bn[i]);
		}else {
			System.out.println("NO uch sequential seats Now");
		}
	}
		private void cancelCommand(String[]cmds) {//取消预定座位
			if(cmds.length !=2) {
				System.out.println("\ncancel command format error");
				return;
			}
			int bookingNumber=readInt(cmds[1]);
			if(bookingNumber<=0) {
				System.out.println("\n\ncancel command parameter error");
				return;
			}
			boolean state=flight.cancel(bookingNumber);
			if(state) 
				System.out.println("Your seat has been cancellend");
			else 
				System.out.println("The seat has not been reserved");
		}
		private void listCommand(String[]cmds) {
			if(cmds.length !=1) {
				System.out.println("\nlist command format error");
				return;
			}
			Passenger[] passengerlist=flight.getPassengerList();
			int flag=0;
			System.out.println("Name  BookingNumber  Row  SeatPosition");
			System.out.println("-----------------");
			if(passengerlist==null||passengerlist.length<=0)
				System.out.println("Now no seat is occupied");
			else {
				flag=0;
				for(int b=0;b<passengerlist.length;b++) {
					if(passengerlist[b]!=null) {
						flag=1;
						System.out.println(formatStr(passengerlist[b].getName())+formatStr(""+passengerlist[b].getBookingNum())+formatStr(""+passengerlist[b].getrow())+formatStr(""+passengerlist[b].getSeatPosition()));
					}
				}
				if(flag==0)
					System.out.println("Now no seat is occupied");
			}
		}
		private String formatStr(String s) {
			for(int i=0;i<10-s.trim().length();i++)
				s+=' ';
			return s;
		}
	}

