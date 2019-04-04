package aeroplane;

public class Fighter implements FighterInterface{
	private String fighterName;//航班名
	private int row;//座位排数
	private int rowLength;//每排座位数
	private int[] fail= {-1};//返回预定号
	private Passenger [] passengerList;//预定座位的旅客
	public Fighter(String FighterName,int rows,int rowLength) 
	throws Exception{
		if(FighterName==null||FighterName.trim().length()==0||rows<=0||rowLength<=0)
			throw new Exception("Error");
		else {
			this.fighterName=FighterName;
			this.row=rows;
			this.rowLength=rowLength;
			this.passengerList =new Passenger[row*rowLength];//创建航班座位
			for(int i=0;i<row*rowLength;i++)
				passengerList[i]=null;
		}
	}

	public int[] reserve(String names[]) {//预定航班座位
		if(names.length>rowLength)
			return fail;
		int i=0,j=0,k=0;
		//true能安排 false不能安排
		boolean flag=false;
		//找同一排相邻且没有被预定的座位，座位个数为names.length
		labelA:for(i=0;i<=row-1;i++) {
			for(j=0;j<=rowLength-names.length;j++) {
				//在本行从j到j+names.length-1照这样的位置
				for(k=j;k<=j+names.length-1;k++) {
					if(passengerList[i*rowLength+k]!=null)
						break;
				}
		
				
				if(k>j+names.length-1) {//已找到，从第i 行第j列开始
					flag=true;//设置已找到的标志
					break labelA;//退出整个循环
				}
			}
			}
			if(!flag)
				return fail;//从第i行第j列开始分配位置
			int [] bn=new int[names.length];//每个旅客返回一个预定号
			for(k=j;k<=j+names.length-1;k++) {
				bn[k-j]=i*rowLength+k+1;          //产生预定号，names[0]对应bn[0]
				passengerList[i*rowLength+k]=new Passenger(names[k-j],i*rowLength+k+1,i,k);
			}
			return bn;
		}
	
	public boolean cancel(int bokingNumber) {//取消航班预定
		boolean Status=false;
		for(int i=0;i<row*rowLength;i++) {
			if(passengerList[i]!=null&&bokingNumber==passengerList[i].getBookingNum()){
				Status=true;
				passengerList[i]=null;
				break;
			}
		}
		return Status;
		
	}
	
	public Passenger [] getPassengerList(){
		return passengerList;
		
	}
	
	
}
